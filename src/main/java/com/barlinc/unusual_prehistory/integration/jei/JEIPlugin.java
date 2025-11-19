package com.barlinc.unusual_prehistory.integration.jei;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2RecipeTypes;
import com.barlinc.unusual_prehistory.screens.TransmogrifierScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Objects;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class JEIPlugin implements IModPlugin {

    public static RecipeType<TransmogrificationRecipe> TRANSMOGRIFICATION = new RecipeType<>(TransmogrificationCategory.UID, TransmogrificationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TransmogrificationCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<TransmogrificationRecipe> transmogrificationRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.TRANSMOGRIFICATION.get());
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

//    @Override
//    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
//        registration.addRecipeTransferHandler(TransmogrifierMenu.class, UP2MenuTypes.TRANSMOGRIFIER.get(), TRANSMOGRIFICATION, 1, 1, 3, 36);
//    }
}
