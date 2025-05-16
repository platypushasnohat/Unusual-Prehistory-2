package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.tags.UP2EntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.unusual_prehistory.entity.UP2Entities.*;

public class UP2EntityTagProvider extends EntityTypeTagsProvider {

    public UP2EntityTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, provider, UnusualPrehistory2.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS).add(
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH,
                EntityType.DOLPHIN,
                EntityType.TURTLE
        );

        tag(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS).add(
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH,
                EntityType.DOLPHIN
        );

        tag(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS).add(
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

    }
}
