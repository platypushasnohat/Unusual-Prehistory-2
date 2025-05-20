package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class WoodBlocks extends RotatedPillarBlock {

    public WoodBlocks(Properties properties) {
        super(properties);
    }

    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        ItemStack itemStack = context.getItemInHand();
        if (!itemStack.canPerformAction(toolAction))
            return null;

        if (ToolActions.AXE_STRIP == toolAction) {
            if (this == UP2Blocks.GINKGO_LOG.get()) {
                return UP2Blocks.STRIPPED_GINKGO_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UP2Blocks.GINKGO_WOOD.get()) {
                return UP2Blocks.STRIPPED_GINKGO_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
