package com.unusualmodding.unusual_prehistory.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2StructureProcessorLists {

    public static final ResourceKey<StructureProcessorList> FOSSIL = registerKey("fossil");

    private static ResourceKey<StructureProcessorList> registerKey(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, modPrefix(name));
    }
}
