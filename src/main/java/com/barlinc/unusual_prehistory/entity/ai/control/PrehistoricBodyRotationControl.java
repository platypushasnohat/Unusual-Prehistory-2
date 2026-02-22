package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class PrehistoricBodyRotationControl extends BodyRotationControl {

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
