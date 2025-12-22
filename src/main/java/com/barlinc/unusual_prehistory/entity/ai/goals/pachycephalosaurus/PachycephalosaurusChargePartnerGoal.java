package com.barlinc.unusual_prehistory.entity.ai.goals.pachycephalosaurus;

import com.barlinc.unusual_prehistory.entity.Pachycephalosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

// todo
public class PachycephalosaurusChargePartnerGoal extends Goal {

    private static final TargetingConditions FRIEND_TARGETING = TargetingConditions.forNonCombat().range(16.0D);

    protected final Pachycephalosaurus pachycephalosaurus;
    protected final Level level;
    @Nullable
    protected Pachycephalosaurus chargePartner;
    private Vec3 chargeDirection;
    private Vec3 partnerChargeDirection;
    private int collisionTicks;
    private int timer;

    public PachycephalosaurusChargePartnerGoal(Pachycephalosaurus pachycephalosaurus) {
        this.pachycephalosaurus = pachycephalosaurus;
        this.level = pachycephalosaurus.level();
        this.chargeDirection = Vec3.ZERO;
        this.partnerChargeDirection = Vec3.ZERO;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (pachycephalosaurus.chargeCooldown > 0 || pachycephalosaurus.getPose() != Pose.STANDING || pachycephalosaurus.isInWater() || pachycephalosaurus.isMobSitting()) {
            return false;
        } else {
            this.chargePartner = this.getChargePartner();
            return this.chargePartner != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.chargePartner != null && this.chargePartner.isAlive() && pachycephalosaurus.chargeCooldown == 0;
    }

    @Override
    public void start() {
        this.collisionTicks = 0;
        this.timer = 0;
    }

    @Override
    public void stop() {
        if (this.chargePartner != null) {
            this.chargePartner.setAttackState(0);
        }
        this.chargePartner = null;
        this.timer = 0;
        this.pachycephalosaurus.setAttackState(0);
    }

    @Override
    public void tick() {
        if (this.chargePartner != null) {
            double distanceToTarget = pachycephalosaurus.getPerceivedTargetDistanceSquareForMeleeAttack(chargePartner);
            double distanceToTargetPartner = chargePartner.getPerceivedTargetDistanceSquareForMeleeAttack(pachycephalosaurus);
            if (pachycephalosaurus.getAttackState() == 2 && chargePartner.getAttackState() == 2) {
                this.pachycephalosaurus.getNavigation().stop();
                this.chargePartner.getNavigation().stop();
                this.tickCharge();
            }
            else {
                if (distanceToTargetPartner > 50) {
                    this.chargePartner.getNavigation().moveTo(pachycephalosaurus, 1.2D);
                }
                else if (distanceToTarget > 50) {
                    this.pachycephalosaurus.getNavigation().moveTo(chargePartner, 1.2D);
                }
                else if (distanceToTarget <= 50 && distanceToTargetPartner <= 50 && !pachycephalosaurus.isInWater() && !chargePartner.isInWater() && !chargePartner.isMobSitting()) {
                    this.pachycephalosaurus.setAttackState(2);
                    this.chargePartner.setAttackState(2);
                }
            }
        }
    }

    protected void tickCharge() {
        this.timer++;
        int speedFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);

        if (timer == 1) {
            this.chargePartner.setPose(UP2Poses.WARNING.get());
            this.pachycephalosaurus.setPose(UP2Poses.WARNING.get());
        }
        if (timer < 50) {
            this.pachycephalosaurus.lookAt(chargePartner, 360F, 30F);
            this.pachycephalosaurus.getLookControl().setLookAt(chargePartner, 30F, 30F);
            this.chargePartner.lookAt(pachycephalosaurus, 360F, 30F);
            this.chargePartner.getLookControl().setLookAt(pachycephalosaurus, 30F, 30F);
        }
        if (timer > 50 && timer < 70) {
            this.chargeDirection = new Vec3(chargePartner.getX() - pachycephalosaurus.getX(), chargePartner.getY() - pachycephalosaurus.getY(), chargePartner.getZ() - pachycephalosaurus.getZ()).normalize();
            float YRot = Mth.approachDegrees(pachycephalosaurus.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 0.5F);
            float speed = 0.85F + effectSpeed;
            this.pachycephalosaurus.setYRot(YRot);
            this.pachycephalosaurus.setYBodyRot(YRot);
            this.pachycephalosaurus.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * speed, pachycephalosaurus.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * speed);
            this.knockbackPartner(pachycephalosaurus);

            this.partnerChargeDirection = new Vec3(pachycephalosaurus.getX() - chargePartner.getX(), pachycephalosaurus.getY() - chargePartner.getY(), pachycephalosaurus.getZ() - chargePartner.getZ()).normalize();
            float partnerYRot = Mth.approachDegrees(chargePartner.getYRot(), (float) (Mth.atan2(partnerChargeDirection.z, partnerChargeDirection.x) * (180F / Math.PI)) - 90.0F, 0.5F);
            this.chargePartner.setYRot(partnerYRot);
            this.chargePartner.setYBodyRot(partnerYRot);
            this.chargePartner.setDeltaMovement(-Mth.sin(partnerYRot * ((float) Math.PI / 180F)) * speed, pachycephalosaurus.getDeltaMovement().y, Mth.cos(partnerYRot * ((float) Math.PI / 180F)) * speed);
            this.knockbackPartner(chargePartner);
        }
        if (timer > 70 || collisionTicks > 10 || pachycephalosaurus.distanceToSqr(chargePartner) < 1.0D) {
            this.timer = 0;
            this.pachycephalosaurus.setPose(Pose.STANDING);
            this.pachycephalosaurus.setAttackState(0);
            this.pachycephalosaurus.chargeCooldown = 150 + pachycephalosaurus.getRandom().nextInt(150);

            this.chargePartner.setPose(Pose.STANDING);
            this.chargePartner.setAttackState(0);
            this.chargePartner.chargeCooldown = 150 + chargePartner.getRandom().nextInt(150);
        }
    }

    @Nullable
    private Pachycephalosaurus getChargePartner() {
        List<? extends Pachycephalosaurus> list = this.level.getNearbyEntities(Pachycephalosaurus.class, FRIEND_TARGETING, this.pachycephalosaurus, this.pachycephalosaurus.getBoundingBox().inflate(8.0D));
        double distance = Double.MAX_VALUE;
        Pachycephalosaurus pachycephalosaurus1 = null;
        for (Pachycephalosaurus pachycephalosaurus2 : list) {
            if (this.pachycephalosaurus.distanceToSqr(pachycephalosaurus2) < distance) {
                pachycephalosaurus1 = pachycephalosaurus2;
                distance = this.pachycephalosaurus.distanceToSqr(pachycephalosaurus2);
            }
        }
        return pachycephalosaurus1;
    }

    private void knockbackPartner(Pachycephalosaurus pachycephalosaurus) {
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
                entity.knockback((knockbackForce * speedForce) * 1.5F, knockbackDirection.x(), knockbackDirection.z());
            }
        }
    }
}