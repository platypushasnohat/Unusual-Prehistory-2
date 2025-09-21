package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.CultivatingRecipe;
import com.unusualmodding.unusual_prehistory.recipes.ExtractingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2RecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CultivatingRecipe>> CULTIVATING_SERIALIZER = RECIPE_SERIALIZERS.register("cultivating", () -> CultivatingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ExtractingRecipe>> EXTRACTING_SERIALIZER = RECIPE_SERIALIZERS.register("extracting", () -> ExtractingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<CultivatingRecipe>> CULTIVATING_RECIPE = RECIPE_TYPES.register("cultivating", () -> new RecipeType<>(){});
    public static final RegistryObject<RecipeType<ExtractingRecipe>> EXTRACTING_RECIPE = RECIPE_TYPES.register("extracting", () -> new RecipeType<>(){});

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
