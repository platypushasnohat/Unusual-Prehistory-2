package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import net.minecraft.world.level.block.Blocks;

public class KentrosaurusGrazeGoal extends AnimationGoal {

    private final Kentrosaurus kentrosaurus;

    public KentrosaurusGrazeGoal(Kentrosaurus kentrosaurus) {
        super(kentrosaurus, 41);
        this.kentrosaurus = kentrosaurus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.kentrosaurus.getGrazingCooldown() == 0 && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown() && this.kentrosaurus.level().getBlockState(this.kentrosaurus.blockPosition().below()).is(Blocks.GRASS_BLOCK);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown() && this.kentrosaurus.level().getBlockState(this.kentrosaurus.blockPosition().below()).is(Blocks.GRASS_BLOCK);
    }

    @Override
    public void start() {
        super.start();
        this.kentrosaurus.setPose(UP2Poses.GRAZING.get());
    }

    @Override
    public void stop() {
        super.stop();
        this.kentrosaurus.setGrazingCooldown(30 * 2 * 20 + kentrosaurus.getRandom().nextInt(30 * 8 * 20));
    }
}
