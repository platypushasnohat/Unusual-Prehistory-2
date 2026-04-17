package com.barlinc.unusual_prehistory.entity.mob.update_6.ambient;

import com.barlinc.unusual_prehistory.entity.ai.control.SwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.AquaticPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
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
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("deprecation")
public class Ampyx extends AmbientMob {

    @Nullable
    private Ampyx lineHead;

    @Nullable
    private Ampyx lineTail;

    @Nullable
    private Ampyx lineLeader;

    protected int schoolSize = 1;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);

    public Ampyx(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SwimmingMoveControl(this, 85, 10, 0.02F);
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
//        this.goalSelector.addGoal(1, new AmpyxFollowLineGoal(this, 2.1F));
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
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));

            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    public void leaveLine() {
        if (this.lineHead != null && this.lineHead.lineTail == this) {
            this.lineHead.lineTail = this.lineTail;
        }
        if (this.lineTail != null && this.lineTail.lineHead == this) {
            this.lineTail.lineHead = this.lineHead;
        }
        if (this.lineLeader != null && this.lineLeader != this) {
            this.lineLeader.schoolSize--;
        }
        this.lineHead = null;
        this.lineTail = null;
        this.lineLeader = null;
    }

    public void joinLine(Ampyx head) {
        if (head == null || head == this) return;
        this.leaveLine();
        this.lineHead = head;
        this.lineTail = head.lineTail;
        head.lineTail = this;
        this.lineLeader = (head.lineLeader != null && head.lineLeader.isAlive()) ? head.lineLeader : head;
        if (lineLeader != null) {
            this.lineLeader.schoolSize++;
        }
    }

    public boolean isLeader() {
        return this.lineHead == null;
    }

    public void setAsLeader() {
        this.lineHead = null;
        this.lineTail = null;
        this.lineLeader = this;
        this.schoolSize = 1;
    }

    public Ampyx getLeader() {
        return (this.lineLeader != null && this.lineLeader.isAlive()) ? this.lineLeader : this;
    }

    @Nullable
    public Ampyx getLineHead() {
        return this.lineHead;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);

        if (spawnType == MobSpawnType.NATURAL) {
            this.setAsLeader();
            int schoolCount = (int) (16 * this.getRandom().nextFloat());
            if (schoolCount > 0 && !this.level().isClientSide) {
                for (int i = 0; i < schoolCount; i++) {
                    Ampyx entity = new Ampyx(UP2Entities.AMPYX.get(), this.level());
                    entity.moveTo(this.getX() + this.getRandom().nextFloat(), this.getY() + this.getRandom().nextFloat(), this.getZ() + this.getRandom().nextFloat());
                    this.level().addFreshEntity(entity);
                }
            }
        } else {
            var nearby = level.getEntitiesOfClass(Ampyx.class, this.getBoundingBox().inflate(16.0D));
            boolean hasLeader = nearby.stream().anyMatch(ampyx -> ampyx.getLeader() != null && ampyx.getLeader().isAlive());
            boolean hasAnyLeader = nearby.stream().anyMatch(Ampyx::isLeader);
            if (!hasLeader && !hasAnyLeader) {
                this.setAsLeader();
            }
        }
        return spawnData;
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

    private static class AmpyxFollowLineGoal extends Goal {

        private final Ampyx ampyx;
        private final double speedModifier;
        private int timeToRecalcPath;

        public AmpyxFollowLineGoal(Ampyx ampyx, double speedModifier) {
            this.ampyx = ampyx;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {

            if (ampyx.isLeader()) return false;

            if (ampyx.getLineHead() != null && ampyx.getLineHead().isAlive()) {
                return true;
            }

            List<Ampyx> list = ampyx.level().getEntitiesOfClass(Ampyx.class, ampyx.getBoundingBox().inflate(10.0D), other -> other != ampyx && other.isAlive());
            Ampyx best = null;
            double max = Double.MAX_VALUE;
            for (Ampyx other : list) {
                double distance = ampyx.distanceToSqr(other);
                if (distance < max) {
                    max = distance;
                    best = other;
                }
            }
            if (best == null) {
                ampyx.setAsLeader();
                return false;
            }
            Ampyx attach = best.isLeader() ? best : best.getLineHead();
            if (attach == null) attach = best;
            this.ampyx.joinLine(attach);
            return ampyx.getLineHead() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return ampyx.getLineHead() != null && ampyx.getLineHead().isAlive() && !ampyx.isLeader();
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
        }

        @Override
        public void stop() {
            this.ampyx.leaveLine();
        }

        @Override
        public void tick() {
            if (ampyx.getLineHead() == null) return;
            if (--timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                Ampyx head = ampyx.getLineHead();
                if (head == null) return;
                double followDistance = 2.5D;
                Vec3 toHead = new Vec3(head.getX() - ampyx.getX(), head.getY() - ampyx.getY(), head.getZ() - ampyx.getZ());
                double dist = toHead.length();
                if (dist < 0.001) return;
                Vec3 dir = toHead.scale(1.0 / dist);
                Vec3 target = new Vec3(head.getX() - dir.x * followDistance, head.getY() - dir.y * followDistance, head.getZ() - dir.z * followDistance);
                this.ampyx.getNavigation().moveTo(target.x, target.y, target.z, speedModifier);
            }
        }
    }
}