package com.barlinc.unusual_prehistory.blocks.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AmbientPlantBlock2 extends PrehistoricPlantBlock {

    private final Supplier<EntityType<?>> toSpawn;
    private final int spawnCount;

    public AmbientPlantBlock2(Properties properties, Supplier<EntityType<?>> toSpawn, int spawnCount) {
        super(properties);
        this.toSpawn = toSpawn;
        this.spawnCount = spawnCount;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.spawnEntity(level, pos, state, random);
    }

    public void spawnEntity(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        for (int j = 0; j < this.spawnCount; j++) {
            Vec3 vec3 = pos.getCenter();
            Entity entity = toSpawn.get().create(level);
            if (entity instanceof Mob mob) {
                entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                level.addFreshEntity(entity);
                ForgeEventFactory.onFinalizeSpawn(mob, level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
            }
        }
        level.scheduleTick(pos, this, 500);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState, boolean b) {
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
        level.scheduleTick(pos, this, 500);
    }
}
