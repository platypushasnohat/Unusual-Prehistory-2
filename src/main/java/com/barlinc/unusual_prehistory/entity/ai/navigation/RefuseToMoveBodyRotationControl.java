package com.barlinc.unusual_prehistory.entity.ai.navigation;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class RefuseToMoveBodyRotationControl extends BodyRotationControl {

    protected final PrehistoricMob mob;

    public RefuseToMoveBodyRotationControl(PrehistoricMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (!mob.refuseToMove()) super.clientTick();
    }
}
