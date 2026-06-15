package com.barlinc.unusual_prehistory.worldgen.feature;

import com.barlinc.unusual_prehistory.worldgen.feature.config.FossilPileConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FossilPileFeature extends Feature<FossilPileConfig> {

    public FossilPileFeature(Codec<FossilPileConfig> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<FossilPileConfig> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        FossilPileConfig config;
        for (config = context.config(); blockPos.getY() > level.getMinBuildHeight() + 3; blockPos = blockPos.below()) {
            if (!level.isEmptyBlock(blockPos.below())) {
                BlockState blockstate = level.getBlockState(blockPos.below());
                if (isDirt(blockstate) || isStone(blockstate)) {
                    break;
                }
            }
        }

        if (blockPos.getY() <= level.getMinBuildHeight() + 3) {
            return false;
        } else {
            int x = random.nextInt(2, 3);
            int y = random.nextInt(1, 2);
            int z = random.nextInt(2, 3);
            float f = (float) (x + y + z) * 0.333F + 0.5F;

            for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-x, -y, -z), blockPos.offset(x, y, z))) {
                if (pos.distSqr(blockPos) <= (double)(f * f)) {
                    level.setBlock(pos, config.state(), 3);
                }
            }

            for (int dx = -x - 1; dx <= x + 1; dx++) {
                for (int dz = -z - 1; dz <= z + 1; dz++) {
                    double dist = Math.sqrt(dx * dx + dz * dz);
                    float floorRadius = (Math.max(x, z) + 1.5F) + (random.nextFloat() - 0.5F) * 2.0F;
                    if (dist <= floorRadius) {
                        BlockPos groundPos = blockPos.offset(dx, -1, dz);
                        BlockState state = level.getBlockState(groundPos);

                        if (isDirt(state)) {
                            level.setBlock(groundPos, config.floorState(), 3);
                        }
                    }
                }
            }
            config.feature().value().place(level, context.chunkGenerator(), random, blockPos.above());

            return true;
        }
    }
}