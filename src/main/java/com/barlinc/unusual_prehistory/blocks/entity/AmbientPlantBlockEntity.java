package com.barlinc.unusual_prehistory.blocks.entity;

import com.barlinc.unusual_prehistory.blocks.plant.AmbientPlantBlock;
import com.barlinc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class AmbientPlantBlockEntity extends BlockEntity {

    private int timer = 0;

    public AmbientPlantBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.AMBIENT_PLANT.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AmbientPlantBlockEntity blockEntity) {
        if (level.isClientSide) return;
        blockEntity.timer++;
        if (state.getBlock() instanceof AmbientPlantBlock block && blockEntity.timer >= 500) {
            EntityType<?> type = block.toSpawn.get();
            if (type != null) {
                Vec3 vec3 = pos.getCenter();
                for (int j = 0; j < 3; j++) {
                    Entity entity = type.create(level);
                    if (entity instanceof Mob mob) {
                        entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                        level.addFreshEntity(entity);
                        ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
                    }
                }
                blockEntity.timer = 0;
            }
        }
    }
}