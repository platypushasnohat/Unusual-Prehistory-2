package com.barlinc.unusual_prehistory.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmoothBodyRotationControl extends BodyRotationControl {

    public float bodyLagMoving;
    public float bodyLagStill;
    public float bodyMax;
    protected final Mob entity;

    public SmoothBodyRotationControl(Mob entity) {
        super(entity);
        this.entity = entity;
        this.bodyLagMoving = 0.45F;
        this.bodyLagStill = 0.225F;
        this.bodyMax = 45.0F;
    }

    @Override
    public void clientTick() {
        if (this.isMoving()) {
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, entity.getYRot(), bodyLagMoving, bodyMax);
            this.rotateHeadIfNecessary();
        } else {
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, entity.yHeadRot, bodyLagStill, bodyMax);
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
        double dx = entity.getX() - entity.xo;
        double dz = entity.getZ() - entity.zo;
        return dx * dx + dz * dz > (double) 2.5000003E-7F;
    }
}