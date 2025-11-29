package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
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

        this.tag(UP2EntityTags.SWEET_BERRY_BUSH_IMMUNE).add(
                KENTROSAURUS.get()
        );

        this.tag(UP2EntityTags.DROMAEOSAURUS_AVOIDS).add(
                CARNOTAURUS.get(),
                MAJUNGASAURUS.get(),
                MEGALANIA.get()
        );

        this.tag(UP2EntityTags.JAWLESS_FISH_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                METRIORHYNCHUS.get(),
                ONCHOPRISTIS.get(),
                STETHACANTHUS.get(),
                EntityType.GUARDIAN,
                EntityType.DROWNED
        );

        this.tag(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                METRIORHYNCHUS.get(),
                ONCHOPRISTIS.get(),
                STETHACANTHUS.get(),
                EntityType.AXOLOTL,
                EntityType.DROWNED,
                EntityType.GUARDIAN
        );

        this.tag(UP2EntityTags.DROMAEOSAURUS_TARGETS).add(
                TELECREX.get(),
                EntityType.CHICKEN
        );

        this.tag(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS).add(
                JAWLESS_FISH.get(),
                STETHACANTHUS.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH,
                EntityType.DOLPHIN,
                EntityType.TURTLE,
                EntityType.DROWNED
        );

        this.tag(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS).add(
                JAWLESS_FISH.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH,
                EntityType.DOLPHIN
        );

        this.tag(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS).add(
                JAWLESS_FISH.get(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

        this.tag(UP2EntityTags.CARNOTAURUS_TARGETS).add(
                DROMAEOSAURUS.get(),
                KENTROSAURUS.get(),
                EntityType.HORSE,
                EntityType.CAMEL,
                EntityType.SNIFFER,
                EntityType.COW,
                EntityType.PIG,
                EntityType.SHEEP,
                EntityType.GOAT,
                EntityType.LLAMA,
                EntityType.DONKEY,
                EntityType.MULE
        );

        this.tag(UP2EntityTags.CARNOTAURUS_IGNORES).add(
                EntityType.CREEPER
        ).addOptional(new ResourceLocation("species", "quake"));

        this.tag(UP2EntityTags.MAJUNGASAURUS_TARGETS).add(
                EntityType.SHEEP,
                EntityType.COW,
                EntityType.PIG,
                EntityType.GOAT
        );

        this.tag(UP2EntityTags.MEGALANIA_TARGETS).add(
                EntityType.SHEEP,
                EntityType.COW,
                EntityType.PIG,
                EntityType.GOAT
        );

        this.tag(UP2EntityTags.METRIORHYNCHUS_TARGETS).add(
                JAWLESS_FISH.get(),
                STETHACANTHUS.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

        this.tag(UP2EntityTags.ONCHOPRISTIS_TARGETS).add(
                JAWLESS_FISH.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

        this.tag(UP2EntityTags.STETHACANTHUS_TARGETS).add(
                JAWLESS_FISH.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

        this.tag(UP2EntityTags.STETHACANTHUS_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                EntityType.DROWNED,
                EntityType.GUARDIAN
        );

        this.tag(UP2EntityTags.SCATTERS_TELECREX).add(
                KENTROSAURUS.get(),
                MAJUNGASAURUS.get(),
                MEGALANIA.get(),
                DROMAEOSAURUS.get(),
                EntityType.OCELOT,
                EntityType.POLAR_BEAR,
                EntityType.WOLF,
                EntityType.SPIDER,
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.CREEPER,
                EntityType.PHANTOM,
                EntityType.STRAY,
                EntityType.HOGLIN,
                EntityType.ZOGLIN,
                EntityType.RAVAGER,
                EntityType.HORSE,
                EntityType.SKELETON_HORSE,
                EntityType.ZOMBIE_HORSE,
                EntityType.MULE,
                EntityType.DONKEY,
                EntityType.DROWNED,
                EntityType.HUSK,
                EntityType.GOAT,
                EntityType.WARDEN,
                EntityType.WITCH,
                EntityType.VILLAGER,
                EntityType.VEX,
                EntityType.VINDICATOR,
                EntityType.EVOKER,
                EntityType.EVOKER_FANGS,
                EntityType.CAMEL,
                EntityType.CAT,
                EntityType.WITHER_SKELETON,
                EntityType.WITHER,
                EntityType.ENDER_DRAGON,
                EntityType.ENDERMAN,
                EntityType.CAVE_SPIDER,
                EntityType.FOX,
                EntityType.IRON_GOLEM,
                EntityType.TNT,
                EntityType.SLIME,
                EntityType.MAGMA_CUBE,
                EntityType.BLAZE,
                EntityType.WANDERING_TRADER,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.PIGLIN,
                EntityType.PILLAGER,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.SHULKER,
                EntityType.GHAST,
                EntityType.ILLUSIONER,
                EntityType.LLAMA
        );

        this.tag(UP2EntityTags.TALPANAS_AVOIDS).add(
                MAJUNGASAURUS.get(),
                MEGALANIA.get(),
                DROMAEOSAURUS.get(),
                EntityType.OCELOT,
                EntityType.POLAR_BEAR,
                EntityType.WOLF,
                EntityType.SPIDER,
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.CREEPER,
                EntityType.PHANTOM,
                EntityType.STRAY,
                EntityType.HOGLIN,
                EntityType.ZOGLIN,
                EntityType.RAVAGER,
                EntityType.HORSE,
                EntityType.SKELETON_HORSE,
                EntityType.ZOMBIE_HORSE,
                EntityType.DONKEY,
                EntityType.DROWNED,
                EntityType.HUSK,
                EntityType.GOAT,
                EntityType.WARDEN,
                EntityType.WITCH,
                EntityType.VILLAGER,
                EntityType.VEX,
                EntityType.VINDICATOR,
                EntityType.EVOKER,
                EntityType.EVOKER_FANGS,
                EntityType.CAMEL,
                EntityType.CAT,
                EntityType.WITHER_SKELETON,
                EntityType.WITHER,
                EntityType.ENDER_DRAGON,
                EntityType.ENDERMAN,
                EntityType.CAVE_SPIDER,
                EntityType.FOX,
                EntityType.IRON_GOLEM,
                EntityType.TNT,
                EntityType.SLIME,
                EntityType.MAGMA_CUBE,
                EntityType.BLAZE,
                EntityType.WANDERING_TRADER,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.PIGLIN,
                EntityType.PILLAGER,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.SHULKER,
                EntityType.GHAST,
                EntityType.ILLUSIONER
        );

        this.tag(UP2EntityTags.SCATTERS_KIMMERIDGEBRACHYPTERAESCHNIDIUM).add(
                KENTROSAURUS.get(),
                MAJUNGASAURUS.get(),
                MEGALANIA.get(),
                DROMAEOSAURUS.get(),
                EntityType.OCELOT,
                EntityType.POLAR_BEAR,
                EntityType.WOLF,
                EntityType.SPIDER,
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.CREEPER,
                EntityType.PHANTOM,
                EntityType.STRAY,
                EntityType.HOGLIN,
                EntityType.ZOGLIN,
                EntityType.RAVAGER,
                EntityType.HORSE,
                EntityType.SKELETON_HORSE,
                EntityType.ZOMBIE_HORSE,
                EntityType.MULE,
                EntityType.DONKEY,
                EntityType.DROWNED,
                EntityType.HUSK,
                EntityType.GOAT,
                EntityType.WARDEN,
                EntityType.WITCH,
                EntityType.VILLAGER,
                EntityType.VEX,
                EntityType.VINDICATOR,
                EntityType.EVOKER,
                EntityType.EVOKER_FANGS,
                EntityType.CAMEL,
                EntityType.CAT,
                EntityType.WITHER_SKELETON,
                EntityType.WITHER,
                EntityType.ENDER_DRAGON,
                EntityType.ENDERMAN,
                EntityType.CAVE_SPIDER,
                EntityType.FOX,
                EntityType.IRON_GOLEM,
                EntityType.TNT,
                EntityType.SLIME,
                EntityType.MAGMA_CUBE,
                EntityType.BLAZE,
                EntityType.WANDERING_TRADER,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.PIGLIN,
                EntityType.PILLAGER,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.SHULKER,
                EntityType.GHAST,
                EntityType.ILLUSIONER,
                EntityType.LLAMA
        );
    }
}
