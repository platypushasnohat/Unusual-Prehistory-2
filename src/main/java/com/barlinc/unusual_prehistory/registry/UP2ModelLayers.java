package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UP2ModelLayers {

    public static final ModelLayerLocation ARANDASPIS = main("arandaspis");
    public static final ModelLayerLocation CARNOTAURUS = main("carnotaurus");
    public static final ModelLayerLocation CEPHALASPIS = main("cephalaspis");
    public static final ModelLayerLocation DIPLOCAULUS_BREVIROSTRIS = main("diplocaulus_brevirostris");
    public static final ModelLayerLocation DIPLOCAULUS_MAGNICORNIS = main("diplocaulus_magnicornis");
    public static final ModelLayerLocation DIPLOCAULUS_RECURVATIS = main("diplocaulus_recurvatis");
    public static final ModelLayerLocation DIPLOCAULUS_SALAMANDROIDES = main("diplocaulus_salamandroides");
    public static final ModelLayerLocation DORYASPIS = main("doryaspis");
    public static final ModelLayerLocation DROMAEOSAURUS = main("dromaeosaurus");
    public static final ModelLayerLocation DUNKLEOSTEUS_LARGE = main("dunkleosteus_large");
    public static final ModelLayerLocation DUNKLEOSTEUS_MEDIUM = main("dunkleosteus_medium");
    public static final ModelLayerLocation DUNKLEOSTEUS_SMALL = main("dunkleosteus_small");
    public static final ModelLayerLocation FURACACAUDA = main("furacacauda");
    public static final ModelLayerLocation KENTROSAURUS = main("kentrosaurus");
    public static final ModelLayerLocation KIMMERIDGEBRACHYTERAESCHNIDIUM = main("kimmeridgebrachypteraeschnidium");
    public static final ModelLayerLocation KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH = main("kimmeridgebrachypteraeschnidium_nymph");
    public static final ModelLayerLocation MAJUNGASAURUS = main("majungasaurus");
    public static final ModelLayerLocation MEGALANIA = main("megalania");
    public static final ModelLayerLocation METRIORHYNCHUS = main("metriorhynchus");
    public static final ModelLayerLocation ONCHOPRISTIS = main("onchopristis");
    public static final ModelLayerLocation SACABAMBASPIS = main("sacabambaspis");
    public static final ModelLayerLocation STETHACANTHUS = main("stethacanthus");
    public static final ModelLayerLocation TALPANAS = main("talpanas");
    public static final ModelLayerLocation TARTUOSTEUS = main("tartuosteus");
    public static final ModelLayerLocation TELECREX = main("telecrex");
    public static final ModelLayerLocation UNICORN = main("unicorn");
    public static final ModelLayerLocation UNICORN_SKELETON = main("unicorn_skeleton");
    public static final ModelLayerLocation LIVING_OOZE = main("living_ooze");

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(new ResourceLocation(UnusualPrehistory2.MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }
}
