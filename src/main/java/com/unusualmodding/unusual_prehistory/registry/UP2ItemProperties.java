package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.items.MobCaptureItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class UP2ItemProperties {

    public static void registerItemProperties() {
        for (RegistryObject<Item> item : UP2Items.ITEMS.getEntries()) {
            if (item.get() instanceof MobCaptureItem) {
                registerVariantProperties(item.get());
            }
        }
    }

    private static void registerVariantProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation(UnusualPrehistory2.MOD_ID, "variant"), (stack, world, player, i) -> stack.hasTag() ? stack.getTag().getInt("Variant") : 0);
    }
}
