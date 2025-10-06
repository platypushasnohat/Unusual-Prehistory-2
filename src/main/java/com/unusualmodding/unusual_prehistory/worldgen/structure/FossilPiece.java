package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.FossilBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2LootTables;
import com.unusualmodding.unusual_prehistory.registry.UP2StructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class FossilPiece extends TemplateStructurePiece {

    public FossilPiece(StructureTemplateManager manager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation) {
        super(UP2StructurePieceTypes.FOSSIL.get(), 0, manager, resourceLocation, resourceLocation.toString(), createPlacementData(rotation), pos);
    }

    public FossilPiece(StructureTemplateManager manager, CompoundTag compoundTag) {
        super(UP2StructurePieceTypes.FOSSIL.get(), compoundTag, manager, id -> createPlacementData(Rotation.valueOf(compoundTag.getString("Rotation"))));
    }

    public FossilPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context.structureTemplateManager(), tag);
    }

    private static StructurePlaceSettings createPlacementData(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).addProcessor(new FossilProcessor());
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        super.addAdditionalSaveData(context, compoundTag);
        compoundTag.putString("Rotation", this.placeSettings.getRotation().name());
    }

    @Override
    protected void handleDataMarker(String data, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox boundingBox) {
        if (data.equals("fossil")) {
            if (pos.getY() > 0) {
                this.createFossil(level, boundingBox, random, pos, UP2LootTables.FOSSIL, false);
            } else {
                this.createFossil(level, boundingBox, random, pos, UP2LootTables.DEEPSLATE_FOSSIL, true);
            }
        }
    }

    protected void createFossil(ServerLevelAccessor level, BoundingBox boundingBox, RandomSource random, BlockPos pos, ResourceLocation resourceLocation, boolean deepslate) {
        Block fossil = deepslate ? UP2Blocks.DEEPSLATE_FOSSIL.get() : UP2Blocks.FOSSIL.get();
        boolean outsideBoundingBox = !boundingBox.isInside(pos);
        boolean stateIsSelf = level.getBlockState(pos).is(fossil);
        boolean invalidPos = level.getBlockState(pos).isAir();
        boolean invalidBelowPos = !level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);

        if (outsideBoundingBox || stateIsSelf || invalidBelowPos || invalidPos) {
            return;
        }
        level.setBlock(pos, fossil.defaultBlockState(), 2);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FossilBlockEntity entity) {
            entity.setLootTable(resourceLocation, random.nextLong());
        }
    }
}