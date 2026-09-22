package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2BlockTagsProvider extends BlockTagsProvider {

    public UP2BlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_VERTEBRA.get(),
                FOSSILIZED_SKULL.get(),
                FOSSILIZED_SKULL_LANTERN.get(),
                FOSSILIZED_SKULL_SOUL_LANTERN.get(),
                FOSSILIZED_BONE_ROD.get(),
                FOSSILIZED_BONE_SPIKE.get(),
                FOSSILIZED_BONE_ROW.get(),

                COBBLED_FOSSILIZED_BONE.get(),
                COBBLED_FOSSILIZED_BONE_SLAB.get(),
                COBBLED_FOSSILIZED_BONE_STAIRS.get(),

                COMMON_FOSSIL_BED.get(),
                UNCOMMON_FOSSIL_BED.get(),
                RARE_FOSSIL_BED.get(),
                UNCOMMON_FOSSIL_BED.get(),

                BIOSTEEL_BLOCK.get(),
                BIOSTEEL_LATTICE.get(),
                BIOSTEEL_TILES.get(),
                BIOSTEEL_TILE_SLAB.get(),
                BIOSTEEL_TILE_STAIRS.get(),

                TRANSMOGRIFIER.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                DIRT_MATRIX.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                LEEDSICHTHYS_CHUNK.get()
        );
    }
}
