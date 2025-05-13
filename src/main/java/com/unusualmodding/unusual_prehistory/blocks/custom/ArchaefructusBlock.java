package com.unusualmodding.unusual_prehistory.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ArchaefructusBlock extends BushBlock implements LiquidBlockContainer {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public ArchaefructusBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter block, BlockPos pos, CollisionContext collision) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter block, BlockPos pos) {
        return state.isFaceSturdy(block, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext block) {
        FluidState fluidstate = block.getLevel().getFluidState(block.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(block) : null;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
        BlockState blockstate = super.updateShape(state, direction, state1, level, pos, pos1);
        if (!blockstate.isAir()) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return blockstate;
    }

    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    public boolean canPlaceLiquid(BlockGetter block, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
