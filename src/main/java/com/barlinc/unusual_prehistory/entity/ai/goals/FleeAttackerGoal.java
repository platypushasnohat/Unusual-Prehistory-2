package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FleeAttackerGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    protected final double speedModifier;

    public FleeAttackerGoal(PrehistoricMob prehistoricMob, double speedModifier) {
        this.prehistoricMob = prehistoricMob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return prehistoricMob.fleeTicks > 0 && prehistoricMob.fleeFromPosition != null;
    }

    @Override
    public void stop() {
        this.prehistoricMob.fleeFromPosition = null;
        this.prehistoricMob.setRunning(false);
    }

    @Override
    public void tick() {
        this.prehistoricMob.setRunning(true);
        if (prehistoricMob.getNavigation().isDone()) {
            Vec3 vec3 = LandRandomPos.getPosAway(prehistoricMob, 10, 4, prehistoricMob.fleeFromPosition);
            if (vec3 != null) {
                this.prehistoricMob.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, speedModifier);
            }
        }
    }
}