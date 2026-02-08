 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousPathNavigation;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
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
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Kaprosuchus extends SemiAquaticMob implements LeapingMob {

     private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Kaprosuchus.class, EntityDataSerializers.INT);

     private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Kaprosuchus.class, EntityDataSerializers.BOOLEAN);

     private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.9F, 0.8F);

     public int leapCooldown = 70 + this.getRandom().nextInt(80);
     public int attackCooldown = 0;

     public final AnimationState swimIdleAnimationState = new AnimationState();
     public final AnimationState attack1AnimationState = new AnimationState();
     public final AnimationState attack2AnimationState = new AnimationState();
     public final AnimationState bash1AnimationState = new AnimationState();
     public final AnimationState bash2AnimationState = new AnimationState();
     public final AnimationState leapAnimationState = new AnimationState();

     private int attackTicks;
     private int bashTicks;

     public Kaprosuchus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 20.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2F)
                 .add(Attributes.ARMOR, 4.0D)
                 .add(Attributes.ATTACK_DAMAGE, 6.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this));
         this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(2, new KaprosuchusAttackGoal(this));
         // todo: fix this not working properly
         this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KAPROSUCHUS_FOOD), false));
         this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 50));
         this.goalSelector.addGoal(5, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(6, new LeaveWaterGoal(this, 1.0D, 1200, 1500));
         this.goalSelector.addGoal(6, new EnterWaterGoal(this, 1.0D, 1500));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(8, new SleepingGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.KAPROSUCHUS_TARGETS)));
         this.targetSelector.addGoal(2, new PrehistoricOwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(3, new PrehistoricOwnerHurtTargetGoal(this));
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.lookControl = new KaprosuchusLookControl(this);
             this.moveControl = new PrehistoricMoveControl(this);
             this.navigation = new SmoothGroundPathNavigation(this, this.level());
             this.isLandNavigator = true;
         } else {
             this.lookControl = new SmoothSwimmingLookControl(this, 20);
             this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.4F, 1.0F, false);
             this.navigation = new SmoothAmphibiousPathNavigation(this, this.level());
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
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public float getStepHeight() {
         return this.isRunning() ? 1.0F : 0.6F;
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.KAPROSUCHUS_FOOD);
     }

     @Override
     public boolean canPacify() {
         return true;
     }

     @Override
     public boolean isPacifyItem(ItemStack itemStack) {
         return itemStack.is(UP2ItemTags.PACIFIES_KAPROSUCHUS);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove();
     }

     @Override
     public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
         return false;
     }

     @Override
     protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
     }

     public void leapCooldown() {
         this.leapCooldown = 70 + this.getRandom().nextInt(80);
     }

     @Override
     public boolean canOwnerCommand(Player player) {
         return true;
     }

     @Override
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return (pose == UP2Poses.SITTING.get() || pose == UP2Poses.SLEEPING.get()) ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);

         if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_KAPROSUCHUS)) {
             this.gameEvent(GameEvent.ENTITY_INTERACT);
             if(!this.level().isClientSide) {
                 if (!player.getAbilities().instabuild) {
                     itemstack.shrink(1);
                 }
                 if (this.getTameAttempts() > 2 && this.getRandom().nextBoolean()) {
                     this.level().broadcastEntityEvent(this, (byte) 7);
                     this.tame(player);
                     this.setPacified(true);
                     this.heal(this.getMaxHealth());
                 } else {
                     this.level().broadcastEntityEvent(this, (byte) 6);
                     this.setTameAttempts(this.getTameAttempts() + 1);
                 }
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         return type;
     }

     @Override
     public void tick() {
         super.tick();
         final boolean ground = !this.isInWater();
         if (!ground && this.isLandNavigator) this.switchNavigator(false);
         if (ground && !this.isLandNavigator) this.switchNavigator(true);
         if (leapCooldown > 0) leapCooldown--;
         if (attackCooldown > 0) attackCooldown--;
     }

     @Override
     public void setupAnimationStates() {
         if ((this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted()) && attackTicks == 0) {
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
         }
         if ((this.bash1AnimationState.isStarted() || this.bash2AnimationState.isStarted()) && bashTicks == 0) {
             this.bash1AnimationState.stop();
             this.bash2AnimationState.stop();
         }
         this.idleAnimationState.animateWhen(!this.isInWater() && this.getIdleState() != 3 && !this.isLeaping() && !this.isInSitPoseTransition() && !this.isInEepyPoseTransition(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater(), this.tickCount);
         this.leapAnimationState.animateWhen(this.isLeaping(), this.tickCount);

         if (this.isMobVisuallySitting()) {
             this.sitEndAnimationState.stop();
             this.swimIdleAnimationState.stop();
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
             this.idleAnimationState.stop();
             this.bash1AnimationState.stop();
             this.bash2AnimationState.stop();
             this.leapAnimationState.stop();
             this.sleepStartAnimationState.stop();
             this.sleepAnimationState.stop();
             this.sleepEndAnimationState.stop();

             if (this.isVisuallySitting()) {
                 this.sitStartAnimationState.startIfStopped(this.tickCount);
                 this.sitAnimationState.stop();
             } else {
                 this.sitStartAnimationState.stop();
                 this.sitAnimationState.startIfStopped(this.tickCount);
             }
         } else {
             this.sitStartAnimationState.stop();
             this.sitAnimationState.stop();
             this.sitEndAnimationState.animateWhen(this.isInSitPoseTransition() && this.getSitPoseTime() >= 0L, this.tickCount);
         }

         if (this.isMobVisuallyEepy()) {
             this.sleepEndAnimationState.stop();
             this.swimIdleAnimationState.stop();
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
             this.idleAnimationState.stop();
             this.bash1AnimationState.stop();
             this.bash2AnimationState.stop();
             this.leapAnimationState.stop();
             this.sitStartAnimationState.stop();
             this.sitAnimationState.stop();
             this.sitEndAnimationState.stop();

             if (this.isVisuallyEepy()) {
                 this.sleepStartAnimationState.startIfStopped(this.tickCount);
                 this.sleepAnimationState.stop();
             } else {
                 this.sleepStartAnimationState.stop();
                 this.sleepAnimationState.startIfStopped(this.tickCount);
             }
         } else {
             this.sleepStartAnimationState.stop();
             this.sleepAnimationState.stop();
             this.sleepEndAnimationState.animateWhen(this.isInEepyPoseTransition() && this.getEepyPoseTime() >= 0L, this.tickCount);
         }
     }

     @Override
     public void setupAnimationCooldowns() {
         if (attackTicks > 0) attackTicks--;
         if (bashTicks > 0) bashTicks--;
         if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
         if (bashTicks == 0 && this.getPose() == UP2Poses.HEADBUTTING.get()) this.setPose(Pose.STANDING);
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.ATTACKING.get()) {
                 if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                 else this.attack2AnimationState.start(this.tickCount);
                 this.attackTicks = 10;
             }
             if (this.getPose() == UP2Poses.HEADBUTTING.get()) {
                 if (this.getRandom().nextBoolean()) this.bash1AnimationState.start(this.tickCount);
                 else this.bash2AnimationState.start(this.tickCount);
                 this.bashTicks = 15;
             }
             else if (this.getPose() == Pose.STANDING) {
                 this.attack1AnimationState.stop();
                 this.attack2AnimationState.stop();
                 this.bash1AnimationState.stop();
                 this.bash2AnimationState.stop();
             }
         }
         super.onSyncedDataUpdated(accessor);
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             default -> super.handleEntityEvent(id);
         }
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(TAME_ATTEMPTS, 0);
         this.entityData.define(LEAPING, false);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("TameAttempts", this.getTameAttempts());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setTameAttempts(compoundTag.getInt("TameAttempts"));
     }

     public void setTameAttempts(int tameAttempts) {
         this.entityData.set(TAME_ATTEMPTS, tameAttempts);
     }

     public int getTameAttempts() {
         return this.entityData.get(TAME_ATTEMPTS);
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
         return UP2SoundEvents.KAPROSUCHUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.KAPROSUCHUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.KAPROSUCHUS_DEATH.get();
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.KAPROSUCHUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Kaprosuchus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.KAPROSUCHUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }

     private static class KaprosuchusLookControl extends PrehistoricLookControl {

         private final Kaprosuchus kaprosuchus;

         public KaprosuchusLookControl(Kaprosuchus kaprosuchus) {
             super(kaprosuchus);
             this.kaprosuchus = kaprosuchus;
         }

         @Override
         protected boolean resetXRotOnTick() {
             return !kaprosuchus.isLeaping();
         }
     }
 }
