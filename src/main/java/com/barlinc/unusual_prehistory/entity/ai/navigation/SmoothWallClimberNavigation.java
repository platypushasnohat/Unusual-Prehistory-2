package com.barlinc.unusual_prehistory.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmoothWallClimberNavigation extends WallClimberNavigation implements ExtendedNavigator {

    public SmoothWallClimberNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    public Mob getMob() {
        return mob;
    }

    @Nullable
    @Override
    public Path getPath() {
        return super.getPath();
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return createSmoothPathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    public int getSurfaceY() {
        return super.getSurfaceY();
    }
}