package com.unusualmodding.unusual_prehistory.data.custom;

import com.google.gson.JsonObject;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ChiselingBuilder {

    private final Block from;
    private final Block to;
    private final RecipeSerializer<?> type;

    public ChiselingBuilder(RecipeSerializer<?> type, Block from, Block to) {
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public static ChiselingBuilder chiseling(Block from, Block to) {
        return new ChiselingBuilder(UP2RecipeSerializers.CHISELING_SERIALIZER.get(), from, to);
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new ChiselingBuilder.Result(resourceLocation, this.type, this.from, this.to));
    }

    public record Result(ResourceLocation resourceLocation, RecipeSerializer<?> type, Block from, Block to) implements FinishedRecipe {

        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.addProperty("from", ForgeRegistries.BLOCKS.getKey(from.defaultBlockState().getBlock()).toString());
            jsonObject.addProperty("to", ForgeRegistries.BLOCKS.getKey(to.defaultBlockState().getBlock()).toString());
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