package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Tartuosteus;
import com.barlinc.unusual_prehistory.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Zhangsolva;
import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class UP2Entities {

    public static List<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> ENTITY_TRANSLATIONS = new ArrayList<>();

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    // Update 1
    public static final DeferredHolder<EntityType<?>, EntityType<Carnotaurus>> CARNOTAURUS = registerLivingEntity("carnotaurus", Carnotaurus::new, MobCategory.CREATURE, 1.6F, 2.9F);
    public static final DeferredHolder<EntityType<?>, EntityType<Diplocaulus>> DIPLOCAULUS = registerLivingEntity("diplocaulus", Diplocaulus::new, MobCategory.CREATURE, 0.6F, 0.4F);
    public static final DeferredHolder<EntityType<?>, EntityType<Dromaeosaurus>> DROMAEOSAURUS = registerLivingEntity("dromaeosaurus", Dromaeosaurus::new, MobCategory.CREATURE, 0.7F, 1.2F);
    public static final DeferredHolder<EntityType<?>, EntityType<Dunkleosteus>> DUNKLEOSTEUS = registerLivingEntity("dunkleosteus", Dunkleosteus::new, MobCategory.WATER_CREATURE, 0.75F, 0.6F);
    public static final DeferredHolder<EntityType<?>, EntityType<JawlessFish>> JAWLESS_FISH = registerLivingEntity("jawless_fish", JawlessFish::new, MobCategory.WATER_AMBIENT, 0.36F, 0.36F);
    public static final DeferredHolder<EntityType<?>, EntityType<Kentrosaurus>> KENTROSAURUS = registerLivingEntity("kentrosaurus", Kentrosaurus::new, MobCategory.CREATURE, 1.6F, 2.2F);
    public static final DeferredHolder<EntityType<?>, EntityType<Kimmeridgebrachypteraeschnidium>> KIMMERIDGEBRACHYPTERAESCHNIDIUM = registerLivingEntity("kimmeridgebrachypteraeschnidium", Kimmeridgebrachypteraeschnidium::new, MobCategory.CREATURE, 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<KimmeridgebrachypteraeschnidiumNymph>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH = registerLivingEntity("kimmeridgebrachypteraeschnidium_nymph", KimmeridgebrachypteraeschnidiumNymph::new, MobCategory.CREATURE, 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<Majungasaurus>> MAJUNGASAURUS = registerLivingEntity("majungasaurus", Majungasaurus::new, MobCategory.CREATURE, 1.2F, 1.9F);
    public static final DeferredHolder<EntityType<?>, EntityType<Megalania>> MEGALANIA = registerLivingEntity("megalania", Megalania::new, MobCategory.CREATURE, 1.7F, 1.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<Stethacanthus>> STETHACANTHUS = registerLivingEntity("stethacanthus", Stethacanthus::new, MobCategory.WATER_CREATURE, 0.7F, 0.7F);
    public static final DeferredHolder<EntityType<?>, EntityType<Talpanas>> TALPANAS = registerLivingEntity("talpanas", Talpanas::new, MobCategory.CREATURE, 0.7F, 0.9F);
    public static final DeferredHolder<EntityType<?>, EntityType<Telecrex>> TELECREX = registerLivingEntity("telecrex", Telecrex::new, MobCategory.CREATURE, 0.7F, 0.9F);
    public static final DeferredHolder<EntityType<?>, EntityType<Unicorn>> UNICORN = registerLivingEntity("unicorn", Unicorn::new, MobCategory.CREATURE, 1.2F, 2.9F);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> DROMAEOSAURUS_EGG = registerEntity("dromaeosaurus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS::get), MobCategory.MISC, 0.25F, 0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> TALPANAS_EGG = registerEntity("talpanas_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS::get), MobCategory.MISC, 0.25F, 0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> TELECREX_EGG = registerEntity("telecrex_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.TELECREX_EGG, UP2Entities.TELECREX::get), MobCategory.MISC, 0.25F, 0.25F);

    // Update 2
    public static final DeferredHolder<EntityType<?>, EntityType<Onchopristis>> ONCHOPRISTIS = registerLivingEntity("onchopristis", Onchopristis::new, MobCategory.WATER_CREATURE, 1.2F, 0.5F);

    // Update 3
    public static final DeferredHolder<EntityType<?>, EntityType<LivingOoze>> LIVING_OOZE = registerLivingEntity("living_ooze", LivingOoze::new, MobCategory.CREATURE, 0.98F, 0.98F);
    public static final DeferredHolder<EntityType<?>, EntityType<Metriorhynchus>> METRIORHYNCHUS = registerLivingEntity("metriorhynchus", Metriorhynchus::new, MobCategory.CREATURE, 1.3F, 1.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<Tartuosteus>> TARTUOSTEUS = registerLivingEntity("tartuosteus", Tartuosteus::new, MobCategory.WATER_CREATURE, 1.2F, 0.5F);

    // Update 4
    public static final DeferredHolder<EntityType<?>, EntityType<Brachiosaurus>> BRACHIOSAURUS = registerLivingEntity("brachiosaurus", Brachiosaurus::new, MobCategory.CREATURE, 4.1F, 9.1F);
    public static final DeferredHolder<EntityType<?>, EntityType<Coelacanthus>> COELACANTHUS = registerLivingEntity("coelacanthus", Coelacanthus::new, MobCategory.WATER_CREATURE, 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<Hibbertopterus>> HIBBERTOPTERUS = registerLivingEntity("hibbertopterus", Hibbertopterus::new, MobCategory.CREATURE, 2.6F, 1.7F);
    public static final DeferredHolder<EntityType<?>, EntityType<Kaprosuchus>> KAPROSUCHUS = registerLivingEntity("kaprosuchus", Kaprosuchus::new, MobCategory.CREATURE, 0.9F, 1.3F);
    public static final DeferredHolder<EntityType<?>, EntityType<Leptictidium>> LEPTICTIDIUM = registerLivingEntity("leptictidium", Leptictidium::new, MobCategory.CREATURE, 0.4F, 0.7F);
    public static final DeferredHolder<EntityType<?>, EntityType<LobeFinnedFish>> LOBE_FINNED_FISH = registerLivingEntity("lobe_finned_fish", LobeFinnedFish::new, MobCategory.WATER_AMBIENT, 0.5F, 0.8F);
    public static final DeferredHolder<EntityType<?>, EntityType<Lystrosaurus>> LYSTROSAURUS = registerLivingEntity("lystrosaurus", Lystrosaurus::new, MobCategory.CREATURE, 0.9F, 0.9F);
    public static final DeferredHolder<EntityType<?>, EntityType<Pachycephalosaurus>> PACHYCEPHALOSAURUS = registerLivingEntity("pachycephalosaurus", Pachycephalosaurus::new, MobCategory.CREATURE, 0.8F, 1.3F);
    public static final DeferredHolder<EntityType<?>, EntityType<Praepusa>> PRAEPUSA = registerLivingEntity("praepusa", Praepusa::new, MobCategory.CREATURE, 0.6F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<Pterodactylus>> PTERODACTYLUS = registerLivingEntity("pterodactylus", Pterodactylus::new, MobCategory.CREATURE, 0.6F, 0.6F);
    public static final DeferredHolder<EntityType<?>, EntityType<Ulughbegsaurus>> ULUGHBEGSAURUS = registerLivingEntity("ulughbegsaurus", Ulughbegsaurus::new, MobCategory.CREATURE, 1.3F, 2.4F);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> PTERODACTYLUS_EGG = registerEntity("pterodactylus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PTERODACTYLUS_EGG, UP2Entities.PTERODACTYLUS::get), MobCategory.MISC, 0.25F, 0.25F);

    // Update 5
    public static final DeferredHolder<EntityType<?>, EntityType<Aegirocassis>> AEGIROCASSIS = registerLivingEntity("aegirocassis", Aegirocassis::new, MobCategory.WATER_CREATURE, 3.5F, 3.9F);
    public static final DeferredHolder<EntityType<?>, EntityType<Desmatosuchus>> DESMATOSUCHUS = registerLivingEntity("desmatosuchus", Desmatosuchus::new, MobCategory.CREATURE, 1.3F, 1.2F);
    public static final DeferredHolder<EntityType<?>, EntityType<Delitzschala>> DELITZSCHALA = registerLivingEntity("delitzschala", Delitzschala::new, MobCategory.AMBIENT, 0.4F, 0.1F);
    public static final DeferredHolder<EntityType<?>, EntityType<Mosasaurus>> MOSASAURUS = registerLivingEntity("mosasaurus", Mosasaurus::new, MobCategory.WATER_CREATURE, 2.9F, 2.7F);
    public static final DeferredHolder<EntityType<?>, EntityType<Psilopterus>> PSILOPTERUS = registerLivingEntity("psilopterus", Psilopterus::new, MobCategory.CREATURE, 0.6F, 1.4F);
    public static final DeferredHolder<EntityType<?>, EntityType<Zhangsolva>> ZHANGSOLVA = registerLivingEntity("zhangsolva", Zhangsolva::new, MobCategory.AMBIENT, 0.3F, 0.4F);

    public static final DeferredHolder<EntityType<?>, EntityType<Grug>> GRUG = registerLivingEntity("grug", Grug::new, MobCategory.CREATURE, 1.5F, 3.1F);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> PSILOPTERUS_EGG = registerEntity("psilopterus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PSILOPTERUS_EGG, UP2Entities.PSILOPTERUS::get), MobCategory.MISC, 0.25F, 0.25F);

    private static <E extends LivingEntity> DeferredHolder<EntityType<?>, EntityType<E>> registerLivingEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        DeferredHolder<EntityType<?>, EntityType<E>> entity = ENTITY_TYPE.register(name, () -> registerLivingEntity(factory, entityClassification, name, width, height));
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        DeferredHolder<EntityType<?>, EntityType<E>> entity = ENTITY_TYPE.register(name, () -> registerEntity(factory, entityClassification, name, width, height));
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    private static <E extends LivingEntity> DeferredHolder<EntityType<?>, EntityType<E>> registerLivingEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        return ENTITY_TYPE.register(name, () -> registerLivingEntity(factory, entityClassification, name, width, height));
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        return ENTITY_TYPE.register(name, () -> registerEntity(factory, entityClassification, name, width, height));
    }

    private static <E extends LivingEntity> EntityType<E> registerLivingEntity(EntityType.EntityFactory<E> factory, MobCategory entityClassification, String name, float width, float height) {
        ResourceLocation location = UnusualPrehistory2.modPrefix(name);
        return EntityType.Builder.of(factory, entityClassification)
                .sized(width, height)
                .setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(location.toString());
    }

    private static <E extends Entity> EntityType<E> registerEntity(EntityType.EntityFactory<E> factory, MobCategory entityClassification, String name, float width, float height) {
        ResourceLocation location = UnusualPrehistory2.modPrefix(name);
        return EntityType.Builder.of(factory, entityClassification)
                .sized(width, height)
                .setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(location.toString());
    }
}
