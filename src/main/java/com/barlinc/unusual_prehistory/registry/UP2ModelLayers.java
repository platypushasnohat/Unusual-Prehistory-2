package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class UP2ModelLayers {

    // Update 1
    public static final ModelLayerLocation CARNOTAURUS = mainLayer("carnotaurus");
    public static final ModelLayerLocation DIPLOCAULUS_TIGER = mainLayer("diplocaulus_tiger");
    public static final ModelLayerLocation DIPLOCAULUS_SWAMPY = mainLayer("diplocaulus_swampy");
    public static final ModelLayerLocation DIPLOCAULUS_MUDDY = mainLayer("diplocaulus_muddy");
    public static final ModelLayerLocation DIPLOCAULUS_DWARF = mainLayer("diplocaulus_dwarf");
    public static final ModelLayerLocation DROMAEOSAURUS = mainLayer("dromaeosaurus");
    public static final ModelLayerLocation DUNKLEOSTEUS_LARGE = mainLayer("dunkleosteus_large");
    public static final ModelLayerLocation DUNKLEOSTEUS_MEDIUM = mainLayer("dunkleosteus_medium");
    public static final ModelLayerLocation DUNKLEOSTEUS_SMALL = mainLayer("dunkleosteus_small");
    public static final ModelLayerLocation JAWLESS_FISH_ARANDASPIS = mainLayer("jawless_fish_arandaspis");
    public static final ModelLayerLocation JAWLESS_FISH_CEPHALASPIS = mainLayer("jawless_fish_cephalaspis");
    public static final ModelLayerLocation JAWLESS_FISH_DORYASPIS = mainLayer("jawless_fish_doryaspis");
    public static final ModelLayerLocation JAWLESS_FISH_FURACACAUDA = mainLayer("jawless_fish_furacacauda");
    public static final ModelLayerLocation JAWLESS_FISH_SACABAMBASPIS = mainLayer("jawless_fish_sacabambaspis");
    public static final ModelLayerLocation KENTROSAURUS = mainLayer("kentrosaurus");
    public static final ModelLayerLocation KIMMERIDGEBRACHYPTERAESCHNIDIUM = mainLayer("kimmeridgebrachypteraeschnidium");
    public static final ModelLayerLocation KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH = mainLayer("kimmeridgebrachypteraeschnidium_nymph");
    public static final ModelLayerLocation MAJUNGASAURUS = mainLayer("majungasaurus");
    public static final ModelLayerLocation MEGALANIA = mainLayer("megalania");
    public static final ModelLayerLocation STETHACANTHUS = mainLayer("stethacanthus");
    public static final ModelLayerLocation TALPANAS = mainLayer("talpanas");
    public static final ModelLayerLocation TELECREX = mainLayer("telecrex");
    public static final ModelLayerLocation UNICORN = mainLayer("unicorn");
    public static final ModelLayerLocation UNICORN_SKELETON = mainLayer("unicorn_skeleton");

    // Update 2
    public static final ModelLayerLocation ONCHOPRISTIS = mainLayer("onchopristis");

    // Update 3
    public static final ModelLayerLocation LIVING_OOZE = mainLayer("living_ooze");
    public static final ModelLayerLocation METRIORHYNCHUS = mainLayer("metriorhynchus");
    public static final ModelLayerLocation TARTUOSTEUS = mainLayer("tartuosteus");

    // Update 4
    public static final ModelLayerLocation BRACHIOSAURUS = mainLayer("brachiosaurus");
    public static final ModelLayerLocation BRACHIOSAURUS_BABY = mainLayer("brachiosaurus_baby");
    public static final ModelLayerLocation COELACANTHUS = mainLayer("coelacanthus");
    public static final ModelLayerLocation HIBBERTOPTERUS = mainLayer("hibbertopterus");
    public static final ModelLayerLocation KAPROSUCHUS = mainLayer("kaprosuchus");
    public static final ModelLayerLocation LEPTICTIDIUM = mainLayer("leptictidium");
    public static final ModelLayerLocation LOBE_FINNED_FISH_ALLENYPTERUS = mainLayer("lobe_finned_fish_allenypterus");
    public static final ModelLayerLocation LOBE_FINNED_FISH_EUSTHENOPTERON = mainLayer("lobe_finned_fish_eusthenopteron");
    public static final ModelLayerLocation LOBE_FINNED_FISH_GOOLOOGONGIA = mainLayer("lobe_finned_fish_gooloogongia");
    public static final ModelLayerLocation LOBE_FINNED_FISH_LACCOGNATHUS = mainLayer("lobe_finned_fish_laccognathus");
    public static final ModelLayerLocation LOBE_FINNED_FISH_SCAUMENACIA = mainLayer("lobe_finned_fish_scaumenacia");
    public static final ModelLayerLocation LYSTROSAURUS = mainLayer("lystrosaurus");
    public static final ModelLayerLocation PACHYCEPHALOSAURUS = mainLayer("pachycephalosaurus");
    public static final ModelLayerLocation PRAEPUSA = mainLayer("praepusa");
    public static final ModelLayerLocation PTERODACTYLUS = mainLayer("pterodactylus");
    public static final ModelLayerLocation ULUGHBEGSAURUS = mainLayer("ulughbegsaurus");

    // Update 5
    public static final ModelLayerLocation AEGIROCASSIS = mainLayer("aegirocassis");
    public static final ModelLayerLocation AEGIROCASSIS_BABY = mainLayer("aegirocassis_baby");
    public static final ModelLayerLocation DESMATOSUCHUS = mainLayer("desmatosuchus");
    public static final ModelLayerLocation DELITZSCHALA = mainLayer("delitzschala");
    public static final ModelLayerLocation PSILOPTERUS = mainLayer("psilopterus");
    public static final ModelLayerLocation ZHANGSOLVA = mainLayer("zhangsolva");

    public static final ModelLayerLocation GRUG = mainLayer("grug");

    // Update 6
    public static final ModelLayerLocation ANTARCTOPELTA = mainLayer("antarctopelta");
    public static final ModelLayerLocation COTYLORHYNCHUS = mainLayer("cotylorhynchus");
    public static final ModelLayerLocation HYNERPETON = mainLayer("hynerpeton");
    public static final ModelLayerLocation MAMMOTH = mainLayer("mammoth");
    public static final ModelLayerLocation PROGNATHODON = mainLayer("prognathodon");
    public static final ModelLayerLocation SETAPEDITES = mainLayer("setapedites");

    // Future
    public static final ModelLayerLocation BARINASUCHUS = mainLayer("barinasuchus");
    public static final ModelLayerLocation DIMORPHODON = mainLayer("dimorphodon");
    public static final ModelLayerLocation ERYON = mainLayer("eryon");
    public static final ModelLayerLocation MANIPULATOR = mainLayer("manipulator");
    public static final ModelLayerLocation PALAEOPHIS = mainLayer("palaeophis");
    public static final ModelLayerLocation THERIZINOSAURUS = mainLayer("therizinosaurus");
    public static final ModelLayerLocation THERIZINOSAURUS_BABY = mainLayer("therizinosaurus_baby");
    public static final ModelLayerLocation WONAMBI = mainLayer("wonambi");

    private static ModelLayerLocation registerLayer(String id, String name) {
        return new ModelLayerLocation(UnusualPrehistory2.modPrefix(id), name);
    }

    private static ModelLayerLocation mainLayer(String id) {
        return registerLayer(id, "main");
    }
}
