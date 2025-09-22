package com.unusualmodding.unusual_prehistory.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class TransmogrificationRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation resourceLocation;
    private final ItemStack output;
    private final NonNullList<Ingredient> ingredients;

    public TransmogrificationRecipe(ResourceLocation resourceLocation, ItemStack output, NonNullList<Ingredient> ingredients) {
        this.resourceLocation = resourceLocation;
        this.output = output;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return !ingredients.isEmpty() && ingredients.get(0).test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ResourceLocation getId() {
        return resourceLocation;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UP2RecipeTypes.TRANSMOGRIFICATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return UP2RecipeTypes.TRANSMOGRIFICATION.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<TransmogrificationRecipe> {
        @Override
        public TransmogrificationRecipe fromJson(ResourceLocation resourceLocation, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new TransmogrificationRecipe(resourceLocation, output, inputs);
        }

        @Override
        public TransmogrificationRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf data) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(data.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromNetwork(data));
            ItemStack output = data.readItem();
            return new TransmogrificationRecipe(resourceLocation, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf data, TransmogrificationRecipe recipe) {
            data.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(data);
            }
            data.writeItemStack(recipe.output, false);
        }
    }
}
