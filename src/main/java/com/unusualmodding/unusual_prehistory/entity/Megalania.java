package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.MegalaniaBehaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Megalania extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> YAWN_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> YAWN_TIMER = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROAR_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROAR_TIMER = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);

    public TemperatureStates localTemperatureState = TemperatureStates.TEMPERATE;
    public float tempProgress = 0F;
    public float prevTempProgress = 0F;

    public enum TemperatureStates {
        TEMPERATE,
        COLD,
        WARM,
        NETHER
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState yawningAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();

    public Megalania(EntityType<? extends Megalania> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new MegalaniaYawnGoal(this));
        this.goalSelector.addGoal(8, new MegalaniaRoarGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.14F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(YAWN_COOLDOWN, 6 * 20 + random.nextInt(12 * 20));
        this.entityData.define(YAWN_TIMER, 0);
        this.entityData.define(ROAR_COOLDOWN, 30 * 40 + random.nextInt(60 * 8 * 40));
        this.entityData.define(ROAR_TIMER, 0);
        this.entityData.define(TEMPERATURE_STATE, 0);
        this.entityData.define(PREV_TEMPERATURE_STATE, -1);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("YawnCooldown", this.getYawnCooldown());
        compoundTag.putInt("YawnTimer", this.getYawnTimer());
        compoundTag.putInt("RoarCooldown", this.getRoarCooldown());
        compoundTag.putInt("RoarTimer", this.getRoarTimer());
        compoundTag.putInt("TemperatureState", this.getTemperatureState().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setYawnCooldown(compoundTag.getInt("YawnCooldown"));
        this.setYawnTimer(compoundTag.getInt("YawnTimer"));
        this.setRoarCooldown(compoundTag.getInt("RoarCooldown"));
        this.setRoarTimer(compoundTag.getInt("RoarTimer"));
        this.entityData.set(TEMPERATURE_STATE, compoundTag.getInt("TemperatureState"));
    }

    public int getYawnTimer() {
        return this.entityData.get(YAWN_TIMER);
    }
    public void setYawnTimer(int timer) {
        this.entityData.set(YAWN_TIMER, timer);
    }

    public int getYawnCooldown() {
        return this.entityData.get(YAWN_COOLDOWN);
    }
    public void setYawnCooldown(int cooldown) {
        this.entityData.set(YAWN_COOLDOWN, cooldown);
    }
    public void yawnCooldown() {
        this.entityData.set(YAWN_COOLDOWN, 6 * 20 + random.nextInt(60 * 2 * 20));
    }

    public int getRoarTimer() {
        return this.entityData.get(ROAR_TIMER);
    }
    public void setRoarTimer(int timer) {
        this.entityData.set(ROAR_TIMER, timer);
    }

    public int getRoarCooldown() {
        return this.entityData.get(ROAR_COOLDOWN);
    }
    public void setRoarCooldown(int cooldown) {
        this.entityData.set(ROAR_COOLDOWN, cooldown);
    }
    public void roarCooldown() {
        this.entityData.set(ROAR_COOLDOWN, 30 * 40 + random.nextInt(60 * 8 * 40));
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
    public void tick() {
        super.tick();

        if (!this.isInWaterOrBubble()) {
            if (this.getRoarCooldown() > 0) {
                if (this.getBehavior().equals(MegalaniaBehaviors.ROAR.getName())) this.setBehavior(Behaviors.IDLE.getName());
                this.setRoarCooldown(this.getRoarCooldown() - 1);
            }
            if (this.getYawnCooldown() > 0) {
                if (this.getBehavior().equals(MegalaniaBehaviors.YAWN.getName())) this.setBehavior(Behaviors.IDLE.getName());
                this.setYawnCooldown(this.getYawnCooldown() - 1);
            }
        }
        if (this.getRoarTimer() > 0) {
            this.setRoarTimer(this.getRoarTimer() - 1);
            if (this.getRoarTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(Behaviors.IDLE.getName());
                this.roarCooldown();
            }
        }
        if (this.getYawnTimer() > 0) {
            this.setYawnTimer(this.getYawnTimer() - 1);
            if (this.getYawnTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(Behaviors.IDLE.getName());
                this.yawnCooldown();
            }
        }

        this.changeTemperature();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() <= 1.0E-5F && !this.isInWaterOrBubble(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
    }

    public void changeTemperature() {
        if (localTemperatureState != this.getPrevTemperatureState()) {
            localTemperatureState = this.getPrevTemperatureState();
            tempProgress = 0.0F;
        }

        this.prevTempProgress = tempProgress;
        if (this.getPrevTemperatureState() != this.getTemperatureState() && tempProgress < 5.0F) {
            tempProgress += 0.05F;
        }
        if (this.getPrevTemperatureState() == this.getTemperatureState() && tempProgress > 0F) {
            tempProgress -= 0.05F;
        }

        if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() >= 1.0F) {
            if (this.level().dimension() == Level.NETHER) {
                this.setTemperatureState(TemperatureStates.NETHER);
            } else {
                this.setTemperatureState(TemperatureStates.WARM);
            }
        } else if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() <= 0.0F) {
            this.setTemperatureState(TemperatureStates.COLD);
        } else {
            this.setTemperatureState(TemperatureStates.TEMPERATE);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.YAWNING.get()) {
                this.yawningAnimationState.start(this.tickCount);
                this.roaringAnimationState.stop();
            }
            if (this.getPose() == UP2Poses.ROARING.get()) {
                this.roaringAnimationState.start(this.tickCount);
                this.idleAnimationState.stop();
                this.yawningAnimationState.stop();
            }
            if (this.getPose() == Pose.STANDING) {
                this.yawningAnimationState.stop();
                this.roaringAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public boolean refuseToMove() {
        return this.getPose() == UP2Poses.ROARING.get() || super.refuseToMove();
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
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
        this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 0.9F);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag compoundTag) {
        this.entityData.set(PREV_TEMPERATURE_STATE, 0);
        this.setTemperatureState(TemperatureStates.TEMPERATE);
        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, spawnDataIn, compoundTag);
    }

    // goals
    public static class MegalaniaYawnGoal extends Goal {

        protected Megalania megalania;
        protected MegalaniaBehaviors behavior;

        public MegalaniaYawnGoal(Megalania megalania) {
            this.megalania = megalania;
            this.behavior = MegalaniaBehaviors.YAWN;
        }

        @Override
        public boolean canUse() {
            return megalania.getYawnCooldown() == 0 && megalania.getBehavior().equals(Behaviors.IDLE.getName()) && !megalania.isInWater() && megalania.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return megalania.getYawnTimer() > 0 && !megalania.isInWater() && megalania.onGround();
        }

        @Override
        public void start() {
            super.start();
            megalania.setPose(UP2Poses.YAWNING.get());
            megalania.setYawnTimer(behavior.getLength());
            megalania.setBehavior(behavior.getName());
            megalania.playSound(behavior.getSound(), 1.0F, megalania.getVoicePitch());
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        public void stop() {
            super.stop();
            megalania.setBehavior(Behaviors.IDLE.getName());
            megalania.yawnCooldown();
        }
    }

    public static class MegalaniaRoarGoal extends Goal {

        protected Megalania megalania;
        protected MegalaniaBehaviors behavior;

        public MegalaniaRoarGoal(Megalania megalania) {
            this.megalania = megalania;
            this.behavior = MegalaniaBehaviors.ROAR;
        }

        @Override
        public boolean canUse() {
            return megalania.getRoarCooldown() == 0 && megalania.getBehavior().equals(Behaviors.IDLE.getName()) && !megalania.isInWater() && megalania.onGround();
        }

        @Override
        public boolean canContinueToUse() {
            return megalania.getRoarTimer() > 0 && !megalania.isInWater() && megalania.onGround();
        }

        @Override
        public void start() {
            super.start();
            megalania.setPose(UP2Poses.ROARING.get());
            megalania.setYawnTimer(behavior.getLength());
            megalania.setBehavior(behavior.getName());
            megalania.playSound(behavior.getSound(), 1.0F, megalania.getVoicePitch());
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        public void stop() {
            super.stop();
            megalania.setBehavior(Behaviors.IDLE.getName());
            megalania.roarCooldown();
        }
    }
}