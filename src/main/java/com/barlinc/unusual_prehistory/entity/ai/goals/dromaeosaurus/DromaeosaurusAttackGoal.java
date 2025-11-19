package com.barlinc.unusual_prehistory.entity.ai.goals.dromaeosaurus;

import com.barlinc.unusual_prehistory.entity.Dromaeosaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

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
            double distanceToTarget = this.dromaeosaurus.getPerceivedTargetDistanceSquareForMeleeAttack(target);

            this.dromaeosaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (this.getAttackReachSqr(target) > 0) {
                this.dromaeosaurus.getNavigation().moveTo(target, 1.0D);
            }

            if (this.dromaeosaurus.getAttackState() == 1) {
                timer++;
                if (timer == 6) {
                    if (this.dromaeosaurus.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                        this.dromaeosaurus.doHurtTarget(target);
                        this.dromaeosaurus.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer >= 15) {
                    timer = 0;
                    this.dromaeosaurus.setAttackState(0);
                }
            } else if (distanceToTarget <= this.getAttackReachSqr(target)) {
                this.dromaeosaurus.setAttackState(1);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}