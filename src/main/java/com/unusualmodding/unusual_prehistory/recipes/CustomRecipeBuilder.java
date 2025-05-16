package com.unusualmodding.unusual_prehistory.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CustomRecipeBuilder {

    private final Ingredient input;
    private final Ingredient egg;
    private final RecipeSerializer<?> type;
    private int processTime;
    private final Item result;
    private final WeightedRandomList<WeightedOutput> outputWeightedRandomList;

    public CustomRecipeBuilder(RecipeSerializer<?> pType, Ingredient dna, Ingredient egg, Item pResult, int processTime) {
        this.type = pType;
        this.input = dna;
        this.egg = egg;
        this.result = pResult;
        this.processTime = processTime;
        this.outputWeightedRandomList = null;
    }

    public CustomRecipeBuilder(RecipeSerializer<?> pType, Ingredient input, Ingredient input2, WeightedRandomList<WeightedOutput> outputWeightedRandomList, int processTime) {
        this.type = pType;
        this.input = input;
        this.egg = input2;
        this.result = null;
        this.processTime = processTime;
        this.outputWeightedRandomList = outputWeightedRandomList;
    }

//    public static CustomRecipeBuilder cultivating(Ingredient dna, Ingredient egg, Item pResult, int processTime) {
//        return new CustomRecipeBuilder(UP2RecipeTypes.CULTIVATOR.get(), dna, egg, pResult, processTime);
//    }

    public static CustomRecipeBuilder analyzing(Ingredient input1, Ingredient input2, WeightedRandomList<WeightedOutput> pResult, int processTime) {
        return new CustomRecipeBuilder(UP2RecipeTypes.EXTRACTING_RECIPE_SERIALIZER.get(), input1, input2, pResult, processTime);
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, String pLocation) {
        this.save(pRecipeConsumer, new ResourceLocation(pLocation));
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        pRecipeConsumer.accept(new Result(pLocation, this.type, this.input, this.egg, this.result,outputWeightedRandomList ,this.processTime));
    }

    public static record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient input, Ingredient egg, Item result, WeightedRandomList<WeightedOutput> outputWeightedRandomList, int processTime) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject jsonObject) {
            JsonArray jsonarray = new JsonArray();
            if(input != null) {
                jsonObject.add("input", input.toJson());
            }
            if(egg != null){
                jsonObject.add("extra_input", egg.toJson());
            }

            if (outputWeightedRandomList != null) {
                JsonElement outputElement = WeightedRandomList.codec(WeightedOutput.CODEC)
                        .encodeStart(JsonOps.INSTANCE, outputWeightedRandomList)
                        .resultOrPartial(System.err::println)
                        .orElseThrow(() -> new IllegalStateException("Failed to encode output for" + id));
                jsonObject.add("output", outputElement);
            }

            jsonObject.addProperty("process_time", processTime);
            if (result != null) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
                jsonObject.add("output", jsonobject);
            }
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
