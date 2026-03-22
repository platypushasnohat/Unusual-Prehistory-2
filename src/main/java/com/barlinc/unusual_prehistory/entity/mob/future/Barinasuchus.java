 package com.barlinc.unusual_prehistory.entity.mob.future;

 import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
 import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Barinasuchus extends PrehistoricMob {

     private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Barinasuchus.class, EntityDataSerializers.INT);

     private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.5F, 1.4F);

     public int attackCooldown = 0;

     public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState scratch1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState scratch2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState snapAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState threatenAnimationState = new SmoothAnimationState();

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
                 .add(Attributes.MAX_HEALTH, 40.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.23F)
                 .add(Attributes.ATTACK_DAMAGE, 7.0D)
                 .add(Attributes.ARMOR, 8.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                 .add(Attributes.FOLLOW_RANGE, 32.0D);
     }

//     @Override
//     protected void registerGoals() {
//         this.goalSelector.addGoal(0, new FloatGoal(this));
//         this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
//         this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
//         this.goalSelector.addGoal(3, new BarinasuchusAttackGoal(this));
//         this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 7.0F, 4.0F, false));
//         this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.BARINASUCHUS_FOOD), false));
//         this.goalSelector.addGoal(6, new PrehistoricRandomStrollGoal(this, 1.0D));
//         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
//         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
//         this.goalSelector.addGoal(8, new RandomSitGoal(this));
//         this.goalSelector.addGoal(8, new SleepingGoal(this));
//         this.goalSelector.addGoal(9, new BarinasuchusYawnGoal(this));
//         this.goalSelector.addGoal(9, new BarinasuchusShakeGoal(this));
//         this.goalSelector.addGoal(9, new BarinasuchusSnapGoal(this));
//         this.goalSelector.addGoal(9, new BarinasuchusScratchGoal(this));
//         this.goalSelector.addGoal(10, new BarinasuchusThreatenGoal(this));
//         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
//         this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
//         this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
//     }

     @Override
     protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new NoSpinGroundPathNavigation(this, level);
     }

     @Override
     protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
         return dimensions.height * 0.7F;
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
         return 1.1F;
     }

     // Riding
     @Override
     protected float getRiddenSpeed(@NotNull Player rider) {
         float sprintSpeed = rider.isSprinting() ? 0.12F : 0.0F;
         return ((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F) + sprintSpeed;
     }

     @Override
     public boolean canSprint() {
         return true;
     }

     @Override
     public Vec3 getRiderOffset() {
         return new Vec3(0.0F, 0.0F, 0.25F);
     }

     @Override
     public boolean canOwnerCommand(Player player) {
         return player.isShiftKeyDown();
     }

     @Override
     public boolean canOwnerMount(Player player) {
         return !this.isBaby();
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
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);
         if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_BARINASUCHUS)) {
             if (!this.level().isClientSide) {
                 if (!player.getAbilities().instabuild) {
                     itemstack.shrink(1);
                 }
                 this.gameEvent(GameEvent.ENTITY_INTERACT);
                 if (this.getTameAttempts() > 2 && this.getRandom().nextBoolean()) {
                     this.level().broadcastEntityEvent(this, (byte) 7);
                     this.tame(player);
                     this.setPacifiedTicks(-1);
                     this.heal(this.getMaxHealth());
                 } else {
                     this.level().broadcastEntityEvent(this, (byte) 6);
                     this.setTameAttempts(this.getTameAttempts() + 1);
                 }
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         return type;
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getIdleState() == 4 || this.getIdleState() == 5;
     }

     @Override
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return (pose == UP2Poses.SLEEPING.get() || pose == UP2Poses.SITTING.get()) ? EEPY_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
     }

     @Override
     public Vec3 getEepyParticleVec() {
         return new Vec3(0, 0, -this.getBbWidth() * 1.5F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
     }

     @Override
     public void setupAnimationStates() {
         if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
         }
         this.idleAnimationState.animateWhen(!this.isInWater() && this.getIdleState() != 5 && !this.isSitting() && !this.isEepy(), this.tickCount);
         this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);
         this.attack1AnimationState.animateWhen(this.attack1AnimationState.isStarted(), this.tickCount);
         this.attack2AnimationState.animateWhen(this.attack2AnimationState.isStarted(), this.tickCount);
     }

     @Override
     public void tickCooldowns() {
         super.tickCooldowns();
         if (attackTicks > 0) attackTicks--;
         if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
             this.attackCooldown = 3 + this.getRandom().nextInt(2);
             this.setPose(Pose.STANDING);
         }
         if (attackCooldown > 0) attackCooldown--;
         if (!this.isEepy()) {
             if (yawnCooldown > 0) yawnCooldown--;
             if (shakeCooldown > 0) shakeCooldown--;
             if (snapCooldown > 0) snapCooldown--;
             if (scratchCooldown > 0) scratchCooldown--;
             if (threatenCooldown > 0) threatenCooldown--;
         }
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
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(TAME_ATTEMPTS, 0);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("TameAttempts", this.getTameAttempts());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setTameAttempts(compoundTag.getInt("TameAttempts"));
     }

     public void setTameAttempts(int tameAttempts) {
         this.entityData.set(TAME_ATTEMPTS, tameAttempts);
     }

     public int getTameAttempts() {
         return this.entityData.get(TAME_ATTEMPTS);
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
     private static class BarinasuchusAttackGoal extends AttackGoal {

         protected final Barinasuchus barinasuchus;

         public BarinasuchusAttackGoal(Barinasuchus barinasuchus) {
             super(barinasuchus);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public void tick() {
             LivingEntity target = barinasuchus.getTarget();
             if (target != null) {
                 double distance = barinasuchus.distanceToSqr(target);
                 this.barinasuchus.lookAt(barinasuchus.getTarget(), 30F, 30F);
                 this.barinasuchus.getLookControl().setLookAt(target, 30F, 30F);

                 if (this.barinasuchus.getAttackState() == 1) {
                     this.barinasuchus.getNavigation().moveTo(target, 1.5D);
                     this.tickBite();
                 } else {
                     this.barinasuchus.getNavigation().moveTo(target, 1.8D);
                     if (distance <= this.getAttackReachSqr(target) && barinasuchus.attackCooldown == 0) {
                         this.barinasuchus.setAttackState(1);
                     }
                 }
             }
         }

         protected void tickBite() {
             this.timer++;
             LivingEntity target = barinasuchus.getTarget();
             if (timer == 1) barinasuchus.setPose(UP2Poses.ATTACKING.get());
             if (timer == 7) barinasuchus.playSound(UP2SoundEvents.BARINASUCHUS_ATTACK.get(), 1.0F, 0.9F + barinasuchus.getRandom().nextFloat() * 0.2F);
             if (timer == 11) {
                 if (this.barinasuchus.distanceTo(target) < this.getAttackReachSqr(target)) {
                     this.barinasuchus.doHurtTarget(target);
                     this.barinasuchus.swing(InteractionHand.MAIN_HAND);
                 }
             }
             if (timer > 15) {
                 this.timer = 0;
                 this.barinasuchus.setAttackState(0);
             }
         }

         @Override
         protected double getAttackReachSqr(LivingEntity target) {
             return this.mob.getBbWidth() * 1.4F * this.mob.getBbWidth() * 1.4F + target.getBbWidth();
         }
     }

     private static class BarinasuchusYawnGoal extends IdleAnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusYawnGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 80, 1, false, false);
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

     private static class BarinasuchusShakeGoal extends IdleAnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusShakeGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 60, 2, false);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.shakeCooldown == 0 && !barinasuchus.isSitting();
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.shakeCooldown();
         }
     }

     private static class BarinasuchusSnapGoal extends IdleAnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusSnapGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 60, 3, false, false);
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

     private static class BarinasuchusScratchGoal extends IdleAnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusScratchGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 30, 4);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.scratchCooldown == 0 && !barinasuchus.isSitting();
         }

         @Override
         public void stop() {
             super.stop();
             this.barinasuchus.scratchCooldown();
         }
     }

     private static class BarinasuchusThreatenGoal extends IdleAnimationGoal {

         private final Barinasuchus barinasuchus;

         public BarinasuchusThreatenGoal(Barinasuchus barinasuchus) {
             super(barinasuchus, 60, 5);
             this.barinasuchus = barinasuchus;
         }

         @Override
         public boolean canUse() {
             return super.canUse() && barinasuchus.threatenCooldown == 0 && !barinasuchus.isSitting();
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
