package com.barlinc.unusual_prehistory.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/*
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://mozilla.org/MPL/2.0/.
 *
 * Source: SmartBrainLib - https://github.com/Tslat/SmartBrainLib/tree/1.20
 * Modifications by: Unusual Prehistory 2 - 3/31/2026
 */

public class SmoothGroundPathNavigation extends GroundPathNavigation implements ExtendedNavigator {

    public SmoothGroundPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    public Mob getMob() {
        return this.mob;
    }

    @Nullable
    @Override
    public Path getPath() {
        return super.getPath();
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);

        return new PathFinder(this.nodeEvaluator, maxVisitedNodes) {
            @Nullable
            @Override
            public Path findPath(PathNavigationRegion navigationRegion, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
                final Path path = super.findPath(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);

                return path == null ? null : new Path(path.nodes, path.getTarget(), path.canReach()) {
                    @Override
                    public Vec3 getEntityPosAtNode(Entity entity, int nodeIndex) {
                        return SmoothGroundPathNavigation.this.getEntityPosAtNode(nodeIndex);
                    }
                };
            }
        };
    }

    @Override
    protected void followThePath() {
        final Vec3 safeSurfacePos = getTempMobPos();
        final int shortcutNode = getClosestVerticalTraversal(Mth.floor(safeSurfacePos.y));
        this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75f ? this.mob.getBbWidth() / 2f : 0.75f - this.mob.getBbWidth() / 2f;

        if (!this.attemptShortcut(shortcutNode, safeSurfacePos)) {
            if (this.isCloseToNextNode(0.5f) || this.isAboutToTraverseVertically() && this.isCloseToNextNode(getMaxDistanceToWaypoint())) {
                this.path.advance();
            }
        }
        this.doStuckDetection(safeSurfacePos);
    }

    @Override
    public int getSurfaceY() {
        return super.getSurfaceY();
    }

    protected int getClosestVerticalTraversal(int safeSurfaceHeight) {
        final int nodesLength = this.path.getNodeCount();
        for (int nodeIndex = this.path.getNextNodeIndex(); nodeIndex < nodesLength; nodeIndex++) {
            if (this.path.getNode(nodeIndex).y != safeSurfaceHeight) return nodeIndex;
        }
        return nodesLength;
    }
}