package com.barl_inc.unusual_prehistory.block;

import com.barl_inc.unusual_prehistory.block.entity.MatrixBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MatrixBlock extends BrushableBlock {

    public MatrixBlock(Block block, SoundEvent brushSound, SoundEvent brushCompletedSound, Properties properties) {
        super(block, brushSound, brushCompletedSound, properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof MatrixBlockEntity blockEntity) {
            blockEntity.checkReset();
        }
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);
            fallingBlock.disableDrop();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MatrixBlockEntity(pos, state);
    }
}
