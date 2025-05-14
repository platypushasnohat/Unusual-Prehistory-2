package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.unusualmodding.unusual_prehistory.items.UP2Items.*;

public class UP2ItemModelProvider extends ItemModelProvider {

    public UP2ItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.item(FROZEN_MEAT);
        this.item(GINKGO_FRUIT);

        // paleo dna
        this.dnaItem(DIPLOCAULUS_DNA);
        this.dnaItem(DUNKLEOSTEUS_DNA);
        this.dnaItem(JAWLESS_FISH_DNA);
        this.dnaItem(SCAUMENACIA_DNA);
        this.dnaItem(STETHACANTHUS_DNA);

        // meso dna
        this.dnaItem(KENTROSAURUS_DNA);
        this.dnaItem(KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA);
        this.dnaItem(MAJUNGASAURUS_DNA);

        // ceno dna
        this.dnaItem(MEGALANIA_DNA);
        this.dnaItem(TELECREX_DNA);

        // plant dna
        this.dnaItem(ARCHAEOSIGILLARIA_DNA);
        this.dnaItem(BENNETTITALES_DNA);
        this.dnaItem(CALAMOPHYTON_DNA);
        this.dnaItem(CLADOPHLEBIS_DNA);
        this.dnaItem(GINKGO_DNA);
        this.dnaItem(HORSETAIL_DNA);
        this.dnaItem(ISOETES_DNA);
        this.dnaItem(LEEFRUCTUS_DNA);
        this.dnaItem(RAIGUENRAYUN_DNA);
        this.dnaItem(SARRACENIA_DNA);

        // water plant dna
        this.dnaItem(ANOSTYLOSTROMA_DNA);
        this.dnaItem(ARCHAEFRUCTUS_DNA);
        this.dnaItem(CLATHRODICTYON_CORAL_DNA);
        this.dnaItem(NELUMBITES_DNA);
        this.dnaItem(QUEREUXIA_DNA);
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
