package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemTagProvider extends ItemTagsProvider {

    public UP2ItemTagProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {

        this.tag(UP2ItemTags.FOSSILS).add(
                FURY_FOSSIL.get(),
                BOOMERANG_FOSSIL.get(),
                RUNNER_FOSSIL.get(),
                GUILLOTINE_FOSSIL.get(),
                JAWLESS_FOSSIL.get(),
                PRICKLY_FOSSIL.get(),
                IMPERATIVE_FOSSIL.get(),
                RUGOSE_FOSSIL.get(),
                THERMAL_FOSSIL.get(),
                LONG_CROCODILE_FOSSIL.get(),
                SAW_FOSSIL.get(),
                ANVIL_FOSSIL.get(),
                PLUMAGE_FOSSIL.get(),

                CALAMOPHYTON_FOSSIL.get(),
                RAIGUENRAYUN_FOSSIL.get(),
                GINKGO_FOSSIL.get(),
                RHYNIA_FOSSIL.get(),
                LEEFRUCTUS_FOSSIL.get(),
                QUILLWORT_FOSSIL.get(),
                LEPIDODENDRON_FOSSIL.get(),
                COOKSONIA_FOSSIL.get(),
                CLADOPHEBIS_FOSSIL.get(),
                BENNETTITALES_FOSSIL.get(),
                HORSETAIL_FOSSIL.get()
        );

        this.tag(UP2ItemTags.TRANSMOGRIFIER_FUEL).add(
                ORGANIC_OOZE.get()
        );

        this.tag(UP2ItemTags.PACIFIES_MOB).add(Items.ENCHANTED_GOLDEN_APPLE);

        this.tag(UP2ItemTags.PACIFIES_DROMAEOSAURUS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_DUNKLEOSTEUS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_STETHACANTHUS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_MAJUNGASAURUS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_CARNOTAURUS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_MEGALANIA)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_METRIORHYNCHUS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.PACIFIES_ONCHOPRISTIS)
                .addTag(UP2ItemTags.PACIFIES_MOB);

        this.tag(UP2ItemTags.CARNOTAURUS_FOOD).add(
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.MUTTON,
                Items.RABBIT,
                Items.COOKED_BEEF,
                Items.COOKED_PORKCHOP,
                Items.COOKED_CHICKEN,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT
        );

        this.tag(UP2ItemTags.DIPLOCAULUS_FOOD).add(
                Items.SPIDER_EYE
        );

        this.tag(UP2ItemTags.DROMAEOSAURUS_FOOD).add(
                Items.CHICKEN,
                Items.COOKED_CHICKEN
        );

        this.tag(UP2ItemTags.DUNKLEOSTEUS_FOOD).add(
                Items.COD,
                Items.SALMON,
                Items.TROPICAL_FISH,
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.JAWLESS_FISH_FOOD).add(
                Items.SEAGRASS
        );

        this.tag(UP2ItemTags.KENTROSAURUS_FOOD).add(
                Items.CACTUS,
                Items.SWEET_BERRIES
        );

        this.tag(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FOOD).add(
                Items.SPIDER_EYE,
                Items.COD,
                Items.SALMON,
                Items.TROPICAL_FISH,
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.MAJUNGASAURUS_FOOD).add(
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.MUTTON,
                Items.RABBIT,
                Items.COOKED_BEEF,
                Items.COOKED_PORKCHOP,
                Items.COOKED_CHICKEN,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT
        );

        this.tag(UP2ItemTags.MEGALANIA_FOOD).add(
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.MUTTON,
                Items.RABBIT,
                Items.COOKED_BEEF,
                Items.COOKED_PORKCHOP,
                Items.COOKED_CHICKEN,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT
        );

        this.tag(UP2ItemTags.METRIORHYNCHUS_FOOD).add(
                Items.COD,
                Items.SALMON,
                Items.TROPICAL_FISH,
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.ONCHOPRISTIS_FOOD).add(
                Items.COD,
                Items.SALMON,
                Items.TROPICAL_FISH,
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.STETHACANTHUS_FOOD).add(
                Items.COD,
                Items.SALMON,
                Items.TROPICAL_FISH,
                Items.COOKED_COD,
                Items.COOKED_SALMON
        );

        this.tag(UP2ItemTags.TALPANAS_FOOD).add(
                Blocks.HANGING_ROOTS.asItem(),
                Items.SWEET_BERRIES
        );

        this.tag(UP2ItemTags.TELECREX_FOOD).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(UP2ItemTags.UNICORN_FOOD).add(
                Items.CAKE
        );

        this.copy(UP2BlockTags.GINKGO_LOGS, UP2ItemTags.GINKGO_LOGS);
        this.copy(UP2BlockTags.LEPIDODENDRON_LOGS, UP2ItemTags.LEPIDODENDRON_LOGS);

        // minecraft
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        this.copy(Tags.Blocks.FENCE_GATES, Tags.Items.FENCE_GATES);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS);

        this.tag(ItemTags.SIGNS).add(
                GINKGO_SIGN.get(),
                LEPIDODENDRON_SIGN.get()
        );

        this.tag(ItemTags.HANGING_SIGNS).add(
                GINKGO_HANGING_SIGN.get(),
                LEPIDODENDRON_HANGING_SIGN.get()
        );

        this.tag(ItemTags.BOATS).add(
                GINKGO_BOAT.get(),
                LEPIDODENDRON_BOAT.get()
        );

        this.tag(ItemTags.CHEST_BOATS).add(
                GINKGO_CHEST_BOAT.get(),
                LEPIDODENDRON_CHEST_BOAT.get()
        );

        this.tag(ItemTags.MUSIC_DISCS).add(
                DOOMSURF_DISC.get(),
                MEGALANIA_DISC.get(),
                TARIFYING_DISC.get()
        );

        // forge
        this.tag(UP2ItemTags.FRUITS).addTag(UP2ItemTags.FRUITS_GINKGO);
        this.tag(UP2ItemTags.FRUITS_GINKGO).add(GINKGO_FRUIT.get());
    }
}
