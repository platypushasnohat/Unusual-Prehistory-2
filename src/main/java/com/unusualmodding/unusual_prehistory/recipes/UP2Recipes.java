package com.unusualmodding.unusual_prehistory.recipes;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2Recipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE = DeferredRegister.create(Registries.RECIPE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ExtractorRecipe>> EXTRACTOR_SERIALIZER = SERIALIZERS.register("extracting", () -> ExtractorRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CultivatorRecipe>> CULTIVATOR_SERIALIZER = SERIALIZERS.register("cultivator", () -> CultivatorRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<CultivatorRecipe>> CULTIVATOR_RECIPE = RECIPE.register("cultivator", () -> new RecipeType<>() {});
}
