package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Telecrex;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;

public class TelecrexPeckGoal extends AnimationGoal {

    private final Telecrex telecrex;

    public TelecrexPeckGoal(Telecrex telecrex) {
        super(telecrex, 40);
        this.telecrex = telecrex;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.telecrex.getPeckCooldown() == 0 && !this.telecrex.isInWater() && !this.telecrex.isFlying();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.telecrex.isInWater() && !this.telecrex.isFlying();
    }

    @Override
    public void start() {
        super.start();
        this.telecrex.setPose(UP2Poses.PECKING.get());
    }

    @Override
    public void stop() {
        super.stop();
        this.telecrex.setPeckCooldown(20 * 2 * 20 + telecrex.getRandom().nextInt(20 * 8 * 20));
    }

    @Override
    public void tick() {
        super.tick();
        this.telecrex.getNavigation().stop();
    }
}
