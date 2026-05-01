package com.barlinc.unusual_prehistory.entity.accessor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;

public interface MobAccessor {

    void unusualPrehistory2$dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean playerKill);
}