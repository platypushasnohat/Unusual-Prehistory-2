package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Ammonite;
import com.barl_inc.unusual_prehistory.item.UP2MobBucketItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class UP2Items {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UnusualPrehistory2.MOD_ID);
    public static List<DeferredItem<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    public static final DeferredItem<Item> ORGANIC_OOZE = registerItem("organic_ooze", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AMMONITE_SPAWN_EGG = registerSpawnEggItem("ammonite", UP2Entities.AMMONITE, 0x5d4630, 0xdd6186);
    public static final DeferredItem<Item> LEEDSICHTHYS_SPAWN_EGG = registerSpawnEggItem("leedsichthys", UP2Entities.LEEDSICHTHYS, 0x15161d, 0xe8e8e8);

    public static final DeferredItem<Item> SPIRAL_FOSSIL = registerItem("spiral_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GARGANTUAN_FOSSIL = registerItem("gargantuan_fossil", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AMMONITE_BUCKET = registerItemNoLang("ammonite_bucket", () -> new UP2MobBucketItem(UP2Entities.AMMONITE.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties(), nameOf(Ammonite.AmmoniteVariant::byId)));

    private static <I extends Item> DeferredItem<I> registerItem(String name, Supplier<? extends I> supplier) {
        DeferredItem<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static <I extends Item> DeferredItem<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static DeferredItem<Item> registerSpawnEggItem(String name, Supplier<? extends EntityType<? extends Mob>> type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new DeferredSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    public static IntFunction<String> nameOf(IntFunction<? extends Enum<?>> enumGetter) {
        return id -> enumGetter.apply(id).name().toLowerCase(Locale.ROOT);
    }
}
