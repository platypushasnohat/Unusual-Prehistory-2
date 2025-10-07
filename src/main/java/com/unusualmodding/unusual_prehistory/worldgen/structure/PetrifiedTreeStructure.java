package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2StructureTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public final class PetrifiedTreeStructure extends Structure {

    private final RandomSource random = RandomSource.create();

    public static final Codec<PetrifiedTreeStructure> CODEC = PetrifiedTreeStructure.simpleCodec(PetrifiedTreeStructure::new);

    private static final ResourceLocation[] PETRIFIED_TREES = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "petrified_tree/stump_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "petrified_tree/tall_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "petrified_tree/fallen_0")
    };

    public PetrifiedTreeStructure(StructureSettings structureSettings) {
        super(structureSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        Rotation rotation = Rotation.getRandom(context.random());
        LevelHeightAccessor levelHeight = context.heightAccessor();
        int height = context.chunkGenerator().getBaseHeight(context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), Heightmap.Types.OCEAN_FLOOR_WG, levelHeight, context.randomState()) - 32;
        int maxHeight = height - 32 - context.random().nextInt(16);
        BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), maxHeight, context.chunkPos().getMinBlockZ());
        ResourceLocation petrifiedTrees = Util.getRandom(PETRIFIED_TREES, context.random());
        return Optional.of(new GenerationStub(blockpos, (piecesBuilder -> piecesBuilder.addPiece(new FossilPiece(context.structureTemplateManager(), petrifiedTrees, blockpos, rotation)))));
    }

    @Override
    public StructureType<?> type() {
        return UP2StructureTypes.PETRIFIED_TREE.get();
    }

    public int startHeight() {
        return Mth.randomBetweenInclusive(random, -36, 10);
    }
}