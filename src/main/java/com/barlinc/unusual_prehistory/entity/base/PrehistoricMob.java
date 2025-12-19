package com.barlinc.unusual_prehistory.entity.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricBodyRotationControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Criterion;
import com.barlinc.unusual_prehistory.registry.UP2Particles;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricMob extends Animal {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.LONG);
    public static final EntityDataAccessor<Byte> DATA_FLAGS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PACIFIED = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PACIFIED_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_EGG = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> RUNNING_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHOT_FROM_OOZE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> IDLE_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EAT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SIT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    public boolean useLowerFluidJumpThreshold = false;
    private int eepyTicks;

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricMoveControl(this);
        this.lookControl = new PrehistoricLookControl(this);
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    public void strongKnockback(Entity entity, double horizontalStrength, double verticalStrength) {
        double x = entity.getX() - this.getX();
        double y = entity.getZ() - this.getZ();
        double scale = Math.max(x * x + y * y, 0.001D);
        entity.push(x / scale * horizontalStrength, verticalStrength, y / scale * horizontalStrength);
    }

    // Navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new PrehistoricBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return 0.0F;
    }

    // Floating
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

    // Mating
    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        return false;
    }

    // Persistence
    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.requiresCustomPersistence();
    }

    // Mob interactions
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_EAT;
    }

    public boolean isPacifyItem(ItemStack itemStack) {
        return this.isFood(itemStack);
    }

    public boolean isPermanentPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PERMANENTLY_PACIFIES_MOB);
    }

    public int pacifiedTicks() {
        return 600 + this.getRandom().nextInt(600 * 8);
    }

    private void applyFoodEffects(ItemStack food, Level level, LivingEntity livingEntity) {
        Item item = food.getItem();
        if (item.isEdible()) {
            for (Pair<MobEffectInstance, Float> pair : food.getFoodProperties(this).getEffects()) {
                if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                    livingEntity.addEffect(new MobEffectInstance(pair.getFirst()));
                }
            }
        }
    }

    protected void feedItemToMob(Player player, InteractionHand hand, ItemStack itemstack) {
        this.usePlayerItem(player, hand, itemstack);
        this.playSound(this.getEatingSound(), 1.0F, this.getVoicePitch());
        this.applyFoodEffects(itemstack, this.level(), this);
        this.gameEvent(GameEvent.EAT);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack) && this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
            this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()), true);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.isPacifyItem(itemstack) && this.canPacify() && !this.isPacified() && !this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
            this.setPacified(true);
            this.setPacifiedTicks(this.pacifiedTicks());
            this.level().broadcastEntityEvent(this, (byte) 9);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.isPermanentPacifyItem(itemstack) && this.canPacify() && (!this.isPacified() || this.getPacifiedTicks() > 0) && !this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
            this.setPacified(true);
            this.setPacifiedTicks(-1);
            this.level().broadcastEntityEvent(this, (byte) 10);
            if (player instanceof ServerPlayer serverPlayer) UP2Criterion.PACIFY_MOB_PERMANENT.trigger(serverPlayer);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    // Entity events
    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 9 -> this.spawnPacifyParticles(false);
            case 10 -> this.spawnPacifyParticles(true);
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnPacifyParticles(boolean permanent) {
        ParticleOptions particleoptions = permanent ? UP2Particles.GOLDEN_HEART.get() : ParticleTypes.HEART;
        for(int i = 0; i < 7; i++) {
            double xspeed = this.random.nextGaussian() * 0.02D;
            double yspeed = this.random.nextGaussian() * 0.08D;
            double zspeed = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), xspeed, yspeed, zspeed);
        }
    }

    @Override
    public void tick () {
        super.tick();

        if (this.level().isClientSide) this.setupAnimationStates();
        this.setupAnimationCooldowns();

        if (this.canHealOverTime()) this.heal(2);

        if (this.level().isClientSide && this.shouldDoEepyParticles()) this.doEepyParticles(1.7F);

        if (this.isLeashed()) this.resetFallDistance();

        if (this.wasShotFromOoze()) {
            if (!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable()) this.resetFallDistance();
            else this.setShotFromOoze(false);
        }

        if (this.getEatCooldown() > 0 && this.canEat()) this.setEatCooldown(this.getEatCooldown() - 1);

        if (this.getPacifiedTicks() > 0) this.setPacifiedTicks(this.getPacifiedTicks() - 1);
        if (this.getPacifiedTicks() == 0 && this.isPacified()) this.setPacified(false);

        if (this.getLastHurtByMob() == null && this.getTarget() == null && !this.isInWaterOrBubble() && !this.isInRain()) {
            this.setSitCooldown(this.getSitCooldown() - 1);
        }

        if (this.isMobSitting() && this.isInWaterOrBubble()) {
            this.standUpInstantly();
            this.sitCooldown();
        }
        if (this.isInRain()) {
            this.standUp();
            this.sitCooldown();
        }
    }

    protected boolean isInRain() {
        return this.level().canSeeSky(this.blockPosition()) && (this.level().isThundering() || this.level().isRaining()) && this.level().getBiome(this.blockPosition()).get().hasPrecipitation();
    }

    public boolean canEat() {
        return true;
    }

    // Animation
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

    // Healing
    public int getHealCooldown() {
        return 200;
    }

    public boolean canHealOverTime() {
        return this.tickCount % this.getHealCooldown() == 0 && this.getHealth() < this.getMaxHealth() && !this.level().isClientSide && this.isAlive();
    }

    // Eepy particles
    public void doEepyParticles(float particleOffset) {
        Vec3 lookVec = new Vec3(0, 0, -this.getBbWidth() * particleOffset).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
        Vec3 eyeVec = this.getEyePosition().add(lookVec);
        if (this.eepyTicks == 0) {
            this.eepyTicks = 40 + random.nextInt(20);
            this.level().addParticle(UP2Particles.EEPY.get(), eyeVec.x, eyeVec.y + (1.0F - random.nextFloat()) * 0.3F, eyeVec.z, 1, 0, 0);
        }
        if (this.eepyTicks > 0) this.eepyTicks--;
    }

    public boolean shouldDoEepyParticles() {
        return false;
    }

    // Sitting
    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        if (this.isMobSitting()) this.standUpInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isMobSitting();
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F && this.isMobSitting() && !this.isInPoseTransition()) {
            this.standUp();
        }
    }

