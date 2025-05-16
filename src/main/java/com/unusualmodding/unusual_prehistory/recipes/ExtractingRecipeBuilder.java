package com.unusualmodding.unusual_prehistory.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class ExtractingRecipeBuilder {

    private final Ingredient input1;
    private final Item inp;
    private final Ingredient input2;
    private final int processTime;
    private final List<WeightedOutput> outputs = new ArrayList<>();

    private ExtractingRecipeBuilder(Item input1, Item input2, int processTime) {
        this.input1 = Ingredient.of(input1);
        this.inp = input1;
        this.input2 = Ingredient.of(input2);
        this.processTime = processTime;
    }

    public static ExtractingRecipeBuilder extracting(Item input1, Item input2, int processTime) {
        return new ExtractingRecipeBuilder(input1, input2, processTime);
    }

    public ExtractingRecipeBuilder addOutput(Item output, int weight) {
        outputs.add(new WeightedOutput(weight, output));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        WeightedRandomList<WeightedOutput> weightedList = WeightedRandomList.create(outputs);
        CustomRecipeBuilder.analyzing(input1, input2, weightedList, processTime).save(consumer, modPrefix("extracting/" + getItemName(inp)));
    }

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }
}