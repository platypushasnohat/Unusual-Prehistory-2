package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.update_1.Majungasaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class MajungasaurusAttackGoal extends AttackGoal {

    protected final Majungasaurus majungasaurus;

    public MajungasaurusAttackGoal(Majungasaurus majungasaurus) {
        super(majungasaurus);
        this.majungasaurus = majungasaurus;
    }

    @Override
    public void start() {
        super.start();
        this.majungasaurus.setCamo(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.majungasaurus.setCamo(false);
        this.majungasaurus.camoCooldown();
    }

    @Override
    public void tick() {
        LivingEntity target = majungasaurus.getTarget();
        if (target != null) {
            double distance = majungasaurus.distanceToSqr(target);
            this.majungasaurus.getLookControl().setLookAt(target, 30F, 30F);
            if (distance > 25 && majungasaurus.getCamoCooldown() == 0 && !majungasaurus.isInWaterOrBubble()) {
                this.majungasaurus.setCamo(true);
                this.majungasaurus.getNavigation().moveTo(target, 0.9D);
            }
            if (distance <= 25 || majungasaurus.getCamoCooldown() > 0) {
                this.majungasaurus.setCamo(false);
                this.majungasaurus.camoCooldown();
                this.majungasaurus.getNavigation().moveTo(target, 2.0D);
            }

            if (majungasaurus.getAttackState() == 1) this.tickAttack();
            else if (distance <= this.getAttackReachSqr(target)) {
                this.majungasaurus.setAttackState(1);
            }
        }
    }

    protected void tickAttack() {
        this.timer++;
        LivingEntity target = majungasaurus.getTarget();
        if (timer == 1) {
            this.majungasaurus.attackAlt = majungasaurus.getRandom().nextBoolean();
            this.majungasaurus.setPose(UP2Poses.ATTACKING.get());
        }
        if (timer == 9) majungasaurus.playSound(UP2SoundEvents.MAJUNGASAURUS_BITE.get(), 1.0F, 0.9F + majungasaurus.getRandom().nextFloat() * 0.25F);
        if (timer == 11) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.majungasaurus.doHurtTarget(target);
                this.majungasaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 17) {
            this.timer = 0;
            this.majungasaurus.setPose(Pose.STANDING);
            this.majungasaurus.setAttackState(0);
        }
    }
}