package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.SuspiciousStoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SuspiciousStoneBlock extends BrushableBlock {

    public SuspiciousStoneBlock(Block block, BlockBehaviour.Properties properties, SoundEvent chiselCompletedSound) {
        super(block, properties, SoundEvents.BRUSH_GENERIC, chiselCompletedSound);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState blockState, boolean b) {
        level.scheduleTick(pos, this, 2);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos blockPos) {
        level.scheduleTick(pos, this, 2);
        return super.updateShape(state, direction, blockState, level, pos, blockPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof SuspiciousStoneBlockEntity suspiciousStoneBlockEntity) {
            suspiciousStoneBlockEntity.checkReset();
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SuspiciousStoneBlockEntity(pos, state);
    }
}