package com.barlinc.unusual_prehistory.blocks;

import com.barlinc.unusual_prehistory.blocks.entity.MatrixBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MatrixBlock extends BrushableBlock {

    public MatrixBlock(SoundEvent brushSound, SoundEvent brushCompletedSound, Properties properties) {
        super(Blocks.DIRT, brushSound, brushCompletedSound, properties);
    }

    @Override
    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (level.getBlockEntity(pos) instanceof MatrixBlockEntity blockEntity) {
            blockEntity.checkReset();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new MatrixBlockEntity(pos, state);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
    }
}
