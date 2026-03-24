package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class FlyingPanicGoal extends Goal {

    protected final PrehistoricFlyingMob prehistoricMob;

    public FlyingPanicGoal(PrehistoricFlyingMob mob) {
        this.prehistoricMob = mob;
    }

    @Override
    public void start() {
        this.prehistoricMob.setFlying(true);
        if (prehistoricMob.onGround()) {
            this.prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
        }
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.isFlying()) {
            return false;
        }
        return prehistoricMob.getLastHurtByMob() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}