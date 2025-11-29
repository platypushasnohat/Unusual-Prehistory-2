package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaLayDownGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaRoarGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Megalania extends SemiAquaticMob {

    private static final EntityDataAccessor<Integer> TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LAY_DOWN_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.7F, 1.05F);

    public TemperatureStates localTemperatureState = TemperatureStates.TEMPERATE;
    public float tempProgress = 0F;
    public float prevTempProgress = 0F;

    @Nullable
    private SemiAquaticRandomStrollGoal randomStrollGoal;

    public int roarCooldown = 300 + this.getRandom().nextInt(60 * 60);

    private final byte TONGUE = 66;
    private final byte FLICK1 = 67;
    private final byte FLICK2 = 68;
    private final byte YAWN = 69;

    public enum TemperatureStates {
        TEMPERATE,
        COLD,
        WARM,
        NETHER
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState tongueAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();
    public final AnimationState biting1AnimationState = new AnimationState();
    public final AnimationState biting2AnimationState = new AnimationState();
    public final AnimationState tailWhipAnimationState = new AnimationState();
    public final AnimationState leapingAnimationState = new AnimationState();
    public final AnimationState aggroAnimationState = new AnimationState();
    public final AnimationState flick1AnimationState = new AnimationState();
    public final AnimationState flick2AnimationState = new AnimationState();
    public final AnimationState yawnAnimationState = new AnimationState();
    public final AnimationState layDownAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();

    private int bitingTicks;
    private int tailWhipTicks;
    private int roarTicks;

    public Megalania(EntityType<? extends Megalania> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
        this.switchNavigator(true);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.randomStrollGoal = new SemiAquaticRandomStrollGoal(this, 1.0D);
        this.goalSelector.addGoal(0, new LeaveWaterGoal(this, 1.0D, 1200, 2400));
        this.goalSelector.addGoal(1, new MegalaniaAttackGoal(this));
        this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.6D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MEGALANIA_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 50, 10, 5, 3));
        this.goalSelector.addGoal(4, this.randomStrollGoal);
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new MegalaniaRoarGoal(this));
        this.goalSelector.addGoal(8, new MegalaniaLayDownGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 200, true, false, this::isHostileToPlayers));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 500, true, false, entity -> entity.getType().is(UP2EntityTags.MEGALANIA_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
            this.lookControl = new LookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.6F, 0.1F, false);
            this.navigation = new AmphibiousPathNavigation(this, level());
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    public boolean isHostileToPlayers(LivingEntity entity) {
        return this.canAttack(entity) && (this.getTemperatureState() == TemperatureStates.WARM || this.getTemperatureState() == TemperatureStates.NETHER);
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

    @Override
    public boolean fireImmune() {
        return this.getTemperatureState() == TemperatureStates.NETHER;
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEMPERATURE_STATE, 0);
        this.entityData.define(PREV_TEMPERATURE_STATE, -1);
        this.entityData.define(LAY_DOWN_COOLDOWN, 50 * 50 + getRandom().nextInt(60 * 2 * 20));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TemperatureState", this.getTemperatureState().ordinal());
        compoundTag.putInt("LayDownCooldown", this.getLayDownCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(TEMPERATURE_STATE, compoundTag.getInt("TemperatureState"));
        this.setLayDownCooldown(compoundTag.getInt("LayDownCooldown"));
    }

    public TemperatureStates getTemperatureState() {
        return TemperatureStates.values()[Mth.clamp(entityData.get(TEMPERATURE_STATE), 0, 3)];
    }
    public void setTemperatureState(TemperatureStates state) {
        if (getTemperatureState() != state) {
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

    public int getLayDownCooldown() {
        return this.entityData.get(LAY_DOWN_COOLDOWN);
    }
    public void setLayDownCooldown(int cooldown) {
        this.entityData.set(LAY_DOWN_COOLDOWN, cooldown);
    }

    public void layDownCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 70 * 50 + random.nextInt(70 * 2 * 20));
    }
    public void standUpCooldown() {
        this.entityData.set(LAY_DOWN_COOLDOWN, 20 * 40 + random.nextInt(50 * 2 * 20));
    }

    public boolean isMegalaniaLayingDown() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }
    public boolean isMegalaniaVisuallyLayingDown() {
        return this.getPoseTime() < 0L != this.isMegalaniaLayingDown();
    }

    public boolean isInPoseTransition() {
        long l = this.getPoseTime();
        return l < (long) (20);
    }

    private boolean isVisuallyLayingDown() {
        return this.isMegalaniaLayingDown() && this.getPoseTime() < 20L && this.getPoseTime() >= 0L;
    }

    public void layDown() {
        if (this.isMegalaniaLayingDown()) return;
        this.setPose(UP2Poses.RESTING.get());
        this.resetLastPoseChangeTick(-(this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUp() {
        if (!this.isMegalaniaLayingDown()) {
            return;
        }
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTick((this.level()).getGameTime());
        this.refreshDimensions();
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand((this.level()).getGameTime());
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.RESTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isMegalaniaLayingDown();
    }

    @Override
    protected void onLeashDistance(float distance) {
        if (distance > 6.0F && this.isMegalaniaLayingDown() && !this.isInPoseTransition()) {
            this.standUp();
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        this.standUpInstantly();
        super.actuallyHurt(damageSource, amount);
    }

    @Override
    public boolean refuseToMove() {
        return this.isMegalaniaLayingDown() || this.isInPoseTransition() || this.getPose() == Pose.ROARING;
    }

    @Override
    public void tick() {
        super.tick();

        final boolean ground = !this.isInWater();
        if (!ground && this.isLandNavigator) switchNavigator(false);
        if (ground && !this.isLandNavigator) switchNavigator(true);

        if (this.roarCooldown > 0 && !this.isInWaterOrBubble() && this.getBehavior().equals(Behaviors.IDLE.getName())) this.roarCooldown--;

        if (this.isMegalaniaLayingDown() && this.isInWaterOrBubble()) this.standUpInstantly();
        if ((this.level().canSeeSky(this.blockPosition()) && (this.level().isThundering() || this.level().isRaining())) || !this.isRightTemperatureToSit()) this.standUp();

        this.tickTemperatureStates();
    }

    public boolean isRightTemperatureToSit() {
        return this.getTemperatureState() == Megalania.TemperatureStates.TEMPERATE || this.getTemperatureState() == Megalania.TemperatureStates.COLD;
    }

    private void tickTemperatureStates() {
        if (!this.level().isClientSide) {
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
        } else {
            this.prevTempProgress = tempProgress;
            if (localTemperatureState != this.getPrevTemperatureState()) {
                localTemperatureState = this.getPrevTemperatureState();
                tempProgress = 0.0F;
            }
            if (this.getPrevTemperatureState() != this.getTemperatureState() && tempProgress < 5.0F) tempProgress += 0.05F;
            if (this.getPrevTemperatureState() == this.getTemperatureState() && tempProgress > 0F) tempProgress -= 0.05F;
        }
    }

    @Override
    public void setupAnimationStates() {
        if (bitingTicks == 0 && (this.biting1AnimationState.isStarted() || this.biting2AnimationState.isStarted())) {
            this.biting1AnimationState.stop();
            this.biting2AnimationState.stop();
        }
        if (tailWhipTicks == 0 && this.tailWhipAnimationState.isStarted()) this.tailWhipAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.getPose() != Pose.ROARING && this.getPose() != UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.aggroAnimationState.animateWhen(this.getBehavior().equals(Behaviors.ANGRY.getName()) && this.getPose() == Pose.STANDING, this.tickCount);

        if (this.isMegalaniaVisuallyLayingDown()) {
            this.standUpAnimationState.stop();
            this.biting1AnimationState.stop();
            this.biting2AnimationState.stop();
            this.roaringAnimationState.stop();
            this.idleAnimationState.stop();
            this.aggroAnimationState.stop();
            this.tailWhipAnimationState.stop();

            if (this.isVisuallyLayingDown()) {
                this.layDownAnimationState.startIfStopped(this.tickCount);
                this.sittingAnimationState.stop();
            } else {
                this.layDownAnimationState.stop();
                this.sittingAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.layDownAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    @Override
    public void setupAnimationCooldowns() {
        if (bitingTicks > 0) bitingTicks--;
        if (bitingTicks == 0 && this.getPose() == UP2Poses.BITING.get()) this.setPose(Pose.STANDING);
        if (tailWhipTicks > 0) tailWhipTicks--;
        if (tailWhipTicks == 0 && this.getPose() == UP2Poses.TAIL_WHIPPING.get()) this.setPose(Pose.STANDING);
        if (roarTicks > 0) roarTicks--;
        if (roarTicks == 0 && this.getPose() == Pose.ROARING) this.setPose(Pose.STANDING);

        if (this.getBehavior().equals(Behaviors.IDLE.getName()) && !this.isInWaterOrBubble() && !this.isAggressive()) {
            if (this.getLayDownCooldown() > 0) this.setLayDownCooldown(this.getLayDownCooldown() - 1);
            if (this.getPose() == Pose.STANDING) {
                if (this.random.nextInt(200) == 0) {
                    this.level().broadcastEntityEvent(this, this.TONGUE);
                }
                else if (this.random.nextInt(800) == 0) {
                    this.level().broadcastEntityEvent(this, this.FLICK1);
                }
                else if (this.random.nextInt(800) == 0) {
                    this.level().broadcastEntityEvent(this, this.FLICK2);
                }
                else if (this.random.nextInt(700) == 0) {
                    this.level().broadcastEntityEvent(this, this.YAWN);
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == this.TONGUE) this.tongueAnimationState.start(this.tickCount);
        else if (id == this.FLICK1) this.flick1AnimationState.start(this.tickCount);
        else if (id == this.FLICK2) this.flick2AnimationState.start(this.tickCount);
        else if (id == this.YAWN) this.yawnAnimationState.start(this.tickCount);
        else super.handleEntityEvent(id);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == Pose.ROARING) {
                this.idleAnimationState.stop();
                this.tongueAnimationState.stop();
                this.tailWhipAnimationState.stop();
                this.biting1AnimationState.stop();
                this.biting2AnimationState.stop();
                this.swimmingAnimationState.stop();
                this.leapingAnimationState.stop();
                this.flick2AnimationState.stop();
                this.flick1AnimationState.stop();
                this.roarTicks = 80;
                this.roaringAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.BITING.get()) {
                this.bitingTicks = 20;
                if (this.getRandom().nextBoolean()) this.biting1AnimationState.start(this.tickCount);
                else this.biting2AnimationState.start(this.tickCount);
            }
            else if (this.getPose() == UP2Poses.TAIL_WHIPPING.get()) {
                this.tailWhipTicks = 30;
                this.tailWhipAnimationState.start(this.tickCount);
            }
            else {
                this.roaringAnimationState.stop();
                this.biting1AnimationState.stop();
                this.biting2AnimationState.stop();
                this.tailWhipAnimationState.stop();
            }
        }
        if (TEMPERATURE_STATE.equals(accessor)) {
            if (this.getTemperatureState().equals(TemperatureStates.COLD)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.12F);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(200);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.TEMPERATE)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.16F);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(120);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.WARM)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.19F);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(80);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.NETHER)) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22F);
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
        this.playSound(UP2SoundEvents.MEGALANIA_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag compoundTag) {
        this.entityData.set(PREV_TEMPERATURE_STATE, 0);
        this.setTemperatureState(TemperatureStates.TEMPERATE);
        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, spawnDataIn, compoundTag);
    }
}