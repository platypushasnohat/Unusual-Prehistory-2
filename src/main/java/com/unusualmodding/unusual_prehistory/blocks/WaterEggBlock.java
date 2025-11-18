package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricAquaticMob;
import com.unusualmodding.unusual_prehistory.entity.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.FrogspawnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class WaterEggBlock extends FrogspawnBlock {

    private final RegistryObject<EntityType> hatchedEntity;
    private final int babyCount;

    public WaterEggBlock(Properties properties, RegistryObject hatchedEntity, int spawnCount) {
        super(properties);
        this.hatchedEntity = hatchedEntity;
        this.babyCount = spawnCount;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        } else if (level.getFluidState(pos.below()).is(FluidTags.WATER)) {
            level.destroyBlock(pos, false);
            for (int j = 0; j < babyCount; j++) {
                Entity entity = hatchedEntity.get().create(level);
                if (entity != null) {
                    if (entity instanceof Animal animal) {
                        animal.setAge(-24000);
                        animal.setPersistenceRequired();
                    }
                    if (entity instanceof PrehistoricAquaticMob prehistoricAquaticMob) {
                        prehistoricAquaticMob.setAge(-24000);
                        prehistoricAquaticMob.setPersistenceRequired();
                    }
                    if (entity instanceof PrehistoricMob prehistoricMob) {
                        prehistoricMob.setVariant(random.nextInt(prehistoricMob.getVariantCount()));
                    }
                    if (entity instanceof Dunkleosteus dunkleosteus) {
                        dunkleosteus.setDunkSize(random.nextInt(dunkleosteus.getVariantCount()));
                    }
                    int k = random.nextInt(1, 361);
                    entity.moveTo(pos.getX(), (double) pos.getY() - 0.5D, pos.getZ(), (float) k, 0.0F);
                    level.addFreshEntity(entity);
                }
            }
        }
    }
}
