 package com.barlinc.unusual_prehistory.entity;

 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.network.ManipulatorOpenInventoryPacket;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Network;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.screens.ManipulatorContainer;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.Direction;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.nbt.ListTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.*;
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
 import net.minecraft.world.item.enchantment.EnchantmentHelper;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.phys.Vec3;
 import net.minecraftforge.common.MinecraftForge;
 import net.minecraftforge.common.capabilities.Capability;
 import net.minecraftforge.common.capabilities.ForgeCapabilities;
 import net.minecraftforge.common.util.LazyOptional;
 import net.minecraftforge.event.entity.player.PlayerContainerEvent;
 import net.minecraftforge.items.wrapper.InvWrapper;
 import net.minecraftforge.network.PacketDistributor;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Manipulator extends PrehistoricMob implements ContainerListener {

     private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Manipulator.class, EntityDataSerializers.INT);

     public final SimpleContainer manipulatorInventory = new SimpleContainer(2);
     private LazyOptional<?> itemHandler;
     public boolean interacting;

     public int attackCooldown = 0;

     public final AnimationState idleArmedAnimationState = new AnimationState();
     public final AnimationState sitArmedAnimationState = new AnimationState();
     public final AnimationState attackAnimationState = new AnimationState();
     public final AnimationState attackArmedAnimationState = new AnimationState();

     private int attackTicks;

     public Manipulator(EntityType<? extends PrehistoricMob> entityType, Level level) {
         super(entityType, level);
         this.manipulatorInventory.addListener(this);
         this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.manipulatorInventory));
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
         return super.refuseToMove() || this.interacting;
     }

     @Override
     public boolean canOwnerCommand(Player player) {
         return player.isShiftKeyDown();
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
         if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_MANIPULATOR)) {
             if (!this.level().isClientSide) {
                 if (!player.getAbilities().instabuild) {
                     itemstack.shrink(1);
                 }
                 if (this.getTameAttempts() > 2 && this.getRandom().nextBoolean()) {
                     this.level().broadcastEntityEvent(this, (byte) 7);
                     this.tame(player);
                     this.setPacified(true);
                     this.heal(this.getMaxHealth());
                 } else {
                     this.level().broadcastEntityEvent(this, (byte) 6);
                     this.setTameAttempts(this.getTameAttempts() + 1);
                 }
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         if (this.isTame() && !player.isShiftKeyDown()) {
             if (player instanceof ServerPlayer serverPlayer) {
                 this.openGui(serverPlayer);
                 return InteractionResult.SUCCESS;
             }
         }
         return type;
     }

     public boolean isHoldingItem() {
         return !this.getMainHandItem().isEmpty() || !this.getOffhandItem().isEmpty();
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
     protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHitIn) {
         for (int i = 0; i < this.manipulatorInventory.getContainerSize(); i++) {
             ItemStack itemstack = this.manipulatorInventory.getItem(i);
             if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                 this.spawnAtLocation(itemstack);
             }
         }
     }

     @Override
     public @NotNull ItemStack getItemBySlot(EquipmentSlot slot) {
         return switch (slot) {
             case MAINHAND -> this.manipulatorInventory.getItem(0);
             case OFFHAND -> this.manipulatorInventory.getItem(1);
             default -> ItemStack.EMPTY;
         };
     }

     @Override
     public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
         super.setItemSlot(slot, stack);
         switch (slot) {
             case MAINHAND:
                 this.manipulatorInventory.setItem(0, this.handItems.get(slot.getIndex()));
                 break;
             case OFFHAND:
                 this.manipulatorInventory.setItem(1, this.handItems.get(slot.getIndex()));
                 break;
         }
     }

     @Override
     protected void populateDefaultEquipmentSlots(@NotNull RandomSource source, @NotNull DifficultyInstance instance) {
         this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 100.0F;
         this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 100.0F;
     }

     @Override
     public void containerChanged(@NotNull Container container) {
     }

     public void openGui(ServerPlayer player) {
         if (player.containerMenu != player.inventoryMenu) {
             player.closeContainer();
         }
         this.interacting = true;
         player.nextContainerCounter();
         UP2Network.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ManipulatorOpenInventoryPacket(player.containerCounter, this.manipulatorInventory.getContainerSize(), this.getId()));
         player.containerMenu = new ManipulatorContainer(player.containerCounter, player.getInventory(), this.manipulatorInventory, this);
         player.initMenu(player.containerMenu);
         MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, player.containerMenu));
     }

     @Override
     public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
         if (this.isAlive() && capability == ForgeCapabilities.ITEM_HANDLER && itemHandler != null) return itemHandler.cast();
         return super.getCapability(capability, facing);
     }

     @Override
     public void invalidateCaps() {
         super.invalidateCaps();
         if (itemHandler != null) {
             LazyOptional<?> oldHandler = itemHandler;
             this.itemHandler = null;
             oldHandler.invalidate();
         }
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
         compoundTag.putBoolean("Interacting", this.interacting);
         ListTag list = new ListTag();
         for (int i = 0; i < this.manipulatorInventory.getContainerSize(); i++) {
             ItemStack itemstack = this.manipulatorInventory.getItem(i);
             if (!itemstack.isEmpty()) {
                 CompoundTag tag = new CompoundTag();
                 tag.putByte("Slot", (byte) i);
                 itemstack.save(tag);
                 list.add(tag);
             }
         }
         compoundTag.put("Inventory", list);
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setTameAttempts(compoundTag.getInt("TameAttempts"));
         this.interacting = compoundTag.getBoolean("Interacting");
         ListTag list = compoundTag.getList("Inventory", 9);
         for (int i = 0; i < list.size(); i++) {
             CompoundTag compoundnbt = list.getCompound(i);
             int j = compoundnbt.getByte("Slot") & 255;
             this.manipulatorInventory.setItem(j, ItemStack.of(compoundnbt));
         }
         if (compoundTag.contains("HandItems", 9)) {
             ListTag handItems = compoundTag.getList("HandItems", 10);
             for (int i = 0; i < this.handItems.size(); i++) {
                 this.manipulatorInventory.setItem(i, ItemStack.of(handItems.getCompound(i)));
             }
         }
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
