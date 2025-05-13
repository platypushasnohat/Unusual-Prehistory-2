package com.unusualmodding.unusual_prehistory.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class UP2LootProvider {
    public static LootTableProvider register(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(UP2BlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
