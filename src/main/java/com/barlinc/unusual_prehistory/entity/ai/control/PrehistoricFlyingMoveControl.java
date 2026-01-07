package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.prehistoricMob.getNavigation().isDone()) {
                float flyingSpeed = (float) (this.speedModifier * this.prehistoricMob.getAttributeValue(Attributes.FLYING_SPEED));
                Vec3 vector3d = new Vec3(this.wantedX - prehistoricMob.getX(), this.wantedY - prehistoricMob.getY(), this.wantedZ - prehistoricMob.getZ());
                Vec3 deltaMovement = prehistoricMob.getDeltaMovement();
                double length = vector3d.length();
                double width = prehistoricMob.getBoundingBox().getSize();
                this.prehistoricMob.setSpeed(flyingSpeed);
                this.prehistoricMob.setDeltaMovement(deltaMovement.add(vector3d.scale(flyingSpeed * 0.05D / length)));
                if (length < width) {
                    this.operation = Operation.WAIT;
                    this.prehistoricMob.setDeltaMovement(deltaMovement.scale(0.6D));
                } else if (length >= width) {
                    float yaw = -((float) Mth.atan2(deltaMovement.x, deltaMovement.z)) * (180F / (float) Math.PI);
                    this.prehistoricMob.setYRot(Mth.approachDegrees(prehistoricMob.getYRot(), yaw, 10));
                    this.prehistoricMob.yBodyRot = prehistoricMob.getYRot();
                }
            }
        }
    }
}