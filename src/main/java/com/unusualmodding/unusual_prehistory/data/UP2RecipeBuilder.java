package com.unusualmodding.unusual_prehistory.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class UP2RecipeBuilder {

    private final NonNullList<Ingredient> ingredients;
    private final RecipeSerializer<?> type;
    private final Item result;
    private final EntityType<?> entityType;

    public UP2RecipeBuilder(RecipeSerializer<?> pType, NonNullList<Ingredient> ingredients, Item pResult) {
        this.type = pType;
        this.ingredients = ingredients;
        this.result = pResult;
        this.entityType = null;
    }

    public UP2RecipeBuilder(RecipeSerializer<?> pType, NonNullList<Ingredient> ingredients, EntityType<?> pResult) {
        this.type = pType;
        this.ingredients = ingredients;
        this.result = null;
        this.entityType = pResult;
    }

    public static UP2RecipeBuilder cultivating(NonNullList<Ingredient> ingredients, Item pResult) {
        return new UP2RecipeBuilder(UP2RecipeTypes.CULTIVATING_SERIALIZER.get(), ingredients, pResult);
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, String pLocation) {
        this.save(pRecipeConsumer, new ResourceLocation(pLocation));
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        pRecipeConsumer.accept(new UP2RecipeBuilder.Result(pLocation, this.type, this.ingredients , this.result, this.entityType));
    }


    public record Result(ResourceLocation id, RecipeSerializer<?> type, NonNullList<Ingredient> ingredients, Item result, EntityType<?> entityType) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject object) {
            JsonArray jsonarray = new JsonArray();

            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            object.add("ingredients", jsonarray);

            if(type != null){
                object.addProperty("output", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
            }
            if(result !=null) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
                object.add("output", jsonobject);
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