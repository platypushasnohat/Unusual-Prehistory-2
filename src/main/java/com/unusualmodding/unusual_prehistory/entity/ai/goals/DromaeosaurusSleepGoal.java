package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import net.minecraft.world.entity.ai.goal.Goal;

public class DromaeosaurusSleepGoal extends Goal {

    protected final Dromaeosaurus dromaeosaurus;

    public DromaeosaurusSleepGoal(Dromaeosaurus dromaeosaurus) {
        this.dromaeosaurus = dromaeosaurus;
    }

    @Override
    public boolean canUse() {
        return !this.dromaeosaurus.isInWater() && this.isEepyTime() && this.dromaeosaurus.onGround() && this.dromaeosaurus.getBehavior().equals(Behaviors.IDLE.getName());
    }

    @Override
    public boolean canContinueToUse() {
        return !this.dromaeosaurus.isInWater() && this.isEepyTime() && this.dromaeosaurus.onGround();
    }

    @Override
    public void start() {
        if (this.dromaeosaurus.isDromaeosaurusLayingDown()) {
            this.dromaeosaurus.standUp();
        } else {
            this.dromaeosaurus.layDown();
        }
    }

    private boolean isEepyTime() {
        return dromaeosaurus.level().isNight() && dromaeosaurus.getHealth() > dromaeosaurus.getMaxHealth() * 0.5F;
    }
}