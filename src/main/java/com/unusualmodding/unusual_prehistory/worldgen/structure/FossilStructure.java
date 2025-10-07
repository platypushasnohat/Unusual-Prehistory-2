package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2StructureTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public final class FossilStructure extends Structure {

    public static final Codec<FossilStructure> CODEC = FossilStructure.simpleCodec(FossilStructure::new);

    private static final ResourceLocation[] FOSSILS = new ResourceLocation[] {
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_2"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_3"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_4"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/spine_5"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_0"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_1"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_2"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/skull_3"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/fury"),
            new ResourceLocation(UnusualPrehistory2.MOD_ID, "fossil/boomerang")
    };

    public FossilStructure(StructureSettings structureSettings) {
        super(structureSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        Rotation rotation = Rotation.getRandom(context.random());
        LevelHeightAccessor levelHeight = context.heightAccessor();
        int height = context.chunkGenerator().getBaseHeight(context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), Heightmap.Types.OCEAN_FLOOR_WG, levelHeight, context.randomState()) - 32;
        int maxHeight = height - 32 - context.random().nextInt(16);
        BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), maxHeight, context.chunkPos().getMinBlockZ());
        ResourceLocation fossils = Util.getRandom(FOSSILS, context.random());
        return Optional.of(new GenerationStub(blockpos, (piecesBuilder -> piecesBuilder.addPiece(new FossilPiece(context.structureTemplateManager(), fossils, blockpos, rotation)))));
    }

    @Override
    public StructureType<?> type() {
        return UP2StructureTypes.FOSSIL.get();
    }
}