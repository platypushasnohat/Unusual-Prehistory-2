package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.base.SemiAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class LeaveWaterGoal extends Goal {

    private final SemiAquaticMob semiAquaticMob;
    private final double speedModifier;
    private final int maxTimeInWater;
    private final int maxTimeOnLand;

    private BlockPos landPos;

    public LeaveWaterGoal(SemiAquaticMob semiAquaticMob, double speedModifier, int maxTimeInWater, int maxTimeOnLand) {
        this.semiAquaticMob = semiAquaticMob;
        this.speedModifier = speedModifier;
        this.maxTimeInWater = maxTimeInWater;
        this.maxTimeOnLand = maxTimeOnLand;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!semiAquaticMob.isInWater()) {
            return false;
        }
        if (semiAquaticMob.timeInWater() <= maxTimeInWater || semiAquaticMob.timeOnLand() >= maxTimeOnLand) {
            return false;
        }
        return findLandPos();
    }

    @Override
    public boolean canContinueToUse() {
        return semiAquaticMob.isInWater();
    }

    @Override
    public void start() {
        semiAquaticMob.getNavigation().moveTo(landPos.getX(), landPos.getY(), landPos.getZ(), speedModifier);
    }

    private boolean findLandPos() {
        RandomSource random = semiAquaticMob.getRandom();
        Level level = semiAquaticMob.level();
        BlockPos original = semiAquaticMob.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();
        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (level.getBlockState(mutable).isSolidRender(level, mutable) && level.getBlockState(mutable.above()).isAir() && mutable.getY() >= original.getY()) {
                landPos = mutable.immutable();
                return true;
            }
        }
        return false;
    }
}