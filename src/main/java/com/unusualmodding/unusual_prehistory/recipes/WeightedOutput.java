package com.unusualmodding.unusual_prehistory.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WeightedOutput extends WeightedEntry.IntrusiveBase {

    public static final WeightedOutput DEFAULT = new WeightedOutput(0, ItemStack.EMPTY);

    public static final Codec<WeightedOutput> CODEC = RecordCodecBuilder.<WeightedOutput>create((instance) ->
            instance.group(
                    Weight.CODEC.fieldOf("weight").forGetter(IntrusiveBase::getWeight),
                    ItemStack.CODEC.fieldOf("item").forGetter(WeightedOutput::getStack)
            ).apply(instance, WeightedOutput::new));
    private final ItemStack stack;

    public WeightedOutput(int weight, Item item) {
        this(Weight.of(weight), item.getDefaultInstance());
    }

    public WeightedOutput(int weight, ItemStack itemStack) {
        this(Weight.of(weight), itemStack);
    }

    public WeightedOutput(Weight weight, ItemStack itemStack) {
        super(weight);
        this.stack = itemStack;
    }

    public ItemStack getStack() {
        return stack;
    }
}