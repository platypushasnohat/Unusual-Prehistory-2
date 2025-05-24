 package com.unusualmodding.unusual_prehistory.entity;

 import com.unusualmodding.unusual_prehistory.entity.ai.goal.LargePanicGoal;
 import com.unusualmodding.unusual_prehistory.entity.base.AncientEntity;
 import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
 import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.LookControl;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.Goal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
 import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
 import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
 import net.minecraft.world.entity.ai.util.DefaultRandomPos;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class DiplocaulusEntity extends AncientEntity {

     private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DiplocaulusEntity.class, EntityDataSerializers.INT);
     public static final EntityDataAccessor<Integer> QUIRK_COOLDOWN = SynchedEntityData.defineId(DiplocaulusEntity.class, EntityDataSerializers.INT);
     public static final EntityDataAccessor<Integer> QUIRK_TIMER = SynchedEntityData.defineId(DiplocaulusEntity.class, EntityDataSerializers.INT);

     public boolean isLandNavigator;

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimIdleAnimationState = new AnimationState();
     public final AnimationState slideAnimationState = new AnimationState();
     public final AnimationState burrowStartAnimationState = new AnimationState();
     public final AnimationState burrowHoldAnimationState = new AnimationState();
     public final AnimationState quirkAnimationState = new AnimationState();

     public DiplocaulusEntity(EntityType<? extends AncientEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         this.switchNavigator(true);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.12F);
     }

     protected void registerGoals() {
         this.goalSelector.addGoal(1, new DiplocaulusQuirkGoal(this));
         this.goalSelector.addGoal(3, new DiplocaulusRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(3, new DiplocaulusSwimGoal(this, 1.25D, 5));
         this.goalSelector.addGoal(4, new LargePanicGoal(this, 2.0D));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
     }

     private void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new MoveControl(this);
             this.navigation = new GroundPathNavigation(this, level());
             this.lookControl = new LookControl(this);
             this.isLandNavigator = true;
         } else {
             this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2F, 0.1F, true);
             this.navigation = new AmphibiousPathNavigation(this, level());
             this.lookControl = new SmoothSwimmingLookControl(this, 10);
             this.isLandNavigator = false;
         }
     }

     @Override
     public void travel(Vec3 pTravelVector) {
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), pTravelVector);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
             if (this.getTarget() == null) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
             }
             this.calculateEntityAnimation(true);
         } else {
             super.travel(pTravelVector);
         }
     }

     public void tick() {
         super.tick();

         final boolean ground = !this.isInWater();

         if (!ground && this.isLandNavigator) {
             switchNavigator(false);
         }
         if (ground && !this.isLandNavigator) {
             switchNavigator(true);
         }

         if (this.level().isClientSide()){
             this.setupAnimationStates();
         }

         if (this.getQuirkCooldown() > 0) {
             this.setQuirkCooldown(this.getQuirkCooldown() - 1);
         }
         if (this.getQuirkTimer() > 0) {
             this.setQuirkTimer(this.getQuirkTimer() - 1);
             if (this.getQuirkTimer() == 0) {
                 this.quirkCooldown();
             }
         }
     }

     private void setupAnimationStates() {
         this.idleAnimationState.animateWhen(this.isAlive() && !this.isInWaterOrBubble(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isAlive() && this.isInWaterOrBubble(), this.tickCount);
         this.quirkAnimationState.animateWhen(this.getQuirkCooldown() == 0, this.tickCount);
     }

     @Override
     protected void defineSynchedData() {
         this.entityData.define(VARIANT, 0);
         this.entityData.define(QUIRK_COOLDOWN, 2 * 20 + random.nextInt(12 * 20));
         this.entityData.define(QUIRK_TIMER, 0);
         super.defineSynchedData();
     }

     public void addAdditionalSaveData(CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("Variant", this.getVariant());
         compoundTag.putInt("QuirkCooldown", this.getQuirkCooldown());
         compoundTag.putInt("QuirkTimer", this.getQuirkTimer());
     }

     public void readAdditionalSaveData(CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setVariant(compoundTag.getInt("Variant"));
         this.setQuirkCooldown(compoundTag.getInt("LookoutCooldown"));
         this.setQuirkTimer(compoundTag.getInt("LookoutTimer"));
     }

     public int getQuirkTimer() {
         return this.entityData.get(QUIRK_TIMER);
     }
     public void setQuirkTimer(int timer) {
         this.entityData.set(QUIRK_TIMER, timer);
     }

     public int getQuirkCooldown() {
         return this.entityData.get(QUIRK_COOLDOWN);
     }
     public void setQuirkCooldown(int cooldown) {
         this.entityData.set(QUIRK_COOLDOWN, cooldown);
     }
     public void quirkCooldown() {
         this.entityData.set(QUIRK_COOLDOWN, 6 * 20 + random.nextInt(30 * 2 * 20));
     }

     public boolean canBreatheUnderwater() {
         return true;
     }

     protected SoundEvent getAmbientSound() {
         return UP2Sounds.DIPLOCAULUS_IDLE.get();
     }

     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
         return UP2Sounds.DIPLOCAULUS_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UP2Sounds.DIPLOCAULUS_DEATH.get();
     }

     protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
         this.playSound(SoundEvents.FROG_STEP, 0.1F, 1.5F);
     }

     @Override
     public float getSoundVolume() {
         return 0.5F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         DiplocaulusEntity diplo = UP2Entities.DIPLOCAULUS.get().create(serverLevel);
         diplo.setVariant(this.getVariant());
         return diplo;
     }

     public void determineVariant (int variantChange) {
         if (variantChange <= 25) {
             this.setVariant(1);
         }
         else if (variantChange <= 50) {
             this.setVariant(2);
         }
         else if (variantChange <= 75) {
             this.setVariant(3);
         }
         else if (variantChange <= 100) {
             this.setVariant(0);
         }
     }

     public String getVariantName() {
         return switch (this.getVariant()) {
             case 1 -> "magnicornis";
             case 2 -> "recurvatis";
             case 3 -> "salamandroides";
             default -> "brevirostris";
         };
     }

     // goals
     private static class DiplocaulusRandomStrollGoal extends RandomStrollGoal{

         private final DiplocaulusEntity diplocaulus;

         public DiplocaulusRandomStrollGoal(DiplocaulusEntity diplocaulus, double speedModifier) {
             super(diplocaulus, speedModifier);
             this.diplocaulus = diplocaulus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && this.diplocaulus.isLandNavigator;
         }

         @Override
         public boolean canContinueToUse() {
             return super.canContinueToUse() && this.diplocaulus.isLandNavigator;
         }
     }

     private static class DiplocaulusSwimGoal extends RandomStrollGoal {

         private final DiplocaulusEntity diplocaulus;

         public DiplocaulusSwimGoal(DiplocaulusEntity diplocaulus, double speed, int chance) {
             super(diplocaulus, speed, chance, false);
             this.diplocaulus = diplocaulus;
         }

         public boolean canUse() {
             if (this.mob.isVehicle() || mob.getTarget() != null || !this.mob.isInWater() && this.diplocaulus.isLandNavigator) {
                 return false;
             } else {
                 if (!this.forceTrigger) {
                     if (this.mob.getRandom().nextInt(this.interval) != 0) {
                         return false;
                     }
                 }
                 Vec3 vector3d = this.getPosition();
                 if (vector3d == null) {
                     return false;
                 } else {
                     this.wantedX = vector3d.x;
                     this.wantedY = vector3d.y;
                     this.wantedZ = vector3d.z;
                     this.forceTrigger = false;
                     return true;
                 }
             }
         }

         @Nullable
         protected Vec3 getPosition() {
             if (this.mob.hasRestriction() && this.mob.distanceToSqr(Vec3.atCenterOf(this.mob.getRestrictCenter())) > this.mob.getRestrictRadius() * this.mob.getRestrictRadius()) {
                 return DefaultRandomPos.getPosTowards(this.mob, 7, 3, Vec3.atBottomCenterOf(this.mob.getRestrictCenter()), 1);
             }
             if (this.mob.getRandom().nextFloat() < 0.3F) {
                 Vec3 vector3d = findSurfaceTarget(this.mob, 15, 7);
                 if(vector3d != null){
                     return vector3d;
                 }
             }
             Vec3 vector3d = DefaultRandomPos.getPos(this.mob, 7, 3);
             return vector3d;
         }

         private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
             BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
             return this.mob.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.mob.level().getBlockState(blockpos).blocksMotion();
         }

         private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
             return this.mob.level().getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir() && this.mob.level().getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
         }

         private Vec3 findSurfaceTarget(PathfinderMob creature, int i, int j) {
             BlockPos upPos = creature.blockPosition();
             while (creature.level().getFluidState(upPos).is(FluidTags.WATER)) {
                 upPos = upPos.above();
             }
             if (isAirAbove(upPos.below(), 0, 0, 0) && canJumpTo(upPos.below(), 0, 0, 0)){
                 return new Vec3(upPos.getX() + 0.5F, upPos.getY() - 1F, upPos.getZ() + 0.5F);
             }
             return null;
         }
     }

     private static class DiplocaulusQuirkGoal extends Goal {

         DiplocaulusEntity diplocaulus;

         public DiplocaulusQuirkGoal(DiplocaulusEntity diplocaulus) {
             this.diplocaulus = diplocaulus;
         }

         @Override
         public boolean canUse() {
             return this.diplocaulus.getQuirkCooldown() == 0;
         }

         @Override
         public boolean canContinueToUse() {
             return this.diplocaulus.getQuirkTimer() > 0;
         }

         @Override
         public void start() {
             super.start();
             this.diplocaulus.setQuirkTimer(60);
         }

         @Override
         public void tick() {
             super.tick();
         }

         @Override
         public void stop() {
             super.stop();
             this.diplocaulus.quirkCooldown();
         }
     }
 }
