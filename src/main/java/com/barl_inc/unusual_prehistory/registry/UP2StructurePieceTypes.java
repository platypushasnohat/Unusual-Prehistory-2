package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.AbandonedLabStructurePiece;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.CliffFossilStructurePiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2StructurePieceTypes {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> ABANDONED_LAB_PIECE = STRUCTURE_PIECE_TYPES.register("abandoned_lab_piece", () -> AbandonedLabStructurePiece::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> CLIFF_FOSSIL_PIECE = STRUCTURE_PIECE_TYPES.register("cliff_fossil_piece", () -> CliffFossilStructurePiece::new);

}