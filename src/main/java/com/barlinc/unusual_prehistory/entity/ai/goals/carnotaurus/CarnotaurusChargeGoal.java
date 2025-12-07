package com.barlinc.unusual_prehistory.entity.ai.goals.carnotaurus;

import com.barlinc.unusual_prehistory.entity.Carnotaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CarnotaurusChargeGoal extends AttackGoal {

    private final Carnotaurus carnotaurus;
    private int collisionTicks;
    private Vec3 chargeDirection;
    private int chargeHits;

    public CarnotaurusChargeGoal(Carnotaurus carnotaurus) {
        super(carnotaurus);
        this.chargeDirection = Vec3.ZERO;
        this.carnotaurus = carnotaurus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.carnotaurus.getChargeCooldown() == 0 && this.carnotaurus.getPose() == Pose.STANDING && !this.carnotaurus.isInWater() && this.carnotaurus.isWithinYRange(this.carnotaurus.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.carnotaurus.getChargeCooldown() == 0 && !this.carnotaurus.isInWater();
    }

    @Override
    public void start() {
        super.start();
        this.collisionTicks = 0;
        this.chargeHits = 0;
        this.carnotaurus.setCharging(false);
        this.carnotaurus.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.collisionTicks = 0;
        this.chargeHits = 0;
        this.carnotaurus.setCharging(false);
        if (this.carnotaurus.getPose() != UP2Poses.STOP_CHARGING.get()) this.carnotaurus.setPose(Pose.STANDING);
        this.carnotaurus.chargeCooldown();
    }

    @Override
    public void tick() {
        LivingEntity target = this.carnotaurus.getTarget();
        if (target != null) {
            double distance = this.carnotaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());

            if (this.carnotaurus.isCharging()) {
                this.carnotaurus.getNavigation().stop();
                this.timer++;
                if (carnotaurus.horizontalCollision) {
                    this.collisionTicks++;
                } else if (this.collisionTicks > 0) {
                    this.collisionTicks--;
                }

                if (this.timer == 1) this.carnotaurus.setPose(UP2Poses.START_CHARGING.get());

                if (this.timer == 18) this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_CHARGE.get(), 1.0F, carnotaurus.getVoicePitch());

                if (timer < 20) {
                    this.carnotaurus.lookAt(target, 360F, 30F);
                    this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
                }

                if (this.timer > 20 && this.timer < 50) this.hurtNearbyEntities();

                if (this.timer > 20 && this.timer < 55) {
                    this.chargeDirection = new Vec3(target.getX() - carnotaurus.getX(), target.getY() - carnotaurus.getY(), target.getZ() - carnotaurus.getZ()).normalize();
                    float YRot = Mth.approachDegrees(carnotaurus.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 2.5F);
                    this.carnotaurus.setYRot(YRot);
                    this.carnotaurus.setYBodyRot(YRot);
                    this.carnotaurus.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * 0.65F, carnotaurus.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * 0.65F);
                }

                if (this.timer == 55) this.carnotaurus.setPose(UP2Poses.STOP_CHARGING.get());

                if (this.timer > 70) {
                    this.timer = 0;
                    this.carnotaurus.chargeCooldown();
                    this.carnotaurus.setCharging(false);
                }

                if (this.collisionTicks > 15) {
                    this.carnotaurus.setCharging(false);
                    this.carnotaurus.setPose(UP2Poses.STOP_CHARGING.get());
                    this.carnotaurus.chargeCooldown();
                    this.timer = 0;
                }

            } else {
                this.carnotaurus.lookAt(target, 30F, 30F);
                this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
                if (this.carnotaurus.getChargeCooldown() == 0) {
                    this.carnotaurus.getNavigation().moveTo(target, 2.2D);
                    if (distance < 80) {
                        this.carnotaurus.setCharging(true);
                    }
                } else {
                    this.carnotaurus.getNavigation().stop();
                    this.carnotaurus.setPose(Pose.STANDING);
                }
            }
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = this.carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.carnotaurus, this.carnotaurus.getBoundingBox().inflate(1.5D));
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
                entity.hurt(entity.damageSources().mobAttack(this.carnotaurus), (float) this.carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((knockbackForce * speedForce) * 2.5F, knockbackDirection.x(), knockbackDirection.z());
                if (entity.isDamageSourceBlocked(this.carnotaurus.damageSources().mobAttack(this.carnotaurus)) && entity instanceof Player player){
                    player.disableShield(true);
                }
                this.chargeHits++;
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.carnotaurus.getBbWidth() * 3.0F * this.carnotaurus.getBbWidth() * 3.0F + target.getBbWidth();
    }
}