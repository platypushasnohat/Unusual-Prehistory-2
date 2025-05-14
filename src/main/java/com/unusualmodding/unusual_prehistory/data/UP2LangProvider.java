package com.unusualmodding.unusual_prehistory.data;

import com.mojang.logging.LogUtils;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import com.unusualmodding.unusual_prehistory.items.UP2Items;
import com.unusualmodding.unusual_prehistory.tab.UP2CreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class UP2LangProvider extends LanguageProvider {

    public UP2LangProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, "en_us");
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected void addTranslations() {

        // creative tab
        creativeTab(UP2CreativeTabs.UNUSUAL_PREHISTORY_2_TAB.get(), "Unusual Prehistory 2");

        // blocks
        UP2Blocks.AUTO_TRANSLATE.forEach(this::forBlock);

        // items
        UP2Items.AUTO_TRANSLATE.forEach(this::forItem);

        // creature dna
        dnaItem(UP2Items.DIPLOCAULUS_DNA.get());
        dnaItem(UP2Items.DUNKLEOSTEUS_DNA.get());
        dnaItem(UP2Items.JAWLESS_FISH_DNA.get());
        dnaItem(UP2Items.KENTROSAURUS_DNA.get());
        dnaItem(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA.get());
        dnaItem(UP2Items.MAJUNGASAURUS_DNA.get());
        dnaItem(UP2Items.MEGALANIA_DNA.get());
        dnaItem(UP2Items.SCAUMENACIA_DNA.get());
        dnaItem(UP2Items.STETHACANTHUS_DNA.get());
        dnaItem(UP2Items.TELECREX_DNA.get());

        // plant dna
        dnaItem(UP2Items.ANOSTYLOSTROMA_DNA.get());
        dnaItem(UP2Items.ARCHAEFRUCTUS_DNA.get());
        dnaItem(UP2Items.ARCHAEOSIGILLARIA_DNA.get());
        dnaItem(UP2Items.BENNETTITALES_DNA.get());
        dnaItem(UP2Items.CALAMOPHYTON_DNA.get());
        dnaItem(UP2Items.CLADOPHLEBIS_DNA.get());
        dnaItem(UP2Items.CLATHRODICTYON_CORAL_DNA.get());
        dnaItem(UP2Items.GINKGO_DNA.get());
        dnaItem(UP2Items.HORSETAIL_DNA.get());
        dnaItem(UP2Items.ISOETES_DNA.get());
        dnaItem(UP2Items.LEEFRUCTUS_DNA.get());
        dnaItem(UP2Items.NELUMBITES_DNA.get());
        dnaItem(UP2Items.QUEREUXIA_DNA.get());
        dnaItem(UP2Items.RAIGUENRAYUN_DNA.get());
        dnaItem(UP2Items.SARRACENIA_DNA.get());

    }

    private void forBlock(Supplier<? extends Block> block) {
        addBlock(block, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    private void forItem(Supplier<? extends Item> item) {
        addItem(item, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    private void forEntity(Supplier<? extends EntityType<?>> entity) {
        addEntityType(entity, UP2TextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.get())).getPath()));
    }

    private String format(ResourceLocation registryName) {
        return WordUtils.capitalizeFully(registryName.getPath().replace("_", " "));
    }

    protected void painting(String name, String author) {
        add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".title",  UP2TextUtils.createTranslation(name));
        add("painting." + UnusualPrehistory2.MOD_ID + "." + name + ".author",  author);
    }

    protected void musicDisc(Supplier<? extends Item> item, String description) {
        String disc = item.get().getDescriptionId();
        add(disc, "Music Disc");
        add(disc + ".desc", description);
    }

    public void creativeTab(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    private void dnaItem(Item... items) {
        List.of(items).forEach((item -> this.add(item, "Bottle of " + format(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item))).replace(" Dna Bottle", "") + " DNA")));
    }
}
