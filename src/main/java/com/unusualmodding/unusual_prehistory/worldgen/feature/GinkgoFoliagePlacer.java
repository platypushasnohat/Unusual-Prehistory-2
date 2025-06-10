package com.unusualmodding.unusual_prehistory.worldgen.feature;

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

public class GinkgoFoliagePlacer extends FoliagePlacer {

    public static final Codec<GinkgoFoliagePlacer> CODEC = RecordCodecBuilder.create((foliagePlacerInstance) -> foliagePlacerParts(foliagePlacerInstance).and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((foliagePlacer) -> foliagePlacer.trunkHeight)).apply(foliagePlacerInstance, GinkgoFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public GinkgoFoliagePlacer(IntProvider intProvider, IntProvider intProvider1, IntProvider trunkHeight) {
        super(intProvider, intProvider1);
        this.trunkHeight = trunkHeight;
    }

    protected FoliagePlacerType<?> type() {
        return UP2FoliagePlacers.GINKGO_FOLIAGE.get();
    }

    protected void createFoliage(LevelSimulatedReader level, FoliagePlacer.FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int radius, int foliageHeight, int offset) {
        BlockPos blockPos = treeNode.pos();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();

        for (int l = -11; l <= foliageHeight; ++l) {

            if (l == -11) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
            }
            if (l == -10) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -1));

                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -2));
            }
            if (l == -9) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, -2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, -2));
            }
            if (l == -8) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, l, treeNode.doubleTrunk());
            }
            if (l == -7) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -1));

                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -2));
            }
            if (l == -6) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, l, treeNode.doubleTrunk());
            }
            if (l == -5) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -1));

                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -2));
            }
            if (l == -4) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, l, treeNode.doubleTrunk());
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 2, l + 1, treeNode.doubleTrunk());
            }
            if (l == -3) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -1));

                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -2));
            }
            if (l == -2) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -1));

                tryPlaceLeaf(level, placer, random, config, mutable.offset(2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-2, 0, 0));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 2));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -2));
            }
            if (l == -1) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -1));
                tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -1));
            }
            if (l == 0) {
                this.placeLeavesRow(level, placer, random, config, treeNode.pos(), 1, l, treeNode.doubleTrunk());
            }
            if (l == 1) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                tryPlaceLeaf(level, placer, random, config, mutable);
            }
        }
    }

    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return Math.max(8, trunkHeight - this.trunkHeight.sample(random));
    }

    protected boolean shouldSkipLocation(RandomSource random, int x, int y, int z, int radius, boolean giantTrunk) {
        return x == radius && z == radius && radius > 0;
    }
}