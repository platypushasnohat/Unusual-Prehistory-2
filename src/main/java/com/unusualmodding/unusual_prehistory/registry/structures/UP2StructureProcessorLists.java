package com.unusualmodding.unusual_prehistory.registry.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2LootTables;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import com.unusualmodding.unusual_prehistory.worldgen.structure.processor.IfStructureProcessor;
import com.unusualmodding.unusual_prehistory.worldgen.structure.processor.MarkerProcessor;
import com.unusualmodding.unusual_prehistory.worldgen.structure.rule_test.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;

import java.util.List;
import java.util.Optional;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;

public class UP2StructureProcessorLists {
    public static final ResourceKey<StructureProcessorList> REPLACE_FOSSIL = createKey("fossil/replace_fossil");
    public static final ResourceKey<StructureProcessorList> HANDLE_FOSSIL_MARKER = createKey("fossil/handle_fossil_marker");

    public static void bootstrap(BootstapContext<StructureProcessorList> ctx) {
        // -- Helpers
        final StructureProcessor toMoss =
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS), // vanilla RuleTest OK inside RuleProcessor
                                AlwaysTrueTest.INSTANCE,
                                PosAlwaysTrueTest.INSTANCE,
                                Blocks.MOSS_BLOCK.defaultBlockState()
                        )
                ));

        final StructureProcessor toDripstone =
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS),
                                AlwaysTrueTest.INSTANCE,
                                PosAlwaysTrueTest.INSTANCE,
                                Blocks.DRIPSTONE_BLOCK.defaultBlockState()
                        )
                ));

        // Fossil with loot (surface)
        final StructureProcessor toFossilWithLoot =
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS),
                                AlwaysTrueTest.INSTANCE,
                                PosAlwaysTrueTest.INSTANCE,
                                UP2Blocks.FOSSIL.get().defaultBlockState(),
                                new AppendLoot(UP2LootTables.FOSSIL)
                        )
                ));

        // Deepslate fossil with loot (below Y<0)
        final StructureProcessor toDeepslateFossilWithLoot =
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS),
                                AlwaysTrueTest.INSTANCE,
                                PosAlwaysTrueTest.INSTANCE,
                                UP2Blocks.DEEPSLATE_FOSSIL.get().defaultBlockState(),
                                new AppendLoot(UP2LootTables.DEEPSLATE_FOSSIL)
                        )
                ));

        // -- Rules

        // If fossilized bone AND biome == LUSH_CAVES AND chance < 0.1 -> moss block
        StructureProcessor lushToMoss = new IfStructureProcessor(
                new AndRuleTest(ImmutableList.of(
                        new OrRuleTest(ImmutableList.of( // (use either tag-match here or skip if you only want biome-based)
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS)
                        )),
                        new BiomeRuleTest(Either.right(Biomes.LUSH_CAVES)),
                        new ChanceRuleTest(0.1f, ChanceRuleTest.ComparisonOperator.LESS_THAN)
                )),
                toMoss,
                Optional.empty()
        );

        // If fossilized bone AND biome == DRIPSTONE_CAVES AND chance < 0.1 -> dripstone block
        StructureProcessor dripstoneToDripstone = new IfStructureProcessor(
                new AndRuleTest(ImmutableList.of(
                        new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS),
                        new BiomeRuleTest(Either.right(Biomes.DRIPSTONE_CAVES)),
                        new ChanceRuleTest(0.1f, ChanceRuleTest.ComparisonOperator.LESS_THAN)
                )),
                toDripstone,
                Optional.empty()
        );

        // If Y<0 -> (if fossilized bone AND chance < 0.08) make DEEPSLATE_FOSSIL + loot
        // else     (if fossilized bone AND chance < 0.08) make FOSSIL + loot
        StructureProcessor ySplitFossil = new IfStructureProcessor(
                new PositionRuleTest(/*x*/Optional.empty(), /*y*/Optional.of(0F), /*z*/Optional.empty(), /*radius*/Optional.empty(),
                        PositionRuleTest.ComparisonOperator.LESS_THAN), // y < 0 ?
                // THEN (Y<0): gate fossilized + chance
                new IfStructureProcessor(
                        new AndRuleTest(ImmutableList.of(
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS),
                                new ChanceRuleTest(0.08f, ChanceRuleTest.ComparisonOperator.LESS_THAN)
                        )),
                        toDeepslateFossilWithLoot,
                        Optional.empty()
                ),
                // ELSE (Y>=0): gate fossilized + chance
                Optional.of(new IfStructureProcessor(
                        new AndRuleTest(ImmutableList.of(
                                new TagMatchTest(UP2BlockTags.FOSSILIZED_BONE_BLOCKS),
                                new ChanceRuleTest(0.08f, ChanceRuleTest.ComparisonOperator.LESS_THAN)
                        )),
                        toFossilWithLoot,
                        Optional.empty()
                ))
        );

        StructureProcessor fossilMarker = new MarkerProcessor(
                "fossil",                              // marker name
                Optional.of(new PositionRuleTest(       // rule: Y > 0
                        Optional.empty(),
                        Optional.of(0f),
                        Optional.empty(),
                        Optional.empty(),
                        PositionRuleTest.ComparisonOperator.GREATER_THAN)),
                UP2Blocks.FOSSIL.get(),                // normal block
                Optional.of(UP2Blocks.DEEPSLATE_FOSSIL.get()), // deep variant
                Optional.of(UP2LootTables.FOSSIL),     // normal loot
                Optional.of(UP2LootTables.DEEPSLATE_FOSSIL),   // deep loot
                1.0f,                                  // chance
                0);                       // deep_y threshold


        register(ctx,
                REPLACE_FOSSIL,
                ImmutableList.of(
                        lushToMoss,
                        dripstoneToDripstone,
                        ySplitFossil,
                        fossilMarker
                )
        );


    }



    private static ResourceKey<StructureProcessorList> createKey(String pName) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, modPrefix(pName));
    }


    private static void register(
            BootstapContext<StructureProcessorList> pContext, ResourceKey<StructureProcessorList> pKey, List<StructureProcessor> pProcessors
    ) {
        pContext.register(pKey, new StructureProcessorList(pProcessors));
    }
}
