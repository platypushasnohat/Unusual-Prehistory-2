 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousPathNavigation;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
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
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Kaprosuchus extends SemiAquaticMob {

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimIdleAnimationState = new AnimationState();
     public final AnimationState attackAnimationState = new AnimationState();

     private int attackTicks;

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
                 .add(Attributes.ATTACK_DAMAGE, 5.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.8D, entity -> entity.getType().is(UP2EntityTags.PRAEPUSA_AVOIDS)));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KAPROSUCHUS_FOOD), false));
         this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 50));
         this.goalSelector.addGoal(4, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 1200, 1500));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D, 1500));
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.lookControl = new PrehistoricLookControl(this);
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
     public boolean refuseToMove() {
         return super.refuseToMove();
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
         if (this.attackAnimationState.isStarted() && attackTicks == 0) this.attackAnimationState.stop();
         this.idleAnimationState.animateWhen(!this.isInWater() && this.getIdleState() != 3, this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWater(), this.tickCount);
     }

     @Override
     public void setupAnimationCooldowns() {
         if (attackTicks > 0) attackTicks--;
         if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.ATTACKING.get()) {
                 this.attackAnimationState.start(this.tickCount);
                 this.attackTicks = 10;
             }
             else {
                 this.attackAnimationState.stop();
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
 }
