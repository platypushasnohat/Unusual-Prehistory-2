package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Leptictidium;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class LeptictidiumAttackGoal extends AttackGoal {

    private final Leptictidium leptictidium;

    public LeptictidiumAttackGoal(Leptictidium leptictidium) {
        super(leptictidium);
        this.leptictidium = leptictidium;
    }

    @Override
    public void tick() {
        LivingEntity target = leptictidium.getTarget();
        if (target != null) {
            double distance = leptictidium.distanceToSqr(target);
            this.leptictidium.lookAt(leptictidium.getTarget(), 30F, 30F);
            this.leptictidium.getLookControl().setLookAt(target, 30F, 30F);

            if (leptictidium.getAttackState() == 1) {
                this.leptictidium.getNavigation().stop();
                this.tickAttack();
            } else {
                this.leptictidium.getNavigation().moveTo(target, 1.7D);
                if (distance <= this.getAttackReachSqr(target) && leptictidium.attackCooldown == 0) {
                    this.leptictidium.setAttackState(1);
                }
            }
        }
    }

    protected void tickAttack() {
        this.timer++;
        LivingEntity target = leptictidium.getTarget();
        if (timer == 1) leptictidium.setPose(UP2Poses.ATTACKING.get());
        if (timer == 8) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.leptictidium.doHurtTarget(target);
                this.leptictidium.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 20) {
            this.timer = 0;
            this.leptictidium.setAttackState(0);
            this.leptictidium.attackCooldown = 5 + leptictidium.getRandom().nextInt(5);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}