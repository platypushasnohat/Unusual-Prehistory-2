package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.ChiselingRecipe;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2RecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<RecipeType<TransmogrificationRecipe>> TRANSMOGRIFICATION = RECIPE_TYPES.register("transmogrification", () -> new SimpleNamedRecipeType<>("transmogrification"));
    public static final RegistryObject<RecipeType<ChiselingRecipe>> CHISELING = RECIPE_TYPES.register("chiseling", () -> new SimpleNamedRecipeType<>("chiseling"));

    public record SimpleNamedRecipeType<T extends Recipe<?>>(String name) implements RecipeType<T> {
        @Override
        public String toString() {
            return name;
        }
    }
}
