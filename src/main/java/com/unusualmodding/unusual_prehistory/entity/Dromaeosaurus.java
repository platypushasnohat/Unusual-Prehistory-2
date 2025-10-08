package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.DromaeosaurusMoveControl;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Particles;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Dromaeosaurus extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> LEAP_COOLDOWN = SynchedEntityData.defineId(Dromaeosaurus.class, EntityDataSerializers.INT);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.7F, 0.5F);

    private int eepyTimer;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState fallAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepEndAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Dromaeosaurus(EntityType<? extends Dromaeosaurus> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
        ((SmoothGroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.refreshDimensions();
        this.moveControl = new DromaeosaurusMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DromaeosaurusSleepGoal(this));
        this.goalSelector.addGoal(2, new DromaeosaurusLeapGoal(this));
        this.goalSelector.addGoal(3, new DromaeosaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, LivingEntity.class, 12.0F, 1.0D, 1.0D, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_AVOIDS)));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(6, new DromaeosaurusRunGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)) {
            public boolean canUse(){
                return super.canUse() && !Dromaeosaurus.this.isBaby() && !Dromaeosaurus.this.isDromaeosaurusSleeping() && Dromaeosaurus.this.level().isDay();
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DROMAEOSAURUS_FOOD);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    public void tick () {
        super.tick();

        Vec3 lookVec = new Vec3(0, 0, -this.getBbWidth() * 1.7F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
        Vec3 eyeVec = this.getEyePosition().add(lookVec);

        if (this.level().isClientSide()) {
            if (this.isDromaeosaurusSleeping()) {
                if (this.eepyTimer == 0) {
                    this.eepyTimer = 40 + random.nextInt(10);
                    this.level().addParticle(UP2Particles.EEPY.get(), eyeVec.x, eyeVec.y + (1.0F - random.nextFloat()) * 0.3F, eyeVec.z, 1, 0, 0);
                }
                if (this.eepyTimer > 0) this.eepyTimer--;
            }
        }

        this.setSprinting(this.getDeltaMovement().horizontalDistance() > 0.05D);
        this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, yBodyRot, 60);

        if (this.isDromaeosaurusSleeping() && this.isInWater()) {
            this.standUpInstantly();
        }

        if (this.getLeapCooldown() > 0) {
            this.setLeapCooldown(this.getLeapCooldown() - 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger(), this.tickCount);
        this.biteAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);

        if (this.isDromaeosaurusVisuallySleeping()) {
            this.fallAnimationState.stop();
            this.idleAnimationState.stop();

            if (this.isVisuallySleeping()) {
                this.sleepStartAnimationState.startIfStopped(this.tickCount);
                this.sleepAnimationState.stop();
            } else {
                this.sleepStartAnimationState.stop();
                this.sleepAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sleepStartAnimationState.stop();
            this.sleepAnimationState.stop();
            this.sleepEndAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEAP_COOLDOWN, 50 * 2 + random.nextInt(50 * 2));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LeapCooldown", this.getLeapCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLeapCooldown(compoundTag.getInt("LeapCooldown"));
        long lastPoseTick = compoundTag.getLong("LastPoseTick");
        if (lastPoseTick < 0L) this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(lastPoseTick);
    }

    public int getLeapCooldown() {
        return this.entityData.get(LEAP_COOLDOWN);
    }

    public void setLeapCooldown(int cooldown) {
        this.entityData.set(LEAP_COOLDOWN, cooldown);
    }

    public void leapCooldown() {
        this.entityData.set(LEAP_COOLDOWN, 50 * 2 + random.nextInt(50 * 2));
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UP2Entities.DROMAEOSAURUS.get().create(level);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float amount) {
        this.standUpInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return (pose == UP2Poses.RESTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 4.0F && this.isDromaeosaurusSleeping() && !this.isInPoseTransition()) {
            this.standUp();
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isDromaeosaurusSleeping();
    }

    public boolean isDromaeosaurusSleeping() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isDromaeosaurusVisuallySleeping() {
        return this.getPoseTime() < 0L != this.isDromaeosaurusSleeping();
    }

    @Override
    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (5);
    }

    private boolean isVisuallySleeping() {
        return this.isDromaeosaurusSleeping() && this.getPoseTime() < 5L && this.getPoseTime() >= 0L;
    }

    public void sleep() {
        if (this.isDromaeosaurusSleeping()) return;
        this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUp() {
        if (!this.isDromaeosaurusSleeping()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.refreshDimensions();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isDromaeosaurusSleeping() ? UP2SoundEvents.DROMAEOSAURUS_EEPY.get() : UP2SoundEvents.DROMAEOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.DROMAEOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DROMAEOSAURUS_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return this.isDromaeosaurusSleeping() ? 200 : 160;
    }
}
