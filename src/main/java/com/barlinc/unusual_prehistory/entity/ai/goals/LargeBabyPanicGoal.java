package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;

public class LargeBabyPanicGoal extends LargePanicGoal {

    private final PrehistoricMob prehistoricMob;

    public LargeBabyPanicGoal(PrehistoricMob mob, double speedModifier) {
        super(mob, speedModifier);
        this.prehistoricMob = mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && prehistoricMob.isBaby();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canUse() && prehistoricMob.isBaby();
    }
}

