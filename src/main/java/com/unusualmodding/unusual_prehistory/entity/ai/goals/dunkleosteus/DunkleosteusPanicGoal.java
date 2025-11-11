package com.unusualmodding.unusual_prehistory.entity.ai.goals.dunkleosteus;

import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.LargePanicGoal;

public class DunkleosteusPanicGoal extends LargePanicGoal {

    private final Dunkleosteus dunkleosteus;

    public DunkleosteusPanicGoal(Dunkleosteus dunkleosteus) {
        super(dunkleosteus, 1.3D);
        this.dunkleosteus = dunkleosteus;
    }

    @Override
    protected boolean shouldPanic() {
        return this.mob.getLastHurtByMob() != null && (dunkleosteus.getDunkSize() == 0 || dunkleosteus.isBaby());
    }
}