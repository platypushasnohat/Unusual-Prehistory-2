package com.unusualmodding.unusual_prehistory.compat;

import com.unusualmodding.unusual_prehistory.blocks.UP2Blocks;
import com.unusualmodding.unusual_prehistory.items.UP2Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;

public class UP2Compat {

    public static void registerCompat() {
        registerCompostables();
        registerFlammables();
    }

    public static void registerCompostables() {
        registerCompostable(UP2Items.GINKGO_FRUIT.get(), 0.65F);

        registerCompostable(UP2Blocks.ARCHAEFRUCTUS.get(), 0.65F);
        registerCompostable(UP2Blocks.ARCHAEOSIGILLARIA.get(), 0.65F);
        registerCompostable(UP2Blocks.CALAMOPHYTON.get(), 0.65F);
        registerCompostable(UP2Blocks.CLADOPHLEBIS.get(), 0.65F);
        registerCompostable(UP2Blocks.COOKSONIA.get(), 0.65F);
        registerCompostable(UP2Blocks.HORSETAIL.get(), 0.65F);
        registerCompostable(UP2Blocks.LARGE_HORSETAIL.get(), 0.65F);
        registerCompostable(UP2Blocks.ISOETES.get(), 0.65F);
        registerCompostable(UP2Blocks.LEEFRUCTUS.get(), 0.65F);
        registerCompostable(UP2Blocks.NELUMBITES.get(), 0.65F);
        registerCompostable(UP2Blocks.RAIGUENRAYUN.get(), 0.65F);
        registerCompostable(UP2Blocks.RHYNIA.get(), 0.65F);
        registerCompostable(UP2Blocks.SARRACENIA.get(), 0.65F);
        registerCompostable(UP2Blocks.TALL_SARRACENIA.get(), 0.65F);
        registerCompostable(UP2Blocks.QUEREUXIA.get(), 0.65F);
        registerCompostable(UP2Blocks.QUEREUXIA_CLOVERS.get(), 0.65F);

        registerCompostable(UP2Blocks.GINKGO_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.GOLDEN_GINKGO_LEAVES.get(), 0.3F);
    }

    public static void registerFlammables() {
        registerFlammable(UP2Blocks.ARCHAEOSIGILLARIA.get(), 60, 100);
        registerFlammable(UP2Blocks.CALAMOPHYTON.get(), 60, 100);
        registerFlammable(UP2Blocks.CLADOPHLEBIS.get(), 60, 100);
        registerFlammable(UP2Blocks.COOKSONIA.get(), 60, 100);
        registerFlammable(UP2Blocks.HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.LARGE_HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.ISOETES.get(), 60, 100);
        registerFlammable(UP2Blocks.LEEFRUCTUS.get(), 60, 100);
        registerFlammable(UP2Blocks.RAIGUENRAYUN.get(), 60, 100);
        registerFlammable(UP2Blocks.RHYNIA.get(), 60, 100);
        registerFlammable(UP2Blocks.SARRACENIA.get(), 60, 100);
        registerFlammable(UP2Blocks.TALL_SARRACENIA.get(), 60, 100);

        registerFlammable(UP2Blocks.GINKGO_LOG.get(), 5, 3);
        registerFlammable(UP2Blocks.GINKGO_WOOD.get(), 5, 3);
        registerFlammable(UP2Blocks.STRIPPED_GINKGO_LOG.get(), 5, 3);
        registerFlammable(UP2Blocks.STRIPPED_GINKGO_WOOD.get(), 5, 3);
        registerFlammable(UP2Blocks.GINKGO_PLANKS.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_SLAB.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_STAIRS.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_FENCE.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_FENCE_GATE.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_LEAVES.get(), 30, 60);
        registerFlammable(UP2Blocks.GINKGO_LEAVES.get(), 30, 60);
    }

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static void registerFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, igniteChance, burnChance);
    }
}
