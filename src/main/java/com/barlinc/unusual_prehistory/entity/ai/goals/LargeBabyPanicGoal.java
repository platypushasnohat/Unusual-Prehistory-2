package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class LargeBabyPanicGoal extends LargePanicGoal {

    protected final PrehistoricMob prehistoricMob;

    public LargeBabyPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height) {
        super(mob, speedModifier, radius, height);
        this.prehistoricMob = mob;
    }

    @Override
    protected boolean shouldPanic() {
        return (this.mob.getLastHurtByMob() != null || this.mob.isFreezing() || this.mob.isOnFire()) && mob.isBaby();
    }
}

