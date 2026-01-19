package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.structure.processor.TreeStructureProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2StructureProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<StructureProcessorType<TreeStructureProcessor>> TREE = PROCESSOR_TYPES.register("tree", () -> () -> TreeStructureProcessor.CODEC);

}
