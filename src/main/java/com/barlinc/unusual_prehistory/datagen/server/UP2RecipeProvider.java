package com.barlinc.unusual_prehistory.datagen.server;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.data.TransmogrificationRecipeBuilder;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2RecipeProvider extends RecipeProvider {

    public UP2RecipeProvider(PackOutput output, CompletableFuture<Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        // Update 1
        transmogrification(output, UP2Items.FURY_FOSSIL, CARNOTAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.BOOMERANG_FOSSIL, DIPLOCAULUS_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.RUNNER_FOSSIL, UP2Items.DROMAEOSAURUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.GUILLOTINE_FOSSIL, DUNKLEOSTEUS_SAC, 2400, 1.5F);
        transmogrification(output, UP2Items.JAWLESS_FOSSIL, JAWLESS_FISH_ROE, 1200, 1.0F);
        transmogrification(output, UP2Items.IMPERATIVE_FOSSIL, KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.PRICKLY_FOSSIL, KENTROSAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.RUGOSE_FOSSIL, MAJUNGASAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.THERMAL_FOSSIL, MEGALANIA_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.ANVIL_FOSSIL, STETHACANTHUS_SAC, 1200, 1.0F);
        transmogrification(output, UP2Items.AGED_FEATHER, UP2Items.TALPANAS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.PLUMAGE_FOSSIL, UP2Items.TELECREX_EGG, 1200, 1.0F);

        // Update 2
        transmogrification(output, UP2Items.SAW_FOSSIL, ONCHOPRISTIS_SAC, 2400, 1.5F);

        // Update 3
        transmogrification(output, UP2Items.MELTDOWN_FOSSIL, UP2Items.METRIORHYNCHUS_EMBRYO, 2400, 1.5F);
        transmogrification(output, UP2Items.MOSSY_FOSSIL, TARTUOSTEUS_ROE, 1200, 1.0F);

        // Update 4
        transmogrification(output, UP2Items.ARM_FOSSIL, BRACHIOSAURUS_EGG, 3600, 2.0F);
        transmogrification(output, UP2Items.GLUTTONOUS_FOSSIL, COELACANTHUS_ROE, 1200, 1.0F);
        transmogrification(output, UP2Items.PLOW_FOSSIL, HIBBERTOPTERUS_EGGS, 2400, 1.5F);
        transmogrification(output, UP2Items.BOAR_TOOTH_FOSSIL, KAPROSUCHUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.TRUNK_MOUSE_FOSSIL, UP2Items.LEPTICTIDIUM_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.FISH_FOSSIL, LOBE_FINNED_FISH_ROE, 1200, 1.0F);
        transmogrification(output, UP2Items.IMPERVIOUS_FOSSIL, LYSTROSAURUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.CRANIUM_FOSSIL, PACHYCEPHALOSAURUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.FLIPPER_FOSSIL, UP2Items.PRAEPUSA_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.WING_FOSSIL, UP2Items.PTERODACTYLUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.DUBIOUS_FOSSIL, ULUGHBEGSAURUS_EGG, 2400, 1.5F);

        // Update 5
        transmogrification(output, UP2Items.BRISTLE_FOSSIL, AEGIROCASSIS_EGGS, 3600, 2.0F);
        transmogrification(output, UP2Items.FLAT_BACK_FOSSIL, DESMATOSUCHUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.CROOKED_BEAK_FOSSIL, UP2Items.PSILOPTERUS_EGG, 1200, 1.0F);

        // Update 6
        transmogrification(output, UP2Items.SURGE_FOSSIL, UP2Items.PROGNATHODON_EMBRYO, 2400, 1.5F);
    }

    protected static void transmogrification(RecipeOutput output, ItemLike inputItem, ItemLike resultItem, int processingTime, float experience) {
        TransmogrificationRecipeBuilder.transmogrification(Ingredient.of(inputItem), resultItem.asItem().getDefaultInstance(), experience, processingTime).unlockedBy(getHasName(inputItem), has(inputItem)).save(output, UnusualPrehistory2.modPrefix("transmogrification/" + getItemName(resultItem)));
    }

    public static ResourceLocation suffix(ResourceLocation location, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath() + suffix);
    }
}
