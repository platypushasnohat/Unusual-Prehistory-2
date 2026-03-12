package com.barlinc.unusual_prehistory.blocks.plant.update_4;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class GuangdedendronCrownBlock extends Block implements BonemealableBlock {

    public static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 16, 10);

    public GuangdedendronCrownBlock(Properties properties) {
        super(properties);
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
        popResource(level, pos, new ItemStack(UP2Blocks.GUANGDEDENDRON_SPORE.get()));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }

    @Override
    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos) {
        return true;
    }
}
