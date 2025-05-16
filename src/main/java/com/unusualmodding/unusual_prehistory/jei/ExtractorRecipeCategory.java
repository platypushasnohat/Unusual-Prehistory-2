package com.unusualmodding.unusual_prehistory.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import com.unusualmodding.unusual_prehistory.recipes.ExtractorRecipe;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ExtractorRecipeJsonManager;
import com.unusualmodding.unusual_prehistory.recipes.extractor.ItemWeightedPairCodec;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ExtractorRecipeCategory implements IRecipeCategory<ExtractorRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory2.MOD_ID, "extracting");
    public final static ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/extractor.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ExtractorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UP2Blocks.EXTRACTOR.get()));
    }

    @Override
    public RecipeType<ExtractorRecipe> getRecipeType() {
        return JEIPlugin.EXTRACTOR_TYPE;
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

    private final ItemStack EXTRACTOR_BOTTLE = new ItemStack(Items.GLASS_BOTTLE);

    @Override
    public void draw(ExtractorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtractorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 5).addItemStack(EXTRACTOR_BOTTLE);

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack input = ingredient.getItems()[0];

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 5).addIngredients(ingredient);
        List<ItemStack> outputs = new ArrayList<>();
        List<ItemWeightedPairCodec> itemWeightedPairs = ExtractorRecipeJsonManager.getItems(input.getItem());
        for (ItemWeightedPairCodec weightedPair : itemWeightedPairs) {
            outputs.add(weightedPair.getItem().getDefaultInstance());
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 35, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 71, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 89, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 52).addItemStacks(outputs);
    }
}
