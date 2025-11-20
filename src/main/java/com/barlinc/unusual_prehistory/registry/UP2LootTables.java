package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class UP2LootTables {

    public static final ResourceLocation TAR_PIT_COMMON = create("archaeology/tar_pit_common");
    public static final ResourceLocation TAR_PIT_RARE = create("archaeology/tar_pit_rare");

    // Modifiers
    public static final ResourceLocation OCEAN_RUIN_WARM = create("archaeology/ocean_ruin_warm");
    public static final ResourceLocation ABANDONED_MINESHAFT = create("archaeology/abandoned_mineshaft");
    public static final ResourceLocation SIMPLE_DUNGEON = create("archaeology/simple_dungeon");

    private static ResourceLocation create(String id) {
        return UnusualPrehistory2.modPrefix(id);
    }
}
