package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockEntities;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2StructureProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

public class FossilProcessor extends StructureProcessor {

    private static final FossilProcessor INSTANCE = new FossilProcessor();

    public static final Codec<FossilProcessor> CODEC = Codec.unit(() -> FossilProcessor.INSTANCE);

    public FossilProcessor() {}

    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos pos, BlockPos blockPos, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo structureBlockInfo, StructurePlaceSettings settings) {
        BlockState state = info.state();
        if (pos.getY() < 0) {
            if (state.is(Blocks.DEEPSLATE)) {
                state = UP2Blocks.DEEPSLATE_FOSSIL.get().defaultBlockState();
                if (!state.hasBlockEntity()) {
                    level.getBlockEntity(info.pos(), UP2BlockEntities.FOSSIL.get()).ifPresent(fossilBlockEntity -> fossilBlockEntity.setLootTable(new ResourceLocation(UnusualPrehistory2.MOD_ID, "archaeology/deepslate_fossil"), info.pos().asLong()));
                }
                return new StructureTemplate.StructureBlockInfo(info.pos(), state, info.nbt());
            }
        } else {
            if (state.is(Blocks.STONE)) {
                state = UP2Blocks.FOSSIL.get().defaultBlockState();
                if (!state.hasBlockEntity()) {
                    level.getBlockEntity(info.pos(), UP2BlockEntities.FOSSIL.get()).ifPresent(fossilBlockEntity -> fossilBlockEntity.setLootTable(new ResourceLocation(UnusualPrehistory2.MOD_ID, "archaeology/fossil"), info.pos().asLong()));
                }
                return new StructureTemplate.StructureBlockInfo(info.pos(), state, info.nbt());
            }
        }
        return info;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return UP2StructureProcessors.FOSSIL.get();
    }
}