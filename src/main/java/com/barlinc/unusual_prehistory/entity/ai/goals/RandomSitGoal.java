package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class RandomSitGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;

    public RandomSitGoal(PrehistoricMob prehistoricMob) {
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.getLastHurtByMob() != null) return false;
        else if (prehistoricMob.getTarget() != null) return false;
        return !prehistoricMob.isOrderedToSit() && !prehistoricMob.hasControllingPassenger() && !prehistoricMob.isBaby() && !prehistoricMob.isEepy() && !prehistoricMob.isInWaterRainOrBubble() && prehistoricMob.getSitCooldown() <= 0 && !prehistoricMob.isLeashed() && prehistoricMob.onGround();
    }

    @Override
    public boolean canContinueToUse() {
        return !prehistoricMob.isOrderedToSit() && !prehistoricMob.isInWaterRainOrBubble() && !prehistoricMob.isLeashed() && prehistoricMob.onGround();
    }

    @Override
    public void start() {
        if (prehistoricMob.isSitting()) {
            this.prehistoricMob.setSitCooldown(6000 + prehistoricMob.getRandom().nextInt(3000));
            this.prehistoricMob.setSitting(false);
        } else {
            this.prehistoricMob.setSitCooldown(1200 + prehistoricMob.getRandom().nextInt(2000));;
            this.prehistoricMob.setSitting(true);
        }
    }
}
