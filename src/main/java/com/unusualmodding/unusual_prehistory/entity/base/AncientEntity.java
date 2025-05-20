package com.unusualmodding.unusual_prehistory.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class AncientEntity extends AgeableMob implements GeoEntity, GeoAnimatable {

    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(AncientEntity.class, EntityDataSerializers.BOOLEAN);

    protected AncientEntity(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount % 200 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && isBaby()){
            return false;
        }
        return prev;
    }

    public boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : pLevel.getPathfindingCostFromLightLevels(pPos);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RUNNING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean bool) {
        this.entityData.set(RUNNING, bool);
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
