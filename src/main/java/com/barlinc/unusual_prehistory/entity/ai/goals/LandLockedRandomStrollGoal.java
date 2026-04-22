package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;

public class LandLockedRandomStrollGoal extends PrehistoricRandomStrollGoal {

    protected final PrehistoricFlyingMob flyingMob;

    public LandLockedRandomStrollGoal(PrehistoricFlyingMob flyingMob, double speedModifier) {
        this(flyingMob, speedModifier, 120, true);
    }

    public LandLockedRandomStrollGoal(PrehistoricFlyingMob flyingMob, double speedModifier, boolean shouldAvoidWater) {
        this(flyingMob, speedModifier, 120, shouldAvoidWater);
    }

    public LandLockedRandomStrollGoal(PrehistoricFlyingMob flyingMob, double speedModifier, int interval, boolean shouldAvoidWater) {
        super(flyingMob, speedModifier, interval, shouldAvoidWater);
        this.flyingMob = flyingMob;
    }

    @Override
    public boolean canUse() {
        return !flyingMob.isFlying() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !flyingMob.isFlying() && super.canContinueToUse();
    }
}
