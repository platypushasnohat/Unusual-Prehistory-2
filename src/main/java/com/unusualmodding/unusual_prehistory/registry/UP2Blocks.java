package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.*;
import com.unusualmodding.unusual_prehistory.worldgen.feature.tree.GinkgoTreeGrower;
import com.unusualmodding.unusual_prehistory.worldgen.feature.tree.GoldenGinkgoTreeGrower;
import com.unusualmodding.unusual_prehistory.worldgen.feature.tree.LepidodendronTreeGrower;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class UP2Blocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnusualPrehistory2.MOD_ID);
    public static List<RegistryObject<? extends Block>> AUTO_TRANSLATE = new ArrayList<>();

    // science doodads
    public static final RegistryObject<Block> TRANSMOGRIFIER = registerBlock("transmogrifier", () -> new TransmogrifierBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().noOcclusion().strength(5.0F, 6.0F).sound(SoundType.METAL).lightLevel(litBlockEmission(7))));

    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil", () -> new FossilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), SoundEvents.DEEPSLATE_BREAK));
    public static final RegistryObject<Block> FOSSIL = registerBlock("fossil", () -> new FossilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F), SoundEvents.STONE_BREAK));

    public static final RegistryObject<Block> FOSSILIZED_BONE_BLOCK = registerBlock("fossilized_bone_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> FOSSILIZED_BONE_VERTEBRA = registerBlock("fossilized_bone_vertebra", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> FOSSILIZED_SKULL = registerBlock("fossilized_skull", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> FOSSILIZED_SKULL_LANTERN = registerBlock("fossilized_skull_lantern", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> FOSSILIZED_SKULL_SOUL_LANTERN = registerBlock("fossilized_skull_soul_lantern", () -> new FossilizedSkullBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK).lightLevel((state) -> 10)));

    // eggs
    public static final Supplier<Block> DIPLOCAULUS_EGGS = registerBlockWithItem("diplocaulus_eggs", () -> new WaterEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().randomTicks().sound(SoundType.FROGSPAWN), UP2Entities.DIPLOCAULUS), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));
    public static final Supplier<Block> JAWLESS_FISH_ROE = registerBlockWithItem("jawless_fish_roe", () -> new WaterEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().randomTicks().sound(SoundType.FROGSPAWN), UP2Entities.JAWLESS_FISH), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));
    public static final Supplier<Block> KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS = registerBlockWithItem("kimmeridgebrachypteraeschnidium_eggs", () -> new WaterEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().randomTicks().sound(SoundType.FROGSPAWN), UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));
    public static final Supplier<Block> DUNKLEOSTEUS_SAC = registerBlockWithItem("dunkleosteus_sac", () -> new WaterEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().randomTicks().sound(SoundType.FROGSPAWN), UP2Entities.DUNKLEOSTEUS), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));
    public static final Supplier<Block> STETHACANTHUS_SAC = registerBlockWithItem("stethacanthus_sac", () -> new WaterEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instabreak().noOcclusion().noCollission().randomTicks().sound(SoundType.FROGSPAWN), UP2Entities.STETHACANTHUS), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final RegistryObject<Block> CARNOTAURUS_EGG = registerBlock("carnotaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.CARNOTAURUS::get, 12, 15, false));
    public static final RegistryObject<Block> KENTROSAURUS_EGG = registerBlock("kentrosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.KENTROSAURUS::get, 8, 15, false));
    public static final RegistryObject<Block> MAJUNGASAURUS_EGG = registerBlock("majungasaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.MAJUNGASAURUS::get, 12, 11, false));
    public static final RegistryObject<Block> MEGALANIA_EGG = registerBlock("megalania_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.MEGALANIA::get, 12, 14, false));

    public static final RegistryObject<Block> MOSSY_DIRT = registerBlock("mossy_dirt", () -> new MossyDirtBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.5F).sound(UP2SoundTypes.MOSSY_DIRT)));
    public static final RegistryObject<Block> MOSS_LAYER = registerBlock("moss_layer", () -> new MossLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> BENNETTITALES = registerBlock("bennettitales", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_BENNETTITALES = registerBlockWithoutItem("potted_bennettitales", () -> new FlowerPotBlock(UP2Blocks.BENNETTITALES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CALAMOPHYTON = registerBlock("calamophyton", () -> new CalamophytonBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> COOKSONIA = registerBlock("cooksonia", () -> new PrehistoricFlowerBlock(() -> MobEffects.REGENERATION, 9, UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_COOKSONIA = registerBlockWithoutItem("potted_cooksonia", () -> new FlowerPotBlock(UP2Blocks.COOKSONIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CLADOPHLEBIS = registerBlock("cladophlebis", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_CLADOPHLEBIS = registerBlockWithoutItem("potted_cladophlebis", () -> new FlowerPotBlock(UP2Blocks.CLADOPHLEBIS.get(), registerFlowerPot()));

    public static final RegistryObject<Block> HORSETAIL = registerBlock("horsetail", () -> new HorsetailBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_HORSETAIL = registerBlockWithoutItem("potted_horsetail", () -> new FlowerPotBlock(UP2Blocks.HORSETAIL.get(), registerFlowerPot()));
    public static final RegistryObject<Block> LARGE_HORSETAIL = registerBlock("large_horsetail", () -> new LargeHorsetailBlock(UP2BlockProperties.TALL_PLANT));

    public static final RegistryObject<Block> ISOETES = registerBlock("isoetes", () -> new IsoetesBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_ISOETES = registerBlockWithoutItem("potted_isoetes", () -> new FlowerPotBlock(UP2Blocks.ISOETES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> LEEFRUCTUS = registerBlock("leefructus", () -> new PrehistoricFlowerBlock(() -> MobEffects.ABSORPTION, 9, UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_LEEFRUCTUS = registerBlockWithoutItem("potted_leefructus", () -> new FlowerPotBlock(UP2Blocks.LEEFRUCTUS.get(), registerFlowerPot()));

    public static final RegistryObject<Block> RAIGUENRAYUN = registerBlock("raiguenrayun", () -> new PrehistoricTallFlowerBlock(UP2BlockProperties.TALL_PLANT));

    public static final RegistryObject<Block> RHYNIA = registerBlock("rhynia", () -> new PrehistoricPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_RHYNIA = registerBlockWithoutItem("potted_rhynia", () -> new FlowerPotBlock(UP2Blocks.RHYNIA.get(), registerFlowerPot()));

    // ginkgo
    public static final RegistryObject<Block> GINKGO_LOG = registerBlock("ginkgo_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> GINKGO_WOOD = registerBlock("ginkgo_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> STRIPPED_GINKGO_LOG = registerBlock("stripped_ginkgo_log", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> STRIPPED_GINKGO_WOOD = registerBlock("stripped_ginkgo_wood", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> GINKGO_PLANKS = registerBlock("ginkgo_planks", () -> new Block(UP2BlockProperties.plank(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> GINKGO_STAIRS = registerBlock("ginkgo_stairs", () -> new StairBlock(() -> GINKGO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(GINKGO_PLANKS.get())));
    public static final RegistryObject<Block> GINKGO_SLAB = registerBlock("ginkgo_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GINKGO_PLANKS.get())));
    public static final RegistryObject<Block> GINKGO_FENCE = registerBlock("ginkgo_fence", () -> new FenceBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> GINKGO_FENCE_GATE = registerBlock("ginkgo_fence_gate", () -> new FenceGateBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> GINKGO_DOOR = registerBlock("ginkgo_door", () -> new DoorBlock(UP2BlockProperties.woodenDoor(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY));
    public static final RegistryObject<Block> GINKGO_TRAPDOOR = registerBlock("ginkgo_trapdoor", () -> new TrapDoorBlock(UP2BlockProperties.woodenTrapdoor(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY));
    public static final RegistryObject<Block> GINKGO_PRESSURE_PLATE = registerBlock("ginkgo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, UP2BlockProperties.woodenPressurePlate(MapColor.TERRACOTTA_YELLOW, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY));
    public static final RegistryObject<Block> GINKGO_BUTTON = registerBlock("ginkgo_button", () -> new ButtonBlock(UP2BlockProperties.woodenButton(SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY, 30, true));

    public static final RegistryObject<Block> GINKGO_LEAVES = registerBlock("ginkgo_leaves", () -> new GinkgoLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES), UP2Particles.GINKGO_LEAVES));
    public static final RegistryObject<Block> GOLDEN_GINKGO_LEAVES = registerBlock("golden_ginkgo_leaves", () -> new GinkgoLeavesBlock(UP2BlockProperties.leaves(MapColor.GOLD, SoundType.AZALEA_LEAVES), UP2Particles.GOLDEN_GINKGO_LEAVES));

    public static final RegistryObject<Block> GINKGO_SAPLING = registerBlock("ginkgo_sapling", () -> new SaplingBlock(new GinkgoTreeGrower(), UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final RegistryObject<Block> POTTED_GINKGO_SAPLING = registerBlockWithoutItem("potted_ginkgo_sapling", () -> new FlowerPotBlock(GINKGO_SAPLING.get(), registerFlowerPot()));

    public static final RegistryObject<Block> GOLDEN_GINKGO_SAPLING = registerBlock("golden_ginkgo_sapling", () -> new SaplingBlock(new GoldenGinkgoTreeGrower(), UP2BlockProperties.sapling(MapColor.GOLD, SoundType.CHERRY_SAPLING)));
    public static final RegistryObject<Block> POTTED_GOLDEN_GINKGO_SAPLING = registerBlockWithoutItem("potted_golden_ginkgo_sapling", () -> new FlowerPotBlock(GOLDEN_GINKGO_SAPLING.get(), registerFlowerPot()));

    public static final RegistryObject<Block> GINKGO_SIGN = registerBlockWithoutItemNoLang("ginkgo_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), UP2BlockProperties.GINKGO_WOOD_TYPE));
    public static final RegistryObject<Block> GINKGO_WALL_SIGN = registerBlockWithoutItemNoLang("ginkgo_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), UP2BlockProperties.GINKGO_WOOD_TYPE));
    public static final RegistryObject<Block> GINKGO_HANGING_SIGN = registerBlockWithoutItemNoLang("ginkgo_hanging_sign", () -> new CeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F), UP2BlockProperties.GINKGO_WOOD_TYPE));
    public static final RegistryObject<Block> GINKGO_WALL_HANGING_SIGN = registerBlockWithoutItemNoLang("ginkgo_wall_hanging_sign", () -> new WallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(GINKGO_HANGING_SIGN.get()), UP2BlockProperties.GINKGO_WOOD_TYPE));

    // lepidodendron
    public static final RegistryObject<Block> LEPIDODENDRON_LOG = registerBlock("lepidodendron_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> MOSSY_LEPIDODENDRON_LOG = registerBlock("mossy_lepidodendron_log", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> LEPIDODENDRON_WOOD = registerBlock("lepidodendron_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> MOSSY_LEPIDODENDRON_WOOD = registerBlock("mossy_lepidodendron_wood", () -> new WoodBlocks(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> STRIPPED_LEPIDODENDRON_LOG = registerBlock("stripped_lepidodendron_log", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> STRIPPED_LEPIDODENDRON_WOOD = registerBlock("stripped_lepidodendron_wood", () -> new RotatedPillarBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> LEPIDODENDRON_PLANKS = registerBlock("lepidodendron_planks", () -> new Block(UP2BlockProperties.plank(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> LEPIDODENDRON_STAIRS = registerBlock("lepidodendron_stairs", () -> new StairBlock(() -> LEPIDODENDRON_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(LEPIDODENDRON_PLANKS.get())));
    public static final RegistryObject<Block> LEPIDODENDRON_SLAB = registerBlock("lepidodendron_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(LEPIDODENDRON_PLANKS.get())));
    public static final RegistryObject<Block> LEPIDODENDRON_FENCE = registerBlock("lepidodendron_fence", () -> new FenceBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final RegistryObject<Block> LEPIDODENDRON_FENCE_GATE = registerBlock("lepidodendron_fence_gate", () -> new FenceGateBlock(UP2BlockProperties.plank(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> LEPIDODENDRON_DOOR = registerBlock("lepidodendron_door", () -> new DoorBlock(UP2BlockProperties.woodenDoor(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY));
    public static final RegistryObject<Block> LEPIDODENDRON_TRAPDOOR = registerBlock("lepidodendron_trapdoor", () -> new TrapDoorBlock(UP2BlockProperties.woodenTrapdoor(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY));
    public static final RegistryObject<Block> LEPIDODENDRON_PRESSURE_PLATE = registerBlock("lepidodendron_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, UP2BlockProperties.woodenPressurePlate(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY));
    public static final RegistryObject<Block> LEPIDODENDRON_BUTTON = registerBlock("lepidodendron_button", () -> new ButtonBlock(UP2BlockProperties.woodenButton(SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS), BlockSetType.CHERRY, 30, true));

    public static final RegistryObject<Block> LEPIDODENDRON_LEAVES = registerBlock("lepidodendron_leaves", () -> new LepidodendronLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final RegistryObject<Block> HANGING_LEPIDODENDRON_LEAVES = registerBlock("hanging_lepidodendron_leaves", () -> new HangingLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES).noCollission()));

    public static final RegistryObject<Block> LEPIDODENDRON_CONE = registerBlock("lepidodendron_cone", () -> new LepidodendronConeBlock(new LepidodendronTreeGrower(), UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));

    public static final RegistryObject<Block> LEPIDODENDRON_SIGN = registerBlockWithoutItemNoLang("lepidodendron_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE));
    public static final RegistryObject<Block> LEPIDODENDRON_WALL_SIGN = registerBlockWithoutItemNoLang("lepidodendron_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE));
    public static final RegistryObject<Block> LEPIDODENDRON_HANGING_SIGN = registerBlockWithoutItemNoLang("lepidodendron_hanging_sign", () -> new CeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F), UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE));
    public static final RegistryObject<Block> LEPIDODENDRON_WALL_HANGING_SIGN = registerBlockWithoutItemNoLang("lepidodendron_wall_hanging_sign", () -> new WallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(LEPIDODENDRON_HANGING_SIGN.get()), UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE));

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        AUTO_TRANSLATE.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        AUTO_TRANSLATE.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithoutItemNoLang(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static <B extends Block> Supplier<B> registerBlockWithItem(String name, Supplier<B> block, Function<Supplier<B>, Item> item) {
        Supplier<B> entry = registerBlockWithoutItem(name, block);
        UP2Items.ITEMS.register(name, () -> item.apply(entry));
        return entry;
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
