package com.unusualmodding.unusual_prehistory.entity.ai.navigation;

import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmoothTurningMoveControl extends MoveControl {

    public SmoothTurningMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        PrehistoricMob prehistoricMob = (PrehistoricMob) mob;
        if (!prehistoricMob.refuseToMove()) {
            super.tick();
        }
        if (operation == Operation.MOVE_TO) {
            operation = Operation.WAIT;
            double x = wantedX - mob.getX();
            double y = wantedY - mob.getY();
            double z = wantedZ - mob.getZ();
            double horizontalDist = x * x + z * z;
            double dist = horizontalDist + y * y;
            if (dist < 2.500000277905201E-7) {
                mob.setZza(0);
                return;
            }
            if (horizontalDist > 0.12) {
                float newYRot = (float) Mth.wrapDegrees((Mth.atan2(z, x) * Mth.RAD_TO_DEG) - 90);
                float maxTurn = ((PrehistoricMob) mob).getMaxTurnDistancePerTick();
                mob.setYRot(rotlerp(mob.getYRot(), newYRot, maxTurn));
            }
            mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (mob.isInWater()) {
                mob.setSpeed(mob.getSpeed() * 2);
            }
            BlockPos blockPos = mob.blockPosition();
            BlockState blockState = mob.level().getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getCollisionShape(mob.level(), blockPos);
            boolean jump = false;
            if (y > mob.maxUpStep() && y <= 1.5) {
                if (mob.horizontalCollision) {
                    jump = true;
                } else {
                    double jumpDistance = Math.max(0.5, 30.7 * Mth.square(mob.getSpeed()) * 0.4) + 6 * ((mob.getSpeed()) * 0.4) - 0.7;
                    if (!mob.level().isEmptyBlock(new BlockPos((int) wantedX, (int) (wantedY - 1), (int) wantedZ))) {
                        jumpDistance += 1.3;
                    }
                    if (horizontalDist < jumpDistance) {
                        jump = true;
                    }
                }
            }
            if (!jump && !voxelShape.isEmpty() && mob.getY() < voxelShape.max(Direction.Axis.Y) + blockPos.getY() && !blockState.is(BlockTags.DOORS) && !blockState.is(BlockTags.FENCES)) {
                jump = true;
            }
            if (jump) {
                mob.getJumpControl().jump();
                operation = Operation.JUMPING;
            }
        } else if (operation == Operation.JUMPING) {
            mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (mob.onGround()) {
                operation = Operation.WAIT;
            }
        } else {
            super.tick();
        }
    }
}
