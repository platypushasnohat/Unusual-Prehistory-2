 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SemiAquaticSwimmingMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
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
 import net.minecraft.world.entity.ai.control.LookControl;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.Blocks;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 @SuppressWarnings("deprecation")
 public class Metriorhynchus extends SemiAquaticMob {

     private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Metriorhynchus.class, EntityDataSerializers.INT);

     public int deathRollCooldown = 0;
     public int biteCooldown = 0;

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimIdleAnimationState = new AnimationState();
     public final AnimationState bite1AnimationState = new AnimationState();
     public final AnimationState bite2AnimationState = new AnimationState();
     public final AnimationState deathRoll1AnimationState = new AnimationState();
     public final AnimationState deathRoll2AnimationState = new AnimationState();
     public final AnimationState bellowAnimationState = new AnimationState();

     private int deathRollTicks;
     private int biteTicks;

     private final byte BELLOW = 60;

     public Metriorhynchus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 36.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.14F)
                 .add(Attributes.ATTACK_DAMAGE, 6.0F);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 2.0D));
         this.goalSelector.addGoal(1, new MetriorhynchusAttackGoal(this));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.METRIORHYNCHUS_FOOD), false));
         this.goalSelector.addGoal(3, new CustomizableRandomSwimGoal(this, 1.0D, 30, 16, 16, 3));
         this.goalSelector.addGoal(3, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.METRIORHYNCHUS_TARGETS)));
     }

     @Override
     protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
         return size.height * 0.5F;
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new MoveControl(this);
             this.navigation = new SmoothGroundPathNavigation(this, level());
             this.lookControl = new LookControl(this);
             this.isLandNavigator = true;
         } else {
             this.moveControl = new SemiAquaticSwimmingMoveControl(this, 85, 10, 0.98F);
             this.navigation = new AmphibiousPathNavigation(this, level());
             this.lookControl = new SmoothSwimmingLookControl(this, 10);
             this.isLandNavigator = false;
         }
     }

     @Override
     public void travel(@NotNull Vec3 travelVector) {
         if (this.refuseToMove() && this.onGround()) {
             this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
             travelVector = travelVector.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVector);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
             if (this.horizontalCollision) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.3 * this.getSpeed(), 0.0));
             }
         } else {
             super.travel(travelVector);
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
     public void tick() {
         super.tick();
         final boolean ground = !this.isInWater();
         if (!ground && this.isLandNavigator) {
             switchNavigator(false);
         }
         if (ground && !this.isLandNavigator) {
             switchNavigator(true);
         }

         if (this.getPose() == UP2Poses.DEATH_ROLL.get() && this.getHeldMobId() != -1) {
             this.positionHeldMob();
         }

         if (deathRollCooldown > 0) deathRollCooldown--;
         if (biteCooldown > 0) biteCooldown--;
     }

     private void positionHeldMob() {
         Entity entity = level().getEntity(this.getHeldMobId());
         if (entity != null) {
             if (deathRollTicks < 35) {
                 Vec3 heldPos = this.getEyePosition().add(new Vec3(0.0F, 0.0F, 2.2F).yRot(-yBodyRot * ((float) Math.PI / 180F)));
                 Vec3 minus = new Vec3(heldPos.x - entity.getX(), heldPos.y - entity.getY(), heldPos.z - entity.getZ());
                 entity.setDeltaMovement(minus);
                 entity.fallDistance = 0.0F;
                 entity.setYRot(0.0F);
                 entity.setYBodyRot(0.0F);
                 entity.setYHeadRot(0.0F);
                 entity.setXRot(0.0F);
                 entity.setDeltaMovement(minus);
                 if (deathRollTicks % 10 == 0) {
                     entity.hurt(damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F);
                 }
             } else {
                 entity.setDeltaMovement(entity.getDeltaMovement().scale(0.6F));
             }
         }
     }

     @Override
     public void setupAnimationCooldowns() {
         if (this.biteTicks > 0) biteTicks--;
         if (this.deathRollTicks > 0) deathRollTicks--;
         if (this.biteTicks == 0 && this.getPose() == UP2Poses.BITING.get()) this.setPose(Pose.STANDING);
         if (this.deathRollTicks == 0 && this.getPose() == UP2Poses.DEATH_ROLL.get()) this.setPose(Pose.STANDING);
         if (this.getBehavior().equals(Behaviors.IDLE.getName()) && !this.isAggressive() && !this.isRunning()) {
             if (this.getPose() == Pose.STANDING) {
                 if (this.random.nextInt(1200) == 0) {
                     this.level().broadcastEntityEvent(this, this.BELLOW);
                 }
             }
         }
     }

     @Override
     public void setupAnimationStates() {
         if (biteTicks == 0 && (this.bite1AnimationState.isStarted() || this.bite2AnimationState.isStarted())) {
             this.bite1AnimationState.stop();
             this.bite2AnimationState.stop();
         }
         if (deathRollTicks == 0 && (this.deathRoll1AnimationState.isStarted() || this.deathRoll2AnimationState.isStarted())) {
             this.deathRoll1AnimationState.stop();
             this.deathRoll2AnimationState.stop();
         }
         this.idleAnimationState.animateWhen(!this.isInWater(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater() && this.getPose() != UP2Poses.DEATH_ROLL.get(), this.tickCount);
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.BITING.get()) {
                 if (this.getRandom().nextBoolean()) this.bite1AnimationState.start(this.tickCount);
                 else this.bite2AnimationState.start(this.tickCount);
                 this.biteTicks = 10;
             }
             else if (this.getPose() == UP2Poses.DEATH_ROLL.get()) {
                 if (this.getRandom().nextBoolean()) this.deathRoll1AnimationState.start(this.tickCount);
                 else this.deathRoll2AnimationState.start(this.tickCount);
                 this.deathRollTicks = 40;
             }
             else {
                 this.bite1AnimationState.stop();
                 this.bite2AnimationState.stop();
                 this.deathRoll1AnimationState.stop();
                 this.deathRoll2AnimationState.stop();
             }
         }
         super.onSyncedDataUpdated(accessor);
     }

     @Override
     public void handleEntityEvent(byte id) {
         if (id == this.BELLOW) this.bellowAnimationState.start(this.tickCount);
         else super.handleEntityEvent(id);
     }

     public Entity getHeldMob() {
         int id = this.getHeldMobId();
         return id == -1 ? null : level().getEntity(id);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(HELD_MOB_ID, -1);
     }

     public void setHeldMobId(int id) {
         this.entityData.set(HELD_MOB_ID, id);
     }

     public int getHeldMobId() {
         return this.entityData.get(HELD_MOB_ID);
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
 }
