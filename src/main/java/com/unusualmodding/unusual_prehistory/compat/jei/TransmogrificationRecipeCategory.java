package com.unusualmodding.unusual_prehistory.compat.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.screens.TransmogrifierScreen;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TransmogrificationRecipeCategory implements IRecipeCategory<TransmogrificationRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory2.MOD_ID, "transmogrification");

    public final static ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/jei/transmogrifier_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    protected final IDrawableStatic staticFuel;
    protected final IDrawableAnimated animatedFuel;
    protected final IDrawableStatic staticProgress;
    protected final IDrawableAnimated animatedProgress;

    public TransmogrificationRecipeCategory(IGuiHelper guiHelper) {
        this(guiHelper, 176, 100);
    }

    public TransmogrificationRecipeCategory(IGuiHelper guiHelper, int width, int height) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, width, height);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()));
        staticFuel = guiHelper.createDrawable(TEXTURE, 176, 1, TransmogrifierScreen.FUEL_WIDTH, TransmogrifierScreen.FUEL_HEIGHT);
        animatedFuel = guiHelper.createAnimatedDrawable(staticFuel, 1000, IDrawableAnimated.StartDirection.RIGHT, true);
        staticProgress = guiHelper.createDrawable(TEXTURE, 176, TransmogrifierScreen.FUEL_HEIGHT, TransmogrifierScreen.PROGRESS_WIDTH, TransmogrifierScreen.PROGRESS_HEIGHT);
        animatedProgress = guiHelper.createAnimatedDrawable(staticProgress, 300, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<TransmogrificationRecipe> getRecipeType() {
        return JEIPlugin.TRANSMOGRIFICATION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.unusual_prehistory.transmogrifier");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    protected void drawProgress(TransmogrificationRecipe recipe, GuiGraphics guiGraphics, int y) {
        int cookTime = recipe.getProcessingTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    @Override
    public void draw(TransmogrificationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        drawProgress(recipe, guiGraphics, 45);
        animatedFuel.draw(guiGraphics, 42, 21);
        animatedProgress.draw(guiGraphics, 38, 6);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmogrificationRecipe recipe, IFocusGroup foci) {
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 31).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 64).addItemStack(new ItemStack(UP2Items.ORGANIC_OOZE.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 31).addItemStack(recipe.getJEIResultItem());
    }
}
