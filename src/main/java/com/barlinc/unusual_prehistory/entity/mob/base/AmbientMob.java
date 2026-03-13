package com.barlinc.unusual_prehistory.entity.mob.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class AmbientMob extends PathfinderMob {

    protected static final EntityDataAccessor<Integer> DESPAWN_TIME = SynchedEntityData.defineId(AmbientMob.class, EntityDataSerializers.INT);

    protected AmbientMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    public boolean isPushable() {
        return false;
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
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.25F;
    }

    @Override
    public void tick () {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.getDespawnTime() > 0) {
                this.setDespawnTime(this.getDespawnTime() - 1);
            } else if (this.getDespawnTime() == 0) {
                this.discard();
            }
        }
        else {
            this.setupAnimationStates();
        }
    }

    public void setupAnimationStates() {
    }

    // Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DESPAWN_TIME, 1200 + this.getRandom().nextInt(600));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("DespawnTime", this.getDespawnTime());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDespawnTime(compoundTag.getInt("DespawnTime"));
    }

    public int getDespawnTime() {
        return this.entityData.get(DESPAWN_TIME);
    }

    public void setDespawnTime(int despawnTime) {
        this.entityData.set(DESPAWN_TIME, despawnTime);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setDespawnTime(1200 + this.getRandom().nextInt(600));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
    }
}
