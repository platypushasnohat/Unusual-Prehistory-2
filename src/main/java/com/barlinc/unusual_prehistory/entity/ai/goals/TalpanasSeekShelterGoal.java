package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Talpanas;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class TalpanasSeekShelterGoal extends FleeSunGoal {

    private int interval = reducedTickDelay(10);
    private final Talpanas talpanas;

    public TalpanasSeekShelterGoal(Talpanas talpanas) {
        super(talpanas, 1.2D);
        this.talpanas = talpanas;
    }
}