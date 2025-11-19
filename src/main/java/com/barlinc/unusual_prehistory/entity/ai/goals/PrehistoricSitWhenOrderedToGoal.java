package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.TameablePrehistoricMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class PrehistoricSitWhenOrderedToGoal extends Goal {

    private final TameablePrehistoricMob tamedMob;

    public PrehistoricSitWhenOrderedToGoal(TameablePrehistoricMob mob) {
        this.tamedMob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canContinueToUse() {
        return this.tamedMob.isOrderedToSit();
    }

    @Override
    public boolean canUse() {
        if (!this.tamedMob.isTame()) {
            return false;
        } else if (this.tamedMob.isInWaterOrBubble()) {
            return false;
        } else if (!this.tamedMob.onGround()) {
            return false;
        } else {
            LivingEntity livingentity = this.tamedMob.getOwner();
            if (livingentity == null) {
                return true;
            } else {
                return (!(this.tamedMob.distanceToSqr(livingentity) < 144.0D) || livingentity.getLastHurtByMob() == null) && this.tamedMob.isOrderedToSit();
            }
        }
    }

    @Override
    public void start() {
        this.tamedMob.getNavigation().stop();
        this.tamedMob.setInSittingPose(true);
    }

    @Override
    public void stop() {
        this.tamedMob.setInSittingPose(false);
    }
}