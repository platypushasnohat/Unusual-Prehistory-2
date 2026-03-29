package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.SemiAquaticMob;

public class SemiAquaticRandomStrollGoal extends PrehistoricRandomStrollGoal {

    protected final SemiAquaticMob semiAquaticMob;

    public SemiAquaticRandomStrollGoal(SemiAquaticMob mob, double speedModifier) {
        this(mob, speedModifier, 120);
    }

    public SemiAquaticRandomStrollGoal(SemiAquaticMob mob, double speedModifier, int interval) {
        super(mob, speedModifier, interval, false);
        this.semiAquaticMob = mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && semiAquaticMob.isLandNavigator && !semiAquaticMob.isInWaterOrBubble();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && semiAquaticMob.isLandNavigator && !semiAquaticMob.isInWaterOrBubble();
    }
}
