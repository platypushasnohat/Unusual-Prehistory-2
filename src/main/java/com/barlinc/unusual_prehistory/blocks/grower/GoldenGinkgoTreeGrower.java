package com.barlinc.unusual_prehistory.blocks.grower;

import com.barlinc.unusual_prehistory.registry.UP2Features;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GoldenGinkgoTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
        return bees ? UP2Features.GOLDEN_GINKGO_BEES : UP2Features.GOLDEN_GINKGO;
    }
}
