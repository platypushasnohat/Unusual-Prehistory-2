 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Items;
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
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.animal.Bucketable;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 @SuppressWarnings("deprecation")
 public class Diplocaulus extends SemiAquaticMob implements Bucketable {

     public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.INT);
     private static final EntityDimensions BURROWED_DIMENSIONS = EntityDimensions.scalable(0.6F, 0.25F);
     private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.BOOLEAN);

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimIdleAnimationState = new AnimationState();
     public final AnimationState burrowStartAnimationState = new AnimationState();
     public final AnimationState burrowIdleAnimationState = new AnimationState();
     public final AnimationState quirkAnimationState = new AnimationState();

     private final byte QUIRK = 67;

     public Diplocaulus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         this.lookControl = new SmoothSwimmingLookControl(this, 20);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 10.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.18F);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargePanicGoal(this, 2.0D, 10, 4));
         this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.DIPLOCAULUS_AVOIDS)));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIPLOCAULUS_FOOD), false));
         this.goalSelector.addGoal(3, new CustomizableRandomSwimGoal(this, 1.0D, 80));
         this.goalSelector.addGoal(3, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(4, new LeaveWaterGoal(this, 1.0D, 1500, 800));
         this.goalSelector.addGoal(4, new EnterWaterGoal(this, 1.0D, 800));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(6, new DiplocaulusBurrowInMudGoal(this));
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new MoveControl(this);
             this.isLandNavigator = true;
         } else {
             this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.34F, 1.0F, false);
             this.isLandNavigator = false;
         }
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if (this.refuseToMove() && this.onGround()) {
             this.getNavigation().stop();
             travelVec = Vec3.ZERO;
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
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIPLOCAULUS_FOOD);
     }

     @Override
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return (pose == UP2Poses.BURROWED.get() ? BURROWED_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
     }

     @Override
     public void tick() {
         super.tick();
         final boolean ground = !this.isInWater();
         if (!ground && this.isLandNavigator) {
             switchNavigator(false);
         }
         if (ground && !this.isLandNavigator) {
             switchNavigator(true);
         }
         if (this.isDiplocaulusBurrowed() && this.isInWaterOrBubble()) {
             this.exitBurrow();
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWater(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater(), this.tickCount);
         if (this.isDiplocaulusVisuallyBurrowed()) {
             this.quirkAnimationState.stop();
             this.idleAnimationState.stop();
             if (this.isVisuallyBurrowed()) {
                 this.burrowStartAnimationState.startIfStopped(this.tickCount);
                 this.burrowIdleAnimationState.stop();
             } else {
                 this.burrowStartAnimationState.stop();
                 this.burrowIdleAnimationState.startIfStopped(this.tickCount);
             }
         } else {
             this.burrowStartAnimationState.stop();
             this.burrowIdleAnimationState.stop();
         }
     }

     @Override
     public void setupAnimationCooldowns() {
         if (this.getBehavior().equals(Behaviors.IDLE.getName())) {
             if (this.getBurrowCooldown() > 0) this.setBurrowCooldown(this.getBurrowCooldown() - 1);
             if (this.random.nextInt(650) == 0 && !this.quirkAnimationState.isStarted()) this.level().broadcastEntityEvent(this, this.QUIRK);
         }
     }

     @Override
     public void handleEntityEvent(byte id) {
         if (id == this.QUIRK) this.quirkAnimationState.start(this.tickCount);
         else super.handleEntityEvent(id);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.isDiplocaulusBurrowed();
     }

     @Override
     protected void onLeashDistance(float distance) {
         if (distance > 4.0F && this.isDiplocaulusBurrowed() && !this.isInPoseTransition()) {
             this.exitBurrow();
         }
     }

     @Override
     protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
         this.exitBurrow();
         super.actuallyHurt(damageSource, amount);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(BURROW_COOLDOWN, 40 * 40 + getRandom().nextInt(60 * 2 * 20));
         this.entityData.define(FROM_BUCKET, false);
     }

     @Override
     public void addAdditionalSaveData(CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("BurrowCooldown", this.getBurrowCooldown());
         compoundTag.putBoolean("FromBucket", this.fromBucket());
     }

     @Override
     public void readAdditionalSaveData(CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setBurrowCooldown(compoundTag.getInt("BurrowCooldown"));
         this.setFromBucket(compoundTag.getBoolean("FromBucket"));
     }

     public int getBurrowCooldown() {
         return this.entityData.get(BURROW_COOLDOWN);
     }

     public void setBurrowCooldown(int cooldown) {
         this.entityData.set(BURROW_COOLDOWN, cooldown);
     }

     public void burrowCooldown() {
         this.entityData.set(BURROW_COOLDOWN, 40 * 40 + random.nextInt(60 * 2 * 20));
     }

     public void exitBurrowCooldown() {
         this.entityData.set(BURROW_COOLDOWN, 20 * 40 + random.nextInt(50 * 2 * 20));
     }

     public boolean isDiplocaulusBurrowed() {
         return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
     }

     public boolean isDiplocaulusVisuallyBurrowed() {
         return this.getPoseTime() < 0L != this.isDiplocaulusBurrowed();
     }

     public boolean isInPoseTransition() {
         long l = this.getPoseTime();
         return l < (long) (40);
     }

     private boolean isVisuallyBurrowed() {
         return this.isDiplocaulusBurrowed() && this.getPoseTime() < 40L && this.getPoseTime() >= 0L;
     }

     public void burrow() {
         if (this.isDiplocaulusBurrowed()) return;
         this.setPose(UP2Poses.BURROWED.get());
         this.resetLastPoseChangeTick(-(this.level()).getGameTime());
         this.refreshDimensions();
     }

     public void exitBurrow() {
         this.setPose(Pose.STANDING);
         this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
         this.refreshDimensions();
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.DIPLOCAULUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return UP2SoundEvents.DIPLOCAULUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.DIPLOCAULUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(SoundEvents.FROG_STEP, 0.1F, 1.5F);
     }

     @Override
     public float getSoundVolume() {
         return 0.7F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         Diplocaulus entity = UP2Entities.DIPLOCAULUS.get().create(level);
         entity.setVariant(this.getVariant());
         return entity;
     }

     @Override
     public boolean fromBucket() {
         return this.entityData.get(FROM_BUCKET);
     }

     @Override
     public void setFromBucket(boolean fromBucket) {
         this.entityData.set(FROM_BUCKET, fromBucket);
     }

     @Override
     public @NotNull SoundEvent getPickupSound() {
         return SoundEvents.BUCKET_FILL_FISH;
     }

     @Override
     public void saveToBucketTag(@NotNull ItemStack bucket) {
         if (this.hasCustomName()) {
             bucket.setHoverName(this.getCustomName());
         }
         Bucketable.saveDefaultDataToBucketTag(this, bucket);
         CompoundTag compoundTag = bucket.getOrCreateTag();
         compoundTag.putInt("BucketVariantTag", this.getVariant());
         compoundTag.putInt("Age", this.getAge());
     }

     @Override
     public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
         Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
         if (compoundTag.contains("BucketVariantTag", 3)) {
             this.setVariant(compoundTag.getInt("BucketVariantTag"));
         }
         this.setAge(compoundTag.getInt("Age"));
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
         return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
     }

     @Override
     public @NotNull ItemStack getBucketItemStack() {
         return new ItemStack(UP2Items.DIPLOCAULUS_BUCKET.get());
     }


     public enum DiplocaulusVariant {
         BREVIROSTRIS(0),
         MAGNICORNIS(1),
         RECURVATIS(2),
         SALAMANDROIDES(3);

         private final int id;

         DiplocaulusVariant(int id) {
             this.id = id;
         }

         public int getId() {
             return this.id;
         }

         public static DiplocaulusVariant byId(int id) {
             if (id < 0 || id >= DiplocaulusVariant.values().length) {
                 id = 0;
             }
             return DiplocaulusVariant.values()[id];
         }
     }

     @Override
     public int getVariantCount() {
         return DiplocaulusVariant.values().length;
     }

     @Override
     public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
         spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
         if (spawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains("BucketVariantTag", 3)) {
             this.setVariant(compoundTag.getInt("BucketVariantTag"));
         } else {
             this.setVariant(random.nextInt(DiplocaulusVariant.values().length));
         }
         return spawnGroupData;
     }

     public static boolean canSpawn(EntityType<Diplocaulus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.DIPLOCAULUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
