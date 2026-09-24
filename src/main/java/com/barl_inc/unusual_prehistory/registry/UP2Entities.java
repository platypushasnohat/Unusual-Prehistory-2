package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Ammonite;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Leedsichthys;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UP2Entities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    public static List<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> ENTITY_TRANSLATIONS = new ArrayList<>();

    public static final DeferredHolder<EntityType<?>, EntityType<Ammonite>> AMMONITE = registerEntity("ammonite", Ammonite::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.9F, 0.9F).eyeHeight(0.45F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Leedsichthys>> LEEDSICHTHYS = registerEntity("leedsichthys", Leedsichthys::new, MobCategory.WATER_CREATURE, builder -> builder.sized(4.5F, 4.25F).eyeHeight(1.9F).clientTrackingRange(10));

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, Consumer<EntityType.Builder<E>> builderConsumer) {
        DeferredHolder<EntityType<?>, EntityType<E>> entity = registerEntityNoLang(name, factory, entityClassification, builderConsumer);
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, Consumer<EntityType.Builder<E>> builderConsumer) {
        return ENTITY_TYPES.register(name, () -> {
            var builder = EntityType.Builder.of(factory, entityClassification);
            builderConsumer.accept(builder);
            return builder.build(UnusualPrehistory2.MOD_ID + ":" + name);
        });
    }
}
