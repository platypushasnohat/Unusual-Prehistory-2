package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static com.unusualmodding.unusual_prehistory.registry.UP2Blocks.*;

public class UP2BlockstateProvider extends BlockStateProvider {

    public UP2BlockstateProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {

        this.pillar(FROZEN_MEAT_BLOCK);

        this.simpleCross(ARCHAEFRUCTUS);
        this.generatedItem(ARCHAEFRUCTUS.get(), TextureFolder.BLOCK);

        this.pottedPlant(ARCHAEOSIGILLARIA, POTTED_ARCHAEOSIGILLARIA);
        this.pottedPlant(BENNETTITALES, POTTED_BENNETTITALES);
        this.pottedPlant(CLADOPHLEBIS, POTTED_CLADOPHLEBIS);
        this.pottedPlant(COOKSONIA, POTTED_COOKSONIA);
        this.pottedPlant(HORSETAIL, POTTED_HORSETAIL);
        this.pottedPlant(ISOETES, POTTED_ISOETES);
        this.pottedPlant(LEEFRUCTUS, POTTED_LEEFRUCTUS);
        this.pottedPlant(SARRACENIA, POTTED_SARRACENIA);

        this.tallPlant(LARGE_HORSETAIL);
        this.tallPlant(TALL_SARRACENIA);
        this.tallPlant(RAIGUENRAYUN);

        this.cubeAllBlock(ANOSTYLOSTROMA);
        this.cubeAllBlock(PETRIFIED_ANOSTYLOSTROMA);

        this.cubeAllBlock(DEAD_CLATHRODICTYON_CORAL_BLOCK);
        this.cubeAllBlock(CLATHRODICTYON_CORAL_BLOCK);

        this.simpleCross(DEAD_CLATHRODICTYON_CORAL);
        this.generatedItem(DEAD_CLATHRODICTYON_CORAL.get(), TextureFolder.BLOCK);

        this.simpleCross(CLATHRODICTYON_CORAL);
        this.generatedItem(CLATHRODICTYON_CORAL.get(), TextureFolder.BLOCK);

        this.pillar(GINKGO_LOG);
        this.wood(GINKGO_WOOD, this.blockTexture(GINKGO_LOG.get()));
        this.pillar(STRIPPED_GINKGO_LOG);
        this.wood(STRIPPED_GINKGO_WOOD, this.blockTexture(STRIPPED_GINKGO_LOG.get()));
        this.cubeAllBlock(GINKGO_PLANKS);
        this.stairs(GINKGO_STAIRS, this.blockTexture(GINKGO_PLANKS.get()));
        this.slab(GINKGO_SLAB, this.blockTexture(GINKGO_PLANKS.get()));
        this.fence(GINKGO_FENCE, this.blockTexture(GINKGO_PLANKS.get()));
        this.fenceGate(GINKGO_FENCE_GATE, this.blockTexture(GINKGO_PLANKS.get()));
        this.doorCutout(GINKGO_DOOR);
        this.trapdoorCutout(GINKGO_TRAPDOOR);
        this.pressurePlate(GINKGO_PRESSURE_PLATE, this.blockTexture(GINKGO_PLANKS.get()));
        this.button(GINKGO_BUTTON, this.blockTexture(GINKGO_PLANKS.get()));

        this.leaves(GINKGO_LEAVES);
        this.leaves(GOLDEN_GINKGO_LEAVES);
    }

    // item
    private void itemModel(RegistryObject<Block> block) {
        this.itemModels().withExistingParent(getItemName(block.get()), this.blockTexture(block.get()));
    }

    private void generatedItem(ItemLike item, TextureFolder folder) {
        String name = getItemName(item);
        this.itemModels().withExistingParent(name, "item/generated").texture("layer0", this.modLoc(folder.format(name)));
    }

    // block
    private void cubeAllBlock(RegistryObject<Block> block) {
        this.simpleBlock(block.get());
        this.itemModel(block);
    }

    private void stairs(RegistryObject<Block> stairs, ResourceLocation texture) {
        this.stairsBlock((StairBlock) stairs.get(), texture);
        this.itemModel(stairs);
    }

    private void slab(RegistryObject<Block> slab, ResourceLocation texture) {
        this.slabBlock((SlabBlock) slab.get(), texture, texture);
        this.itemModel(slab);
    }

    private void wall(RegistryObject<Block> wall, ResourceLocation texture) {
        this.wallBlock((WallBlock) wall.get(), texture);
        this.itemModels().wallInventory(getItemName(wall.get()), texture);
    }

