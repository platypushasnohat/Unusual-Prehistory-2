package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Entities.*;

public class UP2EntityTagProvider extends EntityTypeTagsProvider {

    public UP2EntityTagProvider(PackOutput packOutput, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, provider, UnusualPrehistory2.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {

        // Entity groups
        this.tag(UP2EntityTags.TINY_ANIMALS).add(
                PTERODACTYLUS.get(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), LEPTICTIDIUM.get(),
                EntityType.BAT, EntityType.PARROT
        );

        this.tag(UP2EntityTags.SMALL_ANIMALS).add(
                PRAEPUSA.get(), TALPANAS.get(), TELECREX.get(), DIPLOCAULUS.get(),
                EntityType.CHICKEN, EntityType.BEE, EntityType.FROG,
                EntityType.RABBIT, EntityType.AXOLOTL, EntityType.CAT
        );

        this.tag(UP2EntityTags.MEDIUM_ANIMALS).add(
                PACHYCEPHALOSAURUS.get(), DESMATOSUCHUS.get(), DROMAEOSAURUS.get(),
                KAPROSUCHUS.get(), LYSTROSAURUS.get(),
                EntityType.PIG,
                EntityType.COW, EntityType.GOAT, EntityType.SHEEP,
                EntityType.WOLF, EntityType.OCELOT, EntityType.DONKEY,
                EntityType.MULE, EntityType.LLAMA, EntityType.TURTLE
        );

        this.tag(UP2EntityTags.LARGE_ANIMALS).add(
                BARINASUCHUS.get(),
                CARNOTAURUS.get(), KENTROSAURUS.get(), MAJUNGASAURUS.get(),
                MEGALANIA.get(), METRIORHYNCHUS.get(), ULUGHBEGSAURUS.get(),
                EntityType.CAMEL, EntityType.HOGLIN, EntityType.HORSE,
                EntityType.PANDA, EntityType.POLAR_BEAR, EntityType.SNIFFER
        );

        this.tag(UP2EntityTags.GIANT_ANIMALS).add(
                BRACHIOSAURUS.get(),
                THERIZINOSAURUS.get()
        );

        this.tag(UP2EntityTags.TINY_AQUATIC_ANIMALS).add(
                JAWLESS_FISH.get()
        );

        this.tag(UP2EntityTags.SMALL_AQUATIC_ANIMALS).add(
                EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH, PRAEPUSA.get(),
                STETHACANTHUS.get(), EntityType.FROG, EntityType.AXOLOTL
        );

        this.tag(UP2EntityTags.MEDIUM_AQUATIC_ANIMALS).add(
                EntityType.DOLPHIN, EntityType.GLOW_SQUID, EntityType.SQUID, LOBE_FINNED_FISH.get(),
                TARTUOSTEUS.get(), KAPROSUCHUS.get()
        );

        this.tag(UP2EntityTags.LARGE_AQUATIC_ANIMALS).add(
                ONCHOPRISTIS.get(), DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(),
                MEGALANIA.get()
        );

        this.tag(UP2EntityTags.GIANT_AQUATIC_ANIMALS).add(
                BRACHIOSAURUS.get(), COELACANTHUS.get()
        );

        this.tag(UP2EntityTags.TINY_AQUATIC_PREY).add(
                JAWLESS_FISH.get()
        );

        this.tag(UP2EntityTags.SMALL_AQUATIC_PREY).add(
                EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH, PRAEPUSA.get(),
                STETHACANTHUS.get(), EntityType.FROG, EntityType.AXOLOTL
        );

        this.tag(UP2EntityTags.MEDIUM_AQUATIC_PREY).add(
                EntityType.DOLPHIN, EntityType.GLOW_SQUID, EntityType.SQUID, LOBE_FINNED_FISH.get(),
                TARTUOSTEUS.get()
        );

        this.tag(UP2EntityTags.LARGE_AQUATIC_PREY).add(
                ONCHOPRISTIS.get()
        );

        this.tag(UP2EntityTags.TINY_PREY).add(
                PTERODACTYLUS.get(), KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(),
                EntityType.BAT, EntityType.PARROT
        );

        this.tag(UP2EntityTags.SMALL_PREY).add(
                PRAEPUSA.get(), TALPANAS.get(), TELECREX.get(), DIPLOCAULUS.get(),
                LEPTICTIDIUM.get(), EntityType.CHICKEN, EntityType.BEE, EntityType.FROG,
                EntityType.RABBIT, EntityType.AXOLOTL, EntityType.CAT
        );

        this.tag(UP2EntityTags.MEDIUM_PREY).add(
                PACHYCEPHALOSAURUS.get(), DESMATOSUCHUS.get(), LYSTROSAURUS.get(),
                EntityType.PIG, EntityType.COW, EntityType.GOAT,
                EntityType.SHEEP, EntityType.OCELOT, EntityType.DONKEY,
                EntityType.MULE, EntityType.LLAMA, EntityType.TRADER_LLAMA
        );

        this.tag(UP2EntityTags.LARGE_PREY).add(
                KENTROSAURUS.get(),
                EntityType.CAMEL, EntityType.HOGLIN, EntityType.HORSE, EntityType.SNIFFER
        );

        this.tag(UP2EntityTags.GIANT_PREY).add(
                BRACHIOSAURUS.get(),
                THERIZINOSAURUS.get()
        );

        this.tag(UP2EntityTags.MONSTERS).add(
                EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.DROWNED,
                EntityType.ENDERMITE, EntityType.PIGLIN, EntityType.PILLAGER, EntityType.SHULKER,
                EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER,
                EntityType.STRAY, EntityType.VEX, EntityType.WITCH, EntityType.ZOMBIFIED_PIGLIN,
                EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER
        );

        this.tag(UP2EntityTags.STRONG_MONSTERS).add(
                EntityType.PIGLIN_BRUTE, EntityType.RAVAGER, EntityType.EVOKER, EntityType.ENDERMAN,
                EntityType.VINDICATOR, EntityType.ZOGLIN
        );

        this.tag(UP2EntityTags.AQUATIC_MONSTERS).add(
                EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.STRONG_AQUATIC_MONSTERS).add(
                EntityType.ELDER_GUARDIAN
        );

        // Targets
        this.tag(UP2EntityTags.CARNOTAURUS_TARGETS).addTag(UP2EntityTags.MEDIUM_PREY).addTag(UP2EntityTags.LARGE_PREY);

        this.tag(UP2EntityTags.DROMAEOSAURUS_TARGETS).addTag(UP2EntityTags.SMALL_PREY);

        this.tag(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS).addTag(UP2EntityTags.TINY_AQUATIC_PREY).addTag(UP2EntityTags.SMALL_AQUATIC_PREY);

        this.tag(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS).addTag(UP2EntityTags.SMALL_AQUATIC_PREY).addTag(UP2EntityTags.MEDIUM_AQUATIC_PREY).add(
                EntityType.DROWNED, EntityType.ZOMBIE, EntityType.PIGLIN, EntityType.SKELETON, EntityType.WITHER_SKELETON
        );

        this.tag(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS).addTag(UP2EntityTags.MEDIUM_AQUATIC_PREY).addTag(UP2EntityTags.LARGE_AQUATIC_PREY).add(
                EntityType.TURTLE, EntityType.DROWNED, EntityType.GUARDIAN, EntityType.ZOMBIE, EntityType.PIGLIN, EntityType.SKELETON, EntityType.WITHER_SKELETON
        );

        this.tag(UP2EntityTags.KAPROSUCHUS_TARGETS).addTag(UP2EntityTags.SMALL_AQUATIC_PREY).addTag(UP2EntityTags.SMALL_PREY);

        this.tag(UP2EntityTags.MAJUNGASAURUS_TARGETS).addTag(UP2EntityTags.SMALL_PREY).addTag(UP2EntityTags.MEDIUM_PREY);

        this.tag(UP2EntityTags.MEGALANIA_TARGETS).addTag(UP2EntityTags.SMALL_PREY).addTag(UP2EntityTags.MEDIUM_PREY);

        this.tag(UP2EntityTags.METRIORHYNCHUS_TARGETS).addTag(UP2EntityTags.TINY_AQUATIC_PREY).addTag(UP2EntityTags.SMALL_AQUATIC_PREY);

        this.tag(UP2EntityTags.ONCHOPRISTIS_TARGETS).addTag(UP2EntityTags.TINY_AQUATIC_PREY).addTag(UP2EntityTags.SMALL_AQUATIC_PREY);

        this.tag(UP2EntityTags.PACHYCEPHALOSAURUS_TARGETS).add(
                EntityType.COW,
                EntityType.SHEEP,
                EntityType.CHICKEN,
                EntityType.PIG,
                EntityType.VILLAGER,
                EntityType.MOOSHROOM,
                EntityType.STRIDER,
                EntityType.SNOW_GOLEM,
                LYSTROSAURUS.get(),
                LEPTICTIDIUM.get(),
                PRAEPUSA.get(),
                TALPANAS.get(),
                TELECREX.get()
        );

        this.tag(UP2EntityTags.PACHYCEPHALOSAURUS_TARGETS_TO_KILL).add(
                EntityType.GOAT
        );

        this.tag(UP2EntityTags.PRAEPUSA_TARGETS).add(
                JAWLESS_FISH.get(), EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH
        );

        this.tag(UP2EntityTags.SMALL_PSILOPTERUS_PACK_TARGETS).addTag(UP2EntityTags.TINY_PREY).addTag(UP2EntityTags.SMALL_PREY);

        this.tag(UP2EntityTags.MEDIUM_PSILOPTERUS_PACK_TARGETS).addTag(UP2EntityTags.SMALL_PREY).addTag(UP2EntityTags.MEDIUM_PREY);

        this.tag(UP2EntityTags.LARGE_PSILOPTERUS_PACK_TARGETS).addTag(UP2EntityTags.LARGE_PREY);

        this.tag(UP2EntityTags.STETHACANTHUS_TARGETS).addTag(UP2EntityTags.TINY_AQUATIC_PREY).addTag(UP2EntityTags.SMALL_AQUATIC_PREY);

        this.tag(UP2EntityTags.ULUGHBEGSAURUS_TARGETS).addTag(UP2EntityTags.MEDIUM_PREY);

        // Avoids
        this.tag(UP2EntityTags.COELACANTHUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(), MEGALANIA.get(),
                EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.DROMAEOSAURUS_AVOIDS).add(
                CARNOTAURUS.get(), MAJUNGASAURUS.get(), MEGALANIA.get()
        );

        this.tag(UP2EntityTags.DIMORPHODON_AVOIDS).add(
                CARNOTAURUS.get(), MAJUNGASAURUS.get(), MEGALANIA.get(),
                BRACHIOSAURUS.get(), BARINASUCHUS.get(), KENTROSAURUS.get(),
                MANIPULATOR.get(), METRIORHYNCHUS.get(), ULUGHBEGSAURUS.get(),
                THERIZINOSAURUS.get(), HIBBERTOPTERUS.get()
        );

        this.tag(UP2EntityTags.DIPLOCAULUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(), MEGALANIA.get(),
                CARNOTAURUS.get(), MAJUNGASAURUS.get(), DROMAEOSAURUS.get(),
                EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.JAWLESS_FISH_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(),
                STETHACANTHUS.get(), EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_AVOIDS).add(
                KENTROSAURUS.get(), MAJUNGASAURUS.get(),
                MEGALANIA.get(), DROMAEOSAURUS.get(), EntityType.OCELOT, EntityType.POLAR_BEAR,
                EntityType.WOLF, EntityType.SPIDER, EntityType.ZOMBIE, EntityType.SKELETON,
                EntityType.CREEPER, EntityType.PHANTOM, EntityType.STRAY, EntityType.HOGLIN,
                EntityType.ZOGLIN, EntityType.RAVAGER, EntityType.HORSE, EntityType.SKELETON_HORSE,
                EntityType.ZOMBIE_HORSE, EntityType.MULE, EntityType.DONKEY, EntityType.DROWNED,
                EntityType.HUSK, EntityType.GOAT, EntityType.WARDEN, EntityType.WITCH,
                EntityType.VILLAGER, EntityType.VEX, EntityType.VINDICATOR, EntityType.EVOKER,
                EntityType.EVOKER_FANGS, EntityType.CAMEL, EntityType.CAT, EntityType.WITHER_SKELETON,
                EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.CAVE_SPIDER,
                EntityType.FOX, EntityType.IRON_GOLEM, EntityType.TNT, EntityType.SLIME,
                EntityType.MAGMA_CUBE, EntityType.BLAZE, EntityType.WANDERING_TRADER, EntityType.ZOMBIFIED_PIGLIN,
                EntityType.PIGLIN, EntityType.PILLAGER, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIE_VILLAGER,
                EntityType.SHULKER, EntityType.GHAST, EntityType.ILLUSIONER, EntityType.LLAMA
        );

        this.tag(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(), STETHACANTHUS.get(),
                EntityType.AXOLOTL, EntityType.DROWNED, EntityType.GUARDIAN
        );

        this.tag(UP2EntityTags.LEPTICTIDIUM_AVOIDS).add(
                DROMAEOSAURUS.get(),
                PSILOPTERUS.get(),
                EntityType.OCELOT,
                EntityType.PIG,
                EntityType.CAT,
                EntityType.FOX,
                EntityType.WOLF
        );

        this.tag(UP2EntityTags.MAJUNGASAURUS_AVOIDS).add(
                CARNOTAURUS.get(), ULUGHBEGSAURUS.get(),
                EntityType.RAVAGER, EntityType.IRON_GOLEM, EntityType.WITHER, EntityType.WARDEN
        );

        this.tag(UP2EntityTags.PRAEPUSA_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(),
                MEGALANIA.get(), CARNOTAURUS.get(), MAJUNGASAURUS.get(), DROMAEOSAURUS.get(),
                EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.STETHACANTHUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), EntityType.DROWNED, EntityType.GUARDIAN
        );

        this.tag(UP2EntityTags.TALPANAS_AVOIDS).add(
                MAJUNGASAURUS.get(), MEGALANIA.get(), DROMAEOSAURUS.get(),
                EntityType.OCELOT, EntityType.POLAR_BEAR, EntityType.WOLF, EntityType.SPIDER, EntityType.ZOMBIE,
                EntityType.SKELETON, EntityType.CREEPER, EntityType.PHANTOM, EntityType.STRAY,
                EntityType.HOGLIN, EntityType.ZOGLIN, EntityType.RAVAGER, EntityType.HORSE,
                EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.DONKEY, EntityType.DROWNED,
                EntityType.HUSK, EntityType.GOAT, EntityType.WARDEN, EntityType.WITCH,
                EntityType.VILLAGER, EntityType.VEX, EntityType.VINDICATOR, EntityType.EVOKER,
                EntityType.EVOKER_FANGS, EntityType.CAMEL, EntityType.CAT, EntityType.WITHER_SKELETON,
                EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.ENDERMAN,
                EntityType.CAVE_SPIDER, EntityType.FOX, EntityType.IRON_GOLEM,
                EntityType.TNT, EntityType.SLIME, EntityType.MAGMA_CUBE, EntityType.BLAZE,
                EntityType.WANDERING_TRADER, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN,
                EntityType.PILLAGER, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIE_VILLAGER,
                EntityType.SHULKER, EntityType.GHAST, EntityType.ILLUSIONER
        );

        this.tag(UP2EntityTags.TARTUOSTEUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(), STETHACANTHUS.get(),
                EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.TELECREX_AVOIDS).add(
                MAJUNGASAURUS.get(), MEGALANIA.get(),
                DROMAEOSAURUS.get(), CARNOTAURUS.get(), METRIORHYNCHUS.get(),
                EntityType.OCELOT, EntityType.POLAR_BEAR, EntityType.WOLF, EntityType.SPIDER,
                EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.PHANTOM,
                EntityType.STRAY, EntityType.HOGLIN, EntityType.ZOGLIN, EntityType.RAVAGER,
                EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.DROWNED, EntityType.HUSK,
                EntityType.GOAT, EntityType.WARDEN, EntityType.WITCH, EntityType.VILLAGER,
                EntityType.VEX, EntityType.VINDICATOR, EntityType.EVOKER, EntityType.EVOKER_FANGS,
                EntityType.CAT, EntityType.WITHER_SKELETON, EntityType.WITHER,
                EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.CAVE_SPIDER,
                EntityType.FOX, EntityType.IRON_GOLEM, EntityType.TNT,
                EntityType.SLIME, EntityType.MAGMA_CUBE, EntityType.BLAZE,
                EntityType.WANDERING_TRADER, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN,
                EntityType.PILLAGER, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIE_VILLAGER,
                EntityType.SHULKER, EntityType.GHAST, EntityType.ILLUSIONER
        );

        // Misc
        this.tag(UP2EntityTags.SWEET_BERRY_BUSH_IMMUNE).add(
                KENTROSAURUS.get()
        );

        this.tag(UP2EntityTags.CARNOTAURUS_IGNORES).add(
                EntityType.CREEPER
        ).addOptional(new ResourceLocation("species", "quake"));

        this.tag(UP2EntityTags.COELACANTHUS_NEVER_EATS).add(
                EntityType.ENDER_DRAGON
        );

        this.tag(UP2EntityTags.DIMORPHODON_CANT_GRAB).add(
                DIMORPHODON.get(),
                EntityType.ENDER_DRAGON,
                EntityType.WITHER,
                EntityType.VEX
        );
        this.tag(UP2EntityTags.DIMORPHODON_CAN_GRAB).add(
                EntityType.COW,
                EntityType.SHEEP,
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.DROWNED,
                EntityType.STRAY,
                EntityType.SPIDER,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.PIG,
                EntityType.PIGLIN,
                EntityType.VINDICATOR,
                EntityType.EVOKER,
                EntityType.CREEPER
        );

        this.tag(UP2EntityTags.METRIORHYNCHUS_CANT_GRAB).add(
                METRIORHYNCHUS.get(),
                EntityType.ENDER_DRAGON,
                EntityType.WITHER
        );
        this.tag(UP2EntityTags.METRIORHYNCHUS_CAN_GRAB).add(
                TARTUOSTEUS.get()
        );

        // Minecraft
        this.tag(EntityTypeTags.FROG_FOOD).add(
                DELITZSCHALA.get(),
                ZHANGSOLVA.get()
        );
    }
}
