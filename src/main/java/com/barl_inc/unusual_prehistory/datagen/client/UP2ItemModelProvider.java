package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.platypushasnohat.sinew.datagen.client.SinewItemModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class UP2ItemModelProvider extends SinewItemModelProvider {

    public UP2ItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        this.generatedItem(
                UP2Items.ORGANIC_OOZE,
                UP2Items.SPIRAL_FOSSIL,
                UP2Items.GARGANTUAN_FOSSIL,
                UP2Blocks.AMMONITE_EGGS,
                UP2Blocks.LEEDSICHTHYS_ROE,
                UP2Items.AMMONITE_BUCKET
        );

        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof DeferredSpawnEggItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(UnusualPrehistory2.MOD_ID)) {
                this.withExistingParent(name(item), "item/template_spawn_egg");
            }
        }
    }
}
