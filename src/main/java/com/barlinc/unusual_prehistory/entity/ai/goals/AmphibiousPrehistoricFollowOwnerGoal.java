package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class AmphibiousPrehistoricFollowOwnerGoal extends PrehistoricFollowOwnerGoal {

    public AmphibiousPrehistoricFollowOwnerGoal(PrehistoricMob tamedMob, double speedModifier, float startDistance, float stopDistance, boolean canFly) {
        super(tamedMob, speedModifier, startDistance, stopDistance, canFly);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.owner = null;
        this.tamedMob.getNavigation().stop();
    }

    @Override
    protected boolean canTeleportTo(BlockPos blockPos) {
        BlockPathTypes blockpathtypes = AmphibiousNodeEvaluator.getBlockPathTypeStatic(this.level, blockPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(blockPos.below());
            if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = blockPos.subtract(this.tamedMob.blockPosition());
                return this.level.noCollision(this.tamedMob, this.tamedMob.getBoundingBox().move(blockpos));
            }
        }
    }
}