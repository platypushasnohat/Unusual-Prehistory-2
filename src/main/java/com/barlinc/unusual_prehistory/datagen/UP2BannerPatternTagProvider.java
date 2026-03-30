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
                TARTUOSTEUS.getKey()
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
                ULUGHBEGSAURUS.getKey()
        );

        this.tag(UP2BannerPatternTags.CENOZOIC_BANNER_PATTERN).add(
                LEPTICTIDIUM.getKey(),
                MEGALANIA.getKey(),
                PRAEPUSA.getKey(),
                PSILOPTERUS.getKey(),
                TALPANAS.getKey(),
                TELECREX.getKey()
        );

        this.tag(UP2BannerPatternTags.OOZE_BANNER_PATTERN).add(
                OOZE.getKey()
        );
	}
}