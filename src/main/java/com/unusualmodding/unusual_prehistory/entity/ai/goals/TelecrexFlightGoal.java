package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.entity.Telecrex;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TelecrexFlightGoal extends Goal {

    private final Telecrex telecrex;
    private double x;
    private double y;
    private double z;

    public TelecrexFlightGoal(Telecrex telecrex) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.telecrex = telecrex;
    }

    @Override
    public boolean canUse() {
        if (telecrex.isVehicle() || (telecrex.getTarget() != null && telecrex.getTarget().isAlive()) || telecrex.isPassenger()) {
            return false;
        }
        if (!telecrex.isFlying() && telecrex.getRandom().nextInt(400) != 0) {
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
        this.telecrex.setFlying(true);
        telecrex.getNavigation().moveTo(this.x, this.y, this.z, 1F);
    }

    public void stop() {
        telecrex.getNavigation().stop();
        telecrex.landingFlag = false;
        x = 0;
        y = 0;
        z = 0;
        super.stop();
    }

    public void tick() {
        if (telecrex.isFlying() && telecrex.onGround() && telecrex.timeFlying > 40) {
            telecrex.setFlying(false);
        }
        if (telecrex.isFlying() && telecrex.timeFlying % 200 == 0 && !isOverWaterOrVoid()) {
            telecrex.landingFlag = true;
        }
        if (isOverWaterOrVoid() || telecrex.isInWaterOrBubble()) {
            telecrex.setFlying(true);
            telecrex.landingFlag = false;
        }
    }

    public boolean canContinueToUse() {
        if (telecrex.landingFlag) {
            return !telecrex.getNavigation().isDone() && !telecrex.onGround() && telecrex.groundedFor <= 0;
        } else {
            return telecrex.isFlying() && !telecrex.getNavigation().isDone() && telecrex.groundedFor <= 0;
        }
    }

    private Vec3 findFlightPos() {
        int range = 32;

        Vec3 heightAdjusted = telecrex.position().add(telecrex.getRandom().nextInt(range * 2) - range, 0, telecrex.getRandom().nextInt(range * 2) - range);
        if (telecrex.level().canSeeSky(BlockPos.containing(heightAdjusted))) {
            Vec3 ground = groundPosition(heightAdjusted);
            heightAdjusted = new Vec3(heightAdjusted.x, ground.y + 2 + telecrex.getRandom().nextInt(4), heightAdjusted.z);
        } else {
            Vec3 ground = groundPosition(heightAdjusted);
            BlockPos ceiling = BlockPos.containing(ground).above(2);
            while (ceiling.getY() < telecrex.level().getMaxBuildHeight() && !telecrex.level().getBlockState(ceiling).isSolid()) {
                ceiling = ceiling.above();
            }
            float randCeilVal = 0.3F + telecrex.getRandom().nextFloat() * 0.5F;
            heightAdjusted = new Vec3(heightAdjusted.x, ground.y + (ceiling.getY() - ground.y) * randCeilVal, heightAdjusted.z);
        }

        BlockHitResult result = telecrex.level().clip(new ClipContext(telecrex.getEyePosition(), heightAdjusted, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, telecrex));
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
        while (ground.getY() < telecrex.level().getMaxBuildHeight() && !telecrex.level().getBlockState(ground).isSolid() && telecrex.level().getFluidState(ground).isEmpty()){
            ground.move(0, 1, 0);
            flag = true;
        }
        ground.move(0, -1, 0);
        while (ground.getY() > telecrex.level().getMinBuildHeight() && !telecrex.level().getBlockState(ground).isSolid() && telecrex.level().getFluidState(ground).isEmpty()) {
            ground.move(0, -1, 0);
        }
        return Vec3.atCenterOf(flag ? ground.above() : ground.below());
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = telecrex.blockPosition();
        while (position.getY() > telecrex.level().getMinBuildHeight() && telecrex.level().isEmptyBlock(position) && telecrex.level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return !telecrex.level().getFluidState(position).isEmpty() || telecrex.level().getBlockState(position).is(Blocks.VINE) || position.getY() <= telecrex.level().getMinBuildHeight();
    }
}
