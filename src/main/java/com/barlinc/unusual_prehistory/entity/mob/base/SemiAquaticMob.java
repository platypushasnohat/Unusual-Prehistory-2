package com.barlinc.unusual_prehistory.entity.mob.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

@SuppressWarnings("deprecation")
public abstract class SemiAquaticMob extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> TIME_IN_WATER = SynchedEntityData.defineId(SemiAquaticMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_ON_LAND = SynchedEntityData.defineId(SemiAquaticMob.class, EntityDataSerializers.INT);

    public boolean isLandNavigator;

    protected SemiAquaticMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInWaterOrBubble()) {
            this.setTimeInWater(this.getTimeInWater() + 1);
            this.setTimeOnLand(0);
        } else {
            this.setTimeOnLand(this.getTimeOnLand() + 1);
            this.setTimeInWater(0);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TIME_IN_WATER, 0);
        builder.define(TIME_ON_LAND, 0);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    // entity tag
//    @Override
//    public boolean canBreatheUnderwater() {
//        return true;
//    }

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
