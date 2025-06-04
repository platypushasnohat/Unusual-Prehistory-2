package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UP2Entities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<EntityType<Diplocaulus>> DIPLOCAULUS = ENTITY_TYPE.register(
            "diplocaulus", () ->
            EntityType.Builder.of(Diplocaulus::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(10)
                    .build("diplocaulus")
    );

    public static final RegistryObject<EntityType<Dunkleosteus>> DUNKLEOSTEUS = ENTITY_TYPE.register(
            "dunkleosteus", () ->
            EntityType.Builder.of(Dunkleosteus::new, MobCategory.WATER_CREATURE)
                    .sized(0.75F, 0.6F)
                    .clientTrackingRange(10)
                    .build("dunkleosteus")
    );

    public static final RegistryObject<EntityType<JawlessFish>> JAWLESS_FISH = ENTITY_TYPE.register(
            "jawless_fish", () ->
            EntityType.Builder.of(JawlessFish::new, MobCategory.WATER_CREATURE)
                    .sized(0.36F, 0.36F)
                    .clientTrackingRange(10)
                    .build("jawless_fish")
    );

    public static final RegistryObject<EntityType<Kentrosaurus>> KENTROSAURUS = ENTITY_TYPE.register(
            "kentrosaurus", () ->
                    EntityType.Builder.of(Kentrosaurus::new, MobCategory.CREATURE)
                            .sized(2.0F, 2.25F)
                            .clientTrackingRange(10)
                            .build("kentrosaurus")
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
                    .build("kimmeridgebrachypteraeschnidium_nymph")
    );

    public static final RegistryObject<EntityType<Megalania>> MEGALANIA = ENTITY_TYPE.register(
            "megalania", () ->
                    EntityType.Builder.of(Megalania::new, MobCategory.CREATURE)
                            .sized(2.0F, 1.5F)
                            .clientTrackingRange(10)
                            .build("megalania")
    );

    public static final RegistryObject<EntityType<Scaumenacia>> SCAUMENACIA = ENTITY_TYPE.register(
            "scaumenacia", () ->
            EntityType.Builder.of(Scaumenacia::new, MobCategory.WATER_CREATURE)
                    .sized(0.7F, 0.6F)
                    .clientTrackingRange(10)
                    .build("scaumenacia")
    );

    public static final RegistryObject<EntityType<Stethacanthus>> STETHACANTHUS = ENTITY_TYPE.register(
            "stethacanthus", () ->
            EntityType.Builder.of(Stethacanthus::new, MobCategory.WATER_CREATURE)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(10)
                    .build("stethacanthus")
    );

    public static final RegistryObject<EntityType<Unicorn>> UNICORN = ENTITY_TYPE.register(
            "unicorn", () ->
            EntityType.Builder.of(Unicorn::new, MobCategory.CREATURE)
                    .sized(1.2F, 3.0F)
                    .clientTrackingRange(10)
                    .build("unicorn")
    );
}
