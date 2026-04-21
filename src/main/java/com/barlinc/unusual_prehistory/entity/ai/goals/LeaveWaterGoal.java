package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class LeaveWaterGoal extends MoveToBlockGoal {

    protected final AmphibiousMob amphibiousMob;
    private final int maxTimeInWater;

    public LeaveWaterGoal(AmphibiousMob amphibiousMob, double speedModifier) {
        this(amphibiousMob, speedModifier, 6000);
    }

    public LeaveWaterGoal(AmphibiousMob amphibiousMob, double speedModifier, int maxTimeInWater) {
        super(amphibiousMob, speedModifier, 16);
        this.amphibiousMob = amphibiousMob;
        this.maxTimeInWater = maxTimeInWater;
    }

    @Override
    public boolean canUse() {
        if (!amphibiousMob.isInWater() || amphibiousMob.isEepy() || amphibiousMob.isSitting()) {
            return false;
        } else if (amphibiousMob.getTimeInWater() < maxTimeInWater) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && amphibiousMob.isInWater() && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).isAir() && level.getBlockState(pos).isSolid();
    }
}