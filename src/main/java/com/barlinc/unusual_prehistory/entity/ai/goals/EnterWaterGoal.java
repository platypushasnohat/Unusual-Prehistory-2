package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class EnterWaterGoal extends MoveToBlockGoal {

    protected final AmphibiousMob amphibiousMob;
    private final int maxTimeOnLand;

    public EnterWaterGoal(AmphibiousMob amphibiousMob, double speedModifier) {
        this(amphibiousMob, speedModifier, 6000);
    }

    public EnterWaterGoal(AmphibiousMob amphibiousMob, double speedModifier, int maxTimeOnLand) {
        super(amphibiousMob, speedModifier, 16);
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
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !amphibiousMob.isInWater() && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, @NotNull BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.WATER);
    }
}