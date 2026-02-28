package com.barlinc.unusual_prehistory.entity.ai.goals.pterodactylus;

import com.barlinc.unusual_prehistory.entity.ai.goals.RandomFlightGoal;
import com.barlinc.unusual_prehistory.entity.mob.update_4.Pterodactylus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

public class PterodactylusFlyAndHangGoal extends RandomFlightGoal {

    private final Pterodactylus pterodactylus;
    private boolean wantsToHang = false;
    private int hangTimer = 0;

    public PterodactylusFlyAndHangGoal(Pterodactylus pterodactylus) {
        super(pterodactylus, 1.0F, 1.5F, 13, 4, 100, 900);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.pterodactylus = pterodactylus;
    }

    @Override
    public boolean canUse() {
        if (pterodactylus.isHanging() || pterodactylus.getIdleState() == 1 || pterodactylus.groundTicks > 0) {
            return false;
        }
        this.wantsToHang = pterodactylus.flightTicks > 300 && !pterodactylus.isRunning();
        return super.canUse();
    }

    @Override
    protected Vec3 getPosition() {
        if (wantsToHang) {
            Vec3 hangPos = this.findHangFromPos();
            if (hangPos != null) {
                return hangPos;
            }
        }
        return super.getPosition();
    }

    @Override
    public void start() {
        super.start();
        this.pterodactylus.setHanging(false);
        this.hangTimer = 0;
    }

    @Override
    public void tick() {
        if (wantsToHang) {
            if (hangTimer-- < 0) {
                this.hangTimer = 5 + pterodactylus.getRandom().nextInt(5);
                if (!pterodactylus.isHanging() && pterodactylus.canHangFrom(pterodactylus.posAbove(), pterodactylus.level().getBlockState(pterodactylus.posAbove()))) {
                    this.pterodactylus.setHanging(true);
                    this.pterodactylus.setFlying(false);
                }
            }
        }
        if (pterodactylus.isFlying() && pterodactylus.onGround() && pterodactylus.flightTicks > 40) {
            this.pterodactylus.setFlying(false);
        }
        if (this.isOverWaterOrVoid() || pterodactylus.isInWaterOrBubble()) {
            this.pterodactylus.setFlying(true);
            this.pterodactylus.setHanging(false);
            this.pterodactylus.landingFlag = false;
        }
        if (pterodactylus.isFlying() && pterodactylus.flightTicks > maxTimeFlying && !this.isOverWaterOrVoid()) {
            this.pterodactylus.landingFlag = true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (wantsToHang) {
            return !pterodactylus.getNavigation().isDone() && !pterodactylus.isHanging() && pterodactylus.groundTicks <= 0;
        } else {
            return pterodactylus.isFlying() && !pterodactylus.getNavigation().isDone() && pterodactylus.groundTicks <= 0;
        }
    }

    @Override
    public void stop() {
        if (wantsToHang) {
            this.pterodactylus.getNavigation().stop();
        }
        this.wantsToHang = false;
    }

    public Vec3 findHangFromPos() {
        BlockPos blockpos = null;
        Random random = new Random();
        int range = 14;
        for (int i = 0; i < 15; i++) {
            BlockPos blockpos1 = pterodactylus.blockPosition().offset(random.nextInt(range) - range / 2, 0, random.nextInt(range) - range / 2);
            if (!this.pterodactylus.level().isEmptyBlock(blockpos1) || !pterodactylus.level().isLoaded(blockpos1)) {
                continue;
            }
            while (pterodactylus.level().isEmptyBlock(blockpos1) && blockpos1.getY() < pterodactylus.level().getMaxBuildHeight()) {
                blockpos1 = blockpos1.above();
            }
            if (blockpos1.getY() > pterodactylus.getY() - 1 && pterodactylus.canHangFrom(blockpos1, pterodactylus.level().getBlockState(blockpos1)) && hasLineOfToPos(blockpos1)) {
                blockpos = blockpos1;
            }
        }
        return blockpos == null ? null : Vec3.atCenterOf(blockpos);
    }

    public boolean hasLineOfToPos(BlockPos blockPos) {
        HitResult result = pterodactylus.level().clip(new ClipContext(pterodactylus.getEyePosition(1.0F), new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pterodactylus));
        if (result instanceof BlockHitResult blockRayTraceResult) {
            BlockPos pos = blockRayTraceResult.getBlockPos();
            return pos.equals(blockPos) || pterodactylus.level().isEmptyBlock(pos);
        }
        return true;
    }
}
