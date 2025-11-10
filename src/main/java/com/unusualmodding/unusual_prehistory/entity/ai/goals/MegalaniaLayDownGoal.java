package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Megalania;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import net.minecraft.world.entity.ai.goal.Goal;

public class MegalaniaLayDownGoal extends Goal {

    protected final Megalania megalania;
    private final int minimalPoseTicks;

    public MegalaniaLayDownGoal(Megalania megalania) {
        this.megalania = megalania;
        this.minimalPoseTicks = 20 * 20 + megalania.getRandom().nextInt(20 * 20);
    }

    @Override
    public boolean canUse() {
        return !this.megalania.isInWater() && this.megalania.getLayDownCooldown() <= 0 && this.megalania.getPoseTime() >= (long) this.minimalPoseTicks && !this.megalania.isLeashed() && this.megalania.onGround() && this.megalania.getBehavior().equals(Behaviors.IDLE.getName()) && this.megalania.isRightTemperatureToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.megalania.isInWater() && this.megalania.getPoseTime() >= (long) this.minimalPoseTicks && !this.megalania.isLeashed() && this.megalania.onGround() && this.megalania.isRightTemperatureToSit();
    }

    @Override
    public void start() {
        if (this.megalania.isMegalaniaLayingDown()) {
            this.megalania.layDownCooldown();
            this.megalania.standUp();
        } else {
            this.megalania.standUpCooldown();
            this.megalania.layDown();
        }
    }
}
