package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class UP2StructureTags {

    public static final TagKey<Structure> ON_PALEOZOIC_FOSSIL_MAPS = modStructureTag("on_paleozoic_fossil_maps");
    public static final TagKey<Structure> ON_MESOZOIC_FOSSIL_MAPS = modStructureTag("on_mesozoic_fossil_maps");
    public static final TagKey<Structure> ON_PETRIFIED_TREE_MAPS = modStructureTag("on_petrified_tree_maps");

    private static TagKey<Structure> modStructureTag(String name) {
        return structureTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Structure> forgeStructureTag(String name) {
        return structureTag("forge", name);
    }

    public static TagKey<Structure> structureTag(String modid, String name) {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(modid, name));
    }
}
