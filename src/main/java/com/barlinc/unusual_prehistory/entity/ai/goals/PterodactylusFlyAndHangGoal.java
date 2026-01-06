package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Pterodactylus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class PterodactylusFlyAndHangGoal extends RandomFlightGoal {

    private final Pterodactylus pterodactylus;
    private boolean wantsToHang = false;
    private int hangTimer = 0;

    public PterodactylusFlyAndHangGoal(Pterodactylus pterodactylus) {
        super(pterodactylus, 1.0F, 1.5F, 16, 8, 300, 800);
        this.pterodactylus = pterodactylus;
    }

    @Override
    public void start() {
        super.start();
        this.pterodactylus.setHanging(false);
        this.hangTimer = 0;
    }

    @Override
    public void stop() {
        super.stop();
        if (wantsToHang) {
            this.pterodactylus.getNavigation().stop();
        }
        this.wantsToHang = false;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !pterodactylus.isHanging();
    }

    @Override
    public boolean canContinueToUse() {
        if (wantsToHang) {
            return !pterodactylus.getNavigation().isDone() && !pterodactylus.isHanging() && pterodactylus.groundedFor <= 0;
        } else {
            return super.canContinueToUse();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (wantsToHang) {
            if (hangTimer-- < 0) {
                this.hangTimer = 5 + pterodactylus.getRandom().nextInt(5);
                if (!pterodactylus.isHanging() && pterodactylus.canHangFrom(pterodactylus.posAbove(), pterodactylus.level().getBlockState(pterodactylus.posAbove()))) {
                    this.pterodactylus.setHanging(true);
                    this.pterodactylus.setFlying(false);
                }
            }
        }
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

    public Vec3 findHangFromPos() {
        BlockPos blockpos = null;
        Random random = new Random();
        int range = 14;
        for (int i = 0; i < 15; i++) {
            BlockPos blockpos1 = pterodactylus.blockPosition().offset(random.nextInt(range) - range / 2, 0, random.nextInt(range) - range / 2);
            if (!pterodactylus.level().isEmptyBlock(blockpos1) || !pterodactylus.level().isLoaded(blockpos1)) {
                continue;
            }
            while (pterodactylus.level().isEmptyBlock(blockpos1) && blockpos1.getY() < pterodactylus.level().getMaxBuildHeight()) {
                blockpos1 = blockpos1.above();
            }
            if (blockpos1.getY() > pterodactylus.getY() - 1 && pterodactylus.canHangFrom(blockpos1, pterodactylus.level().getBlockState(blockpos1)) && this.hasLineOfToPos(blockpos1)) {
                blockpos = blockpos1;
            }
        }
        return blockpos == null ? null : Vec3.atCenterOf(blockpos);
    }

    public boolean hasLineOfToPos(BlockPos in) {
        HitResult hitResult = pterodactylus.level().clip(new ClipContext(pterodactylus.getEyePosition(1.0F), new Vec3(in.getX() + 0.5, in.getY() + 0.5, in.getZ() + 0.5), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pterodactylus));
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            return pos.equals(in) || pterodactylus.level().isEmptyBlock(pos);
        }
        return true;
    }
}
