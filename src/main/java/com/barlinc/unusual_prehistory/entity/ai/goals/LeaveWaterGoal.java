package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class LeaveWaterGoal extends Goal {

    protected final AmphibiousMob amphibiousMob;
    private final double speedModifier;
    private final int maxTimeInWater;
    private BlockPos landPos;

    public LeaveWaterGoal(AmphibiousMob amphibiousMob, double speedModifier, int maxTimeInWater) {
        this.amphibiousMob = amphibiousMob;
        this.speedModifier = speedModifier;
        this.maxTimeInWater = maxTimeInWater;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!amphibiousMob.isInWaterOrBubble() || amphibiousMob.isEepy() || amphibiousMob.isSitting()) {
            return false;
        } else if (amphibiousMob.getTimeInWater() < maxTimeInWater) {
            return false;
        }
        return this.findLandPos();
    }

    @Override
    public boolean canContinueToUse() {
        return amphibiousMob.isInWaterOrBubble() && !amphibiousMob.getNavigation().isDone() && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    public void start() {
        this.amphibiousMob.getNavigation().moveTo(landPos.getX(), landPos.getY(), landPos.getZ(), speedModifier);
    }

    @Override
    public void tick() {
        if (amphibiousMob.horizontalCollision && amphibiousMob.isInWaterOrBubble()) {
            float yRot = amphibiousMob.getYRot() * Mth.DEG_TO_RAD;
            this.amphibiousMob.setDeltaMovement(amphibiousMob.getDeltaMovement().add(-Mth.sin(yRot) * 0.3F, 0.3D, Mth.cos(yRot) * 0.3F));
        }
    }

    private boolean findLandPos() {
        RandomSource random = amphibiousMob.getRandom();
        Level level = amphibiousMob.level();
        BlockPos.MutableBlockPos mutablePos = amphibiousMob.blockPosition().mutable();
        for (int i = 0; i < 10; i++) {
            mutablePos.move(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (level.getBlockState(mutablePos).isSolidRender(level, mutablePos) && level.getBlockState(mutablePos.above()).isAir() && mutablePos.getY() >= amphibiousMob.blockPosition().getY()) {
                this.landPos = mutablePos.immutable();
                return true;
            }
        }
        return false;
    }
}