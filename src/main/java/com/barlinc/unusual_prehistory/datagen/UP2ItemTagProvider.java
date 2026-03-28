package com.barlinc.unusual_prehistory.datagen;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemTagProvider extends ItemTagsProvider {

    public UP2ItemTagProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {

        this.tag(UP2ItemTags.TRANSMOGRIFIER_FUEL).add(
                ORGANIC_OOZE.get()
        );

        // Food
        this.tag(UP2ItemTags.RAW_MEATS).add(
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.MUTTON,
                Items.RABBIT);

        this.tag(UP2ItemTags.COOKED_MEATS).add(
                Items.COOKED_BEEF,
                Items.COOKED_PORKCHOP,
                Items.COOKED_CHICKEN,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT);

        this.tag(UP2ItemTags.RAW_FISH).add(
                Items.COD,
                Items.SALMON,
                Items.TROPICAL_FISH);

        this.tag(UP2ItemTags.COOKED_FISH).add(
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.BRACHIOSAURUS_FOOD).addTag(ItemTags.LEAVES).add(
                Blocks.KELP.asItem(),
                Blocks.SEAGRASS.asItem()
        );

        this.tag(UP2ItemTags.AEGIROCASSIS_FOOD).add(
                Items.TROPICAL_FISH
        );

        this.tag(UP2ItemTags.CARNOTAURUS_FOOD).addTags(
                UP2ItemTags.RAW_MEATS,
                UP2ItemTags.COOKED_MEATS
        );

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

        this.tag(UP2ItemTags.DIPLOCAULUS_FOOD).add(
                Items.SPIDER_EYE
        );

        this.tag(UP2ItemTags.DROMAEOSAURUS_FOOD).add(
                Items.CHICKEN,
                Items.COOKED_CHICKEN
        );

        this.tag(UP2ItemTags.DUNKLEOSTEUS_FOOD).addTags(
                UP2ItemTags.RAW_FISH,
                UP2ItemTags.COOKED_FISH
        );

        this.tag(UP2ItemTags.HIBBERTOPTERUS_FOOD).add(
                Blocks.DIRT.asItem(),
                Blocks.COARSE_DIRT.asItem(),
                Blocks.PODZOL.asItem(),
                Blocks.FARMLAND.asItem()
        );

        this.tag(UP2ItemTags.JAWLESS_FISH_FOOD).add(
                Items.SEAGRASS
        );

        this.tag(UP2ItemTags.KAPROSUCHUS_FOOD).addTags(
                UP2ItemTags.RAW_MEATS,
                UP2ItemTags.COOKED_MEATS,
                UP2ItemTags.RAW_FISH,
                UP2ItemTags.COOKED_FISH
        );

        this.tag(UP2ItemTags.KENTROSAURUS_FOOD).add(
                Items.CACTUS,
                Items.SWEET_BERRIES
        );

        this.tag(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD).add(
                Items.SPIDER_EYE
        );

        this.tag(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD).addTags(
                UP2ItemTags.RAW_FISH,
                UP2ItemTags.COOKED_FISH
        );

        this.tag(UP2ItemTags.LEPTICTIDIUM_FOOD).add(
                Items.SWEET_BERRIES
        );

        this.tag(UP2ItemTags.LOBE_FINNED_FISH_FOOD).add(
                Blocks.SEAGRASS.asItem()
        );

        this.tag(UP2ItemTags.LYSTROSAURUS_FOOD).add(
                Blocks.GRASS.asItem()
        );

        this.tag(UP2ItemTags.MAJUNGASAURUS_FOOD).addTags(
                UP2ItemTags.RAW_MEATS,
                UP2ItemTags.COOKED_MEATS
        );

        this.tag(UP2ItemTags.MEGALANIA_FOOD).addTags(
                UP2ItemTags.RAW_MEATS,
                UP2ItemTags.COOKED_MEATS
        );

        this.tag(UP2ItemTags.METRIORHYNCHUS_FOOD).addTags(
                UP2ItemTags.RAW_FISH,
                UP2ItemTags.COOKED_FISH
        );

        this.tag(UP2ItemTags.ONCHOPRISTIS_FOOD).addTags(
                UP2ItemTags.RAW_FISH,
                UP2ItemTags.COOKED_FISH
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

        this.tag(UP2ItemTags.PTERODACTYLUS_FOOD).add(
                Items.APPLE,
                Items.SWEET_BERRIES,
                Items.MELON_SLICE
        );

        this.tag(UP2ItemTags.STETHACANTHUS_FOOD).addTags(
                UP2ItemTags.RAW_FISH,
                UP2ItemTags.COOKED_FISH
        );

        this.tag(UP2ItemTags.TALPANAS_FOOD).add(
                Blocks.HANGING_ROOTS.asItem(),
                Items.SWEET_BERRIES
        );

        this.tag(UP2ItemTags.TARTUOSTEUS_FOOD).add(
                Blocks.SEAGRASS.asItem(),
                Blocks.MOSS_BLOCK.asItem(),
                Blocks.MOSS_CARPET.asItem(),
                UP2Blocks.MOSS_LAYER.get().asItem()
        );

        this.tag(UP2ItemTags.TELECREX_FOOD).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(UP2ItemTags.ULUGHBEGSAURUS_FOOD).addTags(
                UP2ItemTags.RAW_MEATS,
                UP2ItemTags.COOKED_MEATS
        );

        this.tag(UP2ItemTags.UNICORN_FOOD).add(
                Items.CAKE
        );

        this.tag(UP2ItemTags.COELACANTHUS_FOOD).add(
                Items.PUMPKIN_PIE
        );

        // Taming
        this.tag(UP2ItemTags.TAMES_ULUGHBEGSAURUS).addTag(
                UP2ItemTags.ULUGHBEGSAURUS_FOOD
        );

        this.tag(UP2ItemTags.TAMES_KAPROSUCHUS).add(
                Items.BONE
        );

//        this.tag(UP2ItemTags.TAMES_MANIPULATOR).add(
//                Blocks.TORCHFLOWER.asItem()
//        );

        this.tag(UP2ItemTags.TAMES_MEGALANIA).add(
                Items.ROTTEN_FLESH
        );

        // Update 5
        this.tag(UP2ItemTags.MOSASAURUS_FOOD).add(
                Items.COD,
                Items.COOKED_COD,
                Items.SALMON,
                Items.COOKED_SALMON,
                Items.TROPICAL_FISH,
                Items.PUFFERFISH
        );

        this.tag(UP2ItemTags.PACIFIES_MOSASAURUS).add(
                Items.TROPICAL_FISH
        );

        // Pacifying
        this.tag(UP2ItemTags.PERMANENTLY_PACIFIES_MOB).add(Items.ENCHANTED_GOLDEN_APPLE);

        this.tag(UP2ItemTags.PACIFIES_CARNOTAURUS).addTag(UP2ItemTags.CARNOTAURUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_DROMAEOSAURUS).addTag(UP2ItemTags.DROMAEOSAURUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_DUNKLEOSTEUS).addTag(UP2ItemTags.DUNKLEOSTEUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_KAPROSUCHUS).addTag(UP2ItemTags.KAPROSUCHUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_MAJUNGASAURUS).addTag(UP2ItemTags.MAJUNGASAURUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_MEGALANIA).addTag(UP2ItemTags.MEGALANIA_FOOD);
        this.tag(UP2ItemTags.PACIFIES_METRIORHYNCHUS).addTag(UP2ItemTags.METRIORHYNCHUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_ONCHOPRISTIS).addTag(UP2ItemTags.ONCHOPRISTIS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_STETHACANTHUS).addTag(UP2ItemTags.STETHACANTHUS_FOOD);
        this.tag(UP2ItemTags.PACIFIES_COELACANTHUS).add(
                Items.PUFFERFISH
        );
        this.tag(UP2ItemTags.PACIFIES_PRAEPUSA).add(
                Items.TROPICAL_FISH
        );
        this.tag(UP2ItemTags.PACIFIES_ULUGHBEGSAURUS).addTag(UP2ItemTags.ULUGHBEGSAURUS_FOOD);

        // Dye Depot compat
        this.tag(UP2ItemTags.AMBER_DYES).addOptional(new ResourceLocation("dye_depot", "amber_dye"));
        this.tag(UP2ItemTags.AQUA_DYES).addOptional(new ResourceLocation("dye_depot", "aqua_dye"));
        this.tag(UP2ItemTags.BEIGE_DYES).addOptional(new ResourceLocation("dye_depot", "beige_dye"));
        this.tag(UP2ItemTags.CORAL_DYES).addOptional(new ResourceLocation("dye_depot", "coral_dye"));
        this.tag(UP2ItemTags.FOREST_DYES).addOptional(new ResourceLocation("dye_depot", "forest_dye"));
        this.tag(UP2ItemTags.GINGER_DYES).addOptional(new ResourceLocation("dye_depot", "ginger_dye"));
        this.tag(UP2ItemTags.INDIGO_DYES).addOptional(new ResourceLocation("dye_depot", "indigo_dye"));
        this.tag(UP2ItemTags.MAROON_DYES).addOptional(new ResourceLocation("dye_depot", "maroon_dye"));
        this.tag(UP2ItemTags.MINT_DYES).addOptional(new ResourceLocation("dye_depot", "mint_dye"));
        this.tag(UP2ItemTags.NAVY_DYES).addOptional(new ResourceLocation("dye_depot", "navy_dye"));
        this.tag(UP2ItemTags.OLIVE_DYES).addOptional(new ResourceLocation("dye_depot", "olive_dye"));
        this.tag(UP2ItemTags.ROSE_DYES).addOptional(new ResourceLocation("dye_depot", "rose_dye"));
        this.tag(UP2ItemTags.SLATE_DYES).addOptional(new ResourceLocation("dye_depot", "slate_dye"));
        this.tag(UP2ItemTags.TAN_DYES).addOptional(new ResourceLocation("dye_depot", "tan_dye"));
        this.tag(UP2ItemTags.TEAL_DYES).addOptional(new ResourceLocation("dye_depot", "teal_dye"));
        this.tag(UP2ItemTags.VERDANT_DYES).addOptional(new ResourceLocation("dye_depot", "verdant_dye"));

        // Misc
        this.tag(UP2ItemTags.STOPS_MOB_AGING).add(Items.POISONOUS_POTATO);

        this.copy(UP2BlockTags.DRYOPHYLLUM_LOGS, UP2ItemTags.DRYOPHYLLUM_LOGS);
        this.copy(UP2BlockTags.GINKGO_LOGS, UP2ItemTags.GINKGO_LOGS);
        this.copy(UP2BlockTags.LEPIDODENDRON_LOGS, UP2ItemTags.LEPIDODENDRON_LOGS);
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

        this.copy(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS, UP2ItemTags.DIPLOCAULUS_SLIDING_BLOCKS);
        this.copy(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS, UP2ItemTags.DIPLOCAULUS_BURROWING_BLOCKS);
        this.copy(UP2BlockTags.JAWLESS_FISH_NIBBLING_BLOCKS, UP2ItemTags.JAWLESS_FISH_NIBBLING_BLOCKS);
//        this.copy(UP2BlockTags.GUARDED_BY_KENTROSAURUS, UP2ItemTags.GUARDED_BY_KENTROSAURUS);
        this.tag(UP2ItemTags.GUARDED_BY_KENTROSAURUS).add(
                Blocks.CACTUS.asItem(),
                Items.SWEET_BERRIES
        );
        this.copy(UP2BlockTags.TARTUOSTEUS_NIBBLING_BLOCKS, UP2ItemTags.TARTUOSTEUS_NIBBLING_BLOCKS);

        // minecraft
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        this.copy(Tags.Blocks.FENCE_GATES, Tags.Items.FENCE_GATES);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS);

        this.copy(UP2BlockTags.REINFORCED_GLASS, UP2ItemTags.REINFORCED_GLASS);

        this.tag(Tags.Items.GLASS).addTag(UP2ItemTags.REINFORCED_GLASS);

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

        this.tag(ItemTags.MUSIC_DISCS).add(
                DOOMSURF_DISC.get(),
                MEGALANIA_DISC.get(),
                TARIFYING_DISC.get()
        );

        this.tag(ItemTags.LECTERN_BOOKS).add(
                PALEOPEDIA.get()
        );

        // Forge
        this.tag(UP2ItemTags.FRUITS).addTag(UP2ItemTags.FRUITS_GINKGO);
        this.tag(UP2ItemTags.FRUITS_GINKGO).add(GINKGO_FRUIT.get());
    }
}
