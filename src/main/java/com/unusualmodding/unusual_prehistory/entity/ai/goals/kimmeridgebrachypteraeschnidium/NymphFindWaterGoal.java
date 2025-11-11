package com.unusualmodding.unusual_prehistory.entity.ai.goals.kimmeridgebrachypteraeschnidium;

import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumNymph;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class NymphFindWaterGoal extends Goal {

    private final KimmeridgebrachypteraeschnidiumNymph nymph;
    private BlockPos targetPos;
    private final int executionChance = 30;

    public NymphFindWaterGoal(KimmeridgebrachypteraeschnidiumNymph creature) {
        this.nymph = creature;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if (this.nymph.onGround() && !this.nymph.level().getFluidState(this.nymph.blockPosition()).is(FluidTags.WATER)) {
            if (this.nymph.getTarget() != null || this.nymph.getRandom().nextInt(executionChance) == 0) {
                targetPos = generateTarget();
                return targetPos != null;
            }
        }
        return false;
    }

    public void start() {
        if (targetPos != null) {
            this.nymph.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
        }
    }

    public void tick() {
        if (targetPos != null) {
            this.nymph.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
        }
    }

    public boolean canContinueToUse() {
        return !this.nymph.getNavigation().isDone() && targetPos != null && !this.nymph.level().getFluidState(this.nymph.blockPosition()).is(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        BlockPos blockpos = null;
        final RandomSource random = this.nymph.getRandom();
        final int range = 12;
        for (int i = 0; i < 15; i++) {
            BlockPos blockPos = this.nymph.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
            while (this.nymph.level().isEmptyBlock(blockPos) && blockPos.getY() > 1) {
                blockPos = blockPos.below();
            }
            if (this.nymph.level().getFluidState(blockPos).is(FluidTags.WATER)) {
                blockpos = blockPos;
            }
        }
        return blockpos;
    }
}