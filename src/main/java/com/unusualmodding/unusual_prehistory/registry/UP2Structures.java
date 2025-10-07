package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.worldgen.structure.FossilStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2Structures {
    //Example
    //public static final ResourceKey<Structure> KISMET_STRUCTURE = createKey("kismet_structure");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, modPrefix( name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        Map<MobCategory, StructureSpawnOverride> mobSpawnsBox = Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap((category) -> category, (category) -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create())));

        Map<MobCategory, StructureSpawnOverride> mobSpawnsPiece = Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap((category) -> category, (category) -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create())));

        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placements = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredFeature<?, ?>> configurations = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);


        HolderGetter<StructureTemplatePool> structureTemplatePoolHolder = context.lookup(Registries.TEMPLATE_POOL);
        //Example
       // Holder<StructureTemplatePool> poolKS =   structureTemplatePoolHolder.getOrThrow(KPools.KISMET_STRUCTURE);

        //Example
        //context.register(FOSSIL_STRUCTURE, new FossilStructure(
        //        new Structure.StructureSettings(
        //                biomes.getOrThrow(Tags.Biomes.IS_UNDERGROUND),
        //                mobSpawnsBox,
        //                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
        //                TerrainAdjustment.BURY
        //        )
        //));
    }

}
