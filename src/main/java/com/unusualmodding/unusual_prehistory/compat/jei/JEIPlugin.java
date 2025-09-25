package com.unusualmodding.unusual_prehistory.compat.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.*;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Objects;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class JEIPlugin implements IModPlugin {

    public static RecipeType<TransmogrificationRecipe> TRANSMOGRIFICATION = new RecipeType<>(TransmogrificationCategory.UID, TransmogrificationRecipe.class);
    public static final RecipeType<ChiselingRecipe> CHISELING = RecipeType.create(UnusualPrehistory2.MOD_ID, "chiseling", ChiselingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TransmogrificationCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ChiselingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<TransmogrificationRecipe> transmogrificationRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.TRANSMOGRIFICATION.get());
        registration.addRecipes(TRANSMOGRIFICATION, transmogrificationRecipes);

        List<ChiselingRecipe> chiselingRecipes = recipeManager.getAllRecipesFor(UP2RecipeTypes.CHISELING.get());
        registration.addRecipes(CHISELING, chiselingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()), TRANSMOGRIFICATION);
        registration.addRecipeCatalyst(new ItemStack(UP2Items.CHISEL.get()), CHISELING);
    }
}
