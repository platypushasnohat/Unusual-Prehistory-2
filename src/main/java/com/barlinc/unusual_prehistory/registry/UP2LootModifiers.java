package com.barlinc.unusual_prehistory.registry;

import com.mojang.serialization.Codec;
import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.modifier.AddLootTableModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2LootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<Codec<AddLootTableModifier>> ADD_LOOT_TABLE = LOOT_MODIFIERS.register("add_loot_table", AddLootTableModifier.CODEC);
}
