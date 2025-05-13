package com.unusualmodding.unusual_prehistory.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GinkgoLeavesBlock extends LeavesBlock {

    public final Supplier<ParticleType> particle;

    public GinkgoLeavesBlock(BlockBehaviour.Properties properties, Supplier<ParticleType> particle) {
        super(properties);
        this.particle = particle;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
        super.animateTick(state, level, pos, source);
        if (!(source.nextFloat() >= 0.017F)) {
            BlockPos pos1 = pos.below();
            BlockState state1 = level.getBlockState(pos1);
            if (!isFaceFull(state1.getCollisionShape(level, pos1), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, source, (ParticleOptions) particle.get());
            }
        }
    }
}
