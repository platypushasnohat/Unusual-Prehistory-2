package com.barlinc.unusual_prehistory.registry.tags;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class UP2EntityTags {

    public static final TagKey<EntityType<?>> NO_LEAF_COLLISIONS = modEntityTag("no_leaf_collisions");

    // Entity groups
    public static final TagKey<EntityType<?>> TINY_ANIMALS = modEntityTag("tiny_animals");
    public static final TagKey<EntityType<?>> SMALL_ANIMALS = modEntityTag("small_animals");
    public static final TagKey<EntityType<?>> MEDIUM_ANIMALS = modEntityTag("medium_animals");
    public static final TagKey<EntityType<?>> LARGE_ANIMALS = modEntityTag("large_animals");
    public static final TagKey<EntityType<?>> GIANT_ANIMALS = modEntityTag("giant_animals");

    public static final TagKey<EntityType<?>> TINY_PREY = modEntityTag("tiny_prey");
    public static final TagKey<EntityType<?>> SMALL_PREY = modEntityTag("small_prey");
    public static final TagKey<EntityType<?>> MEDIUM_PREY = modEntityTag("medium_prey");
    public static final TagKey<EntityType<?>> LARGE_PREY = modEntityTag("large_prey");
    public static final TagKey<EntityType<?>> GIANT_PREY = modEntityTag("giant_prey");

    public static final TagKey<EntityType<?>> TINY_AQUATIC_ANIMALS = modEntityTag("tiny_aquatic_animals");
    public static final TagKey<EntityType<?>> SMALL_AQUATIC_ANIMALS = modEntityTag("small_aquatic_animals");
    public static final TagKey<EntityType<?>> MEDIUM_AQUATIC_ANIMALS = modEntityTag("medium_aquatic_animals");
    public static final TagKey<EntityType<?>> LARGE_AQUATIC_ANIMALS = modEntityTag("large_aquatic_animals");
    public static final TagKey<EntityType<?>> GIANT_AQUATIC_ANIMALS = modEntityTag("giant_aquatic_animals");

    public static final TagKey<EntityType<?>> TINY_AQUATIC_PREY = modEntityTag("tiny_aquatic_prey");
    public static final TagKey<EntityType<?>> SMALL_AQUATIC_PREY = modEntityTag("small_aquatic_prey");
    public static final TagKey<EntityType<?>> MEDIUM_AQUATIC_PREY = modEntityTag("medium_aquatic_prey");
    public static final TagKey<EntityType<?>> LARGE_AQUATIC_PREY = modEntityTag("large_aquatic_prey");

    public static final TagKey<EntityType<?>> MONSTERS = modEntityTag("monsters");
    public static final TagKey<EntityType<?>> STRONG_MONSTERS = modEntityTag("strong_monsters");

    public static final TagKey<EntityType<?>> AQUATIC_MONSTERS = modEntityTag("aquatic_monsters");
    public static final TagKey<EntityType<?>> STRONG_AQUATIC_MONSTERS = modEntityTag("strong_aquatic_monsters");

    // Targets
    public static final TagKey<EntityType<?>> CARNOTAURUS_TARGETS = modEntityTag("carnotaurus_targets");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_TARGETS = modEntityTag("dromaeosaurus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNKLEOSTEUS_TARGETS = modEntityTag("small_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNKLEOSTEUS_TARGETS = modEntityTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> BIG_DUNKLEOSTEUS_TARGETS = modEntityTag("big_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> KAPROSUCHUS_TARGETS = modEntityTag("kaprosuchus_targets");
    public static final TagKey<EntityType<?>> MAJUNGASAURUS_TARGETS = modEntityTag("majungasaurus_targets");
    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = modEntityTag("megalania_targets");
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_TARGETS = modEntityTag("metriorhynchus_targets");
    public static final TagKey<EntityType<?>> ONCHOPRISTIS_TARGETS = modEntityTag("onchopristis_targets");
    public static final TagKey<EntityType<?>> PACHYCEPHALOSAURUS_TARGETS = modEntityTag("pachycephalosaurus_targets");
    public static final TagKey<EntityType<?>> PACHYCEPHALOSAURUS_TARGETS_TO_KILL = modEntityTag("pachycephalosaurus_targets_to_kill");
    public static final TagKey<EntityType<?>> PRAEPUSA_TARGETS = modEntityTag("praepusa_targets");
    public static final TagKey<EntityType<?>> SMALL_PSILOPTERUS_PACK_TARGETS = modEntityTag("small_psilopterus_pack_targets");
    public static final TagKey<EntityType<?>> MEDIUM_PSILOPTERUS_PACK_TARGETS = modEntityTag("medium_psilopterus_pack_targets");
    public static final TagKey<EntityType<?>> LARGE_PSILOPTERUS_PACK_TARGETS = modEntityTag("large_psilopterus_pack_targets");
    public static final TagKey<EntityType<?>> STETHACANTHUS_TARGETS = modEntityTag("stethacanthus_targets");
    public static final TagKey<EntityType<?>> ULUGHBEGSAURUS_TARGETS = modEntityTag("ulughbegsaurus_targets");

    public static final TagKey<EntityType<?>> LEPTICTIDIUM_DOESNT_TARGET = modEntityTag("leptictidium_doesnt_target");

    // Avoids
    public static final TagKey<EntityType<?>> COELACANTHUS_AVOIDS = modEntityTag("coelacanthus_avoids");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_AVOIDS = modEntityTag("dromaeosaurus_avoids");
    public static final TagKey<EntityType<?>> DIMORPHODON_AVOIDS = modEntityTag("dimorphodon_avoids");
    public static final TagKey<EntityType<?>> DIPLOCAULUS_AVOIDS = modEntityTag("diplocaulus_avoids");
    public static final TagKey<EntityType<?>> JAWLESS_FISH_AVOIDS = modEntityTag("jawless_fish_avoids");
    public static final TagKey<EntityType<?>> LEPTICTIDIUM_AVOIDS = modEntityTag("leptictidium_avoids");
    public static final TagKey<EntityType<?>> MAJUNGASAURUS_AVOIDS = modEntityTag("majungasaurus_avoids");
    public static final TagKey<EntityType<?>> PRAEPUSA_AVOIDS = modEntityTag("praepusa_avoids");
    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_avoids");
    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_nymph_avoids");
    public static final TagKey<EntityType<?>> STETHACANTHUS_AVOIDS = modEntityTag("stethacanthus_avoids");
    public static final TagKey<EntityType<?>> TALPANAS_AVOIDS = modEntityTag("talpanas_avoids");
    public static final TagKey<EntityType<?>> TARTUOSTEUS_AVOIDS = modEntityTag("tartuosteus_avoids");
    public static final TagKey<EntityType<?>> TELECREX_AVOIDS = modEntityTag("telecrex_avoids");

    // Misc
    public static final TagKey<EntityType<?>> SWEET_BERRY_BUSH_IMMUNE = modEntityTag("sweet_berry_bush_immune");

    public static final TagKey<EntityType<?>> CARNOTAURUS_IGNORES = modEntityTag("carnotaurus_ignores");

    public static final TagKey<EntityType<?>> COELACANTHUS_NEVER_EATS = modEntityTag("coelacanthus_never_eats");
    public static final TagKey<EntityType<?>> COELACANTHUS_ALWAYS_EATS = modEntityTag("coelacanthus_always_eats");

    public static final TagKey<EntityType<?>> DIMORPHODON_CANT_GRAB = modEntityTag("dimorphodon_cant_grab");
    public static final TagKey<EntityType<?>> DIMORPHODON_CAN_GRAB = modEntityTag("dimorphodon_can_grab");
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_CANT_GRAB = modEntityTag("metriorhynchus_cant_grab");
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_CAN_GRAB = modEntityTag("metriorhynchus_can_grab");

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
