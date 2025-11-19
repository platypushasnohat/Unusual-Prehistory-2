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
        return this.megalania.roarCooldown == 0 && this.megalania.getBehavior().equals(Behaviors.IDLE.getName()) && !this.megalania.isInWater() && this.megalania.onGround();
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && !this.megalania.isInWater() && this.megalania.onGround();
    }

    @Override
    public void start() {
        super.start();
        this.megalania.setPose(Pose.ROARING);
        this.timer = 80;
        this.megalania.playSound(UP2SoundEvents.MEGALANIA_IDLE.get(), 1.0F, this.megalania.getVoicePitch());
    }

    @Override
    public void tick() {
        this.timer--;
    }

    @Override
    public void stop() {
        super.stop();
        this.megalania.setPose(Pose.STANDING);
        this.megalania.roarCooldown = 300 + megalania.getRandom().nextInt(30 * 40);;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}