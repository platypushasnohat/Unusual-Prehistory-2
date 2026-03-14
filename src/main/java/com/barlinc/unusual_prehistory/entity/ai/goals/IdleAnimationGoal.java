package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class IdleAnimationGoal extends Goal {

    // todo: add canUse predicate?

    protected final PrehistoricMob prehistoricMob;
    protected int timer;
    protected final int animationTime;
    protected final int idleState;
    private final byte animationByte;
    private final byte stopAnimationByte;
    protected final boolean stopMoving;
    protected final boolean stopIfHurt;
    protected final boolean stopInWater;

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte) {
        this(prehistoricMob, animationTime, idleState, animationByte, stopAnimationByte, true, true, true);
    }

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte, boolean stopMoving) {
        this(prehistoricMob, animationTime, idleState, animationByte, stopAnimationByte, stopMoving, true, true);
    }

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte, boolean stopMoving, boolean stopInWater) {
        this(prehistoricMob, animationTime, idleState, animationByte, stopAnimationByte, stopMoving, true, stopInWater);
    }

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte, boolean stopMoving, boolean stopIfHurt, boolean stopInWater) {
        this.prehistoricMob = prehistoricMob;
        this.animationTime = animationTime;
        this.idleState = idleState;
        this.animationByte = animationByte;
        this.stopAnimationByte = stopAnimationByte;
        this.stopMoving = stopMoving;
        this.stopIfHurt = stopIfHurt;
        this.stopInWater = stopInWater;
    }

    @Override
    public boolean canUse() {
        if (stopIfHurt && prehistoricMob.getLastHurtByMob() != null) return false;
        else if (stopMoving && !prehistoricMob.getNavigation().isDone()) return false;
        else if (stopInWater && prehistoricMob.isInWater()) return false;
        return prehistoricMob.isAlive() && prehistoricMob.getIdleState() == 0 && !prehistoricMob.isEepy() && !prehistoricMob.isDancing();
    }

    @Override
    public void start() {
        this.prehistoricMob.setIdleState(idleState);
        this.timer = animationTime;
        this.prehistoricMob.level().broadcastEntityEvent(prehistoricMob, animationByte);
        if (stopMoving) this.prehistoricMob.getNavigation().stop();
    }

    @Override
    public boolean canContinueToUse() {
        if (stopIfHurt && prehistoricMob.getLastHurtByMob() != null) return false;
        else if (stopInWater && prehistoricMob.isInWater()) return false;
        return !prehistoricMob.isDancing() && !prehistoricMob.isEepy() && prehistoricMob.getTarget() == null && timer > 0 && prehistoricMob.isAlive() && prehistoricMob.getIdleState() == idleState;
    }

    @Override
    public void tick() {
        this.timer--;
        if (stopMoving) this.prehistoricMob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.prehistoricMob.level().broadcastEntityEvent(prehistoricMob, stopAnimationByte);
        this.prehistoricMob.setIdleState(0);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
