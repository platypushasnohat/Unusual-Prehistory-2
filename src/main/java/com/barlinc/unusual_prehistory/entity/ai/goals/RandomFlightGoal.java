package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricFlyingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class RandomFlightGoal extends Goal {

    private final PrehistoricFlyingMob mob;
    private final float speedModifier;
    private final int flightRange;
    private final int flightHeight;
    private final int interval;
    private final int maxTimeFlying;
    private double x;
    private double y;
    private double z;

    public RandomFlightGoal(PrehistoricFlyingMob mob, float speedModifier, int flightRange, int flightHeight, int interval, int maxTimeFlying) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.flightRange = flightRange;
        this.flightHeight = flightHeight;
        this.maxTimeFlying = maxTimeFlying;
        this.speedModifier = speedModifier;
        this.interval = interval;
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.isVehicle() || (mob.getTarget() != null && mob.getTarget().isAlive()) || mob.isPassenger()) {
            return false;
        }
        if (!mob.isFlying() && mob.getRandom().nextInt(interval) != 0) {
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

    @Override
    public void start() {
        this.mob.setFlying(true);
        mob.getNavigation().moveTo(this.x, this.y, this.z, speedModifier);
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
        mob.landingFlag = false;
        x = 0;
        y = 0;
        z = 0;
        super.stop();
    }

    @Override
    public void tick() {
        if (mob.isFlying() && mob.onGround() && mob.timeFlying > 40) {
            mob.setFlying(false);
        }
        if (mob.isFlying() && mob.timeFlying % maxTimeFlying == 0 && !isOverWaterOrVoid()) {
            mob.landingFlag = true;
        }
        if (isOverWaterOrVoid() || mob.isInWaterOrBubble()) {
            mob.setFlying(true);
            mob.landingFlag = false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.landingFlag) {
            return !mob.getNavigation().isDone() && !mob.onGround() && mob.groundedFor <= 0;
        } else {
            return mob.isFlying() && !mob.getNavigation().isDone() && mob.groundedFor <= 0;
        }
    }

    private Vec3 findFlightPos() {
        Vec3 heightAdjusted = mob.position().add(mob.getRandom().nextInt(flightRange * 2) - flightRange, 0, mob.getRandom().nextInt(flightRange * 2) - flightRange);
        if (mob.level().canSeeSky(BlockPos.containing(heightAdjusted))) {
            Vec3 ground = groundPosition(heightAdjusted);
            heightAdjusted = new Vec3(heightAdjusted.x, ground.y + flightHeight + mob.getRandom().nextInt(6), heightAdjusted.z);
        } else {
            Vec3 ground = groundPosition(heightAdjusted);
            BlockPos ceiling = BlockPos.containing(ground).above(2);
            while (ceiling.getY() < mob.level().getMaxBuildHeight() && !mob.level().getBlockState(ceiling).isSolid()) {
                ceiling = ceiling.above();
            }
            float randCeilVal = 0.3F + mob.getRandom().nextFloat() * 0.5F;
            heightAdjusted = new Vec3(heightAdjusted.x, ground.y + (ceiling.getY() - ground.y) * randCeilVal, heightAdjusted.z);
        }

        BlockHitResult result = mob.level().clip(new ClipContext(mob.getEyePosition(), heightAdjusted, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
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
        while (ground.getY() < mob.level().getMaxBuildHeight() && !mob.level().getBlockState(ground).isSolid() && mob.level().getFluidState(ground).isEmpty()){
            ground.move(0, 1, 0);
            flag = true;
        }
        ground.move(0, -1, 0);
        while (ground.getY() > mob.level().getMinBuildHeight() && !mob.level().getBlockState(ground).isSolid() && mob.level().getFluidState(ground).isEmpty()) {
            ground.move(0, -1, 0);
        }
        return Vec3.atCenterOf(flag ? ground.above() : ground.below());
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = mob.blockPosition();
        while (position.getY() > mob.level().getMinBuildHeight() && mob.level().isEmptyBlock(position) && mob.level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return !mob.level().getFluidState(position).isEmpty() || mob.level().getBlockState(position).is(Blocks.VINE) || position.getY() <= mob.level().getMinBuildHeight();
    }
}
