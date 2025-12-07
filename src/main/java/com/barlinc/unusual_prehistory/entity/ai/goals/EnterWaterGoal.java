package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class EnterWaterGoal extends Goal {

    protected final SemiAquaticMob semiAquaticMob;
    private final double speedModifier;
    private final int maxTimeOnLand;
    private BlockPos waterPos;

    public EnterWaterGoal(SemiAquaticMob semiAquaticMob, double speedModifier, int maxTimeOnLand) {
        this.semiAquaticMob = semiAquaticMob;
        this.speedModifier = speedModifier;
        this.maxTimeOnLand = maxTimeOnLand;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (semiAquaticMob.isInWater()) {
            return false;
        }
        if (semiAquaticMob.getTimeInWater() != 0 || semiAquaticMob.getTimeOnLand() <= maxTimeOnLand) {
            return false;
        }
        return this.findWaterPos();
    }

    @Override
    public boolean canContinueToUse() {
        return !semiAquaticMob.getNavigation().isDone();
    }

    @Override
    public void start() {
        semiAquaticMob.getNavigation().moveTo(waterPos.getX(), waterPos.getY(), waterPos.getZ(), speedModifier);
    }

    private boolean findWaterPos() {
        RandomSource random = semiAquaticMob.getRandom();
        BlockPos.MutableBlockPos mutablePos = semiAquaticMob.blockPosition().mutable();
        for (int i = 0; i < 10; i++) {
            mutablePos.move(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (semiAquaticMob.level().getFluidState(mutablePos).is(FluidTags.WATER)) {
                waterPos = mutablePos.immutable();
                return true;
            }
        }
        return false;
    }
}