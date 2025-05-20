package com.unusualmodding.unusual_prehistory.registry;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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

public class UP2Blocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnusualPrehistory2.MOD_ID);
    public static List<RegistryObject<? extends Block>> AUTO_TRANSLATE = new ArrayList<>();

    // Science doodads
    public static final RegistryObject<Block> EXTRACTOR = registerBlock("extractor", () -> new ExtractorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> FROZEN_MEAT_BLOCK = registerBlock("frozen_meat_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.5F).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> MOSSY_DIRT = registerBlock("mossy_dirt", () -> new MossyDirtBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> MOSS_LAYER = registerBlock("moss_layer", () -> new MossLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> ARCHAEFRUCTUS = registerBlock("archaefructus", () -> new ArchaefructusBlock(UP2BlockProperties.PLANT));

    public static final RegistryObject<Block> ARCHAEOSIGILLARIA = registerBlock("archaeosigillaria", () -> new AncientPlantBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_ARCHAEOSIGILLARIA = registerBlockWithoutItem("potted_archaeosigillaria", () -> new FlowerPotBlock(UP2Blocks.ARCHAEOSIGILLARIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> BENNETTITALES = registerBlock("bennettitales", () -> new AncientPlantBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_BENNETTITALES = registerBlockWithoutItem("potted_bennettitales", () -> new FlowerPotBlock(UP2Blocks.BENNETTITALES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CALAMOPHYTON = registerBlock("calamophyton", () -> new CalamophytonBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> COOKSONIA = registerBlock("cooksonia", () -> new AncientFlowerBlock(() -> MobEffects.REGENERATION, 9, UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_COOKSONIA = registerBlockWithoutItem("potted_cooksonia", () -> new FlowerPotBlock(UP2Blocks.COOKSONIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CLADOPHLEBIS = registerBlock("cladophlebis", () -> new AncientPlantBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_CLADOPHLEBIS = registerBlockWithoutItem("potted_cladophlebis", () -> new FlowerPotBlock(UP2Blocks.CLADOPHLEBIS.get(), registerFlowerPot()));

    public static final RegistryObject<Block> HORSETAIL = registerBlock("horsetail", () -> new HorsetailBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_HORSETAIL = registerBlockWithoutItem("potted_horsetail", () -> new FlowerPotBlock(UP2Blocks.HORSETAIL.get(), registerFlowerPot()));
    public static final RegistryObject<Block> LARGE_HORSETAIL = registerBlock("large_horsetail", () -> new LargeHorsetailBlock(UP2BlockProperties.TALL_PLANT));

    public static final RegistryObject<Block> ISOETES = registerBlock("isoetes", () -> new IsoetesBlock(UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_ISOETES = registerBlockWithoutItem("potted_isoetes", () -> new FlowerPotBlock(UP2Blocks.ISOETES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> LEEFRUCTUS = registerBlock("leefructus", () -> new AncientFlowerBlock(() -> MobEffects.ABSORPTION, 9, UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_LEEFRUCTUS = registerBlockWithoutItem("potted_leefructus", () -> new FlowerPotBlock(UP2Blocks.LEEFRUCTUS.get(), registerFlowerPot()));

    public static final Supplier<Block> NELUMBITES = registerBlockWithItem("nelumbites", () -> new PlaceableOnWaterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().noOcclusion().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY)), block -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));

    public static final RegistryObject<Block> RAIGUENRAYUN = registerBlock("raiguenrayun", () -> new AncientTallFlowerBlock(UP2BlockProperties.TALL_PLANT));

    public static final RegistryObject<Block> RHYNIA = registerBlock("rhynia", () -> new AncientPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_RHYNIA = registerBlockWithoutItem("potted_rhynia", () -> new FlowerPotBlock(UP2Blocks.RHYNIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SARRACENIA = registerBlock("sarracenia", () -> new SarraceniaBlock(() -> MobEffects.HEALTH_BOOST, 9, UP2BlockProperties.PLANT));
    public static final RegistryObject<Block> POTTED_SARRACENIA = registerBlockWithoutItem("potted_sarracenia", () -> new FlowerPotBlock(UP2Blocks.SARRACENIA.get(), registerFlowerPot()));
    public static final RegistryObject<Block> TALL_SARRACENIA = registerBlock("tall_sarracenia", () -> new TallSarraceniaBlock(UP2BlockProperties.TALL_PLANT));

    public static final RegistryObject<Block> QUEREUXIA = registerBlock("quereuxia", () -> new QuereuxiaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().randomTicks().noCollission().noOcclusion().sound(SoundType.WET_GRASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> QUEREUXIA_PLANT = registerBlockWithoutItem("quereuxia_plant", () -> new QuereuxiaPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().randomTicks().noCollission().noOcclusion().sound(SoundType.WET_GRASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> QUEREUXIA_CLOVERS = registerBlockWithItem("quereuxia_clovers", () -> new QuereuxiaCloversBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().noCollission().noOcclusion().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY)), block -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));

    public static final RegistryObject<Block> ANOSTYLOSTROMA = registerBlock("anostylostroma", () -> new AnostylostromaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> PETRIFIED_ANOSTYLOSTROMA = registerBlock("petrified_anostylostroma", () -> new PetrifiedAnostylostromaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1.0F).sound(SoundType.STONE)));

    // coral
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL_BLOCK = registerBlock("dead_clathrodictyon_coral_block", () -> new Block(UP2BlockProperties.DEAD_CORAL_BLOCK));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL = registerBlock("dead_clathrodictyon_coral", () -> new BaseCoralPlantBlock(UP2BlockProperties.DEAD_CORAL));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL_WALL_FAN = registerBlockWithoutItem("dead_clathrodictyon_coral_wall_fan", () -> new BaseCoralWallFanBlock(UP2BlockProperties.DEAD_CORAL));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL_FAN = registerBlockWithoutItem("dead_clathrodictyon_coral_fan", () -> new BaseCoralFanBlock(UP2BlockProperties.DEAD_CORAL));

    public static final RegistryObject<Block> CLATHRODICTYON_CORAL_BLOCK = registerBlock("clathrodictyon_coral_block", () -> new CoralBlock(DEAD_CLATHRODICTYON_CORAL_BLOCK.get(), UP2BlockProperties.coralBlock(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> CLATHRODICTYON_CORAL = registerBlock("clathrodictyon_coral", () -> new CoralPlantBlock(DEAD_CLATHRODICTYON_CORAL.get(), UP2BlockProperties.coral(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> CLATHRODICTYON_CORAL_WALL_FAN = registerBlockWithoutItem("clathrodictyon_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_CLATHRODICTYON_CORAL_WALL_FAN.get(), UP2BlockProperties.coral(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> CLATHRODICTYON_CORAL_FAN = registerBlockWithoutItem("clathrodictyon_coral_fan", () -> new CoralFanBlock(DEAD_CLATHRODICTYON_CORAL_FAN.get(), UP2BlockProperties.coral(MapColor.COLOR_CYAN)));

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
}
