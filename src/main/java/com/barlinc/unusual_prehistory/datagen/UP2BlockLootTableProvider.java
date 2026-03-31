package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.blocks.egg.TallEggBlock;
import com.barlinc.unusual_prehistory.blocks.plant.ThreeTallPlantBlock;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.barlinc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2BlockLootTableProvider extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new HashSet<>();

    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    private static final float[] LEAVES_SAPLING_CHANCES = new float[] {0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    public UP2BlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        super.add(block, builder);
        this.knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        this.dropSelf(ASPHALT.get());

        this.dropSelf(PALEOSTONE.get());
        this.dropSelf(PALEOSTONE_STAIRS.get());
        this.add(PALEOSTONE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(MESONITE.get());
        this.dropSelf(MESONITE_STAIRS.get());
        this.add(MESONITE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(FLORALITE.get());
        this.dropSelf(FLORALITE_STAIRS.get());
        this.add(FLORALITE_SLAB.get(), this::createSlabItemTable);

        this.add(FOSSILIZED_BONE_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
        this.add(FOSSILIZED_BONE_BARK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
        this.add(FOSSILIZED_BONE_VERTEBRA.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
        this.add(FOSSILIZED_BONE_ROD.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
        this.add(FOSSILIZED_BONE_SPIKE.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
        this.add(FOSSILIZED_BONE_ROW.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));

        this.dropSelf(FOSSILIZED_SKULL.get());
        this.dropSelf(FOSSILIZED_SKULL_LANTERN.get());
        this.dropSelf(FOSSILIZED_SKULL_SOUL_LANTERN.get());

        this.dropSelf(COBBLED_FOSSILIZED_BONE.get());
        this.dropSelf(COBBLED_FOSSILIZED_BONE_STAIRS.get());
        this.add(COBBLED_FOSSILIZED_BONE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(PETRIFIED_BUSH.get());
        this.dropSelf(PETRIFIED_LOG.get());
        this.dropSelf(PETRIFIED_WOOD.get());
        this.dropSelf(POLISHED_PETRIFIED_WOOD.get());
        this.dropSelf(POLISHED_PETRIFIED_WOOD_STAIRS.get());
        this.add(POLISHED_PETRIFIED_WOOD_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get());
        this.dropSelf(POLISHED_PETRIFIED_WOOD_BUTTON.get());

        this.dropSelf(TRANSMOGRIFIER.get());

        this.dropSelf(REINFORCED_GLASS.get());
        this.dropSelf(TINTED_REINFORCED_GLASS.get());
        this.dropSelf(WHITE_REINFORCED_GLASS.get());
        this.dropSelf(LIGHT_GRAY_REINFORCED_GLASS.get());
        this.dropSelf(GRAY_REINFORCED_GLASS.get());
        this.dropSelf(BLACK_REINFORCED_GLASS.get());
        this.dropSelf(BROWN_REINFORCED_GLASS.get());
        this.dropSelf(RED_REINFORCED_GLASS.get());
        this.dropSelf(ORANGE_REINFORCED_GLASS.get());
        this.dropSelf(YELLOW_REINFORCED_GLASS.get());
        this.dropSelf(LIME_REINFORCED_GLASS.get());
        this.dropSelf(GREEN_REINFORCED_GLASS.get());
        this.dropSelf(CYAN_REINFORCED_GLASS.get());
        this.dropSelf(LIGHT_BLUE_REINFORCED_GLASS.get());
        this.dropSelf(BLUE_REINFORCED_GLASS.get());
        this.dropSelf(PURPLE_REINFORCED_GLASS.get());
        this.dropSelf(MAGENTA_REINFORCED_GLASS.get());
        this.dropSelf(PINK_REINFORCED_GLASS.get());

        // Update 1
        this.dropSelf(CARNOTAURUS_EGG.get());
        this.dropSelf(DIPLOCAULUS_EGGS.get());
        this.dropSelf(DUNKLEOSTEUS_SAC.get());
        this.dropSelf(JAWLESS_FISH_ROE.get());
        this.dropSelf(KENTROSAURUS_EGG.get());
        this.dropSelf(KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
        this.dropSelf(MAJUNGASAURUS_EGG.get());
        this.dropSelf(MEGALANIA_EGG.get());
        this.dropSelf(STETHACANTHUS_SAC.get());

        // Update 2
        this.dropSelf(ONCHOPRISTIS_SAC.get());

        // Update 3
        this.dropSelf(TARTUOSTEUS_ROE.get());

        // Update 4
        this.add(BRACHIOSAURUS_EGG.get(), (block) -> this.createSinglePropConditionTable(block, TallEggBlock.HALF, DoubleBlockHalf.LOWER));
        this.dropSelf(COELACANTHUS_ROE.get());
        this.dropSelf(HIBBERTOPTERUS_EGGS.get());
        this.dropSelf(KAPROSUCHUS_EGG.get());
        this.dropSelf(LOBE_FINNED_FISH_ROE.get());
        this.dropSelf(LYSTROSAURUS_EGG.get());
        this.dropSelf(PACHYCEPHALOSAURUS_EGG.get());
        this.dropSelf(ULUGHBEGSAURUS_EGG.get());

        // Update 5
        this.dropSelf(AEGIROCASSIS_EGGS.get());
        this.dropSelf(DESMATOSUCHUS_EGG.get());

        // Future
//        this.dropSelf(BARINASUCHUS_EGG.get());
//        this.dropSelf(MANIPULATOR_OOTHECA.get());
//        this.dropSelf(THERIZINOSAURUS_EGG.get());

        this.dropSelf(BENNETTITALES.get());
        this.dropPottedContents(POTTED_BENNETTITALES.get());

        this.dropSelf(NEOMARIOPTERIS.get());
        this.dropPottedContents(POTTED_NEOMARIOPTERIS.get());

        this.add(CALAMOPHYTON.get(), (block) -> this.createLayeredSinglePropConditionTable(block, ThreeTallPlantBlock.LAYER, 0));
        this.add(BRACHYPHYLLUM.get(), (block) -> this.createLayeredSinglePropConditionTable(block, ThreeTallPlantBlock.LAYER, 0));

        this.dropSelf(COOKSONIA.get());
        this.dropPottedContents(POTTED_COOKSONIA.get());

        this.dropSelf(CLADOPHLEBIS.get());
        this.dropPottedContents(POTTED_CLADOPHLEBIS.get());

        this.dropSelf(ARCHAEOSIGILLARIA.get());
        this.dropPottedContents(POTTED_ARCHAEOSIGILLARIA.get());

        this.dropSelf(QUILLWORT.get());
        this.dropPottedContents(POTTED_QUILLWORT.get());

        this.dropSelf(LEEFRUCTUS.get());
        this.dropPottedContents(POTTED_LEEFRUCTUS.get());

        this.add(RAIGUENRAYUN.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        this.add(TEMPSKYA.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        this.add(AETHOPHYLLUM.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        this.add(ZHANGSOLVA_BLOOM.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        this.add(DELITZSCHALA_STALK.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

        this.dropSelf(RHYNIA.get());
        this.dropPottedContents(POTTED_RHYNIA.get());

        this.dropSelf(HORSETAIL.get());
        this.dropPottedContents(POTTED_HORSETAIL.get());
        this.add(LARGE_HORSETAIL.get(), block -> doublePlantDrops(block, HORSETAIL.get()));

        this.add(MOSSY_DIRT.get(), (block) -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.dropSelf(MOSS_LAYER.get());

        this.add(GUANGDEDENDRON_SPORE.get(), this.createSingleItemTable(GUANGDEDENDRON.get(), ConstantValue.exactly(1.0F)));
        this.dropSelf(GUANGDEDENDRON.get());

        this.dropSelf(CYCAD_SEEDLING.get());
        this.dropSelf(CYCAD_STEM.get());
        this.dropSelf(CYCAD_CROWN.get());

        this.dropSelf(DRYOPHYLLUM_LOG.get());
        this.dropSelf(DRYOPHYLLUM_WOOD.get());
        this.dropSelf(STRIPPED_DRYOPHYLLUM_LOG.get());
        this.dropSelf(STRIPPED_DRYOPHYLLUM_WOOD.get());
        this.dropSelf(DRYOPHYLLUM_PLANKS.get());
        this.dropSelf(DRYOPHYLLUM_STAIRS.get());
        this.add(DRYOPHYLLUM_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(DRYOPHYLLUM_FENCE.get());
        this.dropSelf(DRYOPHYLLUM_FENCE_GATE.get());
        this.add(DRYOPHYLLUM_DOOR.get(), this::createDoorTable);
        this.dropSelf(DRYOPHYLLUM_TRAPDOOR.get());
        this.dropSelf(DRYOPHYLLUM_PRESSURE_PLATE.get());
        this.dropSelf(DRYOPHYLLUM_BUTTON.get());
        this.dropSelf(DRYOPHYLLUM_SIGN.get());
        this.dropSelf(DRYOPHYLLUM_WALL_SIGN.get());
        this.dropSelf(DRYOPHYLLUM_HANGING_SIGN.get());
        this.dropSelf(DRYOPHYLLUM_SAPLING.get());
        this.dropPottedContents(POTTED_DRYOPHYLLUM_SAPLING.get());

        this.add(DRYOPHYLLUM_LEAVES.get(), (block) -> createGinkgoLeavesDrops(block, DRYOPHYLLUM_SAPLING.get(), LEAVES_SAPLING_CHANCES));

        this.dropSelf(GINKGO_LOG.get());
        this.dropSelf(GINKGO_WOOD.get());
        this.dropSelf(STRIPPED_GINKGO_LOG.get());
        this.dropSelf(STRIPPED_GINKGO_WOOD.get());
        this.dropSelf(GINKGO_PLANKS.get());
        this.dropSelf(GINKGO_STAIRS.get());
        this.add(GINKGO_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(GINKGO_FENCE.get());
        this.dropSelf(GINKGO_FENCE_GATE.get());
        this.add(GINKGO_DOOR.get(), this::createDoorTable);
        this.dropSelf(GINKGO_TRAPDOOR.get());
        this.dropSelf(GINKGO_PRESSURE_PLATE.get());
        this.dropSelf(GINKGO_BUTTON.get());
        this.dropSelf(GINKGO_SIGN.get());
        this.dropSelf(GINKGO_WALL_SIGN.get());
        this.dropSelf(GINKGO_HANGING_SIGN.get());
        this.dropSelf(GINKGO_SAPLING.get());
        this.dropPottedContents(POTTED_GINKGO_SAPLING.get());
        this.dropSelf(GOLDEN_GINKGO_SAPLING.get());
        this.dropPottedContents(POTTED_GOLDEN_GINKGO_SAPLING.get());

        this.add(GOLDEN_GINKGO_LEAVES.get(), (block) -> createGinkgoLeavesDrops(block, GOLDEN_GINKGO_SAPLING.get(), LEAVES_SAPLING_CHANCES));

        this.dropSelf(LEPIDODENDRON_LOG.get());
        this.dropSelf(LEPIDODENDRON_WOOD.get());
        this.dropSelf(MOSSY_LEPIDODENDRON_LOG.get());
        this.dropSelf(MOSSY_LEPIDODENDRON_WOOD.get());
        this.dropSelf(STRIPPED_LEPIDODENDRON_LOG.get());
        this.dropSelf(STRIPPED_LEPIDODENDRON_WOOD.get());
        this.dropSelf(LEPIDODENDRON_PLANKS.get());
        this.dropSelf(LEPIDODENDRON_STAIRS.get());
        this.add(LEPIDODENDRON_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(LEPIDODENDRON_FENCE.get());
        this.dropSelf(LEPIDODENDRON_FENCE_GATE.get());
        this.add(LEPIDODENDRON_DOOR.get(), this::createDoorTable);
        this.dropSelf(LEPIDODENDRON_TRAPDOOR.get());
        this.dropSelf(LEPIDODENDRON_PRESSURE_PLATE.get());
        this.dropSelf(LEPIDODENDRON_BUTTON.get());
        this.dropSelf(LEPIDODENDRON_SIGN.get());
        this.dropSelf(LEPIDODENDRON_WALL_SIGN.get());
        this.dropSelf(LEPIDODENDRON_HANGING_SIGN.get());
        this.dropSelf(LEPIDODENDRON_CONE.get());

        this.add(LEPIDODENDRON_LEAVES.get(), this::createLepidodendronLeavesDrops);
        this.add(HANGING_LEPIDODENDRON_LEAVES.get(), this::createLepidodendronLeavesDrops);

        this.dropSelf(METASEQUOIA_LOG.get());
        this.dropSelf(METASEQUOIA_WOOD.get());
        this.dropSelf(STRIPPED_METASEQUOIA_LOG.get());
        this.dropSelf(STRIPPED_METASEQUOIA_WOOD.get());
        this.dropSelf(METASEQUOIA_PLANKS.get());
        this.dropSelf(METASEQUOIA_STAIRS.get());
        this.add(METASEQUOIA_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(METASEQUOIA_FENCE.get());
        this.dropSelf(METASEQUOIA_FENCE_GATE.get());
        this.add(METASEQUOIA_DOOR.get(), this::createDoorTable);
        this.dropSelf(METASEQUOIA_TRAPDOOR.get());
        this.dropSelf(METASEQUOIA_PRESSURE_PLATE.get());
        this.dropSelf(METASEQUOIA_BUTTON.get());
        this.dropSelf(METASEQUOIA_SIGN.get());
        this.dropSelf(METASEQUOIA_WALL_SIGN.get());
        this.dropSelf(METASEQUOIA_HANGING_SIGN.get());
        this.dropSelf(METASEQUOIA_SAPLING.get());
        this.dropPottedContents(POTTED_METASEQUOIA_SAPLING.get());

        this.add(METASEQUOIA_LEAVES.get(), (block) -> createGinkgoLeavesDrops(block, METASEQUOIA_SAPLING.get(), LEAVES_SAPLING_CHANCES));

        this.dropSelf(PROTOTAXITES_NUB.get());
        this.dropSelf(PROTOTAXITES_CLUSTER.get());
        this.dropSelf(PROTOTAXITES.get());
        this.add(LARGE_PROTOTAXITES_NUB.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

    protected static LootTable.Builder doublePlantDrops(Block large, Block big) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(big).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    protected LootTable.Builder createLayeredSinglePropConditionTable(Block block, IntegerProperty property, int value) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }

    protected LootTable.Builder createLepidodendronLeavesDrops(Block leaves) {
        return createSilkTouchOrShearsDispatchTable(leaves, this.applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, LEAVES_STICK_CHANCES)));
    }

    protected LootTable.Builder createGinkgoLeavesDrops(Block leaves, Block sapling, float... chances) {
        return this.createLeavesDrops(leaves, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(leaves, LootItem.lootTableItem(UP2Items.GINKGO_FRUIT.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
}
