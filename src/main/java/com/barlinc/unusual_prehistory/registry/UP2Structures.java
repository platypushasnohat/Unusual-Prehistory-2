package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.structure.BadlandsFossilStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2Structures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<BadlandsFossilStructure>> BADLANDS_FOSSIL = STRUCTURE_TYPES.register("badlands_fossil", () -> () -> BadlandsFossilStructure.CODEC);

}
