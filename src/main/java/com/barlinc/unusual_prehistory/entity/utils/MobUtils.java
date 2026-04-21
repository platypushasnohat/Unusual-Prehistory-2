package com.barlinc.unusual_prehistory.entity.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("deprecation")
public class MobUtils {

    public static float getDepthPathfindingFavor(BlockPos pos, LevelReader world) {
        int y = pos.getY() + Math.abs(world.getMinBuildHeight());
        return 1.0F / (float)(y == 0 ? 1 : y);
    }

    public static float getSurfacePathfindingFavor(BlockPos pos, LevelReader world) {
        int y = Math.abs(world.getMaxBuildHeight()) - pos.getY();
        return 1.0F / (float)(y == 0 ? 1 : y);
    }

    public static void travelInWater(PathfinderMob mob, Vec3 travelVector) {
        mob.moveRelative(mob.getSpeed(), travelVector);
        mob.move(MoverType.SELF, mob.getDeltaMovement());
        mob.setDeltaMovement(mob.getDeltaMovement().scale(0.9D));
        if (mob.horizontalCollision && mob.isEyeInFluid(FluidTags.WATER) && mob.isPathFinding()) {
            mob.setDeltaMovement(mob.getDeltaMovement().add(0.0, 0.005, 0.0));
        }
    }
}
