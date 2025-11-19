package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class SeekShelterGoal extends FleeSunGoal {

    private int interval = reducedTickDelay(100);
    private final PrehistoricMob prehistoricMob;

    public SeekShelterGoal(PrehistoricMob prehistoricMob, double speedModifier) {
        super(prehistoricMob, speedModifier);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public boolean canUse() {
        if (!prehistoricMob.isSleeping() && this.mob.getTarget() == null) {
            if (prehistoricMob.level().isThundering() && prehistoricMob.level().canSeeSky(this.mob.blockPosition())) {
                return this.setWantedPos();
            } else if (this.interval > 0) {
                --this.interval;
                return false;
            } else {
                this.interval = 100;
                BlockPos blockpos = this.mob.blockPosition();
                return mob.level().isDay() && mob.level().canSeeSky(blockpos) && this.setWantedPos();
            }
        } else {
            return false;
        }
    }
}