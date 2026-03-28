 package com.barlinc.unusual_prehistory.entity.mob.future;

 import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Eryon extends SemiAquaticMob {

     public Eryon(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 4.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.24F)
                 .add(Attributes.ARMOR, 4.0D);
     }

//     @Override
//     protected void registerGoals() {
//         this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.8D, 10, 4));
//         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.HIBBERTOPTERUS_FOOD), false));
//         this.goalSelector.addGoal(3, new PrehistoricRandomStrollGoal(this, 1.0D, false));
//         this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
//         this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
//     }

     @Override
     public @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new NoSpinGroundPathNavigation(this, level);
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if (this.refuseToMove() && this.onGround()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVec = travelVec.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
             this.moveRelative(this.getSpeed(), travelVec);
             Vec3 delta = this.getDeltaMovement();
             this.move(MoverType.SELF, delta);
             delta = delta.scale(0.8D);
             if (this.jumping || horizontalCollision) {
                 delta = delta.add(0, 0.1F, 0);
             } else {
                 delta = delta.add(0, -0.05F, 0);
             }
             this.setDeltaMovement(delta.scale(0.8D));
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public @NotNull MobType getMobType() {
         return MobType.ARTHROPOD;
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
     public void tick() {
         super.tick();
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isEepy(), this.tickCount);
     }

     @Override
     public float getWalkAnimationSpeed() {
         return this.isBaby() ? 4.0F : 10.0F;
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         super.onSyncedDataUpdated(accessor);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.HIBBERTOPTERUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.HIBBERTOPTERUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.HIBBERTOPTERUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(UP2SoundEvents.HIBBERTOPTERUS_STEP.get(), 0.4F, this.getStepPitch());
     }

     @Override
     protected float nextStep() {
         return this.moveDist + 0.55F;
     }

     private float getStepPitch() {
         return this.isBaby() ? 1.45F + this.getRandom().nextFloat() * 0.1F : 0.95F + this.getRandom().nextFloat() * 0.1F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.LYSTROSAURUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Eryon> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.HIBBERTOPTERUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
