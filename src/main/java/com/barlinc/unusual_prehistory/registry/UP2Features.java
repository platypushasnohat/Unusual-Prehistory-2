package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.feature.CalamophytonFeature;
import com.barlinc.unusual_prehistory.worldgen.feature.StructureFeature;
import com.barlinc.unusual_prehistory.worldgen.feature.config.StructureFeatureConfig;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.TreeFromStructureFeature;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.config.TreeFromStructureConfig;
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
    public static final RegistryObject<Feature<StructureFeatureConfig>> STRUCTURE_FEATURE = FEATURES.register("structure_feature", () -> new StructureFeature(StructureFeatureConfig.CODEC));
    public static final RegistryObject<Feature<TreeFromStructureConfig>> TREE_STRUCTURE_FEATURE = FEATURES.register("tree", () -> new TreeFromStructureFeature(TreeFromStructureConfig.CODEC));

    public static final ResourceKey<PlacedFeature> MOSS_LAYER_BONEMEAL = createPlacedFeatureKey("moss_layer_bonemeal");
    public static final ResourceKey<PlacedFeature> PATCH_MOSSY_DIRT_PLANTS = createPlacedFeatureKey("patch_mossy_dirt_plants");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CYCAD = createConfiguredFeatureKey("cycad");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DRYOPHYLLUM = createConfiguredFeatureKey("dryophyllum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRYOPHYLLUM_BEES = createConfiguredFeatureKey("dryophyllum_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO = createConfiguredFeatureKey("ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO_BEES = createConfiguredFeatureKey("ginkgo_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GINKGO = createConfiguredFeatureKey("golden_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GINKGO_BEES = createConfiguredFeatureKey("golden_ginkgo_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEPIDODENDRON = createConfiguredFeatureKey("lepidodendron");
    public static final ResourceKey<ConfiguredFeature<?, ?>> METASEQUOIA = createConfiguredFeatureKey("metasequoia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_METASEQUOIA = createConfiguredFeatureKey("mega_metasequoia");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PROTOTAXITES = createConfiguredFeatureKey("prototaxites");

    private static ResourceKey<PlacedFeature> createPlacedFeatureKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, UnusualPrehistory2.modPrefix(name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, UnusualPrehistory2.modPrefix(name));
    }
}
