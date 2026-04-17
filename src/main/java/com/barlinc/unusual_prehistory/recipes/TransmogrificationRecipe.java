package com.barlinc.unusual_prehistory.recipes;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Recipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record TransmogrificationRecipe(Ingredient ingredient, ItemStack result, float experience, int processingTime) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, @NotNull Level level) {
        return this.ingredient.test(input.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return this.result;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return UP2Recipes.TRANSMOGRIFICATION.get();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(UP2Blocks.TRANSMOGRIFIER.get());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return UP2Recipes.TRANSMOGRIFICATION_SERIALIZER.get();
    }

    public interface Factory<T extends TransmogrificationRecipe> {
        T create(Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }
}

