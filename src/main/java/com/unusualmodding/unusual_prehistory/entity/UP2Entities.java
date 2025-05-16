package com.unusualmodding.unusual_prehistory.entity;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UP2Entities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<EntityType<DunkleosteusEntity>> DUNKLEOSTEUS = ENTITY_TYPE.register("dunkleosteus", () -> EntityType.Builder.of(DunkleosteusEntity::new, MobCategory.WATER_CREATURE).sized(0.75F, 0.6F).build("dunkleosteus"));
    public static final RegistryObject<EntityType<KimmeridgebrachypteraeschnidiumEntity>> KIMMERIDGEBRACHYPTERAESCHNIDIUM = ENTITY_TYPE.register("kimmeridgebrachypteraeschnidium", () -> EntityType.Builder.of(KimmeridgebrachypteraeschnidiumEntity::new, MobCategory.CREATURE).sized(0.5F, 0.5F).build("kimmeridgebrachypteraeschnidium"));
    public static final RegistryObject<EntityType<UnicornEntity>> UNICORN = ENTITY_TYPE.register("unicorn", () -> EntityType.Builder.of(UnicornEntity::new, MobCategory.CREATURE).sized(1.2F, 3.0F).build("unicorn"));

}
