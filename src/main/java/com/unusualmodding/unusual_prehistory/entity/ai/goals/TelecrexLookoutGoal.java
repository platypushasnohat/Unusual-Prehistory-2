package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Telecrex;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;

public class TelecrexLookoutGoal extends AnimationGoal {

    private final Telecrex telecrex;

    public TelecrexLookoutGoal(Telecrex telecrex) {
        super(telecrex, 40);
        this.telecrex = telecrex;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.telecrex.getLookoutCooldown() == 0 && !this.telecrex.isInWater() && !this.telecrex.isFlying();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.telecrex.isInWater() && !this.telecrex.isFlying();
    }

    @Override
    public void start() {
        super.start();
        this.telecrex.setPose(UP2Poses.PREENING.get());
    }

    @Override
    public void stop() {
        super.stop();
        this.telecrex.setLookoutCooldown(50 * 2 * 20 + telecrex.getRandom().nextInt(20 * 8 * 20));
    }
}
