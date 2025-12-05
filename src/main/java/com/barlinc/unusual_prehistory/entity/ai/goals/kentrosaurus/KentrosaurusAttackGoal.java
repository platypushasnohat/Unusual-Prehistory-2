package com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus;

import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class KentrosaurusAttackGoal extends AttackGoal {

    private final Kentrosaurus kentrosaurus;

    public KentrosaurusAttackGoal(Kentrosaurus kentrosaurus) {
        super(kentrosaurus);
        this.kentrosaurus = kentrosaurus;
    }

    @Override
    public void tick() {
        LivingEntity target = this.kentrosaurus.getTarget();
        if (target != null) {
            this.kentrosaurus.lookAt(this.kentrosaurus.getTarget(), 30F, 30F);
            this.kentrosaurus.getLookControl().setLookAt(this.kentrosaurus.getTarget(), 30F, 30F);
            double distance = this.kentrosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.kentrosaurus.getAttackState();

            if (attackState == 1) {
                this.timer++;
                if (timer == 1) kentrosaurus.setPose(UP2Poses.TAIL_WHIPPING.get());
                if (timer == 19) {
                    if (this.kentrosaurus.distanceTo(target) <= this.getAttackReachSqr(target)) {
                        this.kentrosaurus.swing(InteractionHand.MAIN_HAND);
                        this.kentrosaurus.doHurtTarget(target);
                    }
                }
                if (timer > 40) {
                    this.timer = 0;
                    this.kentrosaurus.setAttackState(0);
                }
            } else {
                this.kentrosaurus.getNavigation().moveTo(target, 1.7D);
                if (distance <= this.getAttackReachSqr(target)) {
                    this.kentrosaurus.setAttackState(1);
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.kentrosaurus.getBbWidth() * 1.5F * this.kentrosaurus.getBbWidth() * 1.5F + target.getBbWidth();
    }
}
