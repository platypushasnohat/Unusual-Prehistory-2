package com.barlinc.unusual_prehistory.blocks.plant;

import com.barlinc.unusual_prehistory.blocks.entity.AmbientPlantBlockEntity;
import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class AmbientPlantBlock extends BaseEntityBlock implements IPlantable, BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public final Supplier<EntityType<?>> toSpawn;

    public AmbientPlantBlock(Properties properties, Supplier<EntityType<?>> toSpawn) {
        super(properties);
        this.toSpawn = toSpawn;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AmbientPlantBlockEntity(pos, state);
    }

    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
        return state.is(UP2BlockTags.ANCIENT_PLANT_PLACEABLE);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, facingState, level, pos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) {
            return level.getBlockState(blockpos).canSustainPlant(level, blockpos, Direction.UP, this);
        }
        return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return type == PathComputationType.AIR && !this.hasCollision || super.isPathfindable(state, blockGetter, pos, type);
    }

    @Override
    public BlockState getPlant(BlockGetter blockGetter, BlockPos pos) {
        BlockState state = blockGetter.getBlockState(pos);
        if (state.getBlock() != this) return defaultBlockState();
        return state;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, UP2BlockEntities.AMBIENT_PLANT.get(), AmbientPlantBlockEntity::tick);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        popResource(level, pos, new ItemStack(this));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
