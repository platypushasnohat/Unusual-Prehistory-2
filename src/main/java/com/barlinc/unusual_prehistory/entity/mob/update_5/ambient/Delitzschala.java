package com.barlinc.unusual_prehistory.entity.mob.update_5.ambient;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.goals.AmbientMoveToRestrictionGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Delitzschala extends AmbientMob implements FlyingAnimal {

    protected float flightPitch = 0.0F;
    protected float prevFlightPitch = 0.0F;
    protected float flightRoll = 0.0F;
    protected float prevFlightRoll = 0.0F;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState flyAnimationState = new SmoothAnimationState(1.0F);

    public Delitzschala(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.01F)
                .add(Attributes.FLYING_SPEED, 0.7F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AmbientMoveToRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new DelitzschalaWanderAroundGoal(this));
        this.goalSelector.addGoal(3, new DelitzschalaLookAroundGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        NoSpinFlyingPathNavigation flyingPathNavigation = new NoSpinFlyingPathNavigation(this, level){
            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return !level().getBlockState(blockPos.below()).isAir();
            }
        };
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(false);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos blockPos, LevelReader levelReader) {
        return levelReader.getBlockState(blockPos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFlying() && this.getDeltaMovement().y < 0.0 && this.isAlive()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }

        if (this.level().isClientSide && this.isAlive()) {
            UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 3);
        }

        this.tickRotation((float) (this.getDeltaMovement().y * 2.0F * -57.295776F));
    }

    public void tickRotation(float yMov) {
        this.prevFlightPitch = this.flightPitch;
        this.prevFlightRoll = this.flightRoll;
        this.flightPitch = yMov;
        float threshold = 1.0F;
        boolean flag = false;
        if (this.isFlying() && this.yRotO - this.getYRot() > threshold) {
            this.flightRoll += 2.0F;
            flag = true;
        }
        if (this.isFlying() && this.yRotO - this.getYRot() < -threshold) {
            this.flightRoll -= 2.0F;
            flag = true;
        }
        if (!flag) {
            if (this.flightRoll > 0.0F) {
                this.flightRoll = Math.max(this.flightRoll - 2.0F, 0.0F);
            }
            if (this.flightRoll < 0.0F) {
                this.flightRoll = Math.min(this.flightRoll + 2.0F, 0.0F);
            }
        }
        this.flightRoll = Mth.clamp(this.flightRoll, -40.0F, 40.0F);
    }

    public float getFlightPitch(float partialTick) {
        return (prevFlightPitch + (flightPitch - prevFlightPitch) * partialTick);
    }

    public float getFlightRoll(float partialTick) {
        return (prevFlightRoll + (flightRoll - prevFlightRoll) * partialTick);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying(), this.tickCount);
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.BUG_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.BUG_DEATH.get();
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    private static class DelitzschalaWanderAroundGoal extends Goal {

        private final Delitzschala delitzschala;

        public DelitzschalaWanderAroundGoal(Delitzschala delitzschala) {
            this.delitzschala = delitzschala;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return delitzschala.navigation.isDone() && delitzschala.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return delitzschala.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3d = this.getRandomLocation();
            if (vec3d != null) {
                this.delitzschala.navigation.moveTo(delitzschala.navigation.createPath(BlockPos.containing(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vec3d2 = delitzschala.getViewVector(0.0F);
            Vec3 vec3d3 = HoverRandomPos.getPos(delitzschala, 10, 3, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(delitzschala, 10, 2, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    private class DelitzschalaLookAroundGoal extends RandomLookAroundGoal {

        private final Delitzschala delitzschala;

        public DelitzschalaLookAroundGoal(Delitzschala delitzschala) {
            super(Delitzschala.this);
            this.delitzschala = delitzschala;
        }

        @Override
        public boolean canUse() {
            return delitzschala.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return delitzschala.onGround() && super.canContinueToUse();
        }
    }
}
