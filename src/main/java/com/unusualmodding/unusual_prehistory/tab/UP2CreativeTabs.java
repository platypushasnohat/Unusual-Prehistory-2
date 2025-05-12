package com.unusualmodding.unusual_prehistory.tab;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import com.unusualmodding.unusual_prehistory.items.UP2Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2CreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNUSUAL_PREHISTORY_2_TAB = CREATIVE_TABS.register("unusual_prehistory",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(UP2Blocks.TRUMPET_PITCHER.get()))
                    .title(Component.translatable("itemGroup.unusual_prehistory"))
                    .displayItems((pParameters, tabOutput) -> {

                        tabOutput.accept(UP2Blocks.FROZEN_MEAT_BLOCK.get());
                        tabOutput.accept(UP2Blocks.CALAMOPHYTON.get());
                        tabOutput.accept(UP2Blocks.CLADOPHLEBIS.get());
                        tabOutput.accept(UP2Blocks.COOKSONIA.get());
                        tabOutput.accept(UP2Blocks.HORSETAIL.get());
                        tabOutput.accept(UP2Blocks.LARGE_HORSETAIL.get());
                        tabOutput.accept(UP2Blocks.LEEFRUCTUS.get());
                        tabOutput.accept(UP2Blocks.QUILLWORTS.get());
                        tabOutput.accept(UP2Blocks.RAIGUENRAYUN.get());
                        tabOutput.accept(UP2Blocks.RHYNIA.get());
                        tabOutput.accept(UP2Blocks.TRUMPET_PITCHER.get());
                        tabOutput.accept(UP2Blocks.TALL_TRUMPET_PITCHER.get());
                        tabOutput.accept(UP2Blocks.MOSSY_DIRT.get());
                        tabOutput.accept(UP2Blocks.QUEREUXIA.get());
                        tabOutput.accept(UP2Blocks.QUEREUXIA_CLOVERS.get());
                        tabOutput.accept(UP2Blocks.ANOSTYLOSTROMA.get());
                        tabOutput.accept(UP2Blocks.PETRIFIED_ANOSTYLOSTROMA.get());
                        tabOutput.accept(UP2Blocks.CLATHRODICTYON_CORAL_BLOCK.get());
                        tabOutput.accept(UP2Blocks.CLATHRODICTYON_CORAL.get());
                        tabOutput.accept(UP2Items.CLATHRODICTYON_CORAL_FAN.get());
                        tabOutput.accept(UP2Blocks.DEAD_CLATHRODICTYON_CORAL_BLOCK.get());
                        tabOutput.accept(UP2Blocks.DEAD_CLATHRODICTYON_CORAL.get());
                        tabOutput.accept(UP2Items.DEAD_CLATHRODICTYON_CORAL_FAN.get());

                    }).build());
}