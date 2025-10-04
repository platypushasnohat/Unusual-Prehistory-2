package com.unusualmodding.unusual_prehistory.registry.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class UP2BiomeTags {

    public static final TagKey<Biome> HAS_FOSSILS = modBiomeTag("has_structure/fossils");

    private static TagKey<Biome> modBiomeTag(String name) {
        return biomeTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Biome> forgeBiomeTag(String name) {
        return biomeTag("forge", name);
    }

    public static TagKey<Biome> biomeTag(String modid, String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(modid, name));
    }
}
