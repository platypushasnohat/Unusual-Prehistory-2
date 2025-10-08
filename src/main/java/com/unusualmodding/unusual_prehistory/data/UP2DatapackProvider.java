package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.feature.*;
import com.unusualmodding.unusual_prehistory.registry.structures.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class UP2DatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, UP2BiomeModifiers::bootstrap)
//            .add(Registries.CONFIGURED_FEATURE, UP2ConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, UP2PlacedFeatures::bootstrap)
//            .add(Registries.STRUCTURE, UP2Structures::bootstrap)
//            .add(Registries.STRUCTURE_SET, UP2StructureSets::bootstrap)
//            .add(Registries.TEMPLATE_POOL, UP2TemplatePools::bootstrap)
            ;

    public UP2DatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of("minecraft", UnusualPrehistory2.MOD_ID));
    }
}
