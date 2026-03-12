package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricBodyRotationControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.NoSpinGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.utils.JukeboxListener;
import com.barlinc.unusual_prehistory.registry.UP2Criterion;
import com.barlinc.unusual_prehistory.registry.UP2Particles;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public abstract class PrehistoricMob extends TamableAnimal {

    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> PACIFIED_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> FROM_EGG = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHOT_FROM_OOZE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> IDLE_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> EAT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> FOREVER_BABY = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> SIT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> EEPY_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    protected int eepyTicks;

    private BlockPos jukeboxPosition;
    private final DynamicGameEventListener<JukeboxListener> dynamicJukeboxListener;

    private float tailYaw;
    private float prevTailYaw;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eepyStartAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eepyAnimationState = new SmoothAnimationState(0.15F);
    public final SmoothAnimationState eepyEndAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sitStartAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sitAnimationState = new SmoothAnimationState(0.25F);
    public final SmoothAnimationState sitEndAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState danceAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricMoveControl(this);
        this.lookControl = new PrehistoricLookControl(this);
        PositionSource source = new EntityPositionSource(this, this.getEyeHeight());
        this.dynamicJukeboxListener = new DynamicGameEventListener<>(new JukeboxListener(this, source, GameEvent.JUKEBOX_PLAY.getNotificationRadius()));
        this.tailYaw = this.getYRot();
        this.prevTailYaw = this.getYRot();
        this.setPersistenceRequired();
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    public void strongKnockback(Entity entity, double horizontalStrength, double verticalStrength) {
        double x = entity.getX() - this.getX();
        double y = entity.getZ() - this.getZ();
        double scale = Math.max(x * x + y * y, 0.001D);
        entity.push(x / scale * horizontalStrength, verticalStrength, y / scale * horizontalStrength);
    }

    @Override
    public boolean isPushable() {
        return !this.isEepy();
    }

    // Jukebox detection
    @Override
    public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> consumer) {
        if (this.canDanceToJukebox()) {
            if (this.level() instanceof ServerLevel serverlevel) {
                consumer.accept(this.dynamicJukeboxListener, serverlevel);
            }
        }
    }

    public void danceToJukebox(BlockPos pos, boolean dancing) {
        if (dancing) {
            if (!this.isDancing()) {
                this.jukeboxPosition = pos;
                this.setDancing(true);
            }
        } else if (pos.equals(jukeboxPosition)) {
            this.jukeboxPosition = null;
            this.setDancing(false);
        }
    }

    public boolean shouldStopDancing() {
        return this.getLastHurtByMob() != null || this.getTarget() != null || this.hasControllingPassenger() || jukeboxPosition == null || !jukeboxPosition.closerToCenterThan(position(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()) || !level().getBlockState(jukeboxPosition).is(Blocks.JUKEBOX);
    }

    public boolean canDanceToJukebox() {
        return false;
    }

    // Navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new PrehistoricBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new NoSpinGroundPathNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return 0.0F;
    }

    // Floating
    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.6D * this.getBbHeight();
    }

    protected void floatInWaterWhileRidden() {
        if (this.isVehicle() && this.getFluidHeight(FluidTags.WATER) > this.getFluidJumpThreshold()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.04F, 0.0));
        }
    }

    public void floatWhileRidden(Vec3 travelVec) {
        if (this.isInWater() || (this.isInFluidType(this.getEyeInFluidType()) && !this.moveInFluid(this.level().getFluidState(BlockPos.containing(this.getEyePosition())), travelVec, this.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get())))) {
            this.floatInWaterWhileRidden();
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

    public boolean isForeverBabyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.STOPS_MOB_AGING);
    }

    public boolean isPacifyItem(ItemStack itemStack) {
        return this.isFood(itemStack);
    }

    public boolean isPermanentPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PERMANENTLY_PACIFIES_MOB);
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
        InteractionResult type = super.mobInteract(player, hand);
        if (this.isFood(itemstack) && this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
            this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()), true);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.isForeverBabyItem(itemstack) && this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
            this.setForeverBaby(true);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (!type.consumesAction()) {
            return this.interactTameCommands(player, hand);
        }
        if (this.canPacify()) {
            return this.interactPacify(player, hand);
        }
        return type;
    }

    public InteractionResult interactTameCommands(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isTame() && this.isOwnedBy(player) && !this.isFood(itemstack)) {
            if (this.canOwnerCommand(player)) {
                this.setCommand(this.getCommand() + 1);
                if (this.getCommand() == 3) {
                    this.setCommand(0);
                }
                player.displayClientMessage(Component.translatable("entity.unusual_prehistory.all.command_" + this.getCommand(), this.getName()), true);
                this.setOrderedToSit(this.getCommand() == 1);
                return InteractionResult.SUCCESS;
            } else if (this.canOwnerMount(player)) {
                if (!level().isClientSide && player.startRiding(this)) {
                    return InteractionResult.CONSUME;
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public InteractionResult interactPacify(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isPacifyItem(itemstack) && !this.isPacified() && !this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
            this.setPacifiedTicks(this.getPacifiedTicks() + this.pacifiedTicks());
            this.level().broadcastEntityEvent(this, (byte) 9);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.isPermanentPacifyItem(itemstack) && this.getPacifiedTicks() >= 0 && !this.isBaby()) {
            this.feedItemToMob(player, hand, itemstack);
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

        this.tickTailYaw();
        this.tickCooldowns();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
            if (this.shouldDoEepyParticles()) {
                this.doEepyParticles();
            }
        } else {
            if (this.canHealOverTime()) {
                this.heal(2);
            }
            if (tickCount % 20 == 0 && this.shouldStopDancing() && this.isDancing()) {
                this.setDancing(false);
                this.jukeboxPosition = null;
            }
        }

        if (this.isForeverBaby() && this.isBaby()) {
            this.setAge(-24000);
        }

        if (this.isLeashed()) {
            this.resetFallDistance();
        }

        if (this.wasShotFromOoze()) {
            if (!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable()) this.resetFallDistance();
            else this.setShotFromOoze(false);
        }

        if (this.getPacifiedTicks() > 0) {
            this.setPacifiedTicks(this.getPacifiedTicks() - 1);
        }
    }

    public void tickCooldowns() {
        if (this.getEatCooldown() > 0 && this.canEat()) {
            this.setEatCooldown(this.getEatCooldown() - 1);
        }
        if (this.getLastHurtByMob() == null && this.getTarget() == null && !this.isInWaterOrBubble() && !this.isBaby()) {
            if (this.getEepyCooldown() > 0) this.setEepyCooldown(this.getEepyCooldown() - 1);
            if (!this.isEepy() && this.getSitCooldown() > 0) this.setSitCooldown(this.getSitCooldown() - 1);
        }
    }

    public boolean canEat() {
        return true;
    }

    // Tail yaw
    public void tickTailYaw() {
        this.prevTailYaw = tailYaw;
        this.tailYaw = Mth.approachDegrees(this.tailYaw, yBodyRot, 8);
    }

    public float getTailYaw(float partialTick) {
        return (prevTailYaw + (tailYaw - prevTailYaw) * partialTick);
    }

    // Animation
    public void setupAnimationStates() {
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * this.getWalkAnimationSpeed(), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 3.0F : 10.0F;
    }

    // Healing
    public int getHealCooldown() {
        return this.isEepy() ? 100 : 200;
    }

    public boolean canHealOverTime() {
        return this.tickCount % this.getHealCooldown() == 0 && this.getHealth() < this.getMaxHealth() && this.isAlive();
    }

    public boolean refuseToMove() {
        return this.isEepy() || this.isSitting();
    }

    public boolean refuseToLook() {
        return this.isEepy() || this.isSitting();
    }

    // Sitting & Sleeping
    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        if (this.isSitting() && !this.isOrderedToSit()) {
            this.setSitting(false);
            this.setSitCooldown(400);
        }
        else if (this.isEepy()) {
            this.setEepy(false);
            this.setEatCooldown(400);
        }
        super.actuallyHurt(source, amount);
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F) {
            if (this.isSitting() && !this.isOrderedToSit()) {
                this.setSitting(false);
                this.setSitCooldown(this.getSitCooldown() + 200);
            }
            if (this.isEepy()) {
                this.setEepy(false);
                this.setEepyCooldown(this.getEepyCooldown() + 200);
            }
        }
    }

    public boolean isEepyTime() {
        return this.level().isNight();
    }

    public void doEepyParticles() {
        Vec3 lookVec = this.getEepyParticleVec();
        Vec3 eyeVec = this.getEyePosition().add(lookVec);
        if (this.eepyTicks == 0) {
            this.eepyTicks = 40 + random.nextInt(20);
            this.level().addParticle(UP2Particles.EEPY.get(), eyeVec.x, eyeVec.y + (1.0F - random.nextFloat()) * 0.3F, eyeVec.z, 1, 0, 0);
        }
        if (this.eepyTicks > 0) this.eepyTicks--;
    }

    public Vec3 getEepyParticleVec() {
        return new Vec3(0, 0, -this.getBbWidth() * 1.7F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
    }

    public boolean shouldDoEepyParticles() {
        return this.isEepy();
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
            if (this.getCommand() == 1 && !this.isSitting()) {
                this.setSitting(true);
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
            if (this.isSitting()) {
                this.setSitting(false);
            }
            if (this.isEepy()) {
                this.setEepy(false);
            }
        }
        if (player.zza != 0 || player.xxa != 0) {
            this.setRot(player.getYRot(), player.getXRot() * 0.25F);
            this.setYHeadRot(player.getYHeadRot());
            this.setTarget(null);
        }
    }

    // Prevent rider from taking fall damage
    @Override
    public boolean causeFallDamage(float f1, float f2, @NotNull DamageSource source) {
        float[] ret = ForgeHooks.onLivingFall(this, f1, f2);
        if (ret == null) return false;
        f1 = ret[0];
        f2 = ret[1];

        boolean flag = causeInternalFallDamage(f1, f2, source);
        int i = this.calculateFallDamage(f1, f2);
        if (i > 0) {
            this.playSound(i > 4 ? this.getFallSounds().big() : this.getFallSounds().small(), 1.0F, 1.0F);
            this.playBlockFallSound();
            this.hurt(source, (float)i);
            return true;
        } else {
            return flag;
        }
    }

    private boolean causeInternalFallDamage(float f1, float f2, DamageSource damageSource) {
        float[] livingFall = ForgeHooks.onLivingFall(this, f1, f2);
        if (livingFall == null) return false;
        f1 = livingFall[0];
        f2 = livingFall[1];

        int i = this.calculateFallDamage(f1, f2);
        if (i > 0) {
            this.playBlockFallSound();
            this.hurt(damageSource, (float) i);
            return true;
        } else {
            return this.getType().is(EntityTypeTags.FALL_DAMAGE_IMMUNE);
        }
    }

    // Sounds
    public boolean canPlayAmbientSound() {
        return !this.isEepy();
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
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (EEPY.equals(accessor)) {
            this.refreshDimensions();
        }
        else if (SITTING.equals(accessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(PACIFIED_TICKS, 0);
        this.entityData.define(FROM_EGG, false);
        this.entityData.define(SHOT_FROM_OOZE, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(IDLE_STATE, 0);
        this.entityData.define(EAT_COOLDOWN, 600 + random.nextInt(600 * 4));
        this.entityData.define(FOREVER_BABY, false);
        this.entityData.define(SIT_COOLDOWN, 6000 + random.nextInt(3000));
        this.entityData.define(EEPY_COOLDOWN, 100);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(DANCING, false);
        this.entityData.define(EEPY, false);
        this.entityData.define(SITTING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putInt("PacifiedTicks", this.getPacifiedTicks());
        compoundTag.putBoolean("FromEgg", this.isFromEgg());
        compoundTag.putInt("EatCooldown", this.getEatCooldown());
        compoundTag.putBoolean("ForeverBaby", this.isForeverBaby());
        compoundTag.putInt("SitCooldown", this.getSitCooldown());
        compoundTag.putInt("EepyCooldown", this.getEepyCooldown());
        compoundTag.putInt("Command", this.getCommand());
        compoundTag.putBoolean("Eepy", this.isEepy());
        compoundTag.putBoolean("Sitting", this.isSitting());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(compoundTag.getInt("Variant"));
        this.setPacifiedTicks(compoundTag.getInt("PacifiedTicks"));
        this.setFromEgg(compoundTag.getBoolean("FromEgg"));
        this.setEatCooldown(compoundTag.getInt("EatCooldown"));
        this.setForeverBaby(compoundTag.getBoolean("ForeverBaby"));
        this.setSitCooldown(compoundTag.getInt("SitCooldown"));
        this.setEepyCooldown(compoundTag.getInt("EepyCooldown"));
        this.setCommand(compoundTag.getInt("Command"));
        this.setEepy(compoundTag.getBoolean("Eepy"));
        this.setSitting(compoundTag.getBoolean("Sitting"));
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

    // Pacify
    public boolean canPacify() {
        return false;
    }

    public int pacifiedTicks() {
        return 1500 + this.getRandom().nextInt(1500);
    }

    public boolean isPacified() {
        return this.getPacifiedTicks() > 0 || this.getPacifiedTicks() == -1;
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

    // Eating
    public int getEatCooldown() {
        return this.entityData.get(EAT_COOLDOWN);
    }

    public void setEatCooldown(int cooldown) {
        this.entityData.set(EAT_COOLDOWN, cooldown);
    }

    // Never grows up
    public boolean isForeverBaby() {
        return this.entityData.get(FOREVER_BABY);
    }

    public void setForeverBaby(boolean foreverBaby) {
        this.entityData.set(FOREVER_BABY, foreverBaby);
    }

    // Sitting
    public int getSitCooldown() {
        return this.entityData.get(SIT_COOLDOWN);
    }

    public void setSitCooldown(int cooldown) {
        this.entityData.set(SIT_COOLDOWN, cooldown);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    // Sleeping
    public int getEepyCooldown() {
        return this.entityData.get(EEPY_COOLDOWN);
    }

    public void setEepyCooldown(int cooldown) {
        this.entityData.set(EEPY_COOLDOWN, cooldown);
    }

    public boolean isEepy() {
        return this.entityData.get(EEPY);
    }

    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, eepy);
    }

    // Tame commands
    public int getCommand() {
        return this.entityData.get(COMMAND);
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    // Dancing
    public boolean isDancing() {
        return this.entityData.get(DANCING);
    }

    public void setDancing(boolean dancing) {
        this.entityData.set(DANCING, dancing);
    }
}
