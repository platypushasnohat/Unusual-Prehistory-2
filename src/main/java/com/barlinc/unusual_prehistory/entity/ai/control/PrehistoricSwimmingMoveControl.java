package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class PrehistoricSwimmingMoveControl extends SwimmingMoveControl {

    protected final PrehistoricMob prehistoricMob;

    public PrehistoricSwimmingMoveControl(PrehistoricMob prehistoricMob, int maxTurnX, int maxTurnY, float speedModifier) {
        super(prehistoricMob, maxTurnX, maxTurnY, speedModifier);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) super.tick();
    }
}
