package com.unusualmodding.unusualprehistory2.blocks.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UP2PlantBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public UP2PlantBlock(Properties props) {
        super(props);
    }
}
