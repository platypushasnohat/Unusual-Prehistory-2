package com.unusualmodding.unusual_prehistory.recipes.extractor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemWeightedPairCodec {

    public static Codec<ItemWeightedPairCodec> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(item -> item.item),
                    Codec.INT.fieldOf("weight").forGetter(weight -> weight.weight),
                    Codec.INT.optionalFieldOf("amount", 1).forGetter(amount -> amount.amount)
            ).apply(instance, ItemWeightedPairCodec::new)
    );

    private Item item;
    private int weight;
    private int amount;

    public ItemWeightedPairCodec(Item item, int weight, int amount) {
        this.item = item;
        this.weight = weight;
        this.amount = amount;
    }

    public int getWeight() {
        return weight;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public static <T> Map<T, List<ItemWeightedPairCodec>> convertToMap(Map<T, List<ItemWeightedPairCodec>> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                        .map(itemWeightedPairCodec -> new ItemWeightedPairCodec(
                                itemWeightedPairCodec.getItem(),
                                itemWeightedPairCodec.getWeight(),
                                itemWeightedPairCodec.getAmount()
                        ))
                        .collect(Collectors.toList())
        ));
    }

    public static <T> Map<T, List<ItemWeightedPairCodec>> convertFromMap(Map<T, List<ItemWeightedPairCodec>> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                        .map(itemWeightedPair -> new ItemWeightedPairCodec(
                                itemWeightedPair.getItem(),
                                itemWeightedPair.getWeight(),
                                itemWeightedPair.getAmount()
                        ))
                        .collect(Collectors.toList())
        ));
    }
}

