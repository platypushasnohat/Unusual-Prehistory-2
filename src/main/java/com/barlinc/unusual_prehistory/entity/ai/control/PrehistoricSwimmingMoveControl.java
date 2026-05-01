package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;

public class PrehistoricSwimmingMoveControl extends SmoothSwimmingMoveControl {

    protected final PrehistoricMob prehistoricMob;

    public PrehistoricSwimmingMoveControl(PrehistoricMob prehistoricMob, int maxTurnX, int maxTurnY, float inWaterSpeedModifier) {
        this(prehistoricMob, maxTurnX, maxTurnY, inWaterSpeedModifier, 1.0F);
    }

    public PrehistoricSwimmingMoveControl(PrehistoricMob prehistoricMob, int maxTurnX, int maxTurnY, float inWaterSpeedModifier, float outsideWaterSpeedModifier) {
        super(prehistoricMob, maxTurnX, maxTurnY, inWaterSpeedModifier, outsideWaterSpeedModifier, false);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            super.tick();
        }
    }
}
