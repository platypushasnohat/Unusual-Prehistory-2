package com.unusualmodding.unusual_prehistory.registry.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UP2BlockTags {

    public static final TagKey<Block> ACCELERATES_EGG_HATCHING = modBlockTag("accelerates_egg_hatching");
    public static final TagKey<Block> PREVENTS_EGG_HATCHING = modBlockTag("prevents_egg_hatching");

    public static final TagKey<Block> GINKGO_LOGS = modBlockTag("ginkgo_logs");

    public static final TagKey<Block> LEPIDODENDRON_LOGS = modBlockTag("lepidodendron_logs");

    public static final TagKey<Block> ANCIENT_PLANT_PLACEABLES = modBlockTag("ancient_plant_placeable");

    public static final TagKey<Block> LEPIDODENDRON_GROWTHS_PLACEABLES = modBlockTag("lepidodendron_growths_placeable");

    public static final TagKey<Block> ZOMBIE_EGG_TARGETS = modBlockTag("zombie_egg_targets");

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
