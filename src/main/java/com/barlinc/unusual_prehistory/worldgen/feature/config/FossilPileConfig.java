package com.barlinc.unusual_prehistory.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record FossilPileConfig(BlockState state, BlockState floorState, Holder<PlacedFeature> feature) implements FeatureConfiguration {

    public static final Codec<FossilPileConfig> CODEC = RecordCodecBuilder.create((configInstance) ->
            configInstance.group(
                            BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state),
                            BlockState.CODEC.fieldOf("floor_state").forGetter((config) -> config.floorState),
                            PlacedFeature.CODEC.fieldOf("feature").forGetter((config) -> config.feature))
                    .apply(configInstance, FossilPileConfig::new)
    );

}