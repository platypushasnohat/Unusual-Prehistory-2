package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class PrehistoricBodyRotationControl extends SmoothBodyRotationControl {

    protected final PrehistoricMob mob;

    public PrehistoricBodyRotationControl(PrehistoricMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (!mob.refuseToLook()) super.clientTick();
    }
}
