package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.block.FossilizedBoneRodBlock;
import com.barl_inc.unusual_prehistory.block.FossilizedBoneRowBlock;
import com.barl_inc.unusual_prehistory.block.FossilizedBoneSpikeBlock;
import com.barl_inc.unusual_prehistory.block.FossilizedSkullBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UP2Blocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UnusualPrehistory2.MOD_ID);
    public static List<DeferredBlock<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();

    public static final DeferredBlock<Block> FOSSILIZED_BONE_BLOCK = registerBlock("fossilized_bone_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_BARK = registerBlock("fossilized_bone_bark", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_VERTEBRA = registerBlock("fossilized_bone_vertebra", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL = registerBlock("fossilized_skull", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_LANTERN = registerBlock("fossilized_skull_lantern", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).lightLevel((state) -> 15)));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_SOUL_LANTERN = registerBlock("fossilized_skull_soul_lantern", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).lightLevel((state) -> 10)));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROD = registerBlock("fossilized_bone_rod", () -> new FossilizedBoneRodBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_SPIKE = registerBlock("fossilized_bone_spike", () -> new FossilizedBoneSpikeBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).noOcclusion()));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROW = registerBlock("fossilized_bone_row", () -> new FossilizedBoneRowBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get()).noOcclusion()));

    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE = registerBlock("cobbled_fossilized_bone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE_STAIRS = registerBlock("cobbled_fossilized_bone_stairs", () -> new StairBlock(COBBLED_FOSSILIZED_BONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(COBBLED_FOSSILIZED_BONE.get())));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE_SLAB = registerBlock("cobbled_fossilized_bone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COBBLED_FOSSILIZED_BONE.get())));

    public static final DeferredBlock<Block> BIOSTEEL_BLOCK = registerBlock("biosteel_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> BIOSTEEL_LATTICE = registerBlock("biosteel_lattice", () -> new Block(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));
    public static final DeferredBlock<Block> BIOSTEEL_TILES = registerBlock("biosteel_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));
    public static final DeferredBlock<Block> BIOSTEEL_TILE_STAIRS = registerBlock("biosteel_tile_stairs", () -> new StairBlock(BIOSTEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));
    public static final DeferredBlock<Block> BIOSTEEL_TILE_SLAB = registerBlock("biosteel_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BIOSTEEL_BLOCK.get())));

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
}
