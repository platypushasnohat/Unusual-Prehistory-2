package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class EnterWaterGoal extends Goal {

    protected final AmphibiousMob amphibiousMob;
    protected final int maxTimeOnLand;
    private final double speedModifier;
    private BlockPos waterPos;

    public EnterWaterGoal(AmphibiousMob amphibiousMob, double speedModifier) {
        this(amphibiousMob, speedModifier, 6000);
    }

    public EnterWaterGoal(AmphibiousMob amphibiousMob, double speedModifier, int maxTimeOnLand) {
        this.speedModifier = speedModifier;
        this.amphibiousMob = amphibiousMob;
        this.maxTimeOnLand = maxTimeOnLand;
    }

    @Override
    public boolean canUse() {
        if (amphibiousMob.isInWater() || amphibiousMob.isEepy() || amphibiousMob.isSitting()) {
            return false;
        } else if (amphibiousMob.getTimeOnLand() < maxTimeOnLand) {
            return false;
        }
        return this.findWaterPos();
    }

    @Override
    public boolean canContinueToUse() {
        return !amphibiousMob.getNavigation().isDone() && !amphibiousMob.isInWater() && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    public void start() {
        this.amphibiousMob.getNavigation().moveTo(waterPos.getX(), waterPos.getY(), waterPos.getZ(), speedModifier);
    }

    private boolean findWaterPos() {
        RandomSource random = amphibiousMob.getRandom();
        Level level = amphibiousMob.level();
        BlockPos original = amphibiousMob.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (level.getFluidState(mutable).is(FluidTags.WATER)) {
                this.waterPos = mutable.immutable();
                return true;
            }
        }
        return false;
    }
}