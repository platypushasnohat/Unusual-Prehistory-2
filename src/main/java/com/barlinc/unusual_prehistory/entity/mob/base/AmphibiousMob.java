package com.barlinc.unusual_prehistory.entity.mob.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public abstract class AmphibiousMob extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> TIME_IN_WATER = SynchedEntityData.defineId(AmphibiousMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_ON_LAND = SynchedEntityData.defineId(AmphibiousMob.class, EntityDataSerializers.INT);

    public boolean isLandNavigator;

    protected AmphibiousMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
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

    @Override
    public void baseTick() {
        super.baseTick();
        this.handleAirSupply();
    }

    protected void handleAirSupply() {
        this.setAirSupply(300);
    }

    @Override
    public boolean canDrownInFluidType(@NotNull FluidType fluidType) {
        return fluidType != NeoForgeMod.WATER_TYPE.value();
    }
}
