package com.unusualmodding.unusual_prehistory.blocks.custom;

import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class PetrifiedAnostylostromaBlock extends Block {
    private static final Direction[] ALL_DIRECTIONS = Direction.values();

    public PetrifiedAnostylostromaBlock(Properties properties) {
        super(properties);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            this.absorbWater(level, pos);
        }
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean isMoving) {
        this.absorbWater(level, pos);
        super.neighborChanged(state, level, pos, block, pos1, isMoving);
    }

    protected void absorbWater(Level level, BlockPos pos) {
        if (this.removeWaterBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, UP2Blocks.ANOSTYLOSTROMA.get().defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.WATER.defaultBlockState()));
        }
    }

    private boolean removeWaterBreadthFirstSearch(Level level, BlockPos pos) {
        BlockState anostylostromaState = level.getBlockState(pos);
        return BlockPos.breadthFirstTraversal(pos, 5, 65, (pos1, consumer) -> {
            for(Direction direction : ALL_DIRECTIONS) {
                consumer.accept(pos1.relative(direction));
            }
        },
        (blockPos) -> {
            if (blockPos.equals(pos)) {
                return true;
            } else {
                BlockState blockstate = level.getBlockState(blockPos);
                FluidState fluidstate = level.getFluidState(blockPos);
                if (!anostylostromaState.canBeHydrated(level, pos, fluidstate, blockPos)) {
                    return false;
                } else {
                    Block block = blockstate.getBlock();
                    if (block instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)block;
                        if (!bucketpickup.pickupBlock(level, blockPos, blockstate).isEmpty()) {
                            return true;
                        }
                    }
                    if (blockstate.getBlock() instanceof LiquidBlock) {
                        level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                    } else {
                        if (!blockstate.is(Blocks.KELP) && !blockstate.is(Blocks.KELP_PLANT) && !blockstate.is(Blocks.SEAGRASS) && !blockstate.is(Blocks.TALL_SEAGRASS)) {
                            return false;
                        }
                        BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(blockPos) : null;
                        dropResources(blockstate, level, blockPos, blockentity);
                        level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                    return true;
                }
            }
        }) > 1;
    }
}
