package com.unusualmodding.unusual_prehistory.recipes;

import com.google.gson.JsonObject;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TransmogrificationRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    protected final Ingredient input;
    protected final ItemStack result;
    protected final int processingTime;
    private final NonNullList<Ingredient> recipeItems = NonNullList.create();

    public TransmogrificationRecipe(ResourceLocation id, Ingredient input, ItemStack result, int processingTime) {
        this.id = id;
        this.input = input;
        this.result = result;
        this.processingTime = processingTime;
        recipeItems.add(input);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return input.test(!container.getItem(0).isEmpty() ? container.getItem(0) : ItemStack.EMPTY);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    public ItemStack getJEIResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UP2RecipeTypes.TRANSMOGRIFICATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return UP2RecipeTypes.TRANSMOGRIFICATION.get();
    }

    public static class Serializer implements RecipeSerializer<TransmogrificationRecipe> {
        @Override
        public TransmogrificationRecipe fromJson(ResourceLocation resourceLocation, JsonObject pJson) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson,"input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "output"));
            int processingTime = GsonHelper.getAsInt(pJson, "processing_time", 100);
            return new TransmogrificationRecipe(resourceLocation, input, output, processingTime);
        }

        @Override
        public @Nullable TransmogrificationRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient input = Ingredient.fromNetwork(friendlyByteBuf);
            ItemStack result = friendlyByteBuf.readItem();
            int processingTime = friendlyByteBuf.readVarInt();
            return new TransmogrificationRecipe(resourceLocation, input, result, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, TransmogrificationRecipe recipe) {
            recipe.input.toNetwork(friendlyByteBuf);
            friendlyByteBuf.writeItem(recipe.result);
            friendlyByteBuf.writeVarInt(recipe.processingTime);
        }
    }
}
