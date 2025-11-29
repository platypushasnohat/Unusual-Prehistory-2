package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class UP2EntityTags {

    // UP2 tags
    public static final TagKey<EntityType<?>> SWEET_BERRY_BUSH_IMMUNE = modEntityTag("sweet_berry_bush_immune");

    public static final TagKey<EntityType<?>> CARNOTAURUS_TARGETS = modEntityTag("carnotaurus_targets");
    public static final TagKey<EntityType<?>> CARNOTAURUS_IGNORES = modEntityTag("carnotaurus_ignores");

    public static final TagKey<EntityType<?>> DROMAEOSAURUS_AVOIDS = modEntityTag("dromaeosaurus_avoids");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_TARGETS = modEntityTag("dromaeosaurus_targets");

    public static final TagKey<EntityType<?>> BIG_DUNKLEOSTEUS_TARGETS = modEntityTag("big_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNKLEOSTEUS_TARGETS = modEntityTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNKLEOSTEUS_TARGETS = modEntityTag("small_dunkleosteus_targets");

    public static final TagKey<EntityType<?>> JAWLESS_FISH_AVOIDS = modEntityTag("jawless_fish_avoids");

    public static final TagKey<EntityType<?>> MAJUNGASAURUS_TARGETS = modEntityTag("majungasaurus_targets");

    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = modEntityTag("megalania_targets");

    public static final TagKey<EntityType<?>> METRIORHYNCHUS_TARGETS = modEntityTag("metriorhynchus_targets");

    public static final TagKey<EntityType<?>> ONCHOPRISTIS_TARGETS = modEntityTag("onchopristis_targets");

    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_nymph_avoids");

    public static final TagKey<EntityType<?>> STETHACANTHUS_TARGETS = modEntityTag("stethacanthus_targets");
    public static final TagKey<EntityType<?>> STETHACANTHUS_AVOIDS = modEntityTag("stethacanthus_avoids");

    public static final TagKey<EntityType<?>> TALPANAS_AVOIDS = modEntityTag("talpanas_avoids");

    public static final TagKey<EntityType<?>> SCATTERS_TELECREX = modEntityTag("scatters_telecrex");

    public static final TagKey<EntityType<?>> SCATTERS_KIMMERIDGEBRACHYPTERAESCHNIDIUM = modEntityTag("scatters_kimmeridgebrachypteraeschnidium");

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
