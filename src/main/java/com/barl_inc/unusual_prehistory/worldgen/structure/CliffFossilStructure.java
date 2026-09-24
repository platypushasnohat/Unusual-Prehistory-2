package com.barl_inc.unusual_prehistory.worldgen.structure;

import com.barl_inc.unusual_prehistory.registry.UP2StructureTypes;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.CliffFossilStructurePiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class CliffFossilStructure extends Structure {

    public static final MapCodec<CliffFossilStructure> CODEC = CliffFossilStructure.simpleCodec(CliffFossilStructure::new);

    public CliffFossilStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
        int y = context.chunkGenerator().getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

        if (!context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState()).getBlock(y).getFluidState().isEmpty() || context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState()).getBlock(y).is(Blocks.ICE)) {
            return Optional.empty();
        }
        BlockPos pos = new BlockPos(x, y, z);
        return Optional.of(new GenerationStub(pos, pieces -> CliffFossilStructurePiece.addPieces(context.structureTemplateManager(), pos, pieces, random)));
    }

    @Override
    public StructureType<?> type() {
        return UP2StructureTypes.ABANDONED_LAB.get();
    }
}