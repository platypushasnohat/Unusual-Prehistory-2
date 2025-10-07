package com.unusualmodding.unusual_prehistory.registry;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;
import static net.minecraft.Util.prefix;

public class UP2PlacedFeatures {
    public static final ResourceKey<PlacedFeature> FOSSIL_PLACED = registerKey("fossil_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> holder11 = configuredFeatures.getOrThrow(UP2ConfiguredFeatures.FOSSIL);
        Holder<ConfiguredFeature<?, ?>> fossil = configuredFeatures.getOrThrow(UP2ConfiguredFeatures.FOSSIL);
        register(context,
                UP2PlacedFeatures.FOSSIL_PLACED,
                fossil,
                ImmutableList.of(
                        RarityFilter.onAverageOnceEvery(500),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(92)),
                        BiomeFilter.biome()
                )
        );
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, modPrefix( name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
