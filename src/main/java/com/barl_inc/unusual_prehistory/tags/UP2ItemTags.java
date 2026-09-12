package com.barl_inc.unusual_prehistory.tags;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UP2ItemTags {

    public static final TagKey<Item> TRANSMOGRIFIER_FUEL = modItemTag("transmogrifier_fuel");

    private static TagKey<Item> modItemTag(String name) {
        return itemTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Item> commonItemTag(String name) {
        return itemTag("c", name);
    }

    public static TagKey<Item> itemTag(String modId, String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
    }
}
