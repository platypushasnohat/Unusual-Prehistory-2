package com.barlinc.unusual_prehistory.entity.ai.goals.update_6;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Concavenator;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;

public class ConcavenatorAttackGoal extends AttackGoal {

    private final Concavenator concavenator;
    private int circlingTime;
    private int maxCirclingTime;
    private boolean clockwise;

    public ConcavenatorAttackGoal(Concavenator concavenator) {
        super(concavenator);
        this.concavenator = concavenator;
    }

    @Override
    public void start() {
        super.start();
        this.circlingTime = 0;
        this.maxCirclingTime = 50 + concavenator.getRandom().nextInt(50);
        this.clockwise = concavenator.getRandom().nextBoolean();
    }

    @Override
    public void tick() {
        LivingEntity target = concavenator.getTarget();
        if (target != null) {
            double distance = concavenator.distanceTo(target);
            int attackState = concavenator.getAttackState();

            if (attackState == 1) {
                this.tickAttack(target);
                this.concavenator.getNavigation().stop();
                return;
            }
            else if (attackState == 2) {
                this.tickDiveAttack(target);
                this.concavenator.getNavigation().stop();
                return;
            }

            if (concavenator.isSandSwimming() && attackState == 0 && concavenator.getPose() == Pose.STANDING) {
                this.tickCircling(target, distance);
            } else {
                this.concavenator.lookAt(target, 30F, 30F);
                this.concavenator.getLookControl().setLookAt(target, 30F, 30F);
                this.concavenator.getNavigation().moveTo(target, 1.6D);
                this.circlingTime = 0;
                if (distance < 2.0D && concavenator.attackCooldown == 0 && concavenator.getPose() == Pose.STANDING) {
                    this.concavenator.setAttackState(1);
                }
            }
        }
    }

    private void tickCircling(LivingEntity target, double distance) {
        if (circlingTime < maxCirclingTime && distance <= 25.0D) {
            this.circlingTime++;
            Vec3 orbitPos = this.getCirclePos(target, 10.0F + concavenator.getRandom().nextFloat() * 2.0F);
            this.concavenator.getNavigation().moveTo(orbitPos.x, orbitPos.y, orbitPos.z, 1.2D);
            this.concavenator.lookAt(EntityAnchorArgument.Anchor.EYES, orbitPos);
        }
        else {
            this.concavenator.lookAt(target, 30F, 30F);
            this.concavenator.getLookControl().setLookAt(target, 30F, 30F);
            this.concavenator.getNavigation().moveTo(target, 1.4D);
            if (distance <= 2.0D && concavenator.attackCooldown == 0) {
                this.concavenator.setAttackState(1);
                this.circlingTime = 0;
            }
            else if (concavenator.diveAttackCooldown == 0 && distance >= 6.5D && distance < 11.0D) {
                this.concavenator.setAttackState(2);
                this.circlingTime = 0;
            }
        }
        if (distance > 25.0D) {
            this.concavenator.getNavigation().moveTo(target, 1.2D);
        }
    }

    protected void tickAttack(LivingEntity target) {
        this.timer++;
        if (timer == 1) concavenator.setPose(UP2Poses.ATTACKING.get());
        if (timer == 4) concavenator.playSound(UP2SoundEvents.MAJUNGASAURUS_ATTACK.get(), 1.0F, 0.9F + concavenator.getRandom().nextFloat() * 0.2F);
        if (timer == 10) {
            if (this.isInAttackRange(target, 1.6D) && target != null) {
                this.concavenator.doHurtTarget(target);
                this.concavenator.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 15) {
            this.timer = 0;
            this.concavenator.attackCooldown = 7;
            this.concavenator.setPose(Pose.STANDING);
            this.concavenator.setAttackState(0);
        }
    }

    protected void tickDiveAttack(LivingEntity target) {
        this.timer++;
        if (timer < 15) {
            this.concavenator.lookAt(target, 30F, 30F);
            this.concavenator.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer == 15) concavenator.setPose(UP2Poses.CHARGING.get());
        if (timer > 15 && timer < 35) {
            this.chargeAtTarget(target, 0.54F);
        }
        if (timer == 35) concavenator.playSound(UP2SoundEvents.MAJUNGASAURUS_ATTACK.get(), 1.0F, 0.9F + concavenator.getRandom().nextFloat() * 0.2F);
        if (timer == 38) {
            if (this.isInAttackRange(target, 2.0D)) {
                this.concavenator.doHurtTarget(target);
                this.concavenator.strongKnockback(target, 2.0D, 0.15D);
                this.concavenator.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 45) {
            this.timer = 0;
            this.concavenator.setPose(Pose.STANDING);
            this.concavenator.diveAttackCooldown = maxCirclingTime + 20;
            this.clockwise = concavenator.getRandom().nextBoolean();
            this.circlingTime = 0;
            this.concavenator.setAttackState(0);
        }
    }

    public Vec3 getCirclePos(LivingEntity target, float circleDistance) {
        final float angle = (float) (Math.toRadians((clockwise ? -circlingTime : circlingTime) * 4.0F));
        final double extraX = circleDistance * Mth.sin(angle);
        final double extraZ = circleDistance * Mth.cos(angle);
        return target.position().add(extraX, 0, extraZ);
    }
}