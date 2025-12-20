package com.barlinc.unusual_prehistory.entity.ai.goals.diplocaulus;

import com.barlinc.unusual_prehistory.entity.Diplocaulus;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;

public class DiplocaulusBurrowGoal extends Goal {

    private final Diplocaulus diplocaulus;
    private final int minimalPoseTicks;

    public DiplocaulusBurrowGoal(Diplocaulus diplocaulus) {
        this.diplocaulus = diplocaulus;
        this.minimalPoseTicks = 20 * 20 + diplocaulus.getRandom().nextInt(20 * 20);
    }

    @Override
    public boolean canUse() {
        return !diplocaulus.isInWater() && diplocaulus.getBurrowCooldown() == 0 && diplocaulus.getPoseTime() >= (long) minimalPoseTicks && !diplocaulus.isLeashed() && diplocaulus.onGround() && diplocaulus.getLastHurtByMob() == null && this.isBurrowBlock();
    }

    @Override
    public boolean canContinueToUse() {
        return !diplocaulus.isInWater() && diplocaulus.getPoseTime() >= (long) minimalPoseTicks && !diplocaulus.isLeashed() && diplocaulus.onGround();
    }

    @Override
    public void start() {
        if (diplocaulus.isDiplocaulusBurrowed()) {
            this.diplocaulus.burrowCooldown();
            this.diplocaulus.exitBurrow();
        } else {
            this.diplocaulus.exitBurrowCooldown();
            this.diplocaulus.burrow();
        }
    }

    protected boolean isBurrowBlock() {
        return diplocaulus.level().getBlockState(diplocaulus.blockPosition()).is(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS) || diplocaulus.level().getBlockState(diplocaulus.blockPosition().below()).is(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS);
    }
}
