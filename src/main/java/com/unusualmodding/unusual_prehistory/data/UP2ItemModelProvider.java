package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static com.unusualmodding.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemModelProvider extends ItemModelProvider {

    public UP2ItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.item(PALEOPEDIA);

        this.item(PLANT_FOSSIL);
        this.item(PALEOZOIC_FOSSIL);
        this.item(MESOZOIC_FOSSIL);
        this.item(FROZEN_MEAT);
        this.item(ORGANIC_OOZE);
        this.item(GINKGO_FRUIT);

        this.item(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE);
        this.item(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET);
        this.item(JAWLESS_FISH_BUCKET);
        this.item(SCAUMENACIA_BUCKET);
        this.item(STETHACANTHUS_BUCKET);
        this.item(DIPLOCAULUS_BUCKET);
        this.item(DUNKLEOSTEUS_BUCKET);

        // paleo dna
        this.dnaItem(DIPLOCAULUS_DNA);
        this.dnaItem(DUNKLEOSTEUS_DNA);
        this.dnaItem(JAWLESS_FISH_DNA);
        this.dnaItem(SCAUMENACIA_DNA);
        this.dnaItem(STETHACANTHUS_DNA);

        // meso dna
        this.dnaItem(CARNOTAURUS_DNA);
        this.dnaItem(DROMAEOSAURUS_DNA);
        this.dnaItem(KENTROSAURUS_DNA);
        this.dnaItem(KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA);
        this.dnaItem(MAJUNGASAURUS_DNA);

        // ceno dna
        this.dnaItem(MEGALANIA_DNA);
        this.dnaItem(TELECREX_DNA);

        // holocene dna
        this.dnaItem(TALPANAS_DNA);

        // plant dna
        this.dnaItem(ARCHAEOSIGILLARIA_DNA);
        this.dnaItem(BENNETTITALES_DNA);
        this.dnaItem(CALAMOPHYTON_DNA);
        this.dnaItem(CLADOPHLEBIS_DNA);
        this.dnaItem(COOKSONIA_DNA);
        this.dnaItem(GINKGO_DNA);
        this.dnaItem(HORSETAIL_DNA);
        this.dnaItem(ISOETES_DNA);
        this.dnaItem(LEEFRUCTUS_DNA);
        this.dnaItem(LEPIDODENDRON_DNA);
        this.dnaItem(RAIGUENRAYUN_DNA);
        this.dnaItem(RHYNIA_DNA);
        this.dnaItem(SARRACENIA_DNA);

        // water plant dna
        this.dnaItem(ANOSTYLOSTROMA_DNA);
        this.dnaItem(ARCHAEFRUCTUS_DNA);
        this.dnaItem(CLATHRODICTYON_CORAL_DNA);
        this.dnaItem(NELUMBITES_DNA);
        this.dnaItem(QUEREUXIA_DNA);

        // eggs
        this.item(DROMAEOSAURUS_EGG);
        this.item(TALPANAS_EGG);
        this.item(TELECREX_EGG);
        this.item(DUNKLEOSTEUS_SAC);
        this.item(STETHACANTHUS_SAC);

        // spawn eggs
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SpawnEggItem && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getNamespace().equals(UnusualPrehistory2.MOD_ID)) {
                getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()).parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }
    }

    // item
    private ItemModelBuilder item(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder dnaItem(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), modLoc("item/dna/" + item.getId().getPath()));
    }

    // utils
    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
}
