package com.unusualmodding.unusual_prehistory.worldgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unusualmodding.unusual_prehistory.registry.UP2TrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LepidodendronTrunkPlacer extends TrunkPlacer {

    public static final Codec<LepidodendronTrunkPlacer> CODEC = RecordCodecBuilder.create(lepidodendron -> trunkPlacerParts(lepidodendron).apply(lepidodendron, LepidodendronTrunkPlacer::new));

    public LepidodendronTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return UP2TrunkPlacers.LEPIDODENDRON_TRUNK.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {

        int trunkHeight = height + random.nextInt(heightRandA, heightRandA + 3) + random.nextInt(heightRandB - 1, heightRandB + 1);
        int branchStart = trunkHeight - 1;

        for (int i = 0; i < trunkHeight; i++) {
            placeLog(level, blockSetter, random, startPos.above(i), config);

            blockSetter.accept(startPos.above(branchStart).relative(Direction.NORTH, 1), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));
            blockSetter.accept(startPos.above(branchStart).relative(Direction.SOUTH, 1), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));
            blockSetter.accept(startPos.above(branchStart).relative(Direction.EAST, 1), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));
            blockSetter.accept(startPos.above(branchStart).relative(Direction.WEST, 1), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));

            placeLog(level, blockSetter, random, startPos.above(branchStart + 1).relative(Direction.NORTH, 2), config);
            placeLog(level, blockSetter, random, startPos.above(branchStart + 2).relative(Direction.NORTH, 2), config);
            blockSetter.accept(startPos.above(branchStart + 3).relative(Direction.NORTH, 3), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));

            placeLog(level, blockSetter, random, startPos.above(branchStart + 1).relative(Direction.SOUTH, 2), config);
            placeLog(level, blockSetter, random, startPos.above(branchStart + 2).relative(Direction.SOUTH, 2), config);
            blockSetter.accept(startPos.above(branchStart + 3).relative(Direction.SOUTH, 3), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));

            placeLog(level, blockSetter, random, startPos.above(branchStart + 1).relative(Direction.EAST, 2), config);
            placeLog(level, blockSetter, random, startPos.above(branchStart + 2).relative(Direction.EAST, 2), config);
            blockSetter.accept(startPos.above(branchStart + 3).relative(Direction.EAST, 3), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));

            placeLog(level, blockSetter, random, startPos.above(branchStart + 1).relative(Direction.WEST, 2), config);
            placeLog(level, blockSetter, random, startPos.above(branchStart + 2).relative(Direction.WEST, 2), config);
            blockSetter.accept(startPos.above(branchStart + 3).relative(Direction.WEST, 3), ((BlockState) Function.identity().apply(config.trunkProvider.getState(random, startPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(startPos.above(trunkHeight), 0, false));
    }
}
