package com.barl_inc.unusual_prehistory.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class UP2FoodProperties {

    public static final FoodProperties LEEDSICHTHYS_CHUNK = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 300), 0.01F)
            .build();

}
