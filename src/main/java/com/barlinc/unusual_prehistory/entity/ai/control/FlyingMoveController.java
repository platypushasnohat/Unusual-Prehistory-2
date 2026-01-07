package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FlyingMoveController extends MoveControl {

    private final PrehistoricMob prehistoricMob;
    protected final float turnRadius;
    protected final float speed;

    public FlyingMoveController(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 1.0F, 0.95F);
    }

    public FlyingMoveController(PrehistoricMob prehistoricMob, float turnRadius, float speed) {
        super(prehistoricMob);
        this.prehistoricMob = prehistoricMob;
        this.turnRadius = turnRadius;
        this.speed = speed;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - prehistoricMob.getX(), this.wantedY - prehistoricMob.getY(), this.wantedZ - prehistoricMob.getZ());
                double length = vector3d.length();
                double width = prehistoricMob.getBoundingBox().getSize();
                Vec3 scale = vector3d.scale(this.speedModifier * 0.05D / length);
                this.prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(scale).scale(speed).add(0, -0.01, 0));
                if (length < width) {
                    this.operation = Operation.WAIT;
                } else if (length >= width) {
                    float yaw = -((float) Mth.atan2(scale.x * turnRadius, scale.z * turnRadius)) * (180F / (float) Math.PI);
                    this.prehistoricMob.setYRot(Mth.approachDegrees(prehistoricMob.getYRot(), yaw, 8));
                }
            }
        }
    }
}