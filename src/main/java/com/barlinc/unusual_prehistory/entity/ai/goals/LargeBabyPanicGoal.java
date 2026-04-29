package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class LargeBabyPanicGoal extends LargePanicGoal {

    public LargeBabyPanicGoal(PrehistoricMob mob, double speedModifier) {
        this(mob, speedModifier, 10, 4, false);
    }

    public LargeBabyPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height) {
        this(mob, speedModifier, radius, height, false);
    }

    public LargeBabyPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height, boolean shouldEscapeToWater) {
        super(mob, speedModifier, radius, height, shouldEscapeToWater);
    }

    @Override
    protected boolean shouldPanic() {
        return (this.mob.getLastHurtByMob() != null || this.mob.isFreezing() || this.mob.isOnFire()) && mob.isBaby();
    }
}

