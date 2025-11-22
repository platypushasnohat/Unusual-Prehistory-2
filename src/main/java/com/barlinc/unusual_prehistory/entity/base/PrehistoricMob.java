package com.barlinc.unusual_prehistory.entity.base;

import com.barlinc.unusual_prehistory.entity.ai.navigation.PrehistoricMobMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.RefuseToMoveBodyRotationControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.RefuseToMoveLookControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.registry.UP2Particles;
import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricMob extends Animal {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.LONG);
    public static final EntityDataAccessor<Byte> DATA_FLAGS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PACIFIED = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    public boolean useLowerFluidJumpThreshold = false;

    private final byte PACIFY = 9;

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

    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(Items.ENCHANTED_GOLDEN_APPLE);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            int i = this.getAge();
            if (this.isBaby()) {
                this.usePlayerItem(player, hand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                this.playSound(this.getEatingSound(), 1.0F, this.getVoicePitch());
                this.applyFoodEffects(itemstack, this.level(), this);
                this.gameEvent(GameEvent.EAT);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }
        else if (this.isPacifyItem(itemstack) && this.canPacifiy() && !this.isPacified() && !this.isBaby()) {
            this.usePlayerItem(player, hand, itemstack);
            this.setPacified(true);
            this.playSound(this.getEatingSound(), 1.0F, this.getVoicePitch());
            this.applyFoodEffects(itemstack, this.level(), this);
            this.gameEvent(GameEvent.EAT);
            this.level().broadcastEntityEvent(this, PACIFY);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    public void strongKnockback(Entity entity, double horizontalStrength, double verticalStrength) {
        double x = entity.getX() - this.getX();
        double y = entity.getZ() - this.getZ();
        double scale = Math.max(x * x + y * y, 0.001D);
        entity.push(x / scale * horizontalStrength, verticalStrength, y / scale * horizontalStrength);
    }

    private void applyFoodEffects(ItemStack food, Level level, LivingEntity livingEntity) {
        Item item = food.getItem();
        if (item.isEdible()) {
            for(Pair<MobEffectInstance, Float> pair : food.getFoodProperties(this).getEffects()) {
                if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                    livingEntity.addEffect(new MobEffectInstance(pair.getFirst()));
                }
            }
        }
    }

    protected void spawnPacifyParticles() {
        ParticleOptions particleoptions = UP2Particles.GOLDEN_HEART.get();
        for(int i = 0; i < 7; ++i) {
            double xspeed = this.random.nextGaussian() * 0.02D;
            double yspeed = this.random.nextGaussian() * 0.08D;
            double zspeed = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), xspeed, yspeed, zspeed);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == PACIFY) {
            this.spawnPacifyParticles();
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick () {
        super.tick();

        if (this.level().isClientSide()) this.setupAnimationStates();
        this.setupAnimationCooldowns();

        if (this.tickCount % this.getHealCooldown() == 0 && this.getHealth() < this.getMaxHealth()) this.heal(2);
    }

    public void setupAnimationCooldowns() {
    }

    public void setupAnimationStates() {
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * this.getWalkAnimationSpeed(), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 5.0F : 10.0F;
    }

    public int getHealCooldown() {
        return 250;
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
        this.entityData.define(PACIFIED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
        compoundTag.putBoolean("Pacified", this.isPacified());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        long lastPoseTick = compoundTag.getLong("LastPoseTick");
        this.resetLastPoseChangeTick(lastPoseTick);
        this.setPacified(compoundTag.getBoolean("Pacified"));
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

    public boolean isPacified() {
        return this.entityData.get(PACIFIED);
    }

    public void setPacified(boolean pacified) {
        this.entityData.set(PACIFIED, pacified);
    }

    public boolean canPacifiy() {
        return false;
    }
}
