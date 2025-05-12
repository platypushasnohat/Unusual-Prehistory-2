package com.unusualmodding.unusualprehistory2.data.tags;

import com.unusualmodding.unusualprehistory2.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UP2BlockTags {

    // plant placeable blocks
    public static final TagKey<Block> ANCIENT_PLANT_PLACEABLES = blockTag("ancient_plant_placeable");

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(UnusualPrehistory2.MOD_ID, name));
    }
}
