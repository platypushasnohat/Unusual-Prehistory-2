package com.barlinc.unusual_prehistory.worldgen.structure.piece;

import com.barlinc.unusual_prehistory.blocks.entity.MatrixBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class FossilSitePiece extends TemplateStructurePiece {

    public FossilSitePiece(StructurePieceType structurePieceType, int i, StructureTemplateManager structureTemplateManager, ResourceLocation resourceLocation, String id, StructurePlaceSettings settings, BlockPos blockPos) {
        super(structurePieceType, i, structureTemplateManager, resourceLocation, id, settings, blockPos);
    }

    public FossilSitePiece(StructurePieceType structurePieceType, CompoundTag compoundTag, StructureTemplateManager structureTemplateManager, Function<ResourceLocation, StructurePlaceSettings> function) {
        super(structurePieceType, compoundTag, structureTemplateManager, function);
    }

    public static StructurePlaceSettings createPlacementData(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(context, compoundTag);
        compoundTag.putString("Rotation", placeSettings.getRotation().name());
    }

    protected void createMatrix(Block block, ServerLevelAccessor serverLevelAccessor, BoundingBox boundingBox, RandomSource random, BlockPos blockPos, ResourceKey<LootTable> key, MatrixBlockEntity.LootRarity lootRarity) {
        boolean outsideBoundingBox = !boundingBox.isInside(blockPos);
        boolean stateIsSelf = serverLevelAccessor.getBlockState(blockPos).is(block);
        if (outsideBoundingBox || stateIsSelf) {
            return;
        }
        serverLevelAccessor.setBlock(blockPos, block.defaultBlockState(), 2);
        BlockEntity blockEntity = serverLevelAccessor.getBlockEntity(blockPos);
        if (blockEntity instanceof MatrixBlockEntity entity) {
            entity.setLootTable(key, random.nextLong());
            entity.setRarity(lootRarity);
        }
    }
}