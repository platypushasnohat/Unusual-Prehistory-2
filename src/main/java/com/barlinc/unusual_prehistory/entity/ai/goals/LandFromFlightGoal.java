package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class LandFromFlightGoal extends Goal {

    protected final PrehistoricFlyingMob mob;
    protected final int maxFlightTime;

    public LandFromFlightGoal(PrehistoricFlyingMob mob, int maxFlightTime) {
        this.mob = mob;
        this.maxFlightTime = maxFlightTime;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob.isFlying() && (mob.flightTicks > maxFlightTime && mob.getRandom().nextInt(20) == 0 || mob.getTarget() != null) && this.findLandingSpot() != null;
    }

    @Override
    public void start() {
        BlockPos ground = this.findLandingSpot();
        this.mob.getNavigation().moveTo((double) ground.getX() + (double) 0.5F, ground.getY() + 1, (double) ground.getZ() + (double) 0.5F, 1.0F);
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isFlying() && !this.mob.getNavigation().isDone();
    }

    @Override
    public void tick() {
        if (this.mob.onGround() || !this.mob.level().getBlockState(this.mob.blockPosition().below()).isAir()) {
            this.mob.setFlying(false);
        }
    }

    private BlockPos findLandingSpot() {
        BlockPos.MutableBlockPos pos = this.mob.blockPosition().mutable();
        for (int i = 0; i < 14; i++) {
            if (!this.mob.level().getBlockState(pos).isAir()) {
                return pos;
            }
            pos.move(0, -1, 0);
        }
        return null;
    }
}