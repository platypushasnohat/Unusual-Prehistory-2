package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Predicate;

public class IdleAnimationGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    public Predicate<Mob> canUse;
    protected int timer;
    protected final int animationTime;
    protected final int idleState;
    protected final boolean stopMoving;
    protected final boolean stopInWater;
    protected final float chance;

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, Predicate<Mob> canUse) {
        this(prehistoricMob, animationTime, idleState, true, true, 0.001F, canUse);
    }

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, boolean stopMoving, Predicate<Mob> canUse) {
        this(prehistoricMob, animationTime, idleState, stopMoving, true, 0.001F, canUse);
    }

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, boolean stopMoving, boolean stopInWater, float chance, Predicate<Mob> canUse) {
        this.prehistoricMob = prehistoricMob;
        this.animationTime = animationTime;
        this.idleState = idleState;
        this.stopMoving = stopMoving;
        this.stopInWater = stopInWater;
        this.chance = chance;
        this.canUse = canUse;
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.getLastHurtByMob() != null) {
            return false;
        } else if (stopMoving && !prehistoricMob.getNavigation().isDone()) {
            return false;
        } else if (stopInWater && prehistoricMob.isInWater()) {
            return false;
        }
        return canUse.test(prehistoricMob) && prehistoricMob.idleAnimationCooldown == 0 && prehistoricMob.getRandom().nextFloat() < chance && prehistoricMob.isAlive() && prehistoricMob.getIdleState() == 0 && !prehistoricMob.isEepy() && !prehistoricMob.isDancing();
    }

    @Override
    public void start() {
        this.prehistoricMob.setIdleState(idleState);
        this.prehistoricMob.setIdleAnimationCooldown(prehistoricMob.getIdleAnimationCooldown(idleState));
        this.timer = animationTime;
        if (stopMoving) {
            this.prehistoricMob.getNavigation().stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (prehistoricMob.getLastHurtByMob() != null) {
            return false;
        } else if (stopInWater && prehistoricMob.isInWater()) {
            return false;
        }
        return canUse.test(prehistoricMob) && !prehistoricMob.isDancing() && !prehistoricMob.isEepy() && prehistoricMob.getTarget() == null && timer > 0 && prehistoricMob.isAlive() && prehistoricMob.getIdleState() == idleState;
    }

    @Override
    public void tick() {
        this.timer--;
        if (stopMoving) {
            this.prehistoricMob.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.prehistoricMob.setIdleState(0);
        if (stopMoving) {
            this.prehistoricMob.getNavigation().stop();
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
