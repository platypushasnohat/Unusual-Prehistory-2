package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaAttackGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaRoarGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.megalania.MegalaniaSitGoal;
import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Megalania extends SemiAquaticMob {

    private static final EntityDataAccessor<Integer> TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(1.7F, 1.05F);

    public TemperatureStates localTemperatureState = TemperatureStates.TEMPERATE;
    public float tempProgress = 0F;
    public float prevTempProgress = 0F;

    @Nullable
    private SemiAquaticRandomStrollGoal randomStrollGoal;

    public int roarCooldown = 300 + this.getRandom().nextInt(60 * 60);

    public int biteCooldown = 5;
    public int talWhipCooldown = 150;

    private final byte TONGUE = 67;
    private final byte FLICK1 = 68;
    private final byte FLICK2 = 69;
    private final byte YAWN = 70;

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
        this.setMaxUpStep(1.1F);
        this.switchNavigator(true);
        this.lookControl = new SmoothSwimmingLookControl(this, 20);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.randomStrollGoal = new SemiAquaticRandomStrollGoal(this, 1.0D);
        this.goalSelector.addGoal(1, new MegalaniaAttackGoal(this));
        this.goalSelector.addGoal(2, new LargeBabyPanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.MEGALANIA_FOOD), false));
        this.goalSelector.addGoal(4, new CustomizableRandomSwimGoal(this, 1.0D, 50, 10, 5));
        this.goalSelector.addGoal(4, this.randomStrollGoal);
        this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 600, 2400));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new MegalaniaRoarGoal(this));
        this.goalSelector.addGoal(8, new MegalaniaSitGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 200, true, false, this::isHostileToPlayers));
        this.targetSelector.addGoal(2, new MegalaniaTargetGoal<>(this, LivingEntity.class));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, this::isHostileToEverything));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 10, 0.6F, 0.1F, false);
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }
        } else {
            super.travel(travelVec);
        }
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
        if (this.getTemperatureState() == TemperatureStates.NETHER) this.heal(4);
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
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TemperatureState", this.getTemperatureState().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(TEMPERATURE_STATE, compoundTag.getInt("TemperatureState"));
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

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return (pose == UP2Poses.SITTING.get() ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose));
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == Pose.ROARING;
    }

    @Override
    public void tick() {
        super.tick();

        final boolean ground = !this.isInWater();
        if (!ground && this.isLandNavigator) switchNavigator(false);
        if (ground && !this.isLandNavigator) switchNavigator(true);

        if (this.roarCooldown > 0 && !this.isInWaterOrBubble() && this.getBehavior().equals(Behaviors.IDLE.getName())) this.roarCooldown--;

        this.tickTemperatureStates();

        if (this.biteCooldown > 0 && !this.isInWater()) this.biteCooldown--;
        if (this.talWhipCooldown > 0 && !this.isInWater()) this.talWhipCooldown--;
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
        if (roarTicks == 0 && this.roaringAnimationState.isStarted()) this.roaringAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.getPose() != Pose.ROARING && this.getPose() != UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.aggroAnimationState.animateWhen(this.getBehavior().equals(Behaviors.ANGRY.getName()) && this.getPose() == Pose.STANDING, this.tickCount);

        if (this.isMobVisuallySitting()) {
            this.standUpAnimationState.stop();
            this.biting1AnimationState.stop();
            this.biting2AnimationState.stop();
            this.roaringAnimationState.stop();
            this.idleAnimationState.stop();
            this.aggroAnimationState.stop();
            this.tailWhipAnimationState.stop();

            if (this.isVisuallySitting()) {
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
        if (bitingTicks == 0 && this.getPose() == UP2Poses.BITING.get()) {
            this.setPose(Pose.STANDING);
            this.biteCooldown = 4 + this.getRandom().nextInt(3);
        }
        if (tailWhipTicks > 0) tailWhipTicks--;
        if (tailWhipTicks == 0 && this.getPose() == UP2Poses.TAIL_WHIPPING.get()) {
            this.setPose(Pose.STANDING);
            this.talWhipCooldown = 100 + this.getRandom().nextInt(50);
        }
        if (roarTicks > 0) roarTicks--;
        if (roarTicks == 0 && this.getPose() == Pose.ROARING) this.setPose(Pose.STANDING);

        if (this.getBehavior().equals(Behaviors.IDLE.getName()) && !this.isInWaterOrBubble() && !this.isAggressive()) {
            if (this.getPose() == Pose.STANDING) {
                if (this.random.nextInt(200) == 0) {
                    this.level().broadcastEntityEvent(this, this.TONGUE);
                }
                else if (this.random.nextInt(1000) == 0) {
                    this.level().broadcastEntityEvent(this, this.FLICK1);
                }
                else if (this.random.nextInt(1100) == 0) {
                    this.level().broadcastEntityEvent(this, this.FLICK2);
                }
                else if (this.random.nextInt(1200) == 0) {
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
}