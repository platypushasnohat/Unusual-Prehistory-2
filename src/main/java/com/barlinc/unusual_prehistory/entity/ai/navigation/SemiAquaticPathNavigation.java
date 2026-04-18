package com.barlinc.unusual_prehistory.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SemiAquaticPathNavigation extends WaterBoundPathNavigation {

    public SemiAquaticPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
        return new PathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canMoveDirectly(@NotNull Vec3 posVec31, Vec3 posVec32) {
        Vec3 vector3d = new Vec3(posVec32.x, posVec32.y + (double) mob.getBbHeight() * 0.5D, posVec32.z);
        return this.level.clip(new ClipContext(posVec31, vector3d, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() == HitResult.Type.MISS;
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    @Override
    protected @NotNull Vec3 getTempMobPos() {
        return mob.isInWaterOrBubble() ? super.getTempMobPos() : new Vec3(mob.getX(), mob.getY(0.5D), mob.getZ());
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return !level.getBlockState(pos.below()).isAir();
    }
}