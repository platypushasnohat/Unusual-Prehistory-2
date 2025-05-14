package com.unusualmodding.unusual_prehistory.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class UP2FoodValues {

    public static final FoodProperties FROZEN_MEAT = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();

    public static final FoodProperties GINKGO_FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.2F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 0.5F).build();
}
