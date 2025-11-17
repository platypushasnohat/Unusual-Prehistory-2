package com.unusualmodding.unusual_prehistory.entity.base;

import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.unusual_prehistory.entity.ai.navigation.*;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricMob extends Animal {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.LONG);
    public static final EntityDataAccessor<Byte> DATA_FLAGS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    public boolean useLowerFluidJumpThreshold = false;
    public int idleAnimationTimeout = 0;

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricMobMoveControl(this);
        this.lookControl = new RefuseToMoveLookControl(this);
        this.setPersistenceRequired();
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new RefuseToMoveBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return 0.0F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (useLowerFluidJumpThreshold) {
            return super.getFluidJumpThreshold();
        }
        return 0.6 * getBbHeight();
    }

    private void setUseLowerFluidJumpThreshold(boolean jumpThreshold) {
        this.useLowerFluidJumpThreshold = jumpThreshold;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (isInWater() && horizontalCollision) {
            this.setUseLowerFluidJumpThreshold(true);
        }
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean canMate(Animal animal) {
        return false;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_EAT;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            int i = this.getAge();
            if (this.isBaby()) {
                this.usePlayerItem(player, hand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                this.playSound(this.getEatingSound());
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    public void strongKnockback(Entity entity, double horizontalStrength, double verticalStrength) {
        double x = entity.getX() - this.getX();
        double y = entity.getZ() - this.getZ();
        double scale = Math.max(x * x + y * y, 0.001D);
        entity.push(x / scale * horizontalStrength, verticalStrength, y / scale * horizontalStrength);
    }

    @Override
    public void tick () {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        this.setupAnimationCooldowns();

        if (this.tickCount % this.getHealCooldown() == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }
    }

    public void setupAnimationCooldowns() {
    }

    public void setupAnimationStates() {
    }

    public int getHealCooldown() {
        return 100;
    }

    public boolean isInPoseTransition() {
        return false;
    }

    public long getPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    public boolean refuseToMove() {
        return this.isInPoseTransition();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(BEHAVIOR, Behaviors.IDLE.getName());
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
        this.entityData.define(DATA_FLAGS, (byte) 0);
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        long lastPoseTick = compoundTag.getLong("LastPoseTick");
        this.resetLastPoseChangeTick(lastPoseTick);
    }

    protected boolean getFlag(int flagId) {
        return (this.entityData.get(DATA_FLAGS) & flagId) != 0;
    }

    protected void setFlag(int flagId, boolean value) {
        byte b0 = this.entityData.get(DATA_FLAGS);
        if (value) {
            this.entityData.set(DATA_FLAGS, (byte) (b0 | flagId));
        } else {
            this.entityData.set(DATA_FLAGS, (byte) (b0 & ~flagId));
        }
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant, 0, this.getVariantCount()));
    }

    public int getVariantCount() {
        return 128;
    }

    public String getBehavior() {
        return this.entityData.get(BEHAVIOR);
    }

    public void setBehavior(String behavior) {
        this.entityData.set(BEHAVIOR, behavior);
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long l) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, l);
    }

    public void resetLastPoseChangeTickToFullStand(long l) {
        this.resetLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }
}
