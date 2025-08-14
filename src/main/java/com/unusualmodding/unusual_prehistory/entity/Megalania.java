package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.behaviors.BaseBehaviors;
import com.unusualmodding.unusual_prehistory.entity.behaviors.MegalaniaBehaviors;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Megalania extends PrehistoricMob {

    public static final EntityDataAccessor<Integer> YAWN_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> YAWN_TIMER = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROAR_COOLDOWN = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROAR_TIMER = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState yawningAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();

    private int tempChangeCold;
    private int prevTempChangeCold;
    private int tempChangeWarm;
    private int prevTempChangeWarm;
    private int tempChangeNether;
    private int prevTempChangeNether;

    public Megalania(EntityType<? extends Megalania> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new MegalaniaYawnGoal(this));
        this.goalSelector.addGoal(8, new MegalaniaRoarGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.14F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(YAWN_COOLDOWN, 6 * 20 + random.nextInt(12 * 20));
        this.entityData.define(YAWN_TIMER, 0);
        this.entityData.define(ROAR_COOLDOWN, 30 * 40 + random.nextInt(60 * 8 * 40));
        this.entityData.define(ROAR_TIMER, 0);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("YawnCooldown", this.getYawnCooldown());
        compoundTag.putInt("YawnTimer", this.getYawnTimer());
        compoundTag.putInt("RoarCooldown", this.getRoarCooldown());
        compoundTag.putInt("RoarTimer", this.getRoarTimer());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setYawnCooldown(compoundTag.getInt("YawnCooldown"));
        this.setYawnTimer(compoundTag.getInt("YawnTimer"));
        this.setRoarCooldown(compoundTag.getInt("RoarCooldown"));
        this.setRoarTimer(compoundTag.getInt("RoarTimer"));
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

    @Override
    public void tick() {
        super.tick();

        if (!this.isInWaterOrBubble()) {
            if (this.getRoarCooldown() > 0) {
                if (this.getBehavior().equals(MegalaniaBehaviors.ROAR.getName())) this.setBehavior(BaseBehaviors.IDLE.getName());
                this.setRoarCooldown(this.getRoarCooldown() - 1);
            }
            if (this.getYawnCooldown() > 0) {
                if (this.getBehavior().equals(MegalaniaBehaviors.YAWN.getName())) this.setBehavior(BaseBehaviors.IDLE.getName());
                this.setYawnCooldown(this.getYawnCooldown() - 1);
            }
        }
        if (this.getRoarTimer() > 0) {
            this.setRoarTimer(this.getRoarTimer() - 1);
            if (this.getRoarTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(BaseBehaviors.IDLE.getName());
                this.roarCooldown();
            }
        }
        if (this.getYawnTimer() > 0) {
            this.setYawnTimer(this.getYawnTimer() - 1);
            if (this.getYawnTimer() == 0) {
                this.setPose(Pose.STANDING);
                this.setBehavior(BaseBehaviors.IDLE.getName());
                this.yawnCooldown();
            }
        }

        if (this.level().isClientSide()) {
            changeTemperature();
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() <= 1.0E-5F && !this.isInWaterOrBubble(), this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
    }

    public void changeTemperature() {
        if (this.level().isClientSide()) {
            prevTempChangeCold = tempChangeCold;
            prevTempChangeWarm = tempChangeWarm;
            prevTempChangeNether = tempChangeNether;

            if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() >= 1.0F) {
                if (this.level().dimension() == Level.NETHER) {
                    tempChangeNether = 10;
                    tempChangeCold = 0;
                    tempChangeWarm = 0;
                } else {
                    tempChangeWarm = 10;
                    tempChangeCold = 0;
                    tempChangeNether = 0;
                }
            } else if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() <= 0.0F) {
                tempChangeCold = 10;
                tempChangeWarm = 0;
                tempChangeNether = 0;
            }

            if (tempChangeCold > 0) {
                tempChangeCold--;
            }
            if (tempChangeWarm > 0) {
                tempChangeWarm--;
            }
            if (tempChangeNether > 0) {
                tempChangeNether--;
            }
        }
    }

    public float getTemperatureChangeCold(float temperature) {
        return Mth.lerp(temperature, (float) this.prevTempChangeCold, (float) this.tempChangeCold) / 10.0F;
    }

    public float getTemperatureChangeWarm(float temperature) {
        return Mth.lerp(temperature, (float) this.prevTempChangeWarm, (float) this.tempChangeWarm) / 10.0F;
    }

    public float getTemperatureChangeNether(float temperature) {
        return Mth.lerp(temperature, (float) this.prevTempChangeNether, (float) this.tempChangeNether) / 10.0F;
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
        return 0.92F;
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
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return null;
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
            return megalania.getYawnCooldown() == 0 && megalania.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !megalania.isInWater() && megalania.onGround();
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
            megalania.setBehavior(BaseBehaviors.IDLE.getName());
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
            return megalania.getRoarCooldown() == 0 && megalania.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !megalania.isInWater() && megalania.onGround();
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
            megalania.setBehavior(BaseBehaviors.IDLE.getName());
            megalania.roarCooldown();
        }
    }
}
