package com.barlinc.unusual_prehistory.mixins;

import com.barlinc.unusual_prehistory.utils.EntityDropsAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class MobMixin implements EntityDropsAccessor {

    @Shadow
    protected abstract void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean hitByPlayer);

    public void unusualPrehistory2$dropCustomDeathLoot(DamageSource damageSource, int looting, boolean hitByPlayer) {
        this.dropCustomDeathLoot(damageSource, looting, hitByPlayer);
    }
}