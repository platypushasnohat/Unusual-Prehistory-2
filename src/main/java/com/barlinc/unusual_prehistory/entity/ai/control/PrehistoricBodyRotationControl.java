package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class PrehistoricBodyRotationControl extends SmoothBodyRotationControl {

    protected final PrehistoricMob mob;

    public PrehistoricBodyRotationControl(PrehistoricMob mob) {
        this(mob, 0.5F, 30.0F, 0.25F, 20.0F, 0.8F, 180.0F);
    }

    public PrehistoricBodyRotationControl(PrehistoricMob mob, float bodyLagMoving, float bodyMaxMoving, float bodyLagStill, float bodyMaxStill, float headLag, float headMax) {
        super(mob, bodyLagMoving, bodyMaxMoving, bodyLagStill, bodyMaxStill, headLag, headMax);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (!mob.refuseToLook()) super.clientTick();
    }
}
