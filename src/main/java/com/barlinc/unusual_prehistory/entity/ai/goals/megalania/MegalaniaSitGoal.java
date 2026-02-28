package com.barlinc.unusual_prehistory.entity.ai.goals.megalania;

import com.barlinc.unusual_prehistory.entity.ai.goals.RandomSitGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Megalania;

public class MegalaniaSitGoal extends RandomSitGoal {

    protected final Megalania megalania;

    public MegalaniaSitGoal(Megalania megalania) {
        super(megalania);
        this.megalania = megalania;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.megalania.isRightTemperatureToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.megalania.isRightTemperatureToSit();
    }
}
