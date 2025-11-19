package com.barlinc.unusual_prehistory.registry;

import net.minecraft.resources.ResourceLocation;

import static com.barlinc.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2LootTables {

    public static final ResourceLocation PALEOZOIC_FOSSILS = create("archaeology/paleozoic_fossils");
    public static final ResourceLocation MESOZOIC_FOSSILS = create("archaeology/mesozoic_fossils");
    public static final ResourceLocation PLANT_FOSSILS = create("archaeology/plant_fossils");

    private static ResourceLocation create(String id) {
        return modPrefix(id);
    }
}
