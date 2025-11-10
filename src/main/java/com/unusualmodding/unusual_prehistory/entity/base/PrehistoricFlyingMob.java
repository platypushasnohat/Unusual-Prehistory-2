package com.unusualmodding.unusual_prehistory.entity.base;

import com.unusualmodding.unusual_prehistory.entity.ai.navigation.FlyingPathNavigationNoSpin;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricFlyingMob extends PrehistoricMob implements FlyingAnimal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PrehistoricFlyingMob.class, EntityDataSerializers.BOOLEAN);

    protected float flyProgress;
    protected float prevFlyProgress;
    protected float groundProgress = 5.0F;
    protected float prevGroundProgress = 5.0F;
    public int timeFlying = 0;
    protected float flightPitch = 0;
    protected float prevFlightPitch = 0;
    protected float flightRoll = 0;
    protected float prevFlightRoll = 0;
    public int groundedFor = 0;
    public boolean isLandNavigator;
    public boolean landingFlag;

    public boolean useLowerFluidJumpThreshold = false;

    protected PrehistoricFlyingMob(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new FlyingLookControl(this);
        setPersistenceRequired();
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new FlyingBodyRotationControl(this);
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
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
    public double getFluidJumpThreshold() {
        if (useLowerFluidJumpThreshold) {
            return super.getFluidJumpThreshold();
        }
        return 0.6 * getBbHeight();
    }

    private void setUseLowerFluidJumpThreshold(boolean jumpThreshold) {
        this.useLowerFluidJumpThreshold = jumpThreshold;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isInWater() && horizontalCollision) {
            setUseLowerFluidJumpThreshold(true);
        }
        if (this.isFlying()) {
            this.setPose(Pose.FALL_FLYING);
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isInWaterOrBubble() && !this.isFlying()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.1D, 1.0D));
        }
        super.travel(travelVector);
    }

    @Override
    public void tick() {
        super.tick();

        prevFlyProgress = flyProgress;
        prevGroundProgress = groundProgress;
        prevFlightPitch = flightPitch;
        prevFlightRoll = flightRoll;

        this.tickFlight();
        this.tickRotation((float) this.getDeltaMovement().y * 2 * -(float) (180F / (float) Math.PI));
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
    }

    @Override
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
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
            super(PrehistoricFlyingMob.this);
            this.entity = PrehistoricFlyingMob.this;
        }

        public void tick() {
            if (!PrehistoricFlyingMob.this.refuseToMove()) {
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
        protected final PrehistoricFlyingMob mob;

        public FlyingBodyRotationControl(PrehistoricFlyingMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void clientTick() {
            if (!mob.refuseToMove()) super.clientTick();
        }
    }

    public static class FlyingLookControl extends LookControl {
        protected final PrehistoricFlyingMob mob;

        public FlyingLookControl(PrehistoricFlyingMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void tick() {
            if (!mob.refuseToMove()) super.tick();
        }
    }
}
