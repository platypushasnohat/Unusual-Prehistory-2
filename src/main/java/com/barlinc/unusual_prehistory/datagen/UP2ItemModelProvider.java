package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemModelProvider extends ItemModelProvider {

    public UP2ItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.item(UNUSUAL_PREHISTORY);

        this.item(MACHINE_PARTS);
        this.item(GINKGO_FRUIT);

        this.item(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE);
        this.item(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET);
        this.item(PRAEPUSA_BUCKET);
        this.item(STETHACANTHUS_BUCKET);
        this.item(COELACANTHUS_BUCKET);
        this.item(BABY_AEGIROCASSIS_BUCKET);

        this.item(DOOMSURF_DISC);
        this.item(MEGALANIA_DISC);
        this.item(TARIFYING_DISC);

        this.item(TAR_BUCKET);
        this.item(LIVING_OOZE_BUCKET);

        this.item(PETRIFIED_LUCA);
        this.item(LUCA);

        this.handheldRodItem(DIRT_ON_A_STICK);

        this.item(UP2Blocks.CYCAD_SEEDLING);

        this.item(UP2Blocks.GUANGDEDENDRON);

        // fossils
        this.item(BRISTLE_FOSSIL);
        this.item(BRUTE_FOSSIL);
        this.item(ARM_FOSSIL);
        this.item(FURY_FOSSIL);
        this.item(GLUTTONOUS_FOSSIL);
        this.item(FLAT_BACK_FOSSIL);
        this.item(BILL_FOSSIL);
        this.item(BOOMERANG_FOSSIL);
        this.item(RUNNER_FOSSIL);
        this.item(GUILLOTINE_FOSSIL);
        this.item(PLOW_FOSSIL);
        this.item(JAWLESS_FOSSIL);
        this.item(BOAR_TOOTH_FOSSIL);
        this.item(PRICKLY_FOSSIL);
        this.item(IMPERATIVE_FOSSIL);
        this.item(TRUNK_MOUSE_FOSSIL);
        this.item(FISH_FOSSIL);
        this.item(IMPERVIOUS_FOSSIL);
        this.item(RUGOSE_FOSSIL);
        this.item(ROACH_FOSSIL);
        this.item(THERMAL_FOSSIL);
        this.item(MELTDOWN_FOSSIL);
        this.item(SAW_FOSSIL);
        this.item(ANVIL_FOSSIL);
        this.item(CRANIUM_FOSSIL);
        this.item(FLIPPER_FOSSIL);
        this.item(CROOKED_BEAK_FOSSIL);
        this.item(WING_FOSSIL);
        this.item(MOSSY_FOSSIL);
        this.item(PLUMAGE_FOSSIL);
        this.item(SCYTHE_FOSSIL);
        this.item(AGED_FEATHER);
        this.item(DUBIOUS_FOSSIL);

        // eggs
        this.item(UP2Blocks.AEGIROCASSIS_EGGS);
        this.item(UP2Blocks.BARINASUCHUS_EGG);
        this.item(UP2Blocks.BRACHIOSAURUS_EGG);
        this.item(UP2Blocks.CARNOTAURUS_EGG);
        this.item(UP2Blocks.COELACANTHUS_ROE);
        this.item(UP2Blocks.DESMATOSUCHUS_EGG);
        this.item(DIMORPHODON_EGG);
        this.item(UP2Blocks.DIPLOCAULUS_EGGS);
        this.item(DROMAEOSAURUS_EGG);
        this.item(UP2Blocks.DUNKLEOSTEUS_SAC);
        this.item(UP2Blocks.HIBBERTOPTERUS_EGGS);
        this.item(UP2Blocks.JAWLESS_FISH_ROE);
        this.item(UP2Blocks.KAPROSUCHUS_EGG);
        this.item(UP2Blocks.KENTROSAURUS_EGG);
        this.item(UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS);
        this.item(LEPTICTIDIUM_EMBRYO);
        this.item(UP2Blocks.LOBE_FINNED_FISH_ROE);
        this.item(UP2Blocks.LYSTROSAURUS_EGG);
        this.item(UP2Blocks.MAJUNGASAURUS_EGG);
        this.item(UP2Blocks.MANIPULATOR_OOTHECA);
        this.item(UP2Blocks.MEGALANIA_EGG);
        this.item(METRIORHYNCHUS_EMBRYO);
        this.item(UP2Blocks.ONCHOPRISTIS_SAC);
        this.item(UP2Blocks.PACHYCEPHALOSAURUS_EGG);
        this.item(PRAEPUSA_EMBRYO);
        this.item(PSILOPTERUS_EGG);
        this.item(PTERODACTYLUS_EGG);
        this.item(UP2Blocks.STETHACANTHUS_SAC);
        this.item(TALPANAS_EGG);
        this.item(UP2Blocks.TARTUOSTEUS_ROE);
        this.item(TELECREX_EGG);
        this.item(UP2Blocks.THERIZINOSAURUS_EGG);
        this.item(UP2Blocks.ULUGHBEGSAURUS_EGG);

        this.item(CALAMOPHYTON_FOSSIL);
        this.item(RAIGUENRAYUN_FOSSIL);
        this.item(GINKGO_FOSSIL);
        this.item(RHYNIA_FOSSIL);
        this.item(TEMPSKYA_FOSSIL);
        this.item(LEEFRUCTUS_FOSSIL);
        this.item(NEOMARIOPTERIS_FOSSIL);
        this.item(PROTOTAXITES_FOSSIL);
        this.item(QUILLWORT_FOSSIL);
        this.item(LEPIDODENDRON_FOSSIL);
        this.item(COOKSONIA_FOSSIL);
        this.item(CLADOPHLEBIS_FOSSIL);
        this.item(BENNETTITALES_FOSSIL);
        this.item(AETHOPHYLLUM_FOSSIL);
        this.item(BRACHYPHYLLUM_FOSSIL);
        this.item(GUANGDEDENDRON_FOSSIL);
        this.item(METASEQUOIA_FOSSIL);
        this.item(DRYOPHYLLUM_FOSSIL);
        this.item(CYCAD_FOSSIL);

        this.item(UP2Blocks.TEMPSKYA);
        this.item(UP2Blocks.BRACHYPHYLLUM);
        this.item(UP2Blocks.AETHOPHYLLUM);

        this.item(DRYOPHYLLUM_SIGN);
        this.item(DRYOPHYLLUM_HANGING_SIGN);
        this.item(DRYOPHYLLUM_BOAT);
        this.item(DRYOPHYLLUM_CHEST_BOAT);

        this.item(GINKGO_SIGN);
        this.item(GINKGO_HANGING_SIGN);
        this.item(GINKGO_BOAT);
        this.item(GINKGO_CHEST_BOAT);

        this.item(LEPIDODENDRON_SIGN);
        this.item(LEPIDODENDRON_HANGING_SIGN);
        this.item(LEPIDODENDRON_BOAT);
        this.item(LEPIDODENDRON_CHEST_BOAT);

        this.item(METASEQUOIA_SIGN);
        this.item(METASEQUOIA_HANGING_SIGN);
        this.item(METASEQUOIA_BOAT);
        this.item(METASEQUOIA_CHEST_BOAT);

        // spawn eggs
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SpawnEggItem && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getNamespace().equals(UnusualPrehistory2.MOD_ID)) {
                getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()).parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }
    }

    // item
    private ItemModelBuilder item(RegistryObject<?> item) {
        return generated(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    private void handheldItem(RegistryObject<?> item) {
        this.handheld(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    private void handheldRodItem(RegistryObject<?> item) {
        this.handheldRod(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    // utils
    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private void handheld(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
    }

    private void handheldRod(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld_rod");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
    }
}
