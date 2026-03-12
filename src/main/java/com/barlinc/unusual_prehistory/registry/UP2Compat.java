package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class UP2Compat {

    public static void registerCompat() {
        registerCompostables();
        registerFlammables();
        registerDispenserBehaviours();
        registerSigns();
        registerHangingSigns();
        UP2CauldronInteractions.registerCauldronInteractions();
    }

    public static void registerCompostables() {
        registerCompostable(UP2Items.GINKGO_FRUIT.get(), 0.5F);

        registerCompostable(UP2Blocks.COOKSONIA.get(), 0.65F);
        registerCompostable(UP2Blocks.LEEFRUCTUS.get(), 0.65F);
        registerCompostable(UP2Blocks.RAIGUENRAYUN.get(), 0.65F);
        registerCompostable(UP2Blocks.PROTOTAXITES_CLUSTER.get(), 0.65F);
        registerCompostable(UP2Blocks.PROTOTAXITES.get(), 0.65F);
        registerCompostable(UP2Blocks.PROTOTAXITES_NUB.get(), 0.65F);
        registerCompostable(UP2Blocks.LARGE_PROTOTAXITES_NUB.get(), 0.65F);
        registerCompostable(UP2Blocks.AETHOPHYLLUM.get(), 0.65F);

        registerCompostable(UP2Blocks.DRYOPHYLLUM_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.DRYOPHYLLUM_SAPLING.get(), 0.3F);

        registerCompostable(UP2Blocks.GINKGO_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.GOLDEN_GINKGO_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.GINKGO_SAPLING.get(), 0.3F);
        registerCompostable(UP2Blocks.GOLDEN_GINKGO_SAPLING.get(), 0.3F);

        registerCompostable(UP2Blocks.LEPIDODENDRON_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.LEPIDODENDRON_CONE.get(), 0.3F);

        registerCompostable(UP2Blocks.METASEQUOIA_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.METASEQUOIA_SAPLING.get(), 0.3F);

        registerCompostable(UP2Blocks.BENNETTITALES.get(), 0.3F);
        registerCompostable(UP2Blocks.NEOMARIOPTERIS.get(), 0.3F);
        registerCompostable(UP2Blocks.TEMPSKYA.get(), 0.3F);
        registerCompostable(UP2Blocks.BRACHYPHYLLUM.get(), 0.3F);
        registerCompostable(UP2Blocks.RHYNIA.get(), 0.3F);
        registerCompostable(UP2Blocks.HORSETAIL.get(), 0.3F);
        registerCompostable(UP2Blocks.LARGE_HORSETAIL.get(), 0.3F);
        registerCompostable(UP2Blocks.QUILLWORT.get(), 0.3F);
        registerCompostable(UP2Blocks.CALAMOPHYTON.get(), 0.3F);
        registerCompostable(UP2Blocks.CLADOPHLEBIS.get(), 0.3F);
        registerCompostable(UP2Blocks.ARCHAEOSIGILLARIA.get(), 0.3F);

        registerCompostable(UP2Blocks.CYCAD_SEEDLING.get(), 0.3F);
        registerCompostable(UP2Blocks.CYCAD_CROWN.get(), 0.5F);

        registerCompostable(UP2Blocks.GUANGDEDENDRON_SPORE.get(), 0.3F);
        registerCompostable(UP2Blocks.GUANGDEDENDRON_CROWN.get(), 0.5F);
    }

    public static void registerFlammables() {
        registerFlammable(UP2Blocks.CALAMOPHYTON.get(), 60, 100);
        registerFlammable(UP2Blocks.CLADOPHLEBIS.get(), 60, 100);
        registerFlammable(UP2Blocks.COOKSONIA.get(), 60, 100);
        registerFlammable(UP2Blocks.HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.LARGE_HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.QUILLWORT.get(), 60, 100);
        registerFlammable(UP2Blocks.LEEFRUCTUS.get(), 60, 100);
        registerFlammable(UP2Blocks.RAIGUENRAYUN.get(), 60, 100);
        registerFlammable(UP2Blocks.RHYNIA.get(), 60, 100);
        registerFlammable(UP2Blocks.TEMPSKYA.get(), 60, 100);
        registerFlammable(UP2Blocks.BRACHYPHYLLUM.get(), 60, 100);
        registerFlammable(UP2Blocks.NEOMARIOPTERIS.get(), 60, 100);
        registerFlammable(UP2Blocks.BENNETTITALES.get(), 60, 100);
        registerFlammable(UP2Blocks.AETHOPHYLLUM.get(), 60, 100);
        registerFlammable(UP2Blocks.ARCHAEOSIGILLARIA.get(), 60, 100);

        registerFlammable(UP2Blocks.GUANGDEDENDRON_SPORE.get(), 20, 100);
        registerFlammable(UP2Blocks.GUANGDEDENDRON_STEM.get(), 20, 100);
        registerFlammable(UP2Blocks.LUSH_GUANGDEDENDRON_STEM.get(), 20, 100);
        registerFlammable(UP2Blocks.GUANGDEDENDRON_CROWN.get(), 20, 100);

        registerFlammable(UP2Blocks.CYCAD_STEM.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_CROWN.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_SEEDLING.get(), 20, 100);

        registerFlammable(UP2Blocks.DRYOPHYLLUM_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.STRIPPED_DRYOPHYLLUM_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.STRIPPED_DRYOPHYLLUM_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_PLANKS.get(), 5, 20);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_SLAB.get(), 5, 20);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_STAIRS.get(), 5, 20);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_FENCE.get(), 5, 20);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_FENCE_GATE.get(), 5, 20);
        registerFlammable(UP2Blocks.DRYOPHYLLUM_LEAVES.get(), 30, 60);

        registerFlammable(UP2Blocks.GINKGO_LOG.get(), 5, 3);
        registerFlammable(UP2Blocks.GINKGO_WOOD.get(), 5, 3);
        registerFlammable(UP2Blocks.STRIPPED_GINKGO_LOG.get(), 5, 3);
        registerFlammable(UP2Blocks.STRIPPED_GINKGO_WOOD.get(), 5, 3);
        registerFlammable(UP2Blocks.GINKGO_PLANKS.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_SLAB.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_STAIRS.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_FENCE.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_FENCE_GATE.get(), 5, 15);
        registerFlammable(UP2Blocks.GINKGO_LEAVES.get(), 30, 50);
        registerFlammable(UP2Blocks.GOLDEN_GINKGO_LEAVES.get(), 30, 50);

        registerFlammable(UP2Blocks.LEPIDODENDRON_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.LEPIDODENDRON_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.STRIPPED_LEPIDODENDRON_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.STRIPPED_LEPIDODENDRON_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.LEPIDODENDRON_PLANKS.get(), 5, 20);
        registerFlammable(UP2Blocks.LEPIDODENDRON_SLAB.get(), 5, 20);
        registerFlammable(UP2Blocks.LEPIDODENDRON_STAIRS.get(), 5, 20);
        registerFlammable(UP2Blocks.LEPIDODENDRON_FENCE.get(), 5, 20);
        registerFlammable(UP2Blocks.LEPIDODENDRON_FENCE_GATE.get(), 5, 20);
        registerFlammable(UP2Blocks.LEPIDODENDRON_LEAVES.get(), 30, 60);

        registerFlammable(UP2Blocks.METASEQUOIA_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.METASEQUOIA_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.STRIPPED_METASEQUOIA_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.STRIPPED_METASEQUOIA_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.METASEQUOIA_PLANKS.get(), 5, 20);
        registerFlammable(UP2Blocks.METASEQUOIA_SLAB.get(), 5, 20);
        registerFlammable(UP2Blocks.METASEQUOIA_STAIRS.get(), 5, 20);
        registerFlammable(UP2Blocks.METASEQUOIA_FENCE.get(), 5, 20);
        registerFlammable(UP2Blocks.METASEQUOIA_FENCE_GATE.get(), 5, 20);
        registerFlammable(UP2Blocks.METASEQUOIA_LEAVES.get(), 30, 60);
    }

    public static void registerDispenserBehaviours() {
        registerEggDispenserBehaviour(UP2Items.DIMORPHODON_EGG, UP2Entities.DIMORPHODON_EGG::get);
        registerEggDispenserBehaviour(UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS_EGG::get);
        registerEggDispenserBehaviour(UP2Items.PSILOPTERUS_EGG, UP2Entities.PSILOPTERUS_EGG::get);
        registerEggDispenserBehaviour(UP2Items.PTERODACTYLUS_EGG, UP2Entities.PTERODACTYLUS_EGG::get);
        registerEggDispenserBehaviour(UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS_EGG::get);
        registerEggDispenserBehaviour(UP2Items.TELECREX_EGG, UP2Entities.TELECREX_EGG::get);
    }

    public static void registerEggDispenserBehaviour(Supplier<Item> itemSupplier, Supplier<EntityType<? extends ThrowableEgg>> entityTypeSupplier) {
        DispenserBlock.registerBehavior(itemSupplier.get(), new AbstractProjectileDispenseBehavior() {
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack itemStack) {
                ThrowableEgg egg = entityTypeSupplier.get().create(level);
                egg.setPos(pos.x(), pos.y(),pos.z());
                return egg;
            }
        });
    }

    public static void registerSigns() {
        ImmutableSet.Builder<Block> signs = new ImmutableSet.Builder<>();
        signs.addAll(BlockEntityType.SIGN.validBlocks);
        signs.add(UP2Blocks.DRYOPHYLLUM_SIGN.get());
        signs.add(UP2Blocks.DRYOPHYLLUM_WALL_SIGN.get());
        signs.add(UP2Blocks.GINKGO_SIGN.get());
        signs.add(UP2Blocks.GINKGO_WALL_SIGN.get());
        signs.add(UP2Blocks.LEPIDODENDRON_SIGN.get());
        signs.add(UP2Blocks.LEPIDODENDRON_WALL_SIGN.get());
        signs.add(UP2Blocks.METASEQUOIA_SIGN.get());
        signs.add(UP2Blocks.METASEQUOIA_WALL_SIGN.get());
        BlockEntityType.SIGN.validBlocks = signs.build();
    }

    public static void registerHangingSigns() {
        ImmutableSet.Builder<Block> hangingSigns = new ImmutableSet.Builder<>();
        hangingSigns.addAll(BlockEntityType.HANGING_SIGN.validBlocks);
        hangingSigns.add(UP2Blocks.DRYOPHYLLUM_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.DRYOPHYLLUM_WALL_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.GINKGO_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.GINKGO_WALL_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.LEPIDODENDRON_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.LEPIDODENDRON_WALL_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.METASEQUOIA_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.METASEQUOIA_WALL_HANGING_SIGN.get());
        BlockEntityType.HANGING_SIGN.validBlocks = hangingSigns.build();
    }

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static void registerFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, igniteChance, burnChance);
    }
}
