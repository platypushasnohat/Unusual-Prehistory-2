package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.FossilBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class FossilBlock extends BrushableBlock {

    public FossilBlock(BlockBehaviour.Properties properties, SoundEvent chiselCompletedSound) {
        super(Blocks.AIR, properties, SoundEvents.EMPTY, chiselCompletedSound);
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
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        super.playerWillDestroy(level, blockPos, blockState, player);
        if (!player.getAbilities().instabuild && level.getBlockEntity(blockPos) instanceof FossilBlockEntity fossilBlockEntity && blockState.getValue(BlockStateProperties.DUSTED) == 3 && !(player.getMainHandItem().isCorrectToolForDrops(blockState) && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) > 0)) {
            fossilBlockEntity.dropContent(player);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
            fallingblockentity.disableDrop();
            fallingblockentity.setHurtsEntities(1.0F, 3);
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FossilBlockEntity(pos, state);
    }
}