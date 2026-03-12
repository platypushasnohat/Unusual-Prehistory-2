package com.barlinc.unusual_prehistory.entity.ai.goals.update_4;

import com.barlinc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Manipulator;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class ManipulatorAttackGoal extends AttackGoal {

    protected final Manipulator manipulator;

    public ManipulatorAttackGoal(Manipulator manipulator) {
        super(manipulator);
        this.manipulator = manipulator;
    }

    @Override
    public void tick() {
        LivingEntity target = manipulator.getTarget();
        if (target != null) {
            double distance = manipulator.distanceToSqr(target);
            this.manipulator.lookAt(manipulator.getTarget(), 30F, 30F);
            this.manipulator.getLookControl().setLookAt(target, 30F, 30F);

            if (this.manipulator.getAttackState() == 1) {
                this.manipulator.getNavigation().stop();
                this.tickAttack();
            } else {
                this.manipulator.getNavigation().moveTo(target, 1.4D);
                if (distance <= this.getAttackReachSqr(target) && manipulator.attackCooldown == 0) {
                    this.manipulator.setAttackState(1);
                }
            }
        }
    }

    protected void tickAttack() {
        this.timer++;
        LivingEntity target = manipulator.getTarget();
        if (timer == 1) {
            this.manipulator.setPose(UP2Poses.ATTACKING.get());
            this.manipulator.playSound(UP2SoundEvents.MANIPULATOR_ATTACK_VOCAL.get(), 1.0F, 0.9F + manipulator.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 7) {
            this.manipulator.playSound(UP2SoundEvents.MANIPULATOR_ATTACK_SLASH.get(), 0.8F, 1.0F + manipulator.getRandom().nextFloat() * 0.2F);
            if (this.isInAttackRange(target, 2.0D)) {
                this.manipulator.doHurtTarget(target);
                this.manipulator.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer == 17) {
            this.manipulator.playSound(UP2SoundEvents.MANIPULATOR_ATTACK_SLASH.get(), 0.8F, 1.0F + manipulator.getRandom().nextFloat() * 0.2F);
            if (this.isInAttackRange(target, 2.0D)) {
                this.manipulator.doHurtTarget(target);
                this.manipulator.swing(InteractionHand.OFF_HAND);
            }
        }
        if (timer > 30) {
            this.timer = 0;
            this.manipulator.setAttackState(0);
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}