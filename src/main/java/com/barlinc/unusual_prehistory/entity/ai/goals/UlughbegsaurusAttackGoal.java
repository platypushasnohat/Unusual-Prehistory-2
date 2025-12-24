package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Ulughbegsaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class UlughbegsaurusAttackGoal extends AttackGoal {

    protected final Ulughbegsaurus ulughbegsaurus;

    public UlughbegsaurusAttackGoal(Ulughbegsaurus ulughbegsaurus) {
        super(ulughbegsaurus);
        this.ulughbegsaurus = ulughbegsaurus;
    }

    @Override
    public void tick() {
        LivingEntity target = ulughbegsaurus.getTarget();
        if (target != null) {
            double distance = ulughbegsaurus.distanceToSqr(target);
            this.ulughbegsaurus.lookAt(ulughbegsaurus.getTarget(), 30F, 30F);
            this.ulughbegsaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (this.ulughbegsaurus.getAttackState() == 1) {
                this.ulughbegsaurus.getNavigation().moveTo(target, 1.5D);
                this.tickBite();
            } else {
                this.ulughbegsaurus.getNavigation().moveTo(target, 1.7D);
                if (distance <= this.getAttackReachSqr(target) && ulughbegsaurus.attackCooldown == 0) {
                    this.ulughbegsaurus.setAttackState(1);
                }
            }
        }
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = ulughbegsaurus.getTarget();
        if (timer == 1) ulughbegsaurus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 8) ulughbegsaurus.playSound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK.get(), 1.0F, 0.9F + ulughbegsaurus.getRandom().nextFloat() * 0.2F);
        if (timer == 10) {
            if (this.ulughbegsaurus.distanceTo(target) < getAttackReachSqr(target)) {
                this.ulughbegsaurus.doHurtTarget(target);
                this.ulughbegsaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 15) {
            this.timer = 0;
            this.ulughbegsaurus.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}