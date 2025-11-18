package com.unusualmodding.unusual_prehistory.entity.ai.goals;

import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class ZombieAttackEggGoal extends MoveToBlockGoal {

    private final TagKey<Block> blockToRemove;
    private final Mob removerMob;
    private int ticksSinceReachedGoal;

    public ZombieAttackEggGoal(PathfinderMob mob) {
        super(mob, 1.0D, 24, 3);
        this.blockToRemove = UP2BlockTags.ZOMBIE_EGG_TARGETS;
        this.removerMob = mob;
    }

    @Override
    public boolean canUse() {
        if (!ForgeEventFactory.getMobGriefingEvent(this.removerMob.level(), this.removerMob)) {
            return false;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else if (this.findNearestBlock()) {
            this.nextStartTick = reducedTickDelay(20);
            return true;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return false;
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.removerMob.fallDistance = 1.0F;
    }

    @Override
    public void start() {
        super.start();
        this.ticksSinceReachedGoal = 0;
    }

    public void playDestroyProgressSound(LevelAccessor level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + removerMob.getRandom().nextFloat() * 0.2F);
    }

    public void playBreakSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = this.removerMob.level();
        BlockPos blockpos = this.removerMob.blockPosition();
        BlockPos blockpos1 = this.getPosWithBlock(blockpos, level);
        RandomSource randomsource = this.removerMob.getRandom();
        if (this.isReachedTarget() && blockpos1 != null) {
            if (this.ticksSinceReachedGoal > 0) {
                Vec3 vec3 = this.removerMob.getDeltaMovement();
                this.removerMob.setDeltaMovement(vec3.x, 0.3D, vec3.z);
                if (!level.isClientSide) {
                    ((ServerLevel) level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.EGG)), (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.7D, (double)blockpos1.getZ() + 0.5D, 3, ((double)randomsource.nextFloat() - 0.5D) * 0.08D, ((double)randomsource.nextFloat() - 0.5D) * 0.08D, ((double)randomsource.nextFloat() - 0.5D) * 0.08D, (double)0.15F);
                }
            }

            if (this.ticksSinceReachedGoal % 2 == 0) {
                Vec3 vec31 = this.removerMob.getDeltaMovement();
                this.removerMob.setDeltaMovement(vec31.x, -0.3D, vec31.z);
                if (this.ticksSinceReachedGoal % 6 == 0) {
                    this.playDestroyProgressSound(level, this.blockPos);
                }
            }

            if (this.ticksSinceReachedGoal > 60) {
                level.removeBlock(blockpos1, false);
                if (!level.isClientSide) {
                    for(int i = 0; i < 20; ++i) {
                        double d3 = randomsource.nextGaussian() * 0.02D;
                        double d1 = randomsource.nextGaussian() * 0.02D;
                        double d2 = randomsource.nextGaussian() * 0.02D;
                        ((ServerLevel) level).sendParticles(ParticleTypes.POOF, (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY(), (double)blockpos1.getZ() + 0.5D, 1, d3, d1, d2, (double)0.15F);
                    }
                    this.playBreakSound(level, blockpos1);
                }
            }
            this.ticksSinceReachedGoal++;
        }
    }

    @Nullable
    private BlockPos getPosWithBlock(BlockPos pos, BlockGetter blockGetter) {
        if (blockGetter.getBlockState(pos).is(this.blockToRemove)) {
            return pos;
        } else {
            BlockPos[] ablockpos = new BlockPos[]{pos.below(), pos.west(), pos.east(), pos.north(), pos.south(), pos.below().below()};
            for (BlockPos blockpos : ablockpos) {
                if (blockGetter.getBlockState(blockpos).is(this.blockToRemove)) {
                    return blockpos;
                }
            }
            return null;
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        ChunkAccess chunkaccess = level.getChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()), ChunkStatus.FULL, false);
        if (chunkaccess == null) {
            return false;
        } else {
            if (!chunkaccess.getBlockState(pos).canEntityDestroy(level, pos, this.removerMob)) return false;
            return chunkaccess.getBlockState(pos).is(this.blockToRemove) && chunkaccess.getBlockState(pos.above()).isAir() && chunkaccess.getBlockState(pos.above(2)).isAir();
        }
    }

    @Override
    public double acceptedDistance() {
        return 1.14D;
    }
}
