package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Majungasaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

import java.util.Objects;

public class MajungasaurusAttackGoal extends AttackGoal {

    protected final Majungasaurus majungasaurus;

    public MajungasaurusAttackGoal(Majungasaurus majungasaurus) {
        super(majungasaurus);
        this.majungasaurus = majungasaurus;
    }

    @Override
    public void start() {
        super.start();
        this.majungasaurus.setPose(Pose.STANDING);
        this.majungasaurus.exitStealth();
    }

    @Override
    public void stop() {
        super.stop();
        this.majungasaurus.setPose(Pose.STANDING);
        this.majungasaurus.exitStealth();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.majungasaurus.isBaby();
    }

    @Override
    public void tick() {
        LivingEntity target = this.majungasaurus.getTarget();
        if (target != null) {
            double distanceToTarget = this.majungasaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
            int attackState = this.majungasaurus.getAttackState();

            this.majungasaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (distanceToTarget > 50 && this.majungasaurus.getStealthCooldown() <= 0 && !this.majungasaurus.isInWaterOrBubble()) {
                this.majungasaurus.enterStealth();
                this.majungasaurus.getNavigation().moveTo(target, 1.0D);
            }
            if (distanceToTarget <= 50 || this.majungasaurus.getStealthCooldown() > 0) {
                this.majungasaurus.exitStealth();
                this.majungasaurus.getNavigation().moveTo(target, 1.7D);
            }

            if (attackState == 1) tickBite();
            else if (distanceToTarget <= this.getAttackReachSqr(target)) {
                this.majungasaurus.setAttackState(1);
            }
        }
    }

    protected void tickBite() {
        timer++;
        LivingEntity target = this.majungasaurus.getTarget();
        if (timer == 1) majungasaurus.setPose(UP2Poses.BITING.get());
        if (this.timer == 9) this.majungasaurus.playSound(UP2SoundEvents.MAJUNGASAURUS_BITE.get(), 1.0F, majungasaurus.getVoicePitch());
        if (timer == 11) {
            if (this.majungasaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                this.majungasaurus.doHurtTarget(target);
                this.majungasaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 22) {
            timer = 0;
            this.majungasaurus.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.8F * this.mob.getBbWidth() * 1.8 + target.getBbWidth();
    }
}