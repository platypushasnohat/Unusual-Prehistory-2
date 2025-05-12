package com.unusualmodding.unusualprehistory2.items;

import com.unusualmodding.unusualprehistory2.UnusualPrehistory2;
import com.unusualmodding.unusualprehistory2.blocks.UP2Blocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UP2Items {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnusualPrehistory2.MOD_ID);
    public static List<RegistryObject<? extends Item>> AUTO_TRANSLATE = new ArrayList<>();

    public static final RegistryObject<Item> DEAD_CLATHRODICTYON_CORAL_FAN = ITEMS.register("dead_clathrodictyon_coral_fan", () -> new StandingAndWallBlockItem(UP2Blocks.DEAD_CLATHRODICTYON_CORAL_FAN.get(), UP2Blocks.DEAD_CLATHRODICTYON_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> CLATHRODICTYON_CORAL_FAN = ITEMS.register("clathrodictyon_coral_fan", () -> new StandingAndWallBlockItem(UP2Blocks.CLATHRODICTYON_CORAL_FAN.get(), UP2Blocks.CLATHRODICTYON_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        AUTO_TRANSLATE.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        return item;
    }
}
