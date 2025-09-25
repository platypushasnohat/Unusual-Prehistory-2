package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.ChiselingRecipe;
import com.unusualmodding.unusual_prehistory.recipes.TransmogrificationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2RecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TransmogrificationRecipe>> TRANSMOGRIFICATION_SERIALIZER = RECIPE_SERIALIZERS.register("transmogrification", TransmogrificationRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<ChiselingRecipe>> CHISELING_SERIALIZER = RECIPE_SERIALIZERS.register("chiseling", ChiselingRecipe.Serializer::new);

}
