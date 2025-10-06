package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2StructureTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public final class FossilStructure extends Structure {

    public static final Codec<FossilStructure> CODEC = FossilStructure.simpleCodec(FossilStructure::new);

    private static final ResourceLocation[] FOSSILS = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_2")
    };

    public FossilStructure(StructureSettings structureSettings) {
        super(structureSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int buryDepth = 1 + context.random().nextInt(3);
        Rotation rotation = Rotation.getRandom(context.random());
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 0, context.chunkPos().getMinBlockZ());
        BlockPos validPos = new BlockPos(blockPos.getX(), getValidY(context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor(), context.randomState())), blockPos.getZ());
        ResourceLocation fossil = Util.getRandom(FOSSILS, context.random());
        if (validPos.getY() != -51) {
            return Optional.of(new Structure.GenerationStub(validPos.below(buryDepth), structurePiecesBuilder -> structurePiecesBuilder.addPiece(new FossilPiece(context.structureTemplateManager(), fossil, validPos, rotation))));
        }
        return Optional.empty();
    }

    public int getValidY(NoiseColumn sample) {
        int maxLength = 0;
        int currentLength = 0;
        int maxIndex = -51;
        for (int i = -50; i < 50; i += 1) {
            if (sample.getBlock(i).isAir()) {
                int j = i + 1;
                while (j < 50 && sample.getBlock(j).isAir() || sample.getBlock(j).is(BlockTags.REPLACEABLE) || sample.getBlock(j).getFluidState().is(FluidTags.WATER) || sample.getBlock(j).getFluidState().is(FluidTags.LAVA)) {
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
}