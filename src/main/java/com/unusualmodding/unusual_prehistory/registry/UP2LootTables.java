package com.unusualmodding.unusual_prehistory.registry;

import net.minecraft.resources.ResourceLocation;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2LootTables {

    public static final ResourceLocation DEEPSLATE_FOSSIL = create("archaeology/deepslate_fossil");
    public static final ResourceLocation FOSSIL = create("archaeology/fossil");
    public static final ResourceLocation PLANT_FOSSIL = create("archaeology/plant_fossil");

    private static ResourceLocation create(String id) {
        return modPrefix(id);
    }
}
