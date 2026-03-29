package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.tags.UP2BannerPatternTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2BannerPatterns.*;

public class UP2BannerPatternTagProvider extends BannerPatternTagsProvider {

	public UP2BannerPatternTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, UnusualPrehistory2.MOD_ID, helper);
	}

	@Override
	public void addTags(@NotNull Provider provider) {

		this.tag(UP2BannerPatternTags.PALEOZOIC_BANNER_PATTERN).add(
                AEGIROCASSIS.getKey(),
                COELACANTHUS.getKey(),
                DIPLOCAULUS.getKey(),
                DUNKLEOSTEUS.getKey(),
                HIBBERTOPTERUS.getKey(),
                JAWLESS_FISH.getKey(),
                LOBE_FINNED_FISH.getKey(),
                LYSTROSAURUS.getKey(),
                STETHACANTHUS.getKey(),
                TARTUOSTEUS.getKey(),

                AEGIROCASSIS_LARGE.getKey(),
                COELACANTHUS_LARGE.getKey(),
                DIPLOCAULUS_LARGE.getKey(),
                DUNKLEOSTEUS_LARGE.getKey(),
                HIBBERTOPTERUS_LARGE.getKey(),
                JAWLESS_FISH_LARGE.getKey(),
                LOBE_FINNED_FISH_LARGE.getKey(),
                LYSTROSAURUS_LARGE.getKey(),
                STETHACANTHUS_LARGE.getKey(),
                TARTUOSTEUS_LARGE.getKey()
        );

        this.tag(UP2BannerPatternTags.MESOZOIC_BANNER_PATTERN).add(
                BRACHIOSAURUS.getKey(),
                CARNOTAURUS.getKey(),
                DESMATOSUCHUS.getKey(),
                DROMAEOSAURUS.getKey(),
                KAPROSUCHUS.getKey(),
                KENTROSAURUS.getKey(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM.getKey(),
                LYSTROSAURUS.getKey(),
                MAJUNGASAURUS.getKey(),
                METRIORHYNCHUS.getKey(),
                MOSASAURUS.getKey(),
                ONCHOPRISTIS.getKey(),
                PTERODACTYLUS.getKey(),
                ULUGHBEGSAURUS.getKey(),

                BRACHIOSAURUS_LARGE.getKey(),
                CARNOTAURUS_LARGE.getKey(),
                DESMATOSUCHUS_LARGE.getKey(),
                DROMAEOSAURUS_LARGE.getKey(),
                KAPROSUCHUS_LARGE.getKey(),
                KENTROSAURUS_LARGE.getKey(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM_LARGE.getKey(),
                LYSTROSAURUS_LARGE.getKey(),
                MAJUNGASAURUS_LARGE.getKey(),
                METRIORHYNCHUS_LARGE.getKey(),
                MOSASAURUS_LARGE.getKey(),
                ONCHOPRISTIS_LARGE.getKey(),
                PTERODACTYLUS_LARGE.getKey(),
                ULUGHBEGSAURUS_LARGE.getKey()
        );

        this.tag(UP2BannerPatternTags.CENOZOIC_BANNER_PATTERN).add(
                LEPTICTIDIUM.getKey(),
                MEGALANIA.getKey(),
                PRAEPUSA.getKey(),
                PSILOPTERUS.getKey(),
                TALPANAS.getKey(),
                TELECREX.getKey(),

                LEPTICTIDIUM_LARGE.getKey(),
                MEGALANIA_LARGE.getKey(),
                PRAEPUSA_LARGE.getKey(),
                PSILOPTERUS_LARGE.getKey(),
                TALPANAS_LARGE.getKey(),
                TELECREX_LARGE.getKey()
        );

        this.tag(UP2BannerPatternTags.OOZE_BANNER_PATTERN).add(
                OOZE.getKey(),
                OOZE_LARGE.getKey()
        );
	}
}