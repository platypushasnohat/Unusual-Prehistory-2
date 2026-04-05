package com.barlinc.unusual_prehistory.blocks.egg;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TallEggBlock extends EggBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private final VoxelShape upperShape;

    public TallEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int widthPx, int heightPx, int topWidthPx, int topHeightPx, boolean canTrample) {
        super(properties, hatchedEntity, widthPx, heightPx, canTrample);
        int px = (16 - topWidthPx) / 2;
        this.upperShape = Block.box(px, 0, px, 16 - px, topHeightPx, 16 - px);
        this.registerDefaultState((this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER).setValue(HATCH, 0));
    }

    @Override
    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (this.canHatch(level, pos)) {
                if (!this.isReadyToHatch(state)) {
                    level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                    level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(HATCH, this.getHatchLevel(level.getBlockState(pos.above())) + 1), 2);
                    level.setBlock(pos, state.setValue(HATCH, this.getHatchLevel(state) + 1), 2);
                } else {
                    this.spawnEntity(level, pos, state, random);
                }
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        if (blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext)) {
            return super.getStateForPlacement(blockPlaceContext);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, LivingEntity livingEntity, @NotNull ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        BlockPos blockPos2 = blockPos.above();
        level.setBlock(blockPos2, TallEggBlock.copyWaterloggedFrom(level, blockPos2, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)), 3);
    }

    @Override
    public boolean canSurvive(BlockState blockState, @NotNull LevelReader levelReader, @NotNull BlockPos blockPos) {
        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.below());
            return blockState2.is(this) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canSurvive(blockState, levelReader, blockPos);
    }

    public static BlockState copyWaterloggedFrom(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
            return blockState.setValue(BlockStateProperties.WATERLOGGED, levelReader.isWaterAt(blockPos));
        }
        return blockState;
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                TallEggBlock.preventCreativeDropFromBottomPart(level, blockPos, blockState, player);
            } else {
                TallEggBlock.dropResources(blockState, level, blockPos, null, player, player.getMainHandItem());
            }
        }
        return super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @Nullable BlockEntity blockEntity, @NotNull ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, Blocks.AIR.defaultBlockState(), blockEntity, itemStack);
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        BlockPos blockPos2;
        BlockState blockState2;
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState2 = level.getBlockState(blockPos2 = blockPos.below())).is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState3 = blockState2.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(blockPos2, blockState3, 35);
            level.levelEvent(player, 2001, blockPos2, Block.getId(blockState2));
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? shape : upperShape;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState blockState, Direction direction, @NotNull BlockState blockState2, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockPos blockPos2) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || blockState2.is(this) && blockState2.getValue(HALF) != doubleBlockHalf)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }
}
