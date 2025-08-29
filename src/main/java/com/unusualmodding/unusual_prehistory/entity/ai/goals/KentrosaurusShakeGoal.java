package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import com.unusualmodding.unusual_prehistory.entity.enums.BaseBehaviors;
import com.unusualmodding.unusual_prehistory.entity.enums.KentrosaurusBehaviors;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import net.minecraft.world.entity.ai.goal.Goal;

public class KentrosaurusShakeGoal extends Goal {
    protected final Kentrosaurus kentrosaurus;
    protected final KentrosaurusBehaviors behavior;

    public KentrosaurusShakeGoal(Kentrosaurus kentrosaurus) {
        this.kentrosaurus = kentrosaurus;
        this.behavior = KentrosaurusBehaviors.SHAKE;
    }

    @Override
    public boolean canUse() {
        return this.kentrosaurus.getShakeCooldown() == 0 && this.kentrosaurus.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown();
    }

    @Override
    public boolean canContinueToUse() {
        return this.kentrosaurus.getShakeTimer() > 0 && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown();
    }

    @Override
    public void start() {
        super.start();
        this.kentrosaurus.setPose(UP2Poses.SHAKING.get());
        this.kentrosaurus.setShakeTimer(behavior.getLength());
        this.kentrosaurus.setBehavior(behavior.getName());
        this.kentrosaurus.playSound(behavior.getSound(), 1.0F, this.kentrosaurus.getVoicePitch());
    }
}
