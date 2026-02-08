package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaSitGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousPathNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.KeybindUsingMount;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Megalania extends SemiAquaticMob implements KeybindUsingMount, PlayerRideableJumping, LeapingMob {

    private static final EntityDataAccessor<Integer> TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.7F, 1.05F);

    public enum TemperatureStates {
        TEMPERATE,
        COLD,
        WARM,
        NETHER
    }

    public TemperatureStates localTemperatureState = TemperatureStates.TEMPERATE;
    public float tempProgress = 0F;
    public float prevTempProgress = 0F;

    private boolean leapImpulse;
    private float prevLeapProgress;
    private float leapProgress;
    private int leapCooldown = 0;

    @Nullable
    private PrehistoricRandomStrollGoal randomStrollGoal;

    public int biteCooldown = 5;
    public int talWhipCooldown = 150;

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState tongueAnimationState = new AnimationState();
    public final AnimationState roarAnimationState = new AnimationState();
    public final AnimationState bite1AnimationState = new AnimationState();
    public final AnimationState bite2AnimationState = new AnimationState();
    public final AnimationState tailWhipAnimationState = new AnimationState();
    public final AnimationState aggroAnimationState = new AnimationState();
    public final AnimationState flick1AnimationState = new AnimationState();
    public final AnimationState flick2AnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();
    public final AnimationState leapAnimationState = new AnimationState();

    private int bitingTicks;
    private int tailWhipTicks;

    private int flickCooldown = 300 + this.getRandom().nextInt(40 * 50);
    private int yawnCooldown = 500 + this.getRandom().nextInt(60 * 50);
    private int tongueCooldown = 100 + this.getRandom().nextInt(100);
    private int roarCooldown = 650 + this.getRandom().nextInt(70 * 60);

    public Megalania(EntityType<? extends Megalania> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.1F);
        this.switchNavigator(true);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.randomStrollGoal = new PrehistoricRandomStrollGoal(this, 1.0D, false);
        this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new MegalaniaAttackGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricFollowOwnerGoal(this, 1.2D, 7.0F, 4.0F, false));
        this.goalSelector.addGoal(3, new LargeBabyPanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MEGALANIA_FOOD), false));
        this.goalSelector.addGoal(5, new CustomizableRandomSwimGoal(this, 1.0D, 50, 10, 5));
        this.goalSelector.addGoal(6, this.randomStrollGoal);
        this.goalSelector.addGoal(7, new LeaveWaterGoal(this, 1.0D, 600, 2400));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new MegalaniaSitGoal(this));
        this.goalSelector.addGoal(10, new MegalaniaFlickTailGoal(this));
        this.goalSelector.addGoal(10, new MegalaniaYawnGoal(this));
        this.goalSelector.addGoal(10, new MegalaniaTongueGoal(this));
        this.goalSelector.addGoal(10, new MegalaniaRoarGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 200, true, false, this::isHostileToPlayers));
        this.targetSelector.addGoal(4, new MegalaniaTargetGoal<>(this, LivingEntity.class));
        this.targetSelector.addGoal(5, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, this::isHostileToEverything));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 38.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.6F, 0.1F, false);
            this.lookControl = new SmoothSwimmingLookControl(this, 20);
            this.navigation = new SmoothAmphibiousPathNavigation(this, this.level());
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            this.calculateEntityAnimation(false);
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);

        if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_MEGALANIA)) {
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (this.getTameAttempts() > 4 && this.getRandom().nextBoolean()) {
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
        return type;
    }

    public boolean isHostileToPlayers(LivingEntity entity) {
        return this.canAttack(entity) && (this.getTemperatureState() == TemperatureStates.WARM || this.getTemperatureState() == TemperatureStates.NETHER);
    }

    public boolean isHostileToEverything(LivingEntity entity) {
        return this.canAttack(entity) && this.getTemperatureState() == TemperatureStates.NETHER;
    }

    private int getHuntingInterval() {
        switch (this.getTemperatureState()) {
            case COLD -> {
                return 500;
            }
            case WARM -> {
                return 200;
            }
            case NETHER -> {
                return 100;
            }
            default -> {
                return 300;
            }
        }
    }

    public int getHealCooldown() {
        switch (this.getTemperatureState()) {
            case COLD -> {
                return 270;
            }
            case WARM -> {
                return 130;
            }
            case NETHER -> {
                return 60;
            }
            default -> {
                return 200;
            }
        }
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
        this.heal(4);
        return super.killedEntity(level, victim);
    }

    @Override
    public boolean canPacify() {
        return true;
    }

    @Override
    public boolean isPacifyItem(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.PACIFIES_MEGALANIA);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.MEGALANIA_FOOD);
    }

    // Riding
    @Override
    protected float getRiddenSpeed(@NotNull Player rider) {
        float sprintSpeed = rider.isSprinting() && this.getTemperatureState() != TemperatureStates.COLD ? 0.1F : 0.0F;
        return ((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isInWater() ? 0.17F : 0.5F)) + sprintSpeed;
    }

    @Override
    public boolean canSprint() {
        return this.getTemperatureState() != TemperatureStates.COLD && !this.isInWater();
    }

    @Override
    public Vec3 getRiderOffset() {
        return new Vec3(0.0F, 0.3F, 0.0F);
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
    protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
        if (this.isInWater()) {
            float f = player.xxa;
            float f1 = 0.0F;
            float f2 = 0.0F;
            if (player.zza != 0.0F) {
                float f3 = Mth.cos(player.getXRot() * (float) (Math.PI / 180.0));
                float f4 = -Mth.sin(player.getXRot() * (float) (Math.PI / 180.0));
                if (player.zza < 0.0F) {
                    f3 *= -0.5F;
                    f4 *= -0.5F;
                }
                f2 = f4;
                f1 = f3;
            }
            return new Vec3(f, f2, f1);
        } else {
            return super.getRiddenInput(player, vec3);
        }
    }

    @Override
    protected void tickRidden(@NotNull Player player, @NotNull Vec3 vec3) {
        if (this.isInWater()) {
            Vec2 vec2 = new Vec2(player.getXRot() * 0.5F, player.getYRot());
            float f = this.getYRot();
            float f1 = Mth.wrapDegrees(vec2.y - f);
            f += f1 * 0.5F;
            this.setRot(f, vec2.x);
            this.yRotO = this.yBodyRot = this.yHeadRot = f;
        } else {
            super.tickRidden(player, vec3);
        }
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        if (this.leapCooldown == 0) {
            this.setLeaping(true);
            if (this.onGround()) {
                float jumpFactor = this.getBlockJumpFactor() + this.getJumpBoostPower();
                this.addDeltaMovement(this.getLookAngle().multiply(1.0D, 0.0D, 1.0D).normalize().scale((0.1F * jumpPower) * this.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.getBlockSpeedFactor()).add(0.0D, (0.01F * jumpPower) * jumpFactor, 0.0D));
                this.leapImpulse = true;
                this.leapCooldown = 60;
            }
        }
    }

    @Override
    public boolean canJump() {
        return !this.isLeaping() && !this.isInWaterOrBubble() && !this.isMobSitting() && !this.isInSitPoseTransition();
    }

    @Override
    public void handleStartJump(int jumpPower) {
        if (this.getRandom().nextInt(2000) == 0) this.playSound(UP2SoundEvents.MEGALANIA_JUMPSCARE.get(), 2.0F, 1.0F);
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public int getJumpCooldown() {
        return this.leapCooldown;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return super.calculateFallDamage(fallDistance, damageMultiplier) - 5;
    }

    @Override
    public void onKeyPacket(Entity keyPresser, int type) {
        if (keyPresser.isPassengerOfSameVehicle(this)) {
//            if (type == 3) {
//                if (this.getPose() == Pose.STANDING) {
//                    this.setYHeadRot(keyPresser.getYHeadRot());
//                    this.setXRot(keyPresser.getXRot());
//                    if (this.isInWater()) {
//                        this.setPose(UP2Poses.ATTACKING.get());
//                    } else {
//                        if (this.getRandom().nextBoolean() && this.talWhipCooldown == 0) this.setPose(UP2Poses.TAIL_WHIPPING.get());
//                        else this.setPose(UP2Poses.ATTACKING.get());
//                    }
//                }
//            }
        }
    }

    private void tickPlayerBite() {
        if (this.biteCooldown == 0) {
            if (!level().isClientSide) {
                if (this.getPose() == UP2Poses.ATTACKING.get() && this.hasControllingPassenger()) {
                    if (bitingTicks == 7)
                        this.level().playSound(null, this, UP2SoundEvents.MEGALANIA_BITE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (bitingTicks <= 6 && bitingTicks > 4) {
                        this.biteNearbyEntities(2.0D);
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

    private void tickPlayerTailWhip() {
        if (this.talWhipCooldown == 0) {
            if (!level().isClientSide) {
                if (this.getPose() == UP2Poses.TAIL_WHIPPING.get() && this.hasControllingPassenger()) {
                    if (tailWhipTicks == 7)
                        this.level().playSound(null, this, UP2SoundEvents.MEGALANIA_TAIL_SWING.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (tailWhipTicks <= 6 && tailWhipTicks > 4) {
                        this.whipNearbyEnemies();
                        this.swing(InteractionHand.MAIN_HAND);
                    }
                }
            } else {
                Player player = UnusualPrehistory2.PROXY.getClientSidePlayer();
                if (player != null && player.isPassengerOfSameVehicle(this)) {
                    if (UnusualPrehistory2.PROXY.isKeyDown(3) && this.getPose() != UP2Poses.TAIL_WHIPPING.get()) {
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

    public void whipNearbyEnemies() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, this.getBoundingBox().inflate(2.5, -0.5, 2.5));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> !entity.is(this) && !entity.isAlliedTo(this)).limit(3).forEach(entity -> {
                entity.hurt(entity.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                this.strongKnockback(entity, 1.3D, 0.2D);
                if (entity.isDamageSourceBlocked(this.damageSources().mobAttack(this)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                this.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    public void applyPoison(@NotNull LivingEntity entity) {
        float chance = 0;
        int i = 0;

        if (this.level().getDifficulty() == Difficulty.NORMAL) i = 5;
        else if (this.level().getDifficulty() == Difficulty.HARD) i = 10;

        if (this.getTemperatureState() == TemperatureStates.COLD) chance = -0.1F;
        else if (this.getTemperatureState() == TemperatureStates.WARM) chance = 0.1F;
        else if (this.getTemperatureState() == TemperatureStates.NETHER) chance = 0.2F;

        if (i > 0 && this.getRandom().nextFloat() < 0.3F + chance) {
            entity.addEffect(new MobEffectInstance(this.getTemperatureState() == TemperatureStates.NETHER ? MobEffects.WITHER : MobEffects.POISON, i * 40, 0), this);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, effect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        }
        if (effect.getEffect() == MobEffects.WITHER && this.getTemperatureState() == TemperatureStates.NETHER) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, effect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        }
        else {
            return super.canBeAffected(effect);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEMPERATURE_STATE, 0);
        this.entityData.define(PREV_TEMPERATURE_STATE, -1);
        this.entityData.define(TAME_ATTEMPTS, 0);
        this.entityData.define(LEAPING, false);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TemperatureState", this.getTemperatureState().ordinal());
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(TEMPERATURE_STATE, compoundTag.getInt("TemperatureState"));
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
    }

    public TemperatureStates getTemperatureState() {
        return TemperatureStates.values()[Mth.clamp(entityData.get(TEMPERATURE_STATE), 0, 3)];
    }

    public void setTemperatureState(TemperatureStates state) {
        if (this.getTemperatureState() != state) {
            this.entityData.set(PREV_TEMPERATURE_STATE, this.entityData.get(TEMPERATURE_STATE));
        }
        this.entityData.set(TEMPERATURE_STATE, state.ordinal());
    }

    public TemperatureStates getPrevTemperatureState() {
        if (entityData.get(PREV_TEMPERATURE_STATE) == -1) {
            return null;
        }
        return TemperatureStates.values()[Mth.clamp(entityData.get(PREV_TEMPERATURE_STATE), 0, 3)];
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    public float getLeapProgress(float partialTicks) {
        return (prevLeapProgress + (leapProgress - prevLeapProgress) * partialTicks) * 0.2F;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 4;
    }

    @Override
    public void tick() {
        super.tick();

        final boolean ground = !this.isInWater();
        if (!ground && this.isLandNavigator) switchNavigator(false);
        if (ground && !this.isLandNavigator) switchNavigator(true);

        this.tickTemperatureStates();
//        this.tickPlayerBite();
//        this.tickPlayerTailWhip();

        if (this.biteCooldown > 0 && !this.isInWater()) this.biteCooldown--;
        if (this.talWhipCooldown > 0 && !this.isInWater()) this.talWhipCooldown--;

        this.prevLeapProgress = leapProgress;
        if (this.isLeaping() && leapProgress < 5F) leapProgress++;
        if (!this.isLeaping() && leapProgress > 0F) leapProgress--;

        if ((this.onGround() || this.isInWaterOrBubble()) && this.isLeaping() && !leapImpulse) {
            this.setLeaping(false);
        }
        if (leapImpulse) {
            this.leapImpulse = false;
        }

        if (leapCooldown > 0 && !this.isLeaping()) {
            this.leapCooldown--;
        }
    }

    public boolean isRightTemperatureToSit() {
        return this.getTemperatureState() == Megalania.TemperatureStates.TEMPERATE || this.getTemperatureState() == Megalania.TemperatureStates.COLD;
    }

    private void tickTemperatureStates() {
        if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() >= 1.0F) {
            if (this.level().dimension() == Level.NETHER) this.setTemperatureState(TemperatureStates.NETHER);
            else this.setTemperatureState(TemperatureStates.WARM);
        }
        else if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() <= 0.0F) {
            this.setTemperatureState(TemperatureStates.COLD);
        }
        else {
            this.setTemperatureState(TemperatureStates.TEMPERATE);
        }
        this.prevTempProgress = tempProgress;
        if (localTemperatureState != this.getPrevTemperatureState()) {
            this.localTemperatureState = this.getPrevTemperatureState();
            this.tempProgress = 0.0F;
        }
        if (this.getPrevTemperatureState() != this.getTemperatureState() && tempProgress < 5F) tempProgress += 0.4F;
        if (this.getPrevTemperatureState() == this.getTemperatureState() && tempProgress > 0F) tempProgress -= 0.4F;
    }

    public float getTempProgress(float partialTicks) {
        return (prevTempProgress + (tempProgress - prevTempProgress) * partialTicks) * 0.2F;
    }

    @Override
    public void setupAnimationStates() {
        if (bitingTicks == 0 && (this.bite1AnimationState.isStarted() || this.bite2AnimationState.isStarted())) {
            this.bite1AnimationState.stop();
            this.bite2AnimationState.stop();
        }
        if (tailWhipTicks == 0 && this.tailWhipAnimationState.isStarted()) this.tailWhipAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.getIdleState() != 4 && this.getPose() != UP2Poses.TAIL_WHIPPING.get() && !this.isInSitPoseTransition() && !this.isInEepyPoseTransition(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.aggroAnimationState.animateWhen(this.isAggressive() && this.getPose() == Pose.STANDING, this.tickCount);
        this.leapAnimationState.animateWhen(this.leapProgress > 0.0F, this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.sitEndAnimationState.stop();
            this.bite1AnimationState.stop();
            this.bite2AnimationState.stop();
            this.roarAnimationState.stop();
            this.idleAnimationState.stop();
            this.aggroAnimationState.stop();
            this.tailWhipAnimationState.stop();

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
    public void setupAnimationCooldowns() {
        if (bitingTicks > 0) bitingTicks--;
        if (bitingTicks == 0 && this.getPose() == UP2Poses.BITING.get()) {
            this.setPose(Pose.STANDING);
            this.biteCooldown = 4 + this.getRandom().nextInt(3);
        }
        if (tailWhipTicks > 0) tailWhipTicks--;
        if (tailWhipTicks == 0 && this.getPose() == UP2Poses.TAIL_WHIPPING.get()) {
            this.setPose(Pose.STANDING);
            this.talWhipCooldown = 100 + this.getRandom().nextInt(50);
        }
        if (this.getLastHurtByMob() == null && this.getTarget() == null && !this.isInWater()) {
            if (flickCooldown > 0) flickCooldown--;
            if (yawnCooldown > 0) yawnCooldown--;
            if (tongueCooldown > 0) tongueCooldown--;
            if (!this.hasControllingPassenger() && roarCooldown > 0) roarCooldown--;
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 67 -> {
                if (this.getRandom().nextBoolean()) this.flick1AnimationState.start(this.tickCount);
                else this.flick2AnimationState.start(this.tickCount);
            }
            case 68 -> {
                this.flick1AnimationState.stop();
                this.flick2AnimationState.stop();
            }
            case 69 -> this.yawnAnimationState.start(this.tickCount);
            case 70 -> this.yawnAnimationState.stop();
            case 71 -> this.tongueAnimationState.start(this.tickCount);
            case 72 -> this.tongueAnimationState.stop();
            case 73 -> this.roarAnimationState.start(this.tickCount);
            case 74 -> this.roarAnimationState.stop();
            default -> super.handleEntityEvent(id);
        }
    }

    protected void flickCooldown() {
        this.flickCooldown = 300 + this.getRandom().nextInt(40 * 50);
    }

    protected void yawnCooldown() {
        this.yawnCooldown = 500 + this.getRandom().nextInt(60 * 50);
    }

    protected void tongueCooldown() {
        this.tongueCooldown = 100 + this.getRandom().nextInt(100);
    }

    protected void roarCooldown() {
        this.roarCooldown = 650 + this.getRandom().nextInt(70 * 60);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) {
                this.bitingTicks = 20;
                if (this.getRandom().nextBoolean()) this.bite1AnimationState.start(this.tickCount);
                else this.bite2AnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.TAIL_WHIPPING.get()) {
                this.tailWhipTicks = 30;
                this.tailWhipAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.bite1AnimationState.stop();
                this.bite2AnimationState.stop();
                this.tailWhipAnimationState.stop();
            }
        }
        if (TEMPERATURE_STATE.equals(accessor)) {
            if (this.getTemperatureState().equals(TemperatureStates.COLD)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.14F);
                this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(10.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(200);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.TEMPERATE)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.18F);
                this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(16.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(120);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.WARM)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F);
                this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(80);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.NETHER)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3F);
                this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(50);
                }
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.MEGALANIA.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MEGALANIA_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.MEGALANIA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MEGALANIA_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.MEGALANIA_STEP.get(), 0.6F, 1.0F);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag compoundTag) {
        this.entityData.set(PREV_TEMPERATURE_STATE, 0);
        this.setTemperatureState(TemperatureStates.TEMPERATE);
        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, spawnDataIn, compoundTag);
    }

    public static boolean canSpawn(EntityType<Megalania> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.MEGALANIA_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    // Goals
    private static class MegalaniaTargetGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        private final Megalania megalania;

        public MegalaniaTargetGoal(Megalania megalania, Class<T> targetClass) {
            super(megalania, targetClass, 0, true, true, entity -> entity.getType().is(UP2EntityTags.MEGALANIA_TARGETS));
            this.megalania = megalania;
        }

        @Override
        public boolean canUse() {
            return this.getInterval() && !prehistoricMob.isPacified() && !prehistoricMob.isBaby();
        }

        private boolean getInterval() {
            if (megalania.getHuntingInterval() > 0 && megalania.getRandom().nextInt(megalania.getHuntingInterval()) != 0) {
                return false;
            } else {
                this.findTarget();
                return this.target != null;
            }
        }
    }

    private static class MegalaniaFlickTailGoal extends AnimationGoal {

        private final Megalania megalania;

        public MegalaniaFlickTailGoal(Megalania megalania) {
            super(megalania, 20, 1, (byte) 67, (byte) 68, false);
            this.megalania = megalania;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && megalania.flickCooldown == 0 && !megalania.isLeaping();
        }

        @Override
        public void stop() {
            super.stop();
            this.megalania.flickCooldown();
        }
    }

    private static class MegalaniaYawnGoal extends AnimationGoal {

        private final Megalania megalania;

        public MegalaniaYawnGoal(Megalania megalania) {
            super(megalania, 80, 2, (byte) 69, (byte) 70, false);
            this.megalania = megalania;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && megalania.yawnCooldown == 0 && !megalania.isLeaping();
        }

        @Override
        public void stop() {
            super.stop();
            this.megalania.yawnCooldown();
        }
    }

    private static class MegalaniaTongueGoal extends AnimationGoal {

        private final Megalania megalania;

        public MegalaniaTongueGoal(Megalania megalania) {
            super(megalania, 20, 3, (byte) 71, (byte) 72, false, false);
            this.megalania = megalania;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && megalania.tongueCooldown == 0 && !megalania.isLeaping();
        }

        @Override
        public void stop() {
            super.stop();
            this.megalania.tongueCooldown();
        }
    }

    private static class MegalaniaRoarGoal extends AnimationGoal {

        private final Megalania megalania;

        public MegalaniaRoarGoal(Megalania megalania) {
            super(megalania, 80, 4, (byte) 73, (byte) 74);
            this.megalania = megalania;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && megalania.roarCooldown == 0 && !megalania.isMobSitting() && !megalania.hasControllingPassenger() && !megalania.isLeaping();
        }

        @Override
        public void stop() {
            super.stop();
            this.megalania.roarCooldown();
        }

        @Override
        public void tick() {
            super.tick();
            if (timer == 61) megalania.playSound(UP2SoundEvents.MEGALANIA_ROAR.get(), 1.5F, megalania.getVoicePitch());
        }
    }
}