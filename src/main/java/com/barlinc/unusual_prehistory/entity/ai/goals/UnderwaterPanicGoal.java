package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class UnderwaterPanicGoal extends LargePanicGoal {

    public UnderwaterPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height) {
        super(mob, speedModifier, radius, height, false);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(prehistoricMob, radius, height);
        if (prehistoricMob.getLastHurtByMob() != null) {
            vec3 = DefaultRandomPos.getPosAway(prehistoricMob, radius, height, prehistoricMob.getLastHurtByMob().position());
        }
        if (vec3 != null) {
            this.prehistoricMob.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, speedModifier);
        }
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }
}
