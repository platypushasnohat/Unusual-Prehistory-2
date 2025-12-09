package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UP2BlockTags {

    public static final TagKey<Block> FOSSIL_REPLACEABLE = modBlockTag("fossil_replaceable");
    public static final TagKey<Block> TAR_PIT_REPLACEABLE = modBlockTag("tar_pit_replaceable");
    public static final TagKey<Block> PETRIFIED_TREE_REPLACEABLE = modBlockTag("petrified_tree_replaceable");

    public static final TagKey<Block> ACCELERATES_EGG_HATCHING = modBlockTag("accelerates_egg_hatching");
    public static final TagKey<Block> PREVENTS_EGG_HATCHING = modBlockTag("prevents_egg_hatching");

    public static final TagKey<Block> GINKGO_LOGS = modBlockTag("ginkgo_logs");
    public static final TagKey<Block> LEPIDODENDRON_LOGS = modBlockTag("lepidodendron_logs");

    public static final TagKey<Block> ANCIENT_PLANT_PLACEABLES = modBlockTag("ancient_plant_placeable");

    public static final TagKey<Block> ZOMBIE_EGG_TARGETS = modBlockTag("zombie_egg_targets");

    public static final TagKey<Block> FOSSILIZED_BONE_BLOCKS = modBlockTag("fossilized_bone_blocks");
    public static final TagKey<Block> PETRIFIED_WOOD = modBlockTag("petrified_wood");

    public static final TagKey<Block> GUARDED_BY_KENTROSAURUS = modBlockTag("guarded_by_kentrosaurus");

    public static final TagKey<Block> CARNOTAURUS_SPAWNABLE_ON = modBlockTag("carnotaurus_spawnable_on");
    public static final TagKey<Block> DIPLOCAULUS_SPAWNABLE_ON = modBlockTag("diplocaulus_spawnable_on");
    public static final TagKey<Block> DROMAEOSAURUS_SPAWNABLE_ON = modBlockTag("dromaeosaurus_spawnable_on");
    public static final TagKey<Block> KENTROSAURUS_SPAWNABLE_ON = modBlockTag("kentrosaurus_spawnable_on");
    public static final TagKey<Block> KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWNABLE_ON = modBlockTag("kimmeridgebrachypteraeschnidium_spawnable_on");
    public static final TagKey<Block> LYSTROSAURUS_SPAWNABLE_ON = modBlockTag("lystrosaurus_spawnable_on");
    public static final TagKey<Block> MAJUNGASAURUS_SPAWNABLE_ON = modBlockTag("majungasaurus_spawnable_on");
    public static final TagKey<Block> MEGALANIA_SPAWNABLE_ON = modBlockTag("megalania_spawnable_on");
    public static final TagKey<Block> TALPANAS_SPAWNABLE_ON = modBlockTag("talpanas_spawnable_on");
    public static final TagKey<Block> TELECREX_SPAWNABLE_ON = modBlockTag("telecrex_spawnable_on");
    public static final TagKey<Block> UNICORN_SPAWNABLE_ON = modBlockTag("unicorn_spawnable_on");

    public static final TagKey<Block> KENTROSAURUS_GRAZING_BLOCKS = modBlockTag("kentrosaurus_grazing_blocks");
    public static final TagKey<Block> TALPANAS_PECKING_BLOCKS = modBlockTag("talpanas_pecking_blocks");
    public static final TagKey<Block> TELECREX_PECKING_BLOCKS = modBlockTag("telecrex_pecking_blocks");

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
