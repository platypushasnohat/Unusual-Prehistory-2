package com.unusualmodding.unusual_prehistory.recipes.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.ArrayList;
import java.util.List;

public class ExtractingRecipeCodec {

    public static Codec<ExtractingRecipeCodec> CODEC = RecordCodecBuilder.create(inst -> inst.group(ExtraCodecs.TAG_OR_ELEMENT_ID.listOf().fieldOf("input").forGetter(i -> i.input), ItemWeightedPairCodec.CODEC.listOf().fieldOf("entries").forGetter(e -> e.itemWeightedPairs)).apply(inst, ExtractingRecipeCodec::new));

    private List<ItemWeightedPairCodec> itemWeightedPairs;
    private List<ExtraCodecs.TagOrElementLocation> input;
    protected final List<ResourceLocation> spawnItems;
    protected final List<ResourceLocation> spawnItemTags;

    public ExtractingRecipeCodec(List<ExtraCodecs.TagOrElementLocation> inputItems, List<ItemWeightedPairCodec> itemWeightedPairs) {
        List<ResourceLocation> spawnItemTags = new ArrayList<>();
        List<ResourceLocation> spawnItems = new ArrayList<>();
        for(ExtraCodecs.TagOrElementLocation tagOrElementLocation : inputItems){
            if(tagOrElementLocation.tag()){
                spawnItemTags.add(tagOrElementLocation.id());
            } else {
                spawnItems.add(tagOrElementLocation.id());
            }
        }
        this.spawnItems = spawnItems;
        this.spawnItemTags = spawnItemTags;
        this.itemWeightedPairs = itemWeightedPairs;
    }

    public List<ItemWeightedPairCodec> getItemWeightedPairs() {
        return itemWeightedPairs;
    }

    public List<ResourceLocation> getInputItemTags() {
        return spawnItemTags;
    }

    public List<ResourceLocation> getInputItems() {
        return spawnItems;
    }

    public void addToList(List<ItemWeightedPairCodec> itemWeightedPairs) {
        this.itemWeightedPairs.addAll(itemWeightedPairs);
    }

    public int getTotalWeight() {
        int totalWeight = 0;
        for (ItemWeightedPairCodec itemWeightedPair : getItemWeightedPairs()) {
            totalWeight += itemWeightedPair.getWeight();
        }
        return totalWeight;
    }
}

