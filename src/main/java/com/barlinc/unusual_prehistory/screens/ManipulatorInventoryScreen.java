//package com.barlinc.unusual_prehistory.screens;
//
//import com.barlinc.unusual_prehistory.UnusualPrehistory2;
//import com.barlinc.unusual_prehistory.entity.mob.future.Manipulator;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
//import net.minecraft.client.gui.screens.inventory.InventoryScreen;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//import org.jetbrains.annotations.NotNull;
//
//public class ManipulatorInventoryScreen extends AbstractContainerScreen<ManipulatorContainer> {
//
//    private static final ResourceLocation GUI_TEXTURE = UnusualPrehistory2.modPrefix("textures/gui/manipulator_inventory.png");
//    private final Manipulator manipulator;
//    private float mousePosX;
//    private float mousePosY;
//
//    public ManipulatorInventoryScreen(ManipulatorContainer container, Inventory playerInventory, Manipulator manipulator) {
//        super(container, playerInventory, manipulator.getDisplayName());
//        this.manipulator = manipulator;
//    }
//
//    @Override
//    protected void renderBg(GuiGraphics graphics, float partialTicks, int x, int y) {
//        int i = (width - imageWidth) / 2;
//        int j = (height - imageHeight) / 2;
//        graphics.blit(GUI_TEXTURE, i, j, 0, 0, imageWidth, imageHeight);
//        if (!this.menu.getSlot(0).hasItem()) {
//            graphics.blit(GUI_TEXTURE, i + 7, j + 35 - 18, 0, imageHeight, 18, 18);
//        }
//        if (!this.menu.getSlot(1).hasItem()) {
//            graphics.blit(GUI_TEXTURE, i + 7, j + 35, 18, imageHeight, 18, 18);
//        }
//        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, i + 62, j + 60, 12, (float) (i + 51) - mousePosX, (float) (j + 75 - 50) - mousePosY, manipulator);
//    }
//
//    @Override
//    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
//        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
//        this.mousePosX = (float) mouseX;
//        this.mousePosY = (float) mouseY;
//        super.render(graphics, mouseX, mouseY, partialTicks);
//        this.renderTooltip(graphics, mouseX, mouseY);
//    }
//}