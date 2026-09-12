package com.barl_inc.unusual_prehistory.entity.base;

import com.platypushasnohat.sinew.entity.base.AnimatedTamableAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class PrehistoricMob extends AnimatedTamableAnimal {

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }
}
