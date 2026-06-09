package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.structure.piece.DesertFossilSitePiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2StructurePieces {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> DESERT_FOSSIL_SITE = STRUCTURE_PIECE_TYPES.register("desert_fossil_site", () -> DesertFossilSitePiece::new);

}