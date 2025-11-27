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
    }

    public static void registerCompostables() {
        registerCompostable(UP2Items.GINKGO_FRUIT.get(), 0.65F);

        registerCompostable(UP2Blocks.CALAMOPHYTON.get(), 0.65F);
        registerCompostable(UP2Blocks.CLADOPHLEBIS.get(), 0.65F);
        registerCompostable(UP2Blocks.COOKSONIA.get(), 0.65F);
        registerCompostable(UP2Blocks.HORSETAIL.get(), 0.65F);
        registerCompostable(UP2Blocks.LARGE_HORSETAIL.get(), 0.65F);
        registerCompostable(UP2Blocks.QUILLWORT.get(), 0.65F);
        registerCompostable(UP2Blocks.LEEFRUCTUS.get(), 0.65F);
        registerCompostable(UP2Blocks.RAIGUENRAYUN.get(), 0.65F);
        registerCompostable(UP2Blocks.RHYNIA.get(), 0.65F);

        registerCompostable(UP2Blocks.GINKGO_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.GOLDEN_GINKGO_LEAVES.get(), 0.3F);
        registerCompostable(UP2Blocks.GINKGO_SAPLING.get(), 0.3F);
        registerCompostable(UP2Blocks.GOLDEN_GINKGO_SAPLING.get(), 0.3F);

        registerCompostable(UP2Blocks.LEPIDODENDRON_LEAVES.get(), 0.3F);
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
    }

    public static void registerDispenserBehaviours() {
        registerDinosaurEggDispenserBehaviour(UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS_EGG::get);
        registerDinosaurEggDispenserBehaviour(UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS_EGG::get);
        registerDinosaurEggDispenserBehaviour(UP2Items.TELECREX_EGG, UP2Entities.TELECREX_EGG::get);
    }

    public static void registerDinosaurEggDispenserBehaviour(Supplier<Item> itemSupplier, Supplier<EntityType<? extends ThrowableEgg>> entityTypeSupplier) {
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
        signs.add(UP2Blocks.GINKGO_SIGN.get());
        signs.add(UP2Blocks.GINKGO_WALL_SIGN.get());
        signs.add(UP2Blocks.LEPIDODENDRON_SIGN.get());
        signs.add(UP2Blocks.LEPIDODENDRON_WALL_SIGN.get());
        BlockEntityType.SIGN.validBlocks = signs.build();
    }

    public static void registerHangingSigns() {
        ImmutableSet.Builder<Block> hangingSigns = new ImmutableSet.Builder<>();
        hangingSigns.addAll(BlockEntityType.HANGING_SIGN.validBlocks);
        hangingSigns.add(UP2Blocks.GINKGO_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.GINKGO_WALL_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.LEPIDODENDRON_HANGING_SIGN.get());
        hangingSigns.add(UP2Blocks.LEPIDODENDRON_WALL_HANGING_SIGN.get());
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
