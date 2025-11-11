package com.unusualmodding.unusual_prehistory.entity.ai.goals.kimmeridgebrachypteraeschnidium;

import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.unusualmodding.unusual_prehistory.entity.ai.goals.AnimationGoal;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;

public class KimmeridgebrachypteraeschnidiumPreenGoal extends AnimationGoal {

    private final Kimmeridgebrachypteraeschnidium dragonfly;

    public KimmeridgebrachypteraeschnidiumPreenGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
        super(dragonfly, 60);
        this.dragonfly = dragonfly;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.dragonfly.getPreenCooldown() == 0 && !this.dragonfly.isInWater() && this.dragonfly.onGround() && !this.dragonfly.isFlying();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.dragonfly.isInWater() && this.dragonfly.onGround() && !this.dragonfly.isFlying();
    }

    @Override
    public void start() {
        super.start();
        this.dragonfly.setPose(UP2Poses.PREENING.get());
    }

    @Override
    public void stop() {
        super.stop();
        this.dragonfly.setPreenCooldown(2 * 20 + dragonfly.getRandom().nextInt(10 * 20));
    }
}
