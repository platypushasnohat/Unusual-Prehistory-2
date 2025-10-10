package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class CarnotaurusSniffingGoal extends Goal {

    public Carnotaurus carnotaurus;
    public int timer;

    public CarnotaurusSniffingGoal(Carnotaurus carnotaurus) {
        this.carnotaurus = carnotaurus;
    }

    @Override
    public boolean canUse() {
        return carnotaurus.sniffCooldown == 0 && carnotaurus.getPose() == Pose.STANDING && !carnotaurus.isInWater();
    }

    @Override
    public void start() {
        this.timer = 80;
        this.carnotaurus.getNavigation().stop();
        this.carnotaurus.setPose(Pose.SNIFFING);
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && !carnotaurus.isInWater() && carnotaurus.getPose() == Pose.SNIFFING;
    }

    @Override
    public void tick() {
        this.timer--;
    }

    @Override
    public void stop() {
        this.carnotaurus.sniffCooldown = carnotaurus.getRandom().nextInt(10 * 40) + (20 * 40);
        this.carnotaurus.setPose(Pose.STANDING);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}