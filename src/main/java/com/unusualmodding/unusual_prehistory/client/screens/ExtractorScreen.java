package com.unusualmodding.unusual_prehistory.client.screens;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.menus.ExtractorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorScreen extends AbstractContainerScreen<ExtractorMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/extractor.png");

    private static final int SCREEN_WIDTH = 176;
    private static final int SCREEN_HEIGHT = 166;

    private static final int LEFT_PROGRESS_X = 16;
    private static final int LEFT_PROGRESS_Y = 25;
    private static final int RIGHT_PROGRESS_X = 149;
    private static final int RIGHT_PROGRESS_Y = LEFT_PROGRESS_Y;
    private static final int PROGRESS_WIDTH = 11;
    private static final int PROGRESS_HEIGHT = 44;

    public ExtractorScreen(ExtractorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = SCREEN_WIDTH;
        imageHeight = SCREEN_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        Lighting.setupForFlatItems();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (menu.isCrafting()) {
            int height = menu.getScaledProgress(PROGRESS_HEIGHT);
            int blitY = PROGRESS_HEIGHT - height;
            graphics.blit(TEXTURE, leftPos + LEFT_PROGRESS_X, topPos + LEFT_PROGRESS_Y + blitY, 176, blitY, PROGRESS_WIDTH, height);
            graphics.blit(TEXTURE, leftPos + RIGHT_PROGRESS_X, topPos + RIGHT_PROGRESS_Y + blitY, 187, blitY, PROGRESS_WIDTH, height);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
