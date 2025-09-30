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

    public TransmogrificationBuilder(RecipeSerializer<?> type, Ingredient input, Item output, int processingTime) {
        this.type = type;
        this.input = input;
        this.output = output;
        this.processingTime = processingTime;
    }

    public static TransmogrificationBuilder transmogrification(Ingredient dna, Item output, int processingTime) {
        return new TransmogrificationBuilder(UP2RecipeSerializers.TRANSMOGRIFICATION_SERIALIZER.get(), dna, output, processingTime);
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new TransmogrificationBuilder.Result(resourceLocation, this.type, this.input, this.output, this.processingTime));
    }

    public record Result(ResourceLocation resourceLocation, RecipeSerializer<?> type, Ingredient input, Item output, int processingTime) implements FinishedRecipe {

        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.add("input", input.toJson());
            jsonObject.addProperty("processing_time", processingTime);
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