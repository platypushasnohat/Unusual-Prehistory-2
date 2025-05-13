package com.unusualmodding.unusual_prehistory.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UP2ItemTags {

    public static final TagKey<Item> GINKGO_LOGS = itemTag("ginkgo_logs");

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(UnusualPrehistory2.MOD_ID, name));
    }
}
