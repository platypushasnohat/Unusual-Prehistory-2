package com.unusualmodding.unusual_prehistory.blocks.fluid;

import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TarFluid extends ForgeFlowingFluid {

    public TarFluid(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultFluidState().setValue(LEVEL, 8).setValue(FALLING, false));
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    @Override
    public int getAmount(@NotNull FluidState state) {
        return 8;
    }

    @Override
    public boolean isSource(FluidState state) {
        return !state.getValue(FALLING);
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        builder.add(LEVEL);
    }

//    @Override
//    protected void spread(@NotNull Level level, @NotNull BlockPos pos, FluidState fluidState) {
//        if (fluidState.isEmpty()) return;
//
//        BlockState blockstate = level.getBlockState(pos);
//        if (blockstate.getValue(TarFluidBlock.WAITING)) return;
//
//        BlockPos belowPos = pos.below();
//        BlockState belowState = level.getBlockState(belowPos);
//
//        var leadBelow = belowState.getFluidState().is(this);
//
//        if (fluidState.isSource()) {
//            if ((leadBelow && !belowState.getFluidState().isSource()) || (!leadBelow && !belowState.getFluidState().isEmpty())) {
//                if (!FluidInteractionRegistry.canInteract(level, belowPos)) {
//                    spreadTo(level, belowPos, belowState, Direction.DOWN, fluidState);
//                }
//                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
//            } else if (canSpreadTo(level, pos, blockstate, Direction.DOWN, belowPos, belowState, level.getFluidState(belowPos), fluidState.getType())) {
//                spreadTo(level, belowPos, belowState, Direction.DOWN, fluidState.setValue(FALLING, true));
//            }
//        }
//    }
//
//    @Override
//    protected void spreadTo(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state, @NotNull Direction direction, @NotNull FluidState fluid) {
//        if (!state.isAir()) this.beforeDestroyingBlock(level, pos, state);
//        level.setBlock(pos, fluid.createLegacyBlock().setValue(TarFluidBlock.WAITING, TarFluidBlock.shouldWait(level, pos)), 3);
//    }
//
//    @Override
//    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
//        if (state.getFluidState().is(this)) return;
//        level.levelEvent(1501, pos, 0);
//    }

    @Override
    public void animateTick(Level level, BlockPos blockPos, @NotNull FluidState fluidState, @NotNull RandomSource random) {
        BlockPos abovePos = blockPos.above();
        if (level.getBlockState(abovePos).isAir() && !level.getBlockState(abovePos).isSolidRender(level, abovePos)) {
            if (random.nextInt(300) == 0) {
                level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), UP2SoundEvents.TAR_POP.get(), SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }
    }

    @Override
    @Nullable
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_HONEY;
    }
}