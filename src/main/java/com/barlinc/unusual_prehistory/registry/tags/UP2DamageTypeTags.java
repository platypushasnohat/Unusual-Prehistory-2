package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class UP2DamageTypeTags {

    public static final TagKey<DamageType> KENTROSAURUS_IMMUNE_TO = damageTypeTag("kentrosaurus_immune_to");

    public static TagKey<DamageType> damageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, UnusualPrehistory2.modPrefix(name));
    }
}
