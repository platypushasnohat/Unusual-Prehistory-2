package com.barlinc.unusual_prehistory.worldgen.structure;

import com.barlinc.unusual_prehistory.registry.UP2Structures;
import com.barlinc.unusual_prehistory.worldgen.structure.piece.BadlandsFossilPiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BadlandsFossilStructure extends Structure {

    public static final MapCodec<BadlandsFossilStructure> CODEC = BadlandsFossilStructure.simpleCodec(BadlandsFossilStructure::new);

    public BadlandsFossilStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
        int y = context.chunkGenerator().getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

        if (!context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState()).getBlock(y).getFluidState().isEmpty()) {
            return Optional.empty();
        }
        // if it goes above the areas with red sand, doesn't generate
        if (y > 74) {
            return Optional.empty();
        }
        BlockPos pos = new BlockPos(x, y, z);
        return Optional.of(new Structure.GenerationStub(pos, pieces -> BadlandsFossilPiece.addPieces(context.structureTemplateManager(), pos, pieces, random)));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return UP2Structures.BADLANDS_FOSSIL.get();
    }
}