//    public boolean canLookWhileSitting() {
//        return true;
//    }

    public long getPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    public boolean refuseToMove() {
        return this.isInPoseTransition() || this.isMobSitting();
    }

    public boolean isMobSitting() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isMobVisuallySitting() {
        return this.getPoseTime() < 0L != this.isMobSitting();
    }

    public long getSitTransitionTime() {
        return 20L;
    }

    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < this.getSitTransitionTime();
    }

    public boolean isVisuallySitting() {
        return this.isMobSitting() && this.getPoseTime() < this.getSitTransitionTime() && this.getPoseTime() >= 0L;
    }

    public void sitDown() {
        if (this.isMobSitting()) return;
        this.setPose(UP2Poses.SITTING.get());
        this.setLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUp() {
        if (!this.isMobSitting()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.setLastPoseChangeTick((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void resetLastPoseChangeTickToFullStand(long l) {
        this.setLastPoseChangeTick(Math.max(0L, l - 52L - 1L));
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (DATA_POSE.equals(key)) {
           this.refreshDimensions();
        }
        if (SITTING.equals(key)) {
            this.refreshDimensions();
        }
    }

    // Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(BEHAVIOR, Behaviors.IDLE.getName());
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
        this.entityData.define(DATA_FLAGS, (byte) 0);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(PACIFIED, false);
        this.entityData.define(PACIFIED_TICKS, 0);
        this.entityData.define(FROM_EGG, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(RUNNING_TICKS, 0);
        this.entityData.define(SHOT_FROM_OOZE, false);
        this.entityData.define(IDLE_STATE, 0);
        this.entityData.define(EAT_COOLDOWN, 600 + this.getRandom().nextInt(600 * 4));
        this.entityData.define(SIT_COOLDOWN, 2000 + this.getRandom().nextInt(2000 * 2));
        this.entityData.define(SITTING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putLong("LastPoseTick", this.getLastPoseChangeTick());
        compoundTag.putBoolean("Pacified", this.isPacified());
        compoundTag.putInt("PacifiedTicks", this.getPacifiedTicks());
        compoundTag.putBoolean("FromEgg", this.isFromEgg());
        compoundTag.putInt("EatCooldown", this.getEatCooldown());
        compoundTag.putInt("SitCooldown", this.getSitCooldown());
        compoundTag.putBoolean("SittingDown", this.isSittingDown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        this.setLastPoseChangeTick(compoundTag.getLong("LastPoseTick"));
        this.setPacified(compoundTag.getBoolean("Pacified"));
        this.setPacifiedTicks(compoundTag.getInt("PacifiedTicks"));
        this.setFromEgg(compoundTag.getBoolean("FromEgg"));
        this.setEatCooldown(compoundTag.getInt("EatCooldown"));
        this.setSitCooldown(compoundTag.getInt("SitCooldown"));
        this.setSittingDown(compoundTag.getBoolean("SittingDown"));
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

    public int getIdleState() {
        return this.entityData.get(IDLE_STATE);
    }
    public void setIdleState(int idleState) {
        this.entityData.set(IDLE_STATE, idleState);
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

    public long getLastPoseChangeTick() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK);
    }
    public void setLastPoseChangeTick(long l) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, l);
    }

    public boolean isPacified() {
        return this.entityData.get(PACIFIED);
    }
    public void setPacified(boolean pacified) {
        this.entityData.set(PACIFIED, pacified);
    }
    public boolean canPacify() {
        return false;
    }

    public int getPacifiedTicks() {
        return this.entityData.get(PACIFIED_TICKS);
    }
    public void setPacifiedTicks(int ticks) {
        this.entityData.set(PACIFIED_TICKS, ticks);
    }

    public boolean isFromEgg() {
        return this.entityData.get(FROM_EGG);
    }
    public void setFromEgg(boolean fromEgg) {
        this.entityData.set(FROM_EGG, fromEgg);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }
    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public int getRunningTicks() {
        return this.entityData.get(RUNNING_TICKS);
    }
    public void setRunningTicks(int ticks) {
        this.entityData.set(RUNNING_TICKS, ticks);
    }

    public boolean wasShotFromOoze() {
        return this.entityData.get(SHOT_FROM_OOZE);
    }
    public void setShotFromOoze(boolean shotFromOoze) {
        this.entityData.set(SHOT_FROM_OOZE, shotFromOoze);
    }

    public int getEatCooldown() {
        return this.entityData.get(EAT_COOLDOWN);
    }
    public void setEatCooldown(int cooldown) {
        this.entityData.set(EAT_COOLDOWN, cooldown);
    }

    public int getSitCooldown() {
        return this.entityData.get(SIT_COOLDOWN);
    }
    public void setSitCooldown(int cooldown) {
        this.entityData.set(SIT_COOLDOWN, cooldown);
    }
    public void sitCooldown() {
        this.setSitCooldown(2500 + random.nextInt(2500 * 2));
    }
    public void standUpCooldown() {
        this.setSitCooldown(900 + random.nextInt(900 * 2));
    }

    public boolean isSittingDown() {
        return this.entityData.get(SITTING);
    }
    public void setSittingDown(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }
}
