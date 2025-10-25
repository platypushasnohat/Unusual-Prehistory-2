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
        this.item(UNUSUAL_PREHISTORY);

        this.item(MACHINE_PARTS);
        this.item(GINKGO_FRUIT);

        this.item(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE);
        this.item(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET);
        this.item(STETHACANTHUS_BUCKET);
        this.item(DUNKLEOSTEUS_BUCKET);

        this.item(TARIFYING_DISC);

        // eggs
        this.item(DROMAEOSAURUS_EGG);
        this.item(TALPANAS_EGG);
        this.item(TELECREX_EGG);

        this.item(FURY_FOSSIL);
        this.item(BOOMERANG_FOSSIL);
        this.item(RUNNER_FOSSIL);
        this.item(GUILLOTINE_FOSSIL);
        this.item(JAWLESS_FOSSIL);
        this.item(PRICKLY_FOSSIL);
        this.item(IMPERATIVE_FOSSIL);
        this.item(RUGOSE_FOSSIL);
        this.item(THERMAL_FOSSIL);
        this.item(ANVIL_FOSSIL);
        this.item(PLUMAGE_FOSSIL);
        this.item(AGED_FEATHER);

        this.item(CALAMOPHYTON_FOSSIL);
        this.item(RAIGUENRAYUN_FOSSIL);
        this.item(GINKGO_FOSSIL);
        this.item(RHYNIA_FOSSIL);
        this.item(LEEFRUCTUS_FOSSIL);
        this.item(QUILLWORT_FOSSIL);
        this.item(LEPIDODENDRON_FOSSIL);
        this.item(COOKSONIA_FOSSIL);
        this.item(CLADOPHEBIS_FOSSIL);
        this.item(BENNETTITALES_FOSSIL);
        this.item(HORSETAIL_FOSSIL);

        this.item(GINKGO_SIGN);
        this.item(GINKGO_HANGING_SIGN);
        this.item(GINKGO_BOAT);
        this.item(GINKGO_CHEST_BOAT);

        this.item(LEPIDODENDRON_SIGN);
        this.item(LEPIDODENDRON_HANGING_SIGN);
        this.item(LEPIDODENDRON_BOAT);
        this.item(LEPIDODENDRON_CHEST_BOAT);

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
