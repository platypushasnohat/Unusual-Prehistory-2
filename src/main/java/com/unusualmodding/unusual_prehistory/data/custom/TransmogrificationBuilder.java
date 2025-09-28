package com.unusualmodding.unusual_prehistory.data.custom;

import com.google.gson.JsonObject;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TransmogrificationBuilder {

    private final Ingredient input;
    private final RecipeSerializer<?> type;
    private final Item output;
    private final int processingTime;
    private final float experience;

    public TransmogrificationBuilder(RecipeSerializer<?> type, Ingredient input, Item output, int processingTime, float experience) {
        this.type = type;
        this.input = input;
        this.output = output;
        this.processingTime = processingTime;
        this.experience = experience;
    }

    public static TransmogrificationBuilder transmogrification(Ingredient dna, Item output, int processingTime, float experience) {
        return new TransmogrificationBuilder(UP2RecipeSerializers.TRANSMOGRIFICATION_SERIALIZER.get(), dna, output, processingTime, experience);
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new TransmogrificationBuilder.Result(resourceLocation, this.type, this.input, this.output, this.processingTime, this.experience));
    }

    public record Result(ResourceLocation resourceLocation, RecipeSerializer<?> type, Ingredient input, Item output, int processingTime, float experience) implements FinishedRecipe {

        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.add("input", input.toJson());
            jsonObject.addProperty("processing_time", processingTime);
            jsonObject.addProperty("experience", experience);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.output).toString());
            jsonObject.add("output", jsonobject);
        }

        public ResourceLocation getId() {
            return this.resourceLocation;
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