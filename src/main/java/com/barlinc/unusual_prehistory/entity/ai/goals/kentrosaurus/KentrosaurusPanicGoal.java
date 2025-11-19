package com.barlinc.unusual_prehistory.entity.ai.goals.kentrosaurus;

import com.barlinc.unusual_prehistory.entity.Kentrosaurus;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class KentrosaurusPanicGoal extends Goal {
    protected final Kentrosaurus kentrosaurus;

    protected double posX;
    protected double posY;
    protected double posZ;

    public KentrosaurusPanicGoal(Kentrosaurus kentrosaurus) {
        this.kentrosaurus = kentrosaurus;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.shouldPanic()) {
            return false;
        } else {
            return this.findRandomPosition();
        }
    }

    protected boolean shouldPanic() {
        return this.kentrosaurus.getLastHurtByMob() != null && (this.kentrosaurus.getHealth() <= this.kentrosaurus.getMaxHealth() * 0.25F || this.kentrosaurus.isBaby());
    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = LandRandomPos.getPos(this.kentrosaurus, 16, 8);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    public void start() {
        this.kentrosaurus.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.75D);
    }

    public boolean canContinueToUse() {
        return !this.kentrosaurus.getNavigation().isDone();
    }
}
