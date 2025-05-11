package com.unusualmodding.unusualprehistory2.tab;

import com.unusualmodding.unusualprehistory2.UnusualPrehistory2;
import com.unusualmodding.unusualprehistory2.blocks.UP2Blocks;
import com.unusualmodding.unusualprehistory2.items.UP2Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2CreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    private static final CreativeModeTab UP2_TAB = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 9)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.unusual_prehistory_2"))
            .icon(() -> new ItemStack(Items.GLASS_BOTTLE))
            .displayItems((d, entries) ->{


                for(RegistryObject<Item> item : UP2Items.ITEMS.getEntries()){

                        entries.accept(UP2Blocks.CALAMOPHYTON.get());

                    }
            })
            .build();

    public static final RegistryObject<CreativeModeTab> UP2_CREATIVE_TAB = TABS.register("unusual_prehistory", () -> UP2_TAB);

}
