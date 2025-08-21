package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goal.AttackGoal;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Carnotaurus extends PrehistoricMob {

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
            if (this.getPose() == UP2Poses.CHARGING_START.get()) this.chargeStartAnimationState.start(this.tickCount);
            if (this.getPose() == UP2Poses.CHARGING.get()) {
                this.chargeStartAnimationState.stop();
            }
            if (this.getPose() == UP2Poses.CHARGING.get()) {
                this.chargeStartAnimationState.stop();
                this.chargeAnimationState.start(this.tickCount);
            }
            if (this.getPose() == UP2Poses.CHARGING_END.get()) {
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
    static class CarnotaurusAttackGoal extends AttackGoal {

        protected final Carnotaurus carnotaurus;

        public CarnotaurusAttackGoal(Carnotaurus carnotaurus) {
            super(carnotaurus);
            this.carnotaurus = carnotaurus;
        }

        @Override
        public void start() {
            this.carnotaurus.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
        }

        @Override
        public void stop() {
            LivingEntity livingentity = this.carnotaurus.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) this.carnotaurus.setTarget(null);
            this.carnotaurus.setAggressive(false);
            this.carnotaurus.getNavigation().stop();
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.carnotaurus.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) return false;
            else {
                this.path = this.carnotaurus.getNavigation().createPath(livingEntity, 0);
                if (this.path != null) return true;
                else return this.getAttackReachSqr(livingEntity) >= this.carnotaurus.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = this.carnotaurus.getTarget();
            if (livingentity == null) return false;
            else if (!livingentity.isAlive()) return false;
            else if (!this.carnotaurus.isWithinRestriction(livingentity.blockPosition())) return false;
            else return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }

        @Override
        public void tick() {
            LivingEntity target = this.carnotaurus.getTarget();
            if (target != null) {
                double distanceToTarget = this.carnotaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
                Pose pose = this.carnotaurus.getPose();

                this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);

                if (distanceToTarget > 60) {
                    this.carnotaurus.getNavigation().moveTo(target, 1.7D);
                    this.carnotaurus.setSprinting(true);
                } else {
                    this.carnotaurus.getNavigation().moveTo(target, 1.25D);
                    this.carnotaurus.setSprinting(false);
                }

                if (pose == UP2Poses.BITING.get()) tickBite();
                else if (distanceToTarget <= this.getAttackReachSqr(target)) {
                    this.carnotaurus.setPose(UP2Poses.BITING.get());
                }
            }
        }

        protected void tickBite() {
            attackTime++;
            LivingEntity target = this.carnotaurus.getTarget();
            this.carnotaurus.getNavigation().stop();

            if (attackTime == 11) {
                if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.carnotaurus.doHurtTarget(target);
                    this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (attackTime >= 16) {
                attackTime = 0;
                this.carnotaurus.setPose(Pose.STANDING);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
        }
    }
}
