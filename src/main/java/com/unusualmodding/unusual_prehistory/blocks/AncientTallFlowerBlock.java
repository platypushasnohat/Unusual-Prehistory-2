package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AncientTallFlowerBlock extends TallFlowerBlock {

    protected static final VoxelShape UPPER_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape LOWER_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public AncientTallFlowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? LOWER_SHAPE.move(offset.x, offset.y, offset.z) : UPPER_SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter block, BlockPos pos) {
        return state.is(UP2BlockTags.ANCIENT_PLANT_PLACEABLES);
    }
}
