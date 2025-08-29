package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;

public class KentrosaurusShakeGoal extends AnimationGoal {

    private final Kentrosaurus kentrosaurus;

    public KentrosaurusShakeGoal(Kentrosaurus kentrosaurus) {
        super(kentrosaurus, 40);
        this.kentrosaurus = kentrosaurus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.kentrosaurus.getShakeCooldown() == 0 && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown();
    }

    @Override
    public void start() {
        super.start();
        this.kentrosaurus.setPose(UP2Poses.GRAZING.get());
    }

    @Override
    public void stop() {
        super.stop();
        this.kentrosaurus.setShakeCooldown(70 * 2 * 20 + kentrosaurus.getRandom().nextInt(70 * 8 * 20));
    }
}
