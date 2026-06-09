package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.structure.DesertFossilSiteStructure;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UP2Structures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final Supplier<StructureType<DesertFossilSiteStructure>> DESERT_FOSSIL_SITE = register("desert_fossil_site", DesertFossilSiteStructure.CODEC);

    private static <P extends Structure> DeferredHolder<StructureType<?>, StructureType<P>> register(String name, MapCodec<P> codec) {
        return STRUCTURE_TYPES.register(name, () -> () -> codec);
    }
}
