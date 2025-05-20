package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HorsetailBlock extends AncientPlantBlock implements BonemealableBlock {

    public HorsetailBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos source, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource source, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource source, BlockPos pos, BlockState state) {
        AncientTallPlantBlock doubleplantblock = (AncientTallPlantBlock) (UP2Blocks.LARGE_HORSETAIL.get());
        if (doubleplantblock.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(level, doubleplantblock.defaultBlockState(), pos, 2);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(UP2BlockTags.ANCIENT_PLANT_PLACEABLES);
    }
}
