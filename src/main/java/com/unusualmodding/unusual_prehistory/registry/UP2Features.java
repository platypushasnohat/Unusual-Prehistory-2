package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.worldgen.foliage_placer.GinkgoFoliagePlacer;
import com.unusualmodding.unusual_prehistory.worldgen.foliage_placer.LepidodendronFoliagePlacer;
import com.unusualmodding.unusual_prehistory.worldgen.tree_decorator.HangingLepidodendronLeavesDecorator;
import com.unusualmodding.unusual_prehistory.worldgen.tree_decorator.LepidodendronGrowthsDecorator;
import com.unusualmodding.unusual_prehistory.worldgen.trunk_placer.LepidodendronTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2Features {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<GinkgoFoliagePlacer>> GINKGO_FOLIAGE = FOLIAGE_PLACERS.register("ginkgo_foliage_placer", () -> new FoliagePlacerType<>(GinkgoFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<LepidodendronFoliagePlacer>> LEPIDODENDRON_FOLIAGE = FOLIAGE_PLACERS.register("lepidodendron_foliage_placer", () -> new FoliagePlacerType<>(LepidodendronFoliagePlacer.CODEC));

    public static final RegistryObject<TrunkPlacerType<LepidodendronTrunkPlacer>> LEPIDODENDRON_TRUNK = TRUNK_PLACERS.register("lepidodendron_trunk_placer", () -> new TrunkPlacerType<>(LepidodendronTrunkPlacer.CODEC));

    public static final RegistryObject<TreeDecoratorType<?>> HANGING_LEPIDODENDRON_LEAVES = TREE_DECORATORS.register("hanging_lepidodendron_leaves", () -> new TreeDecoratorType<>(HangingLepidodendronLeavesDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<?>> LEPIDODENDRON_GROWTHS = TREE_DECORATORS.register("lepidodendron_growths", () -> new TreeDecoratorType<>(LepidodendronGrowthsDecorator.CODEC));


    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
        TRUNK_PLACERS.register(eventBus);
        TREE_DECORATORS.register(eventBus);
    }
}
