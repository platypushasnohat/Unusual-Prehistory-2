package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob_effects.Dazzled;
import com.barlinc.unusual_prehistory.entity.mob_effects.Paralysis;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2MobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> DAZZLED = MOB_EFFECTS.register("dazzled", Dazzled::new);
    public static final DeferredHolder<MobEffect, MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis", Paralysis::new);
}
