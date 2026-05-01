package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class WaterSleepingGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;

    public WaterSleepingGoal(PrehistoricMob prehistoricMob) {
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public boolean canUse() {
        return prehistoricMob.isEepyTime() && prehistoricMob.getLastHurtByMob() == null && prehistoricMob.getTarget() == null && prehistoricMob.isInWaterOrBubble() && !prehistoricMob.isInLava() && prehistoricMob.getEepyCooldown() == 0 && !prehistoricMob.isSitting() && !prehistoricMob.isBaby() && !prehistoricMob.isFollowingOwner() && !prehistoricMob.isLeashed();
    }

    @Override
    public boolean canContinueToUse() {
        if (!prehistoricMob.isInWaterOrBubble() || !prehistoricMob.isEepyTime() || prehistoricMob.getLastHurtByMob() != null || !super.canContinueToUse() || prehistoricMob.getTarget() != null || prehistoricMob.isInLava() || prehistoricMob.isFollowingOwner() || prehistoricMob.isLeashed()) {
            this.stop();
            return false;
        }
        else return true;
    }

    @Override
    public void start() {
        this.prehistoricMob.xxa = 0.0F;
        this.prehistoricMob.yya = 0.0F;
        this.prehistoricMob.zza = 0.0F;
        this.prehistoricMob.getNavigation().stop();
        this.prehistoricMob.setEepy(true);
    }

    @Override
    public void stop() {
        this.prehistoricMob.setEepyCooldown(100);
        this.prehistoricMob.setEepy(false);
    }
}