package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.FlyingPathNavigationNoSpin;
import com.unusualmodding.unusual_prehistory.entity.base.FlyingPrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Kimmeridgebrachypteraeschnidium extends FlyingPrehistoricMob implements Bucketable {

    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WING_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> PREEN_COOLDOWN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState wingsAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState preenAnimationState = new AnimationState();

    public Kimmeridgebrachypteraeschnidium(EntityType<? extends FlyingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveController();
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new FlyingPathNavigationNoSpin(this, level, 0.75F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.FLYING_SPEED, 1.7F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KimmeridgebrachypteraeschnidiumFlyGoal(this));
        this.goalSelector.addGoal(3, new KimmeridgebrachypteraeschnidiumLookAroundGoal(this));
        this.goalSelector.addGoal(4, new KimmeridgebrachypteraeschnidiumPreenGoal(this));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    @Override
    public void setupAnimationCooldowns() {
        if (this.getPreenCooldown() > 0) {
            this.setPreenCooldown(this.getPreenCooldown() - 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen((this.onGround() || this.isInWaterOrBubble()) && !this.isFlying(), this.tickCount);
        this.preenAnimationState.animateWhen(this.onGround() && this.getPreenCooldown() == 0, this.tickCount);
        if (this.getPose() == UP2Poses.PREENING.get()) {
            this.idleAnimationState.stop();
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {

            playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.5F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());

            this.setBucketData(itemstack1);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
            }
            if (heldItem.isEmpty() && !player.isCreative()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    public boolean refuseToMove() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(BASE_COLOR, 0);
        this.entityData.define(PATTERN, 0);
        this.entityData.define(PATTERN_COLOR, 0);
        this.entityData.define(HAS_PATTERN, false);
        this.entityData.define(WING_COLOR, 0);
        this.entityData.define(PREEN_COOLDOWN, 2 * 20 + random.nextInt(10 * 20));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("PreenCooldown", this.getPreenCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBaseColor(compoundTag.getInt("BaseColor"));
        this.setPattern(compoundTag.getInt("Pattern"));
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setWingColor(compoundTag.getInt("WingColor"));
        this.setHasPattern(compoundTag.getBoolean("HasPattern"));
        this.setPreenCooldown(compoundTag.getInt("PreenCooldown"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            Pose entityPose = this.getPose();
            if (entityPose == Pose.FALL_FLYING) {
                this.idleAnimationState.stop();
                this.preenAnimationState.stop();
                this.flyingAnimationState.start(this.tickCount);
                this.wingsAnimationState.start(this.tickCount);
            } else {
                this.flyingAnimationState.stop();
                this.wingsAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundTag = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundTag.putFloat("Health", this.getHealth());
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("Age", this.getAge());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    private void setBucketData(ItemStack bucket) {
        CompoundTag compoundTag = bucket.getOrCreateTag();
        compoundTag.putFloat("Health", this.getHealth());
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putBoolean("HasPattern", this.getHasPattern());
        compoundTag.putInt("WingColor", this.getWingColor());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    public static String getPatternName(int pattern) {
        return switch (pattern){
            case 1 -> "tailshade";
            case 2 -> "topshade";
            case 3 -> "halfshade";
            case 4 -> "large_stripe";
            case 5 -> "racing_stripe";
            case 6 -> "large_racing_stripe";
            default -> "stripe";
        };
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnType == MobSpawnType.BUCKET && dataTag != null && dataTag.contains("BaseColor", 3)) {
            this.setBaseColor(dataTag.getInt("BaseColor"));
            this.setPattern(dataTag.getInt("Pattern"));
            this.setPatternColor(dataTag.getInt("PatternColor"));
            this.setHasPattern(dataTag.getBoolean("HasPattern"));
            this.setWingColor(dataTag.getInt("WingColor"));
        } else {
            this.setBaseColor(this.random.nextInt(16));
            this.setPattern(this.random.nextInt(7));
            this.setPatternColor(this.random.nextInt(16));
            this.setHasPattern(this.random.nextInt(3)==0);
            this.setWingColor(this.random.nextInt(16));
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BOTTLE_FILL_DRAGONBREATH;
    }

    public int getBaseColor() {
        return this.entityData.get(BASE_COLOR);
    }

    public void setBaseColor(int variant) {
        this.entityData.set(BASE_COLOR, variant);
    }

    public int getPattern() {
        return this.entityData.get(PATTERN);
    }

    public void setPattern(int variant) {
        this.entityData.set(PATTERN, variant);
    }

    public int getPatternColor() {
        return this.entityData.get(PATTERN_COLOR);
    }

    public void setPatternColor(int variant) {
        this.entityData.set(PATTERN_COLOR, variant);
    }

    public int getWingColor() {
        return this.entityData.get(WING_COLOR);
    }

    public void setWingColor(int variant) {
        this.entityData.set(WING_COLOR, variant);
    }

    public Boolean getHasPattern() {
        return this.entityData.get(HAS_PATTERN);
    }

    public void setHasPattern(Boolean variant) {
        this.entityData.set(HAS_PATTERN, variant);
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

    public int getPreenCooldown() {
        return this.entityData.get(PREEN_COOLDOWN);
    }

    public void setPreenCooldown(int cooldown) {
        this.entityData.set(PREEN_COOLDOWN, cooldown);
    }

    public void preenCooldown() {
        this.entityData.set(PREEN_COOLDOWN, 6 * 20 + random.nextInt(30 * 2 * 20));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || super.isInvulnerableTo(source);
    }

    @Override
    @NotNull
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return null;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3, 3, 3);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }
}
