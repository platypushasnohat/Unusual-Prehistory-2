package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class PrehistoricFlyingMoveControl extends MoveControl {

    protected final PrehistoricMob prehistoricMob;
    protected final float stepSize;

    public PrehistoricFlyingMoveControl(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 8);
    }

    public PrehistoricFlyingMoveControl(PrehistoricMob prehistoricMob, float stepSize) {
        super(prehistoricMob);
        this.prehistoricMob = prehistoricMob;
        this.stepSize = stepSize;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (operation == Operation.MOVE_TO) {
                Vec3 moveDirection = new Vec3(wantedX - prehistoricMob.getX(), wantedY - prehistoricMob.getY(), wantedZ - prehistoricMob.getZ());
                double length = moveDirection.length();
                double width = prehistoricMob.getBoundingBox().getSize();
                Vec3 vector3d1 = moveDirection.scale(speedModifier * 0.05D / length);
                this.prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(vector3d1).scale(0.95D).add(0, -0.01, 0));
                if (length < width) {
                    this.operation = Operation.WAIT;
                } else if (length >= width) {
                    float yaw = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    this.prehistoricMob.setYRot(Mth.approachDegrees(prehistoricMob.getYRot(), yaw, stepSize));
                }
            }
        }
    }
}