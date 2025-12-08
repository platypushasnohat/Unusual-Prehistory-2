package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.control.TelecrexMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.RandomFlightGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.TelecrexScatterGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothFlyingPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class Telecrex extends PrehistoricFlyingMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState startFlyingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState flyingFastAnimationState = new AnimationState();
    public final AnimationState flyingIdleAnimationState = new AnimationState();
    public final AnimationState lookoutAnimationState = new AnimationState();
    public final AnimationState peckingAnimationState = new AnimationState();

    private int peckingTimer = 0;
    private int lookoutTimer = 0;

    private int startFlyingTicks;

    public Telecrex(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TELECREX_FOOD), false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Telecrex.this.isFlying();
            }
        });
        this.goalSelector.addGoal(3, new RandomFlightGoal(this, 0.8F, 1.4F, 16, 4, 1600, 200));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new TelecrexMoveControl(this);
            this.navigation = new SmoothFlyingPathNavigation(this, this.level(), 1);
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.isEffectiveAi() && this.getPose() == UP2Poses.START_FLYING.get()) {
            double horizontalSpeed = this.isRunning() ? 0.4D : 0.7D;
            travelVec = travelVec.multiply(horizontalSpeed, 1.0D, horizontalSpeed);
        }
        if (this.refuseToMove() && this.onGround()) {
            this.getNavigation().stop();
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && source.getEntity() != null){
            double range = 8;
            this.setFlying(true);
            this.setRunning(true);
            this.setRunningTicks(this.getFastFlyingTicks());
            List<? extends Telecrex> entities = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (Telecrex telecrex : entities) {
                telecrex.setFlying(true);
                telecrex.setRunning(true);
                telecrex.setRunningTicks(this.getFastFlyingTicks());
            }
        }
        return hurt;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getRunningTicks() > 0) this.setRunningTicks(this.getRunningTicks() - 1);
        if (this.isRunning() && this.getRunningTicks() == 0) {
            this.setRunning(false);
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (startFlyingTicks > 0) startFlyingTicks--;
        if (startFlyingTicks == 0 && this.getPose() == UP2Poses.START_FLYING.get()) {
            this.setPose(Pose.FALL_FLYING);
            this.setFlying(true);
        }
        if (!this.isInWaterOrBubble() && !this.isFlying() && !this.isPecking() && !this.isLooking()) {
            if (this.random.nextInt(500) == 0 && this.level().getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
                this.setPecking(true);
            }
            if (this.random.nextInt(600) == 0) {
                this.setLooking(true);
            }
        }
        if (this.isPecking() && this.peckingTimer++ > 40) {
            this.peckingTimer = 0;
            this.setPecking(false);
        }
        if (this.isLooking() && this.lookoutTimer++ > 40) {
            this.lookoutTimer = 0;
            this.setLooking(false);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.isImmobile() || this.isPecking() || this.isLooking();
    }

    @Override
    public void setFlyingPose() {
        if (this.isFlying()) {
            if (this.getPose() == Pose.STANDING) this.setPose(UP2Poses.START_FLYING.get());
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    public int getFastFlyingTicks() {
        return 100 + this.getRandom().nextInt(50);
    }

    @Override
    public void setupAnimationStates() {
        if (startFlyingTicks == 0 && this.startFlyingAnimationState.isStarted()) this.startFlyingAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isFlying() && !this.isPecking() && !this.isLooking(), this.tickCount);
        this.flyingAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 && !this.isRunning(), this.tickCount);
        this.flyingFastAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 && this.isRunning(), this.tickCount);
        this.flyingIdleAnimationState.animateWhen(this.isFlying() && this.getPose() == Pose.FALL_FLYING, this.tickCount);
        this.peckingAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isFlying() && this.isPecking(), this.tickCount);
        this.lookoutAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isFlying() && this.isLooking(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.START_FLYING.get()) {
                this.startFlyingAnimationState.start(this.tickCount);
                this.startFlyingTicks = 20;
            }
            else {
                this.startFlyingAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public boolean isPecking() {
        return this.getFlag(16);
    }

    public void setPecking(boolean pecking) {
        this.setFlag(16, pecking);
    }

    public boolean isLooking() {
        return this.getFlag(32);
    }

    public void setLooking(boolean looking) {
        this.setFlag(32, looking);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TELECREX_FOOD);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TELECREX_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.TELECREX_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TELECREX_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.06F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TELECREX.get().create(serverLevel);
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

    public static boolean canSpawn(EntityType<Telecrex> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.TELECREX_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}