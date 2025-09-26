package com.unusualmodding.unusual_prehistory.worldgen.feature.foliage_placer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unusualmodding.unusual_prehistory.registry.UP2FoliagePlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class LepidodendronFoliagePlacer extends FoliagePlacer {

    public static final Codec<LepidodendronFoliagePlacer> CODEC = RecordCodecBuilder.create((lepidodendron) -> foliagePlacerParts(lepidodendron).and(Codec.intRange(0, 16).fieldOf("height").forGetter((lepidodendronHeight) -> lepidodendronHeight.height)).apply(lepidodendron, LepidodendronFoliagePlacer::new));

    protected final int height;

    public LepidodendronFoliagePlacer(IntProvider intProvider, IntProvider intProvider1, int height) {
        super(intProvider, intProvider1);
        this.height = height;
    }

    protected FoliagePlacerType<?> type() {
        return UP2FoliagePlacers.LEPIDODENDRON_FOLIAGE.get();
    }

    protected void createFoliage(LevelSimulatedReader level, FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.pos().above(offset);
        BlockPos.MutableBlockPos mutable = blockPos.mutable();

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 1 + treeNode.radiusOffset(), 3, false);
        this.generateDiamond(level, placer, random, config, blockPos, radius + 2 + treeNode.radiusOffset(), 3, treeNode.doubleTrunk());

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 3 + treeNode.radiusOffset(), 4, false);

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 2 + treeNode.radiusOffset(), 5, false);
        mutable.setWithOffset(blockPos, 0, 5, 0);
        tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -5));

        tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 5));

        tryPlaceLeaf(level, placer, random, config, mutable.offset(5, 0, -1));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(5, 0, 0));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(5, 0, 1));

        tryPlaceLeaf(level, placer, random, config, mutable.offset(-5, 0, -1));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(-5, 0, 0));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(-5, 0, 1));

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 1 + treeNode.radiusOffset(), 6, false);
        this.generateDiamond(level, placer, random, config, blockPos, radius + 2 + treeNode.radiusOffset(), 6, treeNode.doubleTrunk());
    }

    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return 3;
    }

    protected void generateDiamond(LevelSimulatedReader world, FoliageSetter placer, RandomSource random, TreeConfiguration config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
        int i = giantTrunk ? 1 : 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int j = -radius; j <= radius + i; ++j) {
            for (int k = -radius; k <= radius + i; ++k) {
                if (!this.validateDiamond(random, j, y, k, radius, giantTrunk)) {
                    mutable.setWithOffset(centerPos, j, y, k);
                    tryPlaceLeaf(world, placer, random, config, mutable);
                }
            }
        }
    }

    protected boolean validateDiamond(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (giantTrunk) {
            dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
            dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
        } else {
            dx = Math.abs(dx);
            dz = Math.abs(dz);
        }
        return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
    }

    protected boolean shouldSkipLocation(RandomSource random, int x, int y, int z, int radius, boolean giantTrunk) {
        return x == radius && z == radius && radius > 0;
    }
}