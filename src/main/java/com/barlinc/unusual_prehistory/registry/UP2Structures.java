package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.structure.UndergroundStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2Structures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final RegistryObject<StructureType<UndergroundStructure>> UNDERGROUND_STRUCTURE = STRUCTURE_TYPES.register("underground_structure", () -> () -> UndergroundStructure.CODEC);

}
