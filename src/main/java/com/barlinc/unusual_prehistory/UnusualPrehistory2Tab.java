package com.barlinc.unusual_prehistory;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UnusualPrehistory2Tab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNUSUAL_PREHISTORY_2_TAB = CREATIVE_TABS.register("unusual_prehistory",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(UNUSUAL_PREHISTORY.get()))
                    .title(Component.translatable("itemGroup.unusual_prehistory"))
                    .displayItems((parameters, output) -> {

                        // Prehistoric
                        output.accept(AEGIROCASSIS_SPAWN_EGG.get());
                        output.accept(BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(CARNOTAURUS_SPAWN_EGG.get());
                        output.accept(COELACANTHUS_SPAWN_EGG.get());
                        output.accept(DESMATOSUCHUS_SPAWN_EGG.get());
                        output.accept(DIPLOCAULUS_SPAWN_EGG.get());
                        output.accept(DROMAEOSAURUS_SPAWN_EGG.get());
                        output.accept(DUNKLEOSTEUS_SPAWN_EGG.get());
                        output.accept(HIBBERTOPTERUS_SPAWN_EGG.get());
                        output.accept(JAWLESS_FISH_SPAWN_EGG.get());
                        output.accept(KAPROSUCHUS_SPAWN_EGG.get());
                        output.accept(KENTROSAURUS_SPAWN_EGG.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_SPAWN_EGG.get());
                        output.accept(LEPTICTIDIUM_SPAWN_EGG.get());
                        output.accept(LOBE_FINNED_FISH_SPAWN_EGG.get());
                        output.accept(LYSTROSAURUS_SPAWN_EGG.get());
                        output.accept(MAJUNGASAURUS_SPAWN_EGG.get());
                        output.accept(MEGALANIA_SPAWN_EGG.get());
                        output.accept(METRIORHYNCHUS_SPAWN_EGG.get());
                        output.accept(MOSASAURUS_SPAWN_EGG.get());
                        output.accept(ONCHOPRISTIS_SPAWN_EGG.get());
                        output.accept(PACHYCEPHALOSAURUS_SPAWN_EGG.get());
                        output.accept(PRAEPUSA_SPAWN_EGG.get());
                        output.accept(PSILOPTERUS_SPAWN_EGG.get());
                        output.accept(PTERODACTYLUS_SPAWN_EGG.get());
                        output.accept(STETHACANTHUS_SPAWN_EGG.get());
                        output.accept(TALPANAS_SPAWN_EGG.get());
                        output.accept(TARTUOSTEUS_SPAWN_EGG.get());
                        output.accept(TELECREX_SPAWN_EGG.get());
                        output.accept(ULUGHBEGSAURUS_SPAWN_EGG.get());

                        // Ambient
                        output.accept(DELITZSCHALA_SPAWN_EGG.get());
                        output.accept(ZHANGSOLVA_SPAWN_EGG.get());

                        // Misc
                        output.accept(LIVING_OOZE_SPAWN_EGG.get());

                        output.accept(PALEOPEDIA.get());

                        output.accept(ORGANIC_OOZE.get());
                        output.accept(LIVING_OOZE_BUCKET.get());
                        output.accept(MACHINE_PARTS.get());
                        output.accept(UP2Blocks.TRANSMOGRIFIER.get());

                        output.accept(UP2Blocks.PALEOSTONE.get());
                        output.accept(UP2Blocks.PALEOSTONE_STAIRS.get());
                        output.accept(UP2Blocks.PALEOSTONE_SLAB.get());

                        output.accept(UP2Blocks.MESONITE.get());
                        output.accept(UP2Blocks.MESONITE_STAIRS.get());
                        output.accept(UP2Blocks.MESONITE_SLAB.get());

                        output.accept(UP2Blocks.FLORALITE.get());
                        output.accept(UP2Blocks.FLORALITE_STAIRS.get());
                        output.accept(UP2Blocks.FLORALITE_SLAB.get());

                        output.accept(UP2Blocks.PETRIFIED_BUSH.get());
                        output.accept(UP2Blocks.PETRIFIED_LOG.get());
                        output.accept(UP2Blocks.PETRIFIED_WOOD.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_SLAB.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_BUTTON.get());

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

                        output.accept(UP2Blocks.REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.TINTED_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.WHITE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.LIGHT_GRAY_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.GRAY_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.BLACK_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.BROWN_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.RED_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.ORANGE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.YELLOW_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.LIME_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.GREEN_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.CYAN_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.LIGHT_BLUE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.BLUE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.PURPLE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.MAGENTA_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.PINK_REINFORCED_GLASS.get());

                        output.accept(BABY_AEGIROCASSIS_BUCKET.get());
                        output.accept(COELACANTHUS_BUCKET.get());
                        output.accept(DIPLOCAULUS_BUCKET.get());
                        output.accept(DUNKLEOSTEUS_BUCKET.get());
                        output.accept(JAWLESS_FISH_BUCKET.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
                        output.accept(LOBE_FINNED_FISH_BUCKET.get());
                        output.accept(PRAEPUSA_BUCKET.get());
                        output.accept(PTERODACTYLUS_POT.get());
                        output.accept(STETHACANTHUS_BUCKET.get());
                        output.accept(DIRT_ON_A_STICK.get());

                        output.accept(DOOMSURF_DISC.get());
                        output.accept(PUMMEL_AND_SNATCH_DISC.get());
                        output.accept(TARIFYING_DISC.get());
                        output.accept(MEGALANIA_DISC.get());

                        output.accept(OOZE_BANNER_PATTERN.get());
                        output.accept(PALEOZOIC_BANNER_PATTERN.get());
                        output.accept(MESOZOIC_BANNER_PATTERN.get());
                        output.accept(CENOZOIC_BANNER_PATTERN.get());

                        output.accept(BRISTLE_FOSSIL.get()); // brachiosaurus
                        output.accept(ARM_FOSSIL.get()); // brachiosaurus
                        output.accept(FURY_FOSSIL.get()); // carnotaurus
                        output.accept(GLUTTONOUS_FOSSIL.get()); // coelacanthus
                        output.accept(FLAT_BACK_FOSSIL.get()); // desmatosuchus
                        output.accept(BOOMERANG_FOSSIL.get()); // diplocaulus
                        output.accept(RUNNER_FOSSIL.get()); // dromaeosaurus
                        output.accept(GUILLOTINE_FOSSIL.get()); // dunkleosteus
                        output.accept(PLOW_FOSSIL.get()); // hibbertopterus
                        output.accept(JAWLESS_FOSSIL.get()); // jawless fish
                        output.accept(BOAR_TOOTH_FOSSIL.get()); // kaprosuchus
                        output.accept(PRICKLY_FOSSIL.get()); // kentrosaurus
                        output.accept(IMPERATIVE_FOSSIL.get()); // kimmeridgebrachypteraeschnidium
                        output.accept(TRUNK_MOUSE_FOSSIL.get()); // leptictidium
                        output.accept(FISH_FOSSIL.get()); // lobe finned fish
                        output.accept(IMPERVIOUS_FOSSIL.get()); // lystrosaurus
                        output.accept(RUGOSE_FOSSIL.get()); // majungasaurus
                        output.accept(THERMAL_FOSSIL.get()); // megalania
                        output.accept(MELTDOWN_FOSSIL.get()); // metriorhynchus
                        output.accept(SURGE_FOSSIL.get()); // mosasaurus
                        output.accept(SAW_FOSSIL.get()); // onchopristis
                        output.accept(CRANIUM_FOSSIL.get()); // pachycephalosaurus
                        output.accept(FLIPPER_FOSSIL.get()); // praepusa
                        output.accept(CROOKED_BEAK_FOSSIL.get()); // desmatosuchus
                        output.accept(WING_FOSSIL.get()); // pterodactylus
                        output.accept(ANVIL_FOSSIL.get()); // stethacanthus
                        output.accept(AGED_FEATHER.get()); // talpanas
                        output.accept(MOSSY_FOSSIL.get()); // tartuosteus
                        output.accept(PLUMAGE_FOSSIL.get()); // telecrex
                        output.accept(DUBIOUS_FOSSIL.get()); // ulughbegsaurus

                        output.accept(UP2Blocks.AEGIROCASSIS_EGGS.get());
                        output.accept(UP2Blocks.BRACHIOSAURUS_EGG.get());
                        output.accept(UP2Blocks.CARNOTAURUS_EGG.get());
                        output.accept(UP2Blocks.COELACANTHUS_ROE.get());
                        output.accept(UP2Blocks.DESMATOSUCHUS_EGG.get());
                        output.accept(UP2Blocks.DIPLOCAULUS_EGGS.get());
                        output.accept(DROMAEOSAURUS_EGG.get());
                        output.accept(UP2Blocks.DUNKLEOSTEUS_SAC.get());
                        output.accept(UP2Blocks.HIBBERTOPTERUS_EGGS.get());
                        output.accept(UP2Blocks.JAWLESS_FISH_ROE.get());
                        output.accept(UP2Blocks.KAPROSUCHUS_EGG.get());
                        output.accept(UP2Blocks.KENTROSAURUS_EGG.get());
                        output.accept(UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
                        output.accept(LEPTICTIDIUM_EMBRYO.get());
                        output.accept(UP2Blocks.LOBE_FINNED_FISH_ROE.get());
                        output.accept(UP2Blocks.LYSTROSAURUS_EGG.get());
                        output.accept(UP2Blocks.MAJUNGASAURUS_EGG.get());
                        output.accept(UP2Blocks.MEGALANIA_EGG.get());
                        output.accept(METRIORHYNCHUS_EMBRYO.get());
                        output.accept(MOSASAURUS_EMBRYO.get());
                        output.accept(UP2Blocks.ONCHOPRISTIS_SAC.get());
                        output.accept(UP2Blocks.PACHYCEPHALOSAURUS_EGG.get());
                        output.accept(PRAEPUSA_EMBRYO.get());
                        output.accept(PSILOPTERUS_EGG.get());
                        output.accept(PTERODACTYLUS_EGG.get());
                        output.accept(UP2Blocks.STETHACANTHUS_SAC.get());
                        output.accept(TALPANAS_EGG.get());
                        output.accept(UP2Blocks.TARTUOSTEUS_ROE.get());
                        output.accept(TELECREX_EGG.get());
                        output.accept(UP2Blocks.ULUGHBEGSAURUS_EGG.get());

                        output.accept(AETHOPHYLLUM_FOSSIL.get());
                        output.accept(BENNETTITALES_FOSSIL.get());
                        output.accept(BRACHYPHYLLUM_FOSSIL.get());
                        output.accept(CALAMOPHYTON_FOSSIL.get());
                        output.accept(CLADOPHLEBIS_FOSSIL.get());
                        output.accept(COOKSONIA_FOSSIL.get());
                        output.accept(CYCAD_FOSSIL.get());
                        output.accept(GUANGDEDENDRON_FOSSIL.get());
                        output.accept(LEEFRUCTUS_FOSSIL.get());
                        output.accept(NEOMARIOPTERIS_FOSSIL.get());
                        output.accept(PROTOTAXITES_FOSSIL.get());
                        output.accept(QUILLWORT_FOSSIL.get());
                        output.accept(RAIGUENRAYUN_FOSSIL.get());
                        output.accept(RHYNIA_FOSSIL.get());
                        output.accept(TEMPSKYA_FOSSIL.get());
                        output.accept(DRYOPHYLLUM_FOSSIL.get());
                        output.accept(GINKGO_FOSSIL.get());
                        output.accept(LEPIDODENDRON_FOSSIL.get());
                        output.accept(METASEQUOIA_FOSSIL.get());

                        // plants
                        output.accept(UP2Blocks.AETHOPHYLLUM.get());
                        output.accept(UP2Blocks.ARCHAEOSIGILLARIA.get());
                        output.accept(UP2Blocks.BENNETTITALES.get());
                        output.accept(UP2Blocks.BRACHYPHYLLUM.get());
                        output.accept(UP2Blocks.CALAMOPHYTON.get());
                        output.accept(UP2Blocks.CLADOPHLEBIS.get());
                        output.accept(UP2Blocks.COOKSONIA.get());
                        output.accept(UP2Blocks.DELITZSCHALA_FERN.get());
                        output.accept(UP2Blocks.HORSETAIL.get());
                        output.accept(UP2Blocks.LARGE_HORSETAIL.get());
                        output.accept(UP2Blocks.LEEFRUCTUS.get());
                        output.accept(UP2Blocks.NEOMARIOPTERIS.get());
                        output.accept(UP2Blocks.QUILLWORT.get());
                        output.accept(UP2Blocks.RAIGUENRAYUN.get());
                        output.accept(UP2Blocks.RHYNIA.get());
                        output.accept(UP2Blocks.TEMPSKYA.get());
                        output.accept(UP2Blocks.ZHANGSOLVA_BLOOM.get());
                        output.accept(UP2Blocks.MOSS_LAYER.get());
                        output.accept(UP2Blocks.MOSSY_DIRT.get());

                        // fungus
                        output.accept(UP2Blocks.PROTOTAXITES_CLUSTER.get());
                        output.accept(UP2Blocks.PROTOTAXITES.get());
                        output.accept(UP2Blocks.PROTOTAXITES_NUB.get());
                        output.accept(UP2Blocks.LARGE_PROTOTAXITES_NUB.get());

                        // trees
                        output.accept(UP2Blocks.CYCAD_SEEDLING.get());
                        output.accept(UP2Blocks.CYCAD_STEM.get());
                        output.accept(UP2Blocks.CYCAD_CROWN.get());

                        output.accept(UP2Blocks.GUANGDEDENDRON.get());

                        output.accept(UP2Blocks.DRYOPHYLLUM_LOG.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_WOOD.get());
                        output.accept(UP2Blocks.STRIPPED_DRYOPHYLLUM_LOG.get());
                        output.accept(UP2Blocks.STRIPPED_DRYOPHYLLUM_WOOD.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_PLANKS.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_STAIRS.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_SLAB.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_FENCE.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_FENCE_GATE.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_DOOR.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_TRAPDOOR.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_BUTTON.get());
                        output.accept(DRYOPHYLLUM_SIGN.get());
                        output.accept(DRYOPHYLLUM_HANGING_SIGN.get());
                        output.accept(DRYOPHYLLUM_BOAT.get());
                        output.accept(DRYOPHYLLUM_CHEST_BOAT.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_LEAVES.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_SAPLING.get());

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

                        output.accept(UP2Blocks.METASEQUOIA_LOG.get());
                        output.accept(UP2Blocks.METASEQUOIA_WOOD.get());
                        output.accept(UP2Blocks.STRIPPED_METASEQUOIA_LOG.get());
                        output.accept(UP2Blocks.STRIPPED_METASEQUOIA_WOOD.get());
                        output.accept(UP2Blocks.METASEQUOIA_PLANKS.get());
                        output.accept(UP2Blocks.METASEQUOIA_STAIRS.get());
                        output.accept(UP2Blocks.METASEQUOIA_SLAB.get());
                        output.accept(UP2Blocks.METASEQUOIA_FENCE.get());
                        output.accept(UP2Blocks.METASEQUOIA_FENCE_GATE.get());
                        output.accept(UP2Blocks.METASEQUOIA_DOOR.get());
                        output.accept(UP2Blocks.METASEQUOIA_TRAPDOOR.get());
                        output.accept(UP2Blocks.METASEQUOIA_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.METASEQUOIA_BUTTON.get());
                        output.accept(METASEQUOIA_SIGN.get());
                        output.accept(METASEQUOIA_HANGING_SIGN.get());
                        output.accept(METASEQUOIA_BOAT.get());
                        output.accept(METASEQUOIA_CHEST_BOAT.get());
                        output.accept(UP2Blocks.METASEQUOIA_LEAVES.get());
                        output.accept(UP2Blocks.METASEQUOIA_SAPLING.get());

                    }).build());
}