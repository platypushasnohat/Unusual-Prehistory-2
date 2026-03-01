package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricFlyingMob extends PrehistoricMob implements FlyingAnimal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PrehistoricFlyingMob.class, EntityDataSerializers.BOOLEAN);

    protected float flyProgress;
    protected float prevFlyProgress;
    public int flightTicks = 0;
    protected float flightPitch = 0;
    protected float prevFlightPitch = 0;
    protected float flightRoll = 0;
    protected float prevFlightRoll = 0;
    public int groundTicks = 0;
    public boolean isLandNavigator;
    public boolean landingFlag;

    public final SmoothAnimationState flyAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState flyFastAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState hoverAnimationState = new SmoothAnimationState();

    protected PrehistoricFlyingMob(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.navigation = new NoSpinGroundPathNavigation(this, this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricFlyingMoveControl(this);
            this.lookControl = new PrehistoricFlyingLookControl(this, 85);
            this.navigation = new NoSpinFlyingPathNavigation(this, this.level());
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean canTrample(@NotNull BlockState state, @NotNull BlockPos pos, float fallDistance) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setFlyingPose();
    }

    public void setFlyingPose() {
        if (this.isFlying()) {
            this.setPose(Pose.FALL_FLYING);
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.prevFlyProgress = this.flyProgress;
        this.prevFlightPitch = this.flightPitch;
        this.prevFlightRoll = this.flightRoll;

        this.tickFlight();
        this.tickRotation((float) this.getDeltaMovement().y * 2 * -(float) (180F / (float) Math.PI));
    }

    public void tickFlight() {
        if (this.isFlying() && flyProgress < 5F) this.flyProgress++;
        if (!this.isFlying() && flyProgress > 0F) this.flyProgress--;

        if (this.isFlying()) {
            this.flightTicks++;
            this.setNoGravity(true);
            if (groundTicks > 0) this.setFlying(false);
            if (this.isLandNavigator) this.switchNavigator(false);
        } else {
            this.flightTicks = 0;
            this.setNoGravity(false);
            if (!this.isLandNavigator) this.switchNavigator(true);
        }

        if (groundTicks > 0) groundTicks--;

        if (!level().isClientSide) {
            if (this.isFlying() && this.isAlive() && !this.isVehicle()) {
                if (landingFlag) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.1D, 0));
                if (horizontalCollision && !landingFlag && !this.isInWater()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05D, 0));
                }
            }
            if (this.isFlying() && flightTicks > 40 && this.onGround()) this.setFlying(false);
        }
    }

    public void tickRotation(float yMov) {
        this.flightPitch = yMov;
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
            if (flightRoll > 0) this.flightRoll = Math.max(flightRoll - 5, 0);
            if (flightRoll < 0) this.flightRoll = Math.min(flightRoll + 5, 0);
        }
        this.flightRoll = Mth.clamp(flightRoll, -60, 60);
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
}
