package com.unusualmodding.unusual_prehistory.worldgen.feature;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class FossilPatchFeature extends Feature<SimpleBlockConfiguration> {

    public FossilPatchFeature(Codec<SimpleBlockConfiguration> configuration) {
        super(configuration);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleblockconfiguration = context.config();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(context.random(), blockpos);
        if (blockstate.is(UP2Blocks.FOSSIL.get()) || blockstate.is(UP2Blocks.DEEPSLATE_FOSSIL.get())) {
            worldgenlevel.setBlock(blockpos, blockstate, 2);
            context.level().getBlockEntity(blockpos, UP2BlockEntities.SUSPICIOUS_STONE.get()).ifPresent(brushableBlockEntity -> brushableBlockEntity.setLootTable(new ResourceLocation(UnusualPrehistory2.MOD_ID, "archaeology/fossil"), blockpos.asLong()));
            return true;
        } else {
            return false;
        }
    }
}