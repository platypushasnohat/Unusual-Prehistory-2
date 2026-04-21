package com.barlinc.unusual_prehistory.entity.mob.update_6.ambient;

import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousPathNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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

    private static final EntityDataAccessor<Boolean> LINE_LEADER = SynchedEntityData.defineId(Ampyx.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private Ampyx lineHead;
    @Nullable
    private Ampyx lineTail;

    private int timeSwimming = 0;
    public boolean crawling;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);

    public Ampyx(EntityType<? extends AmbientMob> entityType, Level level) {
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
//        this.goalSelector.addGoal(1, new AmpyxFollowLineGoal(this, 2.1F));
        this.goalSelector.addGoal(2, new AmphyxWanderGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousPathNavigation(this, level);
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

    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getFluidState(pos.below()).isEmpty() && level.getFluidState(pos).is(FluidTags.WATER) ? 10.0F : super.getWalkTargetValue(pos, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
            this.moveRelative(this.getSpeed(), travelVector);
            Vec3 delta = this.getDeltaMovement();
            this.move(MoverType.SELF, delta);
            if (crawling) {
                delta = delta.scale(0.9D);
                if (this.jumping || horizontalCollision) {
                    delta = delta.add(0, 0.1F, 0);
                } else {
                    delta = delta.add(0, -0.05F, 0);
                }
            }
            this.setDeltaMovement(delta.scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (crawling || !this.isInWaterOrBubble()) {
                this.timeSwimming = 0;
            } else {
                this.timeSwimming++;
            }
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
    }

    public void leaveLine() {
        if (this.lineHead != null) {
            this.lineHead.lineTail = null;
        }
        this.lineHead = null;
    }

    public void joinLine(Ampyx ampyx) {
        this.lineHead = ampyx;
        this.lineHead.lineTail = this;
    }

    public boolean hasLineTail() {
        return this.lineTail != null;
    }

    public boolean isInLine() {
        return this.lineHead != null;
    }

    @Nullable
    public Ampyx getLineHead() {
        return this.lineHead;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LINE_LEADER, false);
    }

    public boolean isLineLeader() {
        return this.entityData.get(LINE_LEADER);
    }

    public void setLineLeader(boolean lineLeader) {
        this.entityData.set(LINE_LEADER, lineLeader);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        if (spawnType == MobSpawnType.NATURAL) {
            this.setLineLeader(true);
            int schoolCount = (int) (16 * this.getRandom().nextFloat());
            if (schoolCount > 0 && !this.level().isClientSide) {
                for (int i = 0; i < schoolCount; i++) {
                    Ampyx entity = new Ampyx(UP2Entities.AMPYX.get(), this.level());
                    entity.moveTo(this.getX() + this.getRandom().nextFloat(), this.getY() + this.getRandom().nextFloat(), this.getZ() + this.getRandom().nextFloat());
                    this.level().addFreshEntity(entity);
                }
            }
        } else {
            var nearbyAmpyx = level.getEntitiesOfClass(Ampyx.class, this.getBoundingBox().inflate(16.0D));
            boolean hasPackLeader = nearbyAmpyx.stream().anyMatch(Ampyx::isLineLeader);
            this.setLineLeader(nearbyAmpyx.size() >= 3 && !hasPackLeader);
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

    private static class AmphyxWanderGoal extends Goal {

        private final Ampyx ampyx;
        private double x;
        private double y;
        private double z;
        private boolean isCrawling;

        public AmphyxWanderGoal(Ampyx ampyx) {
            this.ampyx = ampyx;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (ampyx.getRandom().nextInt(20) != 0 && ampyx.crawling) {
                return false;
            }
            if (ampyx.crawling) {
                this.isCrawling = ampyx.getRandom().nextFloat() < 0.5F;
            } else {
                this.isCrawling = ampyx.timeSwimming > 160 || ampyx.getRandom().nextFloat() < 0.15F;
            }
            Vec3 target = this.getPosition();
            if (target == null) {
                return false;
            } else {
                this.x = target.x;
                this.y = target.y;
                this.z = target.z;
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            double dist = ampyx.distanceToSqr(x, y, z);
            return dist > 4F;
        }

        @Override
        public void tick() {
            this.ampyx.crawling = isCrawling;
            this.ampyx.getNavigation().moveTo(this.x, this.y, this.z, 1F);
        }

        public BlockPos findWaterBlock() {
            BlockPos result = null;
            RandomSource random = ampyx.getRandom();
            int range = 10;
            for (int i = 0; i < 15; i++) {
                BlockPos blockPos = ampyx.blockPosition().offset(random.nextInt(range) - range / 2, random.nextInt(range) - range / 2, random.nextInt(range) - range / 2);
                if (ampyx.level().getFluidState(blockPos).is(FluidTags.WATER) && blockPos.getY() > ampyx.level().getMinBuildHeight() + 1) {
                    result = blockPos;
                }
            }
            return result;
        }

        @Nullable
        protected Vec3 getPosition() {
            BlockPos water = findWaterBlock();
            if (ampyx.isInWaterOrBubble()) {
                if (water == null) {
                    return null;
                }
                if (isCrawling) {
                    while (ampyx.level().getFluidState(water.below()).is(FluidTags.WATER) && water.getY() > ampyx.level().getMinBuildHeight() + 1) {
                        water = water.below();
                    }
                    water = water.above();
                }
                return Vec3.atCenterOf(water);
            } else {
                return water == null ? DefaultRandomPos.getPos(ampyx, 7, 3) : Vec3.atCenterOf(water);
            }
        }
    }

    private static class AmpyxFollowLineGoal extends Goal {

        public final Ampyx ampyx;
        private double speedModifier;
        private int distCheckCounter;

        public AmpyxFollowLineGoal(Ampyx ampyx, double speedModifier) {
            this.ampyx = ampyx;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!this.ampyx.isLineLeader() && !this.ampyx.isInLine()) {
                List<Entity> list = this.ampyx.level().getEntities(this.ampyx, this.ampyx.getBoundingBox().inflate(9.0, 4.0, 9.0), entity -> entity instanceof Ampyx && entity != ampyx);
                Ampyx ampyx1 = null;
                double d0 = Double.MAX_VALUE;
                for (Entity entity : list) {
                    Ampyx ampyx2 = (Ampyx) entity;
                    if (ampyx2.isInLine() && !ampyx2.hasLineTail()) {
                        double d1 = this.ampyx.distanceToSqr(ampyx2);
                        if (!(d1 > d0)) {
                            d0 = d1;
                            ampyx1 = ampyx2;
                        }
                    }
                }
                if (ampyx1 == null) {
                    for (Entity entity1 : list) {
                        Ampyx ampyx2 = (Ampyx) entity1;
                        if (ampyx2.isLineLeader() && !ampyx2.hasLineTail()) {
                            double d2 = this.ampyx.distanceToSqr(ampyx2);
                            if (!(d2 > d0)) {
                                d0 = d2;
                                ampyx1 = ampyx2;
                            }
                        }
                    }
                }
                if (ampyx1 == null) {
                    return false;
                } else if (d0 < 4.0) {
                    return false;
                } else if (!ampyx1.isLineLeader() && !this.firstIsLeader(ampyx1, 1)) {
                    return false;
                } else {
                    this.ampyx.joinLine(ampyx1);
                    return true;
                }
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            if (this.ampyx.isInLine() && this.ampyx.getLineHead().isAlive() && this.firstIsLeader(this.ampyx, 0)) {
                double d0 = this.ampyx.distanceToSqr(this.ampyx.getLineHead());
                if (d0 > 676.0) {
                    if (this.speedModifier <= 3.0) {
                        this.speedModifier *= 1.2;
                        this.distCheckCounter = reducedTickDelay(40);
                        return true;
                    }
                    if (this.distCheckCounter == 0) {
                        return false;
                    }
                }
                if (this.distCheckCounter > 0) {
                    this.distCheckCounter--;
                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void stop() {
            this.ampyx.leaveLine();
            this.speedModifier = 2.1;
        }

        @Override
        public void tick() {
            if (this.ampyx.isInLine()) {
                Ampyx lineHead = this.ampyx.getLineHead();
                double distance = this.ampyx.distanceTo(lineHead);
                Vec3 vec3 = new Vec3(lineHead.getX() - this.ampyx.getX(), lineHead.getY() - this.ampyx.getY(), lineHead.getZ() - this.ampyx.getZ()).normalize().scale(Math.max(distance - 2.0, 0.0));
                this.ampyx.getNavigation().moveTo(this.ampyx.getX() + vec3.x, this.ampyx.getY() + vec3.y, this.ampyx.getZ() + vec3.z, this.speedModifier);
            }
        }

        private boolean firstIsLeader(Ampyx ampyx, int queuePosition) {
            if (queuePosition > 8) {
                return false;
            } else if (ampyx.isInLine()) {
                return ampyx.getLineHead().isLineLeader() || this.firstIsLeader(ampyx.getLineHead(), ++queuePosition);
            } else {
                return false;
            }
        }
    }
}