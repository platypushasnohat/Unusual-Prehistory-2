package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class TarFluidBlock extends LiquidBlock {

    public static final VoxelShape STABLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public TarFluidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, CollisionContext context) {
        if (context.isHoldingItem(UP2Items.TAR_BUCKET.get())) return level.getBlockState(pos.above()).is(this) ? Shapes.block() : STABLE_SHAPE;
        return Shapes.empty();
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, BlockPos pos, Entity entity) {
        if (entity.getY() < pos.getY() + STABLE_SHAPE.max(Direction.Axis.Y)) {
            if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
                entity.makeStuckInBlock(state, new Vec3(0.9F, 1.0D, 0.9F));
            }
            if (!level.isClientSide) entity.setSharedFlagOnFire(true);
        }
    }

    @Override
    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance) {
        if (fallDistance >= 4.0D && entity instanceof LivingEntity livingEntity) {
            LivingEntity.Fallsounds fallSound = livingEntity.getFallSounds();
            SoundEvent sound = fallDistance < 7.0D ? fallSound.small() : fallSound.big();
            entity.playSound(sound, 1.0F, 1.0F);
        }
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
    }
}