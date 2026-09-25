package com.barl_inc.unusual_prehistory.block;

import com.barl_inc.unusual_prehistory.block.entity.FossilBedBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FossilBedBlock extends BaseEntityBlock implements Fallable {

    private final IntProvider xpRange;

    public FossilBedBlock(IntProvider xpRange, Properties properties) {
        super(properties);
        this.xpRange = xpRange;
    }

    @Nullable
    @Override
    protected MapCodec<FossilBedBlock> codec() {
        return null;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, 2);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        level.scheduleTick(pos, this, 2);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);
            fallingBlock.disableDrop();
        }
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
        Vec3 center = fallingBlock.getBoundingBox().getCenter();
        level.levelEvent(2001, BlockPos.containing(center), Block.getId(fallingBlock.getBlockState()));
        level.gameEvent(fallingBlock, GameEvent.BLOCK_DESTROY, center);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockpos = pos.below();
            if (FallingBlock.isFree(level.getBlockState(blockpos))) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() - 0.05D;
                double z = pos.getZ() + random.nextDouble();
                level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), x, y, z, 0.0F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack stack) {
        return this.xpRange.sample(level.getRandom());
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && !player.isCreative()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FossilBedBlockEntity fossilBed && level instanceof ServerLevel serverLevel) {
                fossilBed.unpackLootTable(serverLevel);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
        return state;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FossilBedBlockEntity(pos, state);
    }
}
