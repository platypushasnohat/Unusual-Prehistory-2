package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2LootTables;
import com.unusualmodding.unusual_prehistory.registry.UP2StructureProcessorTypes;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class PetrifiedTreeProcessor extends StructureProcessor {

    public static final Codec<PetrifiedTreeProcessor> CODEC = Codec.unit(PetrifiedTreeProcessor::new);

    public PetrifiedTreeProcessor() {
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos blockPos, BlockPos pos, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo info, StructurePlaceSettings settings) {
        RandomSource random = settings.getRandom(info.pos());
        BlockState blockState = info.state();
        CompoundTag compoundTag = new CompoundTag();

        if (blockState.is(UP2BlockTags.PETRIFIED_WOOD) && level.getBiome(blockPos).is(Biomes.LUSH_CAVES) && random.nextFloat() < 0.1F){
            return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.MOSS_BLOCK.defaultBlockState(), info.nbt());
        }

        if (blockState.is(UP2BlockTags.PETRIFIED_WOOD) && level.getBiome(blockPos).is(Biomes.DRIPSTONE_CAVES) && random.nextFloat() < 0.1F){
            return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.DRIPSTONE_BLOCK.defaultBlockState(), info.nbt());
        }

        if (blockPos.getY() < 0) {
            if (blockState.is(UP2BlockTags.PETRIFIED_WOOD) && random.nextFloat() < 0.08F) {
                compoundTag.putString("LootTable", UP2LootTables.DEEPSLATE_FOSSIL.toString());
                compoundTag.putLong("LootTableSeed", random.nextLong());
                return new StructureTemplate.StructureBlockInfo(info.pos(), UP2Blocks.DEEPSLATE_FOSSIL.get().defaultBlockState(), compoundTag);
            }
        } else {
            if (blockState.is(UP2BlockTags.PETRIFIED_WOOD) && random.nextFloat() < 0.08F) {
                compoundTag.putString("LootTable", UP2LootTables.FOSSIL.toString());
                compoundTag.putLong("LootTableSeed", random.nextLong());
                return new StructureTemplate.StructureBlockInfo(info.pos(), UP2Blocks.FOSSIL.get().defaultBlockState(), compoundTag);
            }
        }
        return info;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return UP2StructureProcessorTypes.PETRIFIED_TREE.get();
    }
}