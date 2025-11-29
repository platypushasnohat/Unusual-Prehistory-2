package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.*;
import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UP2Entities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<EntityType<Carnotaurus>> CARNOTAURUS = ENTITY_TYPE.register(
            "carnotaurus", () ->
            EntityType.Builder.of(Carnotaurus::new, MobCategory.CREATURE)
                    .sized(1.75F, 2.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "carnotaurus").toString())
    );

    public static final RegistryObject<EntityType<Diplocaulus>> DIPLOCAULUS = ENTITY_TYPE.register(
            "diplocaulus", () ->
            EntityType.Builder.of(Diplocaulus::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.4F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "diplocaulus").toString())
    );

    public static final RegistryObject<EntityType<Dromaeosaurus>> DROMAEOSAURUS = ENTITY_TYPE.register(
            "dromaeosaurus", () ->
            EntityType.Builder.of(Dromaeosaurus::new, MobCategory.CREATURE)
                    .sized(0.7F, 1.25F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "dromaeosaurus").toString())
    );

    public static final RegistryObject<EntityType<Dunkleosteus>> DUNKLEOSTEUS = ENTITY_TYPE.register(
            "dunkleosteus", () ->
            EntityType.Builder.of(Dunkleosteus::new, MobCategory.WATER_CREATURE)
                    .sized(0.75F, 0.6F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "dunkleosteus").toString())
    );

    public static final RegistryObject<EntityType<JawlessFish>> JAWLESS_FISH = ENTITY_TYPE.register(
            "jawless_fish", () ->
            EntityType.Builder.of(JawlessFish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.36F, 0.36F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "jawless_fish").toString())
    );

    public static final RegistryObject<EntityType<Kentrosaurus>> KENTROSAURUS = ENTITY_TYPE.register(
            "kentrosaurus", () ->
            EntityType.Builder.of(Kentrosaurus::new, MobCategory.CREATURE)
                    .sized(1.98F, 2.25F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "kentrosaurus").toString())
    );

    public static final RegistryObject<EntityType<Kimmeridgebrachypteraeschnidium>> KIMMERIDGEBRACHYPTERAESCHNIDIUM = ENTITY_TYPE.register(
            "kimmeridgebrachypteraeschnidium", () ->
            EntityType.Builder.of(Kimmeridgebrachypteraeschnidium::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .build("kimmeridgebrachypteraeschnidium")
    );

    public static final RegistryObject<EntityType<KimmeridgebrachypteraeschnidiumNymph>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH = ENTITY_TYPE.register(
            "kimmeridgebrachypteraeschnidium_nymph", () ->
            EntityType.Builder.of(KimmeridgebrachypteraeschnidiumNymph::new, MobCategory.WATER_CREATURE)
                    .sized(0.4F, 0.3F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "kimmeridgebrachypteraeschnidium_nymph").toString())
    );

    public static final RegistryObject<EntityType<Majungasaurus>> MAJUNGASAURUS = ENTITY_TYPE.register(
            "majungasaurus", () ->
            EntityType.Builder.of(Majungasaurus::new, MobCategory.CREATURE)
                    .sized(1.25F, 2.25F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "majungasaurus").toString())
    );

    public static final RegistryObject<EntityType<Megalania>> MEGALANIA = ENTITY_TYPE.register(
            "megalania", () ->
            EntityType.Builder.of(Megalania::new, MobCategory.CREATURE)
                    .sized(1.7F, 1.5F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "megalania").toString())
    );

    public static final RegistryObject<EntityType<Metriorhynchus>> METRIORHYNCHUS = ENTITY_TYPE.register(
            "metriorhynchus", () ->
            EntityType.Builder.of(Metriorhynchus::new, MobCategory.WATER_CREATURE)
                    .sized(1.35F, 1.2F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "metriorhynchus").toString())
    );

    public static final RegistryObject<EntityType<Onchopristis>> ONCHOPRISTIS = ENTITY_TYPE.register(
            "onchopristis", () ->
            EntityType.Builder.of(Onchopristis::new, MobCategory.WATER_CREATURE)
                    .sized(1.2F, 0.5F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "onchopristis").toString())
    );

    public static final RegistryObject<EntityType<Stethacanthus>> STETHACANTHUS = ENTITY_TYPE.register(
            "stethacanthus", () ->
            EntityType.Builder.of(Stethacanthus::new, MobCategory.WATER_CREATURE)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "stethacanthus").toString())
    );

    public static final RegistryObject<EntityType<Talpanas>> TALPANAS = ENTITY_TYPE.register(
            "talpanas", () ->
            EntityType.Builder.of(Talpanas::new, MobCategory.CREATURE)
                    .sized(0.75F, 0.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "talpanas").toString())
    );

    public static final RegistryObject<EntityType<Telecrex>> TELECREX = ENTITY_TYPE.register(
            "telecrex", () ->
            EntityType.Builder.of(Telecrex::new, MobCategory.CREATURE)
                    .sized(0.75F, 0.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "telecrex").toString())
    );

    public static final RegistryObject<EntityType<Unicorn>> UNICORN = ENTITY_TYPE.register(
            "unicorn", () ->
            EntityType.Builder.of(Unicorn::new, MobCategory.CREATURE)
                    .sized(1.25F, 2.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "unicorn").toString())
    );

    // Eggs
    public static final RegistryObject<EntityType<ThrowableEgg>> DROMAEOSAURUS_EGG = ENTITY_TYPE.register(
            "dromaeosaurus_egg", () ->
            EntityType.Builder.<ThrowableEgg>of((entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS::get), MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "dromaeosaurus_egg").toString())
    );

    public static final RegistryObject<EntityType<ThrowableEgg>> TALPANAS_EGG = ENTITY_TYPE.register(
            "talpanas_egg", () ->
            EntityType.Builder.<ThrowableEgg>of((entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS::get), MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "talpanas_egg").toString())
    );

    public static final RegistryObject<EntityType<ThrowableEgg>> TELECREX_EGG = ENTITY_TYPE.register(
            "telecrex_egg", () ->
            EntityType.Builder.<ThrowableEgg>of((entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.TELECREX_EGG, UP2Entities.TELECREX::get), MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "telecrex_egg").toString())
    );

    // Temporary
    public static final RegistryObject<EntityType<ThrowableEgg>> METRI_TEST_EGG = ENTITY_TYPE.register(
            "metri_test_egg", () ->
            EntityType.Builder.<ThrowableEgg>of((entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.METRIORHYNCHUS_EMBRYO, UP2Entities.METRIORHYNCHUS::get), MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "metri_test_egg").toString())
    );

    public static final RegistryObject<EntityType<UP2Boat>> BOAT = ENTITY_TYPE.register(
            "boat", () ->
            EntityType.Builder.<UP2Boat>of(UP2Boat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "boat").toString())
    );

    public static final RegistryObject<EntityType<UP2ChestBoat>> CHEST_BOAT = ENTITY_TYPE.register(
            "chest_boat", () ->
            EntityType.Builder.<UP2ChestBoat>of(UP2ChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .build(new ResourceLocation(UnusualPrehistory2.MOD_ID, "chest_boat").toString())
    );
}
