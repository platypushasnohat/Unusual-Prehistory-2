package com.barlinc.unusual_prehistory.worldgen.structure.processor;

import com.barlinc.unusual_prehistory.registry.UP2StructureProcessors;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class TreeStructureProcessor extends StructureProcessor {

    public static final MapCodec<TreeStructureProcessor> CODEC = MapCodec.unit(() -> TreeStructureProcessor.INSTANCE);

    public static final TreeStructureProcessor INSTANCE = new TreeStructureProcessor();

    public TreeStructureProcessor() {
    }

    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(@NotNull LevelReader level, @NotNull BlockPos blockPosUnused, @NotNull BlockPos pos, StructureTemplate.@NotNull StructureBlockInfo relativeInfo, StructureTemplate.@NotNull StructureBlockInfo info, @NotNull StructurePlaceSettings settings) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(info.pos().getX(), info.pos().getY(), info.pos().getZ());
        if (this.canReplace(level.getBlockState(mutableBlockPos))) {
            return new StructureTemplate.StructureBlockInfo(info.pos(), info.state(), info.nbt());
        }
        return null;
    }

    private boolean canReplace(BlockState blockState) {
        return blockState.isAir() || blockState.is(BlockTags.REPLACEABLE_BY_TREES);
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return UP2StructureProcessors.TREE.get();
    }
}