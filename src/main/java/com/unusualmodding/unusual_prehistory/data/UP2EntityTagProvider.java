package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.Megalania;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2EntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.unusual_prehistory.registry.UP2Entities.*;

public class UP2EntityTagProvider extends EntityTypeTagsProvider {

    public UP2EntityTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, provider, UnusualPrehistory2.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(UP2EntityTags.DROMAEOSAURUS_TARGETS).add(
                TELECREX.get(),
                EntityType.CHICKEN
        );

        tag(UP2EntityTags.BIG_DUNKLEOSTEUS_TARGETS).add(
                JAWLESS_FISH.get(),
                SCAUMENACIA.get(),
                STETHACANTHUS.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH,
                EntityType.DOLPHIN,
                EntityType.TURTLE,
                EntityType.DROWNED
        );

        tag(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS).add(
                JAWLESS_FISH.get(),
                SCAUMENACIA.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH,
                EntityType.DOLPHIN
        );

        tag(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS).add(
                JAWLESS_FISH.get(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(),
                SCAUMENACIA.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

        tag(UP2EntityTags.MAJUNGASAURUS_TARGETS).add(
                EntityType.SHEEP,
                EntityType.COW,
                EntityType.PIG,
                EntityType.GOAT
        );

        tag(UP2EntityTags.JAWLESS_FISH_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                STETHACANTHUS.get(),
                EntityType.GUARDIAN,
                EntityType.DROWNED
        );

        tag(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                STETHACANTHUS.get(),
                EntityType.AXOLOTL,
                EntityType.DROWNED,
                EntityType.GUARDIAN
        );

        tag(UP2EntityTags.SCAUMENACIA_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                STETHACANTHUS.get(),
                EntityType.DROWNED,
                EntityType.GUARDIAN
        );

        tag(UP2EntityTags.STETHACANTHUS_TARGETS).add(
                JAWLESS_FISH.get(),
                SCAUMENACIA.get(),
                EntityType.COD,
                EntityType.SALMON,
                EntityType.TROPICAL_FISH
        );

        tag(UP2EntityTags.STETHACANTHUS_AVOIDS).add(
                DUNKLEOSTEUS.get(),
                EntityType.DROWNED,
                EntityType.GUARDIAN
        );

        tag(UP2EntityTags.SCATTERS_TELECREX).add(
                KENTROSAURUS.get(),
                MAJUNGASAURUS.get(),
                MEGALANIA.get(),
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
