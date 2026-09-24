package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.structure.AbandonedLabStructure;
import com.barl_inc.unusual_prehistory.worldgen.structure.CliffFossilStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2StructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<AbandonedLabStructure>> ABANDONED_LAB = STRUCTURE_TYPES.register("abandoned_lab", () -> () -> AbandonedLabStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<CliffFossilStructure>> CLIFF_FOSSIL = STRUCTURE_TYPES.register("cliff_fossil", () -> () -> CliffFossilStructure.CODEC);

}
