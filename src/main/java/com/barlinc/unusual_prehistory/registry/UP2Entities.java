package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Tartuosteus;
import com.barlinc.unusual_prehistory.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Aegirocassis;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Desmatosuchus;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Grug;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Psilopterus;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Zhangsolva;
import com.barlinc.unusual_prehistory.entity.mob.update_6.*;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Ampyx;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Setapedites;
import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UP2Entities {

    public static List<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> ENTITY_TRANSLATIONS = new ArrayList<>();

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    // Update 1
    public static final DeferredHolder<EntityType<?>, EntityType<Carnotaurus>> CARNOTAURUS = registerEntity("carnotaurus", Carnotaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.75F, 2.9F).eyeHeight(2.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Diplocaulus>> DIPLOCAULUS = registerEntity("diplocaulus", Diplocaulus::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.4F).eyeHeight(0.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Dromaeosaurus>> DROMAEOSAURUS = registerEntity("dromaeosaurus", Dromaeosaurus::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 1.2F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Dunkleosteus>> DUNKLEOSTEUS = registerEntity("dunkleosteus", Dunkleosteus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.75F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<JawlessFish>> JAWLESS_FISH = registerEntity("jawless_fish", JawlessFish::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.35F, 0.35F).eyeHeight(0.175F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Kentrosaurus>> KENTROSAURUS = registerEntity("kentrosaurus", Kentrosaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.75F, 2.3F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Kimmeridgebrachypteraeschnidium>> KIMMERIDGEBRACHYPTERAESCHNIDIUM = registerEntity("kimmeridgebrachypteraeschnidium", Kimmeridgebrachypteraeschnidium::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Majungasaurus>> MAJUNGASAURUS = registerEntity("majungasaurus", Majungasaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.2F, 1.9F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Megalania>> MEGALANIA = registerEntity("megalania", Megalania::new, MobCategory.CREATURE, builder -> builder.sized(1.7F, 1.5F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Stethacanthus>> STETHACANTHUS = registerEntity("stethacanthus", Stethacanthus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.8F, 0.8F).eyeHeight(0.5F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Talpanas>> TALPANAS = registerEntity("talpanas", Talpanas::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 0.9F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Telecrex>> TELECREX = registerEntity("telecrex", Telecrex::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 0.9F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Unicorn>> UNICORN = registerEntity("unicorn", Unicorn::new, MobCategory.CREATURE, builder -> builder.sized(1.2F, 2.9F).eyeHeight(0.8F).clientTrackingRange(10));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> DROMAEOSAURUS_EGG = registerEntity("dromaeosaurus_egg", (entityType, level) -> new ThrowableEgg(entityType, level, UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> TALPANAS_EGG = registerEntity("talpanas_egg", (entityType, level) -> new ThrowableEgg(entityType, level, UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> TELECREX_EGG = registerEntity("telecrex_egg", (entityType, level) -> new ThrowableEgg(entityType, level, UP2Items.TELECREX_EGG, UP2Entities.TELECREX::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));

    // Update 2
    public static final DeferredHolder<EntityType<?>, EntityType<Onchopristis>> ONCHOPRISTIS = registerEntity("onchopristis", Onchopristis::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.2F, 0.5F).eyeHeight(0.5F).clientTrackingRange(10));

    // Update 3
    public static final DeferredHolder<EntityType<?>, EntityType<LivingOoze>> LIVING_OOZE = registerEntity("living_ooze", LivingOoze::new, MobCategory.CREATURE, builder -> builder.sized(0.98F, 0.98F).eyeHeight(0.75F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Metriorhynchus>> METRIORHYNCHUS = registerEntity("metriorhynchus", Metriorhynchus::new, MobCategory.CREATURE, builder -> builder.sized(1.3F, 1.2F).eyeHeight(0.7F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Tartuosteus>> TARTUOSTEUS = registerEntity("tartuosteus", Tartuosteus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.2F, 0.5F).eyeHeight(0.5F).clientTrackingRange(10));

    // Update 4
    public static final DeferredHolder<EntityType<?>, EntityType<Brachiosaurus>> BRACHIOSAURUS = registerEntity("brachiosaurus", Brachiosaurus::new, MobCategory.CREATURE, builder -> builder.sized(4.1F, 9.1F).eyeHeight(9.0F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Coelacanthus>> COELACANTHUS = registerEntity("coelacanthus", Coelacanthus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.75F, 0.6F).eyeHeight(0.3F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Hibbertopterus>> HIBBERTOPTERUS = registerEntity("hibbertopterus", Hibbertopterus::new, MobCategory.CREATURE, builder -> builder.sized(2.75F, 1.7F).eyeHeight(1.6F).passengerAttachments(new Vec3(0.0F, 1.7F, -0.5F)).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Kaprosuchus>> KAPROSUCHUS = registerEntity("kaprosuchus", Kaprosuchus::new, MobCategory.CREATURE, builder -> builder.sized(1.25F, 1.4F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Leptictidium>> LEPTICTIDIUM = registerEntity("leptictidium", Leptictidium::new, MobCategory.CREATURE, builder -> builder.sized(0.55F, 0.7F).eyeHeight(0.5F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<LobeFinnedFish>> LOBE_FINNED_FISH = registerEntity("lobe_finned_fish", LobeFinnedFish::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.75F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Lystrosaurus>> LYSTROSAURUS = registerEntity("lystrosaurus", Lystrosaurus::new, MobCategory.CREATURE, builder -> builder.sized(0.9F, 0.9F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Pachycephalosaurus>> PACHYCEPHALOSAURUS = registerEntity("pachycephalosaurus", Pachycephalosaurus::new, MobCategory.CREATURE, builder -> builder.sized(0.8F, 1.3F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Praepusa>> PRAEPUSA = registerEntity("praepusa", Praepusa::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.5F).eyeHeight(0.25F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Pterodactylus>> PTERODACTYLUS = registerEntity("pterodactylus", Pterodactylus::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.6F).eyeHeight(0.4F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Ulughbegsaurus>> ULUGHBEGSAURUS = registerEntity("ulughbegsaurus", Ulughbegsaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 2.4F).eyeHeight(2.3F).clientTrackingRange(10));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> PTERODACTYLUS_EGG = registerEntity("pterodactylus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PTERODACTYLUS_EGG, UP2Entities.PTERODACTYLUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));

    // Update 5
    public static final DeferredHolder<EntityType<?>, EntityType<Aegirocassis>> AEGIROCASSIS = registerEntity("aegirocassis", Aegirocassis::new, MobCategory.WATER_CREATURE, builder -> builder.sized(3.5F, 3.9F).eyeHeight(1.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Desmatosuchus>> DESMATOSUCHUS = registerEntity("desmatosuchus", Desmatosuchus::new, MobCategory.CREATURE, builder -> builder.sized(1.3F, 1.2F).eyeHeight(0.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Delitzschala>> DELITZSCHALA = registerEntity("delitzschala", Delitzschala::new, MobCategory.AMBIENT, builder -> builder.sized(0.75F, 0.2F).eyeHeight(0.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Psilopterus>> PSILOPTERUS = registerEntity("psilopterus", Psilopterus::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 1.4F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Zhangsolva>> ZHANGSOLVA = registerEntity("zhangsolva", Zhangsolva::new, MobCategory.AMBIENT, builder -> builder.sized(0.5F, 0.45F).eyeHeight(0.225F).clientTrackingRange(10));

    public static final DeferredHolder<EntityType<?>, EntityType<Grug>> GRUG = registerEntity("grug", Grug::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 3.1F).eyeHeight(0.8F).clientTrackingRange(10));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> PSILOPTERUS_EGG = registerEntity("psilopterus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PSILOPTERUS_EGG, UP2Entities.PSILOPTERUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));

    // Update 6
    public static final DeferredHolder<EntityType<?>, EntityType<Ampyx>> AMPYX = registerEntity("ampyx", Ampyx::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.4F, 0.2F).eyeHeight(0.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Antarctopelta>> ANTARCTOPELTA = registerEntity("antarctopelta", Antarctopelta::new, MobCategory.CREATURE, builder -> builder.sized(1.4F, 1.4F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Cotylorhynchus>> COTYLORHYNCHUS = registerEntity("cotylorhynchus", Cotylorhynchus::new, MobCategory.CREATURE, builder -> builder.sized(2.1F, 1.7F).eyeHeight(1.6F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Cryptoclidus>> CRYPTOCLIDUS = registerEntity("cryptoclidus", Cryptoclidus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.7F, 1.25F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Hynerpeton>> HYNERPETON = registerEntity("hynerpeton", Hynerpeton::new, MobCategory.CREATURE, builder -> builder.sized(0.8F, 0.5F).eyeHeight(0.3F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Ichthyosaurus>> ICHTHYOSAURUS = registerEntity("ichthyosaurus", Ichthyosaurus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.5F, 1.5F).eyeHeight(0.8F).clientTrackingRange(10).passengerAttachments(new Vec3(0.0F, 1.8F, -0.5F)));
    public static final DeferredHolder<EntityType<?>, EntityType<Lorrainosaurus>> LORRAINOSAURUS = registerEntity("lorrainosaurus", Lorrainosaurus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(2.6F, 2.2F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Mammoth>> MAMMOTH = registerEntity("mammoth", Mammoth::new, MobCategory.CREATURE, builder -> builder.sized(3.6F, 4.9F).eyeHeight(4.7F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Prognathodon>> PROGNATHODON = registerEntity("prognathodon", Prognathodon::new, MobCategory.WATER_CREATURE, builder -> builder.sized(2.8F, 2.3F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Setapedites>> SETAPEDITES = registerEntity("setapedites", Setapedites::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.4F, 0.2F).eyeHeight(0.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<SpikeToothedSalmon>> SPIKE_TOOTHED_SALMON = registerEntityNoLang("spike_toothed_salmon", SpikeToothedSalmon::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.3F, 1.3F).eyeHeight(0.65F).clientTrackingRange(10));

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, Consumer<EntityType.Builder<E>> builderConsumer) {
        DeferredHolder<EntityType<?>, EntityType<E>> entity = registerEntityNoLang(name, factory, entityClassification, builderConsumer);
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, Consumer<EntityType.Builder<E>> builderConsumer) {
        return ENTITY_TYPE.register(name, () -> {
            var builder = EntityType.Builder.of(factory, entityClassification);
            builderConsumer.accept(builder);
            return builder.build(UnusualPrehistory2.MOD_ID + ":" + name);
        });
    }
}
