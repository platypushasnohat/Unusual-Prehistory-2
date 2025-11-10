package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Dromaeosaurus;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DromaeosaurusLeapGoal extends Goal {

    protected final Dromaeosaurus dromaeosaurus;

    public DromaeosaurusLeapGoal(Dromaeosaurus dromaeosaurus) {
        this.dromaeosaurus = dromaeosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && !this.dromaeosaurus.isVehicle() && this.dromaeosaurus.isSprinting() && this.dromaeosaurus.leapCooldown == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        this.dromaeosaurus.getNavigation().stop();
        if (this.dromaeosaurus.onGround()) {
            this.dromaeosaurus.addDeltaMovement(new Vec3(0, 0.8D, 0));
            this.dromaeosaurus.addDeltaMovement(this.dromaeosaurus.getLookAngle().scale(2.0D).multiply(0.6D, 0, 0.6D));
            this.dromaeosaurus.leapCooldown = 100 + dromaeosaurus.getRandom().nextInt(20 * 20);
        }
    }
}