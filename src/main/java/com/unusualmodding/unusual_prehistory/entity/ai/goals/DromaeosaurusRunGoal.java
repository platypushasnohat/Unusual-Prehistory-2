package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DromaeosaurusRunGoal extends Goal {

    protected final Dromaeosaurus dromaeosaurus;

    public DromaeosaurusRunGoal(Dromaeosaurus dromaeosaurus) {
        this.dromaeosaurus = dromaeosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && !this.dromaeosaurus.isVehicle();
    }

    @Override
    public boolean canContinueToUse() {
        return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && !this.dromaeosaurus.isVehicle();
    }

    @Override
    public void stop() {
        this.dromaeosaurus.setSprinting(false);
        this.dromaeosaurus.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.dromaeosaurus.getNavigation().isDone()) {
            Vec3 vec3 = LandRandomPos.getPos(this.dromaeosaurus, 15, 7);
            if (vec3 != null) {
                this.dromaeosaurus.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F);
            }
        }
    }
}