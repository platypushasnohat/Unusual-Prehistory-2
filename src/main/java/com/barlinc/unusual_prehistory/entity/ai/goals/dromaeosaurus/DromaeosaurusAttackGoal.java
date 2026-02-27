package com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus;

import com.barlinc.unusual_prehistory.entity.Dromaeosaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class DromaeosaurusAttackGoal extends AttackGoal {

    protected final Dromaeosaurus dromaeosaurus;

    public DromaeosaurusAttackGoal(Dromaeosaurus dromaeosaurus) {
        super(dromaeosaurus);
        this.dromaeosaurus = dromaeosaurus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.dromaeosaurus.getHealth() >= this.dromaeosaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.dromaeosaurus.getHealth() >= this.dromaeosaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public void tick() {
        LivingEntity target = this.dromaeosaurus.getTarget();
        if (target != null) {
            double distance = this.dromaeosaurus.distanceToSqr(target);
            this.dromaeosaurus.getLookControl().setLookAt(target, 30F, 30F);
            this.dromaeosaurus.getNavigation().moveTo(target, 1.0D);
            if (this.dromaeosaurus.getAttackState() == 1) {
                this.tickAttack();
            } else if (distance <= this.getAttackReachSqr(target)) {
                this.dromaeosaurus.setAttackState(1);
            }
        }
    }

    private void tickAttack() {
        this.timer++;
        LivingEntity target = dromaeosaurus.getTarget();
        if (timer == 1) dromaeosaurus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 6) {
            if (this.isInAttackRange(target, 1.25D)) {
                this.dromaeosaurus.doHurtTarget(target);
                this.dromaeosaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 15) {
            this.timer = 0;
            this.dromaeosaurus.setPose(Pose.STANDING);
            this.dromaeosaurus.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}