package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AnostylostromaBlock extends Block {
    public AnostylostromaBlock(Properties pProperties) {
        super(pProperties);
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pLevel.dimensionType().ultraWarm()) {
            pLevel.setBlock(pPos, UP2Blocks.PETRIFIED_ANOSTYLOSTROMA.get().defaultBlockState(), 3);
            pLevel.levelEvent(2009, pPos, 0);
            pLevel.playSound(null, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + pLevel.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }
}
