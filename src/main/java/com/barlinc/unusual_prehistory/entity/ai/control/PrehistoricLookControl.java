package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import net.minecraft.world.entity.ai.control.LookControl;

public class PrehistoricLookControl extends LookControl {

    protected final PrehistoricMob mob;

    public PrehistoricLookControl(PrehistoricMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (!mob.refuseToLook()) {
            super.tick();
        }
    }

    @Override
    protected boolean resetXRotOnTick() {
        if (mob instanceof LeapingMob leapingMob) {
            return !leapingMob.isLeaping();
        }
        return super.resetXRotOnTick();
    }
}
