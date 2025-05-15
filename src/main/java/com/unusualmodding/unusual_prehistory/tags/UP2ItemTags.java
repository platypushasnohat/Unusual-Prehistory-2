package com.unusualmodding.unusual_prehistory.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UP2ItemTags {

    // UP2 tags
    public static final TagKey<Item> DNA_BOTTLES = modItemTag("dna_bottles");
    public static final TagKey<Item> PALEOZOIC_DNA_BOTTLES = modItemTag("paleozoic_dna_bottles");
    public static final TagKey<Item> MESOZOIC_DNA_BOTTLES = modItemTag("mesozoic_dna_bottles");
    public static final TagKey<Item> CENOZOIC_DNA_BOTTLES = modItemTag("cenozoic_dna_bottles");
    public static final TagKey<Item> PLANT_DNA_BOTTLES = modItemTag("plant_dna_bottles");
    public static final TagKey<Item> WATER_PLANT_DNA_BOTTLES = modItemTag("aquatic_plant_dna_bottles");

    public static final TagKey<Item> GINKGO_LOGS = modItemTag("ginkgo_logs");

    public static final TagKey<Item> UNICORN_FOOD = modItemTag("unicorn_food");

    // UP2 forge tags
    public static final TagKey<Item> FRUITS = forgeItemTag("fruits");
    public static final TagKey<Item> FRUITS_GINKGO = forgeItemTag("fruits/ginkgo");
    public static final TagKey<Item> BOTTLES  = forgeItemTag("bottles");
    public static final TagKey<Item> BOTTLES_DNA  = forgeItemTag("bottles/dna");

    private static TagKey<Item> modItemTag(String name) {
        return itemTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Item> forgeItemTag(String name) {
        return itemTag("forge", name);
    }

    public static TagKey<Item> itemTag(String modid, String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(modid, name));
    }
}
