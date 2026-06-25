package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;

public class SemiAquaticWanderGoal extends PrehistoricWanderGoal {

    protected final AmphibiousMob semiAquaticMob;

    public SemiAquaticWanderGoal(AmphibiousMob mob, double speedModifier) {
        this(mob, speedModifier, 120);
    }

    public SemiAquaticWanderGoal(AmphibiousMob mob, double speedModifier, int interval) {
        super(mob, speedModifier, interval, true);
        this.semiAquaticMob = mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !semiAquaticMob.isInWaterOrBubble();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !semiAquaticMob.isInWaterOrBubble();
    }
}
