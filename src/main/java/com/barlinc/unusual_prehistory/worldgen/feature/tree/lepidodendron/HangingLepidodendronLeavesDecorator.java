package com.barlinc.unusual_prehistory.worldgen.feature.tree.lepidodendron;

import com.barlinc.unusual_prehistory.blocks.LepidodendronConeBlock;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Trees;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class HangingLepidodendronLeavesDecorator extends TreeDecorator {

    public static final HangingLepidodendronLeavesDecorator INSTANCE = new HangingLepidodendronLeavesDecorator();
    public static final Codec<HangingLepidodendronLeavesDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return UP2Trees.HANGING_LEPIDODENDRON_LEAVES.get();
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
}
