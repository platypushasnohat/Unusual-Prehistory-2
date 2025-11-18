package com.unusualmodding.unusual_prehistory.worldgen.feature.tree_decorator;

import com.mojang.serialization.Codec;
import com.unusualmodding.unusual_prehistory.blocks.LepidodendronConeBlock;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2TreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class HangingLepidodendronLeavesDecorator extends TreeDecorator {

    public static final Codec<HangingLepidodendronLeavesDecorator> CODEC;
    public static final HangingLepidodendronLeavesDecorator INSTANCE = new HangingLepidodendronLeavesDecorator();

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return UP2TreeDecorators.HANGING_LEPIDODENDRON_LEAVES.get();
    }

    @Override
    public void place(Context context) {
        for (BlockPos pos : context.leaves()) {
            if (context.level().isStateAtPosition(pos.below(), BlockState::isAir)) {
                if (context.random().nextInt(2) == 0) {
                    context.setBlock(pos.below(), UP2Blocks.HANGING_LEPIDODENDRON_LEAVES.get().defaultBlockState());
                }
                if (context.random().nextInt(24) == 0) {
                    context.setBlock(pos.below(), LepidodendronConeBlock.createHangingCone());
                }
            }
        }
    }

    static {
        CODEC = Codec.unit(() -> INSTANCE);
    }
}
