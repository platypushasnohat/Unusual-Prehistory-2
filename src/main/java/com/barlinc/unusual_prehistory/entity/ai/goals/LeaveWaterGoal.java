package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.SemiAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class LeaveWaterGoal extends Goal {

    protected final SemiAquaticMob semiAquaticMob;
    private final double speedModifier;
    private final int maxTimeInWater;
    private final int maxTimeOnLand;
    private BlockPos landPos;

    public LeaveWaterGoal(SemiAquaticMob semiAquaticMob, double speedModifier, int maxTimeInWater, int maxTimeOnLand) {
        this.semiAquaticMob = semiAquaticMob;
        this.speedModifier = speedModifier;
        this.maxTimeInWater = maxTimeInWater;
        this.maxTimeOnLand = maxTimeOnLand;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!semiAquaticMob.isInWater()) {
            return false;
        }
        if (semiAquaticMob.getTimeInWater() <= maxTimeInWater || semiAquaticMob.getTimeOnLand() >= maxTimeOnLand) {
            return false;
        }
        return this.findLandPos();
    }

    @Override
    public boolean canContinueToUse() {
        return semiAquaticMob.isInWater();
    }

    @Override
    public void start() {
        this.semiAquaticMob.getNavigation().moveTo(landPos.getX(), landPos.getY(), landPos.getZ(), speedModifier);
    }

    @Override
    public void tick() {
        if (semiAquaticMob.horizontalCollision && semiAquaticMob.isInWater()) {
            float yRot = semiAquaticMob.getYRot() * Mth.DEG_TO_RAD;
            this.semiAquaticMob.setDeltaMovement(semiAquaticMob.getDeltaMovement().add(-Mth.sin(yRot) * 0.2F, 0.1D, Mth.cos(yRot) * 0.2F));
        }
    }

    private boolean findLandPos() {
        RandomSource random = semiAquaticMob.getRandom();
        Level level = semiAquaticMob.level();
        BlockPos.MutableBlockPos mutablePos = semiAquaticMob.blockPosition().mutable();
        for (int i = 0; i < 10; i++) {
            mutablePos.move(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (level.getBlockState(mutablePos).isSolidRender(level, mutablePos) && level.getBlockState(mutablePos.above()).isAir() && mutablePos.getY() >= semiAquaticMob.blockPosition().getY()) {
                landPos = mutablePos.immutable();
                return true;
            }
        }
        return false;
    }
}