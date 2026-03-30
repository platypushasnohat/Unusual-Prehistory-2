package com.barlinc.unusual_prehistory.datagen;

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
    protected void addTags(@NotNull Provider provider) {
        // Unusual Prehistory
        this.tag(UP2BlockTags.DRYOPHYLLUM_LOGS).add(
                DRYOPHYLLUM_LOG.get(),
                DRYOPHYLLUM_WOOD.get(),
                STRIPPED_DRYOPHYLLUM_LOG.get(),
                STRIPPED_DRYOPHYLLUM_WOOD.get()
        );

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

        this.tag(UP2BlockTags.METASEQUOIA_LOGS).add(
                METASEQUOIA_LOG.get(),
                METASEQUOIA_WOOD.get(),
                STRIPPED_METASEQUOIA_LOG.get(),
                STRIPPED_METASEQUOIA_WOOD.get()
        );

        this.tag(UP2BlockTags.ANCIENT_PLANT_PLACEABLE).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(Blocks.GRAVEL).add(Blocks.FARMLAND);

        this.tag(UP2BlockTags.ACCELERATES_EGG_HATCHING).addTag(BlockTags.WOOL).add(
                Blocks.HAY_BLOCK,
                Blocks.MOSS_BLOCK
        );
        this.tag(UP2BlockTags.PREVENTS_EGG_HATCHING).add(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE);

        this.tag(UP2BlockTags.FOSSIL_REPLACEABLE).addTag(BlockTags.DIRT).add(
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.TAR_PIT_REPLACEABLE).addTag(BlockTags.DIRT).add(
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.PETRIFIED_TREE_REPLACEABLE).addTag(BlockTags.DIRT).add(
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.REINFORCED_GLASS).add(
                REINFORCED_GLASS.get(),
                TINTED_REINFORCED_GLASS.get(),
                WHITE_REINFORCED_GLASS.get(),
                LIGHT_GRAY_REINFORCED_GLASS.get(),
                GRAY_REINFORCED_GLASS.get(),
                BLACK_REINFORCED_GLASS.get(),
                BROWN_REINFORCED_GLASS.get(),
                RED_REINFORCED_GLASS.get(),
                ORANGE_REINFORCED_GLASS.get(),
                YELLOW_REINFORCED_GLASS.get(),
                LIME_REINFORCED_GLASS.get(),
                GREEN_REINFORCED_GLASS.get(),
                CYAN_REINFORCED_GLASS.get(),
                LIGHT_BLUE_REINFORCED_GLASS.get(),
                BLUE_REINFORCED_GLASS.get(),
                PURPLE_REINFORCED_GLASS.get(),
                MAGENTA_REINFORCED_GLASS.get(),
                PINK_REINFORCED_GLASS.get()
        );

        this.tag(UP2BlockTags.FOSSILIZED_BONE_BLOCKS).add(
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_VERTEBRA.get()
        );

        this.tag(UP2BlockTags.PETRIFIED_WOOD).add(
                PETRIFIED_LOG.get(),
                PETRIFIED_WOOD.get()
        );

        this.tag(UP2BlockTags.GUANGDEDENDRON_PLANTABLE_ON).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(
                GUANGDEDENDRON.get(),
                GUANGDEDENDRON_SPORE.get(),
                Blocks.GRAVEL,
                Blocks.SUSPICIOUS_GRAVEL
        );

        this.tag(UP2BlockTags.GUARDED_BY_KENTROSAURUS).add(
                Blocks.SWEET_BERRY_BUSH,
                Blocks.CACTUS
        );

        // Spawnable blocks
        this.tag(UP2BlockTags.BARINASUCHUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.BRACHIOSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.CARNOTAURUS_SPAWNABLE_ON).add(
                Blocks.COARSE_DIRT,
                Blocks.RED_SAND,
                Blocks.GRASS_BLOCK
        ).addTag(BlockTags.TERRACOTTA);

        this.tag(UP2BlockTags.DESMATOSUCHUS_SPAWNABLE_ON).add(
                Blocks.SAND,
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.DIMORPHODON_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.DIPLOCAULUS_SPAWNABLE_ON).add(
                Blocks.MUD,
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.DROMAEOSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.HIBBERTOPTERUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.MUD
        );

        this.tag(UP2BlockTags.KAPROSUCHUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.GRAVEL,
                Blocks.SAND
        );

        this.tag(UP2BlockTags.KENTROSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.MUD
        );

        this.tag(UP2BlockTags.LEPTICTIDIUM_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.LYSTROSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.MAJUNGASAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.MANIPULATOR_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.MEGALANIA_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.SAND,
                Blocks.RED_SAND
        );

        this.tag(UP2BlockTags.PACHYCEPHALOSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.PRAEPUSA_SPAWNABLE_ON).add(
                Blocks.SAND
        );

        this.tag(UP2BlockTags.PSILOPTERUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.COARSE_DIRT
        );

        this.tag(UP2BlockTags.PTERODACTYLUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.STONE
        );

        this.tag(UP2BlockTags.TALPANAS_SPAWNABLE_ON).add(
                Blocks.MOSS_BLOCK,
                Blocks.CLAY,
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.TELECREX_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.THERIZINOSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.ULUGHBEGSAURUS_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.UNICORN_SPAWNABLE_ON).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS).addTag(BlockTags.SNOW).add(
                Blocks.MUD,
                MOSSY_DIRT.get(),
                Blocks.MOSS_BLOCK
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_BURROWING_BLOCKS).addTag(BlockTags.SNOW).addTag(BlockTags.SAND).addTag(Tags.Blocks.GRAVEL).add(
                Blocks.MUD
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.MOSS_BLOCK
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS).add(
                Blocks.MUD
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS).add(
                Blocks.SNOW_BLOCK,
                Blocks.SNOW
        );

        this.tag(UP2BlockTags.DIPLOCAULUS_PREFERRED_WALKING_BLOCKS).add(
                Blocks.MUD
        );

        this.tag(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS).add(
                Blocks.MUD,
                Blocks.ICE,
                Blocks.PACKED_ICE,
                Blocks.BLUE_ICE,
                Blocks.SNOW_BLOCK
        );

        this.tag(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS).add(
                Blocks.MUD,
                Blocks.SAND,
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.LYSTROSAURUS_DIGGING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.SNOW_BLOCK,
                Blocks.SAND,
                Blocks.ROOTED_DIRT,
                Blocks.PACKED_MUD
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_GRAZING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.MOSS_BLOCK,
                Blocks.ROOTED_DIRT
        );

        this.tag(UP2BlockTags.JAWLESS_FISH_NIBBLING_BLOCKS).add(
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET
        );

        this.tag(UP2BlockTags.KENTROSAURUS_GRAZING_BLOCKS).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.LOBE_FINNED_FISH_NIBBLING_BLOCKS).add(
                Blocks.SEAGRASS
        );

        this.tag(UP2BlockTags.LYSTROSAURUS_GRAZING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.PACHYCEPHALOSAURUS_GRAZING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.PSILOPTERUS_DIGGING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.SNOW_BLOCK,
                Blocks.SNOW,
                Blocks.PODZOL,
                Blocks.ROOTED_DIRT,
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.TALPANAS_PECKING_BLOCKS).add(
                Blocks.GRASS_BLOCK,
                Blocks.MOSS_BLOCK
        );

        this.tag(UP2BlockTags.TARTUOSTEUS_NIBBLING_BLOCKS).add(
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET
        );

        this.tag(UP2BlockTags.TELECREX_PECKING_BLOCKS).add(
                Blocks.GRASS_BLOCK
        );

        // Minecraft
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                TRANSMOGRIFIER.get(),
                PALEOSTONE.get(),
                PALEOSTONE_STAIRS.get(),
                PALEOSTONE_SLAB.get(),
                MESONITE.get(),
                MESONITE_STAIRS.get(),
                MESONITE_SLAB.get(),
                FLORALITE.get(),
                FLORALITE_STAIRS.get(),
                FLORALITE_SLAB.get(),
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
                ASPHALT.get(),
                OOZE_CAULDRON.get()
        ).addTag(UP2BlockTags.REINFORCED_GLASS);

        this.tag(BlockTags.IMPERMEABLE).addTag(UP2BlockTags.REINFORCED_GLASS);

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),

                GUANGDEDENDRON.get(),
                GUANGDEDENDRON_SPORE.get(),

                CYCAD_STEM.get(),
                CYCAD_CROWN.get(),

                DRYOPHYLLUM_LOG.get(), DRYOPHYLLUM_WOOD.get(),
                STRIPPED_DRYOPHYLLUM_LOG.get(), STRIPPED_DRYOPHYLLUM_WOOD.get(),
                DRYOPHYLLUM_PLANKS.get(), DRYOPHYLLUM_STAIRS.get(), DRYOPHYLLUM_SLAB.get(), DRYOPHYLLUM_FENCE.get(), DRYOPHYLLUM_FENCE_GATE.get(), DRYOPHYLLUM_PRESSURE_PLATE.get(), DRYOPHYLLUM_BUTTON.get(),
                DRYOPHYLLUM_DOOR.get(), DRYOPHYLLUM_TRAPDOOR.get(),

                GINKGO_LOG.get(), GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get(),
                GINKGO_PLANKS.get(), GINKGO_STAIRS.get(), GINKGO_SLAB.get(), GINKGO_FENCE.get(), GINKGO_FENCE_GATE.get(), GINKGO_PRESSURE_PLATE.get(), GINKGO_BUTTON.get(),
                GINKGO_DOOR.get(), GINKGO_TRAPDOOR.get(),

                LEPIDODENDRON_LOG.get(), LEPIDODENDRON_WOOD.get(), MOSSY_LEPIDODENDRON_LOG.get(), MOSSY_LEPIDODENDRON_WOOD.get(),
                STRIPPED_LEPIDODENDRON_LOG.get(), STRIPPED_LEPIDODENDRON_WOOD.get(),
                LEPIDODENDRON_PLANKS.get(), LEPIDODENDRON_STAIRS.get(), LEPIDODENDRON_SLAB.get(), LEPIDODENDRON_FENCE.get(), LEPIDODENDRON_FENCE_GATE.get(), LEPIDODENDRON_PRESSURE_PLATE.get(), LEPIDODENDRON_BUTTON.get(),
                LEPIDODENDRON_DOOR.get(), LEPIDODENDRON_TRAPDOOR.get(),

                METASEQUOIA_LOG.get(), METASEQUOIA_WOOD.get(),
                STRIPPED_METASEQUOIA_LOG.get(), STRIPPED_METASEQUOIA_WOOD.get(),
                METASEQUOIA_PLANKS.get(), METASEQUOIA_STAIRS.get(), METASEQUOIA_SLAB.get(), METASEQUOIA_FENCE.get(), METASEQUOIA_FENCE_GATE.get(), METASEQUOIA_PRESSURE_PLATE.get(), METASEQUOIA_BUTTON.get(),
                METASEQUOIA_DOOR.get(), METASEQUOIA_TRAPDOOR.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                MOSS_LAYER.get(),
                DRYOPHYLLUM_LEAVES.get(),
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get(),
                LEPIDODENDRON_LEAVES.get(),
                METASEQUOIA_LEAVES.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                MOSSY_DIRT.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                GUANGDEDENDRON_SPORE.get(),
                GUANGDEDENDRON.get()
        );

        this.tag(BlockTags.SLABS).add(
                PALEOSTONE_SLAB.get(),
                MESONITE_SLAB.get(),
                FLORALITE_SLAB.get(),
                POLISHED_PETRIFIED_WOOD_SLAB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                PALEOSTONE_STAIRS.get(),
                MESONITE_STAIRS.get(),
                FLORALITE_STAIRS.get(),
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
                RAIGUENRAYUN.get(),
                AETHOPHYLLUM.get(),
                ZHANGSOLVA_BLOOM.get(),
                DELITZSCHALA_FERN.get()
        );

        this.tag(BlockTags.DIRT).add(
                MOSSY_DIRT.get()
        );

        this.tag(BlockTags.SMALL_FLOWERS).add(
                COOKSONIA.get(),
                LEEFRUCTUS.get()
        );

        this.tag(BlockTags.TALL_FLOWERS).add(
                AETHOPHYLLUM.get(),
                RAIGUENRAYUN.get(),
                ZHANGSOLVA_BLOOM.get()
        );

        this.tag(BlockTags.SAPLINGS).add(
                DRYOPHYLLUM_SAPLING.get(),
                GINKGO_SAPLING.get(),
                GOLDEN_GINKGO_SAPLING.get(),
                LEPIDODENDRON_CONE.get()
        );

        this.tag(BlockTags.LEAVES).add(
                DRYOPHYLLUM_LEAVES.get(),
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get(),
                LEPIDODENDRON_LEAVES.get()
        );

        this.tag(BlockTags.LOGS_THAT_BURN).add(
                DRYOPHYLLUM_LOG.get(),
                DRYOPHYLLUM_WOOD.get(),
                STRIPPED_DRYOPHYLLUM_LOG.get(),
                STRIPPED_DRYOPHYLLUM_WOOD.get(),
                GINKGO_LOG.get(),
                GINKGO_WOOD.get(),
                STRIPPED_GINKGO_LOG.get(),
                STRIPPED_GINKGO_WOOD.get(),
                LEPIDODENDRON_LOG.get(),
                LEPIDODENDRON_WOOD.get(),
                MOSSY_LEPIDODENDRON_LOG.get(),
                MOSSY_LEPIDODENDRON_WOOD.get(),
                STRIPPED_LEPIDODENDRON_LOG.get(),
                STRIPPED_LEPIDODENDRON_WOOD.get(),
                METASEQUOIA_LOG.get(),
                METASEQUOIA_WOOD.get(),
                STRIPPED_METASEQUOIA_LOG.get(),
                STRIPPED_METASEQUOIA_WOOD.get()
        );

        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                DRYOPHYLLUM_LOG.get(),
                GINKGO_LOG.get(),
                LEPIDODENDRON_LOG.get(),
                MOSSY_LEPIDODENDRON_LOG.get(),
                METASEQUOIA_LOG.get()
        );

        this.tag(BlockTags.PLANKS).add(
                DRYOPHYLLUM_PLANKS.get(),
                GINKGO_PLANKS.get(),
                LEPIDODENDRON_PLANKS.get(),
                METASEQUOIA_PLANKS.get()
        );

        this.tag(BlockTags.WOODEN_STAIRS).add(
                DRYOPHYLLUM_STAIRS.get(),
                GINKGO_STAIRS.get(),
                LEPIDODENDRON_STAIRS.get(),
                METASEQUOIA_STAIRS.get()
        );

        this.tag(BlockTags.WOODEN_SLABS).add(
                DRYOPHYLLUM_SLAB.get(),
                GINKGO_SLAB.get(),
                LEPIDODENDRON_SLAB.get(),
                METASEQUOIA_SLAB.get()
        );

        this.tag(BlockTags.WOODEN_FENCES).add(
                DRYOPHYLLUM_FENCE.get(),
                GINKGO_FENCE.get(),
                LEPIDODENDRON_FENCE.get(),
                METASEQUOIA_FENCE.get()
        );

        this.tag(BlockTags.FENCE_GATES).add(
                DRYOPHYLLUM_FENCE_GATE.get(),
                GINKGO_FENCE_GATE.get(),
                LEPIDODENDRON_FENCE_GATE.get(),
                METASEQUOIA_FENCE_GATE.get()
        );

        this.tag(BlockTags.WOODEN_DOORS).add(
                DRYOPHYLLUM_DOOR.get(),
                GINKGO_DOOR.get(),
                LEPIDODENDRON_DOOR.get(),
                METASEQUOIA_DOOR.get()
        );

        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                DRYOPHYLLUM_TRAPDOOR.get(),
                GINKGO_TRAPDOOR.get(),
                LEPIDODENDRON_TRAPDOOR.get(),
                METASEQUOIA_TRAPDOOR.get()
        );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                DRYOPHYLLUM_PRESSURE_PLATE.get(),
                GINKGO_PRESSURE_PLATE.get(),
                LEPIDODENDRON_PRESSURE_PLATE.get(),
                METASEQUOIA_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.WOODEN_BUTTONS).add(
                DRYOPHYLLUM_BUTTON.get(),
                GINKGO_BUTTON.get(),
                LEPIDODENDRON_BUTTON.get(),
                METASEQUOIA_BUTTON.get()
        );

        this.tag(BlockTags.STANDING_SIGNS).add(
                DRYOPHYLLUM_SIGN.get(),
                GINKGO_SIGN.get(),
                LEPIDODENDRON_SIGN.get(),
                METASEQUOIA_SIGN.get()
        );

        this.tag(BlockTags.WALL_SIGNS).add(
                DRYOPHYLLUM_WALL_SIGN.get(),
                GINKGO_WALL_SIGN.get(),
                LEPIDODENDRON_WALL_SIGN.get(),
                METASEQUOIA_WALL_SIGN.get()
        );

        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(
                DRYOPHYLLUM_HANGING_SIGN.get(),
                GINKGO_HANGING_SIGN.get(),
                LEPIDODENDRON_HANGING_SIGN.get(),
                METASEQUOIA_HANGING_SIGN.get()
        );

        this.tag(BlockTags.WALL_HANGING_SIGNS).add(
                DRYOPHYLLUM_WALL_HANGING_SIGN.get(),
                GINKGO_WALL_HANGING_SIGN.get(),
                LEPIDODENDRON_WALL_HANGING_SIGN.get(),
                METASEQUOIA_WALL_HANGING_SIGN.get()
        );

        this.tag(BlockTags.CAULDRONS).add(
                OOZE_CAULDRON.get()
        );

        // forge
        this.tag(Tags.Blocks.FENCE_GATES).add(
                DRYOPHYLLUM_FENCE_GATE.get(),
                GINKGO_FENCE_GATE.get(),
                LEPIDODENDRON_FENCE_GATE.get(),
                METASEQUOIA_FENCE_GATE.get()
        );

        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
                DRYOPHYLLUM_FENCE_GATE.get(),
                GINKGO_FENCE_GATE.get(),
                LEPIDODENDRON_FENCE_GATE.get(),
                METASEQUOIA_FENCE_GATE.get()
        );
    }
}
