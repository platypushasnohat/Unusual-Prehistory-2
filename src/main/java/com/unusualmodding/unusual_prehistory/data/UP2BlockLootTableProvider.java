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
        this.dropSelf(FOSSILIZED_BONE_BLOCK.get());
        this.dropSelf(FOSSILIZED_BONE_BARK.get());
        this.dropSelf(FOSSILIZED_BONE_VERTEBRA.get());
        this.dropSelf(FOSSILIZED_SKULL.get());
        this.dropSelf(FOSSILIZED_SKULL_LANTERN.get());
        this.dropSelf(FOSSILIZED_SKULL_SOUL_LANTERN.get());

        this.dropSelf(COBBLED_FOSSILIZED_BONE.get());
        this.dropSelf(COBBLED_FOSSILIZED_BONE_STAIRS.get());
        this.add(COBBLED_FOSSILIZED_BONE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(FOSSILIZED_BONE_SPIKE.get());

        this.dropSelf(PETRIFIED_LOG.get());
        this.dropSelf(PETRIFIED_WOOD.get());
        this.dropSelf(POLISHED_PETRIFIED_WOOD.get());
        this.dropSelf(POLISHED_PETRIFIED_WOOD_STAIRS.get());
        this.add(POLISHED_PETRIFIED_WOOD_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(TRANSMOGRIFIER.get());

        this.dropSelf(CARNOTAURUS_EGG.get());
        this.dropSelf(KENTROSAURUS_EGG.get());
        this.dropSelf(MAJUNGASAURUS_EGG.get());
        this.dropSelf(MEGALANIA_EGG.get());
        this.dropSelf(DIPLOCAULUS_EGGS.get());
        this.dropSelf(JAWLESS_FISH_ROE.get());
        this.dropSelf(KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
        this.dropSelf(STETHACANTHUS_SAC.get());
        this.dropSelf(DUNKLEOSTEUS_SAC.get());

        this.dropSelf(BENNETTITALES.get());
        this.dropPottedContents(POTTED_BENNETTITALES.get());

        this.add(CALAMOPHYTON.get(), block -> layeredPlantDrops(CALAMOPHYTON.get()));

        this.dropSelf(COOKSONIA.get());
        this.dropPottedContents(POTTED_COOKSONIA.get());

        this.dropSelf(CLADOPHLEBIS.get());
        this.dropPottedContents(POTTED_CLADOPHLEBIS.get());

        this.dropSelf(QUILLWORT.get());
        this.dropPottedContents(POTTED_QUILLWORT.get());

        this.dropSelf(LEEFRUCTUS.get());
        this.dropPottedContents(POTTED_LEEFRUCTUS.get());

        this.dropSelf(RHYNIA.get());
        this.dropPottedContents(POTTED_RHYNIA.get());

        this.dropSelf(HORSETAIL.get());
        this.dropPottedContents(POTTED_HORSETAIL.get());
        this.add(LARGE_HORSETAIL.get(), block -> doublePlantDrops(block, HORSETAIL.get()));

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
        this.dropSelf(GINKGO_SIGN.get());
        this.dropSelf(GINKGO_WALL_SIGN.get());
        this.dropSelf(GINKGO_HANGING_SIGN.get());
        this.dropSelf(GINKGO_SAPLING.get());
        this.dropPottedContents(POTTED_GINKGO_SAPLING.get());
        this.dropSelf(GOLDEN_GINKGO_SAPLING.get());
        this.dropPottedContents(POTTED_GOLDEN_GINKGO_SAPLING.get());

        this.add(GINKGO_LEAVES.get(), (block) -> createLeavesDrops(block, GINKGO_SAPLING.get(), LEAVES_SAPLING_CHANCES));
        this.add(GOLDEN_GINKGO_LEAVES.get(), (block) -> createLeavesDrops(block, GOLDEN_GINKGO_SAPLING.get(), LEAVES_SAPLING_CHANCES));

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

        this.add(LEPIDODENDRON_LEAVES.get(), (block) -> createLeavesDrops(block, LEPIDODENDRON_CONE.get(), LEAVES_SAPLING_CHANCES));
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
