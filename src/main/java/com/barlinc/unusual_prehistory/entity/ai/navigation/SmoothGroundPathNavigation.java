package com.barlinc.unusual_prehistory.entity.ai.navigation;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SmoothGroundPathNavigation extends GroundPathNavigation {

    private float distanceModifier = 0.75F;

    public SmoothGroundPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    public SmoothGroundPathNavigation(Mob mob, Level level, float distanceModifier) {
        super(mob, level);
        this.distanceModifier = distanceModifier;
    }

    @Override
    protected void followThePath() {
        Vec3 tempMobPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() * distanceModifier;
        Vec3i nextNodePos = this.path.getNextNodePos();
        double d0 = Math.abs(this.mob.getX() - ((double) nextNodePos.getX() + 0.5D));
        double d1 = Math.abs(this.mob.getY() - (double) nextNodePos.getY());
        double d2 = Math.abs(this.mob.getZ() - ((double) nextNodePos.getZ() + 0.5D));
        boolean flag = d0 < (double) this.maxDistanceToWaypoint && d2 < (double) this.maxDistanceToWaypoint && d1 < 1.0D;
        if (flag || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(tempMobPos)) {
            this.path.advance();
        }
        this.doStuckDetection(tempMobPos);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 currentPosition) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vector3d = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!currentPosition.closerThan(vector3d, 2.0D)) {
                return false;
            } else {
                Vec3 vector3d1 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vector3d2 = vector3d1.subtract(vector3d);
                Vec3 vector3d3 = currentPosition.subtract(vector3d);
                return vector3d2.dot(vector3d3) > 0.0D;
            }
        }
    }
}