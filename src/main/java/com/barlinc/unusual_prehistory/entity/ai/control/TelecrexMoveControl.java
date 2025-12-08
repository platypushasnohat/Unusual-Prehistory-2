package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class TelecrexMoveControl extends MoveControl {

    protected final PrehistoricMob prehistoricMob;

    public TelecrexMoveControl(PrehistoricMob prehistoricMob) {
        super(prehistoricMob);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - prehistoricMob.getX(), this.wantedY - prehistoricMob.getY(), this.wantedZ - prehistoricMob.getZ());
                double length = vector3d.length();
                if (length < prehistoricMob.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().scale(0.5D));
                } else {
                    prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(vector3d.scale(this.speedModifier * 1 * 0.05D / length)));
                    if (prehistoricMob.getTarget() == null) {
                        Vec3 deltaMovement = prehistoricMob.getDeltaMovement();
                        prehistoricMob.setYRot(-((float) Mth.atan2(deltaMovement.x, deltaMovement.z)) * (180F / (float) Math.PI));
                    } else {
                        double d2 = prehistoricMob.getTarget().getX() - prehistoricMob.getX();
                        double d1 = prehistoricMob.getTarget().getZ() - prehistoricMob.getZ();
                        prehistoricMob.setYRot(-((float) Mth.atan2(d2, d1)) * (180F / (float) Math.PI));
                    }
                    prehistoricMob.yBodyRot = prehistoricMob.getYRot();
                }
            } else if (this.operation == Operation.STRAFE) {
                this.operation = Operation.WAIT;
            }
        }
    }
}
