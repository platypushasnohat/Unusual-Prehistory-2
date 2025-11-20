package com.barlinc.unusual_prehistory.data;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Blocks.*;

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

        this.tag(UP2BlockTags.LEPIDODENDRON_LOGS).add(
                LEPIDODENDRON_LOG.get(),
                LEPIDODENDRON_WOOD.get(),
                MOSSY_LEPIDODENDRON_LOG.get(),
                MOSSY_LEPIDODENDRON_WOOD.get(),
                STRIPPED_LEPIDODENDRON_LOG.get(),
                STRIPPED_LEPIDODENDRON_WOOD.get()
        );

        this.tag(UP2BlockTags.ANCIENT_PLANT_PLACEABLES).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(Blocks.GRAVEL).add(Blocks.FARMLAND);

        this.tag(UP2BlockTags.LEPIDODENDRON_GROWTHS_PLACEABLES).addTag(UP2BlockTags.ANCIENT_PLANT_PLACEABLES).addTag(BlockTags.LEAVES).addTag(BlockTags.LOGS);

        this.tag(UP2BlockTags.ACCELERATES_EGG_HATCHING).addTag(BlockTags.WOOL).add(
                Blocks.HAY_BLOCK,
                Blocks.MOSS_BLOCK
        );

        this.tag(UP2BlockTags.PREVENTS_EGG_HATCHING).addTag(BlockTags.ICE);

        this.tag(UP2BlockTags.FOSSILIZED_BONE_BLOCKS).add(
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_VERTEBRA.get()
        );

        this.tag(UP2BlockTags.PETRIFIED_WOOD).add(
                PETRIFIED_LOG.get(),
                PETRIFIED_WOOD.get()
        );

        // minecraft
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                TRANSMOGRIFIER.get(),
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_VERTEBRA.get(),
                FOSSILIZED_SKULL.get(),
                FOSSILIZED_SKULL_LANTERN.get(),
                FOSSILIZED_SKULL_SOUL_LANTERN.get(),
                COBBLED_FOSSILIZED_BONE.get(),
                COBBLED_FOSSILIZED_BONE_STAIRS.get(),
                COBBLED_FOSSILIZED_BONE_SLAB.get(),
                FOSSILIZED_BONE_ROD.get(),
                FOSSILIZED_BONE_SPIKE.get(),
                FOSSILIZED_BONE_ROW.get(),
                PETRIFIED_LOG.get(),
                PETRIFIED_WOOD.get(),
                POLISHED_PETRIFIED_WOOD.get(),
                POLISHED_PETRIFIED_WOOD_STAIRS.get(),
                POLISHED_PETRIFIED_WOOD_SLAB.get(),
                POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get(),
                POLISHED_PETRIFIED_WOOD_BUTTON.get(),
                ASPHALT.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),

                GINKGO_LOG.get(), GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get(),
                GINKGO_PLANKS.get(), GINKGO_STAIRS.get(), GINKGO_SLAB.get(), GINKGO_FENCE.get(), GINKGO_FENCE_GATE.get(), GINKGO_PRESSURE_PLATE.get(), GINKGO_BUTTON.get(),
                GINKGO_DOOR.get(), GINKGO_TRAPDOOR.get(),

                LEPIDODENDRON_LOG.get(), LEPIDODENDRON_WOOD.get(),
                STRIPPED_LEPIDODENDRON_LOG.get(), STRIPPED_LEPIDODENDRON_WOOD.get(),
                LEPIDODENDRON_PLANKS.get(), LEPIDODENDRON_STAIRS.get(), LEPIDODENDRON_SLAB.get(), LEPIDODENDRON_FENCE.get(), LEPIDODENDRON_FENCE_GATE.get(), LEPIDODENDRON_PRESSURE_PLATE.get(), LEPIDODENDRON_BUTTON.get(),
                LEPIDODENDRON_DOOR.get(), LEPIDODENDRON_TRAPDOOR.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                MOSS_LAYER.get(),
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get(),
                LEPIDODENDRON_LEAVES.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                MOSSY_DIRT.get()
        );

        this.tag(BlockTags.SLABS).add(
                POLISHED_PETRIFIED_WOOD_SLAB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                POLISHED_PETRIFIED_WOOD_STAIRS.get()
        );

        this.tag(BlockTags.STONE_PRESSURE_PLATES).add(
                POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.STONE_BUTTONS).add(
                POLISHED_PETRIFIED_WOOD_BUTTON.get()
        );

        this.tag(BlockTags.REPLACEABLE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get()
        );

        this.tag(BlockTags.REPLACEABLE_BY_TREES).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),
                RAIGUENRAYUN.get()
        );

        this.tag(BlockTags.DIRT).add(
                MOSSY_DIRT.get()
        );

        this.tag(BlockTags.SMALL_FLOWERS).add(
                COOKSONIA.get(),
                LEEFRUCTUS.get()
        );

        this.tag(BlockTags.TALL_FLOWERS).add(
                RAIGUENRAYUN.get()
        );

        this.tag(BlockTags.SAPLINGS).add(
                GINKGO_SAPLING.get(),
                GOLDEN_GINKGO_SAPLING.get(),
                LEPIDODENDRON_CONE.get()
        );

        this.tag(BlockTags.LEAVES).add(
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get(),
                LEPIDODENDRON_LEAVES.get()
        );

        this.tag(BlockTags.LOGS).addTag(UP2BlockTags.GINKGO_LOGS).addTag(UP2BlockTags.LEPIDODENDRON_LOGS);

        this.tag(BlockTags.LOGS_THAT_BURN).addTag(UP2BlockTags.GINKGO_LOGS).addTag(UP2BlockTags.LEPIDODENDRON_LOGS);

        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                GINKGO_LOG.get(),
                LEPIDODENDRON_LOG.get(),
                MOSSY_LEPIDODENDRON_LOG.get()
        );

        this.tag(BlockTags.PLANKS).add(
                GINKGO_PLANKS.get(),
                LEPIDODENDRON_PLANKS.get()
        );

        this.tag(BlockTags.WOODEN_STAIRS).add(
                GINKGO_STAIRS.get(),
                LEPIDODENDRON_STAIRS.get()
        );

        this.tag(BlockTags.WOODEN_SLABS).add(
                GINKGO_SLAB.get(),
                LEPIDODENDRON_SLAB.get()
        );

        this.tag(BlockTags.WOODEN_FENCES).add(
                GINKGO_FENCE.get(),
                LEPIDODENDRON_FENCE.get()
        );

        this.tag(BlockTags.FENCE_GATES).add(
                GINKGO_FENCE_GATE.get(),
                LEPIDODENDRON_FENCE_GATE.get()
        );

        this.tag(BlockTags.WOODEN_DOORS).add(
                GINKGO_DOOR.get(),
                LEPIDODENDRON_DOOR.get()
        );

        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                GINKGO_TRAPDOOR.get(),
                LEPIDODENDRON_TRAPDOOR.get()
        );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                GINKGO_PRESSURE_PLATE.get(),
                LEPIDODENDRON_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.WOODEN_BUTTONS).add(
                GINKGO_BUTTON.get(),
                LEPIDODENDRON_BUTTON.get()
        );

        this.tag(BlockTags.STANDING_SIGNS).add(
                GINKGO_SIGN.get(),
                LEPIDODENDRON_SIGN.get()
        );

        this.tag(BlockTags.WALL_SIGNS).add(
                GINKGO_WALL_SIGN.get(),
                LEPIDODENDRON_WALL_SIGN.get()
        );

        this.tag(BlockTags.WALL_HANGING_SIGNS).add(
                GINKGO_WALL_HANGING_SIGN.get(),
                LEPIDODENDRON_WALL_HANGING_SIGN.get()
        );

        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(
                GINKGO_HANGING_SIGN.get(),
                LEPIDODENDRON_HANGING_SIGN.get()
        );

        // forge
        this.tag(Tags.Blocks.FENCE_GATES).add(
                GINKGO_FENCE_GATE.get(),
                LEPIDODENDRON_FENCE_GATE.get()
        );

        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
                GINKGO_FENCE_GATE.get(),
                LEPIDODENDRON_FENCE_GATE.get()
        );
    }
}
