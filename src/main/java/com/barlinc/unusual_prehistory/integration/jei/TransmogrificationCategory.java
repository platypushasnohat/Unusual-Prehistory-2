package com.barlinc.unusual_prehistory.integration.jei;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.screens.TransmogrifierScreen;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
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
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class TransmogrificationCategory implements IRecipeCategory<TransmogrificationRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory2.MOD_ID, "transmogrification");

    public final static ResourceLocation BACKGROUND = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/transmogrifier.png");

    private final IDrawable background;
    private final IDrawable icon;

    protected final IDrawableAnimated fuel;
    protected final IDrawableAnimated progress;

    public TransmogrificationCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(BACKGROUND, 32, 28, 116, 52);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()));
        this.fuel = guiHelper.drawableBuilder(BACKGROUND, 176, 0, TransmogrifierScreen.FUEL_WIDTH, TransmogrifierScreen.FUEL_HEIGHT).buildAnimated(800, IDrawableAnimated.StartDirection.RIGHT, true);
        this.progress = guiHelper.drawableBuilder(BACKGROUND, 176, 14, TransmogrifierScreen.PROGRESS_WIDTH, TransmogrifierScreen.PROGRESS_HEIGHT).buildAnimated(1200, IDrawableAnimated.StartDirection.LEFT, false);;
    }

    @Override
    public @NotNull RecipeType<TransmogrificationRecipe> getRecipeType() {
        return JEIPlugin.TRANSMOGRIFICATION;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(UnusualPrehistory2.MOD_ID + "." + "jei.transmogrification");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    protected void drawProgress(TransmogrificationRecipe recipe, GuiGraphics guiGraphics, int y, int x) {
        int cookTime = recipe.getProcessingTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, (getWidth() - stringWidth) + x, y, 0xFF808080, false);
        }
    }

    @Override
    public void draw(@NotNull TransmogrificationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        drawProgress(recipe, guiGraphics, 24, -91);
        this.fuel.draw(guiGraphics, 70, 32);
        this.progress.draw(guiGraphics, 30, 1);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmogrificationRecipe recipe, @NotNull IFocusGroup foci) {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 3).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 31).addItemStack(new ItemStack(UP2Items.ORGANIC_OOZE.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 3).addItemStack(recipe.getJEIResultItem());
    }
}
