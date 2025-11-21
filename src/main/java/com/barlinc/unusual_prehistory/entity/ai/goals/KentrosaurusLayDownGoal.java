package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import net.minecraft.world.entity.ai.goal.Goal;

public class KentrosaurusLayDownGoal extends Goal {

    protected final Kentrosaurus kentrosaurus;
    private final int minimalPoseTicks;

    public KentrosaurusLayDownGoal(Kentrosaurus kentrosaurus) {
        this.kentrosaurus = kentrosaurus;
        this.minimalPoseTicks = 20 * 20 + kentrosaurus.getRandom().nextInt(20 * 20);
    }

    @Override
    public boolean canUse() {
        return !this.kentrosaurus.isInWater() && this.kentrosaurus.getLayDownCooldown() <= 0 && this.kentrosaurus.getPoseTime() >= (long) this.minimalPoseTicks && !this.kentrosaurus.isLeashed() && this.kentrosaurus.onGround() && this.kentrosaurus.getBehavior().equals(Behaviors.IDLE.getName());
    }

    @Override
    public boolean canContinueToUse() {
        return !this.kentrosaurus.isInWater() && this.kentrosaurus.getPoseTime() >= (long) this.minimalPoseTicks && !this.kentrosaurus.isLeashed() && this.kentrosaurus.onGround();
    }

    @Override
    public void start() {
        if (this.kentrosaurus.isKentrosaurusLayingDown()) {
            this.kentrosaurus.layDownCooldown();
            this.kentrosaurus.standUp();
        } else {
            this.kentrosaurus.standUpCooldown();
            this.kentrosaurus.layDown();
        }
    }
}
