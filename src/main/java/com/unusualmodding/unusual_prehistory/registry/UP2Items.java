package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2BoatType;
import com.unusualmodding.unusual_prehistory.items.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
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
    public static List<RegistryObject<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    // tab icon
    public static final RegistryObject<Item> UNUSUAL_PREHISTORY = registerItem("unusual_prehistory", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ORGANIC_OOZE = registerItem("organic_ooze", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHISEL = registerItem("chisel", () -> new ChiselItem(new Item.Properties().durability(128)));

    public static final RegistryObject<Item> MACHINE_PARTS = registerItem("machine_parts", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> GINKGO_FRUIT = registerItem("ginkgo_fruit", () -> new Item(registerFoodValue(UP2FoodValues.GINKGO_FRUIT)));

    // carnotaurus
    public static final RegistryObject<Item> CARNOTAURUS_SPAWN_EGG = registerSpawnEggItem("carnotaurus", UP2Entities.CARNOTAURUS, 0x8c2f27, 0x252b33);
    public static final RegistryObject<Item> FURY_FOSSIL = registerFossilItem("fury");

    // diplocaulus
    public static final RegistryObject<Item> DIPLOCAULUS_SPAWN_EGG = registerSpawnEggItem("diplocaulus", UP2Entities.DIPLOCAULUS, 0xe5721e, 0x292733);
    public static final RegistryObject<Item> DIPLOCAULUS_BUCKET = registerItemNoLang("diplocaulus_bucket", () -> new MobBucketItem(UP2Entities.DIPLOCAULUS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BOOMERANG_FOSSIL = registerFossilItem("boomerang");

    // dromaeosaurus
    public static final RegistryObject<Item> DROMAEOSAURUS_SPAWN_EGG = registerSpawnEggItem("dromaeosaurus", UP2Entities.DROMAEOSAURUS, 0xf9fa5e, 0xebc754);
    public static final RegistryObject<Item> DROMAEOSAURUS_EGG = registerItem("dromaeosaurus_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> RUNNER_FOSSIL = registerFossilItem("runner");

    // dunkleosteus
    public static final RegistryObject<Item> DUNKLEOSTEUS_SPAWN_EGG = registerSpawnEggItem("dunkleosteus", UP2Entities.DUNKLEOSTEUS, 0x417a69, 0x825147);
    public static final RegistryObject<Item> DUNKLEOSTEUS_BUCKET = registerItemNoLang("dunkleosteus_bucket", () -> new MobBucketItem(UP2Entities.DUNKLEOSTEUS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GUILLOTINE_FOSSIL = registerFossilItem("guillotine");

    // jawless fish
    public static final RegistryObject<Item> JAWLESS_FISH_SPAWN_EGG = registerSpawnEggItem("jawless_fish", UP2Entities.JAWLESS_FISH, 0x312e38, 0x917388);
    public static final RegistryObject<Item> JAWLESS_FISH_BUCKET = registerItemNoLang("jawless_fish_bucket", () -> new MobBucketItem(UP2Entities.JAWLESS_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JAWLESS_FOSSIL = registerFossilItem("jawless");

    // kentrosaurus
    public static final RegistryObject<Item> KENTROSAURUS_SPAWN_EGG = registerSpawnEggItem("kentrosaurus", UP2Entities.KENTROSAURUS, 0x5a6637, 0x1d1f18);
    public static final RegistryObject<Item> PRICKLY_FOSSIL = registerFossilItem("prickly");

    // kimmeridgebrachypteraeschnidium
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM, 0x74c70f, 0x8a3466);
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium_nymph", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, 0x233213, 0x0a1206);
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE = registerItemNoLang("kimmeridgebrachypteraeschnidium_bottle", () -> new MobCaptureItem(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM::get, Fluids.EMPTY, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET = registerItemNoLang("kimmeridgebrachypteraeschnidium_nymph_bucket", () -> new MobBucketItem(UP2Entities.JAWLESS_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IMPERATIVE_FOSSIL = registerFossilItem("imperative");

    // majungasaurus
    public static final RegistryObject<Item> MAJUNGASAURUS_SPAWN_EGG = registerSpawnEggItem("majungasaurus", UP2Entities.MAJUNGASAURUS, 0x5aa81e, 0x194e80);
    public static final RegistryObject<Item> RUGOSE_FOSSIL = registerFossilItem("rugose");

    // megalania
    public static final RegistryObject<Item> MEGALANIA_SPAWN_EGG = registerSpawnEggItem("megalania", UP2Entities.MEGALANIA, 0x4f432b, 0x0fd4e6);
    public static final RegistryObject<Item> THERMAL_FOSSIL = registerFossilItem("thermal");

    // stethacanthus
    public static final RegistryObject<Item> STETHACANTHUS_SPAWN_EGG = registerSpawnEggItem("stethacanthus", UP2Entities.STETHACANTHUS, 0x853028, 0xffc400);
    public static final RegistryObject<Item> STETHACANTHUS_BUCKET = registerItemNoLang("stethacanthus_bucket", () -> new MobBucketItem(UP2Entities.STETHACANTHUS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ANVIL_FOSSIL = registerFossilItem("anvil");

    // talpanas
    public static final RegistryObject<Item> TALPANAS_SPAWN_EGG = registerSpawnEggItem("talpanas", UP2Entities.TALPANAS, 0x30241a, 0xb2dee0);
    public static final RegistryObject<Item> TALPANAS_EGG = registerItem("talpanas_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> AGED_FEATHER = registerItem("aged_feather", () -> new Item(new Item.Properties()));

    // telecrex
    public static final RegistryObject<Item> TELECREX_SPAWN_EGG = registerSpawnEggItem("telecrex", UP2Entities.TELECREX, 0x121018, 0x770f38);
    public static final RegistryObject<Item> TELECREX_EGG = registerItem("telecrex_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> PLUMAGE_FOSSIL = registerFossilItem("plumage");

    // plant fossils
    public static final RegistryObject<Item> BARK_FOSSIL = registerFossilItem("bark");
    public static final RegistryObject<Item> BLOOMED_FOSSIL = registerFossilItem("bloomed");
    public static final RegistryObject<Item> FAN_FOSSIL = registerFossilItem("fan");
    public static final RegistryObject<Item> FIBROUS_FOSSIL = registerFossilItem("fibrous");
    public static final RegistryObject<Item> FLOWERING_FOSSIL = registerFossilItem("flowering");
    public static final RegistryObject<Item> LEAFY_FOSSIL = registerFossilItem("leafy");
    public static final RegistryObject<Item> SAPLING_FOSSIL = registerFossilItem("sapling");
    public static final RegistryObject<Item> SPINDLY_FOSSIL = registerFossilItem("spindly");
    public static final RegistryObject<Item> SPORE_FOSSIL = registerFossilItem("spore");
    public static final RegistryObject<Item> TRUNK_FOSSIL = registerFossilItem("trunk");
    public static final RegistryObject<Item> VASCULAR_FOSSIL = registerFossilItem("vascular");

    // wood
    public static final RegistryObject<Item> GINKGO_SIGN = registerItem("ginkgo_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.GINKGO_SIGN.get(), UP2Blocks.GINKGO_WALL_SIGN.get()));
    public static final RegistryObject<Item> GINKGO_HANGING_SIGN = registerItem("ginkgo_hanging_sign", () -> new HangingSignItem(UP2Blocks.GINKGO_HANGING_SIGN.get(), UP2Blocks.GINKGO_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> GINKGO_BOAT = registerItem("ginkgo_boat", () -> new UP2BoatItem(false, UP2BoatType.Type.GINKGO, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GINKGO_CHEST_BOAT = registerItemNoLang("ginkgo_chest_boat", () -> new UP2BoatItem(true, UP2BoatType.Type.GINKGO, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LEPIDODENDRON_SIGN = registerItem("lepidodendron_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.LEPIDODENDRON_SIGN.get(), UP2Blocks.LEPIDODENDRON_WALL_SIGN.get()));
    public static final RegistryObject<Item> LEPIDODENDRON_HANGING_SIGN = registerItem("lepidodendron_hanging_sign", () -> new HangingSignItem(UP2Blocks.LEPIDODENDRON_HANGING_SIGN.get(), UP2Blocks.LEPIDODENDRON_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> LEPIDODENDRON_BOAT = registerItem("lepidodendron_boat", () -> new UP2BoatItem(false, UP2BoatType.Type.LEPIDODENDRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEPIDODENDRON_CHEST_BOAT = registerItemNoLang("lepidodendron_chest_boat", () -> new UP2BoatItem(true, UP2BoatType.Type.LEPIDODENDRON, new Item.Properties().stacksTo(1)));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
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

    private static RegistryObject<Item> registerFossilItem(String name) {
        return registerItem(name + "_fossil", () -> new Item(new Item.Properties()));
    }

    public static Item.Properties registerFoodValue(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
