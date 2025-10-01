package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Diplocaulus;
import com.unusualmodding.unusual_prehistory.entity.utils.Behaviors;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;

public class DiplocaulusBurrowInMudGoal extends Goal {

    protected final Diplocaulus diplocaulus;
    private final int minimalPoseTicks;

    public DiplocaulusBurrowInMudGoal(Diplocaulus diplocaulus) {
        this.diplocaulus = diplocaulus;
        this.minimalPoseTicks = 20 * 20 + diplocaulus.getRandom().nextInt(20 * 20);
    }

    @Override
    public boolean canUse() {
        return !this.diplocaulus.isInWater() && this.diplocaulus.getBurrowCooldown() == 0 && this.diplocaulus.getPoseTime() >= (long) this.minimalPoseTicks && !this.diplocaulus.isLeashed() && this.diplocaulus.onGround() && this.diplocaulus.getBehavior().equals(Behaviors.IDLE.getName()) && this.diplocaulus.level().getBlockState(this.diplocaulus.blockPosition()).is(Blocks.MUD);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.diplocaulus.isInWater() && this.diplocaulus.getPoseTime() >= (long) this.minimalPoseTicks && !this.diplocaulus.isLeashed() && this.diplocaulus.onGround();
    }

    @Override
    public void start() {
        if (this.diplocaulus.isDiplocaulusBurrowed()) {
            this.diplocaulus.burrowCooldown();
            this.diplocaulus.exitBurrow();
        } else {
            this.diplocaulus.exitBurrowCooldown();
            this.diplocaulus.burrow();
        }
    }
}
