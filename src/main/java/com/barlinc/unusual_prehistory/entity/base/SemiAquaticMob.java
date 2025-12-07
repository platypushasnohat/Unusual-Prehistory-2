package com.barlinc.unusual_prehistory.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

@SuppressWarnings("deprecation")
public abstract class SemiAquaticMob extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> TIME_IN_WATER = SynchedEntityData.defineId(SemiAquaticMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_ON_LAND = SynchedEntityData.defineId(SemiAquaticMob.class, EntityDataSerializers.INT);

    public boolean isLandNavigator;

    protected SemiAquaticMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInWater()) {
            this.setTimeInWater(this.getTimeInWater() + 1);
            this.setTimeOnLand(0);
        } else {
            this.setTimeOnLand(this.getTimeOnLand() + 1);
            this.setTimeInWater(0);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TIME_IN_WATER, 0);
        this.entityData.define(TIME_ON_LAND, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TimeInWater", this.getTimeInWater());
        compoundTag.putInt("TimeOnLand", this.getTimeOnLand());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTimeInWater(compoundTag.getInt("TimeInWater"));
        this.setTimeOnLand(compoundTag.getInt("TimeOnLand"));
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public int getTimeInWater() {
        return this.entityData.get(TIME_IN_WATER);
    }

    public void setTimeInWater(int time) {
        this.entityData.set(TIME_IN_WATER, time);
    }

    public int getTimeOnLand() {
        return this.entityData.get(TIME_ON_LAND);
    }

    public void setTimeOnLand(int time) {
        this.entityData.set(TIME_ON_LAND, time);
    }
}
