 package com.barlinc.unusual_prehistory.entity.mob.update_3;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.GrabbingMob;
 import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
 import net.minecraft.core.BlockPos;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.LevelReader;
 import net.minecraft.world.level.block.Blocks;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 @SuppressWarnings("deprecation")
 public class Metriorhynchus extends SemiAquaticMob implements LeapingMob, GrabbingMob {

     private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Metriorhynchus.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Metriorhynchus.class, EntityDataSerializers.BOOLEAN);

     public int grabCooldown = 0;
     public int attackCooldown = 0;

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState grab1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState grab2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState bellowAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();

     public boolean grabAlt = false;
     public boolean attackAlt = false;

     public int bellowCooldown = 2000 + this.getRandom().nextInt(2000);

     public Metriorhynchus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 30.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.15F)
                 .add(Attributes.ATTACK_DAMAGE, 7.0F);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.0D, 10, 4));
         this.goalSelector.addGoal(1, new AquaticLeapGoal(this, 10, 0.9D, 0.7D));
         this.goalSelector.addGoal(2, new MetriorhynchusAttackGoal(this));
         this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.METRIORHYNCHUS_FOOD), false));
         this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 20, 3));
         this.goalSelector.addGoal(4, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D, 400));
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new MetriorhynchusBellowGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.METRIORHYNCHUS_TARGETS)));
         this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttack));
     }

     @Override
     protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
         return size.height * 0.5F;
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new PrehistoricMoveControl(this);
             this.lookControl = new MetriorhynchusLookControl(this);
             this.navigation = new NoSpinGroundPathNavigation(this, this.level());
             this.isLandNavigator = true;
         } else {
             this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.98F, 0.1F, false);
             this.lookControl = new SmoothSwimmingLookControl(this, 20);
             this.navigation = new WaterBoundPathNavigation(this, this.level());
             this.isLandNavigator = false;
         }
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if (this.refuseToMove() && this.onGround()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVec = travelVec.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVec);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
             if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
             }
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.METRIORHYNCHUS_FOOD);
     }

     @Override
     public boolean isPacifyItem(ItemStack itemStack) {
         return itemStack.is(UP2ItemTags.PACIFIES_METRIORHYNCHUS);
     }

     @Override
     public boolean canPacify() {
         return true;
     }

     @Override
     public boolean checkSpawnObstruction(LevelReader level) {
         return level.isUnobstructed(this);
     }

     public boolean canPickUpTarget(LivingEntity target) {
         if (target == null) {
             return false;
         }
         if (target.getType().is(UP2EntityTags.METRIORHYNCHUS_CANT_GRAB)) {
             return false;
         }
         return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.METRIORHYNCHUS_CAN_GRAB);
     }

     @Override
     public void tick() {
         super.tick();
         if (this.isInWater() && this.isLandNavigator) {
             this.switchNavigator(false);
         }
         if (!this.isInWater() && !this.isLandNavigator) {
             this.switchNavigator(true);
         }

         if (!this.level().isClientSide) {
             if (grabCooldown > 0) grabCooldown--;
             if (attackCooldown > 0) attackCooldown--;
         }
     }

     @Override
     public void setupAnimationCooldowns() {
         if (!this.level().isClientSide && this.getTarget() == null) {
             if (bellowCooldown > 0) bellowCooldown--;
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWater(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater() && this.getPose() != UP2Poses.GRABBING.get(), this.tickCount);
         this.bellowAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
         this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
         this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
         this.grab1AnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get() && !grabAlt, this.tickCount);
         this.grab2AnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get() && grabAlt, this.tickCount);
         this.angryAnimationState.animateWhen(this.isAggressive() && this.getPose() != UP2Poses.GRABBING.get() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
         this.leapAnimationState.animateWhen(this.isLeaping() && this.getPose() != UP2Poses.GRABBING.get(), this.tickCount);
     }

     protected void bellowCooldown() {
         this.bellowCooldown = 2000 + this.getRandom().nextInt(2000);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(HELD_MOB_ID, -1);
         this.entityData.define(LEAPING, false);
     }

     @Override
     public void setHeldMobId(int id) {
         this.entityData.set(HELD_MOB_ID, id);
     }

     @Override
     public int getHeldMobId() {
         return this.entityData.get(HELD_MOB_ID);
     }

     @Override
     public void setLeaping(boolean leaping) {
         this.entityData.set(LEAPING, leaping);
     }

     @Override
     public boolean isLeaping() {
         return this.entityData.get(LEAPING);
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.METRIORHYNCHUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return UP2SoundEvents.METRIORHYNCHUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.METRIORHYNCHUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(SoundEvents.FROG_STEP, 0.3F, 0.9F);
     }

     @Override
     public int getAmbientSoundInterval() {
         return 200;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.METRIORHYNCHUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Metriorhynchus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         int i = level.getSeaLevel();
         int j = i - 13;
         return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
     }

     @Override
     public boolean requiresCustomPersistence() {
         return (this.getSpawnType() != MobSpawnType.CHUNK_GENERATION && this.getSpawnType() != MobSpawnType.NATURAL) || this.isFromEgg();
     }

     @Override
     public boolean removeWhenFarAway(double distanceToPlayer) {
         return !this.requiresCustomPersistence();
     }

     // Goals
     private static class MetriorhynchusBellowGoal extends IdleAnimationGoal {

         private final Metriorhynchus metriorhynchus;

         public MetriorhynchusBellowGoal(Metriorhynchus metriorhynchus) {
             super(metriorhynchus, 40, 1, (byte) 67, (byte) 68, false, false);
             this.metriorhynchus = metriorhynchus;
         }

         @Override
         public void start() {
             super.start();
             this.metriorhynchus.playSound(UP2SoundEvents.METRIORHYNCHUS_BELLOW.get(), 1.5F, 1.0F);
         }

         @Override
         public boolean canUse() {
             return super.canUse() && metriorhynchus.bellowCooldown == 0;
         }

         @Override
         public void stop() {
             super.stop();
             this.metriorhynchus.bellowCooldown();
         }
     }

     private static class MetriorhynchusLookControl extends PrehistoricLookControl {

         private final Metriorhynchus metriorhynchus;

         public MetriorhynchusLookControl(Metriorhynchus metriorhynchus) {
             super(metriorhynchus);
             this.metriorhynchus = metriorhynchus;
         }

         @Override
         protected boolean resetXRotOnTick() {
             return !metriorhynchus.isLeaping();
         }
     }
 }
