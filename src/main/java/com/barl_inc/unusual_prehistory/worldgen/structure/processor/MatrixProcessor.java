package com.barl_inc.unusual_prehistory.worldgen.structure.processor;

import com.barl_inc.unusual_prehistory.block.MatrixBlock;
import com.barl_inc.unusual_prehistory.registry.UP2StructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class MatrixProcessor extends StructureProcessor {

    public static final MapCodec<MatrixProcessor> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BlockState.CODEC.fieldOf("target_state").forGetter(processor -> processor.targetState),
                    BlockState.CODEC.fieldOf("matrix_state").forGetter(processor -> processor.matrixState),
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(processor -> processor.lootTable),
                    Codec.STRING.fieldOf("rarity").forGetter(processor -> processor.rarity)
            ).apply(instance, MatrixProcessor::new)
    );

    public final BlockState targetState;
    public final BlockState matrixState;
    public final ResourceLocation lootTable;
    public final String rarity;

    public MatrixProcessor(BlockState targetState, BlockState matrixState, ResourceLocation lootTable, String rarity) {
        this.targetState = targetState;
        this.matrixState = matrixState;
        this.lootTable = lootTable;
        this.rarity = rarity;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return UP2StructureProcessorTypes.MATRIX_PROCESSOR.get();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings) {
        if (relativeBlockInfo.state().getBlock() != this.targetState.getBlock()) {
            return relativeBlockInfo;
        }
        CompoundTag compoundTag = null;
        if (this.matrixState.getBlock() instanceof MatrixBlock) {
            compoundTag = new CompoundTag();
            RandomSource random = settings.getRandom(relativeBlockInfo.pos());
            compoundTag.putString("LootTable", this.lootTable.toString());
            compoundTag.putLong("LootTableSeed", random.nextLong());
            compoundTag.putString("Rarity", this.rarity);
        }
        return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), this.matrixState, compoundTag);
    }
}
