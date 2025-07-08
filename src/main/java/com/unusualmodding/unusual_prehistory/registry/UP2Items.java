package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.items.MobCaptureItem;
import com.unusualmodding.unusual_prehistory.items.FrozenMeatItem;
import com.unusualmodding.unusual_prehistory.items.ThrowableEggItem;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UP2Items {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnusualPrehistory2.MOD_ID);
    public static List<RegistryObject<? extends Item>> AUTO_TRANSLATE = new ArrayList<>();

    // fossils
    public static final RegistryObject<Item> PLANT_FOSSIL = registerItem("plant_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PALEOZOIC_FOSSIL = registerItem("paleozoic_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MESOZOIC_FOSSIL = registerItem("mesozoic_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FROZEN_MEAT = registerItem("frozen_meat", () -> new FrozenMeatItem(registerFoodValue(UP2FoodValues.FROZEN_MEAT)));

    public static final RegistryObject<Item> ORGANIC_OOZE = registerItem("organic_ooze", () -> new Item(new Item.Properties()));

    // foods
    public static final RegistryObject<Item> GINKGO_FRUIT = registerItem("ginkgo_fruit", () -> new Item(registerFoodValue(UP2FoodValues.GINKGO_FRUIT)));

    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE = registerItemNoLang("kimmeridgebrachypteraeschnidium_bottle", () -> new MobCaptureItem(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM::get, Fluids.EMPTY, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JAWLESS_FISH_BUCKET = registerItemNoLang("jawless_fish_bucket", () -> new MobBucketItem(UP2Entities.JAWLESS_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCAUMENACIA_BUCKET = registerItemNoLang("scaumenacia_bucket", () -> new MobBucketItem(UP2Entities.SCAUMENACIA, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STETHACANTHUS_BUCKET = registerItemNoLang("stethacanthus_bucket", () -> new MobBucketItem(UP2Entities.STETHACANTHUS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    // paleo dna
    public static final RegistryObject<Item> DIPLOCAULUS_DNA = registerDnaItem("diplocaulus");
    public static final RegistryObject<Item> DUNKLEOSTEUS_DNA = registerDnaItem("dunkleosteus");
    public static final RegistryObject<Item> JAWLESS_FISH_DNA = registerDnaItem("jawless_fish");
    public static final RegistryObject<Item> SCAUMENACIA_DNA = registerDnaItem("scaumenacia");
    public static final RegistryObject<Item> STETHACANTHUS_DNA = registerDnaItem("stethacanthus");

    // meso dna
    public static final RegistryObject<Item> CARNOTAURUS_DNA = registerDnaItem("carnotaurus");
    public static final RegistryObject<Item> KENTROSAURUS_DNA = registerDnaItem("kentrosaurus");
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA = registerDnaItem("kimmeridgebrachypteraeschnidium");
    public static final RegistryObject<Item> MAJUNGASAURUS_DNA = registerDnaItem("majungasaurus");

    // ceno dna
    public static final RegistryObject<Item> MEGALANIA_DNA = registerDnaItem("megalania");
    public static final RegistryObject<Item> TELECREX_DNA = registerDnaItem("telecrex");

    // plant dna
    public static final RegistryObject<Item> ANOSTYLOSTROMA_DNA = registerDnaItem("anostylostroma");
    public static final RegistryObject<Item> ARCHAEFRUCTUS_DNA = registerDnaItem("archaefructus");
    public static final RegistryObject<Item> ARCHAEOSIGILLARIA_DNA = registerDnaItem("archaeosigillaria");
    public static final RegistryObject<Item> BENNETTITALES_DNA = registerDnaItem("bennettitales");
    public static final RegistryObject<Item> CALAMOPHYTON_DNA = registerDnaItem("calamophyton");
    public static final RegistryObject<Item> CLADOPHLEBIS_DNA = registerDnaItem("cladophlebis");
    public static final RegistryObject<Item> CLATHRODICTYON_CORAL_DNA = registerDnaItem("clathrodictyon_coral");
    public static final RegistryObject<Item> COOKSONIA_DNA = registerDnaItem("cooksonia");
    public static final RegistryObject<Item> GINKGO_DNA = registerDnaItem("ginkgo");
    public static final RegistryObject<Item> HORSETAIL_DNA = registerDnaItem("horsetail");
    public static final RegistryObject<Item> ISOETES_DNA = registerDnaItem("isoetes");
    public static final RegistryObject<Item> LEEFRUCTUS_DNA = registerDnaItem("leefructus");
    public static final RegistryObject<Item> LEPIDODENDRON_DNA = registerDnaItem("lepidodendron");
    public static final RegistryObject<Item> NELUMBITES_DNA = registerDnaItem("nelumbites");
    public static final RegistryObject<Item> QUEREUXIA_DNA = registerDnaItem("quereuxia");
    public static final RegistryObject<Item> RAIGUENRAYUN_DNA = registerDnaItem("raiguenrayun");
    public static final RegistryObject<Item> RHYNIA_DNA = registerDnaItem("rhynia");
    public static final RegistryObject<Item> SARRACENIA_DNA = registerDnaItem("sarracenia");

    public static final RegistryObject<Item> TALPANAS_EGG = registerItem("talpanas_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TELECREX_EGG = registerItem("telecrex_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16)));

    // block items
    public static final RegistryObject<Item> DEAD_CLATHRODICTYON_CORAL_FAN = registerItemNoLang("dead_clathrodictyon_coral_fan", () -> new StandingAndWallBlockItem(UP2Blocks.DEAD_CLATHRODICTYON_CORAL_FAN.get(), UP2Blocks.DEAD_CLATHRODICTYON_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> CLATHRODICTYON_CORAL_FAN = registerItemNoLang("clathrodictyon_coral_fan", () -> new StandingAndWallBlockItem(UP2Blocks.CLATHRODICTYON_CORAL_FAN.get(), UP2Blocks.CLATHRODICTYON_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));

    // spawn eggs
    public static final RegistryObject<Item> DIPLOCAULUS_SPAWN_EGG = registerSpawnEggItem("diplocaulus", UP2Entities.DIPLOCAULUS, 0xe5721e, 0x292733);
    public static final RegistryObject<Item> DUNKLEOSTEUS_SPAWN_EGG = registerSpawnEggItem("dunkleosteus", UP2Entities.DUNKLEOSTEUS, 0x417a69, 0x825147);
    public static final RegistryObject<Item> JAWLESS_FISH_SPAWN_EGG = registerSpawnEggItem("jawless_fish", UP2Entities.JAWLESS_FISH, 0x312e38, 0x917388);
    public static final RegistryObject<Item> KENTROSAURUS_SPAWN_EGG = registerSpawnEggItem("kentrosaurus", UP2Entities.KENTROSAURUS, 0x5a6637, 0x1d1f18);
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM, 0x74c70f, 0x8a3466);
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium_nymph", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, 0x233213, 0x0a1206);
    public static final RegistryObject<Item> MAJUNGUASAURUS_SPAWN_EGG = registerSpawnEggItem("majungasaurus", UP2Entities.MAJUNGASAURUS, 0x5aa81e, 0x194e80);
    public static final RegistryObject<Item> MEGALANIA_SPAWN_EGG = registerSpawnEggItem("megalania", UP2Entities.MEGALANIA, 0x4f432b, 0x0fd4e6);
    public static final RegistryObject<Item> SCAUMENACIA_SPAWN_EGG = registerSpawnEggItem("scaumenacia", UP2Entities.SCAUMENACIA, 0x687076, 0x376f97);
    public static final RegistryObject<Item> STETHACANTHUS_SPAWN_EGG = registerSpawnEggItem("stethacanthus", UP2Entities.STETHACANTHUS, 0x853028, 0xffc400);
    public static final RegistryObject<Item> TALPANAS_SPAWN_EGG = registerSpawnEggItem("talpanas", UP2Entities.TALPANAS, 0x30241a, 0xb2dee0);
    public static final RegistryObject<Item> TELECREX_SPAWN_EGG = registerSpawnEggItem("telecrex", UP2Entities.TELECREX, 0x121018, 0x770f38);

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        AUTO_TRANSLATE.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        return item;
    }

    private static RegistryObject<Item> registerSpawnEggItem(String name, RegistryObject type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    private static RegistryObject<Item> registerDnaItem(String name) {
        return registerItemNoLang(name + "_dna_bottle", () -> new Item(new Item.Properties()));
    }

    public static Item.Properties registerFoodValue(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
