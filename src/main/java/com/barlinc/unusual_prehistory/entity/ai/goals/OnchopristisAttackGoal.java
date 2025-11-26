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
    public boolean canUse() {
        return super.canUse() && (onchopristis.getTarget().isInWater() || !onchopristis.isInWater());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && (onchopristis.getTarget().isInWater() || !onchopristis.isInWater());
    }

    @Override
    public void tick() {
        LivingEntity target = onchopristis.getTarget();
        if (target != null && (target.isInWater() || !onchopristis.isInWater())) {
            onchopristis.lookAt(target, 30F, 30F);
            onchopristis.getLookControl().setLookAt(target, 30F, 30F);

            double distance = onchopristis.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = onchopristis.getAttackState();

            onchopristis.getNavigation().moveTo(target, 1.5D);
            if (distance < this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 2.5F + target.getBbWidth()) {
                onchopristis.setAttackState(1);
            }
            if (attackState == 1) {
                timer++;
                if (timer == 1) onchopristis.setPose(UP2Poses.ATTACKING.get());
                if (timer == 9) {
                    if (onchopristis.distanceTo(target) < this.getAttackReachSqr(target)) {
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