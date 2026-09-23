package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.platypushasnohat.sinew.datagen.client.SinewBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class UP2BlockStateProvider extends SinewBlockStateProvider {

    public UP2BlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.axisBlock(UP2Blocks.FOSSILIZED_BONE_BLOCK);
        this.axisBlock(UP2Blocks.FOSSILIZED_BONE_VERTEBRA, UnusualPrehistory2.location("block/fossilized_bone_vertebra"), UnusualPrehistory2.location("block/fossilized_bone_block_top"));
        this.axisBlock(UP2Blocks.FOSSILIZED_BONE_BARK, UnusualPrehistory2.location("block/fossilized_bone_block"), UnusualPrehistory2.location("block/fossilized_bone_block"));

        this.block(UP2Blocks.BIOSTEEL_BLOCK);
        this.block(UP2Blocks.BIOSTEEL_LATTICE);
        this.block(UP2Blocks.BIOSTEEL_TILES);
        this.stairsBlock(UP2Blocks.BIOSTEEL_TILES.get(), UP2Blocks.BIOSTEEL_TILE_STAIRS.get());
        this.slabBlock(UP2Blocks.BIOSTEEL_TILES.get(), UP2Blocks.BIOSTEEL_TILE_SLAB.get());

        this.block(UP2Blocks.COMMON_FOSSIL_BED);
        this.block(UP2Blocks.UNCOMMON_FOSSIL_BED);
        this.block(UP2Blocks.RARE_FOSSIL_BED);
        this.block(UP2Blocks.UNUSUAL_FOSSIL_BED);
    }
}
