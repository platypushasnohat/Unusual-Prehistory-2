package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.*;
import com.barlinc.unusual_prehistory.blocks.egg.*;
import com.barlinc.unusual_prehistory.blocks.plant.*;
import com.barlinc.unusual_prehistory.blocks.plant.grower.*;
import com.barlinc.unusual_prehistory.blocks.plant.update_1.*;
import com.barlinc.unusual_prehistory.blocks.plant.update_4.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public class UP2Blocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UnusualPrehistory2.MOD_ID);

    public static final List<Supplier<? extends Block>> EGG_BLOCKS = new ArrayList<>();

    public static List<DeferredBlock<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();

    // Update 1
    public static final DeferredBlock<Block> CARNOTAURUS_EGG = registerEggBlock("carnotaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.CARNOTAURUS::get, 12, 15, false));
    public static final DeferredBlock<Block> DIPLOCAULUS_EGGS = registerWaterEggBlock("diplocaulus_eggs", () -> new WaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.DIPLOCAULUS::get, 2));
    public static final DeferredBlock<Block> DUNKLEOSTEUS_SAC = registerEggBlock("dunkleosteus_sac", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.DUNKLEOSTEUS::get, 1));
    public static final DeferredBlock<Block> JAWLESS_FISH_ROE = registerEggBlock("jawless_fish_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.JAWLESS_FISH::get, 4));
    public static final DeferredBlock<Block> KENTROSAURUS_EGG = registerEggBlock("kentrosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.KENTROSAURUS::get, 8, 15, false));
    public static final DeferredBlock<Block> KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS = registerWaterEggBlock("kimmeridgebrachypteraeschnidium_eggs", () -> new WaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH::get, 4));
    public static final DeferredBlock<Block> MAJUNGASAURUS_EGG = registerEggBlock("majungasaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.MAJUNGASAURUS::get, 12, 11, false));
    public static final DeferredBlock<Block> MEGALANIA_EGG = registerEggBlock("megalania_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.MEGALANIA::get, 12, 14, false));
    public static final DeferredBlock<Block> STETHACANTHUS_SAC = registerEggBlock("stethacanthus_sac", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.STETHACANTHUS::get, 2));

    public static final DeferredBlock<Block> BENNETTITALES = registerBlock("bennettitales", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_BENNETTITALES = registerBlockWithoutItem("potted_bennettitales", () -> new FlowerPotBlock(UP2Blocks.BENNETTITALES.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> CALAMOPHYTON = registerBlock("calamophyton", () -> new CalamophytonBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> COOKSONIA = registerBlock("cooksonia", () -> new PrehistoricFlowerBlock(MobEffects.REGENERATION, 9, UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_COOKSONIA = registerBlockWithoutItem("potted_cooksonia", () -> new FlowerPotBlock(UP2Blocks.COOKSONIA.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> CLADOPHLEBIS = registerBlock("cladophlebis", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_CLADOPHLEBIS = registerBlockWithoutItem("potted_cladophlebis", () -> new FlowerPotBlock(UP2Blocks.CLADOPHLEBIS.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> HORSETAIL = registerBlock("horsetail", () -> new HorsetailBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_HORSETAIL = registerBlockWithoutItem("potted_horsetail", () -> new FlowerPotBlock(UP2Blocks.HORSETAIL.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> LARGE_HORSETAIL = registerBlock("large_horsetail", () -> new LargeHorsetailBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> QUILLWORT = registerBlock("quillwort", () -> new QuillwortBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_QUILLWORT = registerBlockWithoutItem("potted_quillwort", () -> new FlowerPotBlock(UP2Blocks.QUILLWORT.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> LEEFRUCTUS = registerBlock("leefructus", () -> new PrehistoricFlowerBlock(MobEffects.ABSORPTION, 9, UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_LEEFRUCTUS = registerBlockWithoutItem("potted_leefructus", () -> new FlowerPotBlock(UP2Blocks.LEEFRUCTUS.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> RAIGUENRAYUN = registerBlock("raiguenrayun", () -> new PrehistoricTallFlowerBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> RHYNIA = registerBlock("rhynia", () -> new PrehistoricPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> POTTED_RHYNIA = registerBlockWithoutItem("potted_rhynia", () -> new FlowerPotBlock(UP2Blocks.RHYNIA.get(), registerFlowerPot()));

    public static final DeferredBlock<Block> MOSSY_DIRT = registerBlock("mossy_dirt", () -> new MossyDirtBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.5F).sound(UP2SoundTypes.MOSSY_DIRT)));
    public static final DeferredBlock<Block> MOSS_LAYER = registerBlock("moss_layer", () -> new MossLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> GINKGO_LOG = registerBlock("ginkgo_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_WOOD = registerBlock("ginkgo_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_GINKGO_LOG = registerBlock("stripped_ginkgo_log", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_GINKGO_WOOD = registerBlock("stripped_ginkgo_wood", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_PLANKS = registerBlock("ginkgo_planks", () -> new Block(UP2BlockProperties.plank(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_STAIRS = registerBlock("ginkgo_stairs", () -> new StairBlock(GINKGO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(GINKGO_PLANKS.get())));
    public static final DeferredBlock<Block> GINKGO_SLAB = registerBlock("ginkgo_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(GINKGO_PLANKS.get())));
    public static final DeferredBlock<Block> GINKGO_FENCE = registerBlock("ginkgo_fence", () -> new FenceBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_FENCE_GATE = registerBlock("ginkgo_fence_gate", () -> new FenceGateBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final DeferredBlock<Block> GINKGO_DOOR = registerBlock("ginkgo_door", () -> new DoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenDoor(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_TRAPDOOR = registerBlock("ginkgo_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenTrapdoor(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_PRESSURE_PLATE = registerBlock("ginkgo_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenPressurePlate(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_BUTTON = registerBlock("ginkgo_button", () -> new ButtonBlock(BlockSetType.CHERRY, 30, UP2BlockProperties.woodenButton(SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> GINKGO_LEAVES = registerBlock("ginkgo_leaves", () -> new FallingLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES), UP2Particles.GINKGO_LEAVES));
    public static final DeferredBlock<Block> GOLDEN_GINKGO_LEAVES = registerBlock("golden_ginkgo_leaves", () -> new FallingLeavesBlock(UP2BlockProperties.leaves(MapColor.GOLD, SoundType.AZALEA_LEAVES), UP2Particles.GOLDEN_GINKGO_LEAVES));
    public static final DeferredBlock<Block> GINKGO_SAPLING = registerBlock("ginkgo_sapling", () -> new SaplingBlock(new GinkgoTreeGrower(), UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_GINKGO_SAPLING = registerBlockWithoutItem("potted_ginkgo_sapling", () -> new FlowerPotBlock(GINKGO_SAPLING.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> GOLDEN_GINKGO_SAPLING = registerBlock("golden_ginkgo_sapling", () -> new SaplingBlock(new GoldenGinkgoTreeGrower(), UP2BlockProperties.sapling(MapColor.GOLD, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_GOLDEN_GINKGO_SAPLING = registerBlockWithoutItem("potted_golden_ginkgo_sapling", () -> new FlowerPotBlock(GOLDEN_GINKGO_SAPLING.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> GINKGO_SIGN = registerBlockWithoutItemNoLang("ginkgo_sign", () -> new StandingSignBlock(UP2BlockProperties.GINKGO_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> GINKGO_WALL_SIGN = registerBlockWithoutItemNoLang("ginkgo_wall_sign", () -> new WallSignBlock(UP2BlockProperties.GINKGO_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> GINKGO_HANGING_SIGN = registerBlockWithoutItemNoLang("ginkgo_hanging_sign", () -> new CeilingHangingSignBlock(UP2BlockProperties.GINKGO_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> GINKGO_WALL_HANGING_SIGN = registerBlockWithoutItemNoLang("ginkgo_wall_hanging_sign", () -> new WallHangingSignBlock(UP2BlockProperties.GINKGO_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(GINKGO_HANGING_SIGN.get())));

    public static final DeferredBlock<Block> LEPIDODENDRON_LOG = registerBlock("lepidodendron_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> MOSSY_LEPIDODENDRON_LOG = registerBlock("mossy_lepidodendron_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_WOOD = registerBlock("lepidodendron_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> MOSSY_LEPIDODENDRON_WOOD = registerBlock("mossy_lepidodendron_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_LEPIDODENDRON_LOG = registerBlock("stripped_lepidodendron_log", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_LEPIDODENDRON_WOOD = registerBlock("stripped_lepidodendron_wood", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_PLANKS = registerBlock("lepidodendron_planks", () -> new Block(UP2BlockProperties.plank(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_STAIRS = registerBlock("lepidodendron_stairs", () -> new StairBlock(LEPIDODENDRON_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(LEPIDODENDRON_PLANKS.get())));
    public static final DeferredBlock<Block> LEPIDODENDRON_SLAB = registerBlock("lepidodendron_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LEPIDODENDRON_PLANKS.get())));
    public static final DeferredBlock<Block> LEPIDODENDRON_FENCE = registerBlock("lepidodendron_fence", () -> new FenceBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_FENCE_GATE = registerBlock("lepidodendron_fence_gate", () -> new FenceGateBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final DeferredBlock<Block> LEPIDODENDRON_DOOR = registerBlock("lepidodendron_door", () -> new DoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenDoor(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_TRAPDOOR = registerBlock("lepidodendron_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenTrapdoor(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_PRESSURE_PLATE = registerBlock("lepidodendron_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenPressurePlate(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_BUTTON = registerBlock("lepidodendron_button", () -> new ButtonBlock(BlockSetType.CHERRY, 30, UP2BlockProperties.woodenButton(SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_LEAVES = registerBlock("lepidodendron_leaves", () -> new LepidodendronLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> HANGING_LEPIDODENDRON_LEAVES = registerBlock("hanging_lepidodendron_leaves", () -> new HangingLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES).noCollission()));
    public static final DeferredBlock<Block> LEPIDODENDRON_CONE = registerBlock("lepidodendron_cone", () -> new LepidodendronConeBlock(new LepidodendronTreeGrower(), UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> LEPIDODENDRON_SIGN = registerBlockWithoutItemNoLang("lepidodendron_sign", () -> new StandingSignBlock(UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> LEPIDODENDRON_WALL_SIGN = registerBlockWithoutItemNoLang("lepidodendron_wall_sign", () -> new WallSignBlock(UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> LEPIDODENDRON_HANGING_SIGN = registerBlockWithoutItemNoLang("lepidodendron_hanging_sign", () -> new CeilingHangingSignBlock(UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> LEPIDODENDRON_WALL_HANGING_SIGN = registerBlockWithoutItemNoLang("lepidodendron_wall_hanging_sign", () -> new WallHangingSignBlock(UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(LEPIDODENDRON_HANGING_SIGN.get())));

    public static final DeferredBlock<Block> TRANSMOGRIFIER = registerBlock("transmogrifier", () -> new TransmogrifierBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().noOcclusion().strength(5.0F, 6.0F).sound(SoundType.METAL).lightLevel(litBlockEmission(7))));

    public static final DeferredBlock<Block> FOSSILIZED_BONE_BLOCK = registerBlock("fossilized_bone_block", () -> new RotatedPillarBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_BARK = registerBlock("fossilized_bone_bark", () -> new RotatedPillarBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_VERTEBRA = registerBlock("fossilized_bone_vertebra", () -> new RotatedPillarBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL = registerBlock("fossilized_skull", () -> new FossilizedSkullBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_LANTERN = registerBlock("fossilized_skull_lantern", () -> new FossilizedSkullBlock(UP2BlockProperties.fossilLantern(15)));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_SOUL_LANTERN = registerBlock("fossilized_skull_soul_lantern", () -> new FossilizedSkullBlock(UP2BlockProperties.fossilLantern(10)));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE = registerBlock("cobbled_fossilized_bone", () -> new Block(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE_STAIRS = registerBlock("cobbled_fossilized_bone_stairs", () -> new StairBlock(COBBLED_FOSSILIZED_BONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(COBBLED_FOSSILIZED_BONE.get())));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE_SLAB = registerBlock("cobbled_fossilized_bone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COBBLED_FOSSILIZED_BONE.get())));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROD = registerBlock("fossilized_bone_rod", () -> new FossilizedBoneRodBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_SPIKE = registerBlock("fossilized_bone_spike", () -> new FossilizedBoneSpikeBlock(UP2BlockProperties.FOSSIL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROW = registerBlock("fossilized_bone_row", () -> new FossilizedBoneRowBlock(UP2BlockProperties.FOSSIL_BLOCK.noOcclusion()));

    public static final DeferredBlock<Block> PETRIFIED_BUSH = registerBlock("petrified_bush", () -> new PetrifiedBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noOcclusion().noCollission().instrument(NoteBlockInstrument.HARP).strength(1.0F).instabreak().sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> PETRIFIED_LOG = registerBlock("petrified_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> PETRIFIED_WOOD = registerBlock("petrified_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD = registerBlock("polished_petrified_wood", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_STAIRS = registerBlock("polished_petrified_wood_stairs", () -> new StairBlock(POLISHED_PETRIFIED_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_PETRIFIED_WOOD.get())));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_SLAB = registerBlock("polished_petrified_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_PETRIFIED_WOOD.get())));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE = registerBlock("polished_petrified_wood_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_BUTTON = registerBlock("polished_petrified_wood_button", () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> TAR = registerBlockWithoutItem("tar", () -> new TarBlock(UP2Fluids.TAR_FLUID_SOURCE, UP2BlockProperties.TAR));
    public static final DeferredBlock<Block> ASPHALT = registerBlock("asphalt", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 5.0F).sound(SoundType.STONE).speedFactor(1.15F)));

    // Update 2
    public static final DeferredBlock<Block> ONCHOPRISTIS_SAC = registerEggBlock("onchopristis_sac", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.ONCHOPRISTIS::get, 1));

    // Update 3
    public static final DeferredBlock<Block> TARTUOSTEUS_ROE = registerEggBlock("tartuosteus_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.TARTUOSTEUS::get, 1));

    public static final DeferredBlock<Block> OOZE_CAULDRON = registerBlockWithoutItem("ooze_cauldron", () -> new OozeCauldron(UP2BlockProperties.CAULDRON));

    // Update 4
    public static final DeferredBlock<Block> BRACHIOSAURUS_EGG = registerEggBlock("brachiosaurus_egg", () -> new TallEggBlock(UP2BlockProperties.EGG, UP2Entities.BRACHIOSAURUS::get, 16, 16, 16, 8, false));
    public static final DeferredBlock<Block> COELACANTHUS_ROE = registerEggBlock("coelacanthus_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.COELACANTHUS::get, 1));
    public static final DeferredBlock<Block> HIBBERTOPTERUS_EGGS = registerWaterEggBlock("hibbertopterus_eggs", () -> new WaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.HIBBERTOPTERUS::get, 1));
    public static final DeferredBlock<Block> KAPROSUCHUS_EGG = registerEggBlock("kaprosuchus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.KAPROSUCHUS::get, 8, 12, false));
    public static final DeferredBlock<Block> LOBE_FINNED_FISH_ROE = registerEggBlock("lobe_finned_fish_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.LOBE_FINNED_FISH::get, 3));
    public static final DeferredBlock<Block> LYSTROSAURUS_EGG = registerEggBlock("lystrosaurus_egg", () -> new MultipleEggBlock(UP2BlockProperties.EGG, UP2Entities.LYSTROSAURUS::get, 3, 6, 6, 12, 12, false));
    public static final DeferredBlock<Block> PACHYCEPHALOSAURUS_EGG = registerEggBlock("pachycephalosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.PACHYCEPHALOSAURUS::get, 10, 10, false));
    public static final DeferredBlock<Block> ULUGHBEGSAURUS_EGG = registerEggBlock("ulughbegsaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.ULUGHBEGSAURUS::get, 10, 14, false));

    public static final DeferredBlock<Block> AETHOPHYLLUM = registerBlock("aethophyllum", () -> new PrehistoricTallFlowerBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> ARCHAEOSIGILLARIA = registerBlock("archaeosigillaria", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_ARCHAEOSIGILLARIA = registerBlockWithoutItem("potted_archaeosigillaria", () -> new FlowerPotBlock(UP2Blocks.ARCHAEOSIGILLARIA.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> BRACHYPHYLLUM = registerBlock("brachyphyllum", () -> new ThreeTallPlantBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> CYCAD_SEEDLING = registerBlock("cycad_seedling", () -> new CycadSeedlingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY).noOcclusion().ignitedByLava()));
    public static final DeferredBlock<Block> CYCAD_STEM = registerBlock("cycad_stem", () -> new CycadStemBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<Block> CYCAD_CROWN = registerBlock("cycad_crown", () -> new CycadCrownBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).noOcclusion().ignitedByLava()));
    public static final DeferredBlock<Block> GUANGDEDENDRON_SPORE = registerBlockWithoutItem("guangdedendron_spore", () -> new GuangdedendronSporeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> GUANGDEDENDRON = registerBlock("guangdedendron", () -> new GuangdedendronStalkBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ).dynamicShape().ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UP2BlockProperties::never)));
    public static final DeferredBlock<Block> NEOMARIOPTERIS = registerBlock("neomariopteris", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_NEOMARIOPTERIS = registerBlockWithoutItem("potted_neomariopteris", () -> new FlowerPotBlock(UP2Blocks.NEOMARIOPTERIS.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> PROTOTAXITES = registerBlock("prototaxites", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> PROTOTAXITES_NUB = registerBlock("prototaxites_nub", () -> new PrototaxitesNubBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).noCollission().sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> LARGE_PROTOTAXITES_NUB = registerBlock("large_prototaxites_nub", () -> new LargePrototaxitesNubBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).noCollission().sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> PROTOTAXITES_CLUSTER = registerBlock("prototaxites_cluster", () -> new PrototaxitesClusterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).noCollission().sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> TEMPSKYA = registerBlock("tempskya", () -> new PrehistoricTallPlantBlock(UP2BlockProperties.TALL_PLANT));

    public static final DeferredBlock<Block> DRYOPHYLLUM_LOG = registerBlock("dryophyllum_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_WOOD = registerBlock("dryophyllum_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_DRYOPHYLLUM_LOG = registerBlock("stripped_dryophyllum_log", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_DRYOPHYLLUM_WOOD = registerBlock("stripped_dryophyllum_wood", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_PLANKS = registerBlock("dryophyllum_planks", () -> new Block(UP2BlockProperties.plank(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_STAIRS = registerBlock("dryophyllum_stairs", () -> new StairBlock(() -> DRYOPHYLLUM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DRYOPHYLLUM_PLANKS.get())));
    public static final DeferredBlock<Block> DRYOPHYLLUM_SLAB = registerBlock("dryophyllum_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DRYOPHYLLUM_PLANKS.get())));
    public static final DeferredBlock<Block> DRYOPHYLLUM_FENCE = registerBlock("dryophyllum_fence", () -> new FenceBlock(UP2BlockProperties.plank(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_FENCE_GATE = registerBlock("dryophyllum_fence_gate", () -> new FenceGateBlock(UP2BlockProperties.plank(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final DeferredBlock<Block> DRYOPHYLLUM_DOOR = registerBlock("dryophyllum_door", () -> new DoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenDoor(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_TRAPDOOR = registerBlock("dryophyllum_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenTrapdoor(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_PRESSURE_PLATE = registerBlock("dryophyllum_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenPressurePlate(MapColor.COLOR_PINK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_BUTTON = registerBlock("dryophyllum_button", () -> new ButtonBlock(BlockSetType.CHERRY, 30, UP2BlockProperties.woodenButton(SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_LEAVES = registerBlock("dryophyllum_leaves", () -> new LeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_SAPLING = registerBlock("dryophyllum_sapling", () -> new SaplingBlock(new DryophyllumTreeGrower(), UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_DRYOPHYLLUM_SAPLING = registerBlockWithoutItem("potted_dryophyllum_sapling", () -> new FlowerPotBlock(DRYOPHYLLUM_SAPLING.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> DRYOPHYLLUM_SIGN = registerBlockWithoutItemNoLang("dryophyllum_sign", () -> new StandingSignBlock(UP2BlockProperties.DRYOPHYLLUM_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_WALL_SIGN = registerBlockWithoutItemNoLang("dryophyllum_wall_sign", () -> new WallSignBlock(UP2BlockProperties.DRYOPHYLLUM_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_HANGING_SIGN = registerBlockWithoutItemNoLang("dryophyllum_hanging_sign", () -> new CeilingHangingSignBlock(UP2BlockProperties.DRYOPHYLLUM_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_WALL_HANGING_SIGN = registerBlockWithoutItemNoLang("dryophyllum_wall_hanging_sign", () -> new WallHangingSignBlock(UP2BlockProperties.DRYOPHYLLUM_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(DRYOPHYLLUM_HANGING_SIGN.get())));

    public static final DeferredBlock<Block> METASEQUOIA_LOG = registerBlock("metasequoia_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_WOOD = registerBlock("metasequoia_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_METASEQUOIA_LOG = registerBlock("stripped_metasequoia_log", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> STRIPPED_METASEQUOIA_WOOD = registerBlock("stripped_metasequoia_wood", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_PLANKS = registerBlock("metasequoia_planks", () -> new Block(UP2BlockProperties.plank(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_STAIRS = registerBlock("metasequoia_stairs", () -> new StairBlock(METASEQUOIA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(METASEQUOIA_PLANKS.get())));
    public static final DeferredBlock<Block> METASEQUOIA_SLAB = registerBlock("metasequoia_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(METASEQUOIA_PLANKS.get())));
    public static final DeferredBlock<Block> METASEQUOIA_FENCE = registerBlock("metasequoia_fence", () -> new FenceBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_FENCE_GATE = registerBlock("metasequoia_fence_gate", () -> new FenceGateBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final DeferredBlock<Block> METASEQUOIA_DOOR = registerBlock("metasequoia_door", () -> new DoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenDoor(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_TRAPDOOR = registerBlock("metasequoia_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenTrapdoor(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_PRESSURE_PLATE = registerBlock("metasequoia_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, UP2BlockProperties.woodenPressurePlate(MapColor.TERRACOTTA_RED, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_BUTTON = registerBlock("metasequoia_button", () -> new ButtonBlock(BlockSetType.CHERRY, 30, UP2BlockProperties.woodenButton(SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> METASEQUOIA_LEAVES = registerBlock("metasequoia_leaves", () -> new LeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> METASEQUOIA_SAPLING = registerBlock("metasequoia_sapling", () -> new MetasequoiaSaplingBlock(new MetasequoiaTreeGrower(), UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_METASEQUOIA_SAPLING = registerBlockWithoutItem("potted_metasequoia_sapling", () -> new FlowerPotBlock(METASEQUOIA_SAPLING.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> METASEQUOIA_SIGN = registerBlockWithoutItemNoLang("metasequoia_sign", () -> new StandingSignBlock(UP2BlockProperties.METASEQUOIA_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> METASEQUOIA_WALL_SIGN = registerBlockWithoutItemNoLang("metasequoia_wall_sign", () -> new WallSignBlock(UP2BlockProperties.METASEQUOIA_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD)));
    public static final DeferredBlock<Block> METASEQUOIA_HANGING_SIGN = registerBlockWithoutItemNoLang("metasequoia_hanging_sign", () -> new CeilingHangingSignBlock(UP2BlockProperties.METASEQUOIA_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> METASEQUOIA_WALL_HANGING_SIGN = registerBlockWithoutItemNoLang("metasequoia_wall_hanging_sign", () -> new WallHangingSignBlock(UP2BlockProperties.METASEQUOIA_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(METASEQUOIA_HANGING_SIGN.get())));

    public static final DeferredBlock<Block> PALEOSTONE = registerBlock("paleostone", () -> new Block(UP2BlockProperties.FOSSIL_STONE));
    public static final DeferredBlock<Block> PALEOSTONE_STAIRS = registerBlock("paleostone_stairs", () -> new StairBlock(PALEOSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(PALEOSTONE.get())));
    public static final DeferredBlock<Block> PALEOSTONE_SLAB = registerBlock("paleostone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PALEOSTONE.get())));

    public static final DeferredBlock<Block> MESONITE = registerBlock("mesonite", () -> new Block(UP2BlockProperties.FOSSIL_STONE));
    public static final DeferredBlock<Block> MESONITE_STAIRS = registerBlock("mesonite_stairs", () -> new StairBlock(MESONITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MESONITE.get())));
    public static final DeferredBlock<Block> MESONITE_SLAB = registerBlock("mesonite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MESONITE.get())));

    public static final DeferredBlock<Block> FLORALITE = registerBlock("floralite", () -> new Block(UP2BlockProperties.FOSSIL_STONE));
    public static final DeferredBlock<Block> FLORALITE_STAIRS = registerBlock("floralite_stairs", () -> new StairBlock(FLORALITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(FLORALITE.get())));
    public static final DeferredBlock<Block> FLORALITE_SLAB = registerBlock("floralite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(FLORALITE.get())));

    public static final DeferredBlock<Block> REINFORCED_GLASS = registerBlock("reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.NONE)));
    public static final DeferredBlock<Block> TINTED_REINFORCED_GLASS = registerBlock("tinted_reinforced_glass", ()-> new TintedConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_GRAY)));
    public static final DeferredBlock<Block> WHITE_REINFORCED_GLASS = registerBlock("white_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.SNOW)));
    public static final DeferredBlock<Block> LIGHT_GRAY_REINFORCED_GLASS = registerBlock("light_gray_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_LIGHT_GRAY)));
    public static final DeferredBlock<Block> GRAY_REINFORCED_GLASS = registerBlock("gray_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_GRAY)));
    public static final DeferredBlock<Block> BLACK_REINFORCED_GLASS = registerBlock("black_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<Block> BROWN_REINFORCED_GLASS = registerBlock("brown_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> RED_REINFORCED_GLASS = registerBlock("red_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> ORANGE_REINFORCED_GLASS = registerBlock("orange_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<Block> YELLOW_REINFORCED_GLASS = registerBlock("yellow_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> LIME_REINFORCED_GLASS = registerBlock("lime_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_LIGHT_GREEN)));
    public static final DeferredBlock<Block> GREEN_REINFORCED_GLASS = registerBlock("green_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_GREEN)));
    public static final DeferredBlock<Block> CYAN_REINFORCED_GLASS = registerBlock("cyan_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_CYAN)));
    public static final DeferredBlock<Block> LIGHT_BLUE_REINFORCED_GLASS = registerBlock("light_blue_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<Block> BLUE_REINFORCED_GLASS = registerBlock("blue_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_BLUE)));
    public static final DeferredBlock<Block> PURPLE_REINFORCED_GLASS = registerBlock("purple_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<Block> MAGENTA_REINFORCED_GLASS = registerBlock("magenta_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_MAGENTA)));
    public static final DeferredBlock<Block> PINK_REINFORCED_GLASS = registerBlock("pink_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_PINK)));

    // Update 5
    public static final DeferredBlock<Block> AEGIROCASSIS_EGGS = registerEggBlock("aegirocassis_eggs", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.AEGIROCASSIS::get, 1));
    public static final DeferredBlock<Block> DESMATOSUCHUS_EGG = registerEggBlock("desmatosuchus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.DESMATOSUCHUS::get, 8, 13, false));

    public static final DeferredBlock<Block> ZHANGSOLVA_BLOOM = registerBlock("zhangsolva_bloom", () -> new TallAmbientPlantBlock(UP2BlockProperties.TALL_PLANT, UP2Entities.ZHANGSOLVA::get, 2));
    public static final DeferredBlock<Block> DELITZSCHALA_STALK = registerBlock("delitzschala_stalk", () -> new TallAmbientPlantBlock(UP2BlockProperties.TALL_PLANT, UP2Entities.DELITZSCHALA::get, 3));

    // Future
//    public static final DeferredBlock<Block> BARINASUCHUS_EGG = registerEggBlock("barinasuchus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.BARINASUCHUS::get, 12, 12, false));
//    public static final DeferredBlock<Block> MANIPULATOR_OOTHECA = registerWaterEggBlock("manipulator_ootheca", () -> new RotatableEggBlock(UP2BlockProperties.SQUISHY_EGG, UP2Entities.MANIPULATOR::get, 10, 6, false));
//    public static final DeferredBlock<Block> THERIZINOSAURUS_EGG = registerEggBlock("therizinosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.THERIZINOSAURUS::get, 16, 16, false));

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithoutItemNoLang(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static <B extends Block> DeferredBlock<B> registerEggBlock(String name, Supplier<B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        EGG_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerWaterEggBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        EGG_BLOCKS.add(block);
        return block;
    }

    private static BlockBehaviour.Properties registerFlowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightLevel : 0;
    }
}
