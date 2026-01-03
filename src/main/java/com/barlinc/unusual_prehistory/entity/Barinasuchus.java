 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.FloatGoal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Barinasuchus extends PrehistoricMob {

     public int attackCooldown = 0;

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState swimAnimationState = new AnimationState();
     public final AnimationState sitStartAnimationState = new AnimationState();
     public final AnimationState sitAnimationState = new AnimationState();
     public final AnimationState sitEndAnimationState = new AnimationState();
     public final AnimationState attack1AnimationState = new AnimationState();
     public final AnimationState attack2AnimationState = new AnimationState();
     public final AnimationState yawnAnimationState = new AnimationState();
     public final AnimationState shakeAnimationState = new AnimationState();
     public final AnimationState scratch1AnimationState = new AnimationState();
     public final AnimationState scratch2AnimationState = new AnimationState();
     public final AnimationState snapAnimationState = new AnimationState();
     public final AnimationState threatenAnimationState = new AnimationState();

     private int attackTicks;

     private int yawnCooldown = 1100 + this.getRandom().nextInt(1200);
     private int shakeCooldown = 1400 + this.getRandom().nextInt(1600);
     private int snapCooldown = 1500 + this.getRandom().nextInt(1700);
     private int scratchCooldown = 1600 + this.getRandom().nextInt(1700);
     private int threatenCooldown = 2300 + this.getRandom().nextInt(2400);;

     public Barinasuchus(EntityType<? extends PrehistoricMob> entityType, Level level) {
         super(entityType, level);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 50.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.23F)
                 .add(Attributes.ATTACK_DAMAGE, 10.0D)
                 .add(Attributes.ARMOR, 8.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                 .add(Attributes.FOLLOW_RANGE, 32.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new FloatGoal(this));
         this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(2, new BarinasuchusAttackGoal(this));
         this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.BARINASUCHUS_FOOD), false));
         this.goalSelector.addGoal(4, new PrehistoricRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(6, new RandomSitGoal(this));
         this.goalSelector.addGoal(7, new BarinasuchusYawnGoal(this));
         this.goalSelector.addGoal(7, new BarinasuchusShakeGoal(this));
         this.goalSelector.addGoal(7, new BarinasuchusSnapGoal(this));
         this.goalSelector.addGoal(7, new BarinasuchusScratchGoal(this));
         this.goalSelector.addGoal(8, new BarinasuchusThreatenGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
     }

     @Override
     protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
         return dimensions.height * 0.7F;
     }

     @Override
     protected float getWaterSlowDown() {
         return 0.9F;
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if (this.refuseToMove() && this.onGround()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVec = travelVec.multiply(0.0, 1.0, 0.0);
         }
         super.travel(travelVec);
     }

     @Override
     public float getStepHeight() {
         return this.isRunning() ? 1.1F : 0.6F;
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.BARINASUCHUS_FOOD);
     }

     @Override
     public boolean canPacify() {
         return true;
     }

     @Override
     public boolean isPacifyItem(ItemStack itemStack) {
         return itemStack.is(UP2ItemTags.PACIFIES_BARINASUCHUS);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getIdleState() == 4 || this.getIdleState() == 5;
     }

     @Override
     public void tick() {
         super.tick();
     }

     @Override
     public void setupAnimationStates() {
         if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
         }
         this.idleAnimationState.animateWhen(!this.isInWater() && this.getIdleState() != 5, this.tickCount);
         this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);

         if (this.isMobVisuallySitting()) {
             this.sitEndAnimationState.stop();
             this.idleAnimationState.stop();
             this.threatenAnimationState.stop();
             this.scratch1AnimationState.stop();
             this.scratch2AnimationState.stop();
             this.shakeAnimationState.stop();

             if (this.isVisuallySitting()) {
                 this.sitStartAnimationState.startIfStopped(this.tickCount);
                 this.sitAnimationState.stop();
             } else {
                 this.sitStartAnimationState.stop();
                 this.sitAnimationState.startIfStopped(this.tickCount);
             }
         } else {
             this.sitStartAnimationState.stop();
             this.sitAnimationState.stop();
             this.sitEndAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
         }
     }

     @Override
     public void setupAnimationCooldowns() {
         if (attackTicks > 0) attackTicks--;
         if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
             this.attackCooldown = 3 + this.getRandom().nextInt(2);
             this.setPose(Pose.STANDING);
         }
         if (attackCooldown > 0) attackCooldown--;
         if (yawnCooldown > 0) yawnCooldown--;
         if (shakeCooldown > 0) shakeCooldown--;
         if (snapCooldown > 0) snapCooldown--;
         if (scratchCooldown > 0) scratchCooldown--;
         if (threatenCooldown > 0) threatenCooldown--;
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.ATTACKING.get()) {
                 if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                 else this.attack2AnimationState.start(this.tickCount);
                 this.attackTicks = 15;
             }
             else {
                 this.attack1AnimationState.stop();
                 this.attack2AnimationState.stop();
             }
         }
         super.onSyncedDataUpdated(accessor);
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             case 67 -> this.yawnAnimationState.start(this.tickCount);
             case 68 -> this.yawnAnimationState.stop();

             case 69 -> this.shakeAnimationState.start(this.tickCount);
             case 70 -> this.shakeAnimationState.stop();

             case 71 -> this.snapAnimationState.start(this.tickCount);
             case 72 -> this.snapAnimationState.stop();

             case 73 -> {
                 if (this.getRandom().nextBoolean()) this.scratch1AnimationState.start(this.tickCount);
                 else this.scratch2AnimationState.start(this.tickCount);
             }
             case 74 -> {
                 this.scratch1AnimationState.stop();
                 this.scratch2AnimationState.stop();
             }

             case 75 -> this.threatenAnimationState.start(this.tickCount);
             case 76 -> this.threatenAnimationState.stop();
             default -> super.handleEntityEvent(id);
         }
     }

     protected void yawnCooldown() {
         this.yawnCooldown = 1100 + this.getRandom().nextInt(1200);
     }

     protected void shakeCooldown() {
         this.shakeCooldown = 1400 + this.getRandom().nextInt(1600);
     }

     protected void snapCooldown() {
         this.snapCooldown = 1500 + this.getRandom().nextInt(1700);
     }

     protected void scratchCooldown() {
         this.scratchCooldown = 1600 + this.getRandom().nextInt(1700);
     }

     protected void threatenCooldown() {
         this.threatenCooldown = 2300 + this.getRandom().nextInt(2400);
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return this.getIdleState() == 5 ? SoundEvents.EMPTY : UP2SoundEvents.BARINASUCHUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.BARINASUCHUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.BARINASUCHUS_DEATH.get();
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.BARINASUCHUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Barinasuchus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.BARINASUCHUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }

     // Goals
     private static class BarinasuchusYawnGoal extends AnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusYawnGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 80, 1, (byte) 67, (byte) 68, false, false);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.yawnCooldown == 0;
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.yawnCooldown();
         }
     }

     private static class BarinasuchusShakeGoal extends AnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusShakeGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 60, 2, (byte) 69, (byte) 70, false);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.shakeCooldown == 0 && !barinasuchus.isMobSitting();
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.shakeCooldown();
         }
     }

     private static class BarinasuchusSnapGoal extends AnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusSnapGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 60, 3, (byte) 71, (byte) 72, false, false);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.snapCooldown == 0;
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.snapCooldown();
         }
     }

     private static class BarinasuchusScratchGoal extends AnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusScratchGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 30, 4, (byte) 73, (byte) 74);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.scratchCooldown == 0 && !barinasuchus.isMobSitting();
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.scratchCooldown();
         }
     }

     private static class BarinasuchusThreatenGoal extends AnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusThreatenGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 60, 5, (byte) 75, (byte) 76);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.threatenCooldown == 0 && !barinasuchus.isMobSitting();
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.threatenCooldown();
         }

         @Override
         public void tick() {
             super.tick();
             if (timer == 55) {
                 this.barinasuchus.playSound(UP2SoundEvents.BARINASUCHUS_THREATEN.get(), 1.5F, 0.9F + barinasuchus.getRandom().nextFloat() * 0.25F);
             }
         }
     }
 }
