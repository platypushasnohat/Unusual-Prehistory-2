package com.barlinc.unusual_prehistory.worldgen.structure;

import com.barlinc.unusual_prehistory.registry.UP2Structures;
import com.barlinc.unusual_prehistory.worldgen.structure.piece.DesertFossilSitePiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DesertFossilSiteStructure extends Structure {

    public static final MapCodec<DesertFossilSiteStructure> CODEC = DesertFossilSiteStructure.simpleCodec(DesertFossilSiteStructure::new);

    public DesertFossilSiteStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
        int seaLevel = context.chunkGenerator().getSeaLevel();

        int height = 128;

        NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState());

        for (int y = height; y > seaLevel; y--) {
            if (!noiseColumn.getBlock(y).isAir() && noiseColumn.getBlock(y + 1).isAir()) {
                break;
            }
            height--;
        }

        if (height <= seaLevel) {
            return Optional.empty();
        }

        BlockPos blockPos = new BlockPos(x, height, z);
        return Optional.of(new Structure.GenerationStub(blockPos, structurePiecesBuilder -> DesertFossilSitePiece.addPieces(context.structureTemplateManager(), blockPos, structurePiecesBuilder, random)));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return UP2Structures.DESERT_FOSSIL_SITE.get();
    }
}