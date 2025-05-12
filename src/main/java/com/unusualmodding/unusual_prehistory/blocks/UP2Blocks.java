package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.custom.*;
import com.unusualmodding.unusual_prehistory.items.UP2Items;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final RegistryObject<Block> MOSSY_DIRT = registerBlock("mossy_dirt", () -> new MossyDirtBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.5F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> CALAMOPHYTON = registerBlock("calamophyton", () -> new CalamophytonBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> BENNETTITALES = registerBlock("bennettitales", () -> new AncientPlantBlock(UP2Properties.PLANT_PROPERTIES));
    public static final RegistryObject<Block> POTTED_BENNETTITALES = registerBlockWithoutItem("potted_bennettitales", () -> new FlowerPotBlock(UP2Blocks.BENNETTITALES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> COOKSONIA = registerBlock("cooksonia", () -> new AncientFlowerBlock(() -> MobEffects.REGENERATION, 9, UP2Properties.PLANT_PROPERTIES));
    public static final RegistryObject<Block> POTTED_COOKSONIA = registerBlockWithoutItem("potted_cooksonia", () -> new FlowerPotBlock(UP2Blocks.COOKSONIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CLADOPHLEBIS = registerBlock("cladophlebis", () -> new AncientPlantBlock(UP2Properties.PLANT_PROPERTIES));
    public static final RegistryObject<Block> POTTED_CLADOPHLEBIS = registerBlockWithoutItem("potted_cladophlebis", () -> new FlowerPotBlock(UP2Blocks.CLADOPHLEBIS.get(), registerFlowerPot()));

    public static final RegistryObject<Block> QUILLWORTS = registerBlock("quillworts", () -> new AncientPlantBlock(UP2Properties.PLANT_PROPERTIES));
    public static final RegistryObject<Block> POTTED_QUILLWORTS = registerBlockWithoutItem("potted_quillworts", () -> new FlowerPotBlock(UP2Blocks.QUILLWORTS.get(), registerFlowerPot()));

    public static final RegistryObject<Block> LEEFRUCTUS = registerBlock("leefructus", () -> new AncientFlowerBlock(() -> MobEffects.ABSORPTION, 9, UP2Properties.PLANT_PROPERTIES));
    public static final RegistryObject<Block> POTTED_LEEFRUCTUS = registerBlockWithoutItem("potted_leefructus", () -> new FlowerPotBlock(UP2Blocks.LEEFRUCTUS.get(), registerFlowerPot()));

    public static final RegistryObject<Block> RHYNIA = registerBlock("rhynia", () -> new AncientPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_RHYNIA = registerBlockWithoutItem("potted_rhynia", () -> new FlowerPotBlock(UP2Blocks.RHYNIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SARRACENIA = registerBlock("sarracenia", () -> new AncientFlowerBlock(() -> MobEffects.HEALTH_BOOST, 9, UP2Properties.PLANT_PROPERTIES));
    public static final RegistryObject<Block> POTTED_SARRACENIA = registerBlockWithoutItem("potted_sarracenia", () -> new FlowerPotBlock(UP2Blocks.SARRACENIA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> QUEREUXIA = registerBlock("quereuxia", () -> new QuereuxiaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().randomTicks().noCollission().noOcclusion().sound(SoundType.WET_GRASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> QUEREUXIA_PLANT = registerBlockWithoutItem("quereuxia_plant", () -> new QuereuxiaPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().randomTicks().noCollission().noOcclusion().sound(SoundType.WET_GRASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> QUEREUXIA_CLOVERS = registerBlockWithItem("quereuxia_clovers", () -> new QuereuxiaCloversBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().noCollission().noOcclusion().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY)), block -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));

    public static final RegistryObject<Block> ANOSTYLOSTROMA = registerBlock("anostylostroma", () -> new AnostylostromaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> PETRIFIED_ANOSTYLOSTROMA = registerBlock("petrified_anostylostroma", () -> new PetrifiedAnostylostromaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1.0F).sound(SoundType.STONE)));

    // coral
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL_BLOCK = registerBlock("dead_clathrodictyon_coral_block", () -> new Block(UP2Properties.DEAD_CORAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL = registerBlock("dead_clathrodictyon_coral", () -> new BaseCoralPlantBlock(UP2Properties.DEAD_CORAL_PROPERTIES));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL_WALL_FAN = registerBlockWithoutItem("dead_clathrodictyon_coral_wall_fan", () -> new BaseCoralWallFanBlock(UP2Properties.DEAD_CORAL_PROPERTIES));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_CORAL_FAN = registerBlockWithoutItem("dead_clathrodictyon_coral_fan", () -> new BaseCoralFanBlock(UP2Properties.DEAD_CORAL_PROPERTIES));

    public static final RegistryObject<Block> CLATHRODICTYON_CORAL_BLOCK = registerBlock("clathrodictyon_coral_block", () -> new CoralBlock(DEAD_CLATHRODICTYON_CORAL_BLOCK.get(), UP2Properties.coralBlockProperties(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> CLATHRODICTYON_CORAL = registerBlock("clathrodictyon_coral", () -> new CoralPlantBlock(DEAD_CLATHRODICTYON_CORAL.get(), UP2Properties.coralProperties(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> CLATHRODICTYON_CORAL_WALL_FAN = registerBlockWithoutItem("clathrodictyon_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_CLATHRODICTYON_CORAL_WALL_FAN.get(), UP2Properties.coralProperties(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> CLATHRODICTYON_CORAL_FAN = registerBlockWithoutItem("clathrodictyon_coral_fan", () -> new CoralFanBlock(DEAD_CLATHRODICTYON_CORAL_FAN.get(), UP2Properties.coralProperties(MapColor.COLOR_CYAN)));

    // block properties
    public static final class UP2Properties {

        public static final BlockBehaviour.Properties PLANT_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);

        public static final BlockBehaviour.Properties DEAD_CORAL_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F);
        public static final BlockBehaviour.Properties DEAD_CORAL_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().instabreak();

        public static BlockBehaviour.Properties coralProperties(MapColor color) {
            return BlockBehaviour.Properties.of().mapColor(color).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
        }

        public static BlockBehaviour.Properties coralBlockProperties(MapColor color) {
            return BlockBehaviour.Properties.of().mapColor(color).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK);
        }
    }

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
