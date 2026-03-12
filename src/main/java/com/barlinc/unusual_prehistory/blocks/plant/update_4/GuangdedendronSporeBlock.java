package com.barlinc.unusual_prehistory.blocks.plant.update_4;

import com.barlinc.unusual_prehistory.blocks.plant.PrehistoricPlantBlock;
import com.barlinc.unusual_prehistory.registry.UP2Features;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class GuangdedendronSporeBlock extends PrehistoricPlantBlock implements BonemealableBlock {

    public static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 11, 10);

    public GuangdedendronSporeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return random.nextFloat() < 0.4F;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        this.growGuangdedendron(level, pos, state, random);
    }

    public boolean growGuangdedendron(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(UP2Features.GUANGDEDENDRON);
        if (optional.isEmpty()) {
            return false;
        } else {
            var event = ForgeEventFactory.blockGrowFeature(level, random, pos, optional.get());
            if (event.getResult().equals(Event.Result.DENY)) return false;
            level.removeBlock(pos, false);
            if (event.getFeature().value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
                return true;
            } else {
                level.setBlock(pos, state, 3);
                return false;
            }
        }
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
