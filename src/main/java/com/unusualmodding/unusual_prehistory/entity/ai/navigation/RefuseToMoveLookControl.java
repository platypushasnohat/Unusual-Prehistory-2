package com.unusualmodding.unusual_prehistory.entity.ai.navigation;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.LookControl;

public class RefuseToMoveLookControl extends LookControl {
    protected final PrehistoricMob mob;

    public RefuseToMoveLookControl(PrehistoricMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (!mob.refuseToMove()) super.tick();
    }
}
