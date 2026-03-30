package com.barlinc.unusual_prehistory.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmoothBodyRotationControl extends BodyRotationControl {

    private static final int HISTORY_SIZE = 4;
    private static final double MOVE_THRESHOLD = 2.5e-7;

    protected final Mob entity;
    protected int headStableTime;
    protected float lastStableYHeadRot;

    private final double[] histPosX = new double[HISTORY_SIZE];
    private final double[] histPosZ = new double[HISTORY_SIZE];

    public SmoothBodyRotationControl(Mob entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void clientTick() {
        for (int i = HISTORY_SIZE - 1; i > 0; i--) {
            this.histPosX[i] = histPosX[i - 1];
            this.histPosZ[i] = histPosZ[i - 1];
        }
        this.histPosX[0] = entity.getX();
        this.histPosZ[0] = entity.getZ();

        double dx = this.avgDelta(histPosX);
        double dz = this.avgDelta(histPosZ);
        double distSq = dx * dx + dz * dz;

        if (distSq > MOVE_THRESHOLD) {
            float moveAngle = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, moveAngle, 45.0F, 0.9F);
            this.entity.yHeadRot = entity.yBodyRot;
            this.rotateHeadIfNecessary();
            this.lastStableYHeadRot = entity.yHeadRot;
            this.headStableTime = 0;
        } else {
            if (this.notCarryingMobPassengers()) {
                if (Math.abs(this.entity.yHeadRot - lastStableYHeadRot) > 15.0F) {
                    this.headStableTime = 0;
                    this.lastStableYHeadRot = entity.yHeadRot;
                    this.rotateBodyIfNecessary();
                } else {
                    this.headStableTime++;
                    if (this.headStableTime > 10) {
                        this.rotateHeadTowardsFront();
                    }
                }
            }
        }
    }

    protected void rotateBodyIfNecessary() {
        this.entity.yBodyRot = Mth.rotateIfNecessary(entity.yBodyRot, entity.yHeadRot, (float) entity.getMaxHeadYRot());
    }

    protected void rotateHeadIfNecessary() {
        this.entity.yHeadRot = Mth.rotateIfNecessary(entity.yHeadRot, entity.yBodyRot, (float) entity.getMaxHeadYRot());
    }

    protected void rotateHeadTowardsFront() {
        int i = headStableTime - 10;
        float f = Mth.clamp((float) i / 10.0F, 0.0F, 1.0F);
        float f1 = (float) entity.getMaxHeadYRot() * (1.0F - f);
        this.entity.yBodyRot = Mth.rotateIfNecessary(entity.yBodyRot, entity.yHeadRot, f1);
    }

    protected boolean notCarryingMobPassengers() {
        return !(entity.getFirstPassenger() instanceof Mob);
    }

    protected float approachAngle(float current, float target, float degree, float factor) {
        float diff = Mth.degreesDifference(current, target);
        diff = Mth.clamp(diff, -degree, degree);
        return current + diff * factor;
    }

    protected double avgDelta(double[] arr) {
        return this.mean(arr, 0) - this.mean(arr, HISTORY_SIZE / 2);
    }

    protected double mean(double[] arr, int start) {
        double s = 0;
        int half = HISTORY_SIZE / 2;
        for (int i = 0; i < half; i++) {
            s += arr[start + i];
        }
        return s / half;
    }
}
