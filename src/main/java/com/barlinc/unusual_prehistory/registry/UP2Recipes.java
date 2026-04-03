package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.recipes.TransmogrificationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class UP2Recipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<RecipeType<TransmogrificationRecipe>> TRANSMOGRIFICATION = RECIPE_TYPES.register("transmogrification", () -> new SimpleNamedRecipeType<>("transmogrification"));
    public static final RegistryObject<RecipeSerializer<TransmogrificationRecipe>> TRANSMOGRIFICATION_SERIALIZER = RECIPE_SERIALIZERS.register("transmogrification", TransmogrificationRecipe.Serializer::new);

    public record SimpleNamedRecipeType<T extends Recipe<?>>(String name) implements RecipeType<T> {
        @Override
        public @NotNull String toString() {
            return name;
        }
    }
}
