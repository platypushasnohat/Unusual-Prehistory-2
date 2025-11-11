package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.UP2DamageTypes;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.UP2Particles;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class TarBlock extends Block implements BucketPickup {

    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.9F, 1.0D);

    public TarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean skipRendering(@NotNull BlockState state, BlockState adjacentState, @NotNull Direction direction) {
        return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
    }

    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            DamageSource damageSource = level.damageSources().source(UP2DamageTypes.TAR);
            if (!entity.getType().is(UP2EntityTags.TAR_WALKABLE_MOBS)) {
                entity.makeStuckInBlock(state, new Vec3(0.15F, 0.3D, 0.15F));
                entity.hurt(damageSource,1);
            }
            if (level.isClientSide) {
                RandomSource randomsource = level.getRandom();
                boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (flag && randomsource.nextInt(100) == 0) {
                    level.addParticle(UP2Particles.TAR_BUBBLE.get(), entity.getX(), pos.getY() + 1, entity.getZ(), Mth.randomBetween(randomsource, 0.0F, 1.0F) * 0.083333336F, 0.05F, Mth.randomBetween(randomsource, 0.0F, 1.0F) * 0.083333336F);
                }
            }
        }
    }

    @Override
    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float falldistance) {
        if (!((double) falldistance < 4.0D) && entity instanceof LivingEntity livingentity) {
            LivingEntity.Fallsounds fallsounds = livingentity.getFallSounds();
            SoundEvent soundevent = (double) falldistance < 7.0D ? fallsounds.small() : fallsounds.big();
            entity.playSound(soundevent, 1.0F, 1.0F);
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (context instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_COLLISION_SHAPE;
                }
                boolean flag = entity instanceof FallingBlockEntity;
                if (flag || entity.getType().is(UP2EntityTags.TAR_WALKABLE_MOBS) && context.isAbove(Shapes.block(), pos, false) && !context.isDescending()) {
                    return super.getCollisionShape(state, blockGetter, pos, context);
                }
            }
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public @NotNull ItemStack pickupBlock(LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        if (!level.isClientSide()) {
            level.levelEvent(2001, pos, Block.getId(state));
        }
        return new ItemStack(UP2Items.TAR_BUCKET.get());
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull PathComputationType computationType) {
        return true;
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        BlockPos abovePos = pos.above();
        if (level.getBlockState(abovePos).isAir() && !level.getBlockState(abovePos).isSolidRender(level, abovePos)) {
            if (random.nextInt(50) == 0) {
                double x = (double) pos.getX() + random.nextDouble();
                double y = (double) pos.getY() + 1.0D;
                double z = (double) pos.getZ() + random.nextDouble();
                level.addParticle(UP2Particles.TAR_BUBBLE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
                level.playLocalSound(x, y, z, UP2SoundEvents.TAR_POP.get(), SoundSource.BLOCKS, 0.1F, 0.6F + random.nextFloat() * 0.5F, false);
            }
        }
    }
}
