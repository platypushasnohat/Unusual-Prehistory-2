package com.unusualmodding.unusual_prehistory.client.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.CultivatingRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CultivatingRecipeCategory implements IRecipeCategory<CultivatingRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory2.MOD_ID, "cultivating");

    public final static ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/cultivator.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CultivatingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UP2Blocks.CULTIVATOR.get()));
    }

    @Override
    public RecipeType<CultivatingRecipe> getRecipeType() {
        return JEIPlugin.CULTIVATING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(UnusualPrehistory2.MOD_ID + ".blockentity.cultivator_jei");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    private final ItemStack ORGANIC_OOZE = new ItemStack(UP2Items.ORGANIC_OOZE.get());
    private final ItemStack BOTTLE = new ItemStack(Items.GLASS_BOTTLE);

    @Override
    public void draw(CultivatingRecipe recipe, IRecipeSlotsView slots, GuiGraphics stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, slots, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CultivatingRecipe recipe, IFocusGroup foci) {
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 31).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 64).addItemStack(ORGANIC_OOZE);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 31).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 152, 64).addItemStack(BOTTLE);
    }
}
