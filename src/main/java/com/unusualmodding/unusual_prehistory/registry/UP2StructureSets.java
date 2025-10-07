package com.unusualmodding.unusual_prehistory.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.Optional;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2StructureSets {
    //Example
    //public static final ResourceKey<StructureSet> KISMET_STRUCTURE = registerKey("kismet_structure");
    private static ResourceKey<StructureSet> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, modPrefix(name));
    }

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        HolderGetter<StructureSet> structureSetHolderGetter = context.lookup(Registries.STRUCTURE_SET);



        //context.register(KISMET_STRUCTURE, new StructureSet(structures.getOrThrow(KStructuresData.KISMET_STRUCTURE),
        //        new RandomSpreadStructurePlacement(
        //                Vec3i.ZERO,
        //                StructurePlacement.FrequencyReductionMethod.DEFAULT,
        //                1.0F,
        //                1272244448, //this needs to be different for each one
        //                Optional.empty(),
        //                15,
        //                10, //this needs to be lower than the one above
        //                RandomSpreadType.LINEAR
        //        )));
    }
}
