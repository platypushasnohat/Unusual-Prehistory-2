package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.AbandonedLabProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2StructureProcessorTypes {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<AbandonedLabProcessor>> ABANDONED_LAB_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("abandoned_lab_processor", () -> () -> AbandonedLabProcessor.CODEC);

}
