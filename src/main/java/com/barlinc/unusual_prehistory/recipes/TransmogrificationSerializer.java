package com.barlinc.unusual_prehistory.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class TransmogrificationSerializer<T extends TransmogrificationRecipe> implements RecipeSerializer<T> {

    private final TransmogrificationRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public TransmogrificationSerializer(TransmogrificationRecipe.Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(TransmogrificationRecipe::ingredient),
                        ItemStack.CODEC.fieldOf("output").forGetter(TransmogrificationRecipe::result),
                        Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(TransmogrificationRecipe::experience),
                        Codec.INT.fieldOf("processing_time").orElse(1200).forGetter(TransmogrificationRecipe::processingTime)
                    )
                    .apply(instance, factory::create)
        );
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    public @NotNull MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    private T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
        float f = buffer.readFloat();
        int i = buffer.readVarInt();
        return this.factory.create(ingredient, itemstack, f, i);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
        buffer.writeFloat(recipe.experience());
        buffer.writeVarInt(recipe.processingTime());
    }

    public TransmogrificationRecipe create(Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        return this.factory.create(ingredient, result, experience, cookingTime);
    }
}