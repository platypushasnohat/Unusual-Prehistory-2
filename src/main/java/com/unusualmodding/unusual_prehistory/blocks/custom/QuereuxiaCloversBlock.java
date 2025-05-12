package com.unusualmodding.unusual_prehistory.blocks.custom;

import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class QuereuxiaCloversBlock extends WaterlilyBlock {

    public QuereuxiaCloversBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockPos);
        BlockState blockstateBelow = context.getLevel().getBlockState(blockPos.below());
        Level level = context.getLevel();

        if (blockstate.is(Blocks.AIR) && blockstateBelow.is(UP2Blocks.QUEREUXIA.get())) {
            level.setBlockAndUpdate(context.getClickedPos(),UP2Blocks.QUEREUXIA_CLOVERS.get().defaultBlockState());
            level.setBlockAndUpdate(context.getClickedPos().below(), UP2Blocks.QUEREUXIA_PLANT.get().defaultBlockState());
            level.playSound(context.getPlayer(), blockPos, SoundEvents.LILY_PAD_PLACE, SoundSource.BLOCKS,1.0F, 1.0F );
            return null;
        }
        return super.getStateForPlacement(context);
    }
}
