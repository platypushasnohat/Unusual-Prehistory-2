package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.ginkgo.GinkgoFoliagePlacer;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.lepidodendron.HangingLepidodendronLeavesDecorator;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.lepidodendron.LepidodendronFoliagePlacer;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.lepidodendron.LepidodendronTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UP2Trees {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, UnusualPrehistory2.MOD_ID);

    // Trunk placers
    public static final RegistryObject<TrunkPlacerType<LepidodendronTrunkPlacer>> LEPIDODENDRON_TRUNK = TRUNK_PLACERS.register("lepidodendron_trunk_placer", () -> new TrunkPlacerType<>(LepidodendronTrunkPlacer.CODEC));

    // Foliage placers
    public static final RegistryObject<FoliagePlacerType<GinkgoFoliagePlacer>> GINKGO_FOLIAGE = FOLIAGE_PLACERS.register("ginkgo_foliage_placer", () -> new FoliagePlacerType<>(GinkgoFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<LepidodendronFoliagePlacer>> LEPIDODENDRON_FOLIAGE = FOLIAGE_PLACERS.register("lepidodendron_foliage_placer", () -> new FoliagePlacerType<>(LepidodendronFoliagePlacer.CODEC));

    // Tree decorators
    public static final RegistryObject<TreeDecoratorType<?>> HANGING_LEPIDODENDRON_LEAVES = TREE_DECORATORS.register("hanging_lepidodendron_leaves", () -> new TreeDecoratorType<>(HangingLepidodendronLeavesDecorator.CODEC));

}
