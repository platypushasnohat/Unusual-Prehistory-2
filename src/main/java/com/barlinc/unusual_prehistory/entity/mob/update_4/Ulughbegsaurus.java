package com.barlinc.unusual_prehistory.entity.mob.update_4;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.KeybindUsingMount;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.network.MountedEntityKeyPacket;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.utils.UP2LoadedMods;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Ulughbegsaurus extends PrehistoricMob implements KeybindUsingMount, PlayerRideableJumping, LeapingMob {

    private static final EntityDataAccessor<Boolean> RAINBOW = SynchedEntityData.defineId(Ulughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Ulughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Ulughbegsaurus.class, EntityDataSerializers.INT);

    private boolean leapImpulse;

    private int attackCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState jumpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState blinkAnimationState = new SmoothAnimationState();

    private int attackTicks;
    private boolean attackAlt = false;

    private int blinkCooldown = 60 + this.getRandom().nextInt(60);
    private int shakeCooldown = 1000 + this.getRandom().nextInt(1000);
    private int yawnCooldown = 600 + this.getRandom().nextInt(600);

    public Ulughbegsaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
//        this.setMaxUpStep(1.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(3, new UlughbegsaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.7D, 7.0F, 4.0F));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(UP2ItemTags.ULUGHBEGSAURUS_FOOD), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new PrehistoricRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new SleepingGoal(this));
        this.goalSelector.addGoal(10, new UlughbegsaurusBlinkGoal(this));
        this.goalSelector.addGoal(10, new UlughbegsaurusShakeGoal(this));
        this.goalSelector.addGoal(10, new UlughbegsaurusYawnGoal(this));
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
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.JUMP_STRENGTH, 0.5D);
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
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(5);
        return super.killedEntity(level, victim);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_ULUGHBEGSAURUS)) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                if (this.getTameAttempts() > 2 && this.getRandom().nextBoolean()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.setPacifiedTicks(-1);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.setTameAttempts(this.getTameAttempts() + 1);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (!this.isRainbow() && itemstack.is(Tags.Items.DYES)) {
            UlughbegsaurusVariant variant = UlughbegsaurusVariant.byDye(itemstack);
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
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        this.floatWhileRidden(travelVec);
        super.travel(travelVec);
    }

    // Riding
    @Override
    protected float getRiddenSpeed(@NotNull Player rider) {
        float sprintSpeed = rider.isSprinting() ? 0.1F : 0.0F;
        return ((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.55F) + sprintSpeed;
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public boolean canOwnerCommand(Player player) {
        return player.isShiftKeyDown() && (!this.isPlayerHoldingDye(player) || this.isRainbow());
    }

    @Override
    public boolean canOwnerMount(Player player) {
        return !this.isBaby() && (!this.isPlayerHoldingDye(player) || this.isRainbow());
    }

    private boolean isPlayerHoldingDye(Player player) {
        return player.getItemInHand(InteractionHand.OFF_HAND).is(Tags.Items.DYES) || player.getItemInHand(InteractionHand.MAIN_HAND).is(Tags.Items.DYES);
    }

    @Override
    public void onKeyPacket(Entity keyPresser, int type) {
        if (keyPresser.isPassengerOfSameVehicle(this)) {
            if (type == 3) {
                if (this.getPose() == Pose.STANDING) {
                    this.setYHeadRot(keyPresser.getYHeadRot());
                    this.setXRot(keyPresser.getXRot());
                    this.attackAlt = this.getRandom().nextBoolean();
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
                        PacketDistributor.sendToServer(new MountedEntityKeyPacket(this.getId(), player.getId(), 3));
                    }
                }
            }
        }
    }

    private void biteNearbyEntities(double radius) {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, this.getBoundingBox().inflate(radius));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.getFirst();
            if (!entity.is(this) && !this.isAlliedTo(entity)) {
                this.doHurtTarget(entity);
                this.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        this.setLeaping(true);
        if (this.onGround()) {
            this.leapImpulse = true;
            float f = 0.05F + jumpPower * 0.01F;
            float jump = f * this.getBlockJumpFactor() + this.getJumpBoostPower();
            Vec3 jumpForwards = new Vec3(0F, jump * 0.9F, this.zza).yRot((float) Math.toRadians(-this.yBodyRot));
            this.setDeltaMovement(this.getDeltaMovement().add(jumpForwards));
        }
    }

    @Override
    public boolean canJump() {
        return !this.isLeaping() && !this.isInWaterOrBubble();
    }

    @Override
    public void handleStartJump(int jumpPower) {
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return super.calculateFallDamage(fallDistance, damageMultiplier) - 6;
    }

    @Override
    public void tick() {
        super.tick();
        this.tickPlayerBite();

        if ((this.onGround() || this.isInWaterOrBubble()) && this.isLeaping() && !leapImpulse) {
            this.setLeaping(false);
        }
        if (leapImpulse) {
            this.leapImpulse = false;
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackTicks > 0) attackTicks--;
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
            this.attackCooldown = 2 + this.getRandom().nextInt(2);
            this.setPose(Pose.STANDING);
        }
        if (attackCooldown > 0) attackCooldown--;
        if (!this.level().isClientSide) {
            if (blinkCooldown > 0) blinkCooldown--;
            if (this.getLastHurtByMob() == null && this.getTarget() == null && !this.isInWaterOrBubble()) {
                if (shakeCooldown > 0) shakeCooldown--;
                if (yawnCooldown > 0) yawnCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isSitting() && !this.isInWaterOrBubble(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.jumpAnimationState.animateWhen(this.isLeaping(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.sitAnimationState.animateWhen(this.isSitting(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.blinkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackTicks = 15;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TAME_ATTEMPTS, 0);
        builder.define(RAINBOW, false);
        builder.define(LEAPING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
        compoundTag.putBoolean("Rainbow", this.isRainbow());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
        this.setRainbow(compoundTag.getBoolean("Rainbow"));
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    public boolean isRainbow() {
        return this.entityData.get(RAINBOW);
    }

    public void setRainbow(boolean rainbow) {
        this.entityData.set(RAINBOW, rainbow);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Ulughbegsaurus ulughbegsaurus = UP2Entities.ULUGHBEGSAURUS.get().create(level);
        if (ulughbegsaurus != null) {
            ulughbegsaurus.setVariant(this.getVariant());
            ulughbegsaurus.setRainbow(this.isRainbow());
        }
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
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.ULUGHBEGSAURUS_STEP.get(), this.isBaby() ? 0.3F : 1.0F, this.isBaby() ? 1.2F : 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 250;
    }

    public enum UlughbegsaurusVariant {
        WHITE(0, Tags.Items.DYES_WHITE),
        LIGHT_GRAY(1, Tags.Items.DYES_LIGHT_GRAY),
        GRAY(2, Tags.Items.DYES_GRAY),
        BLACK(3, Tags.Items.DYES_BLACK),
        BROWN(4, Tags.Items.DYES_BROWN),
        RED(5, Tags.Items.DYES_RED),
        ORANGE(6, Tags.Items.DYES_ORANGE),
        YELLOW(7, Tags.Items.DYES_YELLOW),
        LIME(8, Tags.Items.DYES_LIME),
        GREEN(9, Tags.Items.DYES_GREEN),
        CYAN(10, Tags.Items.DYES_CYAN),
        LIGHT_BLUE(11, Tags.Items.DYES_LIGHT_BLUE),
        BLUE(12, Tags.Items.DYES_BLUE),
        PURPLE(13, Tags.Items.DYES_PURPLE),
        MAGENTA(14, Tags.Items.DYES_MAGENTA),
        PINK(15, Tags.Items.DYES_PINK),

        // Dye Depot compat
        MAROON(16, UP2ItemTags.MAROON_DYES),
        ROSE(17, UP2ItemTags.ROSE_DYES),
        CORAL(18, UP2ItemTags.CORAL_DYES),
        GINGER(19, UP2ItemTags.GINGER_DYES),
        TAN(20, UP2ItemTags.TAN_DYES),
        BEIGE(21, UP2ItemTags.BEIGE_DYES),
        OLIVE(22, UP2ItemTags.OLIVE_DYES),
        AMBER(23, UP2ItemTags.AMBER_DYES),
        FOREST(24, UP2ItemTags.FOREST_DYES),
        VERDANT(25, UP2ItemTags.VERDANT_DYES),
        TEAL(26, UP2ItemTags.TEAL_DYES),
        AQUA(27, UP2ItemTags.AQUA_DYES),
        MINT(28, UP2ItemTags.MINT_DYES),
        NAVY(29, UP2ItemTags.NAVY_DYES),
        SLATE(30, UP2ItemTags.SLATE_DYES),
        INDIGO(31, UP2ItemTags.INDIGO_DYES);

        private final int id;
        private final TagKey<Item> dye;

        UlughbegsaurusVariant(int id, TagKey<Item> dye) {
            this.id = id;
            this.dye = dye;
        }

        public int getId() {
            return this.id;
        }

        public TagKey<Item> getDye() {
            return this.dye;
        }

        public static UlughbegsaurusVariant byDye(ItemStack stack) {
            for (UlughbegsaurusVariant variant : values()) {
                if (stack.is(variant.getDye())) {
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

    public static int getRandomNaturalColor(RandomSource random) {
        int i = random.nextInt(100);
        if (i < 10) return UlughbegsaurusVariant.ORANGE.getId();
        else if (i < 20) return UlughbegsaurusVariant.BROWN.getId();
        else if (i < 30) return UlughbegsaurusVariant.WHITE.getId();
        else if (i < 38) return UlughbegsaurusVariant.YELLOW.getId();
        else if (UP2LoadedMods.isDyeDepotLoaded()) {
            if (i < 42) return UlughbegsaurusVariant.NAVY.getId();
            else if (i < 52) return UlughbegsaurusVariant.TAN.getId();
            else if (i < 62) return UlughbegsaurusVariant.OLIVE.getId();
        }
        return UlughbegsaurusVariant.BLUE.getId();
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        this.setVariant(getRandomNaturalColor(level.getRandom()));
        if (level.getRandom().nextFloat() < 0.01F) this.setRainbow(true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    // Goals
    private static class UlughbegsaurusAttackGoal extends AttackGoal {

        protected final Ulughbegsaurus ulughbegsaurus;

        public UlughbegsaurusAttackGoal(Ulughbegsaurus ulughbegsaurus) {
            super(ulughbegsaurus);
            this.ulughbegsaurus = ulughbegsaurus;
        }

        @Override
        public void tick() {
            LivingEntity target = ulughbegsaurus.getTarget();
            if (target != null) {
                double distance = ulughbegsaurus.distanceToSqr(target);
                this.ulughbegsaurus.lookAt(ulughbegsaurus.getTarget(), 30F, 30F);
                this.ulughbegsaurus.getLookControl().setLookAt(target, 30F, 30F);

                if (this.ulughbegsaurus.getAttackState() == 1) {
                    this.ulughbegsaurus.getNavigation().moveTo(target, 1.5D);
                    this.tickBite();
                } else {
                    this.ulughbegsaurus.getNavigation().moveTo(target, 1.7D);
                    if (distance <= this.getAttackReachSqr(target) && ulughbegsaurus.attackCooldown == 0) {
                        this.ulughbegsaurus.setAttackState(1);
                    }
                }
            }
        }

        protected void tickBite() {
            this.timer++;
            LivingEntity target = ulughbegsaurus.getTarget();
            if (timer == 1) {
                this.ulughbegsaurus.attackAlt = ulughbegsaurus.getRandom().nextBoolean();
                this.ulughbegsaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 8) ulughbegsaurus.playSound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK.get(), 1.0F, 0.9F + ulughbegsaurus.getRandom().nextFloat() * 0.2F);
            if (timer == 10) {
                if (this.isInAttackRange(target, 1.8D)) {
                    this.ulughbegsaurus.doHurtTarget(target);
                    this.ulughbegsaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 15) {
                this.timer = 0;
                this.ulughbegsaurus.setPose(Pose.STANDING);
                this.ulughbegsaurus.setAttackState(0);
            }
        }
    }

    private static class UlughbegsaurusBlinkGoal extends IdleAnimationGoal {

        private final Ulughbegsaurus ulughbegsaurus;

        public UlughbegsaurusBlinkGoal(Ulughbegsaurus ulughbegsaurus) {
            super(ulughbegsaurus, 20, 1, false, false);
            this.ulughbegsaurus = ulughbegsaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && ulughbegsaurus.blinkCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.ulughbegsaurus.blinkCooldown = 60 + ulughbegsaurus.getRandom().nextInt(60);
        }
    }

    private static class UlughbegsaurusShakeGoal extends IdleAnimationGoal {

        private final Ulughbegsaurus ulughbegsaurus;

        public UlughbegsaurusShakeGoal(Ulughbegsaurus ulughbegsaurus) {
            super(ulughbegsaurus, 80, 2, false);
            this.ulughbegsaurus = ulughbegsaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && ulughbegsaurus.shakeCooldown == 0 && !ulughbegsaurus.isSitting();
        }

        @Override
        public void stop() {
            super.stop();
            this.ulughbegsaurus.shakeCooldown = 1000 + ulughbegsaurus.getRandom().nextInt(1000);
        }
    }

    private static class UlughbegsaurusYawnGoal extends IdleAnimationGoal {

        private final Ulughbegsaurus ulughbegsaurus;

        public UlughbegsaurusYawnGoal(Ulughbegsaurus ulughbegsaurus) {
            super(ulughbegsaurus, 60, 3, false);
            this.ulughbegsaurus = ulughbegsaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && ulughbegsaurus.yawnCooldown == 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.ulughbegsaurus.yawnCooldown = 600 + ulughbegsaurus.getRandom().nextInt(600);
        }
    }
}
