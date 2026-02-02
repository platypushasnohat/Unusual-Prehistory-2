package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.KeybindUsingMount;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.network.MountedEntityKeyPacket;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Network;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Ulughbegsaurus extends PrehistoricMob implements KeybindUsingMount {

    private static final EntityDataAccessor<Boolean> RAINBOW = SynchedEntityData.defineId(Ulughbegsaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.35F, 1.25F);

    public int attackCooldown = 0;

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();

    private int attackTicks;

    public Ulughbegsaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.8D, 10, 4));
        this.goalSelector.addGoal(3, new UlughbegsaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(UP2ItemTags.ULUGHBEGSAURUS_FOOD), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new PrehistoricRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.ULUGHBEGSAURUS_TARGETS)));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new PrehistoricOwnerHurtTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_ULUGHBEGSAURUS);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.ULUGHBEGSAURUS_FOOD);
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
        if (!this.isRainbow() && itemstack.getItem() instanceof DyeItem) {
            UlughbegsaurusVariant variant = UlughbegsaurusVariant.byDye(itemstack.getItem());
            if (variant != null && variant.getId() != this.getVariant()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.setVariant(variant.getId());
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.playSound(SoundEvents.DYE_USE);
                return InteractionResult.SUCCESS;
            }
        }
        return type;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.SITTING.get() || (this.getCommand() == 1 && this.isMobSitting()) ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    // Riding
    @Override
    protected float getRiddenSpeed(@NotNull Player rider) {
        float sprintSpeed = rider.isSprinting() ? 0.1F : 0.0F;
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) + sprintSpeed;
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public Vec3 getRiderOffset() {
        return new Vec3(0.0F, 0.3F, 0.15F);
    }

    @Override
    public boolean canOwnerCommand(Player player) {
        return player.isShiftKeyDown() && (!(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof DyeItem) && !(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof DyeItem) || this.isRainbow());
    }

    @Override
    public boolean canOwnerMount(Player player) {
        return !this.isBaby() && (!(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof DyeItem) && !(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof DyeItem) || this.isRainbow());
    }

    @Override
    public void onKeyPacket(Entity keyPresser, int type) {
        if (keyPresser.isPassengerOfSameVehicle(this)) {
            if (type == 3) {
                if (this.getPose() == Pose.STANDING) {
                    this.setYHeadRot(keyPresser.getYHeadRot());
                    this.setXRot(keyPresser.getXRot());
                    this.setPose(UP2Poses.ATTACKING.get());
                }
            }
        }
    }

    private void tickPlayerBite() {
        if (this.attackCooldown == 0) {
            if (!level().isClientSide) {
                if (this.getPose() == UP2Poses.ATTACKING.get() && this.hasControllingPassenger()) {
                    if (attackTicks == 7)
                        this.level().playSound(null, this, UP2SoundEvents.ULUGHBEGSAURUS_ATTACK.get(), SoundSource.PLAYERS, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                    if (attackTicks <= 6 && attackTicks > 4) {
                        this.biteNearbyEntities(2.3D);
                        this.swing(InteractionHand.MAIN_HAND);
                    }
                }
            } else {
                Player player = UnusualPrehistory2.PROXY.getClientSidePlayer();
                if (player != null && player.isPassengerOfSameVehicle(this)) {
                    if (UnusualPrehistory2.PROXY.isKeyDown(3) && this.getPose() != UP2Poses.ATTACKING.get()) {
                        UP2Network.sendPacketToServer(new MountedEntityKeyPacket(this.getId(), player.getId(), 3));
                    }
                }
            }
        }
    }

    private void biteNearbyEntities(double radius) {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, this.getBoundingBox().inflate(radius));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!entity.is(this) && !this.isAlliedTo(entity)) {
                this.doHurtTarget(entity);
                this.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.tickPlayerBite();
    }

    @Override
    public void setupAnimationCooldowns() {
        if (attackTicks > 0) attackTicks--;
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
            this.attackCooldown = 2 + this.getRandom().nextInt(2);
            this.setPose(Pose.STANDING);
        }
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        if (this.attackTicks == 0 && (this.attack1AnimationState.isStarted() || this.attack2AnimationState.isStarted())) {
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
        }
        this.idleAnimationState.animateWhen(!this.isInWater(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWater(), this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.sitEndAnimationState.stop();
            this.attack1AnimationState.stop();
            this.attack2AnimationState.stop();
            this.idleAnimationState.stop();
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
            this.sitEndAnimationState.animateWhen(this.isInSitPoseTransition() && this.getSitPoseTime() >= 0L, this.tickCount);
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RAINBOW, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Rainbow", this.isRainbow());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setRainbow(compoundTag.getBoolean("Rainbow"));
    }

    public boolean isRainbow() {
        return this.entityData.get(RAINBOW);
    }

    public void setRainbow(boolean rainbow) {
        this.entityData.set(RAINBOW, rainbow);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Ulughbegsaurus ulughbegsaurus = UP2Entities.ULUGHBEGSAURUS.get().create(level);
        ulughbegsaurus.setVariant(this.getVariant());
        return ulughbegsaurus;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.ULUGHBEGSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ULUGHBEGSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ULUGHBEGSAURUS_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    public enum UlughbegsaurusVariant {
        WHITE(0, Items.WHITE_DYE),
        LIGHT_GRAY(1, Items.LIGHT_GRAY_DYE),
        GRAY(2, Items.GRAY_DYE),
        BLACK(3, Items.BLACK_DYE),
        BROWN(4, Items.BROWN_DYE),
        RED(5, Items.RED_DYE),
        ORANGE(6, Items.ORANGE_DYE),
        YELLOW(7, Items.YELLOW_DYE),
        LIME(8, Items.LIME_DYE),
        GREEN(9, Items.GREEN_DYE),
        CYAN(10, Items.CYAN_DYE),
        LIGHT_BLUE(11, Items.LIGHT_BLUE_DYE),
        BLUE(12, Items.BLUE_DYE),
        PURPLE(13, Items.PURPLE_DYE),
        MAGENTA(14, Items.MAGENTA_DYE),
        PINK(15, Items.PINK_DYE);

        private final int id;
        private final ItemLike dye;

        UlughbegsaurusVariant(int id, ItemLike dye) {
            this.id = id;
            this.dye = dye;
        }

        public int getId() {
            return this.id;
        }

        public ItemLike getDye() {
            return this.dye;
        }

        public static UlughbegsaurusVariant byDye(ItemLike item) {
            for (UlughbegsaurusVariant variant : values()) {
                if (variant.getDye() == item) {
                    return variant;
                }
            }
            return null;
        }

        public static UlughbegsaurusVariant byId(int id) {
            if (id < 0 || id >= UlughbegsaurusVariant.values().length) {
                id = 0;
            }
            return UlughbegsaurusVariant.values()[id];
        }
    }

    @Override
    public int getVariantCount() {
        return UlughbegsaurusVariant.values().length;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        int variantCount = this.getVariantCount();
        this.setVariant(level.getRandom().nextInt(variantCount));
        if (level.getRandom().nextFloat() < 0.01F) this.setRainbow(true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Ulughbegsaurus> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.ULUGHBEGSAURUS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }
}
