package com.unusualmodding.unusual_prehistory.worldgen.structure.rule_test;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.registry.UP2RuleTests;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Optional;

public class DimensionRuleTest extends ExtendedRuleTest {
    public static final Codec<DimensionRuleTest> CODEC =
            ResourceKey.codec(Registries.DIMENSION).xmap(DimensionRuleTest::new, t -> t.dimension);

    private final ResourceKey<Level> dimension;

    private transient ResourceKey<DimensionType> cachedTargetDimTypeKey;

    public DimensionRuleTest(ResourceKey<Level> dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        return true;
    }

    @Override
    protected RuleTestType<?> getType() {
        return UP2RuleTests.DIMENSION_RULE_TEST.get();
    }

    @Override
    public boolean extendedTest(BlockPos pos, BlockState state, LevelReader levelReader, RandomSource random) {
        if (levelReader == null) return false;


        // Resolve & cache the target dimension's DimensionType key from registries
        final ResourceKey<DimensionType> targetTypeKey = getTargetDimensionTypeKey(levelReader);
        if (targetTypeKey == null) return false;

        // Resolve the *current world's* DimensionType key
        return targetTypeKey.location().getNamespace().equals(dimension.location().getNamespace()) ;
    }


    /** Gets (and caches) the ResourceKey of the target dimension's DimensionType. */
    private ResourceKey<DimensionType> getTargetDimensionTypeKey(LevelReader levelReader) {
        if (cachedTargetDimTypeKey != null) return cachedTargetDimTypeKey;
        // Each LevelStem carries a Holder<DimensionType>; unwrap its key for stable comparison.
        final Optional<ResourceKey<DimensionType>> keyOpt = levelReader.registryAccess().registry(Registries.DIMENSION_TYPE).flatMap(registry -> registry.getResourceKey(levelReader.dimensionType()));
        cachedTargetDimTypeKey = keyOpt.orElse(null);
        return cachedTargetDimTypeKey;
    }
}