package com.barl_inc.unusual_prehistory.entity.base;

import com.platypushasnohat.sinew.entity.base.AnimatedTamableAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public abstract class PrehistoricMob extends AnimatedTamableAnimal {

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPersistenceRequired();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0.0F;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.requiresCustomPersistence();
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        return false;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.6D * this.getBbHeight();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.isAlive() && this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.heal(2);
            }
        }
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        passenger.setYBodyRot(this.yBodyRot);
        passenger.fallDistance = 0.0F;
    }

    @Override
    public void removeVehicle() {
        if (this.isVehicle()) {
            this.getNavigation().stop();
        }
        super.removeVehicle();
    }

    public boolean canPlayAmbientSound() {
        return true;
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null && this.canPlayAmbientSound()) {
            this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
        }
    }
}
