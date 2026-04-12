package com.barlinc.unusual_prehistory.datagen.server;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemTagProvider extends ItemTagsProvider {

    public UP2ItemTagProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, UnusualPrehistory2.MOD_ID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@NotNull Provider provider) {

        // Update 1
        this.tag(UP2ItemTags.CARNOTAURUS_FOOD).add(
                Items.BEEF, Items.COOKED_BEEF,
                Items.PORKCHOP, Items.COOKED_PORKCHOP,
                Items.MUTTON, Items.COOKED_MUTTON
        );
        this.tag(UP2ItemTags.DIPLOCAULUS_FOOD).add(
                Items.SPIDER_EYE
        );
        this.tag(UP2ItemTags.DROMAEOSAURUS_FOOD).add(
                Items.CHICKEN,
                Items.COOKED_CHICKEN
        );
        this.tag(UP2ItemTags.DUNKLEOSTEUS_FOOD).add(
                Items.COD,
                Items.COOKED_COD,
                Items.SALMON,
                Items.COOKED_SALMON,
                Items.TROPICAL_FISH
        );
        this.tag(UP2ItemTags.JAWLESS_FISH_FOOD).add(
                Blocks.MOSS_BLOCK.asItem()
        );
        this.tag(UP2ItemTags.KENTROSAURUS_FOOD).add(
                Items.CACTUS,
                Items.SWEET_BERRIES
        );
        this.tag(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD).add(
                Items.SPIDER_EYE
        );
        this.tag(UP2ItemTags.MAJUNGASAURUS_FOOD).add(
                Items.BEEF, Items.COOKED_BEEF,
                Items.PORKCHOP, Items.COOKED_PORKCHOP,
                Items.MUTTON, Items.COOKED_MUTTON
        );
        this.tag(UP2ItemTags.MEGALANIA_FOOD).add(
                Items.BEEF, Items.COOKED_BEEF,
                Items.PORKCHOP, Items.COOKED_PORKCHOP,
                Items.MUTTON, Items.COOKED_MUTTON
        );
        this.tag(UP2ItemTags.STETHACANTHUS_FOOD).add(
                Items.TROPICAL_FISH
        );
        this.tag(UP2ItemTags.TALPANAS_FOOD).add(
                Blocks.HANGING_ROOTS.asItem(),
                Items.SWEET_BERRIES
        );
        this.tag(UP2ItemTags.TELECREX_FOOD).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );
        this.tag(UP2ItemTags.UNICORN_FOOD).add(
                Items.CAKE
        );

        this.tag(UP2ItemTags.TAMES_MEGALANIA).add(
                Items.ROTTEN_FLESH
        );

        this.tag(UP2ItemTags.TRANSMOGRIFIER_FUEL).add(
                ORGANIC_OOZE.get()
        );

        this.tag(UP2ItemTags.PERMANENTLY_PACIFIES_MOB).add(Items.ENCHANTED_GOLDEN_APPLE);

        this.tag(UP2ItemTags.PACIFIES_CARNOTAURUS).addTag(UP2ItemTags.CARNOTAURUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_DROMAEOSAURUS).addTag(UP2ItemTags.DROMAEOSAURUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_DUNKLEOSTEUS).addTag(UP2ItemTags.DUNKLEOSTEUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_MAJUNGASAURUS).addTag(UP2ItemTags.MAJUNGASAURUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_MEGALANIA).addTag(UP2ItemTags.MEGALANIA_FOOD);
        this.tag(UP2ItemTags.PACIFIES_STETHACANTHUS).addTag(UP2ItemTags.STETHACANTHUS_FOOD);

        this.copy(UP2BlockTags.GINKGO_LOGS, UP2ItemTags.GINKGO_LOGS);
        this.copy(UP2BlockTags.LEPIDODENDRON_LOGS, UP2ItemTags.LEPIDODENDRON_LOGS);

        this.copy(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS, UP2ItemTags.DIPLOCAULUS_SLIDING_BLOCKS);
        this.copy(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS, UP2ItemTags.DIPLOCAULUS_BURROWING_BLOCKS);

        this.copy(UP2BlockTags.JAWLESS_FISH_NIBBLING_BLOCKS, UP2ItemTags.JAWLESS_FISH_NIBBLING_BLOCKS);

        this.copy(UP2BlockTags.DESMATOSUCHUS_GRAZING_BLOCKS, UP2ItemTags.DESMATOSUCHUS_GRAZING_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_BURROWING_BLOCKS, UP2ItemTags.DESMATOSUCHUS_BURROWING_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS, UP2ItemTags.DESMATOSUCHUS_ROLLING_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS, UP2ItemTags.DESMATOSUCHUS_MUDDY_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS, UP2ItemTags.DESMATOSUCHUS_SNOWY_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS, UP2ItemTags.DESMATOSUCHUS_MOSSY_BLOCKS);

        this.tag(UP2ItemTags.GUARDED_BY_KENTROSAURUS).add(Blocks.CACTUS.asItem(), Items.SWEET_BERRIES);

        this.tag(UP2ItemTags.FRUITS).addTag(UP2ItemTags.FRUITS_GINKGO);
        this.tag(UP2ItemTags.FRUITS_GINKGO).add(GINKGO_FRUIT.get());

        // Update 2
        this.tag(UP2ItemTags.ONCHOPRISTIS_FOOD).add(
                Items.TROPICAL_FISH,
                Items.COOKED_COD, Items.COD,
                Items.SALMON, Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.PACIFIES_ONCHOPRISTIS).addTag(UP2ItemTags.ONCHOPRISTIS_FOOD);

        // Update 3
        this.tag(UP2ItemTags.METRIORHYNCHUS_FOOD).add(
                Items.TROPICAL_FISH,
                Items.COOKED_COD, Items.COD,
                Items.SALMON, Items.COOKED_SALMON
        );
        this.tag(UP2ItemTags.TARTUOSTEUS_FOOD).add(
                Blocks.MOSS_BLOCK.asItem()
        );

        this.tag(UP2ItemTags.PACIFIES_METRIORHYNCHUS).addTag(UP2ItemTags.METRIORHYNCHUS_FOOD);

        this.tag(UP2ItemTags.TAMES_METRIORHYNCHUS).add(Items.TROPICAL_FISH_BUCKET);

        this.copy(UP2BlockTags.TARTUOSTEUS_NIBBLING_BLOCKS, UP2ItemTags.TARTUOSTEUS_NIBBLING_BLOCKS);

        // Update 4
        this.tag(UP2ItemTags.BRACHIOSAURUS_FOOD).addTag(ItemTags.LEAVES).add(
                Blocks.KELP.asItem(),
                Blocks.SEAGRASS.asItem()
        );
        this.tag(UP2ItemTags.COELACANTHUS_FOOD).add(
                Items.PUMPKIN_PIE
        );
        this.tag(UP2ItemTags.HIBBERTOPTERUS_FOOD).add(
                Blocks.DIRT.asItem(),
                Blocks.COARSE_DIRT.asItem(),
                Blocks.PODZOL.asItem(),
                Blocks.FARMLAND.asItem()
        );
        this.tag(UP2ItemTags.KAPROSUCHUS_FOOD).add(
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP
        );
        this.tag(UP2ItemTags.LEPTICTIDIUM_FOOD).add(
                Items.SPIDER_EYE
        );
        this.tag(UP2ItemTags.LOBE_FINNED_FISH_FOOD).add(
                Blocks.SEAGRASS.asItem()
        );
        this.tag(UP2ItemTags.LYSTROSAURUS_FOOD).add(
                UP2Blocks.HORSETAIL.get().asItem()
        );
        this.tag(UP2ItemTags.PACHYCEPHALOSAURUS_FOOD).add(
                Items.SWEET_BERRIES,
                Items.APPLE,
                Items.MELON
        );
        this.tag(UP2ItemTags.PRAEPUSA_FOOD).add(
                Items.COD,
                Items.SALMON,
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );
        this.tag(UP2ItemTags.PTERODACTYLUS_FOOD).add(
                Items.APPLE,
                Items.SWEET_BERRIES,
                Items.MELON_SLICE
        );
        this.tag(UP2ItemTags.ULUGHBEGSAURUS_FOOD).add(
                Items.MUTTON,
                Items.BEEF,
                Items.PORKCHOP,
                Items.COOKED_MUTTON,
                Items.COOKED_BEEF,
                Items.COOKED_PORKCHOP
        );

        this.tag(UP2ItemTags.TAMES_KAPROSUCHUS).add(Items.BONE);
        this.tag(UP2ItemTags.TAMES_ULUGHBEGSAURUS).addTag(UP2ItemTags.ULUGHBEGSAURUS_FOOD);

        // Update 5
        this.tag(UP2ItemTags.AEGIROCASSIS_FOOD).add(Items.TROPICAL_FISH);
        this.tag(UP2ItemTags.DESMATOSUCHUS_FOOD).add(
                Items.SPIDER_EYE,
                Items.EGG,
                Items.CARROT,
                Items.POTATO,
                Items.BAKED_POTATO,
                Items.BEETROOT,
                Items.SALMON,
                Items.COOKED_SALMON,
                Blocks.BROWN_MUSHROOM.asItem(),
                Blocks.RED_MUSHROOM.asItem()
        );
        this.tag(UP2ItemTags.PSILOPTERUS_FOOD).add(
                Items.RABBIT,
                Items.MUTTON,
                Items.BEEF,
                Items.CHICKEN,
                Items.PORKCHOP,
                Items.COOKED_RABBIT,
                Items.COOKED_MUTTON,
                Items.COOKED_BEEF,
                Items.COOKED_CHICKEN,
                Items.COOKED_PORKCHOP
        );

        this.tag(UP2ItemTags.PACIFIES_COELACANTHUS).addTag(UP2ItemTags.COELACANTHUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_KAPROSUCHUS).addTag(UP2ItemTags.KAPROSUCHUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_PRAEPUSA).addTag(UP2ItemTags.PRAEPUSA_FOOD);
        this.tag(UP2ItemTags.PACIFIES_ULUGHBEGSAURUS).addTag(UP2ItemTags.ULUGHBEGSAURUS_FOOD);

        this.tag(UP2ItemTags.AMBER_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "amber_dye"));
        this.tag(UP2ItemTags.AQUA_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "aqua_dye"));
        this.tag(UP2ItemTags.BEIGE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "beige_dye"));
        this.tag(UP2ItemTags.CORAL_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "coral_dye"));
        this.tag(UP2ItemTags.FOREST_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "forest_dye"));
        this.tag(UP2ItemTags.GINGER_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "ginger_dye"));
        this.tag(UP2ItemTags.INDIGO_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "indigo_dye"));
        this.tag(UP2ItemTags.MAROON_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "maroon_dye"));
        this.tag(UP2ItemTags.MINT_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "mint_dye"));
        this.tag(UP2ItemTags.NAVY_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "navy_dye"));
        this.tag(UP2ItemTags.OLIVE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "olive_dye"));
        this.tag(UP2ItemTags.ROSE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "rose_dye"));
        this.tag(UP2ItemTags.SLATE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "slate_dye"));
        this.tag(UP2ItemTags.TAN_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "tan_dye"));
        this.tag(UP2ItemTags.TEAL_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "teal_dye"));
        this.tag(UP2ItemTags.VERDANT_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "verdant_dye"));

        this.tag(UP2ItemTags.STOPS_MOB_AGING).add(Items.POISONOUS_POTATO);

        this.copy(UP2BlockTags.DRYOPHYLLUM_LOGS, UP2ItemTags.DRYOPHYLLUM_LOGS);
        this.copy(UP2BlockTags.METASEQUOIA_LOGS, UP2ItemTags.METASEQUOIA_LOGS);

        // Update 5
        this.tag(UP2ItemTags.SNOW).add(
                Items.SNOWBALL,
                Blocks.SNOW.asItem(),
                Blocks.SNOW_BLOCK.asItem()
        );
        this.tag(UP2ItemTags.MOSS).add(
                UP2Blocks.MOSS_LAYER.get().asItem(),
                Blocks.MOSS_BLOCK.asItem(),
                Blocks.MOSS_CARPET.asItem()
        );
        this.tag(UP2ItemTags.MUD).add(
                Blocks.MUD.asItem()
        );

        this.tag(UP2ItemTags.PALEOZOIC_FOSSILS).add(
                BRISTLE_FOSSIL.get(),
                GUILLOTINE_FOSSIL.get(),
                JAWLESS_FOSSIL.get(),
                ANVIL_FOSSIL.get(),
                MOSSY_FOSSIL.get(),
                BOOMERANG_FOSSIL.get(),
                PLOW_FOSSIL.get(),
                FISH_FOSSIL.get(),
                GLUTTONOUS_FOSSIL.get(),
                IMPERVIOUS_FOSSIL.get(),
                ROTUND_FOSSIL.get()
        );
        this.tag(UP2ItemTags.MESOZOIC_FOSSILS).add(
                IMPERVIOUS_FOSSIL.get(),
                FLAT_BACK_FOSSIL.get(),
                ARM_FOSSIL.get(),
                PRICKLY_FOSSIL.get(),
                IMPERATIVE_FOSSIL.get(),
                MELTDOWN_FOSSIL.get(),
                WING_FOSSIL.get(),
                FURY_FOSSIL.get(),
                RUNNER_FOSSIL.get(),
                BOAR_TOOTH_FOSSIL.get(),
                RUGOSE_FOSSIL.get(),
                SURGE_FOSSIL.get(),
                SAW_FOSSIL.get(),
                CRANIUM_FOSSIL.get(),
                DUBIOUS_FOSSIL.get()
        );
        this.tag(UP2ItemTags.CENOZOIC_FOSSILS).add(
                TRUNK_MOUSE_FOSSIL.get(),
                CROOKED_BEAK_FOSSIL.get(),
                PLUMAGE_FOSSIL.get(),
                FLIPPER_FOSSIL.get(),
                THERMAL_FOSSIL.get(),
                AGED_FEATHER.get()
        );
        this.tag(UP2ItemTags.PLANT_FOSSILS).add(
                BENNETTITALES_FOSSIL.get(),
                CALAMOPHYTON_FOSSIL.get(),
                DELITZSCHALA_FOSSIL.get(),
                COOKSONIA_FOSSIL.get(),
                QUILLWORT_FOSSIL.get(),
                RAIGUENRAYUN_FOSSIL.get(),
                RHYNIA_FOSSIL.get(),
                GINKGO_FOSSIL.get(),
                LEPIDODENDRON_FOSSIL.get(),
                AETHOPHYLLUM_FOSSIL.get(),
                BRACHYPHYLLUM_FOSSIL.get(),
                CYCAD_FOSSIL.get(),
                GUANGDEDENDRON_FOSSIL.get(),
                ZHANGSOLVA_FOSSIL.get(),
                PROTOTAXITES_FOSSIL.get(),
                METASEQUOIA_FOSSIL.get(),
                DRYOPHYLLUM_FOSSIL.get()
        );

        this.tag(UP2ItemTags.MOB_FOSSILS)
                .addTag(UP2ItemTags.PALEOZOIC_FOSSILS)
                .addTag(UP2ItemTags.MESOZOIC_FOSSILS)
                .addTag(UP2ItemTags.CENOZOIC_FOSSILS);

        this.tag(UP2ItemTags.FOSSILS)
                .addTag(UP2ItemTags.MOB_FOSSILS)
                .addTag(UP2ItemTags.PLANT_FOSSILS);

        this.tag(UP2ItemTags.PALEOZOIC_EGGS).add(
                UP2Blocks.AEGIROCASSIS_EGGS.get().asItem(),
                UP2Blocks.COELACANTHUS_ROE.get().asItem(),
                UP2Blocks.DIPLOCAULUS_EGGS.get().asItem(),
                UP2Blocks.DUNKLEOSTEUS_SAC.get().asItem(),
                UP2Blocks.HIBBERTOPTERUS_EGGS.get().asItem(),
                UP2Blocks.JAWLESS_FISH_ROE.get().asItem(),
                UP2Blocks.LOBE_FINNED_FISH_ROE.get().asItem(),
                UP2Blocks.LYSTROSAURUS_EGG.get().asItem(),
                UP2Blocks.STETHACANTHUS_SAC.get().asItem(),
                UP2Blocks.TARTUOSTEUS_ROE.get().asItem()
        );
        this.tag(UP2ItemTags.MESOZOIC_EGGS).add(
                UP2Blocks.BRACHIOSAURUS_EGG.get().asItem(),
                UP2Blocks.CARNOTAURUS_EGG.get().asItem(),
                UP2Blocks.DESMATOSUCHUS_EGG.get().asItem(),
                DROMAEOSAURUS_EGG.get(),
                UP2Blocks.KAPROSUCHUS_EGG.get().asItem(),
                UP2Blocks.KENTROSAURUS_EGG.get().asItem(),
                UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get().asItem(),
                UP2Blocks.LYSTROSAURUS_EGG.get().asItem(),
                UP2Blocks.MAJUNGASAURUS_EGG.get().asItem(),
                METRIORHYNCHUS_EMBRYO.get(),
                PROGNATHODON_EMBRYO.get(),
                UP2Blocks.ONCHOPRISTIS_SAC.get().asItem(),
                UP2Blocks.PACHYCEPHALOSAURUS_EGG.get().asItem(),
                PTERODACTYLUS_EGG.get(),
                UP2Blocks.ULUGHBEGSAURUS_EGG.get().asItem()
        );
        this.tag(UP2ItemTags.CENOZOIC_EGGS).add(
                LEPTICTIDIUM_EMBRYO.get(),
                UP2Blocks.MEGALANIA_EGG.get().asItem(),
                PRAEPUSA_EMBRYO.get(),
                PSILOPTERUS_EGG.get(),
                TALPANAS_EGG.get(),
                TELECREX_EGG.get()
        );
        this.tag(UP2ItemTags.EGGS)
                .addTag(UP2ItemTags.PALEOZOIC_EGGS)
                .addTag(UP2ItemTags.MESOZOIC_EGGS)
                .addTag(UP2ItemTags.CENOZOIC_EGGS);

        this.copy(UP2BlockTags.ACCELERATES_EGG_HATCHING, UP2ItemTags.ACCELERATES_EGG_HATCHING);
        this.copy(UP2BlockTags.PREVENTS_EGG_HATCHING, UP2ItemTags.PREVENTS_EGG_HATCHING);

        // Update 6
        this.tag(UP2ItemTags.SWEET_COTYLORHYNCHUS_FOOD).add(
                Items.SWEET_BERRIES
        );
        this.tag(UP2ItemTags.STINKY_COTYLORHYNCHUS_FOOD).add(
                GINKGO_FRUIT.get()
        );
        this.tag(UP2ItemTags.COTYLORHYNCHUS_FOOD).addTags(
                UP2ItemTags.STINKY_COTYLORHYNCHUS_FOOD,
                UP2ItemTags.SWEET_COTYLORHYNCHUS_FOOD
        );

        this.tag(UP2ItemTags.PROGNATHODON_FOOD).add(
                Items.COD,
                Items.COOKED_COD,
                Items.SALMON,
                Items.COOKED_SALMON,
                Items.TROPICAL_FISH,
                Items.PUFFERFISH
        );

        this.tag(UP2ItemTags.PACIFIES_PROGNATHODON).addTag(UP2ItemTags.PROGNATHODON_FOOD);

        // minecraft
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
//        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
//        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
//        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
//        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
//        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
//        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
//        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
//        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
//        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
//        this.copy(Tags.Blocks.FENCE_GATES, Tags.Items.FENCE_GATES);
//        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
//        this.copy(BlockTags.SLABS, ItemTags.SLABS);
//        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS);

        this.copy(UP2BlockTags.REINFORCED_GLASS, UP2ItemTags.REINFORCED_GLASS);

        this.tag(Tags.Items.GLASS_BLOCKS).addTag(UP2ItemTags.REINFORCED_GLASS);

        this.tag(ItemTags.SIGNS).add(
                DRYOPHYLLUM_SIGN.get(),
                GINKGO_SIGN.get(),
                LEPIDODENDRON_SIGN.get(),
                METASEQUOIA_SIGN.get()
        );

        this.tag(ItemTags.HANGING_SIGNS).add(
                DRYOPHYLLUM_HANGING_SIGN.get(),
                GINKGO_HANGING_SIGN.get(),
                LEPIDODENDRON_HANGING_SIGN.get(),
                METASEQUOIA_HANGING_SIGN.get()
        );

        this.tag(ItemTags.BOATS).add(
                DRYOPHYLLUM_BOAT.get(),
                GINKGO_BOAT.get(),
                LEPIDODENDRON_BOAT.get(),
                METASEQUOIA_BOAT.get()
        );

        this.tag(ItemTags.CHEST_BOATS).add(
                DRYOPHYLLUM_CHEST_BOAT.get(),
                GINKGO_CHEST_BOAT.get(),
                LEPIDODENDRON_CHEST_BOAT.get(),
                METASEQUOIA_CHEST_BOAT.get()
        );

//        this.tag(ItemTags.MUSIC_DISCS).add(
//                DOOMSURF_DISC.get(),
//                MEGALANIA_DISC.get(),
//                TARIFYING_DISC.get(),
//                PUMMEL_AND_SNATCH_DISC.get()
//        );

        this.tag(ItemTags.LECTERN_BOOKS).add(
                PALEOPEDIA.get()
        );
    }
}
