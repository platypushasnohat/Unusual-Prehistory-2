package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class AquaticLeapGoal extends JumpGoal {

    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
    private final PrehistoricMob prehistoricMob;
    private final int interval;
    protected boolean breached;
    private final double jumpDistance;
    private final double jumpHeight;

    public AquaticLeapGoal(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 10, 0.6D, 0.7D);
    }

    public AquaticLeapGoal(PrehistoricMob prehistoricMob, int interval, double jumpDistance, double jumpHeight) {
        this.prehistoricMob = prehistoricMob;
        this.interval = reducedTickDelay(interval);
        this.jumpDistance = jumpDistance;
        this.jumpHeight = jumpHeight;
    }

    @Override
    public boolean canUse() {
        if (this.prehistoricMob.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.prehistoricMob.getMotionDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockpos = this.prehistoricMob.blockPosition();
            for (int k : STEPS_TO_CHECK) {
                if (!this.waterIsClear(blockpos, i, j, k) || !this.surfaceIsClear(blockpos, i, j, k)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean waterIsClear(BlockPos pos, int x, int z, int scale) {
        BlockPos blockpos = pos.offset(x * scale, 0, z * scale);
        return this.prehistoricMob.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.prehistoricMob.level().getBlockState(blockpos).blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos pPos, int pDx, int pDz, int pScale) {
        return this.prehistoricMob.level().getBlockState(pPos.offset(pDx * pScale, 1, pDz * pScale)).isAir() && this.prehistoricMob.level().getBlockState(pPos.offset(pDx * pScale, 2, pDz * pScale)).isAir();
    }

    @Override
    public boolean canContinueToUse() {
        double y = this.prehistoricMob.getDeltaMovement().y;
        return (!(y * y < (double) 0.03F) || this.prehistoricMob.getXRot() == 0.0F || !(Math.abs(this.prehistoricMob.getXRot()) < 10.0F) || !this.prehistoricMob.isInWater()) && !this.prehistoricMob.onGround();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        Direction direction = this.prehistoricMob.getMotionDirection();
        this.prehistoricMob.setDeltaMovement(this.prehistoricMob.getDeltaMovement().add((double) direction.getStepX() * jumpDistance, jumpHeight, (double) direction.getStepZ() * jumpDistance));
        this.prehistoricMob.getNavigation().stop();
        if (this.prehistoricMob instanceof LeapingMob leapingMob) {
            leapingMob.setLeaping(true);
        }
    }

    @Override
    public void stop() {
        this.prehistoricMob.setXRot(0.0F);
        if (this.prehistoricMob instanceof LeapingMob leapingMob) {
            leapingMob.setLeaping(false);
        }
    }

    @Override
    public void tick() {
        boolean flag = this.breached;
        if (!flag) {
            FluidState fluidstate = this.prehistoricMob.level().getFluidState(this.prehistoricMob.blockPosition());
            this.breached = fluidstate.is(FluidTags.WATER);
        }

        if (this.breached && !flag) {
            this.prehistoricMob.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 vec3 = this.prehistoricMob.getDeltaMovement();
        if (vec3.y * vec3.y < (double) 0.03F && this.prehistoricMob.getXRot() != 0.0F) {
            this.prehistoricMob.setXRot(Mth.rotLerp(0.2F, this.prehistoricMob.getXRot(), 0.0F));
        } else if (vec3.length() > (double) 1.0E-5F) {
            double horizontalDistance = vec3.horizontalDistance();
            double xRot = Math.atan2(-vec3.y, horizontalDistance) * (double) (180F / (float) Math.PI);
            this.prehistoricMob.setXRot((float) xRot);
            this.prehistoricMob.setYRot(((float) Mth.atan2(prehistoricMob.getMotionDirection().getStepZ(), prehistoricMob.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }
}