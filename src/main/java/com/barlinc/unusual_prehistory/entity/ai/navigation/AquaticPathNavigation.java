package com.barlinc.unusual_prehistory.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import org.jetbrains.annotations.NotNull;

public class AquaticPathNavigation extends WaterBoundPathNavigation {

    protected final boolean canBreach;

    public AquaticPathNavigation(Mob mob, Level level) {
        this(mob, level, false);
    }

    public AquaticPathNavigation(Mob mob, Level level, boolean canBreach) {
        super(mob, level);
        this.canBreach = canBreach;
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new SwimNodeEvaluator(canBreach);
        return new PathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canUpdatePath() {
        return canBreach || mob.isInLiquid();
    }
}
