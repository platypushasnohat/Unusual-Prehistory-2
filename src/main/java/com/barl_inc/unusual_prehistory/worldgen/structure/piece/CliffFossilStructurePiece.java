package com.barl_inc.unusual_prehistory.worldgen.structure.piece;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2StructurePieceTypes;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.CliffFossilProcessor;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CliffFossilStructurePiece extends TemplateFossilStructurePiece {

    public static final Map<ResourceLocation, Integer> HEIGHT_TO_TEMPLATES = Util.make(Maps.newHashMap(), map -> {
        map.put(UnusualPrehistory2.location("fossil/cliff_fossil_1"), 2);
        map.put(UnusualPrehistory2.location("fossil/cliff_fossil_2"), 2);
        map.put(UnusualPrehistory2.location("fossil/cliff_fossil_3"), 1);
    });

    public CliffFossilStructurePiece(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
        super(UP2StructurePieceTypes.CLIFF_FOSSIL_PIECE.get(), HEIGHT_TO_TEMPLATES.get(id), manager, id, id.toString(), createPlacementData(rotation), pos);
    }

    public CliffFossilStructurePiece(StructureTemplateManager manager, CompoundTag compoundTag) {
        super(UP2StructurePieceTypes.CLIFF_FOSSIL_PIECE.get(), compoundTag, manager, id -> createPlacementData(Rotation.valueOf(compoundTag.getString("Rotation"))));
    }

    public CliffFossilStructurePiece(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        this(context.structureTemplateManager(), compoundTag);
    }

    public static void addPieces(StructureTemplateManager manager, BlockPos pos, StructurePieceAccessor holder, RandomSource random) {
        Set<ResourceLocation> resourceLocations = HEIGHT_TO_TEMPLATES.keySet();
        List<ResourceLocation> templates = new ArrayList<>(resourceLocations.stream().toList());
        ResourceLocation randomTemplate = Util.getRandom(templates, random);
        Rotation rotation = Rotation.getRandom(random);
        holder.addPiece(new CliffFossilStructurePiece(manager, randomTemplate, pos, rotation));
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        int commonFossilCap = 5 + random.nextInt(2);
        int uncommonFossilCap = 3 + random.nextInt(2);
        int rareFossilCap = 1 + random.nextInt(2);
        int unusualFossilCap = random.nextInt(2);
        this.placeSettings.clearProcessors()
                .addProcessor(new CliffFossilProcessor(HEIGHT_TO_TEMPLATES.get(ResourceLocation.parse(this.templateName))))
                .addProcessor(this.matrixProcessor(Blocks.GRAVEL.defaultBlockState(), UP2Blocks.GRAVEL_MATRIX.get().defaultBlockState(), UnusualPrehistory2.location("archaeology/fossil/cliff_fossil/common"), "common", commonFossilCap))
                .addProcessor(this.matrixProcessor(Blocks.GRAVEL.defaultBlockState(), UP2Blocks.GRAVEL_MATRIX.get().defaultBlockState(), UnusualPrehistory2.location("archaeology/fossil/cliff_fossil/uncommon"), "uncommon", uncommonFossilCap))
                .addProcessor(this.matrixProcessor(Blocks.GRAVEL.defaultBlockState(), UP2Blocks.GRAVEL_MATRIX.get().defaultBlockState(), UnusualPrehistory2.location("archaeology/fossil/cliff_fossil/rare"), "rare", rareFossilCap))
                .addProcessor(this.matrixProcessor(Blocks.GRAVEL.defaultBlockState(), UP2Blocks.GRAVEL_MATRIX.get().defaultBlockState(), UnusualPrehistory2.location("archaeology/fossil/cliff_fossil/unusual"), "unusual", unusualFossilCap));
        super.postProcess(level, manager, generator, random, boundingBox, chunkPos, pos);
    }
}