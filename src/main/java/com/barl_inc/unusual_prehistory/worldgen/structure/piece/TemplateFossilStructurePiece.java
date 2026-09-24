package com.barl_inc.unusual_prehistory.worldgen.structure.piece;

import com.barl_inc.unusual_prehistory.worldgen.structure.processor.MatrixProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.function.Function;

public abstract class TemplateFossilStructurePiece extends TemplateStructurePiece {

    public TemplateFossilStructurePiece(StructurePieceType type, int genDepth, StructureTemplateManager structureTemplateManager, ResourceLocation location, String templateName, StructurePlaceSettings placeSettings, BlockPos templatePosition) {
        super(type, genDepth, structureTemplateManager, location, templateName, placeSettings, templatePosition);
    }

    public TemplateFossilStructurePiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager structureTemplateManager, Function<ResourceLocation, StructurePlaceSettings> placeSettingsFactory) {
        super(type, tag, structureTemplateManager, placeSettingsFactory);
    }

    public static StructurePlaceSettings createPlacementData(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        super.addAdditionalSaveData(context, compoundTag);
        compoundTag.putString("Rotation", this.placeSettings.getRotation().name());
    }

    @Override
    protected void handleDataMarker(String metaData, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox boundingBox) {
    }

    public CappedProcessor matrixProcessor(BlockState replaceState, BlockState matrixState, ResourceLocation lootTable, String rarity, int cap) {
        return new CappedProcessor(new MatrixProcessor(replaceState, matrixState, lootTable, rarity), ConstantInt.of(cap));
    }
}
