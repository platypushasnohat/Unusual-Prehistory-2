package com.barlinc.unusual_prehistory.worldgen.feature.tree.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

import javax.annotation.Nullable;
import java.util.*;

/*
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://mozilla.org/MPL/2.0/.
 *
 * Source: Oh The Trees You'll Grow - https://github.com/CorgiTaco-MC/Oh-The-Trees-Youll-Grow/tree/1.20.1
 * Modifications by: Unusual Prehistory - 3/12/2026
 */

public record TreeFromStructureConfig(ResourceLocation baseLocation, ResourceLocation canopyLocation, IntProvider height, BlockStateProvider logProvider, List<BlockStateProvider> leavesProvider, Set<Block> logTarget, List<Block> leavesTarget, BlockPredicate growableOn, BlockPredicate leavesPlacementFilter, int maxLogDepth, List<TreeDecorator> treeDecorators, Map<Block, BlockStateProvider> replaceFromNBT, boolean isSapling, Orientation orientation) implements FeatureConfiguration {

    public static final Codec<Set<Block>> BLOCK_SET_CODEC = Codec.list(BuiltInRegistries.BLOCK.byNameCodec()).xmap(ObjectOpenHashSet::new, ArrayList::new);

    public static final Codec<TreeFromStructureConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ResourceLocation.CODEC.fieldOf("base_location").forGetter(TreeFromStructureConfig::baseLocation),
                    ResourceLocation.CODEC.fieldOf("canopy_location").forGetter(TreeFromStructureConfig::canopyLocation),
                    IntProvider.CODEC.fieldOf("height").forGetter(TreeFromStructureConfig::height),
                    BlockStateProvider.CODEC.fieldOf("log_provider").forGetter(TreeFromStructureConfig::logProvider),
                    BlockStateProvider.CODEC.listOf().fieldOf("leaves_provider").forGetter(TreeFromStructureConfig::leavesProvider),
                    BLOCK_SET_CODEC.fieldOf("log_target").forGetter(TreeFromStructureConfig::logTarget),
                    BuiltInRegistries.BLOCK.byNameCodec().listOf().fieldOf("leaves_target").forGetter(TreeFromStructureConfig::leavesTarget),
                    BlockPredicate.CODEC.fieldOf("can_grow_on_filter").forGetter(TreeFromStructureConfig::growableOn),
                    BlockPredicate.CODEC.fieldOf("can_leaves_place_filter").forGetter(TreeFromStructureConfig::leavesPlacementFilter),
                    Codec.INT.optionalFieldOf("max_log_depth", 5).forGetter(TreeFromStructureConfig::maxLogDepth),
                    TreeDecorator.CODEC.listOf().optionalFieldOf("decorators", new ArrayList<>()).forGetter(TreeFromStructureConfig::treeDecorators),
                    Codec.unboundedMap(BuiltInRegistries.BLOCK.byNameCodec(), BlockStateProvider.CODEC).fieldOf("replace_from_nbt").forGetter(TreeFromStructureConfig::replaceFromNBT),
                    Codec.BOOL.optionalFieldOf("sapling", false).forGetter(TreeFromStructureConfig::isSapling),
                    Orientation.CODEC.optionalFieldOf("orientation", Orientation.STANDARD).forGetter(TreeFromStructureConfig::orientation)
            ).apply(builder, TreeFromStructureConfig::new)
    );

    public enum Orientation {
        STANDARD,
        UPSIDE_DOWN,
        SIDEWAYS;

        public static final Codec<Orientation> CODEC = Codec.STRING.xmap(s -> Orientation.valueOf(s.toUpperCase()), s -> s.name().toUpperCase()); // Guards against case issues
    }

    public static class Builder {
        @Nullable
        private ResourceLocation baseLocation;
        @Nullable
        private ResourceLocation canopyLocation;
        @Nullable
        private IntProvider height;
        @Nullable
        private BlockStateProvider logProvider;
        @Nullable
        private List<BlockStateProvider> leavesProvider;
        @Nullable
        private Set<Block> logTarget;
        @Nullable
        private List<Block> leavesTarget;
        private BlockPredicate growableOn = BlockPredicate.replaceable();
        private BlockPredicate leavesPlacementFilter = BlockPredicate.replaceable();
        private int maxLogDepth = 5;
        private List<TreeDecorator> treeDecorators = new ArrayList<>();
        private Map<Block, BlockStateProvider> replaceFromNBT = new HashMap<>();
        private boolean isSapling = false;
        private Orientation orientation = Orientation.STANDARD;

        public Builder baseLocation(ResourceLocation baseLocation) {
            this.baseLocation = baseLocation;
            return this;
        }

        public Builder canopyLocation(ResourceLocation canopyLocation) {
            this.canopyLocation = canopyLocation;
            return this;
        }

        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        public Builder logProvider(BlockStateProvider logProvider) {
            this.logProvider = logProvider;
            return this;
        }

        public Builder leavesProvider(List<BlockStateProvider> leavesProvider) {
            this.leavesProvider = leavesProvider;
            return this;
        }

        public Builder logTarget(Set<Block> logTarget) {
            this.logTarget = logTarget;
            return this;
        }

        public Builder leavesTarget(List<Block> leavesTarget) {
            this.leavesTarget = leavesTarget;
            return this;
        }

        public Builder growableOn(BlockPredicate growableOn) {
            this.growableOn = growableOn;
            return this;
        }

        public Builder leavesPlacementFilter(BlockPredicate leavesPlacementFilter) {
            this.leavesPlacementFilter = leavesPlacementFilter;
            return this;
        }

        public Builder maxLogDepth(int maxLogDepth) {
            this.maxLogDepth = maxLogDepth;
            return this;
        }

        public Builder treeDecorators(List<TreeDecorator> treeDecorators) {
            this.treeDecorators = treeDecorators;
            return this;
        }

        public Builder replaceFromNBT(Map<Block, BlockStateProvider> placeFromNBT) {
            this.replaceFromNBT = placeFromNBT;
            return this;
        }

        public Builder isSapling(boolean isSapling) {
            this.isSapling = isSapling;
            return this;
        }

        public Builder orientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public TreeFromStructureConfig build() {
            if (baseLocation == null) {
                throw new IllegalStateException("Base location cannot be null");
            }
            if (canopyLocation == null) {
                throw new IllegalStateException("Canopy location cannot be null");
            }
            if (height == null) {
                throw new IllegalStateException("Height cannot be null");
            }
            if (logProvider == null) {
                throw new IllegalStateException("Log provider cannot be null");
            }
            if (leavesProvider == null) {
                throw new IllegalStateException("Leaves provider cannot be null");
            }
            if (logTarget == null) {
                throw new IllegalStateException("Log target cannot be null");
            }
            if (leavesTarget == null) {
                throw new IllegalStateException("Leaves target cannot be null");
            }

            return new TreeFromStructureConfig(
                    baseLocation,
                    canopyLocation,
                    height,
                    logProvider,
                    leavesProvider,
                    logTarget,
                    leavesTarget,
                    growableOn,
                    leavesPlacementFilter,
                    maxLogDepth,
                    treeDecorators,
                    replaceFromNBT,
                    isSapling,
                    orientation
            );
        }
    }
}