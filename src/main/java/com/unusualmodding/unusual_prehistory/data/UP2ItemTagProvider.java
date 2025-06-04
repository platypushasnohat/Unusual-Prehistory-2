package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemTagProvider extends ItemTagsProvider {

    public UP2ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // unusual prehistory
        this.tag(UP2ItemTags.FOSSILS).add(
                PALEOZOIC_FOSSIL.get(),
                MESOZOIC_FOSSIL.get(),
                FROZEN_MEAT.get()
        );

        this.tag(UP2ItemTags.ORGANIC_OOZE).add(
                ORGANIC_OOZE.get()
        );

        this.tag(UP2ItemTags.DNA_BOTTLES).addTag(UP2ItemTags.PALEOZOIC_DNA_BOTTLES).addTag(UP2ItemTags.MESOZOIC_DNA_BOTTLES).addTag(UP2ItemTags.CENOZOIC_DNA_BOTTLES).addTag(UP2ItemTags.PLANT_DNA_BOTTLES).addTag(UP2ItemTags.WATER_PLANT_DNA_BOTTLES);

        this.tag(UP2ItemTags.PALEOZOIC_DNA_BOTTLES).add(
                DIPLOCAULUS_DNA.get(),
                DUNKLEOSTEUS_DNA.get(),
                JAWLESS_FISH_DNA.get(),
                SCAUMENACIA_DNA.get(),
                STETHACANTHUS_DNA.get()
        );

        this.tag(UP2ItemTags.MESOZOIC_DNA_BOTTLES).add(
                CARNOTAURUS_DNA.get(),
                KENTROSAURUS_DNA.get(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA.get(),
                MAJUNGASAURUS_DNA.get()
        );

        this.tag(UP2ItemTags.CENOZOIC_DNA_BOTTLES).add(
                TELECREX_DNA.get(),
                MEGALANIA_DNA.get()
        );

        this.tag(UP2ItemTags.PLANT_DNA_BOTTLES).add(
                ARCHAEOSIGILLARIA_DNA.get(),
                BENNETTITALES_DNA.get(),
                CALAMOPHYTON_DNA.get(),
                CLADOPHLEBIS_DNA.get(),
                GINKGO_DNA.get(),
                HORSETAIL_DNA.get(),
                ISOETES_DNA.get(),
                LEEFRUCTUS_DNA.get(),
                RAIGUENRAYUN_DNA.get(),
                SARRACENIA_DNA.get()
        );

        this.tag(UP2ItemTags.WATER_PLANT_DNA_BOTTLES).add(
                ANOSTYLOSTROMA_DNA.get(),
                ARCHAEFRUCTUS_DNA.get(),
                CLATHRODICTYON_CORAL_DNA.get(),
                NELUMBITES_DNA.get(),
                QUEREUXIA_DNA.get()
        );

        this.tag(UP2ItemTags.EXTRACTOR_INPUT).addTag(UP2ItemTags.FOSSILS);

        this.tag(UP2ItemTags.CULTIVATOR_FUEL).addTag(UP2ItemTags.ORGANIC_OOZE);

        this.tag(UP2ItemTags.DUNKLEOSTEUS_FOOD).addTag(ItemTags.FISHES);

        this.tag(UP2ItemTags.PACIFIES_DUNKLEOSTEUS).add(Items.GOLDEN_APPLE);

        this.tag(UP2ItemTags.KENTROSAURUS_FOOD).add(
                Items.MELON_SLICE,
                Items.APPLE,
                Items.CARROT,
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(UP2ItemTags.STETHACANTHUS_FOOD).addTag(ItemTags.FISHES);

        this.tag(UP2ItemTags.PACIFIES_STETHACANTHUS).add(Items.GOLDEN_APPLE);

        this.tag(UP2ItemTags.UNICORN_FOOD).add(
                Items.COOKIE,
                Items.PUMPKIN_PIE,
                Items.CAKE
        );

        this.copy(UP2BlockTags.GINKGO_LOGS, UP2ItemTags.GINKGO_LOGS);

        // minecraft
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
//        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
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

        // forge
        this.tag(UP2ItemTags.FRUITS).addTag(UP2ItemTags.FRUITS_GINKGO);
        this.tag(UP2ItemTags.FRUITS_GINKGO).add(GINKGO_FRUIT.get());
        this.tag(UP2ItemTags.BOTTLES).addTag(UP2ItemTags.BOTTLES_DNA);
        this.tag(UP2ItemTags.BOTTLES_DNA).addTag(UP2ItemTags.DNA_BOTTLES);
    }
}
