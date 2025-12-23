package com.barlinc.unusual_prehistory.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ConnectedGlassBlock extends AbstractGlassBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");

    public ConnectedGlassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, false).setValue(DOWN, false).setValue(EAST, false).setValue(WEST, false).setValue(NORTH, false).setValue(SOUTH, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos n = blockpos.north();
        BlockPos e = blockpos.east();
        BlockPos s = blockpos.south();
        BlockPos w = blockpos.west();
        BlockPos u = blockpos.above();
        BlockPos d = blockpos.below();
        BlockState northState = level.getBlockState(n);
        BlockState eastState = level.getBlockState(e);
        BlockState southState = level.getBlockState(s);
        BlockState westState = level.getBlockState(w);
        BlockState upState = level.getBlockState(u);
        BlockState downState = level.getBlockState(d);
        return defaultBlockState().setValue(NORTH, northState.is(this)).setValue(NORTH, northState.is(this)).setValue(EAST, eastState.is(this)).setValue(SOUTH, southState.is(this)).setValue(WEST, westState.is(this)).setValue(UP, upState.is(this)).setValue(DOWN, downState.is(this));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState state2, LevelAccessor level, BlockPos blockpos, @NotNull BlockPos pos2) {
        BlockPos n = blockpos.north();
        BlockPos e = blockpos.east();
        BlockPos s = blockpos.south();
        BlockPos w = blockpos.west();
        BlockPos u = blockpos.above();
        BlockPos d = blockpos.below();
        BlockState northState = level.getBlockState(n);
        BlockState eastState = level.getBlockState(e);
        BlockState southState = level.getBlockState(s);
        BlockState westState = level.getBlockState(w);
        BlockState upState = level.getBlockState(u);
        BlockState downState = level.getBlockState(d);
        return state.setValue(NORTH, northState.is(this)).setValue(NORTH, northState.is(this)).setValue(EAST, eastState.is(this)).setValue(SOUTH, southState.is(this)).setValue(WEST, westState.is(this)).setValue(UP, upState.is(this)).setValue(DOWN, downState.is(this));
    }
}