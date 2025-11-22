package com.barlinc.unusual_prehistory.blocks;

import com.barlinc.unusual_prehistory.blocks.blockentity.ExtraDataBlockEntity;
import com.barlinc.unusual_prehistory.blocks.blockentity.FrogSpawnBlockEntity;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class UnderwaterEggBlock extends FrogSpawnBlockEntity implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final Supplier<EntityType<?>> hatchedEntity;
    private final int babyCount;

    public UnderwaterEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int spawnCount) {
        super(properties);
        this.hatchedEntity = hatchedEntity;
        this.babyCount = spawnCount;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(!(blockEntity instanceof ExtraDataBlockEntity dataBlockEntity)) return;
            UUID placer = dataBlockEntity.getOwner();
            level.destroyBlock(pos, false);
            for (int j = 0; j < babyCount; j++) {
                Entity entity = hatchedEntity.get().create(level);
                if (entity instanceof Mob mob) {
                    if (entity instanceof Animal animal) {
                        animal.setAge(-24000);
                        animal.setPersistenceRequired();
                    }
                    if (entity instanceof PrehistoricAquaticMob prehistoricAquaticMob) {
                        prehistoricAquaticMob.setAge(-24000);
                        prehistoricAquaticMob.setPersistenceRequired();
                    }
                    if (entity instanceof PrehistoricMob prehistoricMob) {
                        prehistoricMob.setVariant(random.nextInt(prehistoricMob.getVariantCount()));
                    }
                    int k = random.nextInt(1, 361);
                    if(placer != null) {
                        Player player = level.getPlayerByUUID(placer);
                        if(player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
                        }
                    }
                    entity.moveTo(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), (float) k, 0.0F);
                    ForgeEventFactory.onFinalizeSpawn(mob, level,level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
                }
            }
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockPos blockpos = pos.below();
        FluidState fluidstate = level.getFluidState(pos);
        return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos) && fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return state.isFaceSturdy(blockGetter, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState state = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, state.is(FluidTags.WATER) && state.getAmount() == 8);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExtraDataBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @org.jetbrains.annotations.Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pPlacer instanceof Player player) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof ExtraDataBlockEntity owned) {
                owned.setOwner(player.getUUID());
            }
        }
    }

}
