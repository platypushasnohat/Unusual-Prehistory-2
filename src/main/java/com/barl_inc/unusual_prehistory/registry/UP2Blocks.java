package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.block.*;
import com.barl_inc.unusual_prehistory.item.FossilBedBlockItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UP2Blocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UnusualPrehistory2.MOD_ID);

    public static List<DeferredBlock<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();
    public static List<Supplier<? extends Block>> EGG_BLOCKS = new ArrayList<>();

    public static final BlockBehaviour.Properties WATER_EGG_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().sound(SoundType.FROGSPAWN);

    public static final DeferredBlock<Block> FOSSILIZED_BONE_BLOCK = registerBlock("fossilized_bone_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_BARK = registerBlock("fossilized_bone_bark", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_VERTEBRA = registerBlock("fossilized_bone_vertebra", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL = registerBlock("fossilized_skull", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_LAMP = registerBlock("fossilized_skull_lamp", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).lightLevel((state) -> 15)));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_SOUL_LAMP = registerBlock("fossilized_skull_soul_lamp", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).lightLevel((state) -> 10)));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROD = registerBlock("fossilized_bone_rod", () -> new FossilizedBoneRodBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_SPIKE = registerBlock("fossilized_bone_spike", () -> new FossilizedBoneSpikeBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).noOcclusion()));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROW = registerBlock("fossilized_bone_row", () -> new FossilizedBoneRowBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).noOcclusion()));

    public static final DeferredBlock<Block> GRAVEL_MATRIX = registerBlock("gravel_matrix", () -> new MatrixBlock(Blocks.GRAVEL, SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(0.6F).sound(SoundType.SUSPICIOUS_GRAVEL).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.SNARE)));
    public static final DeferredBlock<Block> SAND_MATRIX = registerBlock("sand_matrix", () -> new MatrixBlock(Blocks.SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.SNARE)));
    public static final DeferredBlock<Block> RED_SAND_MATRIX = registerBlock("red_sand_matrix", () -> new MatrixBlock(Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.5F).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.SNARE)));
    public static final DeferredBlock<Block> DIRT_MATRIX = registerBlock("dirt_matrix", () -> new MatrixBlock(Blocks.DIRT, SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.SUSPICIOUS_GRAVEL)));
    public static final DeferredBlock<Block> MUD_MATRIX = registerBlock("mud_matrix", () -> new MatrixBlock(Blocks.MUD, SoundEvents.BRUSH_GENERIC, SoundEvents.MUD_BREAK, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SNOW_MATRIX = registerBlock("snow_matrix", () -> new MatrixBlock(Blocks.SNOW_BLOCK, SoundEvents.BRUSH_GENERIC, SoundEvents.SNOW_BREAK, BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> COMMON_FOSSIL_BED = registerFossilBedBlock("common_fossil_bed", FossilBedBlockItem.FossilBedRarity.COMMON, () -> new FossilBedBlock(UniformInt.of(0, 1), BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> UNCOMMON_FOSSIL_BED = registerFossilBedBlock("uncommon_fossil_bed", FossilBedBlockItem.FossilBedRarity.UNCOMMON, () -> new FossilBedBlock(UniformInt.of(1, 3), BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> RARE_FOSSIL_BED = registerFossilBedBlock("rare_fossil_bed", FossilBedBlockItem.FossilBedRarity.RARE, () -> new FossilBedBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> UNUSUAL_FOSSIL_BED = registerFossilBedBlock("unusual_fossil_bed", FossilBedBlockItem.FossilBedRarity.UNUSUAL, () -> new FossilBedBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));

    public static final DeferredBlock<Block> BIOSTEEL_BLOCK = registerBlock("biosteel_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));
    public static final DeferredBlock<Block> BIOSTEEL_LATTICE = registerBlock("biosteel_lattice", () -> new Block(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));
    public static final DeferredBlock<Block> BIOSTEEL_TILES = registerBlock("biosteel_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));
    public static final DeferredBlock<Block> BIOSTEEL_TILE_STAIRS = registerBlock("biosteel_tile_stairs", () -> new StairBlock(BIOSTEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));
    public static final DeferredBlock<Block> BIOSTEEL_TILE_SLAB = registerBlock("biosteel_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));

    public static final DeferredBlock<Block> TRANSMOGRIFIER = registerBlock("transmogrifier", () -> new TransmogrifierBlock(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get()).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 10 : 0)));

    public static final DeferredBlock<Block> AMMONITE_EGGS = registerEggBlock("ammonite_eggs", () -> new UnderwaterEggBlock(WATER_EGG_PROPERTIES, UP2Entities.AMMONITE::get, 4));
    public static final DeferredBlock<Block> LEEDSICHTHYS_ROE = registerEggBlock("leedsichthys_roe", () -> new UnderwaterEggBlock(WATER_EGG_PROPERTIES, UP2Entities.LEEDSICHTHYS::get, 1));

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerEggBlock(String name, Supplier<B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        EGG_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerFossilBedBlock(String name, FossilBedBlockItem.FossilBedRarity rarity, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new FossilBedBlockItem(block.get(), new Item.Properties(), rarity));
        return block;
    }
}
