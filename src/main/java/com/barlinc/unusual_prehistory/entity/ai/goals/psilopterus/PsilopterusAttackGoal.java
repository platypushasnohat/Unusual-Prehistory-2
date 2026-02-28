package com.barlinc.unusual_prehistory.entity.ai.goals.psilopterus;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Psilopterus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class PsilopterusAttackGoal extends AttackGoal {

    protected final Psilopterus psilopterus;

    public PsilopterusAttackGoal(Psilopterus psilopterus) {
        super(psilopterus);
        this.psilopterus = psilopterus;
    }

    @Override
    public void tick() {
        LivingEntity target = psilopterus.getTarget();
        if (target != null) {
            double distanceToTarget = psilopterus.getPerceivedTargetDistanceSquareForMeleeAttack(target);
            this.psilopterus.getLookControl().setLookAt(target, 30F, 30F);

            if (psilopterus.getAttackState() == 1) {
                this.tickPeck();
                this.psilopterus.getNavigation().moveTo(target, 1.3D);
            }
            else if (psilopterus.getAttackState() == 2) {
                this.tickKick();
            }
            else {
                if (distanceToTarget < this.getAttackReachSqr(target)) {
                    if (psilopterus.getRandom().nextFloat() < 0.3F && !psilopterus.isInWater()) psilopterus.setAttackState(2);
                    else psilopterus.setAttackState(1);
                }
                this.psilopterus.getNavigation().moveTo(target, 1.5D);
            }
        }
    }

    protected void tickPeck() {
        timer++;
        LivingEntity target = this.psilopterus.getTarget();
        if (timer == 1) {
            this.psilopterus.setPose(UP2Poses.ATTACKING.get());
            this.psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_BITE.get(), 1.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 5) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.psilopterus.doHurtTarget(target);
                this.psilopterus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 10) {
            this.timer = 0;
            this.psilopterus.setAttackState(0);
        }
    }

    protected void tickKick() {
        timer++;
        LivingEntity target = this.psilopterus.getTarget();
        this.psilopterus.getNavigation().stop();
        if (timer == 1) this.psilopterus.setPose(UP2Poses.KICKING.get());
        if (timer == 12) this.psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_ATTACK.get(), 1.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.2F);
        if (timer == 14) {
            if (this.isInAttackRange(target, 1.75D)) {
                this.psilopterus.doHurtTarget(target);
                this.psilopterus.strongKnockback(target, 0.75D, 0.025D);
                this.psilopterus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 30) {
            this.timer = 0;
            this.psilopterus.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.8F * this.mob.getBbWidth() * 1.8F + target.getBbWidth();
    }
}