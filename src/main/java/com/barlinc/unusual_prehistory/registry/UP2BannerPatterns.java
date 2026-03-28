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
    public static final RegistryObject<BannerPattern> MEGALANIA = registerBannerPattern("megalania");

    // Update 4
    public static final RegistryObject<BannerPattern> BRACHIOSAURUS = registerBannerPattern("brachiosaurus");

    private static RegistryObject<BannerPattern> registerBannerPattern(String name) {
        return BANNER_PATTERNS.register(name, () -> new BannerPattern(name));
    }
}