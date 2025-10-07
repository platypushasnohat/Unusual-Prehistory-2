package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.worldgen.structure.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2StructureProcessorTypes {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPE = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<StructureProcessorType<FossilProcessor>> FOSSIL = STRUCTURE_PROCESSOR_TYPE.register("fossil", () -> () -> FossilProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<PetrifiedTreeProcessor>> PETRIFIED_TREE = STRUCTURE_PROCESSOR_TYPE.register("petrified_tree", () -> () -> PetrifiedTreeProcessor.CODEC);

}
