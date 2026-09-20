package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.*;
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
        UP2Entities.ENTITY_TRANSLATIONS.forEach(this::forEntity);

        this.addSound(UP2SoundEvents.TRANSMOGRIFIER_PROCESSING, "Transmogrifier processes");

        this.addBlock(UP2Blocks.COMMON_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.common_fossil_bed.desc", "Common");
        this.addBlock(UP2Blocks.UNCOMMON_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.uncommon_fossil_bed.desc", "Uncommon");
        this.addBlock(UP2Blocks.RARE_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.rare_fossil_bed.desc", "Rare");
        this.addBlock(UP2Blocks.UNUSUAL_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.unusual_fossil_bed.desc", "Unusual");
    }
}
