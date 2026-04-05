package com.barlinc.unusual_prehistory.entity.ai.goals.update_1;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Carnotaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2MobEffects;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class CarnotaurusAttackGoal extends AttackGoal {

    private final Carnotaurus carnotaurus;
    private int collisionTicks;
    private Vec3 chargeDirection;

    public CarnotaurusAttackGoal(Carnotaurus carnotaurus) {
        super(carnotaurus);
        this.carnotaurus = carnotaurus;
        this.chargeDirection = Vec3.ZERO;
    }

    @Override
    public void start() {
        super.start();
        this.collisionTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.collisionTicks = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = carnotaurus.getTarget();
        if (target != null) {
            if (!this.isInChargingPose()) {
                this.carnotaurus.lookAt(target, 30F, 30F);
                this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
            }

            double distance = carnotaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = carnotaurus.getAttackState();

            if (carnotaurus.horizontalCollision) collisionTicks++;
            else if (collisionTicks > 0) collisionTicks--;

            if (this.isInChargingPose() || attackState == 3 || attackState == 2 || attackState == 4) carnotaurus.getNavigation().stop();

            if (attackState == 1) {
                this.carnotaurus.getNavigation().moveTo(target, 1.8D);
                if (carnotaurus.isFurious()) this.tickBiteFast();
                else this.tickBite();
            } else if (attackState == 2) {
                if (carnotaurus.isFurious()) this.tickHeadbuttFast();
                else this.tickHeadbutt();
            } else if (attackState == 3) {
                this.tickCharge();
            } else if (attackState == 4) {
                this.tickRoar();
            } else {
                if (!this.isInChargingPose()) carnotaurus.getNavigation().moveTo(target, 2.2D);
                if (distance <= 10 && carnotaurus.chargeCooldown > 0 && !this.isInChargingPose()) {
                    if (carnotaurus.getRandom().nextFloat() < 0.8F && !(target instanceof Creeper) && carnotaurus.biteCooldown == 0) carnotaurus.setAttackState(1);
                    else if (carnotaurus.headbuttCooldown == 0) carnotaurus.setAttackState(2);
                } else if (distance < 70 && carnotaurus.chargeCooldown == 0 && !this.isInChargingPose() && !carnotaurus.isInWater() && carnotaurus.isWithinYRange(target)) {
                    this.carnotaurus.setAttackState(3);
                } else if (carnotaurus.roarCooldown == 0 && carnotaurus.getHealth() < carnotaurus.getMaxHealth() * 0.5F) {
                    this.carnotaurus.setAttackState(4);
                }
            }
        }
    }

    private boolean isInChargingPose() {
        return carnotaurus.getPose() == UP2Poses.STOP_CHARGING.get() || carnotaurus.getPose() == UP2Poses.CHARGING.get() || carnotaurus.getPose() == UP2Poses.START_CHARGING.get();
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = carnotaurus.getTarget();

        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.ATTACKING.get());
            this.carnotaurus.attackAlt = carnotaurus.getRandom().nextBoolean();
        }
        if (timer == 9) carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_BITE.get(), 1.0F, carnotaurus.getVoicePitch());

        if (timer == 11) {
            if (carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
            }
        }
        if (timer > 15) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.biteCooldown = 3;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickBiteFast() {
        this.timer++;
        LivingEntity target = carnotaurus.getTarget();

        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.ATTACKING_FAST.get());
            this.carnotaurus.attackAlt = carnotaurus.getRandom().nextBoolean();
        }
        if (timer == 3) carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_BITE.get(), 1.0F, 1.1F * carnotaurus.getVoicePitch());
        if (timer == 6) {
            if (carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
            }
        }
        if (timer > 12) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.biteCooldown = 1;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickHeadbutt() {
        this.timer++;

        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.HEADBUTTING.get());
            this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT_VOCAL.get(), 1.5F, 0.9F + carnotaurus.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 12) this.headbuttNearbyEntities();
        if (timer > 20) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.headbuttCooldown = 40 + carnotaurus.getRandom().nextInt(20);
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickHeadbuttFast() {
        this.timer++;

        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.HEADBUTTING_FAST.get());
            this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT_VOCAL.get(), 1.5F, 1.0F + carnotaurus.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 8) this.headbuttNearbyEntities();
        if (timer > 15) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.headbuttCooldown = 20 + carnotaurus.getRandom().nextInt(10);
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickCharge() {
        this.timer++;
        LivingEntity target = carnotaurus.getTarget();
        int speedFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int furyFactor = carnotaurus.hasEffect(UP2MobEffects.FURY) ? carnotaurus.getEffect(UP2MobEffects.FURY).getAmplifier() + 1 : 0;
        int slownessFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);
        float effectFury = 0.1F * (furyFactor - slownessFactor);

        if (timer == 1) carnotaurus.setPose(UP2Poses.START_CHARGING.get());
        if (timer == 18) carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_CHARGE.get(), 1.0F, carnotaurus.getVoicePitch());

        if (timer < 20) {
            this.carnotaurus.lookAt(target, 360F, 30F);
            this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
        }

        if (timer > 20 && timer < 45) {
            this.chargeDirection = new Vec3(target.getX() - carnotaurus.getX(), target.getY() - carnotaurus.getY(), target.getZ() - carnotaurus.getZ()).normalize();
            float YRot = Mth.approachDegrees(carnotaurus.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 1.0F);
            float speed = 0.74F + effectSpeed + effectFury;
            this.carnotaurus.setYRot(YRot);
            this.carnotaurus.setYBodyRot(YRot);
            this.carnotaurus.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * speed, carnotaurus.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * speed);
            this.hurtNearbyEntities();
        }

        if (timer > 45 || collisionTicks > 10) {
            this.timer = 0;
            this.carnotaurus.setPose(UP2Poses.STOP_CHARGING.get());
            this.carnotaurus.chargeCooldown();
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickRoar() {
        this.timer++;
        LivingEntity target = carnotaurus.getTarget();
        if (timer == 1) carnotaurus.setPose(UP2Poses.ROARING.get());
        if (timer == 5) carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_ROAR.get(), 2.0F, 1.0F);
        this.carnotaurus.lookAt(target, 360F, 30F);
        this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);

        if (timer == 8) carnotaurus.roar();

        if (timer > 40) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.roarCooldown();
            this.carnotaurus.setAttackState(0);
        }
    }

    private void headbuttNearbyEntities() {
        List<LivingEntity> nearbyEntities = carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), carnotaurus, carnotaurus.getBoundingBox().inflate(2.0));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> !(entity instanceof Carnotaurus) && !(entity instanceof Creeper)).limit(3).forEach(entity -> {
                if (entity.hurt(entity.damageSources().mobAttack(carnotaurus), (float) carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                    this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT.get(), 1.0F, carnotaurus.getVoicePitch());
                }
                this.carnotaurus.strongKnockback(entity, 1.5D, 0.5D);
                if (entity.isDamageSourceBlocked(carnotaurus.damageSources().mobAttack(carnotaurus)) && entity instanceof Player player) {
                    player.disableShield();
                }
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), carnotaurus, carnotaurus.getBoundingBox().inflate(1.5D));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Carnotaurus)) {
                BlockPos pos = carnotaurus.blockPosition();
                Vec3 targetPos = entity.position();
                Vec3 knockbackDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
                int speedFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
                int slownessFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
                float effectSpeed = 0.15F * (speedFactor - slownessFactor);
                float speedForce = Mth.clamp(carnotaurus.getSpeed() * 1.65F, 0.2F, 3.0F) + effectSpeed;
                float knockbackForce = entity.isDamageSourceBlocked(carnotaurus.level().damageSources().mobAttack(carnotaurus)) ? 1.75F : 2.25F;
                entity.hurt(entity.damageSources().mobAttack(carnotaurus), (float) carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((knockbackForce * speedForce) * 2.5F, knockbackDirection.x(), knockbackDirection.z());
                if (entity.isDamageSourceBlocked(carnotaurus.damageSources().mobAttack(carnotaurus)) && entity instanceof Player player){
                    player.disableShield();
                }
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.4F * this.mob.getBbWidth() * 1.4F + target.getBbWidth();
    }
}
