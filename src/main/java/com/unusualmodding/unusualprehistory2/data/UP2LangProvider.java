package com.unusualmodding.unusualprehistory2.data;

import com.mojang.logging.LogUtils;
import com.unusualmodding.unusualprehistory2.UnusualPrehistory2;
import com.unusualmodding.unusualprehistory2.blocks.UP2Blocks;
import com.unusualmodding.unusualprehistory2.tab.UP2CreativeTabs;
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

import java.util.Objects;
import java.util.function.Supplier;

import static com.unusualmodding.unusualprehistory2.blocks.UP2Blocks.*;
import static com.unusualmodding.unusualprehistory2.items.UP2Items.*;

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
//        UP2Blocks.AUTO_TRANSLATE.forEach(this::forItem);

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
}
