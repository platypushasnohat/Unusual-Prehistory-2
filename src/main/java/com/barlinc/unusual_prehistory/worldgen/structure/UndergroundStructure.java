package com.barlinc.unusual_prehistory.worldgen.structure;

import com.barlinc.unusual_prehistory.registry.UP2Structures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UndergroundStructure extends Structure {

    public static final Codec<UndergroundStructure> CODEC = RecordCodecBuilder.<UndergroundStructure>mapCodec(instance ->
            instance.group(UndergroundStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.INT.fieldOf("size").forGetter(provider -> provider.size),
                    Codec.INT.fieldOf("min_y").forGetter(provider -> provider.min),
                    Codec.INT.fieldOf("max_y").forGetter(provider -> provider.max),
                    Codec.INT.fieldOf("offset_in_ground").forGetter(provider -> provider.offsetInGround),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, UndergroundStructure::new)).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final int min;
    private final int max;
    private final int offsetInGround;
    private final int maxDistanceFromCenter;

    public UndergroundStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, int min, int max, int offsetInGround, int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.min = min;
        this.max = max;
        this.offsetInGround = offsetInGround;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 0, context.chunkPos().getMinBlockZ());
        BlockPos validPos = new BlockPos(blockPos.getX(), getValidY(context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor(), context.randomState())), blockPos.getZ());
        if (validPos.getY() != min - 1 && this.isSufficientlyFlat(context, validPos, 4)) {
            return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.size, validPos.below(-offsetInGround), false, Optional.empty(), this.maxDistanceFromCenter);
        }
        return Optional.empty();
    }

    public boolean isSufficientlyFlat(GenerationContext context, BlockPos origin, int check) {
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -check; x < check; x++) {
            for (int z = -check; z < check; z++) {
                blockPosList.add(origin.offset(x, 0, z));
            }
        }
        int count = 0;
        for (BlockPos pos : blockPosList) {
            NoiseColumn blockView = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
            if (blockView.getBlock(pos.getY()).isAir() && !blockView.getBlock(pos.below().getY()).isAir()) {
                count++;
            }
        }
        return count >= check * check * 2;
    }

    public int getValidY(NoiseColumn sample) {
        int maxLength = 0;
        int currentLength = 0;
        int maxIndex = min - 1;
        for (int i = min; i < max; i += size) {
            if (sample.getBlock(i).isAir()) {
                int j = i + 1;
                while (j < max && sample.getBlock(j).isAir()) {
                    j++;
                }
                int sequenceLength = j - i;
                if (sequenceLength >= size) {
                    currentLength += sequenceLength;
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        maxIndex = i;
                    }
                    i = j - 1;
                }
            } else {
                currentLength = 0;
            }
        }
        return maxIndex;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return UP2Structures.UNDERGROUND_STRUCTURE.get();
    }
}