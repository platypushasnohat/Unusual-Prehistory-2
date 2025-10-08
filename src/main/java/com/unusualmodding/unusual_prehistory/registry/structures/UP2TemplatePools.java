package com.unusualmodding.unusual_prehistory.registry.structures;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2TemplatePools {

    //Example
    //public static final ResourceKey<StructureTemplatePool> KISMET_STRUCTURE = createKey("kismet_structure/start_pool");


    public static ResourceKey<StructureTemplatePool> createKey(String pName) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, modPrefix(pName));
    }

    public static void bootstrap(BootstapContext<StructureTemplatePool> pContext) {
        HolderGetter<StructureTemplatePool> holdergetter = pContext.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> holdergetter2 = pContext.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> empty = holdergetter.getOrThrow(Pools.EMPTY);

        //Example
       //pContext.register(KISMET_STRUCTURE, new StructureTemplatePool(empty,
       //        ImmutableList.of(
        //The id of the nbt and the chance to select it
       //                Pair.of(StructurePoolElement.single(modPrefix("kismet_structure/kismet_structure_1").toString(), holdergetter2.getOrThrow(KProcessorLists.KISMET_STRUCTURE)), 20),
       //                Pair.of(StructurePoolElement.single(modPrefix("kismet_structure/kismet_structure_2").toString(), holdergetter2.getOrThrow(KProcessorLists.KISMET_STRUCTURE)), 20)
       //        ), StructureTemplatePool.Projection.RIGID));


    }
}
