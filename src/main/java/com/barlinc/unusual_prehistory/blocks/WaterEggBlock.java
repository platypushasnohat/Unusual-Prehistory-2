package com.barlinc.unusual_prehistory.blocks;

import com.barlinc.unusual_prehistory.blocks.blockentity.ExtraDataBlockEntity;
import com.barlinc.unusual_prehistory.blocks.blockentity.WaterEggBlockEntity;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public class WaterEggBlock extends WaterEggBlockEntity {

    private final Supplier<EntityType<?>> hatchedEntity;
    private final int babyCount;

    public WaterEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int spawnCount) {
        super(properties);
        this.hatchedEntity = hatchedEntity;
        this.babyCount = spawnCount;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        } else if (level.getFluidState(pos.below()).is(FluidTags.WATER)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (!(blockEntity instanceof ExtraDataBlockEntity dataBlockEntity)) return;
            UUID placer = dataBlockEntity.getOwner();
            level.destroyBlock(pos, false);
            for (int j = 0; j < babyCount; j++) {
                Entity entity = hatchedEntity.get().create(level);
                if (entity instanceof Mob mob) {
                    if (entity instanceof Animal animal) {
                        animal.setBaby(true);
                    }
                    if (entity instanceof PrehistoricAquaticMob prehistoricAquaticMob) {
                        prehistoricAquaticMob.setFromEgg(true);
                    }
                    if (entity instanceof PrehistoricMob prehistoricMob) {
                        prehistoricMob.setFromEgg(true);
                    }
                    int k = random.nextInt(1, 361);
                    if (placer != null) {
                        Player player = level.getPlayerByUUID(placer);
                        if(player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
                        }
                    }
                    entity.moveTo(pos.getX(), (double) pos.getY() - 0.5D, pos.getZ(), (float) k, 0.0F);
                    level.addFreshEntity(entity);
                    ForgeEventFactory.onFinalizeSpawn(mob, level,level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
                }
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ExtraDataBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide && placer instanceof Player player) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ExtraDataBlockEntity owned) {
                owned.setOwner(player.getUUID());
            }
        }
    }
}
