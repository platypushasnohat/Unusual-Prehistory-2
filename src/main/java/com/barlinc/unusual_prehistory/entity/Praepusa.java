 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
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
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.animal.Bucketable;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 @SuppressWarnings("deprecation")
 public class Praepusa extends SemiAquaticMob implements Bucketable {

     private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Praepusa.class, EntityDataSerializers.BOOLEAN);

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimIdleAnimationState = new AnimationState();

     public Praepusa(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         this.lookControl = new SmoothSwimmingLookControl(this, 20);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 12.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.23F);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargePanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.8D, entity -> entity.getType().is(UP2EntityTags.DIPLOCAULUS_AVOIDS)));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PRAEPUSA_FOOD), false));
         this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 60));
         this.goalSelector.addGoal(4, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 1500, 800));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D, 800));
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
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
         return stack.is(UP2ItemTags.PRAEPUSA_FOOD);
     }

     @Override
     public void tick() {
         super.tick();
         final boolean ground = !this.isInWater();
         if (!ground && this.isLandNavigator) this.switchNavigator(false);
         if (ground && !this.isLandNavigator) this.switchNavigator(true);
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWater(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater(), this.tickCount);
     }

     @Override
     public void setupAnimationCooldowns() {
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove();
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(FROM_BUCKET, false);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putBoolean("FromBucket", this.fromBucket());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setFromBucket(compoundTag.getBoolean("FromBucket"));
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
         this.playSound(UP2SoundEvents.DIPLOCAULUS_STEP.get(), 0.15F, 1.0F);
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         Praepusa praepusa = UP2Entities.PRAEPUSA.get().create(level);
         praepusa.setVariant(this.getVariant());
         return praepusa;
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
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
     }

     @Override
     public @NotNull ItemStack getBucketItemStack() {
         return new ItemStack(UP2Items.DIPLOCAULUS_BUCKET.get());
     }

     public static boolean canSpawn(EntityType<Praepusa> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.PRAEPUSA_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
