package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Stethacanthus;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StethacanthusAttackGoal extends AttackGoal {

    private final Stethacanthus stethacanthus;

    public StethacanthusAttackGoal(Stethacanthus stethacanthus) {
        super(stethacanthus);
        this.stethacanthus = stethacanthus;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = stethacanthus.getTarget();
        return super.canUse() && target != null && target.isAlive() && target.isInWater() && !target.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS) && !(target instanceof Player);
    }

    @Override
    public void tick() {
        LivingEntity target = stethacanthus.getTarget();
        if (target != null && target.isInWater()) {
            stethacanthus.lookAt(target, 30F, 30F);
            stethacanthus.getLookControl().setLookAt(target, 30F, 30F);

            double distance = stethacanthus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = stethacanthus.getAttackState();

            stethacanthus.getNavigation().moveTo(target, 1.5D);
            if (distance <= 4) {
                stethacanthus.setAttackState(1);
            }
            if (attackState == 1) {
                timer++;
                if (timer == 6) {
                    if (stethacanthus.distanceTo(target) < getAttackReachSqr(target)) {
                        stethacanthus.doHurtTarget(target);
                        stethacanthus.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer >= 9) {
                    timer = 0;
                    stethacanthus.setAttackState(0);
                }
            }
        }
    }
}