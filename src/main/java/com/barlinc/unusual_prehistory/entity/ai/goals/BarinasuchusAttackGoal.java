package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Barinasuchus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class BarinasuchusAttackGoal extends AttackGoal {

    protected final Barinasuchus barinasuchus;

    public BarinasuchusAttackGoal(Barinasuchus barinasuchus) {
        super(barinasuchus);
        this.barinasuchus = barinasuchus;
    }

    @Override
    public void tick() {
        LivingEntity target = barinasuchus.getTarget();
        if (target != null) {
            double distance = barinasuchus.distanceToSqr(target);
            this.barinasuchus.lookAt(barinasuchus.getTarget(), 30F, 30F);
            this.barinasuchus.getLookControl().setLookAt(target, 30F, 30F);

            if (this.barinasuchus.getAttackState() == 1) {
                this.barinasuchus.getNavigation().moveTo(target, 1.5D);
                this.tickBite();
            } else {
                this.barinasuchus.getNavigation().moveTo(target, 1.8D);
                if (distance <= this.getAttackReachSqr(target) && barinasuchus.attackCooldown == 0) {
                    this.barinasuchus.setAttackState(1);
                }
            }
        }
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = barinasuchus.getTarget();
        if (timer == 1) barinasuchus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 7) barinasuchus.playSound(UP2SoundEvents.BARINASUCHUS_ATTACK.get(), 1.0F, 0.9F + barinasuchus.getRandom().nextFloat() * 0.2F);
        if (timer == 11) {
            if (this.barinasuchus.distanceTo(target) < this.getAttackReachSqr(target)) {
                this.barinasuchus.doHurtTarget(target);
                this.barinasuchus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 15) {
            this.timer = 0;
            this.barinasuchus.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.4F * this.mob.getBbWidth() * 1.4F + target.getBbWidth();
    }
}