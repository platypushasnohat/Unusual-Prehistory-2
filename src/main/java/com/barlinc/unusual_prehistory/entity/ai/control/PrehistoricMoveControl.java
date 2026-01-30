package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PrehistoricMoveControl extends MoveControl {

    private final PrehistoricMob prehistoricMob;

    public PrehistoricMoveControl(PrehistoricMob mob) {
        super(mob);
        this.prehistoricMob = mob;
    }

    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove() && !prehistoricMob.isDancing()) {
            if (this.operation == MoveControl.Operation.STRAFE) {
                this.doStrafing();
            } else if (this.operation == MoveControl.Operation.MOVE_TO) {
                this.doMoveTo();
                if (!this.prehistoricMob.isLeashed() && this.prehistoricMob.isMobSitting() && !this.prehistoricMob.isInSitPoseTransition()) {
                    this.prehistoricMob.stopSitting();
                }
            } else if (this.operation == MoveControl.Operation.JUMPING) {
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.mob.onGround()) {
                    this.operation = MoveControl.Operation.WAIT;
                }
            } else {
                this.mob.setZza(0.0F);
            }
        }
    }

    protected void doStrafing() {
        float speed = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
        float forwards = this.strafeForwards;
        float right = this.strafeRight;
        float f4 = Mth.sqrt(forwards * forwards + right * right);
        if (f4 < 1.0F) {
            f4 = 1.0F;
        }

        f4 = speed / f4;
        forwards *= f4;
        right *= f4;
        float f5 = Mth.sin(this.mob.getYRot() * ((float) Math.PI / 180F));
        float f6 = Mth.cos(this.mob.getYRot() * ((float) Math.PI / 180F));
        float f7 = forwards * f6 - right * f5;
        float f8 = right * f6 + forwards * f5;
        if (!this.isWalkable(f7, f8)) {
            this.strafeForwards = 1.0F;
            this.strafeRight = 0.0F;
        }
        this.mob.setSpeed(speed);
        this.mob.setZza(this.strafeForwards);
        this.mob.setXxa(this.strafeRight);
        this.operation = MoveControl.Operation.WAIT;
    }

    protected void doMoveTo() {
        this.operation = MoveControl.Operation.WAIT;
        double x = this.wantedX - this.mob.getX();
        double y = this.wantedY - this.mob.getY();
        double z = this.wantedZ - this.mob.getZ();
        double direction = x * x + y * y + z * z;
        if (direction < (double) 2.5000003E-7F) {
            this.mob.setZza(0.0F);
            return;
        }
        float f9 = (float) (Mth.atan2(z, x) * (double) (180F / (float) Math.PI)) - 90.0F;
        this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
        this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
        if (mob.isInWater()) {
            mob.setSpeed(mob.getSpeed() * 2);
        }
        BlockPos blockpos = this.mob.blockPosition();
        BlockState blockstate = this.mob.level().getBlockState(blockpos);
        VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
        if (y > (double) this.mob.getStepHeight() && x * x + z * z < (double) Math.max(1.0F, this.mob.getBbWidth()) || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
            this.mob.getJumpControl().jump();
            this.operation = MoveControl.Operation.JUMPING;
        }
    }
}
