package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class UP2BannerPatternTags {

    // Update 5
    public static final TagKey<BannerPattern> PALEOZOIC_BANNER_PATTERN = modBannerPatternTag("paleozoic_banner_pattern");
    public static final TagKey<BannerPattern> MESOZOIC_BANNER_PATTERN = modBannerPatternTag("mesozoic_banner_pattern");
    public static final TagKey<BannerPattern> CENOZOIC_BANNER_PATTERN = modBannerPatternTag("cenozoic_banner_pattern");
    public static final TagKey<BannerPattern> OOZE_BANNER_PATTERN = modBannerPatternTag("ooze_banner_pattern");

    private static TagKey<BannerPattern> modBannerPatternTag(String name) {
        return bannerPatternTag(UnusualPrehistory2.MOD_ID, name);
    }

    public static TagKey<BannerPattern> bannerPatternTag(String modid, String name) {
        return TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(modid, name));
    }
}
