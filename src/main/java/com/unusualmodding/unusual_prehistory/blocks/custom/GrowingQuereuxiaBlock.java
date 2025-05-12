package com.unusualmodding.unusual_prehistory.blocks.custom;

import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingQuereuxiaBlock extends GrowingPlantHeadBlock {

    private final double growPerTickProbability;

    protected GrowingQuereuxiaBlock(Properties properties, Direction direction, VoxelShape shape, boolean fluidTicks, double growPerTickProbability) {
        super(properties, direction, shape, fluidTicks, growPerTickProbability);
        this.growPerTickProbability = growPerTickProbability;

    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        if (state.getValue(AGE) < 25 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, pos.relative(this.growthDirection), serverLevel.getBlockState(pos.relative(this.growthDirection)), randomSource.nextDouble() < this.growPerTickProbability)) {
            if (this.canGrowInto(serverLevel.getBlockState(blockpos))) {
                if (serverLevel.getBlockState(blockpos.above()).is(Blocks.AIR) || serverLevel.getBlockState(blockpos.above()).is(UP2Blocks.QUEREUXIA_CLOVERS.get())) {
                    serverLevel.setBlockAndUpdate(blockpos.above(), UP2Blocks.QUEREUXIA_CLOVERS.get().defaultBlockState());
                    serverLevel.setBlockAndUpdate(blockpos, UP2Blocks.QUEREUXIA_PLANT.get().defaultBlockState());
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, blockpos, serverLevel.getBlockState(blockpos));
                    return;
                }
                serverLevel.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, serverLevel.random));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, blockpos, serverLevel.getBlockState(blockpos));
            }
        }
        if (serverLevel.getBlockState(blockpos).is(UP2Blocks.QUEREUXIA_CLOVERS.get())) {
            serverLevel.setBlockAndUpdate(blockpos.below(), UP2Blocks.QUEREUXIA_PLANT.get().defaultBlockState());
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos blockpos, RandomSource randomSource) {
        if (serverLevel.getBlockState(blockpos.above()).is(UP2Blocks.QUEREUXIA_CLOVERS.get())) {
            serverLevel.setBlockAndUpdate(blockpos, UP2Blocks.QUEREUXIA_PLANT.get().defaultBlockState());
        }
        super.tick(state, serverLevel, blockpos, randomSource);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        if (this.canGrowInto(serverLevel.getBlockState(blockpos))) {
            if (serverLevel.getBlockState(blockpos.above()).is(Blocks.AIR) || serverLevel.getBlockState(blockpos.above()).is(UP2Blocks.QUEREUXIA_CLOVERS.get())) {
                serverLevel.setBlockAndUpdate(blockpos, UP2Blocks.QUEREUXIA_PLANT.get().defaultBlockState());
                serverLevel.setBlockAndUpdate(blockpos.above(), UP2Blocks.QUEREUXIA_CLOVERS.get().defaultBlockState());
                serverLevel.scheduleTick(blockpos, this, 1);
                return;
            }
            serverLevel.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, serverLevel.random));
            serverLevel.scheduleTick(blockpos, this, 1);
        }
    }
}

