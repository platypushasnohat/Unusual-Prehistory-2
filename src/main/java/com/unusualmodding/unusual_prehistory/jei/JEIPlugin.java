package com.unusualmodding.unusual_prehistory.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.CultivatorRecipe;
import com.unusualmodding.unusual_prehistory.recipes.ExtractorRecipe;
import com.unusualmodding.unusual_prehistory.recipes.UP2Recipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static RecipeType<ExtractorRecipe> EXTRACTOR_TYPE = new RecipeType<>(ExtractorRecipeCategory.UID, ExtractorRecipe.class);
    public static RecipeType<CultivatorRecipe> CULTIVATOR_TYPE = new RecipeType<>(CultivatorRecipeCategory.UID, CultivatorRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CultivatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<ExtractorRecipe> recipesAnalyzer = rm.getAllRecipesFor(ExtractorRecipe.Type.INSTANCE);
        registration.addRecipes(EXTRACTOR_TYPE, recipesAnalyzer);

        List<CultivatorRecipe> recipesCultivator = rm.getAllRecipesFor(UP2Recipes.CULTIVATOR_RECIPE.get());
        registration.addRecipes(CULTIVATOR_TYPE, recipesCultivator);
    }
}
