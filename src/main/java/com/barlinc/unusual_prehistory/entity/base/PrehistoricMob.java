package com.barlinc.unusual_prehistory.entity.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricBodyRotationControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Criterion;
import com.barlinc.unusual_prehistory.registry.UP2Particles;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricMob extends TamableAnimal {

    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> PACIFIED = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> PACIFIED_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> FROM_EGG = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHOT_FROM_OOZE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> RUNNING_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> IDLE_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> EAT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Long> SIT_POSE_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.LONG);
    protected static final EntityDataAccessor<Integer> SIT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Long> EEPY_POSE_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.LONG);
    protected static final EntityDataAccessor<Integer> EEPY_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    public boolean useLowerFluidJumpThreshold = false;
    protected int eepyTicks;

    public float prevEyeGlowProgress;
    public float eyeGlowProgress;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepEndAnimationState = new AnimationState();
    public final AnimationState sitStartAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitEndAnimationState = new AnimationState();

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
        return level.getPathfindingCostFromLightLevels(pos);
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
        if (this.isInWater() && horizontalCollision) {
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
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        InteractionResult type = super.mobInteract(player, hand);
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
        if (!interactionresult.consumesAction() && !type.consumesAction()) {
            if (this.isTame() && this.isOwnedBy(player) && !this.isFood(itemstack)) {
                if (this.canOwnerCommand(player)) {
                    this.setCommand(this.getCommand() + 1);
                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(Component.translatable("entity.unusual_prehistory.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 1;
                    this.setOrderedToSit(sit);
                    return InteractionResult.SUCCESS;
                } else if (this.canOwnerMount(player)) {
                    if (!level().isClientSide && player.startRiding(this)) {
                        return InteractionResult.CONSUME;
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return type;
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

        this.tickEyeGlow();

        if (this.level().isClientSide) this.setupAnimationStates();
        this.setupAnimationCooldowns();

        if (this.canHealOverTime()) this.heal(2);

        if (this.level().isClientSide && this.shouldDoEepyParticles()) this.doEepyParticles();

        if (this.isLeashed()) this.resetFallDistance();

        if (this.wasShotFromOoze()) {
            if (!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable()) this.resetFallDistance();
            else this.setShotFromOoze(false);
        }

        if (this.getEatCooldown() > 0 && this.canEat()) this.setEatCooldown(this.getEatCooldown() - 1);

        if (this.getPacifiedTicks() > 0) this.setPacifiedTicks(this.getPacifiedTicks() - 1);
        if (this.getPacifiedTicks() == 0 && this.isPacified()) this.setPacified(false);

        if (this.getLastHurtByMob() == null && this.getTarget() == null && !this.isInWaterOrBubble()) {
            if (this.getEepyCooldown() > 0) this.setEepyCooldown(this.getEepyCooldown() - 1);
            if (!this.isInRain() && this.getSitCooldown() > 0) this.setSitCooldown(this.getSitCooldown() - 1);
        }

        if (this.isMobSitting() && this.isInWaterOrBubble()) {
            this.stopSittingInstantly();
            this.setSitCooldown(6000 + random.nextInt(3000));
        }
        if (this.isInRain()) {
            this.stopSitting();
            this.setSitCooldown(6000 + random.nextInt(3000));
        }
    }

    protected boolean isInRain() {
        return this.level().canSeeSky(this.blockPosition()) && (this.level().isThundering() || this.level().isRaining()) && this.level().getBiome(this.blockPosition()).get().hasPrecipitation();
    }

    public boolean canEat() {
        return true;
    }

    // Eye glow
    public void tickEyeGlow() {
        this.prevEyeGlowProgress = eyeGlowProgress;
        long roundTime = this.level().getDayTime() % 24000;
        boolean night = roundTime >= 13000 && roundTime <= 22000;
        BlockPos pos = this.blockPosition();
        int skyLight = this.level().getBrightness(LightLayer.SKY, pos);
        int blockLight = this.level().getBrightness(LightLayer.BLOCK, pos);
        int brightness;
        if (night) brightness = blockLight;
        else brightness = Math.max(skyLight, blockLight);
        if (brightness < 7 && eyeGlowProgress < 10.0F) eyeGlowProgress++;
        else if (eyeGlowProgress > 0.0F) eyeGlowProgress--;
    }

    public float getEyeGlowProgress(float partialTicks) {
        return (prevEyeGlowProgress + (eyeGlowProgress - prevEyeGlowProgress) * partialTicks) * 0.1F;
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
        return this.isMobEepy() ? 100 : 200;
    }

    public boolean canHealOverTime() {
        return this.tickCount % this.getHealCooldown() == 0 && this.getHealth() < this.getMaxHealth() && !this.level().isClientSide && this.isAlive();
    }

    public boolean refuseToMove() {
        return this.isInSitPoseTransition() || this.isMobSitting() || this.isInSittingPose() || this.isInEepyPoseTransition() || this.isMobEepy();
    }

    // Sitting & Sleeping
    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        if (this.isMobSitting()) this.stopSittingInstantly();
        else if (this.isMobEepy()) this.stopEepyInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isMobSitting() || this.isMobEepy();
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F) {
            if (this.isMobSitting() && !this.isInSitPoseTransition()) this.stopSitting();
            if (this.isMobEepy() && !this.isInEepyPoseTransition()) this.stopEepy();
        }
    }

    // Sitting
    public long getSitPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.getSitPoseTicks());
    }

    public boolean isMobSitting() {
        return this.getSitPoseTicks() < 0L;
    }

    public boolean isMobVisuallySitting() {
        return this.getSitPoseTime() < 0L != this.isMobSitting();
    }

    public long getSitPoseTransitionTime() {
        return 20L;
    }

    public boolean isInSitPoseTransition() {
        long l = this.getSitPoseTime();
        return l < this.getSitPoseTransitionTime();
    }

    public boolean isVisuallySitting() {
        return this.isMobSitting() && this.getSitPoseTime() < this.getSitPoseTransitionTime() && this.getSitPoseTime() >= 0L;
    }

    public void startSitting() {
        if (this.isMobSitting()) return;
        this.setPose(UP2Poses.SITTING.get());
        this.setSitPoseTicks(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void stopSitting() {
        if (!this.isMobSitting()) return;
        this.setPose(Pose.STANDING);
        this.setSitPoseTicks((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void stopSittingInstantly() {
        this.setPose(Pose.STANDING);
        this.resetSitPoseTicks((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void resetSitPoseTicks(long l) {
        this.setSitPoseTicks(Math.max(0L, l - 52L - 1L));
    }

    // Sleeping
    public boolean isEepyTime() {
        return this.level().isNight();
    }

    public void doEepyParticles() {
        Vec3 lookVec = this.getLookVec();
        Vec3 eyeVec = this.getEyePosition().add(lookVec);
        if (this.eepyTicks == 0) {
            this.eepyTicks = 40 + random.nextInt(20);
            this.level().addParticle(UP2Particles.EEPY.get(), eyeVec.x, eyeVec.y + (1.0F - random.nextFloat()) * 0.3F, eyeVec.z, 1, 0, 0);
        }
        if (this.eepyTicks > 0) this.eepyTicks--;
    }

    public Vec3 getLookVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 1.7F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    public boolean shouldDoEepyParticles() {
        return this.isMobEepy();
    }

    public long getEepyPoseTime() {
        return (this.level()).getGameTime() - Math.abs(this.getEepyPoseTicks());
    }

    public boolean isMobEepy() {
        return this.getEepyPoseTicks() < 0L;
    }

    public boolean isMobVisuallyEepy() {
        return this.getEepyPoseTime() < 0L != this.isMobEepy();
    }

    public long getEepyPoseTransitionTime() {
        return 40L;
    }

    public boolean isInEepyPoseTransition() {
        long l = this.getEepyPoseTime();
        return l < this.getEepyPoseTransitionTime();
    }

    public boolean isVisuallyEepy() {
        return this.isMobEepy() && this.getEepyPoseTime() < this.getEepyPoseTransitionTime() && this.getEepyPoseTime() >= 0L;
    }

    public void startEepy() {
        if (this.isMobEepy()) return;
        this.setPose(UP2Poses.SLEEPING.get());
        this.setEepyPoseTicks(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void stopEepy() {
        if (!this.isMobEepy()) return;
        this.setPose(Pose.STANDING);
        this.setEepyPoseTicks((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void stopEepyInstantly() {
        this.setPose(Pose.STANDING);
        this.resetEepyPoseTicks((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void resetEepyPoseTicks(long l) {
        this.setEepyPoseTicks(Math.max(0L, l - 52L - 1L));
    }

    // Taming
    @Override
    public boolean isAlliedTo(@NotNull Entity entity) {
        if (this.isTame() && this.getOwner() != null) {
            if (entity == this.getOwner()) {
                return true;
            }
            if (entity instanceof TamableAnimal tamableAnimal) {
                return tamableAnimal.isOwnedBy(this.getOwner());
            }
            return this.getOwner().isAlliedTo(entity);
        }
        return super.isAlliedTo(entity);
    }

    public boolean canOwnerMount(Player player) {
        return false;
    }

    public boolean canOwnerCommand(Player ownerPlayer) {
        return false;
    }

    @Override
    public boolean isInSittingPose() {
        return super.isInSittingPose() && !(this.isVehicle() || this.isPassenger());
    }

    // Riding
    protected void clampRotation(LivingEntity livingEntity, float clampRange) {
        livingEntity.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(livingEntity.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -clampRange, clampRange);
        livingEntity.yRotO += f1 - f;
        livingEntity.yBodyRotO += f1 - f;
        livingEntity.setYRot(livingEntity.getYRot() + f1 - f);
        livingEntity.setYHeadRot(livingEntity.getYRot());
    }

    @Override
    protected void removePassenger(@NotNull Entity passenger) {
        super.removePassenger(passenger);
        if (!this.level().isClientSide) {
            if (this.getCommand() == 1 && !this.isMobSitting()) {
                this.startSitting();
            }
        }
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity livingEntity) {
        return new Vec3(this.getX(), this.getBoundingBox().minY, this.getZ());
    }

    @Override
    public void positionRider(@NotNull Entity passenger, @NotNull MoveFunction moveFunction) {
        if (this.isPassengerOfSameVehicle(passenger) && passenger instanceof LivingEntity livingEntity && !this.touchingUnloadedChunk()) {
            Vec3 seatOffset = this.getRiderOffset().yRot((float) Math.toRadians(-this.yBodyRot));
            passenger.setYBodyRot(this.yBodyRot);
            passenger.fallDistance = 0.0F;
            this.clampRotation(livingEntity, 105);
            moveFunction.accept(passenger, this.getX() + seatOffset.x, this.getY() + seatOffset.y + this.getPassengersRidingOffset(), this.getZ() + seatOffset.z);
        } else {
            super.positionRider(passenger, moveFunction);
        }
    }

    public Vec3 getRiderOffset() {
        return new Vec3(0.0F, 0.0F, 0.0F);
    }

    @Override
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Player player) return player;
        else return null;
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
        float xxa = player.xxa * 0.5F;
        float zza = player.zza;
        if (zza <= 0.0F) zza *= 0.25F;
        return new Vec3(xxa, 0.0F, zza);
    }

    @Override
    protected void tickRidden(@NotNull Player player, @NotNull Vec3 vec3) {
        super.tickRidden(player, vec3);
        if (player.zza > 0.0F) {
            if (this.isMobSitting() && !this.isInSitPoseTransition()) this.stopSitting();
            if (this.isMobEepy() && !this.isInEepyPoseTransition()) this.stopEepy();
        }
        if (player.zza != 0 || player.xxa != 0) {
            this.setRot(player.getYRot(), player.getXRot() * 0.25F);
            this.setYHeadRot(player.getYHeadRot());
            this.setTarget(null);
        }
    }

    // Sounds
    public boolean canPlayAmbientSound() {
        return !this.isMobEepy();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null && this.canPlayAmbientSound()) {
            this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
        }
    }

    // Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(PACIFIED, false);
        this.entityData.define(PACIFIED_TICKS, 0);
        this.entityData.define(FROM_EGG, false);
        this.entityData.define(SHOT_FROM_OOZE, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(RUNNING_TICKS, 0);
        this.entityData.define(IDLE_STATE, 0);
        this.entityData.define(EAT_COOLDOWN, 600 + random.nextInt(600 * 4));
        this.entityData.define(SIT_POSE_TICKS, 0L);
        this.entityData.define(SIT_COOLDOWN, 6000 + random.nextInt(3000));
        this.entityData.define(EEPY_POSE_TICKS, 0L);
        this.entityData.define(EEPY_COOLDOWN, 100);
        this.entityData.define(COMMAND, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putBoolean("Pacified", this.isPacified());
        compoundTag.putInt("PacifiedTicks", this.getPacifiedTicks());
        compoundTag.putBoolean("FromEgg", this.isFromEgg());
        compoundTag.putInt("EatCooldown", this.getEatCooldown());
        compoundTag.putLong("SitPoseTicks", this.getSitPoseTicks());
        compoundTag.putInt("SitCooldown", this.getSitCooldown());
        compoundTag.putLong("EepyPoseTicks", this.getEepyPoseTicks());
        compoundTag.putInt("EepyCooldown", this.getEepyCooldown());
        compoundTag.putInt("Command", this.getCommand());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        this.setPacified(compoundTag.getBoolean("Pacified"));
        this.setPacifiedTicks(compoundTag.getInt("PacifiedTicks"));
        this.setFromEgg(compoundTag.getBoolean("FromEgg"));
        this.setEatCooldown(compoundTag.getInt("EatCooldown"));
        this.setSitPoseTicks(compoundTag.getLong("SitPoseTicks"));
        this.setSitCooldown(compoundTag.getInt("SitCooldown"));
        this.setEepyPoseTicks(compoundTag.getLong("EepyPoseTicks"));
        this.setEepyCooldown(compoundTag.getInt("EepyCooldown"));
        this.setCommand(compoundTag.getInt("Command"));
    }

    // Idle and attack states
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

    // Variants
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant, 0, this.getVariantCount()));
    }
    public int getVariantCount() {
        return 128;
    }

    // Pacified
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

    // From egg
    public boolean isFromEgg() {
        return this.entityData.get(FROM_EGG);
    }
    public void setFromEgg(boolean fromEgg) {
        this.entityData.set(FROM_EGG, fromEgg);
    }

    // Was just shot from ooze
    public boolean wasShotFromOoze() {
        return this.entityData.get(SHOT_FROM_OOZE);
    }
    public void setShotFromOoze(boolean shotFromOoze) {
        this.entityData.set(SHOT_FROM_OOZE, shotFromOoze);
    }

    // Running
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

    // Eating
    public int getEatCooldown() {
        return this.entityData.get(EAT_COOLDOWN);
    }
    public void setEatCooldown(int cooldown) {
        this.entityData.set(EAT_COOLDOWN, cooldown);
    }

    // Sitting
    public long getSitPoseTicks() {
        return this.entityData.get(SIT_POSE_TICKS);
    }
    public void setSitPoseTicks(long l) {
        this.entityData.set(SIT_POSE_TICKS, l);
    }

    public int getSitCooldown() {
        return this.entityData.get(SIT_COOLDOWN);
    }
    public void setSitCooldown(int cooldown) {
        this.entityData.set(SIT_COOLDOWN, cooldown);
    }

    // Sleeping
    public long getEepyPoseTicks() {
        return this.entityData.get(EEPY_POSE_TICKS);
    }
    public void setEepyPoseTicks(long l) {
        this.entityData.set(EEPY_POSE_TICKS, l);
    }

    public int getEepyCooldown() {
        return this.entityData.get(EEPY_COOLDOWN);
    }
    public void setEepyCooldown(int cooldown) {
        this.entityData.set(EEPY_COOLDOWN, cooldown);
    }

    // Tame commands
    public int getCommand() {
        return this.entityData.get(COMMAND);
    }
    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }
}
