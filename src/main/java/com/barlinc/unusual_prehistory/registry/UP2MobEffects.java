package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.effects.CarnotaurusFury;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2MobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> CARNOTAURUS_FURY = MOB_EFFECTS.register("carnotaurus_fury", CarnotaurusFury::new);

}
