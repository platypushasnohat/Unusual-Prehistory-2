package com.barlinc.unusual_prehistory.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

@SuppressWarnings("deprecation")
public abstract class SemiAquaticMob extends PrehistoricMob {

    public boolean isLandNavigator;

    public int timeInWater;
    public int timeOnLand;

    protected SemiAquaticMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInWater()) {
            timeInWater++;
            timeOnLand = 0;
        } else {
            timeInWater = 0;
            timeOnLand++;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TimeInWater", timeInWater);
        compoundTag.putInt("TimeOnLand", timeOnLand);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        timeInWater = compoundTag.getInt("TimeInWater");
        timeOnLand = compoundTag.getInt("TimeOnLand");
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public int timeInWater() {
        return timeInWater;
    }

    public int timeOnLand() {
        return timeOnLand;
    }
}
