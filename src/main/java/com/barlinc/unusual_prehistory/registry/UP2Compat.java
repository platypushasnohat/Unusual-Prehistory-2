package com.barlinc.unusual_prehistory.registry;

import net.minecraft.world.level.block.*;

public class UP2Compat {

    public static void registerCompat() {
        registerFlammables();
        registerDispenserBehaviours();
        UP2CauldronInteractions.registerCauldronInteractions();
    }

    public static void registerFlammables() {
        // Update 1
        registerFlammable(UP2Blocks.CALAMOPHYTON.get(), 60, 100);
        registerFlammable(UP2Blocks.CLADOPHLEBIS.get(), 60, 100);
        registerFlammable(UP2Blocks.COOKSONIA.get(), 60, 100);
        registerFlammable(UP2Blocks.HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.LARGE_HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.QUILLWORT.get(), 60, 100);
        registerFlammable(UP2Blocks.LEEFRUCTUS.get(), 60, 100);
        registerFlammable(UP2Blocks.RAIGUENRAYUN.get(), 60, 100);
        registerFlammable(UP2Blocks.RHYNIA.get(), 60, 100);

        UP2Blocks.GINKGO.setFlammables();
        registerFlammable(UP2Blocks.GINKGO_LEAVES.get(), 30, 60);
        registerFlammable(UP2Blocks.GOLDEN_GINKGO_LEAVES.get(), 30, 60);

        UP2Blocks.LEPIDODENDRON.setFlammables();
        registerFlammable(UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.LEPIDODENDRON_LEAVES.get(), 30, 60);

        // Update 4
        registerFlammable(UP2Blocks.TEMPSKYA.get(), 60, 100);
        registerFlammable(UP2Blocks.BRACHYPHYLLUM.get(), 60, 100);
        registerFlammable(UP2Blocks.NEOMARIOPTERIS.get(), 60, 100);
        registerFlammable(UP2Blocks.BENNETTITALES.get(), 60, 100);
        registerFlammable(UP2Blocks.AETHOPHYLLUM.get(), 60, 100);
        registerFlammable(UP2Blocks.ARCHAEOSIGILLARIA.get(), 60, 100);

        registerFlammable(UP2Blocks.GUANGDEDENDRON_SPORE.get(), 20, 100);
        registerFlammable(UP2Blocks.GUANGDEDENDRON.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_STEM.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_CROWN.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_SEEDLING.get(), 20, 100);

        UP2Blocks.DRYOPHYLLUM.setFlammables();
        registerFlammable(UP2Blocks.DRYOPHYLLUM_LEAVES.get(), 30, 60);

        UP2Blocks.METASEQUOIA.setFlammables();
        registerFlammable(UP2Blocks.METASEQUOIA_LEAVES.get(), 30, 60);

        // Update 5
        registerFlammable(UP2Blocks.DELITZSCHALA_STALK.get(), 60, 100);
        registerFlammable(UP2Blocks.ZHANGSOLVA_BLOOM.get(), 60, 100);
    }

    public static void registerDispenserBehaviours() {
        // Update 1
        DispenserBlock.registerProjectileBehavior(UP2Items.DROMAEOSAURUS_EGG);
        DispenserBlock.registerProjectileBehavior(UP2Items.TALPANAS_EGG);
        DispenserBlock.registerProjectileBehavior(UP2Items.TELECREX_EGG);

        // Update 4
        DispenserBlock.registerProjectileBehavior(UP2Items.PTERODACTYLUS_EGG);

        // Update 5
        DispenserBlock.registerProjectileBehavior(UP2Items.PSILOPTERUS_EGG);
    }

    public static void registerFlammable(Block block, int encouragement, int flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, encouragement, flammability);
    }
}
