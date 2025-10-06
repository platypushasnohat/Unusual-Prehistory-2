package com.unusualmodding.unusual_prehistory;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(UNUSUAL_PREHISTORY.get()))
                    .title(Component.translatable("itemGroup.unusual_prehistory"))
                    .displayItems((parameters, tabOutput) -> {

                        tabOutput.accept(CHISEL.get());

                        tabOutput.accept(UP2Blocks.FOSSILIZED_BONE_BLOCK.get());
                        tabOutput.accept(UP2Blocks.FOSSILIZED_BONE_VERTEBRA.get());
                        tabOutput.accept(UP2Blocks.FOSSIL.get());
                        tabOutput.accept(UP2Blocks.DEEPSLATE_FOSSIL.get());

                        tabOutput.accept(ORGANIC_OOZE.get());
                        tabOutput.accept(MACHINE_PARTS.get());

                        tabOutput.accept(UP2Blocks.TRANSMOGRIFIER.get());

                        tabOutput.accept(CARNOTAURUS_SPAWN_EGG.get());
                        tabOutput.accept(DIPLOCAULUS_SPAWN_EGG.get());
                        tabOutput.accept(DIPLOCAULUS_BUCKET.get());
                        tabOutput.accept(DROMAEOSAURUS_SPAWN_EGG.get());
                        tabOutput.accept(DUNKLEOSTEUS_SPAWN_EGG.get());
                        tabOutput.accept(DUNKLEOSTEUS_BUCKET.get());
                        tabOutput.accept(JAWLESS_FISH_SPAWN_EGG.get());
                        tabOutput.accept(JAWLESS_FISH_BUCKET.get());
                        tabOutput.accept(KENTROSAURUS_SPAWN_EGG.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_SPAWN_EGG.get());
                        tabOutput.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
                        tabOutput.accept(MAJUNGASAURUS_SPAWN_EGG.get());
                        tabOutput.accept(MEGALANIA_SPAWN_EGG.get());
                        tabOutput.accept(STETHACANTHUS_SPAWN_EGG.get());
                        tabOutput.accept(STETHACANTHUS_BUCKET.get());
                        tabOutput.accept(TALPANAS_SPAWN_EGG.get());
                        tabOutput.accept(TELECREX_SPAWN_EGG.get());

                        tabOutput.accept(FURY_FOSSIL.get());
                        tabOutput.accept(BOOMERANG_FOSSIL.get());
                        tabOutput.accept(RUNNER_FOSSIL.get());
                        tabOutput.accept(GUILLOTINE_FOSSIL.get());
                        tabOutput.accept(JAWLESS_FOSSIL.get());
                        tabOutput.accept(PRICKLY_FOSSIL.get());
                        tabOutput.accept(IMPERATIVE_FOSSIL.get());
                        tabOutput.accept(RUGOSE_FOSSIL.get());
                        tabOutput.accept(THERMAL_FOSSIL.get());
                        tabOutput.accept(ANVIL_FOSSIL.get());
                        tabOutput.accept(AGED_FEATHER.get());
                        tabOutput.accept(PLUMAGE_FOSSIL.get());

                        tabOutput.accept(UP2Blocks.CARNOTAURUS_EGG.get());
                        tabOutput.accept(UP2Blocks.DIPLOCAULUS_EGGS.get());
                        tabOutput.accept(DROMAEOSAURUS_EGG.get());
                        tabOutput.accept(UP2Blocks.DUNKLEOSTEUS_SAC.get());
                        tabOutput.accept(UP2Blocks.JAWLESS_FISH_ROE.get());
                        tabOutput.accept(UP2Blocks.KENTROSAURUS_EGG.get());
                        tabOutput.accept(UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
                        tabOutput.accept(UP2Blocks.MAJUNGASAURUS_EGG.get());
                        tabOutput.accept(UP2Blocks.MEGALANIA_EGG.get());
                        tabOutput.accept(UP2Blocks.STETHACANTHUS_SAC.get());
                        tabOutput.accept(TALPANAS_EGG.get());
                        tabOutput.accept(TELECREX_EGG.get());

                        tabOutput.accept(TRUNK_FOSSIL.get());
                        tabOutput.accept(BARK_FOSSIL.get());
                        tabOutput.accept(SPORE_FOSSIL.get());
                        tabOutput.accept(SPINDLY_FOSSIL.get());
                        tabOutput.accept(VASCULAR_FOSSIL.get());
                        tabOutput.accept(LEAFY_FOSSIL.get());
                        tabOutput.accept(FLOWERING_FOSSIL.get());
                        tabOutput.accept(BLOOMED_FOSSIL.get());
                        tabOutput.accept(FIBROUS_FOSSIL.get());
                        tabOutput.accept(FAN_FOSSIL.get());
                        tabOutput.accept(SAPLING_FOSSIL.get());

                        // plants
                        tabOutput.accept(UP2Blocks.BENNETTITALES.get());
                        tabOutput.accept(UP2Blocks.CALAMOPHYTON.get());
                        tabOutput.accept(UP2Blocks.CLADOPHLEBIS.get());
                        tabOutput.accept(UP2Blocks.COOKSONIA.get());
                        tabOutput.accept(UP2Blocks.HORSETAIL.get());
                        tabOutput.accept(UP2Blocks.LARGE_HORSETAIL.get());
                        tabOutput.accept(UP2Blocks.ISOETES.get());
                        tabOutput.accept(UP2Blocks.LEEFRUCTUS.get());
                        tabOutput.accept(UP2Blocks.RAIGUENRAYUN.get());
                        tabOutput.accept(UP2Blocks.RHYNIA.get());
                        tabOutput.accept(UP2Blocks.MOSS_LAYER.get());
                        tabOutput.accept(UP2Blocks.MOSSY_DIRT.get());

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
                        tabOutput.accept(GINKGO_SIGN.get());
                        tabOutput.accept(GINKGO_HANGING_SIGN.get());
                        tabOutput.accept(GINKGO_BOAT.get());
                        tabOutput.accept(GINKGO_CHEST_BOAT.get());
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
                        tabOutput.accept(LEPIDODENDRON_SIGN.get());
                        tabOutput.accept(LEPIDODENDRON_HANGING_SIGN.get());
                        tabOutput.accept(LEPIDODENDRON_BOAT.get());
                        tabOutput.accept(LEPIDODENDRON_CHEST_BOAT.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_LEAVES.get());
                        tabOutput.accept(UP2Blocks.HANGING_LEPIDODENDRON_LEAVES.get());
                        tabOutput.accept(UP2Blocks.LEPIDODENDRON_CONE.get());

                    }).build());
}