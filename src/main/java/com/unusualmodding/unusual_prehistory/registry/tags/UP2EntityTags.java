package com.unusualmodding.unusual_prehistory.registry.tags;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class UP2EntityTags {

    // UP2 tags
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_AVOIDS = modEntityTag("dromaeosaurus_avoids");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_TARGETS = modEntityTag("dromaeosaurus_targets");

    public static final TagKey<EntityType<?>> BIG_DUNKLEOSTEUS_TARGETS = modEntityTag("big_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNKLEOSTEUS_TARGETS = modEntityTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNKLEOSTEUS_TARGETS = modEntityTag("small_dunkleosteus_targets");

    public static final TagKey<EntityType<?>> JAWLESS_FISH_AVOIDS = modEntityTag("jawless_fish_avoids");

    public static final TagKey<EntityType<?>> MAJUNGASAURUS_TARGETS = modEntityTag("majungasaurus_targets");

    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = modEntityTag("megalania_targets");

    public static final TagKey<EntityType<?>> SCAUMENACIA_AVOIDS = modEntityTag("scaumenacia_avoids");

    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_nymph_avoids");

    public static final TagKey<EntityType<?>> STETHACANTHUS_TARGETS = modEntityTag("stethacanthus_targets");
    public static final TagKey<EntityType<?>> STETHACANTHUS_AVOIDS = modEntityTag("stethacanthus_avoids");

    public static final TagKey<EntityType<?>> TALPANAS_AVOIDS = modEntityTag("talpanas_avoids");

    public static final TagKey<EntityType<?>> SCATTERS_TELECREX = modEntityTag("scatters_telecrex");

    public static final TagKey<EntityType<?>> SCATTERS_KIMMERIDGEBRACHYPTERAESCHNIDIUM = modEntityTag("scatters_kimmeridgebrachypteraeschnidium");

    public static final TagKey<EntityType<?>> SPAWNS_MULTIPLE_BABIES = modEntityTag("spawns_multiple_babies");

    public static final TagKey<EntityType<?>> TAR_WALKABLE_MOBS = modEntityTag("tar_walkable_mobs");

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
