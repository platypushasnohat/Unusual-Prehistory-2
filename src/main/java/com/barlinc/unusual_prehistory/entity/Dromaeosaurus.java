package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus.DromaeosaurusAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus.DromaeosaurusLeapGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus.DromaeosaurusRunGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.PrehistoricMobMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Dromaeosaurus extends PrehistoricMob {

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.7F, 0.5F);

    private int biteTicks;

    public int leapCooldown = 100 + this.getRandom().nextInt(20 * 20);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState fallAnimationState = new AnimationState();
    public final AnimationState startSleepingAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();

    public Dromaeosaurus(EntityType<? extends Dromaeosaurus> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
        this.moveControl = new DromaeosaurusMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DromaeosaurusLeapGoal(this));
        this.goalSelector.addGoal(2, new DromaeosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, 12.0F, 1.0D, 1.0D, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_AVOIDS)));
        this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DROMAEOSAURUS_FOOD), false));
        this.goalSelector.addGoal(6, new DromaeosaurusRunGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)) {
            @Override
            public boolean canUse(){
                return super.canUse() && !Dromaeosaurus.this.isDromaeosaurusEeping() && (Dromaeosaurus.this.level().isDay() || Dromaeosaurus.this.level().dimensionType().fixedTime().isPresent());
            }

            @Override
            public boolean canContinueToUse(){
                return super.canContinueToUse() && !Dromaeosaurus.this.isDromaeosaurusEeping() && (Dromaeosaurus.this.level().isDay() || Dromaeosaurus.this.level().dimensionType().fixedTime().isPresent());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38F);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        SmoothGroundPathNavigation navigation = new SmoothGroundPathNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean canPacifiy() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_DROMAEOSAURUS);
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

        if (!this.level().isClientSide) {
            if (this.isEepyTime()) {
                this.eep();
            } else {
                this.wakeUp();
            }
        }

        this.setSprinting(this.getDeltaMovement().horizontalDistance() > 0.05D);
        this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, yBodyRot, 60);

        if (this.isDromaeosaurusEeping() && this.isInWaterOrBubble()) this.wakeUpInstantly();

        if (leapCooldown > 0) this.leapCooldown--;
    }

    @Override
    public boolean shouldDoEepyParticles() {
        return this.isDromaeosaurusEeping();
    }

    private boolean isEepyTime() {
        return this.level().isNight() && this.getHealth() > this.getMaxHealth() * 0.5F && !this.isInWater() && this.onGround() && this.getBehavior().equals(Behaviors.IDLE.getName()) && !this.isLeashed();
    }

    @Override
    public void setupAnimationCooldowns() {
        if (biteTicks > 0) this.biteTicks--;
        if (this.biteTicks == 0 && this.getPose() == UP2Poses.BITING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void setupAnimationStates() {
        if (this.biteTicks == 0 && this.biteAnimationState.isStarted()) this.biteAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isDromaeosaurusEeping(), this.tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger(), this.tickCount);

        if (this.isDromaeosaurusVisuallyEeping()) {
            this.fallAnimationState.stop();
            this.biteAnimationState.stop();
            this.wakeUpAnimationState.stop();
            this.idleAnimationState.stop();

            if (this.isVisuallyEeping()) {
                this.startSleepingAnimationState.startIfStopped(this.tickCount);
                this.sleepAnimationState.stop();
            } else {
                this.startSleepingAnimationState.stop();
                this.sleepAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.startSleepingAnimationState.stop();
            this.sleepAnimationState.stop();
            this.wakeUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) {
                this.biteAnimationState.start(this.tickCount);
                this.biteTicks = 10;
            }
            else {
                this.biteAnimationState.stop();
            }
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.DROMAEOSAURUS.get().create(level);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        this.wakeUpInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.RESTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 4.0F && this.isDromaeosaurusEeping() && !this.isInPoseTransition()) {
            this.wakeUp();
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isDromaeosaurusEeping();
    }

    public boolean isDromaeosaurusEeping() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isDromaeosaurusVisuallyEeping() {
        return this.getPoseTime() < 0L != this.isDromaeosaurusEeping();
    }

    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (5);
    }

    private boolean isVisuallyEeping() {
        return this.isDromaeosaurusEeping() && this.getPoseTime() < 5L && this.getPoseTime() >= 0L;
    }

    public void eep() {
        if (this.isDromaeosaurusEeping()) return;
        this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void wakeUp() {
        if (!this.isDromaeosaurusEeping()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void wakeUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.refreshDimensions();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isDromaeosaurusEeping() ? UP2SoundEvents.DROMAEOSAURUS_EEPY.get() : UP2SoundEvents.DROMAEOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.DROMAEOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DROMAEOSAURUS_DEATH.get();
    }

    private static class DromaeosaurusMoveControl extends PrehistoricMobMoveControl {

        private final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusMoveControl(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public void tick() {
            if (!this.dromaeosaurus.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.isDromaeosaurusEeping() && !this.dromaeosaurus.isInPoseTransition()) {
                    this.dromaeosaurus.wakeUp();
                }
                super.tick();
            }
        }
    }
}
