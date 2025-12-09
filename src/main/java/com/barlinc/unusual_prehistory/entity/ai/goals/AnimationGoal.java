package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class AnimationGoal extends Goal {

    protected final PrehistoricMob mob;
    protected int timer;
    protected final int animationTime;
    protected final float chance;
    private final byte animationByte;
    private final byte stopAnimationByte;
    protected final boolean stopMoving;
    protected final boolean stopIfHurt;

    public AnimationGoal(PrehistoricMob mob, int animationTime, float chance, byte animationByte, byte stopAnimationByte, boolean stopMoving, boolean stopIfHurt) {
        this.mob = mob;
        this.animationTime = animationTime;
        this.chance = chance;
        this.animationByte = animationByte;
        this.stopAnimationByte = stopAnimationByte;
        this.stopMoving = stopMoving;
        this.stopIfHurt = stopIfHurt;
    }

    @Override
    public boolean canUse() {
        if (stopIfHurt && mob.getLastHurtByMob() != null) return false;
        if (stopMoving && !mob.getNavigation().isDone()) return false;
        return mob.isAlive() && mob.getRandom().nextFloat() < chance && mob.getPose() == Pose.STANDING;
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
        if (stopIfHurt && mob.getLastHurtByMob() != null) return false;
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

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
