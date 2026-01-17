package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class RandomSitGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    protected final int minimalPoseTicks;

    public RandomSitGoal(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 20 * 20 + prehistoricMob.getRandom().nextInt(20 * 20));
    }

    public RandomSitGoal(PrehistoricMob prehistoricMob, int minimalPoseTicks) {
        this.prehistoricMob = prehistoricMob;
        this.minimalPoseTicks = minimalPoseTicks;
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.getLastHurtByMob() != null) return false;
        else if (prehistoricMob.getTarget() != null) return false;
        return !prehistoricMob.isMobEepy() && !prehistoricMob.isInWater() && prehistoricMob.getSitCooldown() <= 0 && prehistoricMob.getSitPoseTime() >= (long) minimalPoseTicks && !prehistoricMob.isLeashed() && prehistoricMob.onGround();
    }

    @Override
    public boolean canContinueToUse() {
        return !prehistoricMob.isInWater() && prehistoricMob.getSitPoseTime() >= (long) minimalPoseTicks && !prehistoricMob.isLeashed() && prehistoricMob.onGround();
    }

    @Override
    public void start() {
        if (prehistoricMob.isMobSitting()) {
            this.prehistoricMob.setSitCooldown(6000 + prehistoricMob.getRandom().nextInt(3000));
            this.prehistoricMob.stopSitting();
        } else {
            this.prehistoricMob.setSitCooldown(1200 + prehistoricMob.getRandom().nextInt(2000));;
            this.prehistoricMob.startSitting();
        }
    }
}
