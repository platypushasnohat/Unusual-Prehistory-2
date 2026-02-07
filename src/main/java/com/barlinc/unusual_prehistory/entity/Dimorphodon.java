package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomFlightGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Dimorphodon extends PrehistoricFlyingMob {

    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState flyFastAnimationState = new AnimationState();
    public final AnimationState hoverAnimationState = new AnimationState();

    public Dimorphodon(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.17F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIMORPHODON_FOOD), false));
        this.goalSelector.addGoal(3, new PrehistoricRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Dimorphodon.this.isFlying();
            }
        });
        this.goalSelector.addGoal(3, new RandomFlightGoal(this, 1.0F, 1.5F, 16, 5, 1500, 300));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricFlyingMoveControl(this);
            this.navigation = new SmoothFlyingPathNavigation(this, this.level(), 0.75F);
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if ((this.refuseToMove() || this.isDancing()) && this.onGround()) {
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
        if (hurt && source.getEntity() != null && this.isAlive() && this.getHealth() < this.getMaxHealth() * 0.5F) {
            this.setFlying(true);
            this.setRunning(true);
            this.setRunningTicks(this.getFastFlyingTicks());
        }
        return hurt;
    }

    @Override
    public boolean canDanceToJukebox() {
        return !this.isFlying();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isFlying()) {
            if (this.getRunningTicks() > 0) this.setRunningTicks(this.getRunningTicks() - 1);
            if (this.isRunning() && this.getRunningTicks() == 0) this.setRunning(false);
        }
    }

    @Override
    public void setupAnimationCooldowns() {
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2;
    }

    @Override
    public void setFlyingPose() {
        if (this.isFlying()) {
            this.setPose(Pose.FALL_FLYING);
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    public int getFastFlyingTicks() {
        return 80 + this.getRandom().nextInt(60);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isDancing() && !this.isFlying() && this.getIdleState() != 1 && this.getIdleState() != 2, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 && !this.isRunning(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 && this.isRunning(), this.tickCount);
        this.hoverAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING, this.tickCount);
        this.danceAnimationState.animateWhen(this.isDancing(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
    }

    public void handleEntityEvent(byte id) {
        switch (id) {
            default -> super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIMORPHODON_FOOD);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.DIMORPHODON_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.DIMORPHODON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DIMORPHODON_DEATH.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.DIMORPHODON.get().create(serverLevel);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    public static boolean canSpawn(EntityType<Dimorphodon> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.DIMORPHODON_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}