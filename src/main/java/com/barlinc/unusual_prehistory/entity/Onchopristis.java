package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.GroundseekingRandomSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargeBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.OnchopristisAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Onchopristis extends PrehistoricAquaticMob {

    public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(Onchopristis.class, EntityDataSerializers.INT);
    private static final EntityDimensions BURROWED_DIMENSIONS = EntityDimensions.scalable(1.2F, 0.34F);

    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState steppedOnAnimationState = new AnimationState();
    public final AnimationState burrowStartAnimationState = new AnimationState();
    public final AnimationState burrowAnimationState = new AnimationState();
    public final AnimationState burrowEndAnimationState = new AnimationState();

    private int attackTicks;
    private int stepTicks;

    private final byte STEP = 60;

    public Onchopristis(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new OnchopristisMoveControl(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.64F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.ONCHOPRISTIS_FOOD), false));
        this.goalSelector.addGoal(4, new OnchopristisAttackGoal(this));
        this.goalSelector.addGoal(5, new GroundseekingRandomSwimGoal(this, 1.0D, 60, 12, 12, 0.02));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.ONCHOPRISTIS_TARGETS)));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.3 * this.getSpeed(), 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.stepTicks > 0) stepTicks--;

        if (this.isAlive() && this.isOnchopristisBurrowed() && !this.level().isClientSide && this.getTarget() == null && this.stepTicks == 0) {
            this.getSteppedOn();
        }

        if (this.isInWater() && this.getBehavior().equals(Behaviors.IDLE.getName())) {
            if (this.getBurrowCooldown() > 0) this.setBurrowCooldown(this.getBurrowCooldown() - 1);
        }

        this.tickBurrowing();
    }

    private void tickBurrowing() {
        if (this.isOnchopristisBurrowed() && !this.isInWater()) this.exitBurrowInstantly();

        if (this.isInWater()) {
            if (!this.isLeashed() && this.getBurrowCooldown() == 0 && this.onGround() && !this.isOnchopristisBurrowed() && this.getBehavior().equals(Behaviors.IDLE.getName())) {
                this.burrow();
            }
            if ((!this.onGround() || this.isOnchopristisBurrowed()) && this.getBurrowCooldown() == 0 && !this.isLeashed()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.05D, 0.0D));
            }
        }

        if (this.isOnchopristisBurrowed() && this.getRandom().nextInt(2200) == 0) {
            this.burrowCooldown();
            this.exitBurrow();
        }
    }

    private void getSteppedOn() {
        this.level().getEntities(this, this.getAggroHitbox()).forEach((entity) -> {
            if (entity instanceof LivingEntity mob && mob.isAlive() && !(mob instanceof Onchopristis)) {
                if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(mob) && !this.isPacified()) {
                    this.setTarget(mob);
                    this.exitBurrowInstantly();
                    this.burrowCooldown();
                }
                this.level().broadcastEntityEvent(this, this.STEP);
                this.stepTicks = 5;
            }
        });
    }

    @NotNull
    private AABB getAggroHitbox() {
        return this.getBoundingBox().inflate(0.25, 0.0, 0.25).move(0, 0.36, 0);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.BURROWED.get() ? BURROWED_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isOnchopristisBurrowed();
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F && this.isOnchopristisBurrowed() && !this.isInPoseTransition()) {
            this.exitBurrow();
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        this.exitBurrowInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    public void burrowCooldown() {
        this.entityData.set(BURROW_COOLDOWN, 300 + random.nextInt(60 * 60));
    }

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
        if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        if (this.isOnchopristisVisuallyBurrowed()) {
            this.burrowEndAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.swimIdleAnimationState.stop();
            if (this.isVisuallyBurrowed()) {
                this.burrowStartAnimationState.startIfStopped(this.tickCount);
                this.burrowAnimationState.stop();
            } else {
                this.burrowStartAnimationState.stop();
                this.burrowAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.burrowStartAnimationState.stop();
            this.burrowAnimationState.stop();
            this.burrowEndAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.attackTicks > 0) attackTicks--;
        if (this.attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                else this.attack2AnimationState.start(this.tickCount);
                this.attackTicks = 20;
            }
            else {
                this.attack1AnimationState.stop();
                this.attack2AnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == this.STEP) this.steppedOnAnimationState.start(this.tickCount);
        else super.handleEntityEvent(id);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isOnchopristisBurrowed();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BURROW_COOLDOWN, 50 * 50 + getRandom().nextInt(60 * 2 * 20));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BurrowCooldown", this.getBurrowCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBurrowCooldown(compoundTag.getInt("BurrowCooldown"));
    }

    public int getBurrowCooldown() {
        return this.entityData.get(BURROW_COOLDOWN);
    }

    public void setBurrowCooldown(int cooldown) {
        this.entityData.set(BURROW_COOLDOWN, cooldown);
    }

    public boolean isOnchopristisBurrowed() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isOnchopristisVisuallyBurrowed() {
        return this.getPoseTime() < 0L != this.isOnchopristisBurrowed();
    }

    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (20);
    }

    private boolean isVisuallyBurrowed() {
        return this.isOnchopristisBurrowed() && this.getPoseTime() < 20L && this.getPoseTime() >= 0L;
    }

    public void burrow() {
        if (this.isOnchopristisBurrowed()) return;
        this.setPose(UP2Poses.BURROWED.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void exitBurrow() {
        if (!this.isOnchopristisBurrowed()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void exitBurrowInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.refreshDimensions();
    }

    @Override
    public boolean canPacifiy() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_ONCHOPRISTIS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.ONCHOPRISTIS_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.ONCHOPRISTIS.get().create(level);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ONCHOPRISTIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.ONCHOPRISTIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.ONCHOPRISTIS_FLOP.get();
    }

    private static class OnchopristisMoveControl extends SmoothSwimmingMoveControl {

        protected final Onchopristis onchopristis;

        public OnchopristisMoveControl(Onchopristis onchopristis) {
            super(onchopristis, 85, 10, 0.02F, 0.1F, false);
            this.onchopristis = onchopristis;
        }

        @Override
        public void tick() {
            if (!this.onchopristis.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.onchopristis.isLeashed() && this.onchopristis.isOnchopristisBurrowed() && !this.onchopristis.isInPoseTransition()) {
                    this.onchopristis.exitBurrow();
                }
                super.tick();
            }
        }
    }
}
