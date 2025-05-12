package com.unusualmodding.unusualprehistory2.data;

import com.unusualmodding.unusualprehistory2.UnusualPrehistory2;
import com.unusualmodding.unusualprehistory2.data.tags.UP2BlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class UP2BlockTagProvider extends BlockTagsProvider {

    public UP2BlockTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider pProvider) {

        // UP2 tags
        this.tag(UP2BlockTags.ANCIENT_PLANT_PLACEABLES).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(Blocks.GRAVEL).add(Blocks.FARMLAND);
    }
}
