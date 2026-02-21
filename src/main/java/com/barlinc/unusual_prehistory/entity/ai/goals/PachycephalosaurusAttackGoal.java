package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Pachycephalosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.utils.MobAttackUtils;
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

public class PachycephalosaurusAttackGoal extends AttackGoal {

    protected final Pachycephalosaurus pachycephalosaurus;
    private int collisionTicks;

    public PachycephalosaurusAttackGoal(Pachycephalosaurus pachycephalosaurus) {
        super(pachycephalosaurus);
        this.pachycephalosaurus = pachycephalosaurus;
    }

    @Override
    public void start() {
        super.start();
        this.collisionTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.pachycephalosaurus.setFightCooldown(1000 + pachycephalosaurus.getRandom().nextInt(1000));
    }

    @Override
    public void tick() {
        LivingEntity target = pachycephalosaurus.getTarget();
        if (target != null) {
            double distance = pachycephalosaurus.distanceToSqr(target);
            int attackState = pachycephalosaurus.getAttackState();

            this.pachycephalosaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (attackState == 1) {
                this.pachycephalosaurus.getNavigation().stop();
                this.tickCharge();
            }
            else {
                if (distance > 40) {
                    this.pachycephalosaurus.getNavigation().moveTo(target, 1.7D);
                }
                if (distance <= 50 && !pachycephalosaurus.isInWater() && pachycephalosaurus.getChargeCooldown() == 0) {
                    this.pachycephalosaurus.setAttackState(1);
                }
            }
        }
    }

    protected void tickCharge() {
        this.timer++;
        LivingEntity target = pachycephalosaurus.getTarget();
        if (timer == 1) pachycephalosaurus.setPose(UP2Poses.WARNING.get());
        if (timer < 50) {
            this.pachycephalosaurus.lookAt(target, 360F, 30F);
            this.pachycephalosaurus.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer == 40) pachycephalosaurus.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN.get(), 2.0F, 0.9F + pachycephalosaurus.getRandom().nextFloat() * 0.3F);
        if (timer > 50 && timer < 70) {
            this.hurtNearbyEntities();
            MobAttackUtils.chargeAtTarget(pachycephalosaurus, target, 0.85F);
        }
        if (timer > 70 || collisionTicks > 10) {
            this.timer = 0;
            this.pachycephalosaurus.setPose(Pose.STANDING);
            this.pachycephalosaurus.setAttackState(0);
            this.pachycephalosaurus.setChargeCooldown(30 + pachycephalosaurus.getRandom().nextInt(15));
            if (target instanceof Pachycephalosaurus) {
                this.stop();
            }
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = pachycephalosaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), pachycephalosaurus, pachycephalosaurus.getBoundingBox().inflate(1.3D));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (entity != pachycephalosaurus) {
                BlockPos pos = pachycephalosaurus.blockPosition();
                Vec3 targetPos = entity.position();
                Vec3 knockbackDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
                int speedFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
                int slownessFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
                float effectSpeed = 0.15F * (speedFactor - slownessFactor);
                float speedForce = Mth.clamp(pachycephalosaurus.getSpeed() * 1.5F, 0.2F, 3.0F) + effectSpeed;
                float knockbackForce = entity.isDamageSourceBlocked(pachycephalosaurus.level().damageSources().mobAttack(pachycephalosaurus)) ? 1.5F : 2.0F;
                if (!(entity instanceof Pachycephalosaurus)) {
                    entity.hurt(entity.damageSources().mobAttack(pachycephalosaurus), (float) pachycephalosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
                entity.knockback((knockbackForce * speedForce) * 1.5F, knockbackDirection.x(), knockbackDirection.z());
                if (entity.isDamageSourceBlocked(pachycephalosaurus.damageSources().mobAttack(pachycephalosaurus)) && entity instanceof Player player){
                    player.disableShield(true);
                }
                this.pachycephalosaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}