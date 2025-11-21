package com.barlinc.unusual_prehistory.registry;

import net.minecraft.world.level.saveddata.maps.MapDecoration;

public class UP2MapIcons {

    public static final MapDecoration.Type FOSSIL_SITE_MAP_DECORATION = MapDecoration.Type.valueOf("FOSSIL_SITE");

    public static byte getMapIconRenderOrdinal(MapDecoration.Type type) {
        return (byte) (type == FOSSIL_SITE_MAP_DECORATION ? 0 : -1);
    }
}
