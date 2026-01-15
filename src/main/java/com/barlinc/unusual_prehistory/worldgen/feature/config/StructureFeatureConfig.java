package com.barlinc.unusual_prehistory.worldgen.feature.config;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public record StructureFeatureConfig(ResourceLocation processor, ResourceLocation postProcessor, List<Pair<ResourceLocation, Integer>> structureLocationAndWeights, int structureYOffset) implements FeatureConfiguration {

    public static final Codec<StructureFeatureConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            ResourceLocation.CODEC.fieldOf("processors").forGetter(featureConfig -> featureConfig.processor),
            ResourceLocation.CODEC.fieldOf("post_processors").orElse(new ResourceLocation("minecraft:empty")).forGetter(featureConfig -> featureConfig.postProcessor),
            Codec.mapPair(ResourceLocation.CODEC.fieldOf("structure"), ExtraCodecs.POSITIVE_INT.fieldOf("weight")).codec().listOf().fieldOf("structures").forGetter(featureConfig -> featureConfig.structureLocationAndWeights),
            Codec.INT.fieldOf("y_offset").orElse(0).forGetter(featureConfig -> featureConfig.structureYOffset)
    ).apply(configInstance, StructureFeatureConfig::new));

}