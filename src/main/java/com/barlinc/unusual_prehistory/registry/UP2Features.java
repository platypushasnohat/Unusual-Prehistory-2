package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.feature.StructureFeature;
import com.barlinc.unusual_prehistory.worldgen.feature.config.StructureFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2Features {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<StructureFeatureConfig>> STRUCTURE_FEATURE = FEATURES.register("structure_feature", () -> new StructureFeature(StructureFeatureConfig.CODEC));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CYCAD = createConfiguredFeatureKey("cycad");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DRYOPHYLLUM = createConfiguredFeatureKey("dryophyllum");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO = createConfiguredFeatureKey("ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GINKGO = createConfiguredFeatureKey("golden_ginkgo");
    public static final ResourceKey<PlacedFeature> MOSSY_DIRT_PLANTS = createPlacedFeatureKey("mossy_dirt_plants");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LEPIDODENDRON = createConfiguredFeatureKey("lepidodendron");
    public static final ResourceKey<PlacedFeature> LIVING_PEAT_PLANTS = createPlacedFeatureKey("living_peat_plants");

    public static final ResourceKey<ConfiguredFeature<?, ?>> METASEQUOIA = createConfiguredFeatureKey("metasequoia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_METASEQUOIA = createConfiguredFeatureKey("mega_metasequoia");
    public static final ResourceKey<PlacedFeature> NEEDLE_LITTER_PLANTS = createPlacedFeatureKey("needle_litter_plants");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PROTOTAXITES = createConfiguredFeatureKey("prototaxites");

    private static ResourceKey<PlacedFeature> createPlacedFeatureKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, UnusualPrehistory2.modPrefix(name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, UnusualPrehistory2.modPrefix(name));
    }
}
