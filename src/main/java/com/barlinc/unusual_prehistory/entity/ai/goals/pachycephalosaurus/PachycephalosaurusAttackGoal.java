package com.barlinc.unusual_prehistory.entity.ai.goals.pachycephalosaurus;

import com.barlinc.unusual_prehistory.entity.Pachycephalosaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
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
import java.util.Objects;

public class PachycephalosaurusAttackGoal extends AttackGoal {

    protected final Pachycephalosaurus pachycephalosaurus;
    private Vec3 chargeDirection;
    private int collisionTicks;

    public PachycephalosaurusAttackGoal(Pachycephalosaurus pachycephalosaurus) {
        super(pachycephalosaurus);
        this.pachycephalosaurus = pachycephalosaurus;
        this.chargeDirection = Vec3.ZERO;
    }

    @Override
    public void start() {
        super.start();
        this.collisionTicks = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.pachycephalosaurus.getTarget();
        if (target != null) {
            double distanceToTarget = this.pachycephalosaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
            int attackState = this.pachycephalosaurus.getAttackState();

            this.pachycephalosaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (attackState == 1) {
                this.pachycephalosaurus.getNavigation().stop();
                this.tickAttack();
            }
            else if (attackState == 2) {
                this.pachycephalosaurus.getNavigation().stop();
                this.tickCharge();
            }
            else {
                this.pachycephalosaurus.getNavigation().moveTo(target, 1.7D);
                if (distanceToTarget <= (pachycephalosaurus.getBbWidth() + target.getBbWidth()) * 1.1F) {
                    this.pachycephalosaurus.setAttackState(1);
                }
                else if (distanceToTarget <= 50 && !pachycephalosaurus.isInWater() && pachycephalosaurus.chargeCooldown == 0 && pachycephalosaurus.canCharge()) {
                    this.pachycephalosaurus.setAttackState(2);
                }
            }
        }
    }

    protected void tickAttack() {
        timer++;
        LivingEntity target = this.pachycephalosaurus.getTarget();
        if (timer == 1) pachycephalosaurus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 11) {
            if (this.pachycephalosaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                this.pachycephalosaurus.doHurtTarget(target);
                this.pachycephalosaurus.strongKnockback(target, 0.7D, 0.1D);
                this.pachycephalosaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 21) {
            this.timer = 0;
            this.pachycephalosaurus.setAttackState(0);
        }
    }

    protected void tickCharge() {
        timer++;
        LivingEntity target = pachycephalosaurus.getTarget();
        int speedFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);
        if (timer == 1) pachycephalosaurus.setPose(UP2Poses.WARN.get());
        if (timer < 50) {
            this.pachycephalosaurus.lookAt(target, 360F, 30F);
            this.pachycephalosaurus.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer > 50 && timer < 70) {
            this.hurtNearbyEntities();
            this.chargeDirection = new Vec3(target.getX() - pachycephalosaurus.getX(), target.getY() - pachycephalosaurus.getY(), target.getZ() - pachycephalosaurus.getZ()).normalize();
            float YRot = Mth.approachDegrees(pachycephalosaurus.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 0.5F);
            float speed = 0.85F + effectSpeed;
            this.pachycephalosaurus.setYRot(YRot);
            this.pachycephalosaurus.setYBodyRot(YRot);
            this.pachycephalosaurus.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * speed, pachycephalosaurus.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * speed);
        }
        if (timer > 70 || collisionTicks > 10) {
            this.timer = 0;
            this.pachycephalosaurus.setPose(Pose.STANDING);
            this.pachycephalosaurus.setAttackState(0);
            this.pachycephalosaurus.chargeCooldown = 150 + pachycephalosaurus.getRandom().nextInt(150);
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = pachycephalosaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), pachycephalosaurus, pachycephalosaurus.getBoundingBox().inflate(1.3D));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Pachycephalosaurus)) {
                BlockPos pos = pachycephalosaurus.blockPosition();
                Vec3 targetPos = entity.position();
                Vec3 knockbackDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
                int speedFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
                int slownessFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
                float effectSpeed = 0.15F * (speedFactor - slownessFactor);
                float speedForce = Mth.clamp(pachycephalosaurus.getSpeed() * 1.5F, 0.2F, 3.0F) + effectSpeed;
                float knockbackForce = entity.isDamageSourceBlocked(pachycephalosaurus.level().damageSources().mobAttack(pachycephalosaurus)) ? 1.5F : 2.0F;
                entity.hurt(entity.damageSources().mobAttack(pachycephalosaurus), (float) pachycephalosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((knockbackForce * speedForce) * 1.5F, knockbackDirection.x(), knockbackDirection.z());
                if (entity.isDamageSourceBlocked(pachycephalosaurus.damageSources().mobAttack(pachycephalosaurus)) && entity instanceof Player player){
                    player.disableShield(true);
                }
                pachycephalosaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.8F * this.mob.getBbWidth() * 1.8 + target.getBbWidth();
    }
}