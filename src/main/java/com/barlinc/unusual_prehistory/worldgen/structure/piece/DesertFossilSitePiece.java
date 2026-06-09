package com.barlinc.unusual_prehistory.worldgen.structure.piece;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.entity.MatrixBlockEntity;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2LootTables;
import com.barlinc.unusual_prehistory.registry.UP2StructurePieces;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DesertFossilSitePiece extends FossilSitePiece {

    public static final Map<ResourceLocation, Integer> HEIGHT_TO_TEMPLATES = Util.make(Maps.newHashMap(), map -> {
        map.put(UnusualPrehistory2.modPrefix("fossil/desert/desert_fossil_0"), 2);
    });

    public DesertFossilSitePiece(StructureTemplateManager manager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation) {
        super(UP2StructurePieces.DESERT_FOSSIL_SITE.get(), HEIGHT_TO_TEMPLATES.get(resourceLocation), manager, resourceLocation, resourceLocation.toString(), createPlacementData(rotation), pos);
    }

    public DesertFossilSitePiece(StructureTemplateManager manager, CompoundTag compoundTag) {
        super(UP2StructurePieces.DESERT_FOSSIL_SITE.get(), compoundTag, manager, (resourceLocation) -> createPlacementData(Rotation.valueOf(compoundTag.getString("Rotation"))));
    }

    public DesertFossilSitePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context.structureTemplateManager(), tag);
    }

    @Override
    public void postProcess(@NotNull WorldGenLevel level, @NotNull StructureManager manager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox boundingBox, @NotNull ChunkPos chunkPos, @NotNull BlockPos blockPos) {
        BlockPos pos = templatePosition;
        this.templatePosition = templatePosition.below(DesertFossilSitePiece.HEIGHT_TO_TEMPLATES.get(ResourceLocation.parse(templateName)));
        super.postProcess(level, manager, generator, random, boundingBox, chunkPos, blockPos);
        this.templatePosition = pos;
    }

    public static void addPieces(StructureTemplateManager manager, BlockPos pos, StructurePieceAccessor holder, RandomSource random) {
        Set<ResourceLocation> resourceLocations = HEIGHT_TO_TEMPLATES.keySet();
        List<ResourceLocation> templates = new ArrayList<>(resourceLocations.stream().toList());
        ResourceLocation randomTemplate = Util.getRandom(templates, random);
        Rotation rotation = Rotation.getRandom(random);
        holder.addPiece(new DesertFossilSitePiece(manager, randomTemplate, pos, rotation));
    }

    @Override
    protected void handleDataMarker(@NotNull String metaData, @NotNull BlockPos pos, @NotNull ServerLevelAccessor level, @NotNull RandomSource random, @NotNull BoundingBox boundingBox) {
        if (metaData.equals("COMMON")) {
            this.createMatrix(UP2Blocks.SAND_MATRIX.get(), level, boundingBox, random, pos, UP2LootTables.DESERT_FOSSIL_COMMON, MatrixBlockEntity.LootRarity.COMMON);
        }
        if (metaData.equals("UNCOMMON")) {
            this.createMatrix(UP2Blocks.SAND_MATRIX.get(), level, boundingBox, random, pos, UP2LootTables.DESERT_FOSSIL_UNCOMMON, MatrixBlockEntity.LootRarity.UNCOMMON);
        }
        if (metaData.equals("RARE")) {
            this.createMatrix(UP2Blocks.SAND_MATRIX.get(), level, boundingBox, random, pos, UP2LootTables.DESERT_FOSSIL_RARE, MatrixBlockEntity.LootRarity.RARE);
        }
        if (metaData.equals("UNUSUAL")) {
            this.createMatrix(UP2Blocks.SAND_MATRIX.get(), level, boundingBox, random, pos, UP2LootTables.DESERT_FOSSIL_UNUSUAL, MatrixBlockEntity.LootRarity.UNUSUAL);
        }
    }
}