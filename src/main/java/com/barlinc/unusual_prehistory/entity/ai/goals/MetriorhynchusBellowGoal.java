package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class MetriorhynchusBellowGoal extends Goal {

    private final Metriorhynchus metriorhynchus;
    public int timer;

    public MetriorhynchusBellowGoal(Metriorhynchus metriorhynchus) {
        this.metriorhynchus = metriorhynchus;
    }

    @Override
    public boolean canUse() {
        return this.metriorhynchus.getPose() == Pose.STANDING && this.metriorhynchus.bellowCooldown == 0 && this.metriorhynchus.getBehavior().equals(Behaviors.IDLE.getName());
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && this.metriorhynchus.getPose() == UP2Poses.BELLOWING.get();
    }

    @Override
    public void start() {
        super.start();
        this.metriorhynchus.setPose(UP2Poses.BELLOWING.get());
        this.metriorhynchus.playSound(UP2SoundEvents.METRIORHYNCHUS_BELLOW.get(), 1.5F, 1.0F);
        this.timer = 30;
    }

    @Override
    public void tick() {
        this.timer--;
    }

    @Override
    public void stop() {
        super.stop();
        this.metriorhynchus.bellowCooldown = 300 + metriorhynchus.getRandom().nextInt(60 * 70);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}