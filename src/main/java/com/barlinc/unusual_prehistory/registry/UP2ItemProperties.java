package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.items.PterodactylusPotItem;
import com.barlinc.unusual_prehistory.items.UP2MobBucketItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class UP2ItemProperties {

    public static void registerItemProperties() {
        for (DeferredHolder<Item, ? extends Item> item : UP2Items.ITEMS.getEntries()) {
            if (item.get() instanceof UP2MobBucketItem) {
                ItemProperties.register(item.get(), UnusualPrehistory2.modPrefix("variant"), (stack, level, living, i) -> stack.hasTag() ? stack.getTag().getInt("BucketVariantTag") : 0);
            }
            if (item.get() instanceof PterodactylusPotItem) {
                ItemProperties.register(item.get(), UnusualPrehistory2.modPrefix("variant"), (stack, level, living, i) -> stack.hasTag() ? stack.getTag().getInt("BucketVariantTag") : 0);
            }
            ItemProperties.register(UP2Items.ORGANIC_OOZE.get(), UnusualPrehistory2.modPrefix("ooze_shape"), (stack, level, living, j) -> (stack.getCount() % 5) / 5F);
        }
    }
}
