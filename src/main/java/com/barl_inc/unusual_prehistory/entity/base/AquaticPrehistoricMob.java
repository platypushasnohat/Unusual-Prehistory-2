package com.barl_inc.unusual_prehistory.entity.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AquaticPrehistoricMob extends PrehistoricMob {

    protected AquaticPrehistoricMob(EntityType<? extends AquaticPrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }
}
