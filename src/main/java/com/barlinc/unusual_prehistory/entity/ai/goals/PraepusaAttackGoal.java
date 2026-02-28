package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.update_4.Praepusa;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

public class PraepusaAttackGoal extends AttackGoal {

    private final Praepusa praepusa;

    public PraepusaAttackGoal(Praepusa praepusa) {
        super(praepusa);
        this.praepusa = praepusa;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = praepusa.getTarget();
        return super.canUse() && praepusa.getPose() == Pose.STANDING && target != null && target.isAlive() && target.isInWater() && !target.getType().is(UP2EntityTags.PRAEPUSA_AVOIDS) && !(target instanceof Player);
    }

    @Override
    public void tick() {
        LivingEntity target = praepusa.getTarget();
        if (target != null && target.isInWater()) {
            this.praepusa.lookAt(target, 30F, 30F);
            this.praepusa.getLookControl().setLookAt(target, 30F, 30F);
            double distance = praepusa.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.praepusa.getNavigation().moveTo(target, 1.5D);
            if (distance <= 4 && praepusa.attackCooldown == 0) {
                praepusa.setAttackState(1);
            }
            if (praepusa.getAttackState() == 1) {
                this.tickAttack();
            }
        }
    }

    protected void tickAttack() {
        this.timer++;
        LivingEntity target = praepusa.getTarget();
        if (timer == 1) praepusa.setPose(UP2Poses.ATTACKING.get());
        if (timer == 8) {
            if (praepusa.distanceTo(target) < this.getAttackReachSqr(target)) {
                this.praepusa.doHurtTarget(target);
                this.praepusa.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 10) {
            this.timer = 0;
            this.praepusa.setAttackState(0);
            this.praepusa.attackCooldown = 4;
        }
    }
}