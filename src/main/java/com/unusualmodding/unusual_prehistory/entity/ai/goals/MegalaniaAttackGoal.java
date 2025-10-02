package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Megalania;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class MegalaniaAttackGoal extends AttackGoal {

    private int timer = 0;
    private final Megalania megalania;

    public MegalaniaAttackGoal(Megalania megalania) {
        super(megalania);
        this.megalania = megalania;
    }

    @Override
    public void tick() {
        LivingEntity target = this.megalania.getTarget();
        if (target != null) {
            this.megalania.lookAt(this.megalania.getTarget(), 30F, 30F);
            this.megalania.getLookControl().setLookAt(this.megalania.getTarget(), 30F, 30F);

            double distance = this.megalania.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.megalania.getAttackState();

            if (attackState == 1) {
                this.timer++;
                if (this.timer == 11) {
                    if (this.megalania.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReachSqr(target)) {
                        this.megalania.swing(InteractionHand.MAIN_HAND);
                        this.megalania.doHurtTarget(target);
                    }
                }
                if (this.timer > 22) {
                    this.timer = 0;
                    this.megalania.setAttackState(0);
                }
                this.megalania.getNavigation().moveTo(target, 2.4D);
            } else {
                this.megalania.getNavigation().moveTo(target, 2.4D);
                if (distance <= this.getAttackReachSqr(target)) {
                    if (this.megalania.getRandom().nextBoolean()) {
                        this.megalania.setAttackState(1);
                    } else {
                        this.megalania.setAttackState(2);
                    }
                }
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}
