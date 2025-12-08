package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GroundSeekingRandomSwimGoal extends RandomStrollGoal {

    protected final PathfinderMob entity;

    private final int radius;
    private final int height;

    public GroundSeekingRandomSwimGoal(PathfinderMob entity, double speedMultiplier, int interval, int radius, int height) {
        super(entity, speedMultiplier, interval);
        this.entity = entity;
        this.radius = radius;
        this.height = height;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && entity.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && entity.isInWater();
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return getRandomSwimmablePosWithSeabed(this.mob, radius, height);
    }

    @Nullable
    private static Vec3 getRandomSwimmablePosWithSeabed(PathfinderMob pathfinder, int radius, int verticalDistance) {
        Vec3 wantedPos = DefaultRandomPos.getPos(pathfinder, radius, verticalDistance);
        int MaxSearchAmount = radius * radius * radius;
        for (int x = 0; wantedPos != null && x < MaxSearchAmount; wantedPos = DefaultRandomPos.getPos(pathfinder, radius, verticalDistance), x++) {
            Vec3 belowPos = wantedPos.subtract(0, 1, 0);
            if (pathfinder.level().getBlockState(BlockPos.containing(belowPos)).entityCanStandOn(pathfinder.level(), BlockPos.containing(wantedPos), pathfinder) && pathfinder.level().getBlockState(BlockPos.containing(wantedPos)).isPathfindable(pathfinder.level(), BlockPos.containing(wantedPos), PathComputationType.WATER)) {
                return wantedPos;
            }
            if (x == MaxSearchAmount - 1) {
                return wantedPos;
            }
        }
        return null;
    }
}
