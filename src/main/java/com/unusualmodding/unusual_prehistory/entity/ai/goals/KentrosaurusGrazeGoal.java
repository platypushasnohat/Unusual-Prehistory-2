package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kentrosaurus;
import com.unusualmodding.unusual_prehistory.entity.enums.BaseBehaviors;
import com.unusualmodding.unusual_prehistory.entity.enums.KentrosaurusBehaviors;
import com.unusualmodding.unusual_prehistory.entity.pose.UP2Poses;
import net.minecraft.world.level.block.Blocks;

public class KentrosaurusGrazeGoal extends AnimationGoal {

    protected final Kentrosaurus kentrosaurus;
    protected final KentrosaurusBehaviors behavior;

    public KentrosaurusGrazeGoal(Kentrosaurus kentrosaurus) {
        super(kentrosaurus, 40);
        this.kentrosaurus = kentrosaurus;
        this.behavior = KentrosaurusBehaviors.GRAZE;
    }

    @Override
    public boolean canUse() {
        return this.kentrosaurus.getGrazingCooldown() == 0 && this.kentrosaurus.getBehavior().equals(BaseBehaviors.IDLE.getName()) && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown() && this.kentrosaurus.level().getBlockState(this.kentrosaurus.blockPosition().below()).is(Blocks.GRASS_BLOCK);
    }

    @Override
    public boolean canContinueToUse() {
        return this.kentrosaurus.getGrazingTimer() > 0 && !this.kentrosaurus.isInWater() && this.kentrosaurus.onGround() && !this.kentrosaurus.isKentrosaurusLayingDown() && this.kentrosaurus.level().getBlockState(this.kentrosaurus.blockPosition().below()).is(Blocks.GRASS_BLOCK);
    }

    @Override
    public void start() {
        super.start();
        this.kentrosaurus.setPose(UP2Poses.GRAZING.get());
        this.kentrosaurus.setGrazingTimer(behavior.getLength());
        this.kentrosaurus.setBehavior(behavior.getName());
        this.kentrosaurus.playSound(behavior.getSound(), 1.0F, this.kentrosaurus.getVoicePitch());
    }
}
