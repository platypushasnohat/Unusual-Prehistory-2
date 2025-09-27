package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class SeekShelterGoal extends FleeSunGoal {

    private int interval = reducedTickDelay(100);
    private final PrehistoricMob prehistoricMob;

    public SeekShelterGoal(PrehistoricMob prehistoricMob, double speedModifier) {
        super(prehistoricMob, speedModifier);
        this.prehistoricMob = prehistoricMob;
    }

    public boolean canUse() {
        if (prehistoricMob.level().canSeeSky(this.prehistoricMob.blockPosition()) && (prehistoricMob.level().isThundering() || prehistoricMob.level().isRaining())) {
            return this.setWantedPos();
        } else if (this.interval > 0) {
            --this.interval;
            return false;
        } else {
            this.interval = 100;
            BlockPos blockpos = this.prehistoricMob.blockPosition();
            return prehistoricMob.level().isDay() && prehistoricMob.level().canSeeSky(blockpos) && !this.setWantedPos();
        }
    }
}
