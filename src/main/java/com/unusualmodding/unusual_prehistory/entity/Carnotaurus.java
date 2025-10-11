package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goals.*;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Carnotaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ROAR_COOLDOWN = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState headbuttAnimationState = new AnimationState();
    public final AnimationState sniffAnimationState = new AnimationState();
    public final AnimationState waveAnimationState = new AnimationState();
    public final AnimationState angryAnimationState = new AnimationState();
    public final AnimationState roarAnimationState = new AnimationState();

    public int sniffCooldown = this.random.nextInt(20 * 30) + (20 * 30);
    public int waveCooldown = this.random.nextInt(40 * 40) + (40 * 40);

    public Carnotaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CarnotaurusRoarGoal(this));
        this.goalSelector.addGoal(2, new CarnotaurusChargeGoal(this));
        this.goalSelector.addGoal(3, new CarnotaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
//        this.goalSelector.addGoal(4, new CarnotaurusWaveGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new CarnotaurusSniffingGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    public void roar() {
        if (this.isAlive()) {
            this.level().broadcastEntityEvent(this, (byte) 39);
            this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 2));
            this.gameEvent(GameEvent.ENTITY_ROAR);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.roarEffect();
        }
        super.handleEntityEvent(id);
    }

    private void roarEffect() {
        for(int i = 0; i < 10; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getChargeCooldown() > 0) {
            this.setChargeCooldown(this.getChargeCooldown() - 1);
        }
        if (this.getRoarCooldown() > 0) {
            this.setRoarCooldown(this.getRoarCooldown() - 1);
        }
        this.setAngry(this.getHealth() < this.getMaxHealth() * 0.5F && !this.isBaby());

        if (!this.isInWaterOrBubble() && this.getPose() != Pose.SNIFFING && sniffCooldown > 0) {
            sniffCooldown--;
        }
        if (!this.isInWaterOrBubble() && this.getPose() != UP2Poses.WAVING.get() && waveCooldown > 0) {
            waveCooldown--;
        }
    }

    public void setupAnimationStates() {
        this.angryAnimationState.animateWhen(this.isAngry(), this.tickCount);
        this.idleAnimationState.animateWhen(!this.isCharging() && !this.isRoaring(), this.tickCount);
        this.chargeAnimationState.animateWhen(this.isCharging(), this.tickCount);
        this.roarAnimationState.animateWhen(this.isRoaring(), this.tickCount);
        this.biteAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.headbuttAnimationState.animateWhen(this.getAttackState() == 2, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == Pose.SNIFFING) {
                this.waveAnimationState.stop();
                this.sniffAnimationState.start(this.tickCount);
            } else {
                this.sniffAnimationState.stop();
            }
            if (this.getPose() == UP2Poses.WAVING.get()) {
                this.sniffAnimationState.stop();
                this.waveAnimationState.start(this.tickCount);
            } else {
                this.waveAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGING, false);
        this.entityData.define(CHARGE_COOLDOWN, 200);
        this.entityData.define(ROARING, false);
        this.entityData.define(ROAR_COOLDOWN, 400);
        this.entityData.define(ANGRY, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Charging", this.isCharging());
        compoundTag.putInt("ChargeCooldown", this.getChargeCooldown());
        compoundTag.putBoolean("Roaring", this.isRoaring());
        compoundTag.putInt("RoarCooldown", this.getRoarCooldown());
        compoundTag.putBoolean("Angry", this.isAngry());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCharging(compoundTag.getBoolean("Charging"));
        this.setChargeCooldown(compoundTag.getInt("ChargeCooldown"));
        this.setRoaring(compoundTag.getBoolean("Roaring"));
        this.setRoarCooldown(compoundTag.getInt("RoarCooldown"));
        this.setAngry(compoundTag.getBoolean("Angry"));
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        this.entityData.set(ANGRY, angry);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public int getChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN);
    }

    public void setChargeCooldown(int cooldown) {
        this.entityData.set(CHARGE_COOLDOWN, cooldown);
    }

    public void chargeCooldown() {
        this.entityData.set(CHARGE_COOLDOWN, 200 + this.getRandom().nextInt(200));
    }

    public boolean isRoaring() {
        return this.entityData.get(ROARING);
    }

    public void setRoaring(boolean roaring) {
        this.entityData.set(ROARING, roaring);
    }

    public int getRoarCooldown() {
        return this.entityData.get(ROAR_COOLDOWN);
    }

    public void setRoarCooldown(int cooldown) {
        this.entityData.set(ROAR_COOLDOWN, cooldown);
    }

    public void roarCooldown() {
        this.entityData.set(ROAR_COOLDOWN, 400 + this.getRandom().nextInt(400));
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UP2Entities.CARNOTAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.CARNOTAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.CARNOTAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.CARNOTAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.CARNOTAURUS_STEP.get(), this.isBaby() ? 0.25F : 1.0F, this.isBaby() ? 1.2F : 0.9F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }
}
