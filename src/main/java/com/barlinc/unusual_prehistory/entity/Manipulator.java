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
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
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
 import net.minecraft.world.item.Items;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Manipulator extends PrehistoricMob {

     public int attackCooldown = 0;

     public final AnimationState idleArmedAnimationState = new AnimationState();
     public final AnimationState sitArmedAnimationState = new AnimationState();
     public final AnimationState attackAnimationState = new AnimationState();
     public final AnimationState attackArmedAnimationState = new AnimationState();

     private int attackTicks;

     public Manipulator(EntityType<? extends PrehistoricMob> entityType, Level level) {
         super(entityType, level);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 28.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.3F)
                 .add(Attributes.ATTACK_DAMAGE, 4.0D)
                 .add(Attributes.FOLLOW_RANGE, 20.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new FloatGoal(this));
         this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
         this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(3, new ManipulatorAttackGoal(this));
         this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 6.0F, 4.0F, false));
         this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MANIPULATOR_FOOD), false));
         this.goalSelector.addGoal(6, new PrehistoricRandomStrollGoal(this, 1.0D));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
     }

     @Override
     protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
         return dimensions.height * 0.9F;
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if ((this.refuseToMove() || this.isDancing()) && this.onGround()) {
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
         return stack.is(UP2ItemTags.MANIPULATOR_FOOD);
     }

     @Override
     public boolean canPacify() {
         return true;
     }

     @Override
     public boolean isPacifyItem(ItemStack itemStack) {
         return itemStack.is(UP2ItemTags.PACIFIES_MANIPULATOR);
     }

     @Override
     public boolean canDanceToJukebox() {
         return true;
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove();
     }

     @Override
     public boolean canOwnerCommand(Player player) {
         return true;
     }

     @Override
     public void startSitting() {
     }

     @Override
     public void stopSitting() {
     }

     @Override
     public void stopSittingInstantly() {
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);
         if (!this.isTame() && itemstack.is(Items.DEBUG_STICK)) {
             if (!player.getAbilities().instabuild) {
                 itemstack.shrink(1);
             }
             this.gameEvent(GameEvent.ENTITY_INTERACT);
             this.tame(player);
             this.level().broadcastEntityEvent(this, (byte) 9);
             this.setPacified(true);
             this.heal(this.getMaxHealth());
             return InteractionResult.SUCCESS;
         }
         return type;
     }

     public boolean isHoldingItem() {
         return !this.getMainHandItem().isEmpty() && !this.getOffhandItem().isEmpty();
     }

     private boolean canPlayIdle() {
         return !this.isInSittingPose() && !this.isDancing();
     }

     @Override
     public void setupAnimationStates() {
         if (attackTicks == 0 && (this.attackAnimationState.isStarted() || this.attackArmedAnimationState.isStarted())) {
             this.attackAnimationState.stop();
             this.attackArmedAnimationState.stop();
         }
         this.idleAnimationState.animateWhen(!this.isHoldingItem() && this.canPlayIdle(), this.tickCount);
         this.idleArmedAnimationState.animateWhen(this.isHoldingItem() && this.canPlayIdle(), this.tickCount);
         this.danceAnimationState.animateWhen(this.isDancing(), this.tickCount);
         this.sitAnimationState.animateWhen(this.isInSittingPose() && !this.isHoldingItem() && !this.isDancing(), this.tickCount);
         this.sitArmedAnimationState.animateWhen(this.isInSittingPose() && this.isHoldingItem() && !this.isDancing(), this.tickCount);
     }

     @Override
     public void setupAnimationCooldowns() {
         if (attackTicks > 0) attackTicks--;
         if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
             this.attackCooldown = 10 + this.getRandom().nextInt(7);
             this.setPose(Pose.STANDING);
         }
         if (attackCooldown > 0) attackCooldown--;
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.ATTACKING.get()) {
                 if (this.isHoldingItem()) this.attackArmedAnimationState.start(this.tickCount);
                 else this.attackAnimationState.start(this.tickCount);
                 this.attackTicks = 30;
             }
             else if (this.getPose() == Pose.STANDING) {
                 this.attackAnimationState.stop();
                 this.attackArmedAnimationState.stop();
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
         return UP2SoundEvents.MANIPULATOR_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.MANIPULATOR_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.MANIPULATOR_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(UP2SoundEvents.MANIPULATOR_STEP.get(), 0.4F, this.getStepPitch());
     }

     @Override
     protected float nextStep() {
         return this.moveDist + 0.75F;
     }

     private float getStepPitch() {
         return this.isBaby() ? 1.45F + this.getRandom().nextFloat() * 0.1F : 0.95F + this.getRandom().nextFloat() * 0.1F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.MANIPULATOR.get().create(level);
     }

     public static boolean canSpawn(EntityType<Manipulator> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.MANIPULATOR_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
