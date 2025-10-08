package com.unusualmodding.unusual_prehistory.registry.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.unusualmodding.unusual_prehistory.registry.UP2Features;
import com.unusualmodding.unusual_prehistory.worldgen.feature.config.NbtFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2ConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOSSIL = registerKey("fossil");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {


        register(context, FOSSIL, UP2Features.NBT_FEATURE.get(), new NbtFeatureConfig(
                modPrefix("fossil/replace_fossil"),
                new ResourceLocation("minecraft:empty"),
                ImmutableList.of(Pair.of(modPrefix("fossil/spine_0"), 20),
                        Pair.of(modPrefix("fossil/spine_0"), 20),
                        Pair.of(modPrefix("fossil/spine_1"), 20),
                        Pair.of(modPrefix("fossil/spine_2"), 20),
                        Pair.of(modPrefix("fossil/spine_3"), 20),
                        Pair.of(modPrefix("fossil/skull_0"), 20),
                        Pair.of(modPrefix("fossil/skull_1"), 20),
                        Pair.of(modPrefix("fossil/skull_2"), 20),
                        Pair.of(modPrefix("fossil/skull_3"), 20),
                        Pair.of(modPrefix("fossil/fury"), 20)),
                0
        ));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, modPrefix(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
