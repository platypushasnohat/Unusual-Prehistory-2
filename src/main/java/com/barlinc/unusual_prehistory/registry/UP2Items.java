package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.Diplocaulus;
import com.barlinc.unusual_prehistory.entity.JawlessFish;
import com.barlinc.unusual_prehistory.entity.utils.UP2BoatType;
import com.barlinc.unusual_prehistory.items.*;
import com.barlinc.unusual_prehistory.utils.VariantHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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

@SuppressWarnings("deprecation")
public class UP2Items {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnusualPrehistory2.MOD_ID);
    public static List<RegistryObject<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    public static final List<Supplier<? extends Item>> EGG_EMBRYO_ITEMS = new ArrayList<>();
    public static final List<Supplier<? extends Item>> FOSSILS = new ArrayList<>();

    // tab icon
    public static final RegistryObject<Item> UNUSUAL_PREHISTORY = registerItem("unusual_prehistory", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ORGANIC_OOZE = registerItem("organic_ooze", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MACHINE_PARTS = registerItem("machine_parts", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GINKGO_FRUIT = registerItem("ginkgo_fruit", () -> new Item(registerFoodValue(UP2FoodValues.GINKGO_FRUIT)));

    // carnotaurus
    public static final RegistryObject<Item> CARNOTAURUS_SPAWN_EGG = registerSpawnEggItem("carnotaurus", UP2Entities.CARNOTAURUS, 0x8c2f27, 0x252b33);
    public static final RegistryObject<Item> FURY_FOSSIL = registerFossilItem("fury");

    // desmatosuchus
    public static final RegistryObject<Item> DESMATOSUCHUS_SPAWN_EGG = registerSpawnEggItem("desmatosuchus", UP2Entities.DESMATOSUCHUS, 0x272d3e, 0xba7725);

    // diplocaulus
    public static final RegistryObject<Item> DIPLOCAULUS_SPAWN_EGG = registerSpawnEggItem("diplocaulus", UP2Entities.DIPLOCAULUS, 0xe5721e, 0x292733);
    public static final RegistryObject<Item> DIPLOCAULUS_BUCKET = registerItemNoLang("diplocaulus_bucket", () -> new UP2MobBucketItem(UP2Entities.DIPLOCAULUS, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties(), VariantHelper.nameOf(Diplocaulus.DiplocaulusVariant::byId)));
    public static final RegistryObject<Item> BOOMERANG_FOSSIL = registerFossilItem("boomerang");

    // dromaeosaurus
    public static final RegistryObject<Item> DROMAEOSAURUS_SPAWN_EGG = registerSpawnEggItem("dromaeosaurus", UP2Entities.DROMAEOSAURUS, 0xf9fa5e, 0xebc754);
    public static final RegistryObject<Item> DROMAEOSAURUS_EGG = registerEggItem("dromaeosaurus", UP2Entities.DROMAEOSAURUS_EGG);
    public static final RegistryObject<Item> RUNNER_FOSSIL = registerFossilItem("runner");

    // dunkleosteus
    public static final RegistryObject<Item> DUNKLEOSTEUS_SPAWN_EGG = registerSpawnEggItem("dunkleosteus", UP2Entities.DUNKLEOSTEUS, 0x417a69, 0x825147);
    public static final RegistryObject<Item> DUNKLEOSTEUS_BUCKET = registerItemNoLang("dunkleosteus_bucket", () -> new MobBucketItem(UP2Entities.DUNKLEOSTEUS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GUILLOTINE_FOSSIL = registerFossilItem("guillotine");

    // jawless fish
    public static final RegistryObject<Item> JAWLESS_FISH_SPAWN_EGG = registerSpawnEggItem("jawless_fish", UP2Entities.JAWLESS_FISH, 0x312e38, 0x917388);
    public static final RegistryObject<Item> JAWLESS_FISH_BUCKET = registerItemNoLang("jawless_fish_bucket", () -> new UP2MobBucketItem(UP2Entities.JAWLESS_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties(), VariantHelper.nameOf(JawlessFish.JawlessFishVariant::byId)));
    public static final RegistryObject<Item> JAWLESS_FOSSIL = registerFossilItem("jawless");

    // kentrosaurus
    public static final RegistryObject<Item> KENTROSAURUS_SPAWN_EGG = registerSpawnEggItem("kentrosaurus", UP2Entities.KENTROSAURUS, 0x5a6637, 0x1d1f18);
    public static final RegistryObject<Item> PRICKLY_FOSSIL = registerFossilItem("prickly");

    // kimmeridgebrachypteraeschnidium
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM, 0x74c70f, 0x8a3466);
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium_nymph", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, 0x233213, 0x0a1206);
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE = registerItemNoLang("kimmeridgebrachypteraeschnidium_bottle", () -> new KimmeridgebrachypteraeschnidiumBottleItem(new Item.Properties()));
    public static final RegistryObject<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET = registerItemNoLang("kimmeridgebrachypteraeschnidium_nymph_bucket", () -> new UP2MobBucketItem(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties()));
    public static final RegistryObject<Item> IMPERATIVE_FOSSIL = registerFossilItem("imperative");

    // living ooze
    public static final RegistryObject<Item> LIVING_OOZE_SPAWN_EGG = registerSpawnEggItem("living_ooze", UP2Entities.LIVING_OOZE, 0x51da69, 0x055b2f);
    public static final RegistryObject<Item> LIVING_OOZE_BUCKET = registerItemNoLang("living_ooze_bucket", () -> new LivingOozeBucketItem(new Item.Properties().stacksTo(1)));

    // lystrosaurus
    public static final RegistryObject<Item> LYSTROSAURUS_SPAWN_EGG = registerSpawnEggItem("lystrosaurus", UP2Entities.LYSTROSAURUS, 0xaa9c65, 0x675a43);

    // majungasaurus
    public static final RegistryObject<Item> MAJUNGASAURUS_SPAWN_EGG = registerSpawnEggItem("majungasaurus", UP2Entities.MAJUNGASAURUS, 0x5aa81e, 0x194e80);
    public static final RegistryObject<Item> RUGOSE_FOSSIL = registerFossilItem("rugose");

    // megalania
    public static final RegistryObject<Item> MEGALANIA_SPAWN_EGG = registerSpawnEggItem("megalania", UP2Entities.MEGALANIA, 0x4f432b, 0x0fd4e6);
    public static final RegistryObject<Item> THERMAL_FOSSIL = registerFossilItem("thermal");

    // metriorhynchus
    public static final RegistryObject<Item> METRIORHYNCHUS_SPAWN_EGG = registerSpawnEggItem("metriorhynchus", UP2Entities.METRIORHYNCHUS, 0x3a2c4e, 0x8b549b);
    public static final RegistryObject<Item> MELTDOWN_FOSSIL = registerFossilItem("meltdown");
    public static final RegistryObject<Item> METRIORHYNCHUS_EMBRYO = registerEmbryoItem("metriorhynchus", UP2Entities.METRIORHYNCHUS);

    // onchopristis
    public static final RegistryObject<Item> ONCHOPRISTIS_SPAWN_EGG = registerSpawnEggItem("onchopristis", UP2Entities.ONCHOPRISTIS, 0xa27e47, 0x382b1e);
    public static final RegistryObject<Item> SAW_FOSSIL = registerFossilItem("saw");

    // pachycephalosaurus
    public static final RegistryObject<Item> PACHYCEPHALOSAURUS_SPAWN_EGG = registerSpawnEggItem("pachycephalosaurus", UP2Entities.PACHYCEPHALOSAURUS, 0x4f3d72, 0x67bedb);

    // stethacanthus
    public static final RegistryObject<Item> STETHACANTHUS_SPAWN_EGG = registerSpawnEggItem("stethacanthus", UP2Entities.STETHACANTHUS, 0x6e2e1f, 0xffa200);
    public static final RegistryObject<Item> STETHACANTHUS_BUCKET = registerItemNoLang("stethacanthus_bucket", () -> new MobBucketItem(UP2Entities.STETHACANTHUS, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ANVIL_FOSSIL = registerFossilItem("anvil");

    // talpanas
    public static final RegistryObject<Item> TALPANAS_SPAWN_EGG = registerSpawnEggItem("talpanas", UP2Entities.TALPANAS, 0x503527, 0x93ad87);
    public static final RegistryObject<Item> TALPANAS_EGG = registerEggItem("talpanas", UP2Entities.TALPANAS_EGG);
    public static final RegistryObject<Item> AGED_FEATHER = registerItem("aged_feather", () -> new Item(new Item.Properties()));

    // tartuosteus
    public static final RegistryObject<Item> TARTUOSTEUS_SPAWN_EGG = registerSpawnEggItem("tartuosteus", UP2Entities.TARTUOSTEUS, 0x23673c, 0x0b2720);

    // telecrex
    public static final RegistryObject<Item> TELECREX_SPAWN_EGG = registerSpawnEggItem("telecrex", UP2Entities.TELECREX, 0x121018, 0x770f38);
    public static final RegistryObject<Item> TELECREX_EGG = registerEggItem("telecrex", UP2Entities.TELECREX_EGG);
    public static final RegistryObject<Item> PLUMAGE_FOSSIL = registerFossilItem("plumage");

    // plant fossils
    public static final RegistryObject<Item> BENNETTITALES_FOSSIL = registerFossilItem("bennettitales");
    public static final RegistryObject<Item> CALAMOPHYTON_FOSSIL = registerFossilItem("calamophyton");
    public static final RegistryObject<Item> CLADOPHEBIS_FOSSIL = registerFossilItem("cladophebis");
    public static final RegistryObject<Item> COOKSONIA_FOSSIL = registerFossilItem("cooksonia");
    public static final RegistryObject<Item> HORSETAIL_FOSSIL = registerFossilItem("horsetail");
    public static final RegistryObject<Item> LEEFRUCTUS_FOSSIL = registerFossilItem("leefructus");
    public static final RegistryObject<Item> PROTOTAXITES_FOSSIL = registerFossilItem("prototaxites");
    public static final RegistryObject<Item> QUILLWORT_FOSSIL = registerFossilItem("quillwort");
    public static final RegistryObject<Item> RAIGUENRAYUN_FOSSIL = registerFossilItem("raiguenrayun");
    public static final RegistryObject<Item> RHYNIA_FOSSIL = registerFossilItem("rhynia");
    public static final RegistryObject<Item> GINKGO_FOSSIL = registerFossilItem("ginkgo");
    public static final RegistryObject<Item> LEPIDODENDRON_FOSSIL = registerFossilItem("lepidodendron");

    // wood
    public static final RegistryObject<Item> GINKGO_SIGN = registerItem("ginkgo_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.GINKGO_SIGN.get(), UP2Blocks.GINKGO_WALL_SIGN.get()));
    public static final RegistryObject<Item> GINKGO_HANGING_SIGN = registerItem("ginkgo_hanging_sign", () -> new HangingSignItem(UP2Blocks.GINKGO_HANGING_SIGN.get(), UP2Blocks.GINKGO_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> GINKGO_BOAT = registerItem("ginkgo_boat", () -> new UP2BoatItem(false, UP2BoatType.Type.GINKGO, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GINKGO_CHEST_BOAT = registerItemNoLang("ginkgo_chest_boat", () -> new UP2BoatItem(true, UP2BoatType.Type.GINKGO, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LEPIDODENDRON_SIGN = registerItem("lepidodendron_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.LEPIDODENDRON_SIGN.get(), UP2Blocks.LEPIDODENDRON_WALL_SIGN.get()));
    public static final RegistryObject<Item> LEPIDODENDRON_HANGING_SIGN = registerItem("lepidodendron_hanging_sign", () -> new HangingSignItem(UP2Blocks.LEPIDODENDRON_HANGING_SIGN.get(), UP2Blocks.LEPIDODENDRON_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> LEPIDODENDRON_BOAT = registerItem("lepidodendron_boat", () -> new UP2BoatItem(false, UP2BoatType.Type.LEPIDODENDRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEPIDODENDRON_CHEST_BOAT = registerItemNoLang("lepidodendron_chest_boat", () -> new UP2BoatItem(true, UP2BoatType.Type.LEPIDODENDRON, new Item.Properties().stacksTo(1)));

    // tar
    public static final RegistryObject<Item> TAR_BUCKET = registerItem("tar_bucket", () -> new BucketItem(UP2Fluids.TAR_FLUID_SOURCE.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    // discs
    public static final RegistryObject<Item> DOOMSURF_DISC = registerItemNoLang("doomsurf_disc", () -> new RecordItem(15, UP2SoundEvents.DOOMSURF_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3920));
    public static final RegistryObject<Item> MEGALANIA_DISC = registerItemNoLang("megalania_disc", () -> new RecordItem(14, UP2SoundEvents.MEGALANIA_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1400));
    public static final RegistryObject<Item> TARIFYING_DISC = registerItemNoLang("tarifying_disc", () -> new RecordItem(13, UP2SoundEvents.TARIFYING_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 5180));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static RegistryObject<Item> registerEggItem(String name, Supplier<? extends EntityType<?>> entityType) {
        RegistryObject<Item> item = registerItem(name + "_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16), entityType));
        EGG_EMBRYO_ITEMS.add(item);
        return item;
    }

    private static RegistryObject<Item> registerEmbryoItem(String name, Supplier<? extends EntityType<?>> entityType) {
        RegistryObject<Item> item = registerItem(name + "_embryo", () -> new EmbryoItem(new Item.Properties(), entityType));
        EGG_EMBRYO_ITEMS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> registerSpawnEggItem(String name, Supplier<? extends EntityType<? extends Mob>> type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    private static RegistryObject<Item> registerFossilItem(String name) {
        RegistryObject<Item> item = registerItem(name + "_fossil", () -> new Item(new Item.Properties()));
        FOSSILS.add(item);
        return item;
    }

    public static Item.Properties registerFoodValue(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
