package com.barlinc.unusual_prehistory.entity.ai.goals.dunkleosteus;

import com.barlinc.unusual_prehistory.entity.Dunkleosteus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class DunkleosteusAttackGoal extends AttackGoal {

    private final Dunkleosteus dunkleosteus;

    public DunkleosteusAttackGoal(Dunkleosteus dunkleosteus) {
        super(dunkleosteus);
        this.dunkleosteus = dunkleosteus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.dunkleosteus.getTarget().isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.dunkleosteus.getTarget().isInWater();
    }

    @Override
    public void tick() {
        LivingEntity target = this.dunkleosteus.getTarget();
        if (target != null) {
            this.dunkleosteus.lookAt(target, 30F, 30F);
            this.dunkleosteus.getLookControl().setLookAt(target, 30F, 30F);
            double distance = this.dunkleosteus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.dunkleosteus.getAttackState();

            this.dunkleosteus.getNavigation().moveTo(target, 1.3D);
            if (distance <= getAttackReachSqr(target)) {
                this.dunkleosteus.setAttackState(1);
            }
            if (attackState == 1) {
                timer++;
                if (timer == 2) {
                    this.dunkleosteus.playSound(dunkleosteus.getBiteSound(), 1.0F, 1.0F);
                }
                if (timer == 5) {
                    if (this.dunkleosteus.distanceTo(target) < getAttackReachSqr(target)) {
                        this.dunkleosteus.doHurtTarget(this.dunkleosteus.getTarget());
                        this.dunkleosteus.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer >= 26) {
                    timer = 0;
                    this.dunkleosteus.setAttackState(0);
                }
            }
        }
    }
}