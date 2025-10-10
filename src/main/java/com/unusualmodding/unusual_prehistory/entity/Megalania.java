package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.RefuseToMoveLookControl;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.unusual_prehistory.entity.base.SemiAquaticMob;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.MegalaniaBehaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Megalania extends SemiAquaticMob {

    public static final EntityDataAccessor<Integer> ROAR_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROAR_TIMER = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LEAP_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);

    public TemperatureStates localTemperatureState = TemperatureStates.TEMPERATE;
    public float tempProgress = 0F;
    public float prevTempProgress = 0F;

    private int tongueTimer = 0;
    private int yawnTimer = 0;

    private int leapTimer;

    public enum TemperatureStates {
        TEMPERATE,
        COLD,
        WARM,
        NETHER
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState yawningAnimationState = new AnimationState();
    public final AnimationState tongueAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();
    public final AnimationState bitingAnimationState = new AnimationState();
    public final AnimationState tailWhipAnimationState = new AnimationState();
    public final AnimationState leapingAnimationState = new AnimationState();

    public Megalania(EntityType<? extends Megalania> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
        this.switchNavigator(true);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 8.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LeaveWaterGoal(this, 1.0D, 1200, 2400));
        this.goalSelector.addGoal(1, new MegalaniaAttackGoal(this));
        this.goalSelector.addGoal(2, new MegalaniaLeapGoal(this));
        this.goalSelector.addGoal(3, new CustomizableRandomSwimGoal(this, 1.0D, 50, 10, 5, 3));
        this.goalSelector.addGoal(3, new SemiAquaticRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new MegalaniaRoarGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
            this.lookControl = new LookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.6F, 0.1F, false);
            this.navigation = new AmphibiousPathNavigation(this, level());
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround() && this.getPose() != Pose.LONG_JUMPING) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEAP_COOLDOWN, 60 * 2 + random.nextInt(60 * 2));
        this.entityData.define(ROAR_COOLDOWN, 30 * 40 + random.nextInt(60 * 8 * 40));
        this.entityData.define(ROAR_TIMER, 0);
        this.entityData.define(TEMPERATURE_STATE, 0);
        this.entityData.define(PREV_TEMPERATURE_STATE, -1);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LeapCooldown", this.getLeapCooldown());
        compoundTag.putInt("RoarCooldown", this.getRoarCooldown());
        compoundTag.putInt("RoarTimer", this.getRoarTimer());
        compoundTag.putInt("TemperatureState", this.getTemperatureState().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLeapCooldown(compoundTag.getInt("LeapCooldown"));
        this.setRoarCooldown(compoundTag.getInt("RoarCooldown"));
        this.setRoarTimer(compoundTag.getInt("RoarTimer"));
        this.entityData.set(TEMPERATURE_STATE, compoundTag.getInt("TemperatureState"));
    }

    public int getLeapCooldown() {
        return this.entityData.get(LEAP_COOLDOWN);
    }

    public void setLeapCooldown(int cooldown) {
        this.entityData.set(LEAP_COOLDOWN, cooldown);
    }

    public void leapCooldown() {
        this.entityData.set(LEAP_COOLDOWN, 60 * 2 + random.nextInt(60 * 2));
    }

    public int getRoarTimer() {
        return this.entityData.get(ROAR_TIMER);
    }

    public void setRoarTimer(int timer) {
        this.entityData.set(ROAR_TIMER, timer);
    }

    public int getRoarCooldown() {
        return this.entityData.get(ROAR_COOLDOWN);
    }

    public void setRoarCooldown(int cooldown) {
        this.entityData.set(ROAR_COOLDOWN, cooldown);
    }

    public void roarCooldown() {
        this.entityData.set(ROAR_COOLDOWN, 30 * 40 + random.nextInt(60 * 8 * 40));
    }

    public boolean isLicking() {
        return this.getFlag(16);
    }

    public void setLicking(boolean licking) {
        this.setFlag(16, licking);
    }

    public boolean isYawning() {
        return this.getFlag(32);
    }

    public void setYawning(boolean yawning) {
        this.setFlag(32, yawning);
    }

    public TemperatureStates getTemperatureState() {
        return TemperatureStates.values()[Mth.clamp(entityData.get(TEMPERATURE_STATE), 0, 3)];
    }

    public void setTemperatureState(TemperatureStates state) {
        if (getTemperatureState() != state) {
            this.entityData.set(PREV_TEMPERATURE_STATE, this.entityData.get(TEMPERATURE_STATE));
        }
        this.entityData.set(TEMPERATURE_STATE, state.ordinal());
    }

    public TemperatureStates getPrevTemperatureState() {
        if (entityData.get(PREV_TEMPERATURE_STATE) == -1) {
            return null;
        }
        return TemperatureStates.values()[Mth.clamp(entityData.get(PREV_TEMPERATURE_STATE), 0, 3)];
    }

    @Override
    public void tick() {
        super.tick();

        final boolean ground = !this.isInWater();
        if (!ground && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            switchNavigator(true);
        }

        if (leapTimer > 0) {
            leapTimer--;
        }
        if (leapTimer == 0 && this.getPose() == Pose.LONG_JUMPING) {
            this.setPose(Pose.STANDING);
        }

        if (this.getLeapCooldown() > 0) {
            this.setLeapCooldown(this.getLeapCooldown() - 1);
        }

        if (this.level().isClientSide) {
            if (leapTimer == 0 && this.leapingAnimationState.isStarted()) {
                this.leapingAnimationState.stop();
            }
        }

        if (this.getRoarCooldown() > 0) {
            if (this.getBehavior().equals(MegalaniaBehaviors.ROAR.getName())) {
                this.setBehavior(Behaviors.IDLE.getName());
            }
            this.setRoarCooldown(this.getRoarCooldown() - 1);
        }

        if (this.getRoarTimer() > 0) {
            this.setRoarTimer(this.getRoarTimer() - 1);
            if (this.getRoarTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(Behaviors.IDLE.getName());
                this.roarCooldown();
            }
        }

        if (!this.level().isClientSide) {
            if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() >= 1.0F) {
                if (this.level().dimension() == Level.NETHER) {
                    this.setTemperatureState(TemperatureStates.NETHER);
                } else {
                    this.setTemperatureState(TemperatureStates.WARM);
                }
            } else if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() <= 0.0F) {
                this.setTemperatureState(TemperatureStates.COLD);
            } else {
                this.setTemperatureState(TemperatureStates.TEMPERATE);
            }
        } else {
            this.prevTempProgress = tempProgress;
            if (localTemperatureState != this.getPrevTemperatureState()) {
                localTemperatureState = this.getPrevTemperatureState();
                tempProgress = 0.0F;
            }
            if (this.getPrevTemperatureState() != this.getTemperatureState() && tempProgress < 5.0F) {
                tempProgress += 0.05F;
            }
            if (this.getPrevTemperatureState() == this.getTemperatureState() && tempProgress > 0F) {
                tempProgress -= 0.05F;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() <= 1.0E-5F && !this.isInWaterOrBubble(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.bitingAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.tailWhipAnimationState.animateWhen(this.getAttackState() == 2, this.tickCount);
        this.yawningAnimationState.animateWhen(this.isYawning(), this.tickCount);
        this.tongueAnimationState.animateWhen(this.isLicking(), this.tickCount);
    }

    @Override
    public void setupAnimationCooldowns() {
        if (!this.isInWaterOrBubble()) {
            if (this.getBehavior().equals(Behaviors.IDLE.getName())) {
                if (!this.isLicking() && !this.isYawning() && this.random.nextInt(280) == 0) {
                    this.setLicking(true);
                }
                if (!this.isLicking() && !this.isYawning() && this.random.nextInt(1000) == 0) {
                    this.setYawning(true);
                }
                if (this.isLicking() && this.tongueTimer++ > 20) {
                    this.tongueTimer = 0;
                    this.setLicking(false);
                }
                if (this.isYawning() && this.yawnTimer++ > 80) {
                    this.yawnTimer = 0;
                    this.setYawning(false);
                }
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ROARING.get()) {
                this.roaringAnimationState.start(this.tickCount);
                this.idleAnimationState.stop();
                this.yawningAnimationState.stop();
                this.tongueAnimationState.stop();
                this.tailWhipAnimationState.stop();
                this.bitingAnimationState.stop();
                this.swimmingAnimationState.stop();
                this.leapingAnimationState.stop();
            } else if (this.getPose() == Pose.LONG_JUMPING) {
                this.leapTimer = 20;
                this.leapingAnimationState.start(this.tickCount);
            } else {
                this.roaringAnimationState.stop();
            }
        }
        if (TEMPERATURE_STATE.equals(accessor)) {
            if (this.getTemperatureState().equals(TemperatureStates.COLD)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.12F);
            } else if (this.getTemperatureState().equals(TemperatureStates.TEMPERATE)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.16F);
            } else if (this.getTemperatureState().equals(TemperatureStates.WARM)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.19F);
            } else if (this.getTemperatureState().equals(TemperatureStates.NETHER)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22F);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean refuseToMove() {
        return this.getPose() == UP2Poses.ROARING.get() || this.getPose() == Pose.LONG_JUMPING || super.refuseToMove();
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return UP2Entities.MEGALANIA.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MEGALANIA_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.MEGALANIA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MEGALANIA_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 0.9F);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag compoundTag) {
        this.entityData.set(PREV_TEMPERATURE_STATE, 0);
        this.setTemperatureState(TemperatureStates.TEMPERATE);
        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, spawnDataIn, compoundTag);
    }
}