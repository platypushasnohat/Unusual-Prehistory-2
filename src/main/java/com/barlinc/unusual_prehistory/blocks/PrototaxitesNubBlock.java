package com.barlinc.unusual_prehistory.blocks;

import com.barlinc.unusual_prehistory.registry.UP2Features;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class PrototaxitesNubBlock extends BushBlock implements BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public PrototaxitesNubBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextInt(25) == 0) {
            int i = 5;

            for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
                if (level.getBlockState(blockpos).is(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for(int k = 0; k < 4; ++k) {
                if (level.isEmptyBlock(blockpos1) && state.canSurvive(level, blockpos1)) {
                    pos = blockpos1;
                }
                blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (level.isEmptyBlock(blockpos1) && state.canSurvive(level, blockpos1)) {
                level.setBlock(blockpos1, state, 2);
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.isSolidRender(level, pos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        } else {
            return blockstate.canSustainPlant(level, blockpos, Direction.UP, this);
        }
    }

    public boolean growPrototaxites(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(UP2Features.GOLDEN_GINKGO);
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
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return (double) random.nextFloat() < 0.4D;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        this.growPrototaxites(level, pos, state, random);
    }
}
