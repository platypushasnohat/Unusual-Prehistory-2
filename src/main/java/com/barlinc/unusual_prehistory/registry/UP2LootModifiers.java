package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.modifier.AddLootTableModifier;
import com.barlinc.unusual_prehistory.modifier.FossilSiteMapLootModifier;
import com.barlinc.unusual_prehistory.modifier.ReplaceLootTableModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class UP2LootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<Codec<AddLootTableModifier>> ADD_LOOT_TABLE = LOOT_MODIFIERS.register("add_loot_table", AddLootTableModifier.CODEC);
    public static final RegistryObject<Codec<ReplaceLootTableModifier>> REPLACE_LOOT_TABLE = LOOT_MODIFIERS.register("replace_loot_table", ReplaceLootTableModifier.CODEC);
    public static final RegistryObject<Codec<FossilSiteMapLootModifier>> CABIN_MAP_LOOT_MODIFIER = LOOT_MODIFIERS.register("fossil_site_map", FossilSiteMapLootModifier.CODEC);

}
