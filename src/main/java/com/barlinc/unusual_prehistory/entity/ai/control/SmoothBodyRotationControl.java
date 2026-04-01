package com.barlinc.unusual_prehistory.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmoothBodyRotationControl extends BodyRotationControl {

    protected final Mob entity;
    protected final float bodyLag;
    protected final float bodyMax;
    protected int headStableTime;
    protected float lastStableYHeadRot;

    public SmoothBodyRotationControl(Mob entity, float bodyLagMoving, float bodyMax) {
        super(entity);
        this.entity = entity;
        this.bodyLag = bodyLagMoving;
        this.bodyMax = bodyMax;
    }

    @Override
    public void clientTick() {
        if (this.isMoving()) {
            double dx = entity.getX() - entity.xo;
            double dz = entity.getZ() - entity.zo;
            float moveAngle = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, moveAngle, bodyLag, bodyMax);
            this.rotateHeadIfNecessary();
            this.lastStableYHeadRot = entity.yHeadRot;
            this.headStableTime = 0;
        } else {
            this.rotateHeadIfNecessary();
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, entity.yHeadRot, bodyLag * 0.66F, bodyMax * 0.33F);
        }
    }

    protected float approachAngle(float current, float target, float factor, float maxDelta) {
        float diff = Mth.degreesDifference(current, target);
        diff = Mth.clamp(diff, -maxDelta, maxDelta);
        return current + diff * factor;
    }

    protected void rotateHeadIfNecessary() {
        this.entity.yHeadRot = Mth.rotateIfNecessary(entity.yHeadRot, entity.yBodyRot, (float) entity.getMaxHeadYRot());
    }

    protected boolean isMoving() {
        double dx = this.entity.getX() - this.entity.xo;
        double dz = this.entity.getZ() - this.entity.zo;
        return dx * dx + dz * dz > (double) 2.5000003E-7F;
    }
}
