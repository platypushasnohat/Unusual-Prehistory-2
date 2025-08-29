package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class KimmeridgebrachypteraeschnidiumFlyGoal extends Goal {

    private Kimmeridgebrachypteraeschnidium dragonfly;
    private double x;
    private double y;
    private double z;

    public KimmeridgebrachypteraeschnidiumFlyGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.dragonfly = dragonfly;
    }

    @Override
    public boolean canUse() {
        if (dragonfly.isVehicle() || (dragonfly.getTarget() != null && dragonfly.getTarget().isAlive()) || dragonfly.isPassenger()) {
            return false;
        }
        if (!dragonfly.isFlying() && dragonfly.getRandom().nextInt(50) != 0) {
            return false;
        }
        Vec3 target = this.getPosition();
        if (target == null) {
            return false;
        } else {
            this.x = target.x;
            this.y = target.y;
            this.z = target.z;
            return true;
        }
    }

    private Vec3 getPosition() {
        return findFlightPos();
    }

    public void start() {
        this.dragonfly.setFlying(true);
        dragonfly.getNavigation().moveTo(this.x, this.y, this.z, 1F);
    }

    public void stop() {
        dragonfly.getNavigation().stop();
        dragonfly.landingFlag = false;
        x = 0;
        y = 0;
        z = 0;
        super.stop();
    }

    public void tick() {
        if (dragonfly.isFlying() && dragonfly.onGround() && dragonfly.timeFlying > 40) {
            dragonfly.setFlying(false);
        }
        if (dragonfly.isFlying() && dragonfly.timeFlying % 600 == 0 && !isOverWaterOrVoid()) {
            dragonfly.landingFlag = true;
        }
        if (isOverWaterOrVoid() || dragonfly.isInWaterOrBubble()) {
            dragonfly.setFlying(true);
            dragonfly.landingFlag = false;
        }
    }

    public boolean canContinueToUse() {
        if (dragonfly.landingFlag) {
            return !dragonfly.getNavigation().isDone() && !dragonfly.onGround() && dragonfly.groundedFor <= 0;
        } else {
            return dragonfly.isFlying() && !dragonfly.getNavigation().isDone() && dragonfly.groundedFor <= 0;
        }
    }

    private Vec3 findFlightPos() {
        int range = 13;

        Vec3 heightAdjusted = dragonfly.position().add(dragonfly.getRandom().nextInt(range * 2) - range, 0, dragonfly.getRandom().nextInt(range * 2) - range);
        if (dragonfly.level().canSeeSky(BlockPos.containing(heightAdjusted))) {
            Vec3 ground = groundPosition(heightAdjusted);
            heightAdjusted = new Vec3(heightAdjusted.x, ground.y + 4 + dragonfly.getRandom().nextInt(8), heightAdjusted.z);
        } else {
            Vec3 ground = groundPosition(heightAdjusted);
            BlockPos ceiling = BlockPos.containing(ground).above(2);
            while (ceiling.getY() < dragonfly.level().getMaxBuildHeight() && !dragonfly.level().getBlockState(ceiling).isSolid()) {
                ceiling = ceiling.above();
            }
            float randCeilVal = 0.3F + dragonfly.getRandom().nextFloat() * 0.5F;
            heightAdjusted = new Vec3(heightAdjusted.x, ground.y + (ceiling.getY() - ground.y) * randCeilVal, heightAdjusted.z);
        }

        BlockHitResult result = dragonfly.level().clip(new ClipContext(dragonfly.getEyePosition(), heightAdjusted, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, dragonfly));
        if (result.getType() == HitResult.Type.MISS) {
            return heightAdjusted;
        } else {
            return result.getLocation();
        }
    }

    public Vec3 groundPosition(Vec3 airPosition) {
        BlockPos.MutableBlockPos ground = new BlockPos.MutableBlockPos();
        ground.set(airPosition.x, airPosition.y, airPosition.z);
        boolean flag = false;
        while (ground.getY() < dragonfly.level().getMaxBuildHeight() && !dragonfly.level().getBlockState(ground).isSolid() && dragonfly.level().getFluidState(ground).isEmpty()){
            ground.move(0, 1, 0);
            flag = true;
        }
        ground.move(0, -1, 0);
        while (ground.getY() > dragonfly.level().getMinBuildHeight() && !dragonfly.level().getBlockState(ground).isSolid() && dragonfly.level().getFluidState(ground).isEmpty()) {
            ground.move(0, -1, 0);
        }
        return Vec3.atCenterOf(flag ? ground.above() : ground.below());
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = dragonfly.blockPosition();
        while (position.getY() > dragonfly.level().getMinBuildHeight() && dragonfly.level().isEmptyBlock(position) && dragonfly.level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return !dragonfly.level().getFluidState(position).isEmpty() || dragonfly.level().getBlockState(position).is(Blocks.VINE) || position.getY() <= dragonfly.level().getMinBuildHeight();
    }
}
