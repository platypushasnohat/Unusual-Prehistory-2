package com.unusualmodding.unusual_prehistory.client.book;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PaleopediaGui extends BasicBookGui {

    public PaleopediaGui(ItemStack book) {
        super(book, Component.translatable("item.unusual_prehistory.paleopedia"));
    }

    public PaleopediaGui(ItemStack book, String page) {
        super(book, Component.translatable("item.unusual_prehistory.paleopedia"));
        this.currentPageJSON = new ResourceLocation(this.getTextFileDirectory() + page + ".json");
    }

    @Override
    protected int getBindingColor() {
        return 0x714420;
    }

    @Override
    protected int getTextColor() {
        return 0x757575;
    }

    @Override
    public ResourceLocation getRootPage() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "book/paleopedia/root.json");
    }

    @Override
    public String getTextFileDirectory() {
        return UnusualPrehistory2.MOD_ID + ":book/paleopedia/";
    }
}
