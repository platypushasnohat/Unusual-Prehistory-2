package com.unusualmodding.unusual_prehistory.client.book;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PaleopediaGui extends GuiBasicBook {

    public PaleopediaGui(ItemStack bookStack) {
        super(bookStack, Component.translatable("item.unusual_prehistory.paleopedia"));
    }

    @Override
    protected int getBindingColor() {
        return 0x714420;
    }

    @Override
    protected int getTextColor() {
        return 9729114;
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
