package com.barlinc.unusual_prehistory.mixins;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {

    // Don't collide with the lowest leaf block
    @ModifyReturnValue(method = "getCollisionShape", at = @At("RETURN"))
    protected VoxelShape getCollisionShape(VoxelShape original, @Local(argsOnly = true) BlockState state, @Local(argsOnly = true) BlockGetter level, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) CollisionContext context) {
        if (state.getBlock() instanceof LeavesBlock && context instanceof EntityCollisionContext entityContext && entityContext.getEntity() instanceof PrehistoricMob prehistoricMob && prehistoricMob.getControllingPassenger() instanceof Player) {
            if (level.getBlockState(pos.below()).getBlock() instanceof LeavesBlock) {
                return original;
            }
            return Shapes.empty();
        }
        return original;
    }
}