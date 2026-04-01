package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class PrehistoricBodyRotationControl extends SmoothBodyRotationControl {

    protected final PrehistoricMob mob;

    public PrehistoricBodyRotationControl(PrehistoricMob mob) {
        this(mob, 0.5F, 30.0F);
    }

    public PrehistoricBodyRotationControl(PrehistoricMob mob, float bodyLag, float bodyMax) {
        super(mob, bodyLag, bodyMax);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (!mob.refuseToLook()) super.clientTick();
    }
}
