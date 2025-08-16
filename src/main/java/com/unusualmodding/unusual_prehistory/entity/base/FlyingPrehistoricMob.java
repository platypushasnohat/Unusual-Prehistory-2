package com.unusualmodding.unusual_prehistory.entity.base;

import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.FlyingPathNavigationNoSpin;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.RefuseToMoveBodyRotationControl;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.RefuseToMoveLookControl;
import com.unusualmodding.unusual_prehistory.entity.enums.BaseBehaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class FlyingPrehistoricMob extends Animal implements FlyingAnimal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(FlyingPrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(FlyingPrehistoricMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(FlyingPrehistoricMob.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(FlyingPrehistoricMob.class, EntityDataSerializers.LONG);

    protected float flyProgress;
    protected float prevFlyProgress;
    protected float groundProgress = 5.0F;
    protected float prevGroundProgress = 5.0F;
    protected int timeFlying = 0;
    protected float flightPitch = 0;
    protected float prevFlightPitch = 0;
    protected float flightRoll = 0;
    protected float prevFlightRoll = 0;
    public int groundedFor = 0;
    public boolean isLandNavigator;
    public boolean landingFlag;

    private int healCooldown = 600;
    public int idleAnimationTimeout = 0;

    protected FlyingPrehistoricMob(EntityType<? extends FlyingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new FlyingLookControl(this);
        setPersistenceRequired();
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new FlyingBodyRotationControl(this);
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController();
            this.navigation = new FlyingPathNavigationNoSpin(this, level(), 1.0F);
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isFlying()) {
            this.setPose(Pose.FALL_FLYING);
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean canMate(Animal animal) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        prevFlyProgress = flyProgress;
        prevGroundProgress = groundProgress;
        prevFlightPitch = flightPitch;
        prevFlightRoll = flightRoll;

        this.tickFlight();

        if (this.tickCount % healCooldown == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        tickRotation((float) this.getDeltaMovement().y * 2 * -(float) (180F / (float) Math.PI));
    }

    public void tickFlight() {
        if (isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (onGround() && groundProgress < 5F) {
            groundProgress++;
        }
        if (!onGround() && groundProgress > 0F) {
            groundProgress--;
        }

        if (this.isFlying()) {
            timeFlying++;
            this.setNoGravity(true);
            if (this.isLandNavigator) {
                switchNavigator(false);
            }
            if (groundedFor > 0) {
                this.setFlying(false);
            }
        } else {
            timeFlying = 0;
            this.setNoGravity(false);
            if (!this.isLandNavigator) {
                switchNavigator(true);
            }
        }
        if (groundedFor > 0) {
            groundedFor--;
        }

        if (!level().isClientSide) {
            if (isFlying() && this.isAlive() && !this.isVehicle()) {
                if (landingFlag) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.1D, 0));
                }
                if ((horizontalCollision || this.isInWaterOrBubble()) && !landingFlag) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05D, 0));
                }
            }
            if (this.isFlying() && timeFlying > 40 && this.onGround()) {
                this.setFlying(false);
            }
        }
    }

    public void setupAnimationStates() {
    }

    public void tickRotation(float yMov) {
        flightPitch = yMov;
        float threshold = 1F;
        boolean flag = false;
        if (isFlying() && this.yRotO - this.getYRot() > threshold) {
            flightRoll += 10;
            flag = true;
        }
        if (isFlying() && this.yRotO - this.getYRot() < -threshold) {
            flightRoll -= 10;
            flag = true;
        }
        if (!flag) {
            if (flightRoll > 0) {
                flightRoll = Math.max(flightRoll - 5, 0);
            }
            if (flightRoll < 0) {
                flightRoll = Math.min(flightRoll + 5, 0);
            }
        }
        flightRoll = Mth.clamp(flightRoll, -60, 60);
    }

    public void setHealCooldown(int cooldown) {
        healCooldown = cooldown;
    }

    public boolean isInPoseTransition() {
        return false;
    }

    public long getPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    public boolean refuseToMove() {
        return this.isInPoseTransition();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FLYING, false);
        this.entityData.define(BEHAVIOR, BaseBehaviors.IDLE.getName());
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putString("Behavior", this.getBehavior());
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        this.setBehavior(compoundTag.getString("Behavior"));
        long lastPoseTick = compoundTag.getLong("LastPoseTick");
        this.resetLastPoseChangeTick(lastPoseTick);
    }

    @Override
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }
    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public String getBehavior() {
        return this.entityData.get(BEHAVIOR);
    }
    public void setBehavior(String behavior) {
        this.entityData.set(BEHAVIOR, behavior);
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long l) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, l);
    }
    public void resetLastPoseChangeTickToFullStand(long l) {
        this.resetLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }

    public float getFlightPitch(float partialTick) {
        return (prevFlightPitch + (flightPitch - prevFlightPitch) * partialTick);
    }
    public float getFlightRoll(float partialTick) {
        return (prevFlightRoll + (flightRoll - prevFlightRoll) * partialTick);
    }
    public float getFlyProgress(float partialTick) {
        return (prevFlyProgress + (flyProgress - prevFlyProgress) * partialTick) * 0.2F;
    }
    public float getGroundProgress(float partialTick) {
        return (prevGroundProgress + (groundProgress - prevGroundProgress) * partialTick) * 0.2F;
    }

    public class FlyingMoveController extends MoveControl {
        private final Mob entity;

        public FlyingMoveController() {
            super(FlyingPrehistoricMob.this);
            this.entity = FlyingPrehistoricMob.this;
        }

        public void tick() {
            if (!FlyingPrehistoricMob.this.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    Vec3 vector3d = new Vec3(this.wantedX - entity.getX(), this.wantedY - entity.getY(), this.wantedZ - entity.getZ());
                    double d0 = vector3d.length();
                    double width = entity.getBoundingBox().getSize();
                    Vec3 vector3d1 = vector3d.scale(this.speedModifier * 0.05D / d0);
                    entity.setDeltaMovement(entity.getDeltaMovement().add(vector3d1).scale(0.95D).add(0, -0.01, 0));
                    if (d0 < width) {
                        this.operation = Operation.WAIT;
                    } else if (d0 >= width) {
                        float yaw = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                        entity.setYRot(Mth.approachDegrees(entity.getYRot(), yaw, 8));
                    }
                }
            }
        }
    }

    public static class FlyingBodyRotationControl extends BodyRotationControl {
        protected final FlyingPrehistoricMob mob;

        public FlyingBodyRotationControl(FlyingPrehistoricMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void clientTick() {
            if (!mob.refuseToMove()) super.clientTick();
        }
    }

    public static class FlyingLookControl extends LookControl {
        protected final FlyingPrehistoricMob mob;

        public FlyingLookControl(FlyingPrehistoricMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void tick() {
            if (!mob.refuseToMove()) super.tick();
        }
    }
}
