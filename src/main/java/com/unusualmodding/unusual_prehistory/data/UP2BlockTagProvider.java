package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.data.tags.UP2BlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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

        // minecraft tags
        this.tag(BlockTags.DIRT).add(MOSSY_DIRT.get());

        // UP2 tags
        this.tag(UP2BlockTags.ANCIENT_PLANT_PLACEABLES).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(Blocks.GRAVEL).add(Blocks.FARMLAND);
    }
}
