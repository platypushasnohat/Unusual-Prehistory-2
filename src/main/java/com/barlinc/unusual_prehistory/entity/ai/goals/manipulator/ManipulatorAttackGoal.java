package com.barlinc.unusual_prehistory.entity.ai.goals.manipulator;

import com.barlinc.unusual_prehistory.entity.Manipulator;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class ManipulatorAttackGoal extends AttackGoal {

    protected final Manipulator manipulator;

    public ManipulatorAttackGoal(Manipulator manipulator) {
        super(manipulator);
        this.manipulator = manipulator;
    }

    @Override
    public void tick() {
        LivingEntity target = manipulator.getTarget();
        if (target != null) {
            double distance = manipulator.distanceToSqr(target);
            this.manipulator.lookAt(manipulator.getTarget(), 30F, 30F);
            this.manipulator.getLookControl().setLookAt(target, 30F, 30F);

            if (this.manipulator.getAttackState() == 1) {
                this.manipulator.getNavigation().stop();
                this.tickAttack();
            } else {
                this.manipulator.getNavigation().moveTo(target, 1.3D);
                if (distance <= this.getAttackReachSqr(target) && manipulator.attackCooldown == 0) {
                    this.manipulator.setAttackState(1);
                }
            }
        }
    }

    protected void tickAttack() {
        this.timer++;
        LivingEntity target = manipulator.getTarget();
        if (timer == 1) manipulator.setPose(UP2Poses.ATTACKING.get());
        if (timer == 7) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.manipulator.doHurtTarget(target);
                this.manipulator.swing(InteractionHand.OFF_HAND);
            }
        }
        if (timer == 17) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.manipulator.doHurtTarget(target);
                this.manipulator.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 30) {
            this.timer = 0;
            this.manipulator.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}