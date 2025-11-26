package com.barlinc.unusual_prehistory.registry;

import net.minecraft.world.level.saveddata.maps.MapDecoration;

public class UP2MapIcons {

    public static final MapDecoration.Type MESOZOIC_FOSSIL_SITE = MapDecoration.Type.valueOf("MESOZOIC_FOSSIL_SITE");
    public static final MapDecoration.Type PALEOZOIC_FOSSIL_SITE = MapDecoration.Type.valueOf("PALEOZOIC_FOSSIL_SITE");
    public static final MapDecoration.Type PETRIFIED_TREE_SITE = MapDecoration.Type.valueOf("PETRIFIED_TREE_SITE");

    public static byte getMapIconRenderOrdinal(MapDecoration.Type type) {
        if (type == MESOZOIC_FOSSIL_SITE || type == PALEOZOIC_FOSSIL_SITE || type == PETRIFIED_TREE_SITE) return 0;
        else return -1;
    }
}
