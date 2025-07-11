package com.unusualmodding.unusual_prehistory.registry.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class UP2EntityTags {

    // UP2 tags
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_TARGETS = modEntityTag("dromaeosaurus_targets");

    public static final TagKey<EntityType<?>> BIG_DUNKLEOSTEUS_TARGETS = modEntityTag("big_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNKLEOSTEUS_TARGETS = modEntityTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNKLEOSTEUS_TARGETS = modEntityTag("small_dunkleosteus_targets");

    public static final TagKey<EntityType<?>> JAWLESS_FISH_AVOIDS = modEntityTag("jawless_fish_avoids");

    public static final TagKey<EntityType<?>> SCAUMENACIA_AVOIDS = modEntityTag("scaumenacia_avoids");

    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_nymph_avoids");

    public static final TagKey<EntityType<?>> STETHACANTHUS_TARGETS = modEntityTag("stethacanthus_targets");
    public static final TagKey<EntityType<?>> STETHACANTHUS_AVOIDS = modEntityTag("stethacanthus_avoids");

    public static final TagKey<EntityType<?>> SCATTERS_TELECREX= modEntityTag("scatters_telecrex");

    private static TagKey<EntityType<?>> modEntityTag(String name) {
        return entityTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<EntityType<?>> forgeEntityTag(String name) {
        return entityTag("forge", name);
    }

    public static TagKey<EntityType<?>> entityTag(String modid, String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(modid, name));
    }
}
