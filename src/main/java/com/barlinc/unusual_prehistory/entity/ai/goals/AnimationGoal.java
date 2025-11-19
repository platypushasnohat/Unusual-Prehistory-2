package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class AnimationGoal extends Goal {

    private final PathfinderMob mob;
    private int timer;
    private final int animationTime;

    public AnimationGoal(PathfinderMob mob, int animationTime) {
        this.mob = mob;
        this.animationTime = animationTime;
    }

    @Override
    public boolean canUse() {
        return mob.getPose() == Pose.STANDING;
    }

    @Override
    public void start() {
        this.timer = animationTime;
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0;
    }

    @Override
    public void tick() {
        this.timer--;
    }

    @Override
    public void stop() {
        this.mob.setPose(Pose.STANDING);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
