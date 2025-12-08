package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.BreedableMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Talpanas extends BreedableMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();
    public final AnimationState peckAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();

    private int peckCooldown = 300 + this.getRandom().nextInt(300);
    private int shakeCooldown = 240 + this.getRandom().nextInt(240);

    public Talpanas(EntityType<? extends BreedableMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LargePanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.2D, 1.2D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 4.0F, 1.2D, 1.2D, entity -> entity.getType().is(UP2EntityTags.TALPANAS_AVOIDS)));
        this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.3D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(UP2ItemTags.TALPANAS_FOOD), false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new TalpanasPeckGoal(this));
        this.goalSelector.addGoal(7, new TalpanasShakeGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        SmoothGroundPathNavigation navigation = new SmoothGroundPathNavigation(this, level);
        navigation.setAvoidSun(true);
        return navigation;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    @Override
    public double getFluidJumpThreshold() {
        return this.isBaby() ? 0.2D : 0.4D;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && !this.isInWaterOrBubble() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            this.getNavigation().stop();
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TALPANAS.get().create(serverLevel);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.onGround() || this.isInWaterOrBubble(), this.tickCount);
        this.flapAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.PECKING.get()) {
                this.peckAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.PECKING.get()) {
                this.shakeAnimationState.start(this.tickCount);
            }
            else {
                this.peckAnimationState.stop();
                this.shakeAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == UP2Poses.PECKING.get() || this.getPose() == UP2Poses.SHAKING.get();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return true;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TALPANAS_FOOD);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TALPANAS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.TALPANAS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TALPANAS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.06F, 1.0F);
    }

    public static boolean canSpawn(EntityType<Talpanas> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.TALPANAS_SPAWNABLE_ON);
    }

    private static class TalpanasPeckGoal extends Goal {

        private final Talpanas talpanas;
        private int timer;

        public TalpanasPeckGoal(Talpanas talpanas) {
            this.talpanas = talpanas;
        }

        @Override
        public boolean canUse() {
            return talpanas.peckCooldown == 0 && talpanas.getPose() == Pose.STANDING && talpanas.getLastHurtByMob() == null && talpanas.getNavigation().isDone() && talpanas.level().getBlockState(talpanas.blockPosition().below()).is(Blocks.GRASS_BLOCK);
        }

        @Override
        public void start() {
            this.timer = 40;
            this.talpanas.getNavigation().stop();
            this.talpanas.setPose(UP2Poses.PECKING.get());
        }

        @Override
        public boolean canContinueToUse() {
            return timer > 0 && talpanas.getPose() == UP2Poses.PECKING.get() && talpanas.getLastHurtByMob() == null;
        }

        @Override
        public void tick() {
            this.timer--;
        }

        @Override
        public void stop() {
            this.talpanas.peckCooldown = 300 + talpanas.getRandom().nextInt(300);
            this.talpanas.setPose(Pose.STANDING);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    private static class TalpanasShakeGoal extends Goal {

        private final Talpanas talpanas;
        private int timer;

        public TalpanasShakeGoal(Talpanas talpanas) {
            this.talpanas = talpanas;
        }

        @Override
        public boolean canUse() {
            return talpanas.shakeCooldown == 0 && talpanas.getPose() == Pose.STANDING && talpanas.getLastHurtByMob() == null && talpanas.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.timer = 20;
            this.talpanas.getNavigation().stop();
            this.talpanas.setPose(UP2Poses.SHAKING.get());
        }

        @Override
        public boolean canContinueToUse() {
            return timer > 0 && talpanas.getPose() == UP2Poses.SHAKING.get() && talpanas.getLastHurtByMob() == null;
        }

        @Override
        public void tick() {
            this.timer--;
        }

        @Override
        public void stop() {
            this.talpanas.shakeCooldown = 240 + talpanas.getRandom().nextInt(240);
            this.talpanas.setPose(Pose.STANDING);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}