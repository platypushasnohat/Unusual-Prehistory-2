package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CarnotaurusChargeGoal extends Goal {

    private final Carnotaurus carnotaurus;
    private int timer;
    private Vec3 chargeDirection;
    private int collisionTicks;

    public CarnotaurusChargeGoal(Carnotaurus carnotaurus) {
        this.carnotaurus = carnotaurus;
        this.chargeDirection = Vec3.ZERO;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.carnotaurus.isVehicle() && !this.carnotaurus.isBaby() && this.carnotaurus.getTarget() != null && this.carnotaurus.getTarget().isAlive() && this.carnotaurus.getChargeCooldown() <= 0 && !this.carnotaurus.isInWaterOrBubble() && this.carnotaurus.getAttackState() == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !carnotaurus.isInWater();
    }

    @Override
    public void start() {
        LivingEntity target = this.carnotaurus.getTarget();
        if (target == null) {
            return;
        }
        this.timer = 0;
        this.collisionTicks = 0;
        this.carnotaurus.setCharging(true);
        this.carnotaurus.setSprinting(true);
        this.carnotaurus.setBehavior(Behaviors.ANGRY.getName());
        this.carnotaurus.setAggressive(true);
    }

    @Override
    public void stop() {
        this.timer = 0;
        this.collisionTicks = 0;
        this.carnotaurus.setCharging(false);
        this.carnotaurus.setSprinting(false);
        this.carnotaurus.setBehavior(Behaviors.IDLE.getName());
        this.carnotaurus.setAggressive(false);
        this.carnotaurus.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.carnotaurus.getTarget();
        BlockPos pos = carnotaurus.blockPosition();
        int speedFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? carnotaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);
        if (target != null) {
            this.timer++;
            this.carnotaurus.getNavigation().stop();

            if (this.carnotaurus.horizontalCollision) {
                this.collisionTicks++;
            } else {
                this.collisionTicks--;
            }

            if (timer < 17) {
                this.carnotaurus.lookAt(target, 360F, 30F);
                this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (this.timer == 17) {
                Vec3 targetPos = target.position();
                this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_CHARGE.get(), 1.0F, carnotaurus.getVoicePitch());
                this.chargeDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
            }

            if (this.timer > 20) {
                this.carnotaurus.setDeltaMovement(this.chargeDirection.x * (-0.7 - effectSpeed), this.carnotaurus.getDeltaMovement().y, this.chargeDirection.z * (-0.7 - effectSpeed));
                tryToHurt();
            }

            if (this.timer >= 75 || this.collisionTicks > 40 || this.carnotaurus.isInWater()) {
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
                float effectSpeed = 0.15F * (speedFactor - slownessFactor);
                float speedForce = Mth.clamp(carnotaurus.getSpeed() * 1.65F, 0.2F, 3.0F) + effectSpeed;
                float knockbackForce = entity.isDamageSourceBlocked(carnotaurus.level().damageSources().mobAttack(carnotaurus)) ? 1.75F : 2.25F;
                entity.hurt(entity.damageSources().mobAttack(this.carnotaurus), (float) this.carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((knockbackForce * speedForce) * 2.5F, this.chargeDirection.x(), this.chargeDirection.z());
                if (entity.isDamageSourceBlocked(this.carnotaurus.damageSources().mobAttack(this.carnotaurus)) && entity instanceof Player player){
                    player.disableShield(true);
                }
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    private void finishCharging(Carnotaurus carnotaurus) {
        this.timer = 0;
        carnotaurus.chargeCooldown();
    }
}