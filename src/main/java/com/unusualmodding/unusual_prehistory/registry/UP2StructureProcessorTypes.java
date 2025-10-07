package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.worldgen.structure.processor.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2StructureProcessorTypes {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPE = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<StructureProcessorType<ReplaceBlockProcessor>> REPLACE_BLOCK_PROCESSOR = STRUCTURE_PROCESSOR_TYPE.register("replace_block_processor", () -> ()-> ReplaceBlockProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<IfStructureProcessor>> IF_PROCESSOR = STRUCTURE_PROCESSOR_TYPE.register("if_processor", () -> ()-> IfStructureProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<MarkerProcessor>> MARKER_PROCESSOR = STRUCTURE_PROCESSOR_TYPE.register("marker_processor", () -> ()-> MarkerProcessor.CODEC);

}
