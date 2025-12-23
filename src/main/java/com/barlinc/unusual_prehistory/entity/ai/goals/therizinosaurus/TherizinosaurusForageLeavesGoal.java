package com.barlinc.unusual_prehistory.entity.ai.goals.therizinosaurus;

import com.barlinc.unusual_prehistory.entity.Therizinosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TherizinosaurusForageLeavesGoal extends MoveToBlockGoal {

    private final Therizinosaurus therizinosaurus;
    private int idleAtLeavesTime = 0;
    private boolean isAboveDestination;

    private int moveCooldown = 0;

    public TherizinosaurusForageLeavesGoal(Therizinosaurus therizinosaurus, double speedModifier, int searchRange, int verticalSearchRange) {
        super(therizinosaurus, speedModifier, searchRange, verticalSearchRange);
        this.therizinosaurus = therizinosaurus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !therizinosaurus.isBaby() && therizinosaurus.getControllingPassenger() == null && therizinosaurus.getMainHandItem().isEmpty();
    }

    @Override
    public void stop() {
        this.idleAtLeavesTime = 0;
    }

    @Override
    public void start() {
        super.start();
        this.moveCooldown = 30 + therizinosaurus.getRandom().nextInt(50);
    }

    @Override
    public double acceptedDistance() {
        return 4D;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.moveCooldown == 0;
    }

    @Override
    public void tick() {
        if (moveCooldown > 0) {
            this.moveCooldown--;
        }
        BlockPos blockpos = this.getMoveToTarget();
        if (!this.isWithinXZDist(blockpos, mob.position(), this.acceptedDistance())) {
            this.isAboveDestination = false;
            this.tryTicks++;
            if (this.shouldRecalculatePath()) {
                this.moveCooldown = 30 + therizinosaurus.getRandom().nextInt(50);
                this.mob.getNavigation().moveTo((double) ((float) blockpos.getX()) + 0.5D, blockpos.getY(), (double) ((float) blockpos.getZ()) + 0.5D, speedModifier);
            }
        } else {
            this.isAboveDestination = true;
            this.tryTicks = 0;
        }

        if (this.isReachedTarget() && Math.abs(therizinosaurus.getY() - blockPos.getY()) <= 3) {
            this.therizinosaurus.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5));
            this.therizinosaurus.setForagingTree(therizinosaurus.getY() + 2 < blockPos.getY());
            if (therizinosaurus.getPose() == Pose.STANDING) {
                therizinosaurus.setPose(UP2Poses.FORAGING.get());
            }
            if (idleAtLeavesTime >= 10) {
                this.stop();
            } else {
                this.idleAtLeavesTime++;
            }
        }

    }

    @Override
    protected void moveMobToBlock() {
    }

    private boolean isWithinXZDist(BlockPos blockpos, Vec3 positionVec, double distance) {
        return blockpos.distSqr(new BlockPos((int) positionVec.x(), blockpos.getY(), (int) positionVec.z())) < distance * distance;
    }

    @Override
    protected boolean isReachedTarget() {
        return this.isAboveDestination;
    }

    @Override
    protected boolean isValidTarget(@NotNull LevelReader level, @NotNull BlockPos pos) {
        return level.getBlockState(pos).is(UP2BlockTags.THERIZINOSAURUS_FORAGING_BLOCKS) && this.canSeeBlock(pos);
    }

    private boolean canSeeBlock(BlockPos destinationBlock) {
        final Vec3 vec3 = new Vec3(therizinosaurus.getX(), therizinosaurus.getEyeY() + 2, therizinosaurus.getZ());
        final Vec3 blockVec = Vec3.atCenterOf(destinationBlock);
        final BlockHitResult result = therizinosaurus.level().clip(new ClipContext(vec3, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, therizinosaurus));
        return result.getBlockPos().equals(destinationBlock);
    }
}