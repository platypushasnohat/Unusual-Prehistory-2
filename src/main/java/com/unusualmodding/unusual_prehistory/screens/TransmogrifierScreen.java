package com.unusualmodding.unusual_prehistory.screens;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TransmogrifierScreen extends AbstractContainerScreen<TransmogrifierMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/transmogrifier.png");

    private static final int SCREEN_WIDTH = 176;
    private static final int SCREEN_HEIGHT = 166;

    private static final int PROGRESS_X = 65;
    private static final int PROGRESS_Y = 29;
    private static final int PROGRESS_WIDTH = 48;
    private static final int PROGRESS_HEIGHT = 20;

    private static final int FUEL_X = 100;
    private static final int FUEL_Y = 66;
    private static final int FUEL_WIDTH = 44;
    private static final int FUEL_HEIGHT = 11;

    public TransmogrifierScreen(TransmogrifierMenu menu, Inventory playerInventory, Component title) {
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
        this.renderBackground(graphics);

        Lighting.setupForFlatItems();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (menu.isCrafting()) {
            int width = menu.getScaledProgress(PROGRESS_WIDTH);
            graphics.blit(TEXTURE, leftPos + PROGRESS_X, topPos + PROGRESS_Y, 176, 0, width, PROGRESS_HEIGHT);
        }

        int width = menu.getScaledFuel(FUEL_WIDTH);
        graphics.blit(TEXTURE, leftPos + FUEL_X, topPos + FUEL_Y, 176, 20, width, FUEL_HEIGHT);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
