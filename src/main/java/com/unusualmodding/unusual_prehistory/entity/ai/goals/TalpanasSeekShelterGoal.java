package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Talpanas;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class TalpanasSeekShelterGoal extends FleeSunGoal {

    private int interval = reducedTickDelay(100);
    private final Talpanas talpanas;

    public TalpanasSeekShelterGoal(Talpanas talpanas) {
        super(talpanas, 1.0D);
        this.talpanas = talpanas;
    }

    public boolean canUse() {
        if (talpanas.level().canSeeSky(this.mob.blockPosition()) && this.talpanas.level().isDay()) {
            return this.setWantedPos();
        } else if (this.interval > 0) {
            this.interval--;
            return false;
        } else {
            this.interval = 100;
            return this.talpanas.level().isDay() && talpanas.level().canSeeSky(this.mob.blockPosition()) && !this.setWantedPos();
        }
    }
}