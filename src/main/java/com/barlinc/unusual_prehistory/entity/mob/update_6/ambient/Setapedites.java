package com.barlinc.unusual_prehistory.entity.mob.update_6.ambient;

import com.barlinc.unusual_prehistory.entity.ai.navigation.AquaticPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Aegirocassis;
import com.barlinc.unusual_prehistory.entity.utils.MobUtils;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class Setapedites extends AmbientMob {

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState(1.0F);

    public Setapedites(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.9F)
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SetapeditesFollowAegirocassisGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 48));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AquaticPathNavigation(this, level);
    }

    @Override
    public boolean canDrownInFluidType(@NotNull FluidType fluidType) {
        return fluidType != NeoForgeMod.WATER_TYPE.value();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    protected void handleAirSupply(int airSupply) {
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

    @Override
    public void baseTick() {
        int airSupply = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(airSupply);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    public boolean isSetapeditesSwimming() {
        return this.isInWaterOrBubble() && !this.onGround();
    }

    public boolean isCrawling() {
        return (this.isInWaterOrBubble() && this.onGround()) || !this.isInWaterOrBubble();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isCrawling(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isSetapeditesSwimming(), this.tickCount);
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
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        if (spawnType == MobSpawnType.NATURAL) {
            int schoolCount = (int) (24 * this.getRandom().nextFloat());
            if (schoolCount > 0 && !this.level().isClientSide) {
                for (int i = 0; i < schoolCount; i++) {
                    float distance = 1.0F;
                    Setapedites entity = new Setapedites(UP2Entities.SETAPEDITES.get(), this.level());
                    entity.moveTo(this.getX() + this.getRandom().nextFloat() * distance, this.getY() + this.getRandom().nextFloat() * distance, this.getZ() + this.getRandom().nextFloat() * distance);
                    this.level().addFreshEntity(entity);
                }
            }
        }
        return spawnData;
    }

    private static class SetapeditesFollowAegirocassisGoal extends Goal {

        protected final Setapedites setapedites;
        private int timeToRecalcPath;
        private int nextStartTick;
        private LivingEntity target;

        public SetapeditesFollowAegirocassisGoal(Setapedites setapedites) {
            this.setapedites = setapedites;
            this.nextStartTick = this.nextStartTick(setapedites);
        }

        protected int nextStartTick(Setapedites setapedites) {
            return reducedTickDelay(40 + setapedites.getRandom().nextInt(40));
        }

        @Override
        public boolean canUse() {
            if (!setapedites.shouldBeRestricted()) return false;
            else if (nextStartTick > 0) {
                this.nextStartTick--;
                return false;
            }
            this.nextStartTick = this.nextStartTick(setapedites);
            List<LivingEntity> list = setapedites.level().getEntitiesOfClass(LivingEntity.class, setapedites.getBoundingBox().inflate(16.0D), entity -> entity instanceof Aegirocassis);
            if (list.isEmpty()) return false;
            this.target = list.getFirst();
            return target != null;
        }

        @Override
        public boolean canContinueToUse() {
            return target != null && target.isAlive() && setapedites.distanceToSqr(target) > 64.0D;
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
        }

        @Override
        public void stop() {
            this.target = null;
            this.setapedites.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (target == null) return;
            if (--timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                this.setapedites.getNavigation().moveTo(target, 1.0D);
            }
        }
    }
}
