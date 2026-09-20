package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class UP2ModelLayers {

    public static final ModelLayerLocation LEEDSICHTHYS = register("leedsichthys");
    public static final ModelLayerLocation LEEDSICHTHYS_BABY = register("leedsichthys_baby");

    private static ModelLayerLocation register(String id) {
        return new ModelLayerLocation(UnusualPrehistory2.location(id), "main");
    }
}