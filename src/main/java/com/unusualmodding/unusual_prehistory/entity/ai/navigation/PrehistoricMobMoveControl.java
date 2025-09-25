package com.unusualmodding.unusual_prehistory.entity.ai.navigation;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.MoveControl;

public class PrehistoricMobMoveControl extends MoveControl {

    public PrehistoricMob prehistoricMob;

    public PrehistoricMobMoveControl(PrehistoricMob mob) {
        super(mob);
        this.prehistoricMob = mob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            super.tick();
        }
    }
}
