package com.barl_inc.unusual_prehistory.entity.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class SchoolingPrehistoricMob extends AquaticPrehistoricMob {

    protected SchoolingPrehistoricMob(EntityType<? extends SchoolingPrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }
}
