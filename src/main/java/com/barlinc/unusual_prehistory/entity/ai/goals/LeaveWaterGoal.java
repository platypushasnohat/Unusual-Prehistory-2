package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.AmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class LeaveWaterGoal extends Goal {

    protected final AmphibiousMob semiAquaticMob;
    private final double speedModifier;
    private final int maxTimeInWater;
    private BlockPos landPos;

    public LeaveWaterGoal(AmphibiousMob semiAquaticMob, double speedModifier, int maxTimeInWater) {
        this.semiAquaticMob = semiAquaticMob;
        this.speedModifier = speedModifier;
        this.maxTimeInWater = maxTimeInWater;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!semiAquaticMob.isInWaterOrBubble()) {
            return false;
        } else if (semiAquaticMob.getTimeInWater() < maxTimeInWater) {
            return false;
        }
        return this.findLandPos();
    }

    @Override
    public boolean canContinueToUse() {
        return semiAquaticMob.isInWaterOrBubble();
    }

    @Override
    public void start() {
        this.semiAquaticMob.getNavigation().moveTo(landPos.getX(), landPos.getY(), landPos.getZ(), speedModifier);
    }

    @Override
    public void tick() {
        if (semiAquaticMob.horizontalCollision && semiAquaticMob.isInWaterOrBubble()) {
            float yRot = semiAquaticMob.getYRot() * Mth.DEG_TO_RAD;
            this.semiAquaticMob.setDeltaMovement(semiAquaticMob.getDeltaMovement().add(-Mth.sin(yRot) * 0.3F, 0.26D, Mth.cos(yRot) * 0.3F));
        }
    }

    private boolean findLandPos() {
        RandomSource random = semiAquaticMob.getRandom();
        Level level = semiAquaticMob.level();
        BlockPos.MutableBlockPos mutablePos = semiAquaticMob.blockPosition().mutable();
        for (int i = 0; i < 10; i++) {
            mutablePos.move(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (level.getBlockState(mutablePos).isSolidRender(level, mutablePos) && level.getBlockState(mutablePos.above()).isAir() && mutablePos.getY() >= semiAquaticMob.blockPosition().getY()) {
                this.landPos = mutablePos.immutable();
                return true;
            }
        }
        return false;
    }
}