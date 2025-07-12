package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goal.AttackGoal;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2Particles;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.EnumSet;
import java.util.Objects;

public class Dromaeosaurus extends Animal {

    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Dromaeosaurus.class, EntityDataSerializers.LONG);
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

    public Dromaeosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.refreshDimensions();
        this.moveControl = new DromaeosaurusMoveControl(this);
        this.lookControl = new DromaeosaurusLookControl(this);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new DromaeosaurusBodyRotationControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DromaeosaurusAttackGoal(this));
        this.goalSelector.addGoal(2, new DromaeosaurusRunGoal(this));
        this.goalSelector.addGoal(3, new DromaeosaurusSleepGoal(this));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, LivingEntity.class, 12.0F, 1.0D, 1.0D, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_AVOIDS)));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)) {
            public boolean canUse(){
                return super.canUse() && !Dromaeosaurus.this.isBaby() && !Dromaeosaurus.this.isDromaeosaurusSleeping();
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38F);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.DROMAEOSAURUS_FOOD) && this.getHealth() < this.getMaxHealth()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal((float) itemstack.getFoodProperties(this).getNutrition());
            this.gameEvent(GameEvent.EAT, this);
            this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
            for (int i = 0; i < 3; i++) {
                final double d2 = this.random.nextGaussian() * 0.02D;
                final double d0 = this.random.nextGaussian() * 0.02D;
                final double d1 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemstack), this.getX() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, this.getY() + this.getBbHeight() * 0.5F + (double) (this.random.nextFloat() * this.getBbHeight() * 0.5F), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, d0, d1, d2);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick () {
        super.tick();

        Vec3 lookVec = new Vec3(0, 0, -this.getBbWidth() * 1.7F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
        Vec3 eyeVec = this.getEyePosition().add(lookVec);

        if (this.level().isClientSide()) {
            this.setupAnimationStates();

            if (this.isDromaeosaurusSleeping()) {
                if (this.eepyTimer == 0) {
                    this.eepyTimer = 40 + random.nextInt(10);
                    this.level().addParticle(UP2Particles.EEPY.get(), eyeVec.x, eyeVec.y + (1.0F - random.nextFloat()) * 0.3F, eyeVec.z, 1, 0, 0);
                }
                if (this.eepyTimer > 0) this.eepyTimer--;
            }
        }

        if (this.isDromaeosaurusSleeping() && this.isInWater()) {
            this.standUpInstantly();
        }

        if (this.getLeapCooldown() > 0) {
            this.setLeapCooldown(this.getLeapCooldown() - 1);
        }

        if (this.tickCount % 600 == 0 && this.getHealth() < this.getMaxHealth() && !this.isDromaeosaurusSleeping()) {
            this.heal(2);
        }
        if (this.tickCount % 300 == 0 && this.getHealth() < this.getMaxHealth() && this.isDromaeosaurusSleeping()) {
            this.heal(2);
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble(), this.tickCount);

        if (this.isDromaeosaurusVisuallySleeping()) {
            this.sleepEndAnimationState.stop();
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
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) this.biteAnimationState.start(this.tickCount);
            if (this.getPose() == Pose.STANDING) this.biteAnimationState.stop();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
        this.entityData.define(LEAP_COOLDOWN, 50 * 2 + random.nextInt(50 * 2));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LeapCooldown", this.getLeapCooldown());
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLeapCooldown(compoundTag.getInt("LeapCooldown"));
        long l = compoundTag.getLong("LastPoseTick");
        if (l < 0L) this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(l);
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
    public boolean canFallInLove() {
        return false;
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

    public boolean refuseToMove() {
        return this.isDromaeosaurusSleeping() || this.isInPoseTransition();
    }

    public boolean isDromaeosaurusSleeping() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isDromaeosaurusVisuallySleeping() {
        return this.getPoseTime() < 0L != this.isDromaeosaurusSleeping();
    }

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

    @VisibleForTesting
    public void resetLastPoseChangeTick(long l) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, l);
    }

    private void resetLastPoseChangeTickToFullStand(long l) {
        this.resetLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }

    public long getPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

//    @Nullable
//    @Override
//    protected SoundEvent getAmbientSound() {
//        return UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
//        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getDeathSound() {
//        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
//    }

    // goals
    static class DromaeosaurusAttackGoal extends AttackGoal {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusAttackGoal(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.dromaeosaurus.isBaby() && this.dromaeosaurus.getHealth() > this.dromaeosaurus.getMaxHealth() * 0.5F;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !this.dromaeosaurus.isBaby() && this.dromaeosaurus.getHealth() >= this.dromaeosaurus.getMaxHealth() * 0.5F;
        }

        @Override
        public void start() {
            super.start();
            this.dromaeosaurus.setPose(Pose.STANDING);
        }

        @Override
        public void stop() {
            super.stop();
            this.dromaeosaurus.setPose(Pose.STANDING);
        }

        @Override
        public void tick() {
            LivingEntity target = this.dromaeosaurus.getTarget();
            if (target != null) {
                double distanceToTarget = this.dromaeosaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);

                this.dromaeosaurus.getLookControl().setLookAt(target, 30F, 30F);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

                if (this.dromaeosaurus.getSensing().hasLineOfSight(target) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 0.0 || this.dromaeosaurus.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = target.getX();
                    this.pathedTargetY = target.getY();
                    this.pathedTargetZ = target.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.dromaeosaurus.getRandom().nextInt(7);

                    if (distanceToTarget > 1024.0) this.ticksUntilNextPathRecalculation += 10;
                    else if (distanceToTarget > 256.0) this.ticksUntilNextPathRecalculation += 5;

                    if (!this.dromaeosaurus.getNavigation().moveTo(target, 1.75D)) this.ticksUntilNextPathRecalculation += 15;

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                this.path = this.dromaeosaurus.getNavigation().createPath(target, 0);
                if (this.getAttackReachSqr(target) > 0) this.dromaeosaurus.getNavigation().moveTo(this.path, 1.0D);

                if (this.dromaeosaurus.getPose() == UP2Poses.BITING.get()) tickBite();
                else if (distanceToTarget <= this.getAttackReachSqr(target)) {
                    this.dromaeosaurus.setPose(UP2Poses.BITING.get());
                }
            }
        }

        protected void tickBite() {
            attackTime++;
            LivingEntity target = this.dromaeosaurus.getTarget();
            if (attackTime == 6) {
                if (this.dromaeosaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.dromaeosaurus.doHurtTarget(target);
                    this.dromaeosaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (attackTime >= 15) {
                attackTime = 0;
                this.dromaeosaurus.setPose(Pose.STANDING);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
        }
    }

    static class DromaeosaurusRunGoal extends Goal {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusRunGoal(Dromaeosaurus dromaeosaurus) {
            this.dromaeosaurus = dromaeosaurus;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && !this.dromaeosaurus.isVehicle();
        }

        @Override
        public boolean canContinueToUse() {
            return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && !this.dromaeosaurus.isVehicle();
        }

        @Override
        public void stop() {
            this.dromaeosaurus.setSprinting(false);
            this.dromaeosaurus.getNavigation().stop();
        }

        @Override
        public void tick() {
            this.dromaeosaurus.setSprinting(this.dromaeosaurus.getDeltaMovement().horizontalDistance() > 0.05);

            if (this.dromaeosaurus.getNavigation().isDone()) {
                Vec3 vec3 = LandRandomPos.getPos(this.dromaeosaurus, 15, 7);
                if (vec3 != null) {
                    this.dromaeosaurus.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F);
                }
            }

            if (this.dromaeosaurus.onGround() && this.dromaeosaurus.getLeapCooldown() <= 0) {
                this.dromaeosaurus.addDeltaMovement(new Vec3(0, 0.8D, 0));
                this.dromaeosaurus.addDeltaMovement(this.dromaeosaurus.getLookAngle().scale(2.0D).multiply(0.3D, 0, 0.3D));
                this.dromaeosaurus.leapCooldown();
            }
        }
    }

    static class DromaeosaurusSleepGoal extends Goal {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusSleepGoal(Dromaeosaurus dromaeosaurus) {
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public boolean canUse() {
            return !this.dromaeosaurus.isInWater() && this.dromaeosaurus.level().isNight() && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.onGround() && !this.dromaeosaurus.isAggressive() && this.dromaeosaurus.getHealth() > this.dromaeosaurus.getMaxHealth() * 0.5F;
        }

        @Override
        public boolean canContinueToUse() {
            return !this.dromaeosaurus.isInWater() && this.dromaeosaurus.level().isNight() && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.onGround() && !this.dromaeosaurus.isAggressive() && this.dromaeosaurus.getHealth() > this.dromaeosaurus.getMaxHealth() * 0.5F;
        }

        @Override
        public void start() {
            if (this.dromaeosaurus.isDromaeosaurusSleeping() && (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.isAggressive() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F)) {
                this.dromaeosaurus.standUp();
            } else {
                this.dromaeosaurus.sleep();
            }
        }

        @Override
        public void stop() {
            if (this.dromaeosaurus.isDromaeosaurusSleeping() && (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.isAggressive() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F)) {
                this.dromaeosaurus.standUp();
            }
        }
    }

    private static class DromaeosaurusLookControl extends LookControl {

        protected final Dromaeosaurus dromaeosaurus;

        DromaeosaurusLookControl(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public void tick() {
            if (!this.dromaeosaurus.refuseToMove()) super.tick();
        }
    }

    private static class DromaeosaurusMoveControl extends MoveControl {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusMoveControl(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public void tick() {
            if (!this.dromaeosaurus.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.isDromaeosaurusSleeping() && !this.dromaeosaurus.isInPoseTransition()) {
                    this.dromaeosaurus.standUp();
                }
                super.tick();
            }
        }
    }

    private static class DromaeosaurusBodyRotationControl extends BodyRotationControl {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusBodyRotationControl(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public void clientTick() {
            if (!this.dromaeosaurus.refuseToMove()) super.clientTick();
        }
    }
}
