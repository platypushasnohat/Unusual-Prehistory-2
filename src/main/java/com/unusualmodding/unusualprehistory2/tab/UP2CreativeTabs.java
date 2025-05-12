package com.unusualmodding.unusualprehistory2.tab;

import com.unusualmodding.unusualprehistory2.UnusualPrehistory2;
import com.unusualmodding.unusualprehistory2.blocks.UP2Blocks;
import com.unusualmodding.unusualprehistory2.items.UP2Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2CreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNUSUAL_PREHISTORY_2_TAB = CREATIVE_TABS.register("unusualprehistory2",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(UP2Blocks.SARRACENIA.get()))
                    .title(Component.translatable("itemGroup.unusualprehistory2"))
                    .displayItems((pParameters, tabOutput) -> {

                        tabOutput.accept(UP2Blocks.CALAMOPHYTON.get());
                        tabOutput.accept(UP2Blocks.CLADOPHLEBIS.get());
                        tabOutput.accept(UP2Blocks.COOKSONIA.get());
                        tabOutput.accept(UP2Blocks.ISOETES_BEESTONII.get());
                        tabOutput.accept(UP2Blocks.LEEFRUCTUS.get());
                        tabOutput.accept(UP2Blocks.RHYNIA.get());
                        tabOutput.accept(UP2Blocks.SARRACENIA.get());
                        tabOutput.accept(UP2Blocks.MOSSY_DIRT.get());
                        tabOutput.accept(UP2Blocks.ANOSTYLOSTROMA.get());
                        tabOutput.accept(UP2Blocks.CLATHRODICTYON_CORAL_BLOCK.get());
                        tabOutput.accept(UP2Blocks.CLATHRODICTYON_CORAL.get());
                        tabOutput.accept(UP2Items.CLATHRODICTYON_CORAL_FAN.get());
                        tabOutput.accept(UP2Blocks.DEAD_CLATHRODICTYON_CORAL_BLOCK.get());
                        tabOutput.accept(UP2Blocks.DEAD_CLATHRODICTYON_CORAL.get());
                        tabOutput.accept(UP2Items.DEAD_CLATHRODICTYON_CORAL_FAN.get());

                    }).build());
}