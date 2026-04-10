package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class EnterWaterGoal extends Goal {

    protected final AmphibiousMob amphibiousMob;
    private final double speedModifier;
    private final int maxTimeOnLand;
    private BlockPos waterPos;

    public EnterWaterGoal(AmphibiousMob amphibiousMob, double speedModifier, int maxTimeOnLand) {
        this.amphibiousMob = amphibiousMob;
        this.speedModifier = speedModifier;
        this.maxTimeOnLand = maxTimeOnLand;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (amphibiousMob.isInWaterOrBubble() || amphibiousMob.isEepy() || amphibiousMob.isSitting()) {
            return false;
        } else if (amphibiousMob.getTimeOnLand() < maxTimeOnLand) {
            return false;
        }
        return this.findWaterPos();
    }

    @Override
    public boolean canContinueToUse() {
        return !amphibiousMob.isInWaterOrBubble() && !amphibiousMob.getNavigation().isDone() && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    public void start() {
        this.amphibiousMob.getNavigation().moveTo(waterPos.getX(), waterPos.getY(), waterPos.getZ(), speedModifier);
    }

    private boolean findWaterPos() {
        RandomSource random = amphibiousMob.getRandom();
        BlockPos.MutableBlockPos mutablePos = amphibiousMob.blockPosition().mutable();
        for (int i = 0; i < 10; i++) {
            mutablePos.move(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (amphibiousMob.level().getFluidState(mutablePos).is(FluidTags.WATER)) {
                this.waterPos = mutablePos.immutable();
                return true;
            }
        }
        return false;
    }
}