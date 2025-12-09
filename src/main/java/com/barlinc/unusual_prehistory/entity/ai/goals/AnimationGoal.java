package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class AnimationGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    protected int timer;
    protected final int animationTime;
    protected final int idleState;
    private final byte animationByte;
    private final byte stopAnimationByte;
    protected final boolean stopMoving;
    protected final boolean stopIfHurt;

    public AnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte) {
        this(prehistoricMob, animationTime, idleState, animationByte, stopAnimationByte, true, true);
    }

    public AnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte, boolean stopMoving) {
        this(prehistoricMob, animationTime, idleState, animationByte, stopAnimationByte, stopMoving, true);
    }

    public AnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, byte animationByte, byte stopAnimationByte, boolean stopMoving, boolean stopIfHurt) {
        this.prehistoricMob = prehistoricMob;
        this.animationTime = animationTime;
        this.idleState = idleState;
        this.animationByte = animationByte;
        this.stopAnimationByte = stopAnimationByte;
        this.stopMoving = stopMoving;
        this.stopIfHurt = stopIfHurt;
    }

    @Override
    public boolean canUse() {
        if (stopIfHurt && prehistoricMob.getLastHurtByMob() != null) return false;
        else if (stopMoving && !prehistoricMob.getNavigation().isDone()) return false;
        return prehistoricMob.isAlive() && prehistoricMob.getIdleState() == 0;
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
        return prehistoricMob.getTarget() == null && timer > 0 && prehistoricMob.isAlive() && prehistoricMob.getIdleState() == idleState;
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
