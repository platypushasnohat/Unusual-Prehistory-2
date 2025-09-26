package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.worldgen.feature.tree_decorator.HangingLepidodendronLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2TreeDecorators {

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<?>> HANGING_LEPIDODENDRON_LEAVES = TREE_DECORATORS.register("hanging_lepidodendron_leaves", () -> new TreeDecoratorType<>(HangingLepidodendronLeavesDecorator.CODEC));
}
