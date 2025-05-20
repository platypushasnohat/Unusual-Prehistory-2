package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UP2ModelLayers {

    public static final ModelLayerLocation CEPHALASPIS_LAYER = main("cephalaspis");
    public static final ModelLayerLocation DORYASPIS_LAYER = main("doryaspis");
    public static final ModelLayerLocation DUNKLEOSTEUS_LARGE_LAYER = main("dunkleosteus_large");
    public static final ModelLayerLocation DUNKLEOSTEUS_MEDIUM_LAYER = main("dunkleosteus_medium");
    public static final ModelLayerLocation DUNKLEOSTEUS_SMALL_LAYER = main("dunkleosteus_small");
    public static final ModelLayerLocation FURACACAUDA_LAYER = main("furacacauda");
    public static final ModelLayerLocation KIMMERIDGEBRACHYTERAESCHNIDIUM_LAYER = main("kimmeridgebrachypteraeschnidium");
    public static final ModelLayerLocation SACABAMBASPIS_LAYER = main("sacabambaspis");
    public static final ModelLayerLocation SCAMENACIA_LAYER = main("scaumenacia");
    public static final ModelLayerLocation STETHACANTHUS_LAYER = main("stethacanthus");
    public static final ModelLayerLocation UNICORN_LAYER = main("unicorn");
    public static final ModelLayerLocation UNICORN_SKELETON_LAYER = main("unicorn_skeleton");

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(new ResourceLocation(UnusualPrehistory2.MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }
}
