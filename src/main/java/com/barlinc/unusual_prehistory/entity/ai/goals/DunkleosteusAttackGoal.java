package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.update_1.Dunkleosteus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2DamageTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DunkleosteusAttackGoal extends AttackGoal {

    private final Dunkleosteus dunkleosteus;

    public DunkleosteusAttackGoal(Dunkleosteus dunkleosteus) {
        super(dunkleosteus);
        this.dunkleosteus = dunkleosteus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && (dunkleosteus.getTarget().isInWater() || !dunkleosteus.isInWater());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && (dunkleosteus.getTarget().isInWater() || !dunkleosteus.isInWater());
    }

    @Override
    public void tick() {
        LivingEntity target = this.dunkleosteus.getTarget();
        if (target != null) {
            this.dunkleosteus.lookAt(target, 30F, 30F);
            this.dunkleosteus.getLookControl().setLookAt(target, 30F, 30F);
            double distance = this.dunkleosteus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.dunkleosteus.getAttackState();
            this.dunkleosteus.getNavigation().moveTo(target, dunkleosteus.getChaseSpeed());

            if (attackState == 1) {
                this.tickBite();
            } else if (distance < this.getAttackReachSqr(target) && dunkleosteus.attackCooldown == 0) {
                this.dunkleosteus.setAttackState(1);
            }
        }
    }

    protected void tickBite() {
        timer++;
        LivingEntity target = dunkleosteus.getTarget();
        if (timer == 1) dunkleosteus.setPose(UP2Poses.ATTACKING.get());
        if (timer == 2) dunkleosteus.playSound(dunkleosteus.getBiteSound(), 1.0F, dunkleosteus.getRandom().nextFloat() * 0.1F + 0.9F);
        if (timer == 5) {
            if (this.isInAttackRange(target, 1.5D)) {
                DamageSource damagesource = UP2DamageTypes.execute(dunkleosteus.level(), dunkleosteus, dunkleosteus);
                target.hurt(damagesource, (float) dunkleosteus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                this.dunkleosteus.doKnockback(target);
                this.dunkleosteus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 10) {
            this.timer = 0;
            this.dunkleosteus.setPose(Pose.STANDING);
            this.dunkleosteus.attackCooldown = 5 + dunkleosteus.getRandom().nextInt(3);
            this.dunkleosteus.setAttackState(0);
        }
    }
}