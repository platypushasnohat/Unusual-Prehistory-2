package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.entity.ai.goal.AttackGoal;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Majungasaurus extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState eyesAnimationState = new AnimationState();
    public final AnimationState biteRightAnimationState = new AnimationState();
    public final AnimationState biteLeftAnimationState = new AnimationState();
    public final AnimationState chargeStartAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState chargeEndAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public Majungasaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MajungasaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.19F)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void tick () {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        this.eyesAnimationState.animateWhen(!this.isAggressive(), this.tickCount);
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
        return UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 0.9F);
    }

    // goals
    static class MajungasaurusAttackGoal extends AttackGoal {

        protected final Majungasaurus majungasaurus;

        public MajungasaurusAttackGoal(Majungasaurus majungasaurus) {
            super(majungasaurus);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public void start() {
            super.start();
            this.majungasaurus.setPose(Pose.STANDING);
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.setPose(Pose.STANDING);
        }

        @Override
        public void tick() {
            LivingEntity target = this.majungasaurus.getTarget();
            if (target != null) {
                double distanceToTarget = this.majungasaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
                Pose pose = this.majungasaurus.getPose();

                this.majungasaurus.getLookControl().setLookAt(target, 30F, 30F);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

                if (this.majungasaurus.getSensing().hasLineOfSight(target) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 0.0 || this.majungasaurus.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = target.getX();
                    this.pathedTargetY = target.getY();
                    this.pathedTargetZ = target.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.majungasaurus.getRandom().nextInt(7);

                    if (distanceToTarget > 1024.0) this.ticksUntilNextPathRecalculation += 10;
                    else if (distanceToTarget > 256.0) this.ticksUntilNextPathRecalculation += 5;

                    if (!this.majungasaurus.getNavigation().moveTo(target, 1.75D))
                        this.ticksUntilNextPathRecalculation += 15;

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                this.path = this.majungasaurus.getNavigation().createPath(target, 0);
                if (this.getAttackReachSqr(target) > 0) this.majungasaurus.getNavigation().moveTo(this.path, 1.75D);

                if (pose == UP2Poses.BITING.get()) tickBite();
                if (pose == UP2Poses.CHARGING_START.get()) tickCharge();
                else if (distanceToTarget <= this.getAttackReachSqr(target) && pose != UP2Poses.CHARGING_START.get() && pose != UP2Poses.CHARGING.get() && pose != UP2Poses.CHARGING_END.get()) {
                    this.majungasaurus.setPose(UP2Poses.BITING.get());
                }
                if (distanceToTarget > 80 && this.majungasaurus.onGround()) {
                    this.majungasaurus.setPose(UP2Poses.CHARGING_START.get());
                }
            }
        }

        protected void tickBite() {
            attackTime++;
            LivingEntity target = this.majungasaurus.getTarget();
            if (attackTime == 11) {

                if (this.majungasaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.majungasaurus.doHurtTarget(target);
                    this.majungasaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (attackTime >= 20) {
                attackTime = 0;
                this.majungasaurus.setPose(Pose.STANDING);
            }
        }

        protected void tickCharge() {
            attackTime++;
            this.majungasaurus.getNavigation().stop();
            LivingEntity target = this.majungasaurus.getTarget();
            Vec3 targetPos = target.position();
            DamageSource damageSource = this.majungasaurus.damageSources().mobAttack(this.majungasaurus);
            double x = -(this.majungasaurus.position().x - targetPos.x);
            double z = -(this.majungasaurus.position().z - targetPos.z);
            Vec3 direction = new Vec3(x, this.majungasaurus.getDeltaMovement().y, z).normalize();

            if (this.attackTime == 20) {
                this.majungasaurus.setPose(UP2Poses.CHARGING.get());
                this.majungasaurus.lookAt(Objects.requireNonNull(target), 360F, 30F);
                this.majungasaurus.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (this.attackTime > 20 && this.attackTime < 48) {
                this.majungasaurus.setDeltaMovement(direction.x * 0.16, this.majungasaurus.getDeltaMovement().y, direction.z * 0.16);
                if (this.majungasaurus.distanceTo(Objects.requireNonNull(target)) < 1.1F) {
                    this.majungasaurus.doHurtTarget(target);
                    this.majungasaurus.swing(InteractionHand.MAIN_HAND);
                    if (target.isDamageSourceBlocked(damageSource) && target instanceof Player player){
                        player.disableShield(true);
                    }
                }
            }

            if (this.attackTime == 48) this.majungasaurus.setPose(UP2Poses.CHARGING_END.get());

            if (this.attackTime >= 63 || this.majungasaurus.horizontalCollision) {
                this.attackTime = 0;
                this.majungasaurus.setPose(Pose.STANDING);
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
        }
    }
}
