package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.blocks.CalamophytonBlock;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.unusualmodding.unusual_prehistory.registry.UP2Blocks.*;

public class UP2BlockLootTableProvider extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new HashSet<>();

    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    private static final float[] LEAVES_SAPLING_CHANCES = new float[] {0.05F, 0.0625F, 0.083333336F, 0.1F};

    public UP2BlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        this.dropSelf(ARCHAEFRUCTUS.get());

        this.dropSelf(ARCHAEOSIGILLARIA.get());
        this.dropPottedContents(POTTED_ARCHAEOSIGILLARIA.get());

        this.dropSelf(BENNETTITALES.get());
        this.dropPottedContents(POTTED_BENNETTITALES.get());

        this.add(CALAMOPHYTON.get(), block -> layeredPlantDrops(CALAMOPHYTON.get()));

        this.dropSelf(COOKSONIA.get());
        this.dropPottedContents(POTTED_COOKSONIA.get());

        this.dropSelf(CLADOPHLEBIS.get());
        this.dropPottedContents(POTTED_CLADOPHLEBIS.get());

        this.dropSelf(ISOETES.get());
        this.dropPottedContents(POTTED_ISOETES.get());

        this.dropSelf(LEEFRUCTUS.get());
        this.dropPottedContents(POTTED_LEEFRUCTUS.get());

        this.dropSelf(NELUMBITES.get());

        this.dropSelf(RHYNIA.get());
        this.dropPottedContents(POTTED_RHYNIA.get());

        this.dropSelf(QUEREUXIA.get());
        this.add(QUEREUXIA_PLANT.get(), createSingleItemTable(QUEREUXIA.get()));
        this.dropSelf(QUEREUXIA_CLOVERS.get());

        this.dropSelf(HORSETAIL.get());
        this.dropPottedContents(POTTED_HORSETAIL.get());
        this.add(LARGE_HORSETAIL.get(), block -> doublePlantDrops(block, HORSETAIL.get()));

        this.dropSelf(SARRACENIA.get());
        this.dropPottedContents(POTTED_SARRACENIA.get());
        this.add(TALL_SARRACENIA.get(), block -> doublePlantDrops(block, SARRACENIA.get()));

        this.dropSelf(PETRIFIED_ANOSTYLOSTROMA.get());
        this.dropSelf(ANOSTYLOSTROMA.get());

        this.add(CLATHRODICTYON_CORAL_BLOCK.get(), (block) -> createSingleItemTableWithSilkTouch(block, DEAD_CLATHRODICTYON_CORAL_BLOCK.get()));
        this.dropWhenSilkTouch(CLATHRODICTYON_CORAL.get());
        this.dropWhenSilkTouch(CLATHRODICTYON_CORAL_FAN.get());
        this.dropWhenSilkTouch(CLATHRODICTYON_CORAL_WALL_FAN.get());

        this.dropSelf(DEAD_CLATHRODICTYON_CORAL_BLOCK.get());
        this.dropWhenSilkTouch(DEAD_CLATHRODICTYON_CORAL.get());
        this.dropWhenSilkTouch(DEAD_CLATHRODICTYON_CORAL_FAN.get());
        this.dropWhenSilkTouch(DEAD_CLATHRODICTYON_CORAL_WALL_FAN.get());

        this.add(MOSSY_DIRT.get(), (block) -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.dropSelf(MOSS_LAYER.get());

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

        this.add(GINKGO_LEAVES.get(), (block) -> createLeavesDrops(block, Blocks.SPRUCE_SAPLING, LEAVES_SAPLING_CHANCES));
        this.add(GOLDEN_GINKGO_LEAVES.get(), (block) -> createLeavesDrops(block, Blocks.SPRUCE_SAPLING, LEAVES_SAPLING_CHANCES));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

    protected static LootTable.Builder doublePlantDrops(Block large, Block big) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(big).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(large).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    protected static LootTable.Builder layeredPlantDrops(Block block) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(block).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CalamophytonBlock.LAYER, 0))));
    }
}
