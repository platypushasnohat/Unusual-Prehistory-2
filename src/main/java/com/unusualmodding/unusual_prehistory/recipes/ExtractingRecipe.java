package com.unusualmodding.unusual_prehistory.recipes;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class ExtractingRecipe implements Recipe<Container> {

    private static final Logger LOGGER = LogUtils.getLogger();
    private final ResourceLocation id;
    private final WeightedRandomList<WeightedOutput> outputList;
    private final int processTime;
    private final Ingredient input;
    private final Ingredient input2;

    public ExtractingRecipe(ResourceLocation id, Ingredient input, Ingredient input2, WeightedRandomList<WeightedOutput> outputWeightedRandomList, int processTime) {
        this.id = id;
        this.input = input;
        this.input2 = input2;
        this.outputList = outputWeightedRandomList;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return input.test(!container.getItem(0).isEmpty() ? container.getItem(0) : ItemStack.EMPTY) && input2.test(!container.getItem(1).isEmpty() ? container.getItem(1) : ItemStack.EMPTY);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UP2RecipeTypes.EXTRACTING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return UP2RecipeTypes.EXTRACTING_RECIPE.get();
    }

    public ItemStack getOutputItem(RandomSource randomSource) {
        return outputList.getRandom(randomSource).orElse(WeightedOutput.DEFAULT).getStack();
    }

    public int getProcessTime() {
        return processTime;
    }

    public static class Serializer implements RecipeSerializer<ExtractingRecipe> {
        @Override
        public ExtractingRecipe fromJson(ResourceLocation resourceLocation, JsonObject pJson) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "input"));
            Ingredient input2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "extra_input"));
            WeightedRandomList<WeightedOutput> results = WeightedRandomList.codec(WeightedOutput.CODEC).parse(JsonOps.INSTANCE, pJson.get("output"))
                    .resultOrPartial(e -> LOGGER.error("[ExtractingRecipe] Failed to parse recipe results for {}", resourceLocation))
                    .orElse(WeightedRandomList.create());

            int processTime = GsonHelper.getAsInt(pJson, "process_time", 1000);

            return new ExtractingRecipe(resourceLocation, input, input2, results, processTime);
        }

        @Override
        public @Nullable ExtractingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient input = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient input2 = Ingredient.fromNetwork(friendlyByteBuf);

            final WeightedRandomList<WeightedOutput> results = friendlyByteBuf.readWithCodec(NbtOps.INSTANCE, WeightedRandomList.codec(WeightedOutput.CODEC));
            int processTime = friendlyByteBuf.readVarInt();
            return new ExtractingRecipe(resourceLocation, input, input2, results, processTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, ExtractingRecipe recipe) {
            recipe.input.toNetwork(friendlyByteBuf);
            recipe.input2.toNetwork(friendlyByteBuf);
            friendlyByteBuf.writeWithCodec(NbtOps.INSTANCE, WeightedRandomList.codec(WeightedOutput.CODEC), recipe.outputList);
            friendlyByteBuf.writeVarInt(recipe.processTime);
        }
    }
}
