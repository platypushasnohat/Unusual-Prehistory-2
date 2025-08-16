package com.unusualmodding.unusual_prehistory.client.book.recipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface SpecialRecipeInGuideBook {
    NonNullList<Ingredient> getDisplayIngredients();

    ItemStack getDisplayResultFor(NonNullList<ItemStack> stacks);
}
