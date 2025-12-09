package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UP2ItemTags {

    // UP2 tags
    public static final TagKey<Item> FOSSILS = modItemTag("fossils");

    public static final TagKey<Item> PACIFIES_MOB = modItemTag("pacifies_mob");
    public static final TagKey<Item> PACIFIES_CARNOTAURUS = modItemTag("pacifies_carnotaurus");
    public static final TagKey<Item> PACIFIES_DROMAEOSAURUS = modItemTag("pacifies_dromaeosaurus");
    public static final TagKey<Item> PACIFIES_DUNKLEOSTEUS = modItemTag("pacifies_dunkleosteus");
    public static final TagKey<Item> PACIFIES_MAJUNGASAURUS = modItemTag("pacifies_majungasaurus");
    public static final TagKey<Item> PACIFIES_MEGALANIA = modItemTag("pacifies_megalania");
    public static final TagKey<Item> PACIFIES_METRIORHYNCHUS = modItemTag("pacifies_metriorhynchus");
    public static final TagKey<Item> PACIFIES_ONCHOPRISTIS = modItemTag("pacifies_onchopristis");
    public static final TagKey<Item> PACIFIES_STETHACANTHUS = modItemTag("pacifies_stethacanthus");

    public static final TagKey<Item> CARNOTAURUS_FOOD = modItemTag("carnotaurus_food");
    public static final TagKey<Item> DIPLOCAULUS_FOOD = modItemTag("diplocaulus_food");
    public static final TagKey<Item> DROMAEOSAURUS_FOOD = modItemTag("dromaeosaurus_food");
    public static final TagKey<Item> DUNKLEOSTEUS_FOOD = modItemTag("dunkleosteus_food");
    public static final TagKey<Item> JAWLESS_FISH_FOOD = modItemTag("jawless_fish_food");
    public static final TagKey<Item> KENTROSAURUS_FOOD = modItemTag("kentrosaurus_food");
    public static final TagKey<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD = modItemTag("kimmeridgebrachypteraeschnidium_food");
    public static final TagKey<Item> LYSTROSAURUS_FOOD = modItemTag("lystrosaurus_food");
    public static final TagKey<Item> MAJUNGASAURUS_FOOD = modItemTag("majungasaurus_food");
    public static final TagKey<Item> MEGALANIA_FOOD = modItemTag("megalania_food");
    public static final TagKey<Item> METRIORHYNCHUS_FOOD = modItemTag("metriorhynchus_food");
    public static final TagKey<Item> ONCHOPRISTIS_FOOD = modItemTag("onchopristis_food");
    public static final TagKey<Item> STETHACANTHUS_FOOD = modItemTag("stethacanthus_food");
    public static final TagKey<Item> TALPANAS_FOOD = modItemTag("talpanas_food");
    public static final TagKey<Item> TARTUOSTEUS_FOOD = modItemTag("tartuosteus_food");
    public static final TagKey<Item> TELECREX_FOOD = modItemTag("telecrex_food");
    public static final TagKey<Item> UNICORN_FOOD = modItemTag("unicorn_food");

    public static final TagKey<Item> TRANSMOGRIFIER_FUEL = modItemTag("transmogrifier_fuel");

    public static final TagKey<Item> GINKGO_LOGS = modItemTag("ginkgo_logs");
    public static final TagKey<Item> LEPIDODENDRON_LOGS = modItemTag("lepidodendron_logs");

    public static final TagKey<Item> LIVING_OOZE_CANNOT_ABSORB = modItemTag("living_ooze_cannot_absorb");

    // Forge tags
    public static final TagKey<Item> FRUITS = forgeItemTag("fruits");
    public static final TagKey<Item> FRUITS_GINKGO = forgeItemTag("fruits/ginkgo");

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
