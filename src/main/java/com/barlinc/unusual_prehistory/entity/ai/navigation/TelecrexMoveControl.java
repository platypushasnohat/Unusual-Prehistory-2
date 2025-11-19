package com.barlinc.unusual_prehistory.entity.ai.navigation;

import com.barlinc.unusual_prehistory.entity.Telecrex;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class TelecrexMoveControl extends MoveControl {

    private final Telecrex telecrex;

    public TelecrexMoveControl(Telecrex telecrex) {
        super(telecrex);
        this.telecrex = telecrex;
    }

    public void tick() {
        if (!telecrex.refuseToMove()) {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - telecrex.getX(), this.wantedY - telecrex.getY(), this.wantedZ - telecrex.getZ());
                double d0 = vector3d.length();
                if (d0 < telecrex.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    telecrex.setDeltaMovement(telecrex.getDeltaMovement().scale(0.5D));
                } else {
                    telecrex.setDeltaMovement(telecrex.getDeltaMovement().add(vector3d.scale(this.speedModifier * 1 * 0.05D / d0)));
                    if (telecrex.getTarget() == null) {
                        Vec3 vector3d1 = telecrex.getDeltaMovement();
                        telecrex.setYRot(-((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI));
                    } else {
                        double d2 = telecrex.getTarget().getX() - telecrex.getX();
                        double d1 = telecrex.getTarget().getZ() - telecrex.getZ();
                        telecrex.setYRot(-((float) Mth.atan2(d2, d1)) * (180F / (float) Math.PI));
                    }
                    telecrex.yBodyRot = telecrex.getYRot();
                }
            } else if (this.operation == Operation.STRAFE) {
                this.operation = Operation.WAIT;
            }
        }
    }
}
