package com.unusualmodding.unusual_prehistory.compat.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.*;
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

    public static RecipeType<TransmogrificationRecipe> TRANSMOGRIFICATION_TYPE = new RecipeType<>(TransmogrificationRecipeCategory.UID, TransmogrificationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TransmogrificationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<TransmogrificationRecipe> cultivatingRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.TRANSMOGRIFICATION.get());
        registration.addRecipes(TRANSMOGRIFICATION_TYPE, cultivatingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()), TRANSMOGRIFICATION_TYPE);
    }
}
