package com.unusualmodding.unusual_prehistory.recipes;

import com.google.gson.JsonObject;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeSerializers;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ChiselingRecipe implements Recipe<Container> {

    protected final ResourceLocation recipeID;
    protected final BlockState input;
    protected final BlockState output;

    public ChiselingRecipe(ResourceLocation recipeID, BlockState input, BlockState output) {
        this.recipeID = recipeID;
        this.input = input;
        this.output = output;
    }

    public BlockState getInput() {
        return input;
    }

    public BlockState getOutput() {
        return output;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeID;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UP2RecipeSerializers.CHISELING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return UP2RecipeTypes.CHISELING.get();
    }

    public static class Serializer implements RecipeSerializer<ChiselingRecipe> {

        @Override
        public ChiselingRecipe fromJson(ResourceLocation id, JsonObject object) {
            Block input = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(GsonHelper.getAsString(object, "from")));
            Block output = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(GsonHelper.getAsString(object, "to")));
            if (input != null && output != null) {
                return new ChiselingRecipe(id, input.defaultBlockState(), output.defaultBlockState());
            }
            return new ChiselingRecipe(id, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState());
        }

        @Nullable
        @Override
        public ChiselingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            Block input = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS);
            Block output = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS);
            return new ChiselingRecipe(id, input.defaultBlockState(), output.defaultBlockState());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ChiselingRecipe recipe) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.input.getBlock());
            buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.output.getBlock());
        }
    }
}