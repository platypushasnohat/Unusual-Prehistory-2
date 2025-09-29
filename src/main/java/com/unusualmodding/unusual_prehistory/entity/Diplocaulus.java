 package com.unusualmodding.unusual_prehistory.entity;

 import com.unusualmodding.unusual_prehistory.entity.ai.goals.CustomizableRandomSwimGoal;
 import com.unusualmodding.unusual_prehistory.entity.ai.goals.LargePanicGoal;
 import com.unusualmodding.unusual_prehistory.entity.ai.goals.SemiAquaticRandomStrollGoal;
 import com.unusualmodding.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
 import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
 import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.Goal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Diplocaulus extends SemiAquaticMob {

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimIdleAnimationState = new AnimationState();
     public final AnimationState slideAnimationState = new AnimationState();
     public final AnimationState burrowStartAnimationState = new AnimationState();
     public final AnimationState burrowHoldAnimationState = new AnimationState();
     public final AnimationState quirkAnimationState = new AnimationState();

     private int quirkCooldown = this.random.nextInt(10 * 40) + (20 * 40);

     public Diplocaulus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.12F);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new DiplocaulusQuirkGoal(this));
         this.goalSelector.addGoal(1, new LargePanicGoal(this, 2.0D));
         this.goalSelector.addGoal(2, new CustomizableRandomSwimGoal(this, 1.0D, 10, 10, 10, 3));
         this.goalSelector.addGoal(3, new SemiAquaticRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
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

         if (this.getPose() != UP2Poses.QUIRK.get() && quirkCooldown > 0) {
             quirkCooldown--;
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(this.isAlive() && !this.isInWaterOrBubble(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isAlive() && this.isInWaterOrBubble(), this.tickCount);
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
         if (DATA_POSE.equals(entityDataAccessor)) {
             if (this.getPose() == UP2Poses.QUIRK.get()) {
                 this.quirkAnimationState.start(this.tickCount);
             } else if (this.getPose() == Pose.STANDING) {
                 this.quirkAnimationState.stop();
             }
         }
         super.onSyncedDataUpdated(entityDataAccessor);
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.DIPLOCAULUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(DamageSource damageSource) {
         return UP2SoundEvents.DIPLOCAULUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.DIPLOCAULUS_DEATH.get();
     }

     @Override
     protected void playStepSound(BlockPos pos, BlockState state) {
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

     @Nullable
     @Override
     public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
         spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
         if (spawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains("BucketVariantTag", 3)) {
             this.setVariant(compoundTag.getInt("BucketVariantTag"));
         } else {
             this.setVariant(random.nextInt(DiplocaulusVariant.values().length));
         }
         return spawnGroupData;
     }

     // goals
     static class DiplocaulusQuirkGoal extends Goal {

         public Diplocaulus diplocaulus;
         public int timer;

         public DiplocaulusQuirkGoal(Diplocaulus diplocaulus) {
             this.diplocaulus = diplocaulus;
         }

         @Override
         public boolean canUse() {
             return diplocaulus.quirkCooldown == 0 && diplocaulus.getPose() == Pose.STANDING;
         }

         @Override
         public void start() {
             this.timer = 80;
             this.diplocaulus.getNavigation().stop();
             this.diplocaulus.setPose(UP2Poses.QUIRK.get());
         }

         @Override
         public boolean canContinueToUse() {
             return timer > 0 && diplocaulus.getPose() == UP2Poses.QUIRK.get();
         }

         @Override
         public void tick() {
             this.timer--;
         }

         @Override
         public void stop() {
             diplocaulus.quirkCooldown = diplocaulus.random.nextInt(10 * 40) + (20 * 40);
             this.diplocaulus.setPose(Pose.STANDING);
         }

         @Override
         public boolean requiresUpdateEveryTick() {
             return true;
         }
     }
 }
