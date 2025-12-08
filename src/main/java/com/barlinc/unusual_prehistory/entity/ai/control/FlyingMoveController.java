package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FlyingMoveController extends MoveControl {

    private final PrehistoricMob prehistoricMob;

    public FlyingMoveController(PrehistoricMob prehistoricMob) {
        super(prehistoricMob);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - prehistoricMob.getX(), this.wantedY - prehistoricMob.getY(), this.wantedZ - prehistoricMob.getZ());
                double length = vector3d.length();
                double width = prehistoricMob.getBoundingBox().getSize();
                Vec3 scale = vector3d.scale(this.speedModifier * 0.05D / length);
                prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(scale).scale(0.95D).add(0, -0.01, 0));
                if (length < width) {
                    this.operation = Operation.WAIT;
                } else if (length >= width) {
                    float yaw = -((float) Mth.atan2(scale.x, scale.z)) * (180F / (float) Math.PI);
                    prehistoricMob.setYRot(Mth.approachDegrees(prehistoricMob.getYRot(), yaw, 8));
                }
            }
        }
    }
}