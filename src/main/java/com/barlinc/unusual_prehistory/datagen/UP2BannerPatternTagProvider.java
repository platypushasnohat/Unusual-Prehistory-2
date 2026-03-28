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
                DIPLOCAULUS.getKey()
        );

        this.tag(UP2BannerPatternTags.MESOZOIC_BANNER_PATTERN).add(
                BRACHIOSAURUS.getKey(),
                CARNOTAURUS.getKey()
        );

        this.tag(UP2BannerPatternTags.CENOZOIC_BANNER_PATTERN).add(
                MEGALANIA.getKey()
        );
	}
}