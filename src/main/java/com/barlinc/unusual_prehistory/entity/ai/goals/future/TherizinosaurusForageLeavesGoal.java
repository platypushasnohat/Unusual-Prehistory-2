package com.barlinc.unusual_prehistory.entity.ai.goals.future;

import com.barlinc.unusual_prehistory.entity.mob.future.Therizinosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TherizinosaurusForageLeavesGoal extends MoveToBlockGoal {

    private final Therizinosaurus therizinosaurus;
    private boolean stopFlag = false;

    private int reachCheckTime = 50;
    private int timer;

    public TherizinosaurusForageLeavesGoal(Therizinosaurus therizinosaurus, double speedModifier, int range) {
        super(therizinosaurus, speedModifier, range, 7);
        this.therizinosaurus = therizinosaurus;
    }

    @Override
    public void start() {
        super.start();
        this.timer = 0;
    }

    @Override
    public void stop() {
        this.blockPos = BlockPos.ZERO;
        super.stop();
        this.stopFlag = false;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !therizinosaurus.isBaby();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !stopFlag;
    }

    @Override
    protected int nextStartTick(@NotNull PathfinderMob mob) {
        return reducedTickDelay(220 + therizinosaurus.getRandom().nextInt(500));
    }

    @Override
    public double acceptedDistance() {
        return 4.0D;
    }

    @Override
    protected boolean isReachedTarget() {
        BlockPos target = this.getMoveToTarget();
        return therizinosaurus.distanceToSqr(target.getX() + 0.5F, therizinosaurus.getY(), target.getZ() + 0.5F) < this.acceptedDistance();
    }

    @Override
    protected @NotNull BlockPos getMoveToTarget() {
        return this.getStandAtTreePos(blockPos);
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos target = this.getMoveToTarget();

        if (reachCheckTime > 0) {
            this.reachCheckTime--;
        } else {
            this.reachCheckTime = 50 + therizinosaurus.getRandom().nextInt(100);
            if (!this.canReach(target)) {
                this.stopFlag = true;
                this.blockPos = BlockPos.ZERO;
                return;
            }
        }
        if (this.isReachedTarget()) {
            if (therizinosaurus.getPose() == Pose.STANDING) {
                if (this.getHeightOfBlock(therizinosaurus.level(), blockPos) > 4) {
                    this.therizinosaurus.setForagingTree(true);
                }
                this.therizinosaurus.setPose(UP2Poses.FORAGING.get());
            } else if (therizinosaurus.getPose() == UP2Poses.FORAGING.get()) {
                this.timer++;
                if (timer % 5 == 0) {
                    this.spawnEffectsAtBlock(blockPos);
                    this.therizinosaurus.playSound(therizinosaurus.level().getBlockState(blockPos).getSoundType().getHitSound(), 1.0F, 0.9F + therizinosaurus.getRandom().nextFloat() * 0.25F);
                }
                if (timer >= 60) {
                    this.stopFlag = true;
                    this.blockPos = BlockPos.ZERO;
                }
            }
        }
    }

    @Override
    protected void moveMobToBlock() {
        BlockPos pos = this.getMoveToTarget();
        this.mob.getNavigation().moveTo((double) ((float) pos.getX()) + 0.5D, pos.getY(), (double) ((float) pos.getZ()) + 0.5D, this.speedModifier);
    }

    protected BlockPos getStandAtTreePos(BlockPos target) {
        Vec3 vec3 = Vec3.atCenterOf(target).subtract(therizinosaurus.position());
        float f = -((float) Mth.atan2(vec3.x, vec3.z)) * 180.0F / (float) Math.PI;
        Direction direction = Direction.fromYRot(f);
        if (therizinosaurus.level().getBlockState(target.below()).isAir()) {
            target = target.relative(direction);
        }
        return target.relative(direction.getOpposite(), 4).atY((int) therizinosaurus.getY());
    }

    private int getHeightOfBlock(LevelReader level, BlockPos pos) {
        int i = 0;
        while (pos.getY() > level.getMinBuildHeight() && (level.getBlockState(pos).is(UP2BlockTags.THERIZINOSAURUS_FORAGING_BLOCKS) || level.getBlockState(pos).isAir())) {
            pos = pos.below();
            i++;
        }
        return i;
    }

    private boolean highEnough(LevelReader level, BlockPos pos) {
        int height = this.getHeightOfBlock(level, pos);
        if (therizinosaurus.isBaby()) {
            return height <= 1;
        }
        return height > 3 && height < 8;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, @NotNull BlockPos pos) {
        return level.getBlockState(pos).is(UP2BlockTags.THERIZINOSAURUS_FORAGING_BLOCKS) && this.highEnough(level, pos);
    }

    private boolean canReach(BlockPos target) {
        Path path = therizinosaurus.getNavigation().createPath(target, 0);
        if (path == null) {
            return false;
        } else {
            Node node = path.getEndNode();
            if (node == null) {
                return false;
            } else {
                int i = node.x - target.getX();
                int j = node.y - target.getY();
                int k = node.z - target.getZ();
                return (double) (i * i + j * j + k * k) <= 3D;
            }
        }
    }

    public void spawnEffectsAtBlock(BlockPos blockPos) {
        float radius = 0.3F;
        for (int i1 = 0; i1 < 3; i1++) {
            double motionX = therizinosaurus.getRandom().nextGaussian() * 0.07D;
            double motionY = therizinosaurus.getRandom().nextGaussian() * 0.07D;
            double motionZ = therizinosaurus.getRandom().nextGaussian() * 0.07D;
            float angle = (float) ((0.0174532925 * therizinosaurus.yBodyRot) + i1);
            double extraX = radius * Mth.sin(Mth.PI + angle);
            double extraY = 0.8F;
            double extraZ = radius * Mth.cos(angle);
            BlockState state = therizinosaurus.level().getBlockState(blockPos);
            ((ServerLevel) therizinosaurus.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5 + extraX, blockPos.getY() + 0.5 + extraY, blockPos.getZ() + 0.5 + extraZ, 1, motionX, motionY, motionZ, 1);
        }
    }
}