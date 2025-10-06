package com.unusualmodding.unusual_prehistory.worldgen.structure;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.FossilBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2LootTables;
import com.unusualmodding.unusual_prehistory.registry.UP2StructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;
import java.util.List;

public class FossilPiece extends TemplateStructurePiece {

    private boolean placedInCave;

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
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        if (!this.placedInCave) {
            int originalY = this.templatePosition.getY();
            BlockPos structureCenter = StructureTemplate.transform(new BlockPos(this.template.getSize().getX() / 2, 0, this.template.getSize().getZ() / 2), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO).offset(templatePosition);
            BlockPos cavePos = this.getCaveHeight(structureCenter, level, random);
            this.templatePosition = new BlockPos(this.templatePosition.getX(), cavePos.getY(), this.templatePosition.getZ());
            BlockPos structureEdge = StructureTemplate.transform(new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO).offset(this.templatePosition);
            this.templatePosition = new BlockPos(this.templatePosition.getX(), this.getHeight(this.templatePosition, level, structureEdge), this.templatePosition.getZ());
            if (templatePosition.getY() > chunkGenerator.getSeaLevel() - this.minimumDepthBeneathSurface()) {
                templatePosition = templatePosition.atY(-256);
            }
            this.templatePosition = this.templatePosition.below(buryDepth());
            this.boundingBox.move(0, this.templatePosition.getY() - originalY, 0);
            this.placedInCave = true;
        }
        if (templatePosition.getY() >= level.getMinBuildHeight()) {
            super.postProcess(level, structureManager, chunkGenerator, random, boundingBox, chunkPos, pos);
        }
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

    private int getHeight(BlockPos leftPos, BlockGetter level, BlockPos rightPos) {
        int j = 512;
        for (BlockPos blockpos : BlockPos.betweenClosed(leftPos, rightPos)) {
            int x = blockpos.getX();
            int y = leftPos.getY() - 1;
            int z = blockpos.getZ();
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);
            BlockState blockstate = level.getBlockState(mutableBlockPos);
            for (FluidState fluidstate = level.getFluidState(mutableBlockPos); (blockstate.isAir() || fluidstate.is(FluidTags.WATER) || blockstate.is(BlockTags.ICE)) && y > level.getMinBuildHeight() + 1; fluidstate = level.getFluidState(mutableBlockPos)) {
                y--;
                mutableBlockPos.set(x, y, z);
                blockstate = level.getBlockState(mutableBlockPos);
            }
            j = Math.min(j, y);
        }
        return j;
    }

    protected BlockPos getCaveHeight(BlockPos currentStructureCenter, WorldGenLevel level, RandomSource random) {
        List<BlockPos> possibilities = new ArrayList<>();
        int seaLevel = Math.max(level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, currentStructureCenter.getX(), currentStructureCenter.getZ()), 63);
        BlockPos.MutableBlockPos chunkCenter = new BlockPos.MutableBlockPos(currentStructureCenter.getX(), level.getMinBuildHeight() + 3, currentStructureCenter.getZ());
        while (chunkCenter.getY() < seaLevel - this.minimumDepthBeneathSurface()) {
            BlockState currentState = level.getBlockState(chunkCenter);
            chunkCenter.move(0, 1, 0);
            BlockState nextState = level.getBlockState(chunkCenter);
            if (!(currentState.isAir() || currentState.is(BlockTags.REPLACEABLE)) && (nextState.isAir() || nextState.is(BlockTags.REPLACEABLE))) {
                possibilities.add(chunkCenter.immutable().below());
            }
        }
        if (possibilities.isEmpty()) {
            return currentStructureCenter;
        } else{
            return possibilities.size() == 1 ? possibilities.get(0) : possibilities.get(random.nextInt(possibilities.size() - 1));
        }
    }

    public int minimumDepthBeneathSurface() {
        return 6;
    }

    public int buryDepth() {
        return 0;
    }
}