    private void pillar(RegistryObject<Block> pillar) {
        this.axisBlock((RotatedPillarBlock) pillar.get(), this.blockTexture(pillar.get()), this.modLoc("block/" + getItemName(pillar.get()) + "_top"));
        this.itemModel(pillar);
    }

    private void wood(RegistryObject<Block> log, ResourceLocation texture) {
        this.axisBlock((RotatedPillarBlock) log.get(), texture, texture);
        this.itemModel(log);
    }

    private void fence(RegistryObject<Block> fence, ResourceLocation texture) {
        this.fenceBlock((FenceBlock) fence.get(), texture);
        this.itemModels().fenceInventory(getItemName(fence.get()), texture);
    }

    private void fenceGate(RegistryObject<Block> gate, ResourceLocation texture) {
        this.fenceGateBlock((FenceGateBlock) gate.get(), texture);
        this.itemModel(gate);
    }

    private void trapdoor(RegistryObject<Block> trapdoor) {
        this.trapdoorBlock((TrapDoorBlock) trapdoor.get(), this.blockTexture(trapdoor.get()), true);
        this.itemModels().withExistingParent(getItemName(trapdoor.get()), this.modLoc("block/" + getItemName(trapdoor.get()) + "_bottom"));
    }

    private void trapdoorCutout(RegistryObject<Block> trapdoor) {
        this.trapdoorBlockWithRenderType((TrapDoorBlock) trapdoor.get(), this.blockTexture(trapdoor.get()), true, "cutout");
        this.itemModels().withExistingParent(getItemName(trapdoor.get()), this.modLoc("block/" + getItemName(trapdoor.get()) + "_bottom"));
    }

    private void door(RegistryObject<Block> door) {
        String name = getItemName(door.get());
        this.doorBlock((DoorBlock) door.get(), name.replace("_door", ""), this.modLoc("block/" + name + "_bottom"), this.modLoc("block/" + name + "_top"));
        this.generatedItem(door.get(), TextureFolder.ITEM);
    }

    private void doorCutout(RegistryObject<Block> door) {
        String name = getItemName(door.get());
        this.doorBlockWithRenderType((DoorBlock) door.get(), name.replace("_door", ""), this.modLoc("block/" + name + "_bottom"), this.modLoc("block/" + name + "_top"), "cutout");
        this.generatedItem(door.get(), TextureFolder.ITEM);
    }

    private void button(RegistryObject<Block> button, ResourceLocation texture) {
        this.buttonBlock((ButtonBlock) button.get(), texture);
        this.itemModels().buttonInventory(getItemName(button.get()), texture);
    }

    private void pressurePlate(RegistryObject<Block> pressurePlate, ResourceLocation texture) {
        this.pressurePlateBlock((PressurePlateBlock) pressurePlate.get(), texture);
        this.itemModel(pressurePlate);
    }

    private void leaves(RegistryObject<Block> leaves) {
        this.simpleBlock(leaves.get(), this.models().withExistingParent(getItemName(leaves.get()), "block/leaves").texture("all", this.blockTexture(leaves.get())));
        this.itemModel(leaves);
    }

    private void simpleCross(RegistryObject<Block> block) {
        this.simpleBlock(block.get(), this.models().cross(getItemName(block.get()), this.blockTexture(block.get())).renderType("cutout"));
    }

    private void tallPlant(RegistryObject<Block> flower) {
        String name = getItemName(flower.get());
        Function<String, ModelFile> model = s -> this.models().cross(name + "_" + s, this.modLoc("block/" + name + "_" + s)).renderType("cutout");

        this.itemModels().withExistingParent(name, "item/generated").texture("layer0", this.modLoc("block/" + name + "_top"));
        this.getVariantBuilder(flower.get()).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(model.apply("top"))).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(model.apply("bottom")));
    }

    private void pot(RegistryObject<Block> pot, ResourceLocation texture) {
        ModelFile model = this.models().withExistingParent(getBlockName(pot.get()), "block/flower_pot_cross").texture("plant", texture).renderType("cutout");
        this.simpleBlock(pot.get(), model);
    }

    private void pottedPlant(RegistryObject<Block> plant, RegistryObject<Block> pot) {
        this.pot(pot, this.blockTexture(plant.get()));
        this.simpleCross(plant);
        this.generatedItem(plant.get(), TextureFolder.BLOCK);
    }

    // utils
    private static String getItemName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }

    private static String getBlockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private enum TextureFolder {
        ITEM, BLOCK;
        public String format(String itemName) {
            return this.name().toLowerCase() + '/' + itemName;
        }
    }
}
