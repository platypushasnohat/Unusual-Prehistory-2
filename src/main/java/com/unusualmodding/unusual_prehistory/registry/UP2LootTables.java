package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;

public class UP2LootTables {

    public static final ResourceLocation DEEPSLATE_FOSSIL = create("archaeology/deepslate_fossil");
    public static final ResourceLocation FOSSIL = create("archaeology/fossil");

    private static ResourceLocation create(String id) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, id);
    }
}
