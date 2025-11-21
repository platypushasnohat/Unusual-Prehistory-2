package com.barlinc.unusual_prehistory.entity.ai.goals.megalania;

import com.barlinc.unusual_prehistory.entity.Megalania;
import com.barlinc.unusual_prehistory.entity.utils.Behaviors;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class MegalaniaRoarGoal extends Goal {

    private final Megalania megalania;
    public int timer;

    public MegalaniaRoarGoal(Megalania megalania) {
        this.megalania = megalania;
    }

    @Override
    public boolean canUse() {
        return this.megalania.getPose() == Pose.STANDING && !this.megalania.isMegalaniaLayingDown() && this.megalania.roarCooldown == 0 && this.megalania.getBehavior().equals(Behaviors.IDLE.getName()) && !this.megalania.isInWater() && this.megalania.onGround();
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && !this.megalania.isInWater() && this.megalania.onGround() && this.megalania.getPose() == Pose.ROARING;
    }

    @Override
    public void start() {
        super.start();
        this.megalania.setPose(Pose.ROARING);
        this.timer = 80;
    }

    @Override
    public void tick() {
        this.timer--;
        if (timer == 61) megalania.playSound(UP2SoundEvents.MEGALANIA_ROAR.get(), 1.5F, megalania.getVoicePitch());
    }

    @Override
    public void stop() {
        super.stop();
        this.megalania.roarCooldown = 300 + megalania.getRandom().nextInt(60 * 60);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}