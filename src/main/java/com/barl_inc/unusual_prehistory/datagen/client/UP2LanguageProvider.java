package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2CreativeTabs;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.platypushasnohat.sinew.datagen.client.SinewLanguageProvider;
import net.minecraft.data.PackOutput;

public class UP2LanguageProvider extends SinewLanguageProvider {

    public UP2LanguageProvider(PackOutput output) {
        super(output, UnusualPrehistory2.MOD_ID);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeTab(UP2CreativeTabs.UNUSUAL_PREHISTORY_TAB.get(), "Unusual Prehistory 2");
        UP2Items.ITEM_TRANSLATIONS.forEach(this::forItem);
        UP2Blocks.BLOCK_TRANSLATIONS.forEach(this::forBlock);
    }
}
