package com.unusualmodding.unusual_prehistory;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.unusualmodding.unusual_prehistory.registry.UP2Items.*;

public class UnusualPrehistory2Tab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNUSUAL_PREHISTORY_2_TAB = CREATIVE_TABS.register("unusual_prehistory",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(MESOZOIC_FOSSIL.get()))
                    .title(Component.translatable("itemGroup.unusual_prehistory"))
                    .displayItems((parameters, tabOutput) -> {

                        tabOutput.accept(CHISEL.get());

                        tabOutput.accept(PALEOZOIC_FOSSIL.get());
                        tabOutput.accept(MESOZOIC_FOSSIL.get());
                        tabOutput.accept(FROZEN_MEAT.get());
                        tabOutput.accept(PLANT_FOSSIL.get());
                        tabOutput.accept(UP2Blocks.DEEPSLATE_FOSSIL.get());
                        tabOutput.accept(UP2Blocks.FOSSIL.get());
                        tabOutput.accept(UP2Blocks.FROZEN_MEAT_BLOCK.get());

                        tabOutput.accept(ORGANIC_OOZE.get());

                        tabOutput.accept(UP2Blocks.TRANSMOGRIFIER.get());

                        // paleozoic
                        tabOutput.accept(DIPLOCAULUS_SPAWN_EGG.get());
                        tabOutput.accept(DIPLOCAULUS_BUCKET.get());
                        tabOutput.accept(DUNKLEOSTEUS_SPAWN_EGG.get());
                        tabOutput.accept(DUNKLEOSTEUS_BUCKET.get());
                        tabOutput.accept(JAWLESS_FISH_SPAWN_EGG.get());
                        tabOutput.accept(JAWLESS_FISH_BUCKET.get());
                        tabOutput.accept(STETHACANTHUS_SPAWN_EGG.get());
                        tabOutput.accept(STETHACANTHUS_BUCKET.get());

                        tabOutput.accept(CARNOTAURUS_SPAWN_EGG.get());
                        tabOutput.accept(DROMAEOSAURUS_SPAWN_EGG.get());
                        tabOutput.accept(KENTROSAURUS_SPAWN_EGG.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_SPAWN_EGG.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
                        tabOutput.accept(MAJUNGASAURUS_SPAWN_EGG.get());

                        tabOutput.accept(MEGALANIA_SPAWN_EGG.get());
                        tabOutput.accept(TELECREX_SPAWN_EGG.get());

                        tabOutput.accept(TALPANAS_SPAWN_EGG.get());

                        tabOutput.accept(DIPLOCAULUS_DNA.get());
                        tabOutput.accept(DUNKLEOSTEUS_DNA.get());
                        tabOutput.accept(JAWLESS_FISH_DNA.get());
                        tabOutput.accept(STETHACANTHUS_DNA.get());

                        tabOutput.accept(CARNOTAURUS_DNA.get());
                        tabOutput.accept(DROMAEOSAURUS_DNA.get());
                        tabOutput.accept(KENTROSAURUS_DNA.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA.get());
                        tabOutput.accept(MAJUNGASAURUS_DNA.get());

                        tabOutput.accept(MEGALANIA_DNA.get());
                        tabOutput.accept(TELECREX_DNA.get());

                        tabOutput.accept(TALPANAS_DNA.get());

                        tabOutput.accept(UP2Blocks.DIPLOCAULUS_EGGS.get());
                        tabOutput.accept(DUNKLEOSTEUS_SAC.get());
                        tabOutput.accept(UP2Blocks.JAWLESS_FISH_ROE.get());
                        tabOutput.accept(STETHACANTHUS_SAC.get());

                        tabOutput.accept(UP2Blocks.CARNOTAURUS_EGG.get());
                        tabOutput.accept(DROMAEOSAURUS_EGG.get());
                        tabOutput.accept(UP2Blocks.KENTROSAURUS_EGG.get());
                        tabOutput.accept(UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
                        tabOutput.accept(UP2Blocks.MAJUNGASAURUS_EGG.get());

                        tabOutput.accept(UP2Blocks.MEGALANIA_EGG.get());
                        tabOutput.accept(TELECREX_EGG.get());

                        tabOutput.accept(TALPANAS_EGG.get());

                        // plant dna
                        tabOutput.accept(ARCHAEOSIGILLARIA_DNA.get());
                        tabOutput.accept(BENNETTITALES_DNA.get());
                        tabOutput.accept(CALAMOPHYTON_DNA.get());
                        tabOutput.accept(CLADOPHLEBIS_DNA.get());
                        tabOutput.accept(COOKSONIA_DNA.get());
                        tabOutput.accept(GINKGO_DNA.get());
                        tabOutput.accept(HORSETAIL_DNA.get());
                        tabOutput.accept(ISOETES_DNA.get());
                        tabOutput.accept(LEEFRUCTUS_DNA.get());
                        tabOutput.accept(LEPIDODENDRON_DNA.get());
                        tabOutput.accept(RAIGUENRAYUN_DNA.get());
                        tabOutput.accept(RHYNIA_DNA.get());
                        tabOutput.accept(SARRACENIA_DNA.get());

                        // water plant dna
                        tabOutput.accept(ARCHAEFRUCTUS_DNA.get());
                        tabOutput.accept(NELUMBITES_DNA.get());
                        tabOutput.accept(QUEREUXIA_DNA.get());
                        tabOutput.accept(ANOSTYLOSTROMA_DNA.get());
                        tabOutput.accept(CLATHRODICTYON_CORAL_DNA.get());

                        // plants
                        tabOutput.accept(UP2Blocks.ARCHAEFRUCTUS.get());
                        tabOutput.accept(UP2Blocks.ARCHAEOSIGILLARIA.get());
                        tabOutput.accept(UP2Blocks.CALAMOPHYTON.get());
                        tabOutput.accept(UP2Blocks.CLADOPHLEBIS.get());
                        tabOutput.accept(UP2Blocks.COOKSONIA.get());
                        tabOutput.accept(UP2Blocks.HORSETAIL.get());
                        tabOutput.accept(UP2Blocks.LARGE_HORSETAIL.get());
                        tabOutput.accept(UP2Blocks.ISOETES.get());
                        tabOutput.accept(UP2Blocks.LEEFRUCTUS.get());
                        tabOutput.accept(UP2Blocks.NELUMBITES.get());
                        tabOutput.accept(UP2Blocks.RAIGUENRAYUN.get());
                        tabOutput.accept(UP2Blocks.RHYNIA.get());
                        tabOutput.accept(UP2Blocks.SARRACENIA.get());
                        tabOutput.accept(UP2Blocks.TALL_SARRACENIA.get());
                        tabOutput.accept(UP2Blocks.MOSS_LAYER.get());
                        tabOutput.accept(UP2Blocks.MOSSY_DIRT.get());

                        // water plants
                        tabOutput.accept(UP2Blocks.QUEREUXIA.get());
                        tabOutput.accept(UP2Blocks.QUEREUXIA_CLOVERS.get());
                        tabOutput.accept(UP2Blocks.PETRIFIED_ANOSTYLOSTROMA.get());
                        tabOutput.accept(UP2Blocks.ANOSTYLOSTROMA.get());
                        tabOutput.accept(UP2Blocks.CLATHRODICTYON_CORAL_BLOCK.get());
                        tabOutput.accept(UP2Blocks.CLATHRODICTYON_CORAL.get());
                        tabOutput.accept(UP2Items.CLATHRODICTYON_CORAL_FAN.get());
                        tabOutput.accept(UP2Blocks.DEAD_CLATHRODICTYON_CORAL_BLOCK.get());
                        tabOutput.accept(UP2Blocks.DEAD_CLATHRODICTYON_CORAL.get());
                        tabOutput.accept(UP2Items.DEAD_CLATHRODICTYON_CORAL_FAN.get());

                        // trees
                        tabOutput.accept(UP2Blocks.GINKGO_LOG.get());
                        tabOutput.accept(UP2Blocks.GINKGO_WOOD.get());
                        tabOutput.accept(UP2Blocks.STRIPPED_GINKGO_LOG.get());
                        tabOutput.accept(UP2Blocks.STRIPPED_GINKGO_WOOD.get());
                        tabOutput.accept(UP2Blocks.GINKGO_PLANKS.get());
                        tabOutput.accept(UP2Blocks.GINKGO_STAIRS.get());
                        tabOutput.accept(UP2Blocks.GINKGO_SLAB.get());
                        tabOutput.accept(UP2Blocks.GINKGO_FENCE.get());
                        tabOutput.accept(UP2Blocks.GINKGO_FENCE_GATE.get());
                        tabOutput.accept(UP2Blocks.GINKGO_DOOR.get());
                        tabOutput.accept(UP2Blocks.GINKGO_TRAPDOOR.get());
                        tabOutput.accept(UP2Blocks.GINKGO_PRESSURE_PLATE.get());
                        tabOutput.accept(UP2Blocks.GINKGO_BUTTON.get());
                        tabOutput.accept(UP2Blocks.GINKGO_LEAVES.get());
                        tabOutput.accept(UP2Blocks.GOLDEN_GINKGO_LEAVES.get());
                        tabOutput.accept(UP2Blocks.GINKGO_SAPLING.get());
                        tabOutput.accept(UP2Blocks.GOLDEN_GINKGO_SAPLING.get());
                        tabOutput.accept(GINKGO_FRUIT.get());

                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_LOG.get());
                        tabOutput.accept(UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_WOOD.get());
                        tabOutput.accept(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD.get());
                        tabOutput.accept(UP2Blocks.STRIPPED_LEPIDODENDRON_LOG.get());
                        tabOutput.accept(UP2Blocks.STRIPPED_LEPIDODENDRON_WOOD.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_PLANKS.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_STAIRS.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_SLAB.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_FENCE.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_FENCE_GATE.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_DOOR.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_TRAPDOOR.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_PRESSURE_PLATE.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_BUTTON.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_LEAVES.get());
                        tabOutput.accept(UP2Blocks.HANGING_LEPIDODENDRON_LEAVES.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_CONE.get());

                    }).build());
}