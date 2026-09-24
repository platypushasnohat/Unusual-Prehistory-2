package com.barl_inc.unusual_prehistory.worldgen.structure.piece;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2StructurePieceTypes;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.AbandonedLabProcessor;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AbandonedLabStructurePiece extends TemplateStructurePiece {

    public static final Map<ResourceLocation, Integer> HEIGHT_TO_TEMPLATES = Util.make(Maps.newHashMap(), map -> {
        map.put(UnusualPrehistory2.location("abandoned_lab/abandoned_lab"), 2);
    });

    public AbandonedLabStructurePiece(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
        super(UP2StructurePieceTypes.ABANDONED_LAB_PIECE.get(), HEIGHT_TO_TEMPLATES.get(id), manager, id, id.toString(), createPlacementData(rotation), pos);
    }

    public AbandonedLabStructurePiece(StructureTemplateManager manager, CompoundTag compoundTag) {
        super(UP2StructurePieceTypes.ABANDONED_LAB_PIECE.get(), compoundTag, manager, id -> createPlacementData(Rotation.valueOf(compoundTag.getString("Rotation"))));
    }

    public AbandonedLabStructurePiece(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        this(context.structureTemplateManager(), compoundTag);
    }

    public static void addPieces(StructureTemplateManager manager, BlockPos pos, StructurePieceAccessor holder, RandomSource random) {
        Set<ResourceLocation> resourceLocations = HEIGHT_TO_TEMPLATES.keySet();
        List<ResourceLocation> templates = new ArrayList<>(resourceLocations.stream().toList());
        ResourceLocation randomTemplate = Util.getRandom(templates, random);
        Rotation rotation = Rotation.getRandom(random);
        holder.addPiece(new AbandonedLabStructurePiece(manager, randomTemplate, pos, rotation));
    }

    private static StructurePlaceSettings createPlacementData(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        super.addAdditionalSaveData(context, compoundTag);
        compoundTag.putString("Rotation", this.placeSettings.getRotation().name());
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        BlockPos blockPos = this.templatePosition;
        this.templatePosition = this.templatePosition.below(HEIGHT_TO_TEMPLATES.get(ResourceLocation.parse(this.templateName)));
        this.placeSettings.clearProcessors().addProcessor(new AbandonedLabProcessor());
        super.postProcess(level, manager, generator, random, boundingBox, chunkPos, pos);
        this.templatePosition = blockPos;
    }

    @Override
    protected void handleDataMarker(String metaData, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox boundingBox) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        if (metaData.equals("loot_chest")) {
            ResourceKey<LootTable> lootTable = ResourceKey.create(Registries.LOOT_TABLE, UnusualPrehistory2.location("chests/abandoned_lab"));
            if (level.getBlockEntity(pos.below()) instanceof RandomizableContainerBlockEntity container) {
                container.setLootTable(lootTable, random.nextLong());
            }
        }
    }
}