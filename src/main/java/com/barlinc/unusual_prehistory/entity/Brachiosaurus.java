 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.UnusualPrehistory2;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
 import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
 import com.barlinc.unusual_prehistory.entity.utils.KeybindUsingMount;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.events.ScreenShakeEvent;
 import com.barlinc.unusual_prehistory.network.MountedEntityKeyPacket;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Network;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.UP2Developers;
 import com.barlinc.unusual_prehistory.utils.UP2Math;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.particles.BlockParticleOption;
 import net.minecraft.core.particles.ParticleTypes;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
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
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.AABB;
 import net.minecraft.world.phys.Vec3;
 import net.minecraftforge.common.Tags;
 import net.minecraftforge.entity.PartEntity;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Brachiosaurus extends SemiAquaticMob implements KeybindUsingMount {

     private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(4.1F, 6.1F);

     private final BrachiosaurusPart[] allParts;
     public final BrachiosaurusPart headPart;
     public final BrachiosaurusPart neckPart1;
     public final BrachiosaurusPart neckPart2;
     public final BrachiosaurusPart tailPart1;
     public final BrachiosaurusPart tailPart2;

     public int attackCooldown = 0;
     public int stompCooldown = 100;

     private double lastStompX = 0;
     private double lastStompZ = 0;
     private float screenShakeAmount;

     protected float neckXRot;
     protected float neckYRot;

     private float[] yawBuffer = new float[128];
     private int yawPointer = -1;

     private int timer = 0;

     public final AnimationState idleAnimationState = new AnimationState();
     public final AnimationState sitStartAnimationState = new AnimationState();
     public final AnimationState sitAnimationState = new AnimationState();
     public final AnimationState sitEndAnimationState = new AnimationState();
     public final AnimationState attack1AnimationState = new AnimationState();
     public final AnimationState attack2AnimationState = new AnimationState();
     public final AnimationState tailWhip1AnimationState = new AnimationState();
     public final AnimationState tailWhip2AnimationState = new AnimationState();
     public final AnimationState stompAnimationState = new AnimationState();

     private int attackTicks;
     private int stompTicks;

     public Brachiosaurus(EntityType<? extends SemiAquaticMob> entityType, Level level) {
         super(entityType, level);
         this.refreshDimensions();
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0F);
         this.headPart = new BrachiosaurusPart(this, "head", 2.5F, 2.5F);
         this.neckPart1 = new BrachiosaurusPart(this, "neck1", 2.5F, 6.0F);
         this.neckPart2 = new BrachiosaurusPart(this, "neck2", 2.5F, 6.0F);
         this.tailPart1 = new BrachiosaurusPart(this, "tail1", 2.5F, 2.5F);
         this.tailPart2 = new BrachiosaurusPart(this, "tail2", 2.0F, 2.0F);
         this.allParts = new BrachiosaurusPart[]{headPart, neckPart1, neckPart2, tailPart1, tailPart2};
         this.setId(ENTITY_COUNTER.getAndAdd(allParts.length + 1) + 1);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 500.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.17F)
                 .add(Attributes.ATTACK_DAMAGE, 16.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                 .add(Attributes.FOLLOW_RANGE, 20.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this));
         this.goalSelector.addGoal(1, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(2, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(3, new BrachiosaurusAttackGoal(this));
         this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.BRACHIOSAURUS_FOOD), false));
         this.goalSelector.addGoal(5, new PrehistoricRandomStrollGoal(this, 1.0D, false));
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new RandomSitGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
     }

     @Override
     public void setId(int i1) {
         super.setId(i1);
         for (int i = 0; i < this.allParts.length; i++) {
             this.allParts[i].setId(i1 + i + 1);
         }
     }

     @Override
     protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
         return dimensions.height * 0.98F;
     }

     @Override
     protected float getWaterSlowDown() {
         return 0.9F;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
         SmoothGroundPathNavigation navigation = new SmoothGroundPathNavigation(this, level);
         navigation.setCanWalkOverFences(true);
         return navigation;
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if (this.refuseToMove() && this.onGround()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVec = travelVec.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater() && !this.onGround()) {
             this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.01D, 0.0D));
         }
         super.travel(travelVec);
     }

     @Override
     public float getStepHeight() {
         return 3.6F;
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.BRACHIOSAURUS_FOOD);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getIdleState() == 4 || this.getIdleState() == 5 || this.getPose() == UP2Poses.STOMPING.get() || this.getAttackState() == 2;
     }

     @Override
     public boolean canBeCollidedWith() {
         return !this.isAggressive();
     }

     @Override
     public boolean isPushable() {
         return false;
     }

     @Override
     public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
         return pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
     }

     @Override
     public long getSitTransitionTime() {
         return 40L;
     }

     @Override
     public void tick() {
         this.tickMultipart();
         super.tick();
         this.tickPlayerStomp();

         this.lastStompX = this.getX();
         this.lastStompZ = this.getZ();

         this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, this.getYRot(), 4);

         if (screenShakeAmount > 0) screenShakeAmount = Math.max(0, screenShakeAmount - 0.34F);
         if (this.onGround() && !this.isInFluidType() && this.walkAnimation.speed() > 0.1F && !this.isBaby()) {
             this.tickFootsteps();
         }
     }

     private void tickMultipart() {
         if (yawPointer == -1) {
             for (int i = 0; i < yawBuffer.length; i++) {
                 yawBuffer[i] = this.yBodyRot;
             }
         }
         if (++this.yawPointer == this.yawBuffer.length) {
             this.yawPointer = 0;
         }
         this.yawBuffer[this.yawPointer] = this.yBodyRot;

         Vec3[] avector3d = new Vec3[this.allParts.length];
         for (int j = 0; j < this.allParts.length; ++j) {
             avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
         }

         Vec3 center = this.position().add(0, this.getBbHeight(), 0);
         this.neckXRot = this.wrapNeckDegrees(Mth.approachDegrees(this.neckXRot, -30.0F, 45.0F));
         this.neckYRot = this.wrapNeckDegrees(Mth.approachDegrees(this.neckYRot, this.getTargetNeckYRot(), 45.0F));
         float headXStep = neckXRot / 4F;
         float headYStep = neckYRot / 4F;

         boolean isMoving = this.walkAnimation.speed() > 0.1F;

         float headAdditionalY = isMoving ? -0.8F : 0.0F;
         float headAdditionalZ = isMoving ? 3.0F : 0.0F;

         if (this.isMobSitting()) {
             headAdditionalZ = -2.0F;
         }

         float neck1AdditionalY = isMoving ? 0.8F : 0.0F;
         float neck1AdditionalZ = isMoving ? -1.0F : 0.0F;

         float neck2AdditionalY = isMoving ? 0.8F : 0.0F;
         float neck2AdditionalZ = isMoving ? -1.0F : 0.0F;

         this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 10.5F + headAdditionalY, 7.8F + headAdditionalZ).scale(this.getScale()), headXStep, (yBodyRot + headYStep)).add(center));
         this.neckPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -4.0F + neck1AdditionalY, -2.2F + neck1AdditionalZ).scale(this.getScale()), headXStep, (yBodyRot + headYStep)).add(this.headPart.centeredPosition()));
         this.neckPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -6.0F + neck2AdditionalY, -2.2F + neck2AdditionalZ).scale(this.getScale()), headXStep, (yBodyRot + headYStep)).add(this.neckPart1.centeredPosition()));

         this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -3.5F, -4.0F).scale(this.getScale()), headXStep, yBodyRot).add(center));
         this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -1.0F, -3.0F).scale(this.getScale()), headXStep, yBodyRot).add(this.tailPart1.centeredPosition()));

         for (int l = 0; l < this.allParts.length; ++l) {
             this.allParts[l].xo = avector3d[l].x;
             this.allParts[l].yo = avector3d[l].y;
             this.allParts[l].zo = avector3d[l].z;
             this.allParts[l].xOld = avector3d[l].x;
             this.allParts[l].yOld = avector3d[l].y;
             this.allParts[l].zOld = avector3d[l].z;
         }
     }

     private float wrapNeckDegrees(float f) {
         return f % 360.0F;
     }

     private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
         return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
     }

     public float getYawFromBuffer(int pointer, float partialTick) {
         int i = this.yawPointer - pointer & 127;
         int j = this.yawPointer - pointer - 1 & 127;
         float d0 = this.yawBuffer[j];
         float d1 = this.yawBuffer[i] - d0;
         return d0 + d1 * partialTick;
     }

     public float getTargetNeckYRot() {
         float buffered = this.getYawFromBuffer(10, 1.0F) - this.yBodyRot;
         return this.getYHeadRot() - this.yBodyRot + buffered;
     }

     @Override
     public boolean isMultipartEntity() {
         return true;
     }

     @Override
     public PartEntity<?>[] getParts() {
         return allParts;
     }

     private void tickFootsteps() {
         float walkPosition = (float) Math.cos(this.walkAnimation.position() * 0.515F - 1.5F);
         if (Math.abs(walkPosition) < 0.2F) {
             if (this.screenShakeAmount <= 0.3F) {
                 this.playSound(SoundEvents.CAMEL_STEP, 3.0F, 0.7F);
                 UnusualPrehistory2.PROXY.screenShake(new ScreenShakeEvent(this.position(), 10, 2.5F, 16, false));
             }
             this.screenShakeAmount = 1F;
         }
     }

     @Override
     public void calculateEntityAnimation(boolean flying) {
         float f1 = (float) Mth.length(this.getX() - this.lastStompX, 0, this.getZ() - this.lastStompZ);
         float f2 = Math.min(f1 * 4.0F, 1.0F);
         this.walkAnimation.update(f2, 0.4F);
     }

     @Override
     public int getHeadRotSpeed() {
         return 4;
     }

     @Override
     public void setupAnimationStates() {
         if (attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
         }
         if (stompTicks == 0 && this.stompAnimationState.isStarted()) this.stompAnimationState.stop();
         this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.STOMPING.get(), this.tickCount);

         if (this.isMobVisuallySitting()) {
             this.sitEndAnimationState.stop();
             this.idleAnimationState.stop();
             this.stompAnimationState.stop();
             this.attack1AnimationState.stop();
             this.attack2AnimationState.stop();
             this.tailWhip1AnimationState.stop();
             this.tailWhip2AnimationState.stop();

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
         if (stompTicks > 0) stompTicks--;
         if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
             this.attackCooldown = 6 + this.getRandom().nextInt(8);
             this.setPose(Pose.STANDING);
         }
         if (stompTicks == 0 && this.getPose() == UP2Poses.STOMPING.get()) {
             this.setPose(Pose.STANDING);
         }
         if (attackCooldown > 0) attackCooldown--;
         if (stompCooldown > 0) stompCooldown--;
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.ATTACKING.get()) {
                 if (this.getRandom().nextBoolean()) this.attack1AnimationState.start(this.tickCount);
                 else this.attack2AnimationState.start(this.tickCount);
                 this.attackTicks = 40;
             }
             else if (this.getPose() == UP2Poses.STOMPING.get()) {
                 this.stompAnimationState.start(this.tickCount);
                 this.stompTicks = 70;
             }
             else if (this.getPose() == Pose.STANDING) {
                 this.attack1AnimationState.stop();
                 this.attack2AnimationState.stop();
                 this.stompAnimationState.stop();
             }
         }
         super.onSyncedDataUpdated(accessor);
     }

     @Override
     public @NotNull AABB getBoundingBoxForCulling() {
         return this.getBoundingBox().inflate(4, 6, 4);
     }

     @Override
     public boolean shouldRenderAtSqrDistance(double distance) {
         return Math.sqrt(distance) < 1024.0D;
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             case 40 -> this.stompEffect();
             default -> super.handleEntityEvent(id);
         }
     }

     private void stompEffect() {
         Vec3 groundedVec = UP2Math.getGroundBelowPosition(level(), new Vec3(this.getRandomX(2.0D), this.getY() + 0.25F, this.getRandomZ(2.0D)));
         BlockPos ground = BlockPos.containing(groundedVec.subtract(0, 0.5F, 0));
         BlockState state = this.level().getBlockState(ground);
         for (int i = 0; i <= (this.getRandom().nextInt(60) + 80); i++) {
             this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), true, this.getRandomX(2.0D), this.getY() + 0.25F, this.getRandomZ(2.0D), 0.0D, 0.0D, 0.0D);
         }
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.BRACHIOSAURUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.BRACHIOSAURUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.BRACHIOSAURUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
     }

     @Override
     public float getSoundVolume() {
         return this.isBaby() ? 1.0F : 2.0F;
     }

     @Override
     public int getAmbientSoundInterval() {
         return 400;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.BRACHIOSAURUS.get().create(level);
     }

     public static boolean canSpawn(EntityType<Brachiosaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
         return level.getBlockState(pos.below()).is(UP2BlockTags.BRACHIOSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
     }

     // Dev taming stuff
     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);

         if (!this.isTame() && (itemstack.is(Items.DEBUG_STICK) && UP2Developers.isDeveloper(player.getUUID())) || (itemstack.is(Tags.Items.GEMS_DIAMOND) && player.getUUID().equals(UP2Developers.MAGMASTRIDER.getUuid()))) {
             if (!player.getAbilities().instabuild) {
                 itemstack.shrink(1);
             }
             this.gameEvent(GameEvent.ENTITY_INTERACT);
             this.tame(player);
             this.level().broadcastEntityEvent(this, (byte) 9);
             this.heal(this.getMaxHealth());
             return InteractionResult.SUCCESS;
         }
         return type;
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
     protected float getRiddenSpeed(@NotNull Player rider) {
         float sprintSpeed = rider.isSprinting() ? 0.05F : 0.0F;
         return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F + sprintSpeed;
     }

     @Override
     public boolean canSprint() {
         return true;
     }

     @Override
     public void positionRider(@NotNull Entity passenger, @NotNull MoveFunction moveFunction) {
         if (this.isPassengerOfSameVehicle(passenger) && passenger instanceof LivingEntity living && !this.touchingUnloadedChunk()) {
             float seatY = 14.5F;
             float seatZ = 6.5F;
             Vec3 seatOffset = new Vec3(0F, seatY, seatZ).yRot((float) Math.toRadians(-this.yBodyRot));
             passenger.setYBodyRot(this.yBodyRot);
             passenger.fallDistance = 0.0F;
             clampRotation(living, 105);
             moveFunction.accept(passenger, this.getX() + seatOffset.x, this.getY() + seatOffset.y + this.getPassengersRidingOffset(), this.getZ() + seatOffset.z);
         } else {
             super.positionRider(passenger, moveFunction);
         }
     }

     protected void tickPlayerStomp() {
         if (this.stompCooldown == 0 && !this.isMobSitting()) {
             if (!level().isClientSide) {
                 if (this.getAttackState() == 2 && this.hasControllingPassenger()) {
                     this.timer++;
                     if (timer == 7) this.playSound(UP2SoundEvents.BRACHIOSAURUS_STOMP.get(), 2.5F, 1.0F);
                     if (timer == 10) this.setPose(UP2Poses.STOMPING.get());
                     if (timer == 48) {
                         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(6.0D, -0.5D, 6.0D))) {
                             if (entity == this) {
                                 continue;
                             }
                             entity.hurt(this.damageSources().mobAttack(this), (float) (this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F));
                             this.strongKnockback(entity, 9.0D, 0.55D);
                         }
                         UnusualPrehistory2.PROXY.screenShake(new ScreenShakeEvent(this.position(), 40, 4.0F, 24, false));
                         this.level().broadcastEntityEvent(this, (byte) 40);
                     }
                     if (this.timer > 80) {
                         this.timer = 0;
                         this.stompCooldown = 150 + this.getRandom().nextInt(100);
                         this.setAttackState(0);
                     }
                 }
             } else {
                 Player player = UnusualPrehistory2.PROXY.getClientSidePlayer();
                 if (player != null && player.isPassengerOfSameVehicle(this)) {
                     if (UnusualPrehistory2.PROXY.isKeyDown(3) && this.getPose() != UP2Poses.STOMPING.get() && this.getAttackState() == 0) {
                         UP2Network.sendPacketToServer(new MountedEntityKeyPacket(this.getId(), player.getId(), 3));
                     }
                 }
             }
         }
     }

     @Override
     public void onKeyPacket(Entity keyPresser, int type) {
         if (keyPresser.isPassengerOfSameVehicle(this)) {
             if (type == 3) {
                 if (this.getPose() == Pose.STANDING && this.getAttackState() == 0 && this.stompCooldown == 0) {
                     this.setAttackState(2);
                 }
             }
         }
     }
 }
