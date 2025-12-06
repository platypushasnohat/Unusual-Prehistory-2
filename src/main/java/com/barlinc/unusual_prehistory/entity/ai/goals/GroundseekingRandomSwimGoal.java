package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GroundseekingRandomSwimGoal extends RandomStrollGoal {

    private final PathfinderMob entity;
    protected Vec3 wantedPos;

    int radius;
    int height;
    double prox;

    public GroundseekingRandomSwimGoal(PathfinderMob entity, double spdmultiplier, int interval, int radius, int height, double proximity) {
        super(entity, spdmultiplier, interval);
        this.entity = entity;
        this.radius = radius;
        this.height = height;
        this.prox = proximity;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && entity.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        wantedPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
        return super.canContinueToUse() && entity.isInWater() && !(this.wantedPos.distanceTo(this.entity.position()) <= this.entity.getBbWidth() * prox);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Nullable
    protected Vec3 getPosition() {
        return getRandomSwimmablePosWithSeabed(this.mob, radius, height);
    }

    @Nullable
    public static Vec3 getRandomSwimmablePosWithSeabed(PathfinderMob pathfinder, int pRadius, int pVerticalDistance) {
        Vec3 testPos = DefaultRandomPos.getPos(pathfinder, pRadius, pVerticalDistance);
        int MaxSearchAmount = pRadius*pRadius*pRadius;
        for (int x = 0; testPos != null && x < MaxSearchAmount; testPos = DefaultRandomPos.getPos(pathfinder, pRadius, pVerticalDistance), x ++) {
            if (testPos != null) {
                Vec3 belowPos = testPos.subtract(0, 1, 0);
                if (pathfinder.level().getBlockState(BlockPos.containing(belowPos)).entityCanStandOn(pathfinder.level(), BlockPos.containing(testPos), pathfinder) && pathfinder.level().getBlockState(BlockPos.containing(testPos)).isPathfindable(pathfinder.level(), BlockPos.containing(testPos), PathComputationType.WATER)) {
                    return testPos;
                }
                if (x == MaxSearchAmount - 1) {
                    return testPos;
                }
            }
        }
        return null;
    }
}
