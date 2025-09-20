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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Carnotaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(Carnotaurus.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState biteRightAnimationState = new AnimationState();
    public final AnimationState biteLeftAnimationState = new AnimationState();
    public final AnimationState chargeStartAnimationState = new AnimationState();
    public final AnimationState chargeEndAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Carnotaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CarnotaurusChargeGoal(this));
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
//        this.chargeStartAnimationState.animateWhen(this.getPose() == UP2Poses.CHARGING_START.get() && this.isCharging(), this.tickCount);
//        this.chargeEndAnimationState.animateWhen(this.getPose() == UP2Poses.CHARGING_END.get() && !this.isCharging(), this.tickCount);

//        if (this.idleAnimationTimeout == 0) {
//            this.idleAnimationTimeout = 160;
//            this.idleAnimationState.start(this.tickCount);
//        } else {
//            --this.idleAnimationTimeout;
//        }
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
            if (this.getPose() == UP2Poses.CHARGING_END.get()) {
                this.idleAnimationState.stop();
                this.chargeStartAnimationState.stop();
                this.chargeEndAnimationState.stop();
                this.chargeEndAnimationState.startIfStopped(this.tickCount);
            }
            if (this.getPose() == Pose.STANDING) {
                this.chargeStartAnimationState.stop();
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
        this.entityData.set(CHARGE_COOLDOWN, 100);
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
    static class CarnotaurusChargeGoal extends Goal {

        private final Carnotaurus carnotaurus;
        private int attackTime;
        private Vec3 chargeDirection;

        public CarnotaurusChargeGoal(Carnotaurus carnotaurus) {
            this.carnotaurus = carnotaurus;
            this.chargeDirection = Vec3.ZERO;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !this.carnotaurus.isVehicle() && this.carnotaurus.getTarget() != null && this.carnotaurus.getTarget().isAlive() && this.carnotaurus.getChargeCooldown() <= 0 && !this.carnotaurus.isInWater();
        }

        @Override
        public void start() {
            LivingEntity target = this.carnotaurus.getTarget();
            if (target == null) {
                return;
            }
            this.attackTime = 0;
            this.carnotaurus.setCharging(true);
            this.carnotaurus.setPose(Pose.STANDING);
        }

        @Override
        public void stop() {
            this.attackTime = 0;
            this.carnotaurus.setCharging(false);
            this.carnotaurus.setPose(UP2Poses.CHARGING_END.get());
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.carnotaurus.getTarget();
            BlockPos pos = carnotaurus.blockPosition();
            if (this.carnotaurus.horizontalCollision && this.carnotaurus.onGround()) {
                this.carnotaurus.jumpFromGround();
            }

            if (target != null) {
                this.attackTime++;
                this.carnotaurus.getNavigation().stop();

                if (attackTime < 20) {
                    this.carnotaurus.getNavigation().stop();
                    this.carnotaurus.lookAt(target, 360F, 30F);
                    this.carnotaurus.setPose(UP2Poses.CHARGING_START.get());
                }

                if (this.attackTime == 20) {
                    Vec3 targetPos = target.position();
                    this.chargeDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
                    this.carnotaurus.setPose(Pose.STANDING);
                }

                if (this.attackTime > 20) {
                    this.carnotaurus.setDeltaMovement(this.chargeDirection.x * -0.5, this.carnotaurus.getDeltaMovement().y, this.chargeDirection.z * -0.5);
                    tryToHurt();
                }

                if (this.attackTime >= 100 || this.carnotaurus.horizontalCollision || this.carnotaurus.isInWater()) {
                    finishCharging(this.carnotaurus);
                }
            }
        }

        private void tryToHurt() {
            List<LivingEntity> nearbyEntities = this.carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.carnotaurus, this.carnotaurus.getBoundingBox());
            if (!nearbyEntities.isEmpty()) {
                LivingEntity entity = nearbyEntities.get(0);
                if (!(entity instanceof Carnotaurus)) {
                    int speedFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
                    int slownessFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
                    float effectSpeed = 0.25F * (speedFactor - slownessFactor);
                    float speedForce = Mth.clamp(carnotaurus.getSpeed() * 1.65F, 0.2F, 3.0F) + effectSpeed;
                    float knockbackForce = entity.isDamageSourceBlocked(carnotaurus.level().damageSources().mobAttack(carnotaurus)) ? 1.5F : 2.5F;
                    entity.hurt(entity.damageSources().mobAttack(this.carnotaurus), (float) this.carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    entity.knockback((knockbackForce * speedForce) * 2.5F, this.chargeDirection.x(), this.chargeDirection.z());
                    this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
        }

        private void finishCharging(Carnotaurus carnotaurus) {
            this.attackTime = 0;
            carnotaurus.chargeCooldown();
        }
    }
}
