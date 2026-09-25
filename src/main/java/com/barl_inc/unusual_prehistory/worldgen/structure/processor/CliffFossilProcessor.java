package com.barl_inc.unusual_prehistory.worldgen.structure.processor;

import com.barl_inc.unusual_prehistory.registry.UP2StructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class CliffFossilProcessor extends StructureProcessor {

    public static final MapCodec<CliffFossilProcessor> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("sink_by").forGetter(processor -> processor.sinkBy)
            ).apply(instance, CliffFossilProcessor::new)
    );

    private final int sinkBy;

    public CliffFossilProcessor(int sinkBy) {
        this.sinkBy = sinkBy;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return UP2StructureProcessorTypes.CLIFF_FOSSIL_PROCESSOR.get();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos blockPos, BlockPos pos, StructureTemplate.StructureBlockInfo relativeInfo, StructureTemplate.StructureBlockInfo info, StructurePlaceSettings settings) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(info.pos().getX(), pos.getY(), info.pos().getZ());
        mutablePos.move(0, -this.sinkBy, 0);
        while (this.sinkThrough(level.getBlockState(mutablePos)) && mutablePos.getY() > level.getMinBuildHeight()) {
            mutablePos.move(0, -1, 0);
        }
        int i = mutablePos.getY();
        int j = relativeInfo.pos().getY() + 1;
        BlockPos fallTo = new BlockPos(info.pos().getX(), i + j, info.pos().getZ());

        BlockState state = info.state();
        RandomSource random = settings.getRandom(info.pos());
        if (state.is(Blocks.GRAVEL) && random.nextFloat() < 0.4F) {
            if (random.nextFloat() > 0.28F) {
                state = Blocks.COBBLESTONE.defaultBlockState();
            } else {
                state = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
            }
        }

        return new StructureTemplate.StructureBlockInfo(fallTo, state, info.nbt());
    }

    private boolean sinkThrough(BlockState blockState) {
        return !blockState.getFluidState().isEmpty() || blockState.is(BlockTags.REPLACEABLE) || blockState.isAir();
    }
}