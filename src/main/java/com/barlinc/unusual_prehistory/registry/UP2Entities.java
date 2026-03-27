package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.misc.UP2Boat;
import com.barlinc.unusual_prehistory.entity.misc.UP2ChestBoat;
import com.barlinc.unusual_prehistory.entity.mob.future.*;
import com.barlinc.unusual_prehistory.entity.mob.future.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.future.ambient.Zhangsolva;
import com.barlinc.unusual_prehistory.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Tartuosteus;
import com.barlinc.unusual_prehistory.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Aegirocassis;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Desmatosuchus;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Mosasaurus;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Psilopterus;
import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UP2Entities {

    public static List<RegistryObject<? extends EntityType<?>>> ENTITY_TRANSLATIONS = new ArrayList<>();

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory2.MOD_ID);

    // Update 1
    public static final RegistryObject<EntityType<Carnotaurus>> CARNOTAURUS = registerLivingEntity("carnotaurus", Carnotaurus::new, MobCategory.CREATURE, 1.6F, 2.9F);
    public static final RegistryObject<EntityType<Diplocaulus>> DIPLOCAULUS = registerLivingEntity("diplocaulus", Diplocaulus::new, MobCategory.CREATURE, 0.6F, 0.4F);
    public static final RegistryObject<EntityType<Dromaeosaurus>> DROMAEOSAURUS = registerLivingEntity("dromaeosaurus", Dromaeosaurus::new, MobCategory.CREATURE, 0.7F, 1.2F);
    public static final RegistryObject<EntityType<Dunkleosteus>> DUNKLEOSTEUS = registerLivingEntity("dunkleosteus", Dunkleosteus::new, MobCategory.WATER_CREATURE, 0.75F, 0.6F);
    public static final RegistryObject<EntityType<JawlessFish>> JAWLESS_FISH = registerLivingEntity("jawless_fish", JawlessFish::new, MobCategory.WATER_AMBIENT, 0.36F, 0.36F);
    public static final RegistryObject<EntityType<Kentrosaurus>> KENTROSAURUS = registerLivingEntity("kentrosaurus", Kentrosaurus::new, MobCategory.CREATURE, 1.6F, 2.2F);
    public static final RegistryObject<EntityType<Kimmeridgebrachypteraeschnidium>> KIMMERIDGEBRACHYPTERAESCHNIDIUM = registerLivingEntity("kimmeridgebrachypteraeschnidium", Kimmeridgebrachypteraeschnidium::new, MobCategory.CREATURE, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<KimmeridgebrachypteraeschnidiumNymph>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH = registerLivingEntity("kimmeridgebrachypteraeschnidium_nymph", KimmeridgebrachypteraeschnidiumNymph::new, MobCategory.CREATURE, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<Majungasaurus>> MAJUNGASAURUS = registerLivingEntity("majungasaurus", Majungasaurus::new, MobCategory.CREATURE, 1.2F, 1.9F);
    public static final RegistryObject<EntityType<Megalania>> MEGALANIA = registerLivingEntity("megalania", Megalania::new, MobCategory.CREATURE, 1.7F, 1.5F);
    public static final RegistryObject<EntityType<Stethacanthus>> STETHACANTHUS = registerLivingEntity("stethacanthus", Stethacanthus::new, MobCategory.WATER_CREATURE, 0.7F, 0.7F);
    public static final RegistryObject<EntityType<Talpanas>> TALPANAS = registerLivingEntity("talpanas", Talpanas::new, MobCategory.CREATURE, 0.7F, 0.9F);
    public static final RegistryObject<EntityType<Telecrex>> TELECREX = registerLivingEntity("telecrex", Telecrex::new, MobCategory.CREATURE, 0.7F, 0.9F);
    public static final RegistryObject<EntityType<Unicorn>> UNICORN = registerLivingEntity("unicorn", Unicorn::new, MobCategory.CREATURE, 1.2F, 2.9F);

    public static final RegistryObject<EntityType<ThrowableEgg>> DROMAEOSAURUS_EGG = registerEntity("dromaeosaurus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS::get), MobCategory.MISC, 0.25F, 0.25F);
    public static final RegistryObject<EntityType<ThrowableEgg>> TALPANAS_EGG = registerEntity("talpanas_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS::get), MobCategory.MISC, 0.25F, 0.25F);
    public static final RegistryObject<EntityType<ThrowableEgg>> TELECREX_EGG = registerEntity("telecrex_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.TELECREX_EGG, UP2Entities.TELECREX::get), MobCategory.MISC, 0.25F, 0.25F);

    public static final RegistryObject<EntityType<UP2Boat>> BOAT = registerEntity("boat", UP2Boat::new, MobCategory.MISC, 1.375F, 0.5625F);
    public static final RegistryObject<EntityType<UP2ChestBoat>> CHEST_BOAT = registerEntityNoLang("chest_boat", UP2ChestBoat::new, MobCategory.MISC, 1.375F, 0.5625F);

    // Update 2
    public static final RegistryObject<EntityType<Onchopristis>> ONCHOPRISTIS = registerLivingEntity("onchopristis", Onchopristis::new, MobCategory.WATER_CREATURE, 1.2F, 0.5F);

    // Update 3
    public static final RegistryObject<EntityType<LivingOoze>> LIVING_OOZE = registerLivingEntity("living_ooze", LivingOoze::new, MobCategory.CREATURE, 0.98F, 0.98F);
    public static final RegistryObject<EntityType<Metriorhynchus>> METRIORHYNCHUS = registerLivingEntity("metriorhynchus", Metriorhynchus::new, MobCategory.CREATURE, 1.3F, 1.25F);
    public static final RegistryObject<EntityType<Tartuosteus>> TARTUOSTEUS = registerLivingEntity("tartuosteus", Tartuosteus::new, MobCategory.WATER_CREATURE, 1.2F, 0.5F);

    // Update 4
    public static final RegistryObject<EntityType<Brachiosaurus>> BRACHIOSAURUS = registerLivingEntity("brachiosaurus", Brachiosaurus::new, MobCategory.CREATURE, 4.1F, 9.1F);
    public static final RegistryObject<EntityType<Coelacanthus>> COELACANTHUS = registerLivingEntity("coelacanthus", Coelacanthus::new, MobCategory.WATER_CREATURE, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<Hibbertopterus>> HIBBERTOPTERUS = registerLivingEntity("hibbertopterus", Hibbertopterus::new, MobCategory.CREATURE, 2.6F, 1.7F);
    public static final RegistryObject<EntityType<Kaprosuchus>> KAPROSUCHUS = registerLivingEntity("kaprosuchus", Kaprosuchus::new, MobCategory.CREATURE, 0.9F, 1.3F);
    public static final RegistryObject<EntityType<Leptictidium>> LEPTICTIDIUM = registerLivingEntity("leptictidium", Leptictidium::new, MobCategory.CREATURE, 0.4F, 0.7F);
    public static final RegistryObject<EntityType<LobeFinnedFish>> LOBE_FINNED_FISH = registerLivingEntity("lobe_finned_fish", LobeFinnedFish::new, MobCategory.WATER_AMBIENT, 0.5F, 0.8F);
    public static final RegistryObject<EntityType<Lystrosaurus>> LYSTROSAURUS = registerLivingEntity("lystrosaurus", Lystrosaurus::new, MobCategory.CREATURE, 0.9F, 0.9F);
    public static final RegistryObject<EntityType<Pachycephalosaurus>> PACHYCEPHALOSAURUS = registerLivingEntity("pachycephalosaurus", Pachycephalosaurus::new, MobCategory.CREATURE, 0.8F, 1.3F);
    public static final RegistryObject<EntityType<Praepusa>> PRAEPUSA = registerLivingEntity("praepusa", Praepusa::new, MobCategory.CREATURE, 0.6F, 0.5F);
    public static final RegistryObject<EntityType<Pterodactylus>> PTERODACTYLUS = registerLivingEntity("pterodactylus", Pterodactylus::new, MobCategory.CREATURE, 0.6F, 0.6F);
    public static final RegistryObject<EntityType<Ulughbegsaurus>> ULUGHBEGSAURUS = registerLivingEntity("ulughbegsaurus", Ulughbegsaurus::new, MobCategory.CREATURE, 1.3F, 2.4F);

    public static final RegistryObject<EntityType<ThrowableEgg>> PTERODACTYLUS_EGG = registerEntity("pterodactylus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PTERODACTYLUS_EGG, UP2Entities.PTERODACTYLUS::get), MobCategory.MISC, 0.25F, 0.25F);

    // Update 5
    public static final RegistryObject<EntityType<Aegirocassis>> AEGIROCASSIS = registerLivingEntity("aegirocassis", Aegirocassis::new, MobCategory.WATER_CREATURE, 3.5F, 3.9F);
    public static final RegistryObject<EntityType<Desmatosuchus>> DESMATOSUCHUS = registerLivingEntity("desmatosuchus", Desmatosuchus::new, MobCategory.CREATURE, 1.3F, 1.2F);
    public static final RegistryObject<EntityType<Delitzschala>> DELITZSCHALA = registerLivingEntity("delitzschala", Delitzschala::new, MobCategory.AMBIENT, 0.4F, 0.1F);
    public static final RegistryObject<EntityType<Mosasaurus>> MOSASAURUS = registerLivingEntity("mosasaurus", Mosasaurus::new, MobCategory.WATER_CREATURE, 2.9F, 2.7F);
    public static final RegistryObject<EntityType<Psilopterus>> PSILOPTERUS = registerLivingEntity("psilopterus", Psilopterus::new, MobCategory.CREATURE, 0.6F, 1.4F);
    public static final RegistryObject<EntityType<Zhangsolva>> ZHANGSOLVA = registerLivingEntity("zhangsolva", Zhangsolva::new, MobCategory.AMBIENT, 0.3F, 0.4F);

    public static final RegistryObject<EntityType<ThrowableEgg>> PSILOPTERUS_EGG = registerEntity("psilopterus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PSILOPTERUS_EGG, UP2Entities.PSILOPTERUS::get), MobCategory.MISC, 0.25F, 0.25F);

    // Future
    public static final RegistryObject<EntityType<Barinasuchus>> BARINASUCHUS = registerLivingEntity("barinasuchus", Barinasuchus::new, MobCategory.CREATURE, 1.5F, 2.1F);
    public static final RegistryObject<EntityType<Cotylorhynchus>> COTYLORHYNCHUS = registerLivingEntity("cotylorhynchus", Cotylorhynchus::new, MobCategory.CREATURE, 2.2F, 1.6F);
    public static final RegistryObject<EntityType<Dimorphodon>> DIMORPHODON = registerLivingEntity("dimorphodon", Dimorphodon::new, MobCategory.CREATURE, 0.9F, 0.9F);
    public static final RegistryObject<EntityType<Eryon>> ERYON = registerLivingEntity("eryon", Eryon::new, MobCategory.CREATURE, 0.5F, 0.3F);
    public static final RegistryObject<EntityType<Mammoth>> MAMMOTH = registerLivingEntity("mammoth", Mammoth::new, MobCategory.CREATURE, 3.2F, 5.2F);
    public static final RegistryObject<EntityType<Manipulator>> MANIPULATOR = registerLivingEntity("manipulator", Manipulator::new, MobCategory.CREATURE, 1.5F, 1.9F);
    public static final RegistryObject<EntityType<Palaeophis>> PALAEOPHIS = registerLivingEntity("palaeophis", Palaeophis::new, MobCategory.WATER_CREATURE, 1.8F, 1.25F);
    public static final RegistryObject<EntityType<Therizinosaurus>> THERIZINOSAURUS = registerLivingEntity("therizinosaurus", Therizinosaurus::new, MobCategory.CREATURE, 2.2F, 5.4F);
    public static final RegistryObject<EntityType<Wonambi>> WONAMBI = registerLivingEntity("wonambi", Wonambi::new, MobCategory.CREATURE, 1.25F, 0.5F);

    public static final RegistryObject<EntityType<ThrowableEgg>> DIMORPHODON_EGG = registerEntity("dimorphodon_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.DIMORPHODON_EGG, UP2Entities.DIMORPHODON::get), MobCategory.MISC, 0.25F, 0.25F);

    private static <E extends LivingEntity> RegistryObject<EntityType<E>> registerLivingEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        RegistryObject<EntityType<E>> entity = ENTITY_TYPE.register(name, () -> registerLivingEntity(factory, entityClassification, name, width, height));
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> registerEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        RegistryObject<EntityType<E>> entity = ENTITY_TYPE.register(name, () -> registerEntity(factory, entityClassification, name, width, height));
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    private static <E extends LivingEntity> RegistryObject<EntityType<E>> registerLivingEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
        return ENTITY_TYPE.register(name, () -> registerLivingEntity(factory, entityClassification, name, width, height));
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> registerEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
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
