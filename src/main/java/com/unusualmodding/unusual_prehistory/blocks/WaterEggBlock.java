package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.entity.Diplocaulus;
import com.unusualmodding.unusual_prehistory.entity.Dunkleosteus;
import com.unusualmodding.unusual_prehistory.entity.base.AncientAquaticEntity;
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

public class WaterEggBlock extends FrogspawnBlock {

    private final RegistryObject<EntityType> hatchedEntity;

    public WaterEggBlock(Properties properties, RegistryObject hatchedEntity) {
        super(properties);
        this.hatchedEntity = hatchedEntity;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        } else if (level.getFluidState(pos.below()).is(FluidTags.WATER)) {
            level.destroyBlock(pos, false);
            int i = 2 + random.nextInt(2);
            for (int j = 1; j <= i; ++j) {
                Entity entity = hatchedEntity.get().create(level);
                if (entity != null) {
                    if (entity instanceof Animal animal) {
                        animal.setAge(-24000);
                        animal.setPersistenceRequired();
                    }
                    if (entity instanceof AncientAquaticEntity ancientAquatic) {
                        ancientAquatic.setAge(-24000);
                        ancientAquatic.setPersistenceRequired();
                    }
                    if (entity instanceof Dunkleosteus dunkleosteus) {
                        int sizeChange = random.nextInt(0, 100);
                        if (sizeChange <= 30) {
                            dunkleosteus.setDunkSize(1);
                        } else if (sizeChange <= 60) {
                            dunkleosteus.setDunkSize(2);
                        } else {
                            dunkleosteus.setDunkSize(0);
                        }
                    }
                    if (entity instanceof PrehistoricMob prehistoricMob) {
                        prehistoricMob.setVariant(random.nextInt(prehistoricMob.getVariantCount()));
                    }
                    int k = random.nextInt(1, 361);
                    entity.moveTo(pos.getX(), (double) pos.getY() - 0.5D, pos.getZ(), (float) k, 0.0F);
                    level.addFreshEntity(entity);
                }
            }
        }
    }
}
