package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.items.UP2Items;
import com.unusualmodding.unusual_prehistory.recipes.ExtractingRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class UP2RecipeProvider extends RecipeProvider {

    public UP2RecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ExtractingRecipeBuilder.extracting(UP2Items.FROZEN_MEAT.get(), Items.GLASS_BOTTLE,200)
                .addOutput(UP2Items.MEGALANIA_DNA.get(), 15)
                .addOutput(UP2Items.TELECREX_DNA.get(), 15)
                .addOutput(Items.BEEF, 30)
                .addOutput(Items.BONE, 30)
                .build(consumer);
    }
}
