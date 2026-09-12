package com.barl_inc.unusual_prehistory.tags;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UP2BlockTags {

    public static final TagKey<Block> ACCELERATES_EGG_HATCHING = modBlockTag("accelerates_egg_hatching");
    public static final TagKey<Block> PREVENTS_EGG_HATCHING = modBlockTag("prevents_egg_hatching");

    private static TagKey<Block> modBlockTag(String name) {
        return blockTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Block> commonBlockTag(String name) {
        return blockTag("c", name);
    }

    public static TagKey<Block> blockTag(String modId, String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
    }
}
