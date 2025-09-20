package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
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
import net.minecraft.world.InteractionHand;
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

import java.util.EnumSet;
import java.util.Objects;

public class Carnotaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState biteRightAnimationState = new AnimationState();
    public final AnimationState biteLeftAnimationState = new AnimationState();
    public final AnimationState chargeStartAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState chargeEndAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Carnotaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CarnotaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getChargeCooldown() > 0) {
            this.setChargeCooldown(this.getChargeCooldown() - 1);
        }
    }

    public void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == UP2Poses.BITING.get()) {
                if (this.getRandom().nextBoolean()) this.biteRightAnimationState.start(this.tickCount);
                this.biteLeftAnimationState.start(this.tickCount);
            }
            if (this.getPose() == UP2Poses.CHARGING_START.get()) {
                this.idleAnimationState.stop();
                this.chargeStartAnimationState.start(this.tickCount);
            }
            if (this.getPose() == UP2Poses.CHARGING.get()) {
                this.idleAnimationState.stop();
                this.chargeStartAnimationState.stop();
                this.chargeAnimationState.start(this.tickCount);
            }
            if (this.getPose() == UP2Poses.CHARGING_END.get()) {
                this.idleAnimationState.stop();
                this.chargeAnimationState.stop();
                this.chargeStartAnimationState.stop();
                this.chargeEndAnimationState.start(this.tickCount);
            }
            if (this.getPose() == Pose.STANDING) {
                this.chargeStartAnimationState.stop();
                this.chargeAnimationState.stop();
                this.chargeEndAnimationState.stop();
                this.biteRightAnimationState.stop();
                this.biteLeftAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGING, false);
        this.entityData.define(CHARGE_COOLDOWN, 200);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Charging", this.isCharging());
        compoundTag.putInt("ChargeCooldown", this.getChargeCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCharging(compoundTag.getBoolean("Charging"));
        this.setChargeCooldown(compoundTag.getInt("ChargeCooldown"));
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
        this.entityData.set(CHARGE_COOLDOWN, 200);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return null;
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
        this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 0.9F);
    }

    // goals
    static class CarnotaurusAttackGoal extends Goal {

        private final Carnotaurus carnotaurus;
        private int attackTime = 0;
        private Vec3 rollMotion = new Vec3(0,0,0);

        public CarnotaurusAttackGoal(Carnotaurus carnotaurus) {
            this.carnotaurus = carnotaurus;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return !this.carnotaurus.isVehicle() && this.carnotaurus.getTarget() != null && this.carnotaurus.getTarget().isAlive();
        }

        public void start() {
            this.carnotaurus.setCharging(false);
            this.carnotaurus.setSprinting(false);
            this.attackTime = 0;
        }

        public void stop() {
            this.carnotaurus.setCharging(false);
            this.carnotaurus.setSprinting(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity target = this.carnotaurus.getTarget();
            if (target != null) {
                double distance = this.carnotaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (this.carnotaurus.isCharging()) {
                    tickChargeAttack();
                } else {
                    if (distance <= 60 && this.carnotaurus.getChargeCooldown() <= 0 && this.carnotaurus.onGround()) {
                        this.carnotaurus.setCharging(true);
                    }
                    else {
                        if (distance < 16) {
                            this.carnotaurus.getNavigation().moveTo(target, 1.0D);
                        } else {
                            this.carnotaurus.getNavigation().moveTo(target, 1.4D);
                        }
                        this.carnotaurus.lookAt(Objects.requireNonNull(target), 30F, 30F);
                        this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
                    }
                }
            }
        }

        protected void tickChargeAttack() {
            this.attackTime++;
            this.carnotaurus.getNavigation().stop();
            LivingEntity target = this.carnotaurus.getTarget();
            DamageSource damageSource = this.carnotaurus.damageSources().mobAttack(this.carnotaurus);
            carnotaurus.setPose(UP2Poses.CHARGING_START.get());

            if (this.attackTime == 12) {
                carnotaurus.setPose(UP2Poses.CHARGING.get());
                Vec3 targetPos = target.position();
                double x = -(this.carnotaurus.position().x - targetPos.x);
                double z = -(this.carnotaurus.position().z - targetPos.z);
                this.rollMotion = new Vec3(x, this.carnotaurus.getDeltaMovement().y, z).normalize();
                this.carnotaurus.lookAt(Objects.requireNonNull(target), 360F, 30F);
                this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
                this.carnotaurus.setSprinting(true);
            }

            if (this.attackTime > 12 && this.attackTime < 48 + this.carnotaurus.getRandom().nextInt(4)) {
                this.carnotaurus.setDeltaMovement(this.rollMotion.x * 0.56, this.carnotaurus.getDeltaMovement().y, this.rollMotion.z * 0.56);
                if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) < 1.1F) {
                    this.carnotaurus.doHurtTarget(target);
                    this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                    if (target.isDamageSourceBlocked(damageSource) && target instanceof Player player){
                        player.disableShield(true);
                    }
                }
            }

            if (attackTime > 53 || this.carnotaurus.horizontalCollision) {
                this.carnotaurus.setSprinting(false);
            }

            if (this.attackTime >= 69 || this.carnotaurus.horizontalCollision) {
                carnotaurus.setPose(UP2Poses.CHARGING_END.get());
                this.attackTime = 0;
                this.carnotaurus.setCharging(false);
                this.carnotaurus.chargeCooldown();
            }
        }
    }
}
