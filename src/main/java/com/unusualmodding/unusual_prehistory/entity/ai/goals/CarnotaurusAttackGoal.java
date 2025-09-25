package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Objects;

public class CarnotaurusAttackGoal extends Goal {

    private int timer = 0;
    private Carnotaurus carnotaurus;

    public CarnotaurusAttackGoal(Carnotaurus carnotaurus) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.carnotaurus = carnotaurus;
    }

    @Override
    public boolean canUse() {
        return !this.carnotaurus.isBaby() && this.carnotaurus.getTarget() != null && this.carnotaurus.getTarget().isAlive();
    }

    @Override
    public void start() {
        this.carnotaurus.setAttackState(0);
        this.timer = 0;
    }

    @Override
    public void stop() {
        this.carnotaurus.setAttackState(0);
    }

    @Override
    public void tick() {
        LivingEntity target = this.carnotaurus.getTarget();
        if (target != null) {
            this.carnotaurus.lookAt(this.carnotaurus.getTarget(), 30F, 30F);
            this.carnotaurus.getLookControl().setLookAt(this.carnotaurus.getTarget(), 30F, 30F);

            double distance = this.carnotaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.carnotaurus.getAttackState();

            if (attackState == 1) {
                tickBite();
            } else if (attackState == 2) {
                tickHeadbutt();
            } else {
                this.carnotaurus.getNavigation().moveTo(target, 1.8D);
                if (distance <= 8) {
                    if (this.carnotaurus.getRandom().nextBoolean()) {
                        this.carnotaurus.setAttackState(1);
                    } else {
                        this.carnotaurus.setAttackState(2);
                    }
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = this.carnotaurus.getTarget();
        this.carnotaurus.getNavigation().stop();

        if (this.timer == 11) {
            if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReach(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
            }
        }
        if (this.timer > 22) {
            this.timer = 0;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickHeadbutt() {
        this.timer++;
        LivingEntity target = this.carnotaurus.getTarget();
        this.carnotaurus.getNavigation().stop();

        if (this.timer == 12) {
            if (this.carnotaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReach(target)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
                this.carnotaurus.strongKnockback(target, 1.5D, 0.5D);
            }
        }
        if (this.timer > 28) {
            this.timer = 0;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected double getAttackReach(LivingEntity target) {
        return this.carnotaurus.getBbWidth() * 1.1F * this.carnotaurus.getBbWidth() * 1.1F + target.getBbWidth();
    }
}
