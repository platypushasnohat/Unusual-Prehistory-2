package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import net.minecraft.world.entity.ai.goal.Goal;

public class DromaeosaurusSleepGoal extends Goal {

    protected final Dromaeosaurus dromaeosaurus;

    public DromaeosaurusSleepGoal(Dromaeosaurus dromaeosaurus) {
        this.dromaeosaurus = dromaeosaurus;
    }

    @Override
    public boolean canUse() {
        return !this.dromaeosaurus.isInWater() && this.dromaeosaurus.level().isNight() && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.onGround() && !this.dromaeosaurus.isAggressive() && this.dromaeosaurus.getHealth() > this.dromaeosaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.dromaeosaurus.isInWater() && this.dromaeosaurus.level().isNight() && !this.dromaeosaurus.isLeashed() && this.dromaeosaurus.onGround() && !this.dromaeosaurus.isAggressive() && this.dromaeosaurus.getHealth() > this.dromaeosaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public void start() {
        if (this.dromaeosaurus.isDromaeosaurusSleeping() && (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.isAggressive() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F)) {
            this.dromaeosaurus.standUp();
        } else {
            this.dromaeosaurus.sleep();
        }
    }

    @Override
    public void stop() {
        if (this.dromaeosaurus.isDromaeosaurusSleeping() && (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.isAggressive() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F)) {
            this.dromaeosaurus.standUp();
        }
    }
}