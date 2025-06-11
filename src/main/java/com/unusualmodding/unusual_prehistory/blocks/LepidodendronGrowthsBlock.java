package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class LepidodendronGrowthsBlock extends Block {

    public static final DirectionProperty FACING;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape UP_AABB;
    protected static final VoxelShape DOWN_AABB;

    public LepidodendronGrowthsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return state.getFluidState().isEmpty();
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos blockPos) {
        BlockState blockstate = level.getBlockState(blockPos.relative(state.getValue(FACING)));
        return blockstate.is(UP2BlockTags.LEPIDODENDRON_GROWTHS_PLACEABLES);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos blockPos, BlockPos neighborBlockPos) {
        return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, blockPos, neighborBlockPos);
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_AABB;
            case UP -> UP_AABB;
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
        };
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    static {
        FACING = BlockStateProperties.FACING;

        UP_AABB = Block.box(3, 0.0F, 3, 13, 3, 13);
        DOWN_AABB = Block.box(3, 13, 3, 13, 16.0F, 13);

        NORTH_AABB = Block.box(3, 3, 13, 13, 13, 16.0F);
        SOUTH_AABB = Block.box(3, 3, 0.0F, 13, 13, 3);

        EAST_AABB = Block.box(0.0F, 3, 3, 3, 13, 13);
        WEST_AABB = Block.box(13, 3, 3, 16.0F, 13, 13);
    }
}
