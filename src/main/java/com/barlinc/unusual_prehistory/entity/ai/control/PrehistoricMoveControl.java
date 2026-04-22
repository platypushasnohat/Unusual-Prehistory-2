package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PrehistoricMoveControl extends MoveControl {

    protected final PrehistoricMob prehistoricMob;
    protected final float maxYRotChange;

    public PrehistoricMoveControl(PrehistoricMob mob) {
        this(mob, 90.0F);
    }

    public PrehistoricMoveControl(PrehistoricMob mob, float maxYRotChange) {
        super(mob);
        this.prehistoricMob = mob;
        this.maxYRotChange = maxYRotChange;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (this.operation == Operation.STRAFE) {
                this.doStrafing();
            } else if (this.operation == Operation.MOVE_TO) {
                this.doMoveTo();
                if (!prehistoricMob.isLeashed() && prehistoricMob.isSitting() && !prehistoricMob.isOrderedToSit()) {
                    this.prehistoricMob.setSitting(false);
                }
            } else if (operation == Operation.JUMPING) {
                this.mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (mob.onGround()) {
                    this.operation = Operation.WAIT;
                }
            } else {
                this.mob.setZza(0.0F);
            }
        }
    }

    public void doStrafing() {
        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
        float forwards = strafeForwards;
        float right = strafeRight;
        float sqrt = Mth.sqrt(forwards * forwards + right * right);
        if (sqrt < 1.0F) {
            sqrt = 1.0F;
        }
        sqrt = speed / sqrt;
        forwards *= sqrt;
        right *= sqrt;
        float f5 = Mth.sin(mob.getYRot() * Mth.DEG_TO_RAD);
        float f6 = Mth.cos(mob.getYRot() * Mth.DEG_TO_RAD);
        float f7 = forwards * f6 - right * f5;
        float f8 = right * f6 + forwards * f5;
        if (!this.isWalkable(f7, f8)) {
            this.strafeForwards = 1.0F;
            this.strafeRight = 0.0F;
        }
        this.mob.setSpeed(speed);
        this.mob.setZza(strafeForwards);
        this.mob.setXxa(strafeRight);
        this.operation = Operation.WAIT;
    }

    public void doMoveTo() {
        this.operation = Operation.WAIT;
        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
        double x = wantedX - mob.getX();
        double y = wantedY - mob.getY();
        double z = wantedZ - mob.getZ();
        double direction = x * x + y * y + z * z;
        if (direction < (double) 2.5000003E-7F) {
            this.mob.setZza(0.0F);
            return;
        }
        this.rotateBody();
        this.mob.setSpeed(speed);
        if (mob.isInWaterOrBubble()) {
            this.mob.setSpeed(mob.getSpeed() * 2);
        }
        this.tryJump(x, y, z);
    }

    public void rotateBody() {
        double xDiff = wantedX - mob.getX();
        double zDiff = wantedZ - mob.getZ();
        float movementAngle = (float) Mth.wrapDegrees(Mth.atan2(zDiff, xDiff) * Mth.RAD_TO_DEG - 90);
        this.mob.setYRot(this.rotlerp(mob.getYRot(), movementAngle, maxYRotChange));
    }

    public void tryJump(double xDiff, double yDiff, double zDiff) {
        BlockPos blockpos = mob.blockPosition();
        BlockState blockstate = mob.level().getBlockState(blockpos);
        VoxelShape voxelshape = blockstate.getCollisionShape(mob.level(), blockpos);
        if (yDiff > (double) mob.maxUpStep() && xDiff * xDiff + zDiff * zDiff < (double) Math.max(1.0F, mob.getBbWidth()) || !voxelshape.isEmpty() && mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
            this.mob.getJumpControl().jump();
            this.operation = Operation.JUMPING;
        }
    }
}
