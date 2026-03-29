package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class UP2BannerPatterns {

	public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, UnusualPrehistory2.MOD_ID);

    // Update 1
    public static final RegistryObject<BannerPattern> CARNOTAURUS = registerBannerPattern("carnotaurus");
    public static final RegistryObject<BannerPattern> DIPLOCAULUS = registerBannerPattern("diplocaulus");
    public static final RegistryObject<BannerPattern> DROMAEOSAURUS = registerBannerPattern("dromaeosaurus");
    public static final RegistryObject<BannerPattern> DUNKLEOSTEUS = registerBannerPattern("dunkleosteus");
    public static final RegistryObject<BannerPattern> JAWLESS_FISH = registerBannerPattern("jawless_fish");
    public static final RegistryObject<BannerPattern> KENTROSAURUS = registerBannerPattern("kentrosaurus");
    public static final RegistryObject<BannerPattern> KIMMERIDGEBRACHYPTERAESCHNIDIUM = registerBannerPattern("kimmeridgebrachypteraeschnidium");
    public static final RegistryObject<BannerPattern> MAJUNGASAURUS = registerBannerPattern("majungasaurus");
    public static final RegistryObject<BannerPattern> MEGALANIA = registerBannerPattern("megalania");
    public static final RegistryObject<BannerPattern> STETHACANTHUS = registerBannerPattern("stethacanthus");
    public static final RegistryObject<BannerPattern> TALPANAS = registerBannerPattern("talpanas");
    public static final RegistryObject<BannerPattern> TELECREX = registerBannerPattern("telecrex");

    // Update 2
    public static final RegistryObject<BannerPattern> ONCHOPRISTIS = registerBannerPattern("onchopristis");

    // Update 3
    public static final RegistryObject<BannerPattern> OOZE = registerBannerPattern("ooze");
    public static final RegistryObject<BannerPattern> METRIORHYNCHUS = registerBannerPattern("metriorhynchus");
    public static final RegistryObject<BannerPattern> TARTUOSTEUS = registerBannerPattern("tartuosteus");

    // Update 4
    public static final RegistryObject<BannerPattern> BRACHIOSAURUS = registerBannerPattern("brachiosaurus");
    public static final RegistryObject<BannerPattern> COELACANTHUS = registerBannerPattern("coelacanthus");
    public static final RegistryObject<BannerPattern> HIBBERTOPTERUS = registerBannerPattern("hibbertopterus");
    public static final RegistryObject<BannerPattern> KAPROSUCHUS = registerBannerPattern("kaprosuchus");
    public static final RegistryObject<BannerPattern> LEPTICTIDIUM = registerBannerPattern("leptictidium");
    public static final RegistryObject<BannerPattern> LOBE_FINNED_FISH = registerBannerPattern("lobe_finned_fish");
    public static final RegistryObject<BannerPattern> LYSTROSAURUS = registerBannerPattern("lystrosaurus");
    public static final RegistryObject<BannerPattern> PACHYCEPHALOSAURUS = registerBannerPattern("pachycephalosaurus");
    public static final RegistryObject<BannerPattern> PRAEPUSA = registerBannerPattern("praepusa");
    public static final RegistryObject<BannerPattern> PTERODACTYLUS = registerBannerPattern("pterodactylus");
    public static final RegistryObject<BannerPattern> ULUGHBEGSAURUS = registerBannerPattern("ulughbegsaurus");

    // Update 5
    public static final RegistryObject<BannerPattern> AEGIROCASSIS = registerBannerPattern("aegirocassis");
    public static final RegistryObject<BannerPattern> DESMATOSUCHUS = registerBannerPattern("desmatosuchus");
    public static final RegistryObject<BannerPattern> MOSASAURUS = registerBannerPattern("mosasaurus");
    public static final RegistryObject<BannerPattern> PSILOPTERUS = registerBannerPattern("psilopterus");

    private static RegistryObject<BannerPattern> registerBannerPattern(String name) {
        return BANNER_PATTERNS.register(name, () -> new BannerPattern(name));
    }
}