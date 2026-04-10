package com.barlinc.unusual_prehistory.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class SwimmingMoveControl extends MoveControl {

    protected final int maxTurnX;
    protected final int maxTurnY;
    protected final float inWaterSpeedModifier;

    public SwimmingMoveControl(Mob mob, int maxTurnX, int maxTurnY, float inWaterSpeedModifier) {
        super(mob);
        this.maxTurnX = maxTurnX;
        this.maxTurnY = maxTurnY;
        this.inWaterSpeedModifier = inWaterSpeedModifier;
    }

    @Override
    public void tick() {
        if (operation == MoveControl.Operation.MOVE_TO && !mob.getNavigation().isDone()) {
            double x = wantedX - mob.getX();
            double y = wantedY - mob.getY();
            double z = wantedZ - mob.getZ();
            double direction = x * x + y * y + z * z;
            if (direction < 2.5000003E-7F) {
                this.mob.setZza(0.0F);
            } else {
                float newYRot = (float) Mth.wrapDegrees(Mth.atan2(z, x) * Mth.RAD_TO_DEG - 90);
                this.mob.setYRot(this.rotlerp(mob.getYRot(), newYRot, (float) maxTurnY));
                this.mob.yBodyRot = mob.getYRot();
                this.mob.yHeadRot = mob.getYRot();
                float f1 = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.mob.isInWater()) {
                    this.mob.setSpeed(f1 * this.inWaterSpeedModifier);
                    double d4 = Math.sqrt(x * x + z * z);
                    if (Math.abs(y) > 1.0E-5F || Math.abs(d4) > 1.0E-5F) {
                        float f3 = -((float) (Mth.atan2(y, d4) * 180.0F / (float) Math.PI));
                        f3 = Mth.clamp(Mth.wrapDegrees(f3), (float) (-maxTurnX), (float) maxTurnX);
                        this.mob.setXRot(this.rotlerp(mob.getXRot(), f3, 5.0F));
                    }
                    float f6 = Mth.cos(mob.getXRot() * (float) (Math.PI / 180.0));
                    float f4 = Mth.sin(mob.getXRot() * (float) (Math.PI / 180.0));
                    this.mob.zza = f6 * f1;
                    this.mob.yya = -f4 * f1;
                }
                else {
                    float f5 = Math.abs(Mth.wrapDegrees(mob.getYRot() - newYRot));
                    float f2 = getTurningSpeedFactor(f5);
                    this.mob.setSpeed(f1 * f2);
                }
            }
        } else {
            this.mob.setSpeed(0.0F);
            this.mob.setXxa(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }

    protected static float getTurningSpeedFactor(float degreesToTurn) {
        return 1.0F - Mth.clamp((degreesToTurn - 10.0F) / 50.0F, 0.0F, 1.0F);
    }
}
