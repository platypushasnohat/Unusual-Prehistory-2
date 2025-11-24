package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Onchopristis;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class OnchopristisAttackGoal extends AttackGoal {

    private final Onchopristis onchopristis;

    public OnchopristisAttackGoal(Onchopristis onchopristis) {
        super(onchopristis);
        this.onchopristis = onchopristis;
    }

    @Override
    public void tick() {
        LivingEntity target = onchopristis.getTarget();
        if (target != null && target.isInWater()) {
            onchopristis.lookAt(target, 30F, 30F);
            onchopristis.getLookControl().setLookAt(target, 30F, 30F);

            double distance = onchopristis.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = onchopristis.getAttackState();

            onchopristis.getNavigation().moveTo(target, 1.5D);
            if (distance <= 4) {
                onchopristis.setAttackState(1);
            }
            if (attackState == 1) {
                timer++;
                if (timer == 1) onchopristis.setPose(UP2Poses.ATTACKING.get());
                if (timer == 9) {
                    if (onchopristis.distanceTo(target) < getAttackReachSqr(target)) {
                        onchopristis.doHurtTarget(target);
                        onchopristis.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer > 20) {
                    timer = 0;
                    onchopristis.setAttackState(0);
                }
            }
        }
    }
}