package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.feature.CalamophytonFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2Features {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<CalamophytonFeature> CALAMOPHYTON = FEATURES.register("calamophyton", () -> new CalamophytonFeature(NoneFeatureConfiguration.CODEC));

    public static final ResourceKey<PlacedFeature> MOSS_LAYER_BONEMEAL = createPlacedFeatureKey("moss_layer_bonemeal");
    public static final ResourceKey<PlacedFeature> HORSETAIL_BONEMEAL = createPlacedFeatureKey("horsetail_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO = createConfiguredFeatureKey("ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO_BEES = createConfiguredFeatureKey("ginkgo_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GINKGO = createConfiguredFeatureKey("golden_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GINKGO_BEES = createConfiguredFeatureKey("golden_ginkgo_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEPIDODENDRON = createConfiguredFeatureKey("lepidodendron");

    private static ResourceKey<PlacedFeature> createPlacedFeatureKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, UnusualPrehistory2.modPrefix(name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, UnusualPrehistory2.modPrefix(name));
    }
}
