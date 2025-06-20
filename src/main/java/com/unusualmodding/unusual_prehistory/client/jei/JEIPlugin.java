package com.unusualmodding.unusual_prehistory.client.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.CultivatingRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
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

    public static RecipeType<CultivatingRecipe> CULTIVATOR_TYPE = new RecipeType<>(CultivatingRecipeCategory.UID, CultivatingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CultivatingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<CultivatingRecipe> recipesCultivator = recipeManager.getAllRecipesFor(UP2RecipeTypes.CULTIVATING_RECIPE.get());
        registration.addRecipes(CULTIVATOR_TYPE, recipesCultivator);
    }
}
