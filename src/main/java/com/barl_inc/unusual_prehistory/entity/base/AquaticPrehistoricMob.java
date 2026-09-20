package com.barl_inc.unusual_prehistory.entity.base;

import com.platypushasnohat.sinew.client.animation.SmoothAnimationState;
import com.platypushasnohat.sinew.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.platypushasnohat.sinew.entity.ai.navigation.SmoothWaterNavigation;
import com.platypushasnohat.sinew.entity.utils.BodyChainMob;
import com.platypushasnohat.sinew.utils.SinewSoundUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

@SuppressWarnings("deprecation")
public abstract class AquaticPrehistoricMob extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(AquaticPrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    public boolean shallowWater;

    public float swimPitch;
    public float prevSwimPitch;
    public float swimRoll;
    public float prevSwimRoll;

    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState flopAnimationState = new SmoothAnimationState();

    protected AquaticPrehistoricMob(EntityType<? extends AquaticPrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
    }

    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    public boolean isLeaping() {
        return entityData.get(LEAPING);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothWaterNavigation(this, level);
    }

    public void switchShallowNavigation(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = this.createNavigation(this.level());
            this.shallowWater = false;
        }
    }

    public boolean isInShallowWater() {
        return this.isInWaterOrBubble() && this.getFluidHeight(FluidTags.WATER) < this.getBbHeight();
    }

    public boolean shouldUseShallowNavigation() {
        return false;
    }

    public void fixShallowNavigation() {
        if (this.isInShallowWater() && !this.shallowWater) {
            this.switchShallowNavigation(true);
        } else if (!this.isInShallowWater() && this.shallowWater) {
            this.switchShallowNavigation(false);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
            this.travelInWater(travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    public void travelInWater(Vec3 travelVector) {
        this.moveRelative(this.getSpeed(), travelVector);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
        }
    }

    @Override
    public boolean canDrownInFluidType(FluidType fluidType) {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.updateSwimRoll();
            this.updateSwimPitch();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.tickFlopping();
        if (this.shouldUseShallowNavigation()) {
            this.fixShallowNavigation();
        }
    }

    @Override
    public void baseTick() {
        int airSupply = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(airSupply);
    }

    public void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    public float flopChance() {
        return 1.0F;
    }

    public boolean shouldFlop() {
        return true;
    }

    public SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    public void tickFlopping() {
        if (!this.isInWater() && this.onGround() && this.getRandom().nextFloat() < this.flopChance() && this.shouldFlop()) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.getRandom().nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.getRandom().nextFloat() * 2.0F - 1.0F) * 0.2F));
            if (this.getRandom().nextFloat() < 0.25F) {
                this.setYRot(this.getRandom().nextFloat() * 360.0F);
            }
            this.playSound(this.getFlopSound(), this.getSoundVolume(), SinewSoundUtils.randomizePitch(this));
        }
    }

    public void updateSwimRoll() {
        this.prevSwimRoll = this.swimRoll;
        if (this.isInWater()) {
            float turn = Mth.degreesDifference(this.getYRot(), this.yRotO);
            if (Math.abs(turn) > 1.0F) {
                if (Math.abs(this.swimRoll) < this.getRollClamp()) {
                    this.swimRoll -= Math.signum(turn);
                }
            } else if (this.swimRoll != 0.0F) {
                float sign = Math.signum(this.swimRoll);
                this.swimRoll -= sign * 0.9F;
                if (this.swimRoll * sign < 0.0F) {
                    this.swimRoll = 0.0F;
                }
            }
        } else {
            this.swimRoll = 0.0F;
        }
    }

    public void updateSwimPitch() {
        this.prevSwimPitch = this.swimPitch;
        float target = 0.0F;
        if (this.isInWater() || this.isLeaping()) {
            double dx = this.getX() - this.xo;
            double dy = this.getY() - this.yo;
            double dz = this.getZ() - this.zo;
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            double speed = Math.sqrt(horizontal * horizontal + dy * dy);
            float speedFactor = (float) Mth.clamp((speed - 0.01D) / (0.05D - 0.01D), 0.0D, 1.0D);
            if (speedFactor > 0.0F) {
                float angle = (float) (-(Mth.atan2(dy, horizontal) * (180.0D / Math.PI)));
                target = Mth.clamp(angle, -this.getPitchClamp(), this.getPitchClamp()) * speedFactor;
            }
        }
        this.swimPitch += (target - this.swimPitch) * 0.2F;
        if (this instanceof BodyChainMob bodyChainMob) {
            bodyChainMob.getBodyChain().tick(this.yBodyRot, this.swimPitch, target);
        }
    }

    public float getPitchClamp() {
        return 85.0F;
    }

    public float getRollClamp() {
        return 30.0F;
    }

    public float getSwimPitch(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevSwimPitch, this.swimPitch);
    }

    public float getSwimRoll(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevSwimRoll, this.swimRoll);
    }
}
