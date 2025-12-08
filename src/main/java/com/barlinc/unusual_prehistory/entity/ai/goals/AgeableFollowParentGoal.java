package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class AgeableFollowParentGoal extends Goal {

    protected final AgeableMob entity;

    @Nullable
    protected AgeableMob parent;

    private final double speedModifier;
    private int timeToRecalcPath;

    public AgeableFollowParentGoal(AgeableMob entity, double speedModifier) {
        this.entity = entity;
        this.speedModifier = speedModifier;
    }

    public boolean canUse() {
        if (this.entity.getAge() >= 0) {
            return false;
        } else {
            List<? extends AgeableMob> mob = this.entity.level().getEntitiesOfClass(this.entity.getClass(), this.entity.getBoundingBox().inflate(8.0F, 4.0F, 8.0F));
            AgeableMob mob1 = null;
            double maxValue = Double.MAX_VALUE;

            for (AgeableMob mob2 : mob) {
                if (mob2.getAge() >= 0) {
                    double distance = this.entity.distanceToSqr(mob2);
                    if (!(distance > maxValue)) {
                        maxValue = distance;
                        mob1 = mob2;
                    }
                }
            }

            if (mob1 == null) {
                return false;
            } else if (maxValue < (double) 9.0F) {
                return false;
            } else {
                this.parent = mob1;
                return true;
            }
        }
    }

    public boolean canContinueToUse() {
        if (this.entity.getAge() >= 0) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double distance = this.entity.distanceToSqr(this.parent);
            return !(distance < (double) 9.0F) && !(distance > (double) 256.0F);
        }
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void stop() {
        this.parent = null;
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.entity.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
