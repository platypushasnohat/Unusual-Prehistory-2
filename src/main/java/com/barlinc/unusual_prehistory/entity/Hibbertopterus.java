 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
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
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Hibbertopterus extends SemiAquaticMob {

     private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.9F, 0.8F);

     private static final EntityDataAccessor<Integer> PLOW_TIME = SynchedEntityData.defineId(Hibbertopterus.class, EntityDataSerializers.INT);

     private boolean plowing;

     public Hibbertopterus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 40.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.15F)
                 .add(Attributes.ARMOR, 10.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.HIBBERTOPTERUS_FOOD), false));
         this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1.0D, false));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
     }

     @Override
     public @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new SmoothGroundPathNavigation(this, level);
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
             if (this.jumping) {
                 this.setDeltaMovement(this.getDeltaMovement().scale(1.0D));
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.42D, 0.0D));
             } else {
                 this.setDeltaMovement(this.getDeltaMovement().scale(0.4D));
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.05D, 0.0D));
             }
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public float getStepHeight() {
         return 1.1F;
     }

     @Override
     public boolean canCollideWith(@NotNull Entity entity) {
         return super.canCollideWith(entity) && !(entity instanceof Hibbertopterus);
     }

     @Override
     public boolean canBeCollidedWith() {
         return true;
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.HIBBERTOPTERUS_FOOD);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove();
     }

     @Override
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return (pose == UP2Poses.SITTING.get() || pose == UP2Poses.SLEEPING.get()) ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
     }

     @Nullable
     @Override
     public LivingEntity getControllingPassenger() {
         Entity entity = this.getFirstPassenger();
         if (entity instanceof Player player) {
             if (player.getMainHandItem().is(Items.CARROT_ON_A_STICK) || player.getOffhandItem().is(Items.CARROT_ON_A_STICK)) {
                 return player;
             }
         }
         return null;
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         boolean flag = this.isFood(player.getItemInHand(hand));
         if (!flag && !this.isBaby() && !this.isVehicle() && !player.isSecondaryUseActive() && !player.isShiftKeyDown()) {
             if (!this.level().isClientSide) {
                 player.startRiding(this);
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         if (itemstack.is(Items.CARROT_ON_A_STICK) && player.isPassenger() && player.getControlledVehicle() == this) {

             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         else {
             return super.mobInteract(player, hand);
         }
     }

     protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
         return new Vec3(0.0D, 0.0D, 1.0D);
     }

     @Override
     protected float getRiddenSpeed(@NotNull Player player) {
         return (float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.25D * this.boostFactor());
     }

     public float boostFactor() {
         return this.plowing ? 1.5F : 1.0F;
     }

     @Override
     public Vec3 getRiderOffset() {
         return new Vec3(0.0F, 0.0F, -0.5F);
     }

     public boolean startPlowing(ItemStack stack) {
         if (this.getControllingPassenger() != null && !plowing) {
             this.heal(2);
             return true;
         }
         return false;
     }

     @Override
     public void tick() {
         super.tick();
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(this.getIdleState() != 3 && !this.isInSitPoseTransition() && !this.isInEepyPoseTransition(), this.tickCount);
     }

     @Override
     public void setupAnimationCooldowns() {
     }

     @Override
     public float getWalkAnimationSpeed() {
         return this.isBaby() ? 6.0F : 10.0F;
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             default -> super.handleEntityEvent(id);
         }
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         super.onSyncedDataUpdated(accessor);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(PLOW_TIME, 0);
     }

     public void setPlowTime(int plowTime) {
         this.entityData.set(PLOW_TIME, plowTime);
     }

     public int getPlowTime() {
         return this.entityData.get(PLOW_TIME);
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
         return UP2Entities.HIBBERTOPTERUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Hibbertopterus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.HIBBERTOPTERUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
