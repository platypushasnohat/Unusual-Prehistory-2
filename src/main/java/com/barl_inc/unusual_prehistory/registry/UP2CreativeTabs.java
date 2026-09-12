package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2CreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UNUSUAL_PREHISTORY_TAB = CREATIVE_MODE_TAB.register("unusual_prehistory_creative_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(UP2Blocks.FOSSILIZED_SKULL_LANTERN.get()))
                    .title(Component.translatable("creative_tab.unusual_prehistory"))
                    .displayItems((parameters, output) -> {
                        output.accept(UP2Blocks.FOSSILIZED_BONE_BLOCK.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_BARK.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_VERTEBRA.get());
                        output.accept(UP2Blocks.FOSSILIZED_SKULL.get());
                        output.accept(UP2Blocks.FOSSILIZED_SKULL_LANTERN.get());
                        output.accept(UP2Blocks.FOSSILIZED_SKULL_SOUL_LANTERN.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_ROD.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_ROW.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_SPIKE.get());
                        output.accept(UP2Blocks.COBBLED_FOSSILIZED_BONE.get());
                        output.accept(UP2Blocks.COBBLED_FOSSILIZED_BONE_STAIRS.get());
                        output.accept(UP2Blocks.COBBLED_FOSSILIZED_BONE_SLAB.get());

                        output.accept(UP2Blocks.DIRT_MATRIX.get());
                        output.accept(UP2Blocks.COMMON_FOSSIL_BED.get());
                        output.accept(UP2Blocks.UNCOMMON_FOSSIL_BED.get());
                        output.accept(UP2Blocks.RARE_FOSSIL_BED.get());
                        output.accept(UP2Blocks.UNUSUAL_FOSSIL_BED.get());

                        output.accept(UP2Blocks.BIOSTEEL_BLOCK.get());
                        output.accept(UP2Blocks.BIOSTEEL_TILES.get());
                        output.accept(UP2Blocks.BIOSTEEL_TILE_STAIRS.get());
                        output.accept(UP2Blocks.BIOSTEEL_TILE_SLAB.get());
                        output.accept(UP2Blocks.BIOSTEEL_LATTICE.get());

                        output.accept(UP2Items.ORGANIC_OOZE.get());
                        output.accept(UP2Blocks.TRANSMOGRIFIER.get());

                    })
                    .build());
}
