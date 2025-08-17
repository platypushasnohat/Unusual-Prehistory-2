package com.unusualmodding.unusual_prehistory.client.book;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookPageButton extends Button {

    private final boolean isForward;
    private final boolean playTurnSound;
    private final BasicBookGui bookGUI;

    public BookPageButton(BasicBookGui bookGUI, int p_i51079_1_, int p_i51079_2_, boolean p_i51079_3_, OnPress onPress, boolean p_i51079_5_) {
        super(p_i51079_1_, p_i51079_2_, 23, 13, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.isForward = p_i51079_3_;
        this.playTurnSound = p_i51079_5_;
        this.bookGUI = bookGUI;
    }

    public void renderWidget(GuiGraphics guiGraphics, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        int lvt_5_1_ = 0;
        int lvt_6_1_ = 0;
        if (this.isHovered) {
            lvt_5_1_ += 23;
        }
        if (!this.isForward) {
            lvt_6_1_ += 13;
        }
        drawNextArrow(guiGraphics, this.getX(), this.getY(), lvt_5_1_, lvt_6_1_, 18, 12);
    }

    public void drawNextArrow(GuiGraphics guiGraphics, int p_238474_2_, int p_238474_3_, int p_238474_4_, int p_238474_5_, int p_238474_6_, int p_238474_7_) {
        BookBlit.blitWithColor(guiGraphics, bookGUI.getBookWidgetTexture(), p_238474_2_, p_238474_3_, 100, p_238474_4_, p_238474_5_, p_238474_6_, p_238474_7_, 256, 256, 255, 255, 255, 255);
    }

    public void playDownSound(SoundManager soundManager) {
        if (this.playTurnSound) {
            soundManager.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        }
    }
}
