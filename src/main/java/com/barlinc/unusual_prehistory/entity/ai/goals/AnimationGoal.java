package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class AnimationGoal extends Goal {

    protected final PrehistoricMob mob;
    protected int timer;
    protected final int animationTime;
    protected final float chance;
    private final byte animationByte;
    private final byte stopAnimationByte;
    protected final boolean stopMoving;

    public AnimationGoal(PrehistoricMob mob, int animationTime, float chance, byte animationByte, byte stopAnimationByte, boolean stopMoving) {
        this.mob = mob;
        this.animationTime = animationTime;
        this.chance = chance;
        this.animationByte = animationByte;
        this.stopAnimationByte = stopAnimationByte;
        this.stopMoving = stopMoving;
    }

    @Override
    public boolean canUse() {
        if (stopMoving) return mob.getNavigation().isDone();
        return mob.isAlive() && mob.getRandom().nextFloat() < chance;
    }

    @Override
    public void start() {
        this.timer = animationTime;
        this.mob.level().broadcastEntityEvent(mob, animationByte);
        if (stopMoving) {
            this.mob.getNavigation().stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && mob.isAlive();
    }

    @Override
    public void tick() {
        this.timer--;
        if (stopMoving) {
            this.mob.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.mob.level().broadcastEntityEvent(mob, stopAnimationByte);
    }
}
