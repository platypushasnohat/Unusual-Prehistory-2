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
import net.minecraft.world.level.block.Blocks;
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

        this.tag(UP2ItemTags.FOSSILS).add(
                FURY_FOSSIL.get(),
                BOOMERANG_FOSSIL.get(),
                RUNNER_FOSSIL.get(),
                GUILLOTINE_FOSSIL.get(),
                JAWLESS_FOSSIL.get(),
                PRICKLY_FOSSIL.get(),
                IMPERATIVE_FOSSIL.get(),
                RUGOSE_FOSSIL.get(),
                THERMAL_FOSSIL.get(),
                ANVIL_FOSSIL.get(),
                PLUMAGE_FOSSIL.get(),
                BARK_FOSSIL.get(),
                BLOOMED_FOSSIL.get(),
                FAN_FOSSIL.get(),
                FIBROUS_FOSSIL.get(),
                FLOWERING_FOSSIL.get(),
                LEAFY_FOSSIL.get(),
                SAPLING_FOSSIL.get(),
                SPINDLY_FOSSIL.get(),
                SPORE_FOSSIL.get(),
                TRUNK_FOSSIL.get(),
                VASCULAR_FOSSIL.get()
        );

        this.tag(UP2ItemTags.TRANSMOGRIFIER_FUEL).add(
                ORGANIC_OOZE.get()
        );

        this.tag(UP2ItemTags.DROMAEOSAURUS_FOOD).add(
                Items.CHICKEN,
                Items.COOKED_CHICKEN
        );

        this.tag(UP2ItemTags.DUNKLEOSTEUS_FOOD).addTag(ItemTags.FISHES);

        this.tag(UP2ItemTags.PACIFIES_DUNKLEOSTEUS).add(Items.GOLDEN_APPLE);

        this.tag(UP2ItemTags.KENTROSAURUS_FOOD).add(
                Items.MELON_SLICE,
                Items.APPLE,
                Items.CARROT,
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(UP2ItemTags.MAJUNGASAURUS_FOOD).add(
                Items.MUTTON,
                Items.COOKED_MUTTON,
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP,
                Items.BEEF,
                Items.COOKED_BEEF
        );

        this.tag(UP2ItemTags.STETHACANTHUS_FOOD).addTag(ItemTags.FISHES);

        this.tag(UP2ItemTags.PACIFIES_STETHACANTHUS).add(Items.GOLDEN_APPLE);

        this.tag(UP2ItemTags.TALPANAS_FOOD).add(
                Items.WHEAT,
                Blocks.GRASS.asItem(),
                Items.SWEET_BERRIES
        );

        this.tag(UP2ItemTags.TELECREX_FOOD).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(UP2ItemTags.UNICORN_FOOD).add(
                Items.CAKE
        );

        this.copy(UP2BlockTags.GINKGO_LOGS, UP2ItemTags.GINKGO_LOGS);
        this.copy(UP2BlockTags.LEPIDODENDRON_LOGS, UP2ItemTags.LEPIDODENDRON_LOGS);

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

        // forge
        this.tag(UP2ItemTags.FRUITS).addTag(UP2ItemTags.FRUITS_GINKGO);
        this.tag(UP2ItemTags.FRUITS_GINKGO).add(GINKGO_FRUIT.get());
    }
}
