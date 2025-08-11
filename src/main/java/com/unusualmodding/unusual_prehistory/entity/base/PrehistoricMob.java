package com.unusualmodding.unusual_prehistory.entity.base;

import com.unusualmodding.unusual_prehistory.entity.ai.navigation.SmoothTurningMoveControl;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public abstract class PrehistoricMob extends Animal {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    private boolean useLowerFluidJumpThreshold = false;
    private int healCooldown = 600;
    public int idleAnimationTimeout = 0;

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothTurningMoveControl(this);
        setPersistenceRequired();
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
        return Mth.clamp(60 - getBbWidth() * 35, 5, 60);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }
}
