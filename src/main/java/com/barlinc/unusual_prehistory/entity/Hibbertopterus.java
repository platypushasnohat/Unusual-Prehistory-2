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
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.RandomSource;
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
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Hibbertopterus extends SemiAquaticMob {

     private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.9F, 0.8F);

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
         this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KAPROSUCHUS_FOOD), false));
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
         return stack.is(UP2ItemTags.KAPROSUCHUS_FOOD);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove();
     }

     @Override
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return (pose == UP2Poses.SITTING.get() || pose == UP2Poses.SLEEPING.get()) ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
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
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         super.onSyncedDataUpdated(accessor);
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             default -> super.handleEntityEvent(id);
         }
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
         return level.getBlockState(pos.below()).is(UP2BlockTags.KAPROSUCHUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
