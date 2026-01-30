 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.UnusualPrehistory2;
 import com.barlinc.unusual_prehistory.entity.ai.goals.LargePanicGoal;
 import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricRandomStrollGoal;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.SaddlelessItemBasedSteering;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.events.ScreenShakeEvent;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Items;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.Direction;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.BlockTags;
 import net.minecraft.util.Mth;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
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
 import net.minecraft.world.item.Items;
 import net.minecraft.world.item.context.UseOnContext;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.Block;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.BlockHitResult;
 import net.minecraft.world.phys.Vec3;
 import net.minecraftforge.common.ToolActions;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Hibbertopterus extends SemiAquaticMob implements ItemSteerable {

     private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.9F, 0.8F);

     private static final EntityDataAccessor<Integer> PLOW_TIME = SynchedEntityData.defineId(Hibbertopterus.class, EntityDataSerializers.INT);

     private final SaddlelessItemBasedSteering steering = new SaddlelessItemBasedSteering(this.entityData, PLOW_TIME);

     public final AnimationState plowAnimationState = new AnimationState();

     public Hibbertopterus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 40.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.17F)
                 .add(Attributes.ARMOR, 10.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(1, new LargePanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.HIBBERTOPTERUS_FOOD), false));
         this.goalSelector.addGoal(3, new PrehistoricRandomStrollGoal(this, 1.0D, false));
         this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
     }

     @Override
     public @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new SmoothGroundPathNavigation(this, level);
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if ((this.refuseToMove() || this.isDancing()) && this.onGround()) {
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
     public boolean canDanceToJukebox() {
         return true;
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
         return this.isAlive() && !this.isBaby();
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
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return (pose == UP2Poses.SITTING.get() || pose == UP2Poses.SLEEPING.get()) ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
     }

     @Nullable
     @Override
     public LivingEntity getControllingPassenger() {
         Entity entity = this.getFirstPassenger();
         if (entity instanceof Player player) {
             if (player.getMainHandItem().is(UP2Items.DIRT_ON_A_STICK.get()) || player.getOffhandItem().is(UP2Items.DIRT_ON_A_STICK.get())) {
                 return player;
             }
         }
         return null;
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         boolean flag = this.isFood(player.getItemInHand(hand));
         if (!flag && !this.isBaby() && !this.isVehicle() && !player.isSecondaryUseActive() && !player.isShiftKeyDown()) {
             if (!this.level().isClientSide) {
                 player.startRiding(this);
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         else {
             return super.mobInteract(player, hand);
         }
     }

     protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
         return new Vec3(0.0D, 0.0D, 1.0D);
     }

     @Override
     protected float getRiddenSpeed(@NotNull Player player) {
         return (float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225D * this.steering.boostFactor());
     }

     @Override
     public Vec3 getRiderOffset() {
         return new Vec3(0.0F, 0.0F, -0.5F);
     }

     @Override
     public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity livingEntity) {
         return new Vec3(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
     }

     @Override
     protected void tickRidden(@NotNull Player player, @NotNull Vec3 vec3) {
         this.setRot(player.getYRot(), player.getXRot() * 0.5F);
         this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
         this.steering.tickBoost();
     }

     @Override
     public boolean boost() {
         return this.steering.boost(this.getRandom());
     }

     private void tickPlowing() {
         if (!(this.getControllingPassenger() instanceof Player player)) return;

         for (int i = 0; i < 4; i++) {
             float offset = 38.0F - i * 38.0F;
             double blockPosX = this.getX() - Mth.sin((float) Math.toRadians(this.getYRot() - offset)) * 1.7D;
             double blockPosZ = this.getZ() + Mth.cos((float) Math.toRadians(this.getYRot() - offset)) * 1.7D;
             BlockPos pos = BlockPos.containing(blockPosX, this.getY() - 0.75D, blockPosZ);
             BlockPos above = pos.above();
             BlockState state = this.level().getBlockState(pos);
             BlockState aboveState = this.level().getBlockState(above);
             boolean replaceable = aboveState.canBeReplaced() || aboveState.is(BlockTags.REPLACEABLE) || aboveState.is(BlockTags.SMALL_FLOWERS) || aboveState.is(BlockTags.TALL_FLOWERS);
             if (replaceable && !aboveState.isAir()) this.level().destroyBlock(above, true);
             if (!replaceable) continue;
             UseOnContext context = new UseOnContext(player.level(), player, InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_HOE), new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
             BlockState modified = state.getToolModifiedState(context, ToolActions.HOE_TILL, false);
             if (modified != null) {
                 this.level().setBlock(pos, modified, 11);
                 this.level().levelEvent(2001, pos, Block.getId(state));
             }
         }
     }

     @Override
     public void tick() {
         super.tick();
         if (!this.level().isClientSide && this.steering.isBoosting()) {
             this.tickPlowing();
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isDancing() && !this.isInSitPoseTransition() && !this.isInEepyPoseTransition(), this.tickCount);
         this.plowAnimationState.animateWhen(this.steering.isBoosting() && this.hasControllingPassenger(), this.tickCount);
         this.danceAnimationState.animateWhen(this.isDancing(), this.tickCount);
     }

     @Override
     public void setupAnimationCooldowns() {
     }

     @Override
     public float getWalkAnimationSpeed() {
         return this.isBaby() ? 5.0F : 10.0F;
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             default -> super.handleEntityEvent(id);
         }
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (PLOW_TIME.equals(accessor) && this.level().isClientSide) {
             this.steering.onSynced();
         }
         super.onSyncedDataUpdated(accessor);
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(PLOW_TIME, 0);
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
         return UP2Entities.HIBBERTOPTERUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Hibbertopterus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.HIBBERTOPTERUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }
 }
