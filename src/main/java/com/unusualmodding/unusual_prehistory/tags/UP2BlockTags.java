package com.unusualmodding.unusual_prehistory.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UP2BlockTags {

    public static final TagKey<Block> GINKGO_LOGS = modBlockTag("ginkgo_logs");

    public static final TagKey<Block> ANCIENT_PLANT_PLACEABLES = modBlockTag("ancient_plant_placeable");

    private static TagKey<Block> modBlockTag(String name) {
        return blockTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Block> forgeBlockTag(String name) {
        return blockTag("forge", name);
    }

    public static TagKey<Block> blockTag(String modid, String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(modid, name));
    }
}
