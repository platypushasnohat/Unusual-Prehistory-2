package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.worldgen.structure.rule_test.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UP2RuleTests {

    public static final DeferredRegister<RuleTestType<?>> RULE_TESTS = DeferredRegister.create(Registries.RULE_TEST, UnusualPrehistory2.MOD_ID);
    public static final RegistryObject<RuleTestType<BiomeRuleTest>> BIOME_RULE_TEST = RULE_TESTS.register("biome_rule_test", () -> () -> BiomeRuleTest.CODEC);

    public static final RegistryObject<RuleTestType<DimensionRuleTest>> DIMENSION_RULE_TEST = RULE_TESTS.register("dimension_rule_test", () -> () -> DimensionRuleTest.CODEC);
    public static final RegistryObject<RuleTestType<ChanceRuleTest>> CHANCE_RULE_TEST = RULE_TESTS.register("chance_rule_test", () -> () -> ChanceRuleTest.CODEC);
    public static final RegistryObject<RuleTestType<PositionRuleTest>> POS_RULE_TEST = RULE_TESTS.register("pos_rule_test", () -> () -> PositionRuleTest.CODEC);
    public static final RegistryObject<RuleTestType<AndRuleTest>> AND_RULE_TEST = RULE_TESTS.register("and_rule_test", () -> () -> AndRuleTest.CODEC);
    public static final RegistryObject<RuleTestType<OrRuleTest>> OR_RULE_TEST = RULE_TESTS.register("or_rule_test", () -> () -> OrRuleTest.CODEC);
}
