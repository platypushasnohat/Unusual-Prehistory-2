package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;

public class LandLockedWanderGoal extends PrehistoricWanderGoal {

    protected final PrehistoricFlyingMob flyingMob;

    public LandLockedWanderGoal(PrehistoricFlyingMob flyingMob, double speedModifier) {
        this(flyingMob, speedModifier, 120, true);
    }

    public LandLockedWanderGoal(PrehistoricFlyingMob flyingMob, double speedModifier, boolean shouldAvoidWater) {
        this(flyingMob, speedModifier, 120, shouldAvoidWater);
    }

    public LandLockedWanderGoal(PrehistoricFlyingMob flyingMob, double speedModifier, int interval, boolean shouldAvoidWater) {
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
