package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Megalania;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.MegalaniaBehaviors;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.world.entity.ai.goal.Goal;

public class MegalaniaRoarGoal extends Goal {

    private final Megalania megalania;
    private final MegalaniaBehaviors behavior;

    public MegalaniaRoarGoal(Megalania megalania) {
        this.megalania = megalania;
        this.behavior = MegalaniaBehaviors.ROAR;
    }

    @Override
    public boolean canUse() {
        return megalania.getRoarCooldown() == 0 && megalania.getBehavior().equals(Behaviors.IDLE.getName()) && !megalania.isInWater() && megalania.onGround();
    }

    @Override
    public boolean canContinueToUse() {
        return megalania.getRoarTimer() > 0 && !megalania.isInWater() && megalania.onGround();
    }

    @Override
    public void start() {
        super.start();
        megalania.setPose(UP2Poses.ROARING.get());
        megalania.setRoarTimer(behavior.getLength());
        megalania.setBehavior(behavior.getName());
        megalania.playSound(behavior.getSound(), 1.0F, megalania.getVoicePitch());
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void stop() {
        super.stop();
        megalania.setBehavior(Behaviors.IDLE.getName());
        megalania.roarCooldown();
    }
}