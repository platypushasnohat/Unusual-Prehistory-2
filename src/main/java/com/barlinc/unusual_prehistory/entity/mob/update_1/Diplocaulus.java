 package com.barlinc.unusual_prehistory.entity.mob.update_1;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.SemiAquaticMob;
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
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.Goal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
 import net.minecraft.world.entity.ai.util.DefaultRandomPos;
 import net.minecraft.world.entity.animal.Bucketable;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.LevelReader;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 import java.util.EnumSet;

 @SuppressWarnings("deprecation")
 public class Diplocaulus extends SemiAquaticMob implements Bucketable {

     public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> SLIDING = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> BURROWED = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.BOOLEAN);

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState burrowAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState quirkAnimationState = new SmoothAnimationState();

     private int quirkCooldown = 600 + this.getRandom().nextInt(60 * 60);

     public Diplocaulus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 10.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2F);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargePanicGoal(this, 1.6D, 10, 4, true));
         this.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 10.0F, 1.6D, true, entity -> entity.getType().is(UP2EntityTags.DIPLOCAULUS_AVOIDS)));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIPLOCAULUS_FOOD), false));
         this.goalSelector.addGoal(3, new DiplocaulusSlideGoal(this, 2.0D));
         this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 80));
         this.goalSelector.addGoal(4, new SemiAquaticRandomStrollGoal(this, 1.0D) {
             @Override
             public boolean canUse() {
                 return super.canUse() && !Diplocaulus.this.isSliding();
             }
         });
         this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 1500, 800));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D, 800));
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new DiplocaulusBurrowGoal(this));
         this.goalSelector.addGoal(8, new DiplocaulusQuirkGoal(this));
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new PrehistoricMoveControl(this);
             this.lookControl = new PrehistoricLookControl(this);
             this.navigation = new NoSpinGroundPathNavigation(this, this.level(), 0.2F);
             this.isLandNavigator = true;
         } else {
             this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.34F, 1.0F, false);
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
     public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
         if (!this.isInWaterOrBubble() && level.getBlockState(pos.below()).is(UP2BlockTags.DIPLOCAULUS_PREFERRED_WALKING_BLOCKS)) return 10.0F;
         return super.getWalkTargetValue(pos, level);
     }

     @Override
     public float getStepHeight() {
         return this.isSliding() || this.isRunning() ? 1.0F : 0.6F;
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIPLOCAULUS_FOOD);
     }

     @Override
     public boolean isPushable() {
         return !this.isBurrowed();
     }

     @Override
     public void doPush(@NotNull Entity entity) {
         if (!this.isBurrowed()) super.doPush(entity);
     }

     @Override
     public void tick() {
         super.tick();
         if (this.isInWater() && this.isLandNavigator) this.switchNavigator(false);
         if (!this.isInWater() && !this.isLandNavigator) this.switchNavigator(true);

         if (this.isBurrowed() && this.isInWaterOrBubble() || !this.onGround()) this.setBurrowed(false);

         if (this.isSliding() && !this.isInWaterOrBubble() && this.getDeltaMovement().horizontalDistance() > 0.05D) {
             for (int i = 0; i < 1; i++) {
                 double velocityX = this.random.nextGaussian() * 0.15D;
                 double velocityY = this.random.nextGaussian() * 0.15D;
                 double velocityZ = this.random.nextGaussian() * 0.15D;
                 this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1), this.getRandomY() - 0.5D, this.getRandomZ(1) - 0.75D, velocityX, velocityY, velocityZ);
             }
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWater() && !this.isBurrowed(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater() && !this.isBurrowed(), this.tickCount);
         this.burrowAnimationState.animateWhen(this.isBurrowed(), this.tickCount);
         this.quirkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
     }

     @Override
     public void tickCooldowns() {
         super.tickCooldowns();
         if (this.getBurrowCooldown() > 0) this.setBurrowCooldown(this.getBurrowCooldown() - 1);
         if (quirkCooldown > 0) quirkCooldown--;
     }

     protected void quirkCooldown() {
         this.quirkCooldown = 600 + this.getRandom().nextInt(60 * 60);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.isBurrowed();
     }

     @Override
     protected void onLeashDistance(float distance) {
         if (distance > 5.0F && this.isBurrowed()) this.setBurrowed(false);
     }

     @Override
     protected void actuallyHurt(@NotNull DamageSource source, float amount) {
         this.setBurrowed(false);
         super.actuallyHurt(source, amount);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(BURROW_COOLDOWN, 800 + this.getRandom().nextInt(800));
         this.entityData.define(SLIDING, false);
         this.entityData.define(FROM_BUCKET, false);
         this.entityData.define(BURROWED, false);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("BurrowCooldown", this.getBurrowCooldown());
         compoundTag.putBoolean("FromBucket", this.fromBucket());
         compoundTag.putBoolean("Burrowed", this.isBurrowed());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setBurrowCooldown(compoundTag.getInt("BurrowCooldown"));
         this.setFromBucket(compoundTag.getBoolean("FromBucket"));
         this.setBurrowed(compoundTag.getBoolean("Burrowed"));
     }

     public boolean isBurrowed() {
         return this.entityData.get(BURROWED);
     }

     public void setBurrowed(boolean burrowed) {
         this.entityData.set(BURROWED, burrowed);
     }

     public int getBurrowCooldown() {
         return this.entityData.get(BURROW_COOLDOWN);
     }

     public void setBurrowCooldown(int cooldown) {
         this.entityData.set(BURROW_COOLDOWN, cooldown);
     }

     public void burrowCooldown() {
         this.entityData.set(BURROW_COOLDOWN, 800 + this.getRandom().nextInt(800));
     }

     public boolean isSliding() {
         return this.entityData.get(SLIDING);
     }

     public void setSliding(boolean sliding) {
         this.entityData.set(SLIDING, sliding);
     }

     protected boolean isSlideableBlockBelow() {
         return this.level().getBlockState(this.blockPosition().below()).is(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS) || this.level().getBlockState(this.blockPosition()).is(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS);
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
         if (this.isSliding()) super.playStepSound(pos, state);
         else this.playSound(UP2SoundEvents.DIPLOCAULUS_STEP.get(), 0.15F, 1.0F);
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         Diplocaulus diplocaulus = UP2Entities.DIPLOCAULUS.get().create(level);
         if (diplocaulus != null) {
             diplocaulus.setVariant(this.getVariant());
         }
         return diplocaulus;
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

     // goals
     private static class DiplocaulusSlideGoal extends Goal {

         protected final Diplocaulus diplocaulus;
         protected double wantedX;
         protected double wantedY;
         protected double wantedZ;
         protected final double speedModifier;

         public DiplocaulusSlideGoal(Diplocaulus diplocaulus, double speedModifier) {
             this.diplocaulus = diplocaulus;
             this.speedModifier = speedModifier;
             this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
         }

         @Override
         public boolean canUse() {
             if (diplocaulus.isVehicle() || diplocaulus.isPathFinding() || diplocaulus.isInWaterOrBubble() && !diplocaulus.isSlideableBlockBelow()) {
                 return false;
             } else {
                 if (diplocaulus.getNoActionTime() >= 100) return false;
                 if (diplocaulus.getRandom().nextInt(reducedTickDelay(120)) != 0) return false;
                 Vec3 vec3 = this.getPosition();
                 if (vec3 == null) return false;
                 else {
                     this.wantedX = vec3.x;
                     this.wantedY = vec3.y;
                     this.wantedZ = vec3.z;
                     return true;
                 }
             }
         }

         @Override
         public void tick() {
             this.diplocaulus.getLookControl().setLookAt(wantedX, wantedY, wantedZ, 30F, 30F);
         }

         @Nullable
         protected Vec3 getPosition() {
             return DefaultRandomPos.getPos(diplocaulus, 20, 1);
         }

         @Override
         public boolean canContinueToUse() {
             return !diplocaulus.getNavigation().isDone() && !diplocaulus.isVehicle() && !diplocaulus.isInWaterOrBubble() && diplocaulus.isSlideableBlockBelow();
         }

         @Override
         public void start() {
             this.diplocaulus.setSliding(true);
             this.diplocaulus.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
         }

         @Override
         public void stop() {
             this.diplocaulus.setSliding(false);
             this.diplocaulus.getNavigation().stop();
         }
     }

     private static class DiplocaulusBurrowGoal extends Goal {

         private final Diplocaulus diplocaulus;

         public DiplocaulusBurrowGoal(Diplocaulus diplocaulus) {
             this.diplocaulus = diplocaulus;
         }

         @Override
         public boolean canUse() {
             return !diplocaulus.isInWater() && diplocaulus.getBurrowCooldown() == 0 && !diplocaulus.isLeashed() && diplocaulus.onGround() && diplocaulus.getLastHurtByMob() == null && this.isBurrowBlock();
         }

         @Override
         public boolean canContinueToUse() {
             return !diplocaulus.isInWater() && !diplocaulus.isLeashed() && diplocaulus.onGround();
         }

         @Override
         public void start() {
             if (diplocaulus.isBurrowed()) {
                 this.diplocaulus.burrowCooldown();
                 this.diplocaulus.setBurrowed(false);
             } else {
                 this.diplocaulus.burrowCooldown();
                 this.diplocaulus.setBurrowed(true);
             }
         }

         protected boolean isBurrowBlock() {
             return diplocaulus.level().getBlockState(diplocaulus.blockPosition()).is(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS) || diplocaulus.level().getBlockState(diplocaulus.blockPosition().below()).is(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS);
         }
     }

     private static class DiplocaulusQuirkGoal extends IdleAnimationGoal {

         private final Diplocaulus diplocaulus;

         public DiplocaulusQuirkGoal(Diplocaulus diplocaulus) {
             super(diplocaulus, 60, 1, (byte) 67, (byte) 68, false, false);
             this.diplocaulus = diplocaulus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && diplocaulus.quirkCooldown == 0 && !diplocaulus.isBurrowed();
         }

         @Override
         public boolean canContinueToUse() {
             return super.canContinueToUse() && !diplocaulus.isBurrowed();
         }

         @Override
         public void stop() {
             super.stop();
             this.diplocaulus.quirkCooldown();
         }
     }
 }
