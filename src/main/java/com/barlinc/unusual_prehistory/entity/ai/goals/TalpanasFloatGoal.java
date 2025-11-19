package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Talpanas;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class TalpanasFloatGoal extends FloatGoal {

	private final Talpanas talpanas;

    public TalpanasFloatGoal(Talpanas talpanas) {
        super(talpanas);
        this.talpanas = talpanas;
    }


    @Override
    public void tick() {
        if (this.talpanas.getRandom().nextFloat() < 0.2F) {
            this.talpanas.getJumpControl().jump();
        }
    }
}