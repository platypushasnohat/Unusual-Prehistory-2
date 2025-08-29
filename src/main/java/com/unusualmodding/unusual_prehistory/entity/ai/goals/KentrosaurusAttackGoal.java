package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Objects;

public class KentrosaurusAttackGoal extends Goal {
    private int attackTime = 0;
    private Kentrosaurus kentrosaurus;

    public KentrosaurusAttackGoal(Kentrosaurus kentrosaurus) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
        this.kentrosaurus = kentrosaurus;
    }

    @Override
    public boolean canUse() {
        return !this.kentrosaurus.isBaby() && this.kentrosaurus.getTarget() != null && this.kentrosaurus.getTarget().isAlive();
    }

    @Override
    public void start() {
        this.kentrosaurus.setAttackState(0);
        this.attackTime = 0;
    }

    @Override
    public void stop() {
        this.kentrosaurus.setAttackState(0);
    }

    @Override
    public void tick() {
        LivingEntity target = this.kentrosaurus.getTarget();
        if (target != null) {
            this.kentrosaurus.lookAt(this.kentrosaurus.getTarget(), 30F, 30F);
            this.kentrosaurus.getLookControl().setLookAt(this.kentrosaurus.getTarget(), 30F, 30F);

            double distance = this.kentrosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.kentrosaurus.getAttackState();

            if (attackState == 1 || attackState == 2) {
                tickAttack();
            } else {
                this.kentrosaurus.getNavigation().moveTo(target, 1.75D);
                if (distance <= 13) {
                    if (this.kentrosaurus.getRandom().nextBoolean()) {
                        this.kentrosaurus.setAttackState(1);
                    } else {
                        this.kentrosaurus.setAttackState(2);
                    }
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void tickAttack() {
        this.attackTime++;
        LivingEntity target = this.kentrosaurus.getTarget();

        if (this.attackTime == 10) {
            this.kentrosaurus.addDeltaMovement(this.kentrosaurus.getLookAngle().scale(2.0D).multiply(0.15D, 0, 0.15D));
        }

        if (this.attackTime == 20) {
            if (this.kentrosaurus.distanceTo(Objects.requireNonNull(target)) <= this.getAttackReach(target)) {
                this.kentrosaurus.swing(InteractionHand.MAIN_HAND);
                this.kentrosaurus.doHurtTarget(target);
            }
        }
        if (this.attackTime >= 40) {
            this.attackTime = 0;
            this.kentrosaurus.setAttackState(0);
        }
    }

    protected double getAttackReach(LivingEntity target) {
        return this.kentrosaurus.getBbWidth() * 1.5F * this.kentrosaurus.getBbWidth() * 1.0F + target.getBbWidth();
    }
}
