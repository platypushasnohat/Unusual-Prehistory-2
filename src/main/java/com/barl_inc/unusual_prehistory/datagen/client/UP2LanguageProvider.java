package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.*;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
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

        this.addSound(UP2SoundEvents.AMMONITE_HURT, "Ammonite hurts");
        this.addSound(UP2SoundEvents.AMMONITE_DEATH, "Ammonite dies");
        this.addSound(UP2SoundEvents.AMMONITE_IDLE, "Ammonite gurgles");
        this.addSound(UP2SoundEvents.AMMONITE_SWIM, "Ammonite swims");
        this.addSound(UP2SoundEvents.AMMONITE_FLOP, "Ammonite flops");

        this.addSound(UP2SoundEvents.LEEDSICHTHYS_HURT, "Leedsichthys hurts");
        this.addSound(UP2SoundEvents.LEEDSICHTHYS_DEATH, "Leedsichthys dies");
        this.addSound(UP2SoundEvents.LEEDSICHTHYS_IDLE, "Leedsichthys bellows");
        this.addSound(UP2SoundEvents.LEEDSICHTHYS_SWIM, "Leedsichthys swims");

        this.addBlock(UP2Blocks.COMMON_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.common_fossil_bed.desc", "Common");
        this.addBlock(UP2Blocks.UNCOMMON_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.uncommon_fossil_bed.desc", "Uncommon");
        this.addBlock(UP2Blocks.RARE_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.rare_fossil_bed.desc", "Rare");
        this.addBlock(UP2Blocks.UNUSUAL_FOSSIL_BED, "Fossil Bed");
        this.add("block.unusual_prehistory.unusual_fossil_bed.desc", "Unusual");

        this.addItem(UP2Items.AMMONITE_BUCKET, "Bucket of Ammonite");

        this.add("unusual_prehistory.jei.transmogrification", "Transmogrification");
        this.add("unusual_prehistory.jade.egg_block.hatch_time", "Hatch time: %s");
        this.add("config.jade.plugin_unusual_prehistory.transmogrifier", "Transmogrifier Contents");
        this.add("config.jade.plugin_unusual_prehistory.egg_block", "Egg Block Info");

        this.addTag(() -> UP2ItemTags.TRANSMOGRIFIER_FUEL, "Transmogrifier Fuel");
        this.addTag(() -> UP2BlockTags.ACCELERATES_EGG_HATCHING, "Accelerates Egg Hatching");
        this.addTag(() -> UP2BlockTags.PREVENTS_EGG_HATCHING, "Prevents Egg Hatching");

        this.add("entity.unusual_prehistory.ammonite.variant_ammonite_crioceratites", "Crioceratites");
        this.add("entity.unusual_prehistory.ammonite.variant_ammonite_hoplites", "Hoplites");
        this.add("entity.unusual_prehistory.ammonite.variant_ammonite_nostoceras", "Nostoceras");
        this.add("entity.unusual_prehistory.ammonite.variant_ammonite_pinacoceras", "Pinacoceras");
        this.add("entity.unusual_prehistory.ammonite.variant_ammonite_tropites", "Tropites");
    }
}
