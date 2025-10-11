package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Carnotaurus;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
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
        this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_SNIFF.get(), 1.0F, this.carnotaurus.getVoicePitch());
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && !carnotaurus.isInWater() && carnotaurus.getPose() == Pose.SNIFFING;
    }

    @Override
    public void tick() {
        this.timer--;
        this.carnotaurus.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.carnotaurus.sniffCooldown = carnotaurus.getRandom().nextInt(20 * 30) + (20 * 30);;
        this.carnotaurus.setPose(Pose.STANDING);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}