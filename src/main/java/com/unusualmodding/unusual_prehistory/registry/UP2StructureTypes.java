package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.worldgen.structure.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2StructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<StructureType<FossilStructure>> FOSSIL = STRUCTURE_TYPES.register("fossil", () -> () -> (FossilStructure.CODEC));
    public static final RegistryObject<StructureType<PetrifiedTreeStructure>> PETRIFIED_TREE = STRUCTURE_TYPES.register("petrified_tree", () -> () -> (PetrifiedTreeStructure.CODEC));

}
