package com.unusualmodding.unusual_prehistory.entity.base;

import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.*;
import com.unusualmodding.unusual_prehistory.entity.behaviors.BaseBehaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public abstract class PrehistoricMob extends Animal {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.LONG);

    private boolean useLowerFluidJumpThreshold = false;
    private int healCooldown = 600;
    public int idleAnimationTimeout = 0;

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothTurningMoveControl(this);
        this.lookControl = new RefuseToMoveLookControl(this);
        setPersistenceRequired();
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new RefuseToMoveBodyRotationControl(this);
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
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (isInWater() && horizontalCollision) {
            setUseLowerFluidJumpThreshold(true);
        }
    }

    public float getMaxTurnDistancePerTick() {
        return Mth.clamp(90 - getBbWidth() * 35, 5, 90);
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
    public void tick () {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.tickCount % healCooldown == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }
    }

    public void setupAnimationStates() {
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

    private void resetLastPoseChangeTickToFullStand(long l) {
        this.resetLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }
}
