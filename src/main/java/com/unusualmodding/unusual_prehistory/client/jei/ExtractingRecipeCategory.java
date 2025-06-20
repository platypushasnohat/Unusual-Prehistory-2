package com.unusualmodding.unusual_prehistory.client.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.ExtractingRecipe;
import com.unusualmodding.unusual_prehistory.recipes.data.ExtractingRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.recipes.data.ItemWeightedPairCodec;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
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
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ExtractingRecipeCategory implements IRecipeCategory<ExtractingRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory2.MOD_ID, "extracting");
    public final static ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/extractor.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ExtractingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UP2Blocks.EXTRACTOR.get()));
    }

    @Override
    public RecipeType<ExtractingRecipe> getRecipeType() {
        return JEIPlugin.EXTRACTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(UnusualPrehistory2.MOD_ID + ".blockentity.extractor_jei");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(ExtractingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtractingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 5).addItemStack(new ItemStack(Items.GLASS_BOTTLE));

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack input = ingredient.getItems()[0];

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 5).addIngredients(ingredient);
        List<ItemStack> outputs = new ArrayList<>();
        List<ItemWeightedPairCodec> itemWeightedPairs = ExtractingRecipeJsonManager.getItems(input.getItem());

        for (ItemWeightedPairCodec weightedPair : itemWeightedPairs) {
            outputs.add(new ItemStack(weightedPair.getItem()));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 35, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 71, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 89, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 52).addItemStacks(outputs);
    }
}
