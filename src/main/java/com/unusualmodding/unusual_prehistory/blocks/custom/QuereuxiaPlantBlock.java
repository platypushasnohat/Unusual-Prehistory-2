package com.unusualmodding.unusual_prehistory.blocks.custom;

import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;

public class QuereuxiaPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {

    public QuereuxiaPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, Shapes.block(), true);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) UP2Blocks.QUEREUXIA.get();
    }

    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    public boolean canPlaceLiquid(BlockGetter getter, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
