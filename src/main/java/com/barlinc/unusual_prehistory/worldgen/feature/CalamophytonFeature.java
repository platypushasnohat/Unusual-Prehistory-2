package com.barlinc.unusual_prehistory.worldgen.feature;

import com.barlinc.unusual_prehistory.blocks.CalamophytonBlock;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CalamophytonFeature extends Feature<NoneFeatureConfiguration> {

    public CalamophytonFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        int i = 0;
        for (int j = 0; j < 6; j++) {
            int k = random.nextInt(8) - random.nextInt(8);
            int l = random.nextInt(8) - random.nextInt(8);
            int i1 = level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX() + k, pos.getZ() + l);
            BlockPos blockpos = new BlockPos(pos.getX() + k, i1, pos.getZ() + l);
            if (this.canPlace(level, pos)) {
                BlockState blockstate = UP2Blocks.CALAMOPHYTON.get().defaultBlockState();
                if (blockstate.canSurvive(level, blockpos)) {
                    level.setBlock(blockpos, UP2Blocks.CALAMOPHYTON.get().defaultBlockState().setValue(CalamophytonBlock.LAYER, 0), 2);
                    level.setBlock(blockpos.above(), UP2Blocks.CALAMOPHYTON.get().defaultBlockState().setValue(CalamophytonBlock.LAYER, 1), 2);
                    level.setBlock(blockpos.above(2), UP2Blocks.CALAMOPHYTON.get().defaultBlockState().setValue(CalamophytonBlock.LAYER, 2), 2);
                }
                i++;
            }
        }
        return i > 0;
    }

    private boolean canPlace(WorldGenLevel level, BlockPos pos) {
        return this.isOpenSpaceAbove(level, pos) && level.getBlockState(pos.below()).is(UP2BlockTags.ANCIENT_PLANT_PLACEABLES);
    }

    private boolean isOpenSpaceAbove(WorldGenLevel level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == Blocks.AIR && level.getBlockState(pos.above()).getBlock() == Blocks.AIR && level.getBlockState(pos.above(2)).getBlock() == Blocks.AIR;
    }
}