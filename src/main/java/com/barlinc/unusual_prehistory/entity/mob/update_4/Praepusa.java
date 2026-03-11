 package com.barlinc.unusual_prehistory.entity.mob.update_4;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Items;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.particles.BlockParticleOption;
 import net.minecraft.core.particles.ParticleTypes;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.sounds.SoundSource;
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
 import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
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

     private static final EntityDataAccessor<Integer> MITOSIS_COOLDOWN = SynchedEntityData.defineId(Praepusa.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Praepusa.class, EntityDataSerializers.BOOLEAN);

     private boolean prevOnGround = false;
     private Vec3 prevVelocity = Vec3.ZERO;

     public int attackCooldown = 0;

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState slap1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState slap2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState loafAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState applauseAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState mitosisAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState bounceAnimationState = new SmoothAnimationState();

     private int mitosisTicks;
     private int bounceTicks = 0;
     private boolean slapAlt = false;

     private int slapCooldown = 300 + this.getRandom().nextInt(300);
     private int loafCooldown = 400 + this.getRandom().nextInt(400);
     private int applauseCooldown = 900 + this.getRandom().nextInt(900);

     public Praepusa(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 10.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.16F)
                 .add(Attributes.ATTACK_DAMAGE, 3.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargePanicGoal(this, 1.6D, 10, 4, true));
         this.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.8D, entity -> entity.getType().is(UP2EntityTags.PRAEPUSA_AVOIDS)));
         this.goalSelector.addGoal(2, new PraepusaAttackGoal(this));
         this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.PRAEPUSA_FOOD), false));
         this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 50));
         this.goalSelector.addGoal(4, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 1200, 1500));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D, 1500));
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new RandomSitGoal(this));
         this.goalSelector.addGoal(8, new PraepusaSlapGoal(this));
         this.goalSelector.addGoal(8, new PraepusaLoafGoal(this));
         this.goalSelector.addGoal(8, new PraepusaApplauseGoal(this));
         this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 500, true, true, this::canHuntFish));
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new PrehistoricMoveControl(this);
             this.lookControl = new PrehistoricLookControl(this);
             this.navigation = new NoSpinGroundPathNavigation(this, this.level());
             this.isLandNavigator = true;
         } else {
             this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.4F, 1.0F, false);
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
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public float getStepHeight() {
         return this.isRunning() ? 1.0F : 0.6F;
     }

     @Override
     public boolean canPacify() {
         return true;
     }

     @Override
     public boolean isPacifyItem(ItemStack itemStack) {
         return itemStack.is(UP2ItemTags.PACIFIES_PRAEPUSA);
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.PRAEPUSA_FOOD);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2 || this.getIdleState() == 3 || this.getPose() == UP2Poses.MITOSIS.get();
     }

     @Override
     public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
         if (this.getMitosisCooldown() == 0) this.setPose(UP2Poses.MITOSIS.get());
         return super.killedEntity(level, victim);
     }

     public boolean canHuntFish(Entity entity) {
         return this.getMitosisCooldown() == 0 && entity.getType().is(UP2EntityTags.PRAEPUSA_TARGETS);
     }

     @Override
     public void tick() {
         super.tick();
         if (this.isInWater() && this.isLandNavigator) this.switchNavigator(false);
         if (!this.isInWater() && !this.isLandNavigator) this.switchNavigator(true);

         if (this.getMitosisCooldown() > 0) this.setMitosisCooldown(this.getMitosisCooldown() - 1);
         if (this.getPose() == UP2Poses.MITOSIS.get()) {
             this.setMitosisCooldown(2400 + this.getRandom().nextInt(1200));
             if (this.mitosisTicks == 7) {
                 this.performMitosis();
                 this.addDeltaMovement(this.getLookAngle().scale(2.0D).multiply(this.level().getRandom().nextFloat() * (this.level().getRandom().nextBoolean() ? -0.25F : 0.25F), 0, this.level().getRandom().nextFloat() * (this.level().getRandom().nextBoolean() ? -0.25F : 0.25F)));
                 this.level().playSound(null, this.blockPosition(), UP2SoundEvents.PRAEPUSA_MITOSIS.get(), SoundSource.NEUTRAL, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
             }
         }

         if (!this.level().isClientSide && this.isAlive() && !this.isInWaterOrBubble()) {
             this.bounce();
         }

         if (attackCooldown > 0) attackCooldown--;
     }

     @Override
     public boolean causeFallDamage(float distance, float multiplier, @NotNull DamageSource source) {
         return false;
     }

     protected void performMitosis() {
         Praepusa praepusa = UP2Entities.PRAEPUSA.get().create(this.level());
         if (praepusa != null) {
             praepusa.moveTo(this.position());
             praepusa.setPersistenceRequired();
             praepusa.setMitosisCooldown(2400 + praepusa.getRandom().nextInt(1200));
             praepusa.addDeltaMovement(praepusa.getLookAngle().scale(2.0D).multiply(praepusa.level().getRandom().nextFloat() * (praepusa.level().getRandom().nextBoolean() ? -0.25F : 0.25F), 0, praepusa.level().getRandom().nextFloat() * (praepusa.level().getRandom().nextBoolean() ? -0.25F : 0.25F)));
             this.level().addFreshEntity(praepusa);
         }
     }

     private void bounce() {
         double impactThreshold = 0.1D;
         if (this.onGround() && !prevOnGround && prevVelocity.y < -impactThreshold) {
             double impactSpeed = Math.abs(prevVelocity.y);
             double bounceFactor = 0.7D;
             double minBounceVelocity = 0.38D;
             double newYVelocity = impactSpeed * bounceFactor;
             if (newYVelocity > minBounceVelocity) {
                 this.setDeltaMovement(this.getDeltaMovement().x, newYVelocity, this.getDeltaMovement().z);
                 if (this.bounceTicks == 0) this.level().broadcastEntityEvent(this, (byte) 73);
                 this.bounceTicks = 10;
                 this.level().playSound(null, this.blockPosition(), UP2SoundEvents.PRAEPUSA_BOUNCE.get(), SoundSource.NEUTRAL, 0.3F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                 this.setOnGround(false);
                 this.hasImpulse = true;
                 BlockPos blockBelow = BlockPos.containing(this.getX(), this.getY() - 0.1, this.getZ());
                 BlockState blockState = this.level().getBlockState(blockBelow);
                 if (!blockState.isAir()) {
                     ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX(), this.getY() + 0.1, this.getZ(), 8, 0.15, 0.05, 0.15, 0.05);
                 }
             }
         }
         this.prevVelocity = this.getDeltaMovement();
         this.prevOnGround = this.onGround();
     }

     @Override
     public void setupAnimationStates() {
         if (this.mitosisAnimationState.isStarted() && mitosisTicks == 0) this.mitosisAnimationState.stop();
         this.idleAnimationState.animateWhen(!this.isInWater() && this.getIdleState() != 3 && !this.isSitting(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater(), this.tickCount);
         this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
         this.sitAnimationState.animateWhen(this.isSitting(), this.tickCount);
         this.slap1AnimationState.animateWhen(this.getIdleState() == 1 && !slapAlt, this.tickCount);
         this.slap2AnimationState.animateWhen(this.getIdleState() == 1 && slapAlt, this.tickCount);
         this.loafAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
         this.applauseAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
     }

     @Override
     public void setupAnimationCooldowns() {
         if (mitosisTicks > 0) mitosisTicks--;
         if (bounceTicks > 0) bounceTicks--;
         if (mitosisTicks == 0 && this.getPose() == UP2Poses.MITOSIS.get()) this.setPose(Pose.STANDING);
         if (slapCooldown > 0) slapCooldown--;
         if (loafCooldown > 0) loafCooldown--;
         if (applauseCooldown > 0) applauseCooldown--;
         if (bounceTicks == 0) this.level().broadcastEntityEvent(this, (byte) 74);
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.MITOSIS.get()) {
                 this.mitosisAnimationState.start(this.tickCount);
                 this.mitosisTicks = 40;
             } else if (this.getPose() == Pose.STANDING) {
                 this.mitosisAnimationState.stop();
             }
         }
         super.onSyncedDataUpdated(accessor);
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             case 73 -> this.bounceAnimationState.start(this.tickCount);
             case 74 -> this.bounceAnimationState.stop();
             default -> super.handleEntityEvent(id);
         }
     }

     protected void slapCooldown() {
         this.slapCooldown = 500 + this.getRandom().nextInt(500);
     }

     protected void loafCooldown() {
         this.loafCooldown = 700 + this.getRandom().nextInt(700);
     }

     protected void applauseCooldown() {
         this.applauseCooldown = 1000 + this.getRandom().nextInt(1000);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(MITOSIS_COOLDOWN, 0);
         this.entityData.define(FROM_BUCKET, false);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("MitosisCooldown", this.getMitosisCooldown());
         compoundTag.putBoolean("FromBucket", this.fromBucket());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setMitosisCooldown(compoundTag.getInt("MitosisCooldown"));
         this.setFromBucket(compoundTag.getBoolean("FromBucket"));
     }

     public int getMitosisCooldown() {
         return this.entityData.get(MITOSIS_COOLDOWN);
     }
     public void setMitosisCooldown(int cooldown) {
         this.entityData.set(MITOSIS_COOLDOWN, cooldown);
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
         compoundTag.putInt("MitosisCooldown", this.getMitosisCooldown());
     }

     @Override
     public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
         Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
         if (compoundTag.contains("BucketVariantTag", 3)) {
             this.setVariant(compoundTag.getInt("BucketVariantTag"));
         }
         this.setAge(compoundTag.getInt("Age"));
         this.setMitosisCooldown(compoundTag.getInt("MitosisCooldown"));
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         if (this.isFood(itemstack) && !this.isBaby() && this.getMitosisCooldown() == 0 && this.getPose() == Pose.STANDING) {
             this.feedItemToMob(player, hand, itemstack);
             this.setPose(UP2Poses.MITOSIS.get());
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         if (itemstack.isEmpty() && this.loafCooldown > 0 && this.getIdleState() == 0 && this.getLastHurtByMob() == null && !this.isInWater()) {
             this.loafCooldown = 0;
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             return InteractionResult.SUCCESS;
         }
         return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
     }

     @Override
     public @NotNull ItemStack getBucketItemStack() {
         return new ItemStack(UP2Items.PRAEPUSA_BUCKET.get());
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.PRAEPUSA_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return UP2SoundEvents.PRAEPUSA_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.PRAEPUSA_DEATH.get();
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.PRAEPUSA.get().create(level);
     }

     public static boolean canSpawn(EntityType<Praepusa> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.PRAEPUSA_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }

     // Goals
     private static class PraepusaSlapGoal extends IdleAnimationGoal {

         private final Praepusa praepusa;

         public PraepusaSlapGoal(Praepusa praepusa) {
             super(praepusa, 40, 1, (byte) 67, (byte) 68);
             this.praepusa = praepusa;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && praepusa.slapCooldown == 0;
         }

         @Override
         public void stop() {
             super.stop();
             this.praepusa.slapCooldown();
         }

         @Override
         public void start() {
             super.start();
             this.praepusa.slapAlt = praepusa.getRandom().nextBoolean();
         }
     }

     private static class PraepusaLoafGoal extends IdleAnimationGoal {

         private final Praepusa praepusa;

         public PraepusaLoafGoal(Praepusa praepusa) {
             super(praepusa, 80, 2, (byte) 69, (byte) 70);
             this.praepusa = praepusa;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && praepusa.loafCooldown == 0;
         }

         @Override
         public void stop() {
             super.stop();
             this.praepusa.loafCooldown();
         }
     }

     private static class PraepusaApplauseGoal extends IdleAnimationGoal {

         private final Praepusa praepusa;

         public PraepusaApplauseGoal(Praepusa praepusa) {
             super(praepusa, 60, 3, (byte) 71, (byte) 72);
             this.praepusa = praepusa;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && praepusa.applauseCooldown == 0 && !praepusa.isSitting();
         }

         @Override
         public void stop() {
             super.stop();
             this.praepusa.applauseCooldown();
         }
     }
 }
