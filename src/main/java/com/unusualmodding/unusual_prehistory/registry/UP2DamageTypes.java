package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class UP2DamageTypes {

    public static final ResourceKey<DamageType> TAR = register("tar");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(TAR, new DamageType("tar", 0.2F));
    }

    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, UnusualPrehistory2.modPrefix(name));
    }
}
