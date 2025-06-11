package com.unusualmodding.unusual_prehistory.worldgen.foliage_placer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unusualmodding.unusual_prehistory.registry.UP2Features;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class GinkgoFoliagePlacer extends FoliagePlacer {

    public static final Codec<GinkgoFoliagePlacer> CODEC = RecordCodecBuilder.create((foliagePlacerInstance) -> foliagePlacerParts(foliagePlacerInstance).and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((foliagePlacer) -> foliagePlacer.trunkHeight)).apply(foliagePlacerInstance, GinkgoFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public GinkgoFoliagePlacer(IntProvider intProvider, IntProvider intProvider1, IntProvider trunkHeight) {
        super(intProvider, intProvider1);
        this.trunkHeight = trunkHeight;
    }

    protected FoliagePlacerType<?> type() {
        return UP2Features.GINKGO_FOLIAGE.get();
    }

    protected void createFoliage(LevelSimulatedReader level, FoliagePlacer.FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int radius, int foliageHeight, int offset) {
        BlockPos blockPos = treeNode.pos();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();

        for (int height = -11; height <= foliageHeight; ++height) {
            if (height == -11) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, height, treeNode.doubleTrunk());
                if (random.nextBoolean()) {
                    this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, height - 1, treeNode.doubleTrunk());
                }
            }
            if (height == -10 || height == -7 || height == -5 || height == -2) {
                this.generateDiamond(level, placer, random, config, blockPos, 2, height, treeNode.doubleTrunk());
            }
            if (height == -9 || height == -6 || height == -4) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, height, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, height, 0);
                if (random.nextBoolean()) {
                    tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 2));
                }
                if (random.nextBoolean()) {
                    tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 2));
                }
                if (random.nextBoolean()) {
                    tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, -2));
                }
                if (random.nextBoolean()) {
                    tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, -2));
                }
            }
            if (height == -8 || height == -6 || height == -3) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, height, treeNode.doubleTrunk());
            }
            if (height == -1) {
                this.generateDiamond(level, placer, random, config, blockPos, 1, height, treeNode.doubleTrunk());
            }
            if (height == 0) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, height, treeNode.doubleTrunk());
            }
            if (height == 1) {
                mutable.setWithOffset(blockPos, 0, height, 0);
                tryPlaceLeaf(level, placer, random, config, mutable);
                if (random.nextBoolean()) {
                    tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 1, 0));
                } else if (random.nextBoolean()) {
                    this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, height, treeNode.doubleTrunk());
                    tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 1, 0));
                    if (random.nextBoolean()) {
                        tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 2, 0));
                    }
                }
            }
        }
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

    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return Math.max(8, trunkHeight - this.trunkHeight.sample(random));
    }

    protected boolean shouldSkipLocation(RandomSource random, int x, int y, int z, int radius, boolean giantTrunk) {
        return x == radius && z == radius && radius > 0;
    }
}