package com.barlinc.unusual_prehistory;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UnusualPrehistory2Tab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNUSUAL_PREHISTORY_2_TAB = CREATIVE_TABS.register("unusual_prehistory",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(UNUSUAL_PREHISTORY.get()))
                    .title(Component.translatable("itemGroup.unusual_prehistory"))
                    .displayItems((parameters, output) -> {

                        // Spawn eggs
                        UP2Items.ITEMS.getEntries().forEach(spawnEgg -> {
                            if ((spawnEgg.get() instanceof ForgeSpawnEggItem)) {
                                output.accept(spawnEgg.get());
                            }
                        });

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

                        output.accept(TAR_BUCKET.get());
                        output.accept(UP2Blocks.ASPHALT.get());
                        output.accept(TARIFYING_DISC.get());

                        output.accept(UP2Blocks.PETRIFIED_LOG.get());
                        output.accept(UP2Blocks.PETRIFIED_WOOD.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_SLAB.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_BUTTON.get());

                        output.accept(ORGANIC_OOZE.get());
                        output.accept(MACHINE_PARTS.get());

                        output.accept(UP2Blocks.TRANSMOGRIFIER.get());

                        output.accept(DIPLOCAULUS_BUCKET.get());
                        output.accept(DUNKLEOSTEUS_BUCKET.get());
                        output.accept(JAWLESS_FISH_BUCKET.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
                        output.accept(STETHACANTHUS_BUCKET.get());

                        output.accept(FURY_FOSSIL.get());
                        output.accept(BOOMERANG_FOSSIL.get());
                        output.accept(RUNNER_FOSSIL.get());
                        output.accept(GUILLOTINE_FOSSIL.get());
                        output.accept(JAWLESS_FOSSIL.get());
                        output.accept(PRICKLY_FOSSIL.get());
                        output.accept(IMPERATIVE_FOSSIL.get());
                        output.accept(RUGOSE_FOSSIL.get());
                        output.accept(THERMAL_FOSSIL.get());
                        output.accept(ANVIL_FOSSIL.get());
                        output.accept(AGED_FEATHER.get());
                        output.accept(PLUMAGE_FOSSIL.get());

                        output.accept(UP2Blocks.CARNOTAURUS_EGG.get());
                        output.accept(UP2Blocks.DIPLOCAULUS_EGGS.get());
                        output.accept(DROMAEOSAURUS_EGG.get());
                        output.accept(UP2Blocks.DUNKLEOSTEUS_SAC.get());
                        output.accept(UP2Blocks.JAWLESS_FISH_ROE.get());
                        output.accept(UP2Blocks.KENTROSAURUS_EGG.get());
                        output.accept(UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
                        output.accept(UP2Blocks.MAJUNGASAURUS_EGG.get());
                        output.accept(UP2Blocks.MEGALANIA_EGG.get());
                        output.accept(UP2Blocks.STETHACANTHUS_SAC.get());
                        output.accept(TALPANAS_EGG.get());
                        output.accept(TELECREX_EGG.get());

                        output.accept(BENNETTITALES_FOSSIL.get());
                        output.accept(CALAMOPHYTON_FOSSIL.get());
                        output.accept(CLADOPHEBIS_FOSSIL.get());
                        output.accept(COOKSONIA_FOSSIL.get());
                        output.accept(HORSETAIL_FOSSIL.get());
                        output.accept(LEEFRUCTUS_FOSSIL.get());
                        output.accept(QUILLWORT_FOSSIL.get());
                        output.accept(RAIGUENRAYUN_FOSSIL.get());
                        output.accept(RHYNIA_FOSSIL.get());
                        output.accept(GINKGO_FOSSIL.get());
                        output.accept(LEPIDODENDRON_FOSSIL.get());

                        // plants
                        output.accept(UP2Blocks.BENNETTITALES.get());
                        output.accept(UP2Blocks.CALAMOPHYTON.get());
                        output.accept(UP2Blocks.CLADOPHLEBIS.get());
                        output.accept(UP2Blocks.COOKSONIA.get());
                        output.accept(UP2Blocks.HORSETAIL.get());
                        output.accept(UP2Blocks.LARGE_HORSETAIL.get());
                        output.accept(UP2Blocks.LEEFRUCTUS.get());
                        output.accept(UP2Blocks.QUILLWORT.get());
                        output.accept(UP2Blocks.RAIGUENRAYUN.get());
                        output.accept(UP2Blocks.RHYNIA.get());
                        output.accept(UP2Blocks.MOSS_LAYER.get());
                        output.accept(UP2Blocks.MOSSY_DIRT.get());

                        // trees
                        output.accept(UP2Blocks.GINKGO_LOG.get());
                        output.accept(UP2Blocks.GINKGO_WOOD.get());
                        output.accept(UP2Blocks.STRIPPED_GINKGO_LOG.get());
                        output.accept(UP2Blocks.STRIPPED_GINKGO_WOOD.get());
                        output.accept(UP2Blocks.GINKGO_PLANKS.get());
                        output.accept(UP2Blocks.GINKGO_STAIRS.get());
                        output.accept(UP2Blocks.GINKGO_SLAB.get());
                        output.accept(UP2Blocks.GINKGO_FENCE.get());
                        output.accept(UP2Blocks.GINKGO_FENCE_GATE.get());
                        output.accept(UP2Blocks.GINKGO_DOOR.get());
                        output.accept(UP2Blocks.GINKGO_TRAPDOOR.get());
                        output.accept(UP2Blocks.GINKGO_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.GINKGO_BUTTON.get());
                        output.accept(GINKGO_SIGN.get());
                        output.accept(GINKGO_HANGING_SIGN.get());
                        output.accept(GINKGO_BOAT.get());
                        output.accept(GINKGO_CHEST_BOAT.get());
                        output.accept(UP2Blocks.GINKGO_LEAVES.get());
                        output.accept(UP2Blocks.GOLDEN_GINKGO_LEAVES.get());
                        output.accept(UP2Blocks.GINKGO_SAPLING.get());
                        output.accept(UP2Blocks.GOLDEN_GINKGO_SAPLING.get());
                        output.accept(GINKGO_FRUIT.get());

                        output.accept(UP2Blocks.LEPIDODENDRON_LOG.get());
                        output.accept(UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_WOOD.get());
                        output.accept(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD.get());
                        output.accept(UP2Blocks.STRIPPED_LEPIDODENDRON_LOG.get());
                        output.accept(UP2Blocks.STRIPPED_LEPIDODENDRON_WOOD.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_PLANKS.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_STAIRS.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_SLAB.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_FENCE.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_FENCE_GATE.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_DOOR.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_TRAPDOOR.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_BUTTON.get());
                        output.accept(LEPIDODENDRON_SIGN.get());
                        output.accept(LEPIDODENDRON_HANGING_SIGN.get());
                        output.accept(LEPIDODENDRON_BOAT.get());
                        output.accept(LEPIDODENDRON_CHEST_BOAT.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_LEAVES.get());
                        output.accept(UP2Blocks.HANGING_LEPIDODENDRON_LEAVES.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_CONE.get());

                    }).build());
}