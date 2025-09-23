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
                ItemProperties.register(item.get(), new ResourceLocation(UnusualPrehistory2.MOD_ID, "variant"), (stack, level, living, i) -> stack.hasTag() ? stack.getTag().getInt("Variant") : 0);
            }
            ItemProperties.register(UP2Items.ORGANIC_OOZE.get(), new ResourceLocation("ooze_shape"), (stack, level, living, j) -> (stack.getCount() % 5) / 5F);

            ItemProperties.register(UP2Items.CHISEL.get(), new ResourceLocation("chiseling"), (stack, level, living, j) -> living != null && living.getUseItem() == stack ? (float)(living.getUseItemRemainingTicks() % 10) / 10.0F : 0.0F);
        }
    }
}
