package com.barl_inc.unusual_prehistory.integration.jei;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.screen.TransmogrifierScreen;
import com.barl_inc.unusual_prehistory.inventory.TransmogrifierMenu;
import com.barl_inc.unusual_prehistory.recipe.TransmogrificationRecipe;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Menus;
import com.barl_inc.unusual_prehistory.registry.UP2RecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static RecipeType<TransmogrificationRecipe> TRANSMOGRIFICATION = new RecipeType<>(TransmogrificationCategory.UID, TransmogrificationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return UnusualPrehistory2.location("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TransmogrificationCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<TransmogrificationRecipe> transmogrificationRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.TRANSMOGRIFICATION.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(TRANSMOGRIFICATION, transmogrificationRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()), TRANSMOGRIFICATION);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(TransmogrifierScreen.class, 62, 29, 54, 20, TRANSMOGRIFICATION);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(TransmogrifierMenu.class, UP2Menus.TRANSMOGRIFIER.get(), TRANSMOGRIFICATION, 1, 1, 3, 36);
    }
}
