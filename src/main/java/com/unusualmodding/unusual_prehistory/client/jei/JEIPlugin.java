package com.unusualmodding.unusual_prehistory.client.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.ExtractingRecipe;
import com.unusualmodding.unusual_prehistory.recipes.CultivatingRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static RecipeType<CultivatingRecipe> CULTIVATING_TYPE = new RecipeType<>(CultivatingRecipeCategory.UID, CultivatingRecipe.class);
    public static RecipeType<ExtractingRecipe> EXTRACTING_TYPE = new RecipeType<>(ExtractingRecipeCategory.UID, ExtractingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CultivatingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ExtractingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<CultivatingRecipe> cultivatingRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.CULTIVATING_RECIPE.get());
        registration.addRecipes(CULTIVATING_TYPE, cultivatingRecipes);

        List<ExtractingRecipe> extractingRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.EXTRACTING_RECIPE.get());
        registration.addRecipes(EXTRACTING_TYPE, extractingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(UP2Blocks.CULTIVATOR.get()), CULTIVATING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(UP2Blocks.EXTRACTOR.get()), EXTRACTING_TYPE);
    }
}
