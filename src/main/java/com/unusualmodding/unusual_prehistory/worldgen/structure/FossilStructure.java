package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2StructureTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public final class FossilStructure extends Structure {

    private final RandomSource random = RandomSource.create();

    public static final Codec<FossilStructure> CODEC = FossilStructure.simpleCodec(FossilStructure::new);

    private static final ResourceLocation[] FOSSILS = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_2"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_3"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_2"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_3"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/fury")
    };

    private static final ResourceLocation[] DEEP_FOSSILS = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_2"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_3"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_2"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_3")
    };

    public FossilStructure(StructureSettings structureSettings) {
        super(structureSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        Rotation rotation = Rotation.getRandom(context.random());
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 0, context.chunkPos().getMinBlockZ());
        BlockPos validPos = new BlockPos(blockPos.getX(), getValidY(context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor(), context.randomState())), blockPos.getZ());
        ResourceLocation fossil = validPos.getY() < 0 ? Util.getRandom(DEEP_FOSSILS, context.random()) : Util.getRandom(FOSSILS, context.random());
        if (validPos.getY() != -51) {
            return Optional.of(new Structure.GenerationStub(validPos, structurePiecesBuilder -> structurePiecesBuilder.addPiece(new FossilPiece(context.structureTemplateManager(), fossil, validPos, rotation))));
        }
        return Optional.empty();
    }

    public int getValidY(NoiseColumn sample) {
        int maxLength = 0;
        int currentLength = 0;
        int maxIndex = -51;
        for (int i = startHeight(); i < 92; i += 1) {
            if (sample.getBlock(i).isAir()) {
                int j = i + 1;
                while (j < 92 && sample.getBlock(j).isAir()) {
                    j++;
                }
                int sequenceLength = j - i;
                if (sequenceLength >= 1) {
                    currentLength += sequenceLength;
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        maxIndex = i;
                    }
                    i = j - 1;
                }
            } else {
                currentLength = 0;
            }
        }
        return maxIndex;
    }

    @Override
    public StructureType<?> type() {
        return UP2StructureTypes.FOSSIL.get();
    }

    public int startHeight() {
        return Mth.randomBetweenInclusive(random, -50, 0);
    }
}