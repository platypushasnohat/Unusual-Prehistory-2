package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Therizinosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class TherizinosaurusAttackGoal extends AttackGoal {

    private final Therizinosaurus therizinosaurus;
    private int collisionTicks;
    private Vec3 chargeDirection;

    public TherizinosaurusAttackGoal(Therizinosaurus therizinosaurus) {
        super(therizinosaurus);
        this.therizinosaurus = therizinosaurus;
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
        LivingEntity target = therizinosaurus.getTarget();
        if (target != null) {
            if (!this.isInChargingPose()) {
                this.therizinosaurus.lookAt(therizinosaurus.getTarget(), 30F, 30F);
                this.therizinosaurus.getLookControl().setLookAt(therizinosaurus.getTarget(), 30F, 30F);
            }

            double distance = therizinosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = therizinosaurus.getAttackState();

            if (therizinosaurus.horizontalCollision) collisionTicks++;
            else if (collisionTicks > 0) collisionTicks--;

            if (this.isInChargingPose() || attackState == 1 || attackState == 3 || attackState == 4) therizinosaurus.getNavigation().stop();

            if (attackState == 1) {
                this.tickSlash();
            } else if (attackState == 2) {
                this.therizinosaurus.getNavigation().moveTo(target, 1.3D);
                this.tickSlashRush();
            } else if (attackState == 3) {
                this.tickCharge();
            }  else if (attackState == 4) {
                this.tickChargeEnd();
            } else {
                if (!this.isInChargingPose()) therizinosaurus.getNavigation().moveTo(target, 1.6D);
                if (distance < 14 && therizinosaurus.chargeCooldown > 0 && !this.isInChargingPose()) {
                    if (therizinosaurus.getRandom().nextFloat() < 0.8F && therizinosaurus.slashCooldown == 0) therizinosaurus.setAttackState(1);
                    else if (therizinosaurus.slashRushCooldown == 0) therizinosaurus.setAttackState(2);
                } else if (distance < 60 && therizinosaurus.chargeCooldown == 0 && !this.isInChargingPose() && !therizinosaurus.isInWater() && therizinosaurus.isWithinYRange(target)) {
                    this.therizinosaurus.setAttackState(3);
                }
            }
        }
    }

    private boolean isInChargingPose() {
        return therizinosaurus.getPose() == UP2Poses.STOP_CHARGING.get() || therizinosaurus.getPose() == UP2Poses.CHARGING.get() || therizinosaurus.getPose() == UP2Poses.START_CHARGING.get();
    }

    protected void tickSlash() {
        this.timer++;
        LivingEntity target = therizinosaurus.getTarget();

        if (timer == 1) therizinosaurus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 7) therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_ATTACK.get(), 1.0F, therizinosaurus.getVoicePitch());
        if (timer == 14) {
            if (therizinosaurus.distanceTo(target) <= this.getAttackReachSqr(target)) {
                this.therizinosaurus.swing(InteractionHand.MAIN_HAND);
                this.therizinosaurus.doHurtTarget(target);
                this.therizinosaurus.strongKnockback(target, 0.5D, 0.0D);
            }
        }
        if (timer > 20) {
            this.timer = 0;
            this.therizinosaurus.slashCooldown = 2 + therizinosaurus.getRandom().nextInt(2);
            this.therizinosaurus.setAttackState(0);
        }
    }

    protected void tickSlashRush() {
        this.timer++;
        LivingEntity target = therizinosaurus.getTarget();

        if (timer == 1) therizinosaurus.setPose(UP2Poses.SLASH_RUSH.get());
        if (timer == 2 || timer == 12 || timer == 22 || timer == 32 || timer == 42) therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_ATTACK.get(), 1.0F, therizinosaurus.getVoicePitch() * 1.1F);
        if (timer == 8 || timer == 18 || timer == 28 || timer == 38 || timer == 48) {
            this.therizinosaurus.swing(InteractionHand.MAIN_HAND);
            this.therizinosaurus.doHurtTarget(target);
            this.therizinosaurus.strongKnockback(target, 0.4D, 0.0D);
        }
        if (timer > 50) {
            this.timer = 0;
            this.therizinosaurus.slashRushCooldown();
            this.therizinosaurus.setAttackState(0);
        }
    }

    protected void tickCharge() {
        this.timer++;
        LivingEntity target = therizinosaurus.getTarget();
        int speedFactor = therizinosaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? therizinosaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = therizinosaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? therizinosaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);

        if (timer == 1) {
            therizinosaurus.setPose(UP2Poses.START_CHARGING.get());
            therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_WARN.get(), 3.0F, therizinosaurus.getVoicePitch());
        }

        if (timer < 30) {
            this.therizinosaurus.lookAt(target, 360F, 30F);
            this.therizinosaurus.getLookControl().setLookAt(target, 30F, 30F);
        }

        if (timer > 30 && timer < 45) {
            this.chargeDirection = new Vec3(target.getX() - therizinosaurus.getX(), target.getY() - therizinosaurus.getY(), target.getZ() - therizinosaurus.getZ()).normalize();
            float YRot = Mth.approachDegrees(therizinosaurus.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 1.0F);
            float speed = 0.6F + effectSpeed;
            this.therizinosaurus.setYRot(YRot);
            this.therizinosaurus.setYBodyRot(YRot);
            this.therizinosaurus.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * speed, therizinosaurus.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * speed);
        }

        if (timer > 45 || collisionTicks > 10 || (timer > 30 && therizinosaurus.distanceTo(target) < therizinosaurus.getBbWidth() + target.getBbWidth() + 1.3D)) {
            this.timer = 0;
            this.therizinosaurus.setAttackState(4);
        }
    }

    protected void tickChargeEnd() {
        this.timer++;
        LivingEntity target = therizinosaurus.getTarget();
        if (timer == 1) therizinosaurus.setPose(UP2Poses.STOP_CHARGING.get());
        if (timer == 14) therizinosaurus.playSound(UP2SoundEvents.THERIZINOSAURUS_ATTACK.get(), 1.0F, therizinosaurus.getVoicePitch() * 0.9F);
        if (timer == 22) {
            if (therizinosaurus.distanceTo(target) <= this.getAttackReachSqr(target)) {
                this.therizinosaurus.swing(InteractionHand.MAIN_HAND);
                this.therizinosaurus.doHurtTarget(target);
                this.therizinosaurus.strongKnockback(target, 0.7D, 0.0D);
            }
        }
        if (timer > 40) {
            this.timer = 0;
            this.therizinosaurus.chargeCooldown();
            this.therizinosaurus.setAttackState(0);
            this.chargeDirection = Vec3.ZERO;
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.4F * this.mob.getBbWidth() * 1.4F + target.getBbWidth();
    }
}
