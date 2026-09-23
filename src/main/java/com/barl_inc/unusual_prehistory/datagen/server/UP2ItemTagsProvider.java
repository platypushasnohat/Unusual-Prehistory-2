package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class UP2ItemTagsProvider extends ItemTagsProvider {

	public UP2ItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagLookup<Block>> lookup, ExistingFileHelper helper) {
		super(output, provider, lookup, UnusualPrehistory2.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(UP2ItemTags.TRANSMOGRIFIER_FUEL).add(UP2Items.ORGANIC_OOZE.get());

		this.tag(Tags.Items.SLIME_BALLS).add(UP2Items.ORGANIC_OOZE.get());
	}
}