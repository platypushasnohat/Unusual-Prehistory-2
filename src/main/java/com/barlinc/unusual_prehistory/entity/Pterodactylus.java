package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.AnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PterodactylusFlyAndHangGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PterodactylusScatterGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Pterodactylus extends PrehistoricFlyingMob {

    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(Pterodactylus.class, EntityDataSerializers.BOOLEAN);

    private boolean validHangingPos = false;
    private int checkHangingTime;
    private BlockPos prevHangPos;
    public int timeHanging = 0;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState hangIdleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState flyFastAnimationState = new AnimationState();
    public final AnimationState hoverAnimationState = new AnimationState();
    public final AnimationState stretchAnimationState = new AnimationState();
    public final AnimationState hangingStretchAnimationState = new AnimationState();

    private int stretchCooldown = 400 + this.getRandom().nextInt(600);

    public Pterodactylus(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricFlyingMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.FLYING_SPEED, 0.8F)
                .add(Attributes.MOVEMENT_SPEED, 0.01F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PterodactylusScatterGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PTERODACTYLUS_FOOD), true));
        this.goalSelector.addGoal(3, new PterodactylusFlyAndHangGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new PterodactylusStretchGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothFlyingPathNavigation(this, level, 1.0F);
    }

    @Override
    public void switchNavigator(boolean onLand) {
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.7F;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() || this.onGround() || this.isHanging()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && source.getEntity() != null && this.isAlive()) {
            double range = 8;
            this.setFlying(true);
            this.setRunning(true);
            if (this.isHanging()) this.setHanging(false);
            this.setRunningTicks(this.getFastFlyingTicks());
            List<? extends Pterodactylus> entities = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (Pterodactylus pterodactylus : entities) {
                pterodactylus.setFlying(true);
                pterodactylus.setRunning(true);
                if (pterodactylus.isHanging()) pterodactylus.setHanging(false);
                pterodactylus.setRunningTicks(pterodactylus.getFastFlyingTicks());
            }
        }
        return hurt;
    }

    public boolean canHangFrom(BlockPos pos, BlockState state) {
        return state.isFaceSturdy(level(), pos, Direction.DOWN) && level().isEmptyBlock(pos.below()) && level().isEmptyBlock(pos.below(2));
    }

    public BlockPos posAbove() {
        return BlockPos.containing(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getRunningTicks() > 0) this.setRunningTicks(this.getRunningTicks() - 1);
        if (this.isRunning() && this.getRunningTicks() == 0) this.setRunning(false);

        if (!level().isClientSide) this.tickHanging();
    }

    private void tickHanging() {
        if (this.isHanging()) {
            BlockPos above = posAbove();
            if (checkHangingTime-- < 0 || random.nextFloat() < 0.1F || prevHangPos != above) {
                this.validHangingPos = this.canHangFrom(above, level().getBlockState(above));
                this.checkHangingTime = 5 + random.nextInt(5);
                this.prevHangPos = above;
            }
            if (validHangingPos) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.1F, 0.3F, 0.1F).add(0, 0.08D, 0));
            } else {
                this.setHanging(false);
                this.setFlying(true);
            }
            this.timeHanging++;
            if (this.isHanging() && timeHanging > 800) {
                this.setFlying(true);
                this.setHanging(false);
            }
        } else {
            this.timeHanging = 0;
            this.validHangingPos = false;
            this.prevHangPos = null;
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (stretchCooldown > 0) stretchCooldown--;
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1;
    }

    public int getFastFlyingTicks() {
        return 100 + this.getRandom().nextInt(50);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && !this.isHanging(), this.tickCount);
        this.hangIdleAnimationState.animateWhen(!this.isFlying() && this.isHanging(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 && !this.isRunning() && !this.isHanging(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 && this.isRunning() && !this.isHanging(), this.tickCount);
        this.hoverAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING, this.tickCount);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> {
                if (this.isHanging()) this.hangingStretchAnimationState.start(this.tickCount);
                else this.stretchAnimationState.start(this.tickCount);
            }
            case 68 -> {
                this.hangingStretchAnimationState.stop();
                this.stretchAnimationState.stop();
            }
            default -> super.handleEntityEvent(id);
        }
    }

    protected void stretchCooldown() {
        this.stretchCooldown = 400 + this.getRandom().nextInt(600);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.PTERODACTYLUS_FOOD);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HANGING, false);
    }

    public boolean isHanging() {
        return this.entityData.get(HANGING);
    }

    public void setHanging(boolean hanging) {
        this.entityData.set(HANGING, hanging);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PTERODACTYLUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PTERODACTYLUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PTERODACTYLUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        Pterodactylus pterodactylus = UP2Entities.PTERODACTYLUS.get().create(level);
        pterodactylus.setVariant(this.getVariant());
        return pterodactylus;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    public enum PterodactylusVariant {
        PTERODACTYLUS(0),
        BANANA(1);

        private final int id;

        PterodactylusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static PterodactylusVariant byId(int id) {
            if (id < 0 || id >= PterodactylusVariant.values().length) {
                id = 0;
            }
            return PterodactylusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return PterodactylusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData spawnData, @org.jetbrains.annotations.Nullable CompoundTag compoundTag) {
        if (level.getRandom().nextFloat() < 0.2F) this.setVariant(1);
        else this.setVariant(0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Pterodactylus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.PTERODACTYLUS_SPAWNABLE_ON);
    }

    // Goals
    private static class PterodactylusStretchGoal extends AnimationGoal {

        private final Pterodactylus pterodactylus;

        public PterodactylusStretchGoal(Pterodactylus pterodactylus) {
            super(pterodactylus, 40, 1, (byte) 67, (byte) 68);
            this.pterodactylus = pterodactylus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && pterodactylus.stretchCooldown == 0 && (pterodactylus.onGround() || pterodactylus.isHanging());
        }

        @Override
        public void stop() {
            super.stop();
            this.pterodactylus.stretchCooldown();
        }
    }
}