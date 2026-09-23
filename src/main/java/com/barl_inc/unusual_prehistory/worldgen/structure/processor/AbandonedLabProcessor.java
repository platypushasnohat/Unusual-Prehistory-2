package com.barl_inc.unusual_prehistory.worldgen.structure.processor;

import com.barl_inc.unusual_prehistory.registry.UP2StructureProcessorTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class AbandonedLabProcessor extends StructureProcessor {

    public static final MapCodec<AbandonedLabProcessor> CODEC = MapCodec.unit(AbandonedLabProcessor::new);

    public AbandonedLabProcessor() {
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return UP2StructureProcessorTypes.ABANDONED_LAB_PROCESSOR.get();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos blockPos, BlockPos pos, StructureTemplate.StructureBlockInfo relativeInfo, StructureTemplate.StructureBlockInfo info, StructurePlaceSettings settings) {
        BlockState state = info.state();
        RandomSource random = settings.getRandom(info.pos());
        if (state.is(Blocks.COBBLESTONE) && random.nextFloat() < 0.25F) {
            return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), info.nbt());
        }
        if (state.is(Blocks.STONE_BRICKS) && random.nextFloat() < 0.5F) {
            if (random.nextFloat() > 0.25F) {
                return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), info.nbt());
            } else {
                return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), info.nbt());
            }
        }
        return info;
    }
}