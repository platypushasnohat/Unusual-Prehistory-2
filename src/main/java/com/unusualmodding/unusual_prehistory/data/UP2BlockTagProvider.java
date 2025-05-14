package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.tags.UP2BlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.unusual_prehistory.blocks.UP2Blocks.*;

public class UP2BlockTagProvider extends BlockTagsProvider {

    public UP2BlockTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider pProvider) {
        // unusual prehistory
        this.tag(UP2BlockTags.GINKGO_LOGS).add(
                GINKGO_LOG.get(),
                GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(),
                STRIPPED_GINKGO_WOOD.get()
        );

        this.tag(UP2BlockTags.ANCIENT_PLANT_PLACEABLES).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(Blocks.GRAVEL).add(Blocks.FARMLAND);

        // minecraft
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                PETRIFIED_ANOSTYLOSTROMA.get(),
                CLATHRODICTYON_CORAL_BLOCK.get(),
                DEAD_CLATHRODICTYON_CORAL.get(),
                DEAD_CLATHRODICTYON_CORAL_BLOCK.get(),
                DEAD_CLATHRODICTYON_CORAL_FAN.get(),
                DEAD_CLATHRODICTYON_CORAL_WALL_FAN.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),
                GINKGO_LOG.get(), GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get(),
                GINKGO_PLANKS.get(), GINKGO_STAIRS.get(), GINKGO_SLAB.get(), GINKGO_FENCE.get(), GINKGO_FENCE_GATE.get(), GINKGO_PRESSURE_PLATE.get(), GINKGO_BUTTON.get(),
                GINKGO_DOOR.get(), GINKGO_TRAPDOOR.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                FROZEN_MEAT_BLOCK.get(),
                ANOSTYLOSTROMA.get(),
                MOSS_LAYER.get(),
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                MOSSY_DIRT.get()
        );

        this.tag(BlockTags.REPLACEABLE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get()
        );

        this.tag(BlockTags.REPLACEABLE_BY_TREES).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),
                RAIGUENRAYUN.get(),
                TALL_SARRACENIA.get()
        );

        this.tag(BlockTags.DIRT).add(
                MOSSY_DIRT.get()
        );

        this.tag(BlockTags.SMALL_FLOWERS).add(
                COOKSONIA.get(),
                LEEFRUCTUS.get(),
                SARRACENIA.get()
        );

        this.tag(BlockTags.TALL_FLOWERS).add(
                RAIGUENRAYUN.get(),
                TALL_SARRACENIA.get()
        );

        this.tag(BlockTags.LEAVES).add(
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get()
        );

        this.tag(BlockTags.LOGS).add(
                GINKGO_LOG.get(),
                GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(),
                STRIPPED_GINKGO_WOOD.get()
        );

        this.tag(BlockTags.LOGS_THAT_BURN).add(
                GINKGO_LOG.get(),
                GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(),
                STRIPPED_GINKGO_WOOD.get()
        );

        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                GINKGO_LOG.get()
        );

        this.tag(BlockTags.PLANKS).add(
                GINKGO_PLANKS.get()
        );

        this.tag(BlockTags.WOODEN_STAIRS).add(
                GINKGO_STAIRS.get()
        );

        this.tag(BlockTags.WOODEN_SLABS).add(
                GINKGO_SLAB.get()
        );

        this.tag(BlockTags.WOODEN_FENCES).add(
                GINKGO_FENCE.get()
        );

        this.tag(BlockTags.FENCE_GATES).add(
                GINKGO_FENCE_GATE.get()
        );

        this.tag(BlockTags.WOODEN_DOORS).add(
                GINKGO_DOOR.get()
        );

        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                GINKGO_TRAPDOOR.get()
        );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                GINKGO_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.WOODEN_BUTTONS).add(
                GINKGO_BUTTON.get()
        );

        // forge
        this.tag(Tags.Blocks.FENCE_GATES).add(
                GINKGO_FENCE_GATE.get()
        );

        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
                GINKGO_FENCE_GATE.get()
        );
    }
}
