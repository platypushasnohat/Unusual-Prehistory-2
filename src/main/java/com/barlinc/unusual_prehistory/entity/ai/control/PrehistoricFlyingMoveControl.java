package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class PrehistoricFlyingMoveControl extends MoveControl {

    private final PrehistoricMob prehistoricMob;

    public PrehistoricFlyingMoveControl(PrehistoricMob prehistoricMob) {
        super(prehistoricMob);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - prehistoricMob.getX(), this.wantedY - prehistoricMob.getY(), this.wantedZ - prehistoricMob.getZ());
                double d0 = vector3d.length();
                double width = prehistoricMob.getBoundingBox().getSize();
                Vec3 vector3d1 = vector3d.scale(this.speedModifier * 0.05D / d0);
                this.prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(vector3d1).scale(0.95D).add(0, -0.01, 0));
                if (d0 < width) {
                    this.operation = Operation.WAIT;
                } else if (d0 >= width) {
                    float yaw = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    this.prehistoricMob.setYRot(Mth.approachDegrees(prehistoricMob.getYRot(), yaw, 8));
                }
            }
        }
    }
}