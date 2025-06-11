package com.unusualmodding.unusual_prehistory.worldgen.tree_decorator;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.blocks.LepidodendronGrowthsBlock;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Features;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class LepidodendronGrowthsDecorator extends TreeDecorator {

    public static final Codec<LepidodendronGrowthsDecorator> CODEC;
    public static final LepidodendronGrowthsDecorator INSTANCE = new LepidodendronGrowthsDecorator();

    @Override
    protected TreeDecoratorType<?> type() {
        return UP2Features.LEPIDODENDRON_GROWTHS.get();
    }

    @Override
    public void place(TreeDecorator.Context context) {
        RandomSource random = context.random();
        List<BlockPos> list = context.logs();
        List<BlockPos> list1 = context.leaves();

        int i = list.get(0).getY();
        int j = list1.get(0).getY();

        list.stream().filter((pos) -> pos.getY() - i <= 20).forEach((pos1) -> {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (random.nextFloat() <= 0.12F) {
                    BlockPos blockpos = pos1.offset(direction.getStepX(), 0, direction.getStepZ());
                    if (context.isAir(blockpos)) {
                        context.setBlock(blockpos, UP2Blocks.LEPIDODENDRON_GROWTHS.get().defaultBlockState().setValue(LepidodendronGrowthsBlock.FACING, direction));
                    }
                }
            }
        });

        list1.stream().filter((pos) -> pos.getY() - j <= 20).forEach((pos1) -> {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (random.nextFloat() <= 0.08F) {
                    BlockPos blockpos = pos1.offset(direction.getStepX(), 0, direction.getStepZ());
                    if (context.isAir(blockpos)) {
                        context.setBlock(blockpos, UP2Blocks.LEPIDODENDRON_GROWTHS.get().defaultBlockState().setValue(LepidodendronGrowthsBlock.FACING, direction));
                    }
                }
            }
            for (Direction direction : Direction.Plane.VERTICAL) {
                if (random.nextFloat() <= 0.08F) {
                    BlockPos blockpos = pos1.offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
                    if (context.isAir(blockpos)) {
                        context.setBlock(blockpos, UP2Blocks.LEPIDODENDRON_GROWTHS.get().defaultBlockState().setValue(LepidodendronGrowthsBlock.FACING, direction));
                    }
                }
            }
        });
    }

    static {
        CODEC = Codec.unit(() -> INSTANCE);
    }
}
