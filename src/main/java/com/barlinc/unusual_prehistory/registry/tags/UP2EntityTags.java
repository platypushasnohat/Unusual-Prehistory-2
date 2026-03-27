package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class UP2EntityTags {

    // Update 1
    public static final TagKey<EntityType<?>> CARNOTAURUS_TARGETS = modEntityTag("carnotaurus_targets");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_TARGETS = modEntityTag("dromaeosaurus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNKLEOSTEUS_TARGETS = modEntityTag("small_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNKLEOSTEUS_TARGETS = modEntityTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> BIG_DUNKLEOSTEUS_TARGETS = modEntityTag("big_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MAJUNGASAURUS_TARGETS = modEntityTag("majungasaurus_targets");
    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = modEntityTag("megalania_targets");
    public static final TagKey<EntityType<?>> STETHACANTHUS_TARGETS = modEntityTag("stethacanthus_targets");

    public static final TagKey<EntityType<?>> DIPLOCAULUS_AVOIDS = modEntityTag("diplocaulus_avoids");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_AVOIDS = modEntityTag("dromaeosaurus_avoids");
    public static final TagKey<EntityType<?>> JAWLESS_FISH_AVOIDS = modEntityTag("jawless_fish_avoids");
    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_avoids");
    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_nymph_avoids");
    public static final TagKey<EntityType<?>> MAJUNGASAURUS_AVOIDS = modEntityTag("majungasaurus_avoids");
    public static final TagKey<EntityType<?>> STETHACANTHUS_AVOIDS = modEntityTag("stethacanthus_avoids");
    public static final TagKey<EntityType<?>> TALPANAS_AVOIDS = modEntityTag("talpanas_avoids");
    public static final TagKey<EntityType<?>> TELECREX_AVOIDS = modEntityTag("telecrex_avoids");

    public static final TagKey<EntityType<?>> CARNOTAURUS_IGNORES = modEntityTag("carnotaurus_ignores");

    public static final TagKey<EntityType<?>> SWEET_BERRY_BUSH_IMMUNE = modEntityTag("sweet_berry_bush_immune");

    // Update 3
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_TARGETS = modEntityTag("metriorhynchus_targets");

    public static final TagKey<EntityType<?>> TARTUOSTEUS_AVOIDS = modEntityTag("tartuosteus_avoids");

    public static final TagKey<EntityType<?>> METRIORHYNCHUS_CANT_GRAB = modEntityTag("metriorhynchus_cant_grab");
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_CAN_GRAB = modEntityTag("metriorhynchus_can_grab");

    // Update 4
    public static final TagKey<EntityType<?>> KAPROSUCHUS_TARGETS = modEntityTag("kaprosuchus_targets");
    public static final TagKey<EntityType<?>> PACHYCEPHALOSAURUS_TARGETS = modEntityTag("pachycephalosaurus_targets");
    public static final TagKey<EntityType<?>> PACHYCEPHALOSAURUS_TARGETS_TO_KILL = modEntityTag("pachycephalosaurus_targets_to_kill");
    public static final TagKey<EntityType<?>> PRAEPUSA_TARGETS = modEntityTag("praepusa_targets");
    public static final TagKey<EntityType<?>> ULUGHBEGSAURUS_TARGETS = modEntityTag("ulughbegsaurus_targets");

    public static final TagKey<EntityType<?>> COELACANTHUS_AVOIDS = modEntityTag("coelacanthus_avoids");
    public static final TagKey<EntityType<?>> LEPTICTIDIUM_AVOIDS = modEntityTag("leptictidium_avoids");
    public static final TagKey<EntityType<?>> PRAEPUSA_AVOIDS = modEntityTag("praepusa_avoids");

    public static final TagKey<EntityType<?>> LEPTICTIDIUM_DOESNT_TARGET = modEntityTag("leptictidium_doesnt_target");

    public static final TagKey<EntityType<?>> COELACANTHUS_NEVER_EATS = modEntityTag("coelacanthus_never_eats");
    public static final TagKey<EntityType<?>> COELACANTHUS_ALWAYS_EATS = modEntityTag("coelacanthus_always_eats");

    // Update 5
    public static final TagKey<EntityType<?>> PSILOPTERUS_KICK_TARGETS = modEntityTag("psilopterus_kick_targets");
    public static final TagKey<EntityType<?>> MEDIUM_PSILOPTERUS_PACK_TARGETS = modEntityTag("medium_psilopterus_pack_targets");
    public static final TagKey<EntityType<?>> LARGE_PSILOPTERUS_PACK_TARGETS = modEntityTag("large_psilopterus_pack_targets");

    // Future
    public static final TagKey<EntityType<?>> DIMORPHODON_AVOIDS = modEntityTag("dimorphodon_avoids");

    public static final TagKey<EntityType<?>> DIMORPHODON_CANT_GRAB = modEntityTag("dimorphodon_cant_grab");
    public static final TagKey<EntityType<?>> DIMORPHODON_CAN_GRAB = modEntityTag("dimorphodon_can_grab");

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
