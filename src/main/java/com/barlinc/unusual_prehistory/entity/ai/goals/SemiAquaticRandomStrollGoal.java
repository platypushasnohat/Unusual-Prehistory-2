package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;

public class SemiAquaticRandomStrollGoal extends PrehistoricRandomStrollGoal {

    protected final AmphibiousMob semiAquaticMob;

    public SemiAquaticRandomStrollGoal(AmphibiousMob mob, double speedModifier) {
        this(mob, speedModifier, 120);
    }

    public SemiAquaticRandomStrollGoal(AmphibiousMob mob, double speedModifier, int interval) {
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
