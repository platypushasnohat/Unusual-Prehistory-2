package com.barlinc.unusual_prehistory.datagen.server;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.fluid.TarFluidType;
import com.barlinc.unusual_prehistory.registry.UP2Fluids;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2FluidTagsProvider extends FluidTagsProvider {

    public UP2FluidTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@NotNull Provider provider) {
        // Unusual Prehistory
        this.tag(FluidTags.WATER).add(UP2Fluids.TAR_FLUID_SOURCE.get(), UP2Fluids.TAR_FLUID_FLOWING.get());

    }
}
