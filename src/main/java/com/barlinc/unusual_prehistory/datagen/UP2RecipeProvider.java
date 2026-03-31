package com.barlinc.unusual_prehistory.datagen;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.data.TransmogrificationBuilder;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.barlinc.unusual_prehistory.registry.UP2Blocks.*;
import static net.minecraft.data.recipes.RecipeCategory.*;

public class UP2RecipeProvider extends RecipeProvider implements IConditionBuilder {

    public UP2RecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(MISC, UP2Items.PALEOPEDIA.get(), 1).requires(Items.BOOK).requires(Tags.Items.BONES).unlockedBy("has_book", has(Items.BOOK)).save(consumer);

        ShapedRecipeBuilder.shaped(MISC, UP2Blocks.TRANSMOGRIFIER.get()).define('#', Tags.Items.INGOTS_GOLD).define('X', UP2Items.MACHINE_PARTS.get()).define('Y', Tags.Items.DUSTS_REDSTONE).define('Z', Tags.Items.INGOTS_COPPER).pattern("###").pattern("ZXZ").pattern("#Y#").unlockedBy("has_machine_parts", has(UP2Items.MACHINE_PARTS.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(MISC, UP2Items.ORGANIC_OOZE.get(), 2).requires(Tags.Items.SLIMEBALLS).requires(Items.ROTTEN_FLESH).requires(Items.SUGAR).requires(Tags.Items.MUSHROOMS).unlockedBy("has_slime", has(Items.SLIME_BALL)).save(consumer);

        ShapelessRecipeBuilder.shapeless(MISC, UP2Items.DIRT_ON_A_STICK.get(), 1).requires(Tags.Items.TOOLS_FISHING_RODS).requires(Blocks.DIRT.asItem()).unlockedBy("has_fishing_rod", has(Tags.Items.TOOLS_FISHING_RODS)).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PALEOSTONE_STAIRS.get(), 4).define('G', PALEOSTONE.get()).pattern("G  ").pattern("GG ").pattern("GGG").unlockedBy("has_greynite", has(PALEOSTONE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PALEOSTONE_SLAB.get(), 6).define('G', PALEOSTONE.get()).pattern("GGG").unlockedBy("has_greynite", has(PALEOSTONE.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MESONITE_STAIRS.get(), 4).define('R', MESONITE.get()).pattern("R  ").pattern("RR ").pattern("RRR").unlockedBy("has_ranatite", has(MESONITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MESONITE_SLAB.get(), 6).define('R', MESONITE.get()).pattern("RRR").unlockedBy("has_ranatite", has(MESONITE.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FLORALITE_STAIRS.get(), 4).define('V', FLORALITE.get()).pattern("V  ").pattern("VV ").pattern("VVV").unlockedBy("has_vistostone", has(FLORALITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FLORALITE_SLAB.get(), 6).define('V', FLORALITE.get()).pattern("VVV").unlockedBy("has_vistostone", has(FLORALITE.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(MISC, FOSSILIZED_SKULL_LANTERN.get(), 1).requires(FOSSILIZED_SKULL.get()).requires(Items.TORCH).unlockedBy("has_fossilized_skull", has(FOSSILIZED_SKULL.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(MISC, FOSSILIZED_SKULL_SOUL_LANTERN.get(), 1).requires(FOSSILIZED_SKULL.get()).requires(Items.SOUL_TORCH).unlockedBy("has_fossilized_skull", has(FOSSILIZED_SKULL.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(DECORATIONS, UP2Items.PALEOZOIC_BANNER_PATTERN.get(), 1).requires(Items.PAPER).requires(UP2ItemTags.PALEOZOIC_FOSSILS).unlockedBy("has_paleozoic_fossil", has(UP2ItemTags.PALEOZOIC_FOSSILS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DECORATIONS, UP2Items.MESOZOIC_BANNER_PATTERN.get(), 1).requires(Items.PAPER).requires(UP2ItemTags.MESOZOIC_FOSSILS).unlockedBy("has_mesozoic_fossil", has(UP2ItemTags.MESOZOIC_FOSSILS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DECORATIONS, UP2Items.CENOZOIC_BANNER_PATTERN.get(), 1).requires(Items.PAPER).requires(UP2ItemTags.CENOZOIC_FOSSILS).unlockedBy("has_cenozoic_fossil", has(UP2ItemTags.CENOZOIC_FOSSILS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DECORATIONS, UP2Items.OOZE_BANNER_PATTERN.get(), 1).requires(Items.PAPER).requires(UP2Items.ORGANIC_OOZE.get()).unlockedBy("has_organic_ooze", has(UP2Items.ORGANIC_OOZE.get())).save(consumer);

        woodSet(UP2ItemTags.DRYOPHYLLUM_LOGS, DRYOPHYLLUM_PLANKS.get(), DRYOPHYLLUM_SLAB.get(), DRYOPHYLLUM_STAIRS.get(), DRYOPHYLLUM_LOG.get(), DRYOPHYLLUM_WOOD.get(), STRIPPED_DRYOPHYLLUM_LOG.get(), STRIPPED_DRYOPHYLLUM_WOOD.get(), UP2Items.DRYOPHYLLUM_BOAT.get(), UP2Items.DRYOPHYLLUM_CHEST_BOAT.get(), DRYOPHYLLUM_BUTTON.get(), DRYOPHYLLUM_DOOR.get(), DRYOPHYLLUM_TRAPDOOR.get(), DRYOPHYLLUM_FENCE.get(), DRYOPHYLLUM_FENCE_GATE.get(), DRYOPHYLLUM_PRESSURE_PLATE.get(), UP2Blocks.DRYOPHYLLUM_SIGN.get(), UP2Blocks.DRYOPHYLLUM_HANGING_SIGN.get(), consumer);

        woodSet(UP2ItemTags.GINKGO_LOGS, GINKGO_PLANKS.get(), GINKGO_SLAB.get(), GINKGO_STAIRS.get(), GINKGO_LOG.get(), GINKGO_WOOD.get(), STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get(), UP2Items.GINKGO_BOAT.get(), UP2Items.GINKGO_CHEST_BOAT.get(), GINKGO_BUTTON.get(), GINKGO_DOOR.get(), GINKGO_TRAPDOOR.get(), GINKGO_FENCE.get(), GINKGO_FENCE_GATE.get(), GINKGO_PRESSURE_PLATE.get(), UP2Blocks.GINKGO_SIGN.get(), UP2Blocks.GINKGO_HANGING_SIGN.get(), consumer);

        woodSet(UP2ItemTags.LEPIDODENDRON_LOGS, LEPIDODENDRON_PLANKS.get(), LEPIDODENDRON_SLAB.get(), LEPIDODENDRON_STAIRS.get(), LEPIDODENDRON_LOG.get(), LEPIDODENDRON_WOOD.get(), STRIPPED_LEPIDODENDRON_LOG.get(), STRIPPED_LEPIDODENDRON_WOOD.get(), UP2Items.LEPIDODENDRON_BOAT.get(), UP2Items.LEPIDODENDRON_CHEST_BOAT.get(), LEPIDODENDRON_BUTTON.get(), LEPIDODENDRON_DOOR.get(), LEPIDODENDRON_TRAPDOOR.get(), LEPIDODENDRON_FENCE.get(), LEPIDODENDRON_FENCE_GATE.get(), LEPIDODENDRON_PRESSURE_PLATE.get(), UP2Blocks.LEPIDODENDRON_SIGN.get(), UP2Blocks.LEPIDODENDRON_HANGING_SIGN.get(), consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MOSSY_LEPIDODENDRON_WOOD.get(), 3).define('L', MOSSY_LEPIDODENDRON_LOG.get()).pattern("LL").pattern("LL").group("bark").unlockedBy("has_log", has(LEPIDODENDRON_LOG.get())).save(consumer);

        woodSet(UP2ItemTags.METASEQUOIA_LOGS, METASEQUOIA_PLANKS.get(), METASEQUOIA_SLAB.get(), METASEQUOIA_STAIRS.get(), METASEQUOIA_LOG.get(), METASEQUOIA_WOOD.get(), STRIPPED_METASEQUOIA_LOG.get(), STRIPPED_METASEQUOIA_WOOD.get(), UP2Items.METASEQUOIA_BOAT.get(), UP2Items.METASEQUOIA_CHEST_BOAT.get(), METASEQUOIA_BUTTON.get(), METASEQUOIA_DOOR.get(), METASEQUOIA_TRAPDOOR.get(), METASEQUOIA_FENCE.get(), METASEQUOIA_FENCE_GATE.get(), METASEQUOIA_PRESSURE_PLATE.get(), UP2Blocks.METASEQUOIA_SIGN.get(), UP2Blocks.METASEQUOIA_HANGING_SIGN.get(), consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PETRIFIED_WOOD.get(), 3).define('P', PETRIFIED_LOG.get()).pattern("PP").pattern("PP").group("bark").unlockedBy("has_log", has(PETRIFIED_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, POLISHED_PETRIFIED_WOOD.get(), 4).define('P', PETRIFIED_WOOD.get()).pattern("PP").pattern("PP").unlockedBy("has_log", has(PETRIFIED_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, POLISHED_PETRIFIED_WOOD_STAIRS.get(), 4).define('P', POLISHED_PETRIFIED_WOOD.get()).pattern("P  ").pattern("PP ").pattern("PPP").unlockedBy("has_log", has(PETRIFIED_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, POLISHED_PETRIFIED_WOOD_SLAB.get(), 6).define('P', POLISHED_PETRIFIED_WOOD.get()).pattern("PPP").unlockedBy("has_log", has(PETRIFIED_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get()).define('P', POLISHED_PETRIFIED_WOOD.get()).pattern("PP").unlockedBy("has_log", has(PETRIFIED_LOG.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, POLISHED_PETRIFIED_WOOD_BUTTON.get(), 1).requires(POLISHED_PETRIFIED_WOOD.get()).unlockedBy("has_log", has(PETRIFIED_LOG.get())).save(consumer);
        stonecutting(PETRIFIED_LOG.get(), PETRIFIED_WOOD.get(), 1, consumer);
        stonecutting(PETRIFIED_LOG.get(), POLISHED_PETRIFIED_WOOD.get(), 1, consumer);
        stonecutting(POLISHED_PETRIFIED_WOOD.get(), POLISHED_PETRIFIED_WOOD_STAIRS.get(), 1, consumer);
        stonecutting(PETRIFIED_LOG.get(), POLISHED_PETRIFIED_WOOD_SLAB.get(), 2, consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FOSSILIZED_BONE_BARK.get(), 3).define('F', FOSSILIZED_BONE_BLOCK.get()).pattern("FF").pattern("FF").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, COBBLED_FOSSILIZED_BONE.get(), 4).define('F', FOSSILIZED_BONE_BARK.get()).pattern("FF").pattern("FF").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, COBBLED_FOSSILIZED_BONE_STAIRS.get(), 4).define('C', COBBLED_FOSSILIZED_BONE.get()).pattern("C  ").pattern("CC ").pattern("CCC").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, COBBLED_FOSSILIZED_BONE_SLAB.get(), 6).define('C', COBBLED_FOSSILIZED_BONE.get()).pattern("CCC").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FOSSILIZED_BONE_VERTEBRA.get(), 2).define('F', FOSSILIZED_BONE_BLOCK.get()).pattern("FF").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FOSSILIZED_BONE_BLOCK.get(), 2).define('F', FOSSILIZED_BONE_VERTEBRA.get()).pattern("FF").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FOSSILIZED_BONE_ROD.get(), 4).define('F', FOSSILIZED_BONE_BLOCK.get()).pattern("F").pattern("F").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FOSSILIZED_BONE_ROW.get(), 6).define('F', FOSSILIZED_BONE_BLOCK.get()).pattern("FFF").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, FOSSILIZED_BONE_SPIKE.get(), 2).define('F', FOSSILIZED_BONE_ROD.get()).pattern("F").pattern("F").unlockedBy("has_fossilized_bone", has(FOSSILIZED_BONE_BLOCK.get())).save(consumer);
        stonecutting(FOSSILIZED_BONE_BLOCK.get(), FOSSILIZED_BONE_BARK.get(), 1, consumer);
        stonecutting(FOSSILIZED_BONE_BLOCK.get(), COBBLED_FOSSILIZED_BONE.get(), 1, consumer);
        stonecutting(FOSSILIZED_BONE_BLOCK.get(), FOSSILIZED_BONE_VERTEBRA.get(), 1, consumer);
        stonecutting(FOSSILIZED_BONE_BLOCK.get(), FOSSILIZED_BONE_ROD.get(), 2, consumer);
        stonecutting(FOSSILIZED_BONE_BLOCK.get(), FOSSILIZED_BONE_ROW.get(), 2, consumer);
        stonecutting(FOSSILIZED_BONE_BLOCK.get(), FOSSILIZED_BONE_SPIKE.get(), 2, consumer);
        stonecutting(COBBLED_FOSSILIZED_BONE.get(), COBBLED_FOSSILIZED_BONE_STAIRS.get(), 1, consumer);
        stonecutting(COBBLED_FOSSILIZED_BONE.get(), COBBLED_FOSSILIZED_BONE_SLAB.get(), 2, consumer);
        stonecutting(FOSSILIZED_BONE_VERTEBRA.get(), FOSSILIZED_BONE_BLOCK.get(), 1, consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MOSSY_DIRT.get(), 8).define('D', Blocks.DIRT.asItem()).define('M', MOSS_LAYER.get()).pattern("DDD").pattern("DMD").pattern("DDD").unlockedBy("has_moss_layer", has(MOSS_LAYER.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_COLORLESS).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, TINTED_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_TINTED).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, WHITE_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_WHITE).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIGHT_GRAY_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_LIGHT_GRAY).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, GRAY_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_GRAY).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BLACK_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_BLACK).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BROWN_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_BROWN).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, RED_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_RED).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORANGE_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_ORANGE).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, YELLOW_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_YELLOW).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIME_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_LIME).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, GREEN_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_GREEN).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, CYAN_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_CYAN).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIGHT_BLUE_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_LIGHT_BLUE).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BLUE_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_BLUE).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PURPLE_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_PURPLE).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MAGENTA_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_MAGENTA).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PINK_REINFORCED_GLASS.get(), 8).define('G', Tags.Items.GLASS_PINK).define('I', Tags.Items.INGOTS_IRON).pattern("GGG").pattern("GIG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, WHITE_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_WHITE).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("white_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIGHT_GRAY_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_LIGHT_GRAY).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("light_gray_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, GRAY_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_GRAY).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("gray_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BLACK_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_BLACK).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("black_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BROWN_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_BROWN).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("brown_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, RED_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_RED).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("red_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORANGE_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_ORANGE).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("orange_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, YELLOW_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_YELLOW).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("yellow_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIME_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_LIME).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("lime_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, GREEN_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_GREEN).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("green_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, CYAN_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_CYAN).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("cyan_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIGHT_BLUE_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_LIGHT_BLUE).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("light_blue_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BLUE_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_BLUE).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("blue_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PURPLE_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_PURPLE).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("purple_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MAGENTA_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_MAGENTA).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("magenta_reinforced_glass_from_dye"));
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PINK_REINFORCED_GLASS.get(), 8).define('G', UP2ItemTags.REINFORCED_GLASS).define('D', Tags.Items.DYES_PINK).pattern("GGG").pattern("GDG").pattern("GGG").unlockedBy("has_glass", has(Tags.Items.GLASS)).save(consumer, getSaveLocation("pink_reinforced_glass_from_dye"));

        transmogrification(consumer, UP2Items.BRISTLE_FOSSIL.get(), AEGIROCASSIS_EGGS.get().asItem(), 3600);
        transmogrification(consumer, UP2Items.ARM_FOSSIL.get(), BRACHIOSAURUS_EGG.get().asItem(), 3600);
        transmogrification(consumer, UP2Items.FURY_FOSSIL.get(), CARNOTAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.GLUTTONOUS_FOSSIL.get(), COELACANTHUS_ROE.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.FLAT_BACK_FOSSIL.get(), DESMATOSUCHUS_EGG.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.BOOMERANG_FOSSIL.get(), DIPLOCAULUS_EGGS.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.RUNNER_FOSSIL.get(), UP2Items.DROMAEOSAURUS_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.GUILLOTINE_FOSSIL.get(), DUNKLEOSTEUS_SAC.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.PLOW_FOSSIL.get(), HIBBERTOPTERUS_EGGS.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.JAWLESS_FOSSIL.get(), JAWLESS_FISH_ROE.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.BOAR_TOOTH_FOSSIL.get(), KAPROSUCHUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.IMPERATIVE_FOSSIL.get(), KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.PRICKLY_FOSSIL.get(), KENTROSAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.TRUNK_MOUSE_FOSSIL.get(), UP2Items.LEPTICTIDIUM_EMBRYO.get(), 1200);
        transmogrification(consumer, UP2Items.FISH_FOSSIL.get(), LOBE_FINNED_FISH_ROE.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.IMPERVIOUS_FOSSIL.get(), LYSTROSAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.RUGOSE_FOSSIL.get(), MAJUNGASAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.THERMAL_FOSSIL.get(), MEGALANIA_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.MELTDOWN_FOSSIL.get(), UP2Items.METRIORHYNCHUS_EMBRYO.get(), 2400);
        transmogrification(consumer, UP2Items.SAW_FOSSIL.get(), ONCHOPRISTIS_SAC.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.CRANIUM_FOSSIL.get(), PACHYCEPHALOSAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.FLIPPER_FOSSIL.get(), UP2Items.PRAEPUSA_EMBRYO.get(), 1200);
        transmogrification(consumer, UP2Items.WING_FOSSIL.get(), UP2Items.PTERODACTYLUS_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.ANVIL_FOSSIL.get(), STETHACANTHUS_SAC.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.AGED_FEATHER.get(), UP2Items.TALPANAS_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.MOSSY_FOSSIL.get(), TARTUOSTEUS_ROE.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.PLUMAGE_FOSSIL.get(), UP2Items.TELECREX_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.DUBIOUS_FOSSIL.get(), ULUGHBEGSAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.CROOKED_BEAK_FOSSIL.get(), UP2Items.PSILOPTERUS_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.SURGE_FOSSIL.get(), UP2Items.MOSASAURUS_EMBRYO.get(), 2400);

        transmogrification(consumer, UP2Items.AETHOPHYLLUM_FOSSIL.get(), AETHOPHYLLUM.get().asItem(), 600);
        transmogrification(consumer, UP2Items.BENNETTITALES_FOSSIL.get(), BENNETTITALES.get().asItem(), 600);
        transmogrification(consumer, UP2Items.BRACHYPHYLLUM_FOSSIL.get(), BRACHYPHYLLUM.get().asItem(), 600);
        transmogrification(consumer, UP2Items.CALAMOPHYTON_FOSSIL.get(), CALAMOPHYTON.get().asItem(), 600);
        transmogrification(consumer, UP2Items.CLADOPHLEBIS_FOSSIL.get(), CLADOPHLEBIS.get().asItem(), 600);
        transmogrification(consumer, UP2Items.COOKSONIA_FOSSIL.get(), COOKSONIA.get().asItem(), 600);
        transmogrification(consumer, UP2Items.CYCAD_FOSSIL.get(), CYCAD_SEEDLING.get().asItem(), 600);
        transmogrification(consumer, UP2Items.GINKGO_FOSSIL.get(), GINKGO_SAPLING.get().asItem(), 600);
        transmogrification(consumer, UP2Items.GUANGDEDENDRON_FOSSIL.get(), GUANGDEDENDRON.get().asItem(), 600);
        transmogrification(consumer, UP2Items.NEOMARIOPTERIS_FOSSIL.get(), NEOMARIOPTERIS.get().asItem(), 600);
        transmogrification(consumer, UP2Items.PROTOTAXITES_FOSSIL.get(), PROTOTAXITES_CLUSTER.get().asItem(), 600);
        transmogrification(consumer, UP2Items.QUILLWORT_FOSSIL.get(), QUILLWORT.get().asItem(), 600);
        transmogrification(consumer, UP2Items.LEEFRUCTUS_FOSSIL.get(), LEEFRUCTUS.get().asItem(), 600);
        transmogrification(consumer, UP2Items.LEPIDODENDRON_FOSSIL.get(), LEPIDODENDRON_CONE.get().asItem(), 600);
        transmogrification(consumer, UP2Items.RAIGUENRAYUN_FOSSIL.get(), RAIGUENRAYUN.get().asItem(), 600);
        transmogrification(consumer, UP2Items.RHYNIA_FOSSIL.get(), RHYNIA.get().asItem(), 600);
        transmogrification(consumer, UP2Items.TEMPSKYA_FOSSIL.get(), TEMPSKYA.get().asItem(), 600);
        transmogrification(consumer, UP2Items.METASEQUOIA_FOSSIL.get(), METASEQUOIA_SAPLING.get().asItem(), 600);
        transmogrification(consumer, UP2Items.DRYOPHYLLUM_FOSSIL.get(), DRYOPHYLLUM_SAPLING.get().asItem(), 600);

    }

    private static void conditionalRecipe(RecipeBuilder recipe, ICondition condition, Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())).build(consumer, id);
    }

    private static void woodSet(TagKey<Item> logs, Block planks, Block slab, Block stairs, Block log, Block wood, Block strippedLog, Block strippedWood, ItemLike boat, ItemLike chestBoat, Block button, Block door, Block trapdoor, Block fence, Block fenceGate, Block pressurePlate, Block sign, Block hangingSign, Consumer<FinishedRecipe> consumer) {
        woodenBoat(consumer, boat, planks);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat).group("chest_boat").requires(Tags.Items.CHESTS_WOODEN).requires(boat).unlockedBy(getHasName(boat), has(boat)).save(consumer, getSaveLocation(chestBoat));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button).group("wooden_button").requires(planks).unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(button));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, door, 3).group("wooden_door").define('#', planks).pattern("##").pattern("##").pattern("##").unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(door));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, 3).group("wooden_fence").define('#', planks).define('S', Items.STICK).pattern("#S#").pattern("#S#").unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(fence));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fenceGate).group("wooden_fence_gate").define('#', planks).define('S', Items.STICK).pattern("S#S").pattern("S#S").unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(fenceGate));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, pressurePlate).group("wooden_pressure_plate").define('#', planks).pattern("##").unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(pressurePlate));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 3).group("wooden_sign").define('#', planks).define('S', Items.STICK).pattern("###").pattern("###").pattern(" S ").unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(sign));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, trapdoor, 2).group("wooden_trapdoor").define('#', planks).pattern("###").pattern("###").unlockedBy(getHasName(planks), has(planks)).save(consumer, getSaveLocation(trapdoor));
        hangingSign(consumer, hangingSign, strippedLog);
        planksFromLogs(consumer, planks, logs, 4);
        woodFromLogs(consumer, wood, log);
        woodFromLogs(consumer, strippedWood, strippedLog);
        slab(planks, slab, "wooden_slab", consumer);
        stairs(planks, stairs, "wooden_stairs", consumer);
    }

    private static void stairs(ItemLike ingredient, ItemLike stairs, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4).define('#', ingredient).pattern("#  ").pattern("## ").pattern("###").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(stairs)));
    }

    private static void stairs(ItemLike ingredient, ItemLike stairs, String group, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4).group(group).define('#', ingredient).pattern("#  ").pattern("## ").pattern("###").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(stairs)));
    }

    private static void wall(ItemLike ingredient, ItemLike wall, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6).define('#', ingredient).pattern("###").pattern("###").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(wall)));
    }

    private static void slab(ItemLike ingredient, ItemLike slab, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6).define('#', ingredient).pattern("###").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(slab)));
    }

    private static void slab(ItemLike ingredient, ItemLike slab, String group, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6).group(group).define('#', ingredient).pattern("###").unlockedBy("has_" + getName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(slab)));
    }

    public ShapedRecipeBuilder door(Supplier<? extends Block> doorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, doorOut.get(), 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .define('P', plankIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(plankIn.get()).getPath(), has(plankIn.get()));
    }

    public ShapedRecipeBuilder trapdoor(Supplier<? extends Block> trapdoorOut, Supplier<? extends Block> plankIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, trapdoorOut.get(), 2)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', plankIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(plankIn.get()).getPath(), has(plankIn.get()));
    }

    public ShapelessRecipeBuilder button(Supplier<? extends Block> buttonOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, buttonOut.get())
                .requires(blockIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder pressurePlate(Supplier<? extends Block> pressurePlateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pressurePlateOut.get())
                .pattern("BB")
                .define('B', blockIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder stairs(Supplier<? extends Block> stairsOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairsOut.get(), 4)
                .pattern("M  ")
                .pattern("MM ")
                .pattern("MMM")
                .define('M', blockIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder slab(Supplier<? extends Block> slabOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slabOut.get(), 6)
                .pattern("MMM")
                .define('M', blockIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder fence(Supplier<? extends Block> fenceOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceOut.get(), 3)
                .pattern("M/M")
                .pattern("M/M")
                .define('M', blockIn.get())
                .define('/', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGateOut, Supplier<? extends Block> blockIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceGateOut.get())
                .pattern("/M/")
                .pattern("/M/")
                .define('M', blockIn.get())
                .define('/', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(blockIn.get()).getPath(), has(blockIn.get()));
    }

    public ShapelessRecipeBuilder planks(Supplier<? extends Block> plankOut, TagKey<Item> logIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankOut.get(), 4)
                .requires(logIn)
                .group("planks")
                .unlockedBy("has_log", has(logIn));
    }

    public ShapedRecipeBuilder wood(Supplier<? extends Block> woodOut, Supplier<? extends Block> logIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, woodOut.get(), 3)
                .pattern("MM")
                .pattern("MM")
                .define('M', logIn.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(logIn.get()).getPath(), has(logIn.get()));
    }

    private static void stonecutting(ItemLike ingredient, ItemLike result, int amount, Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), BUILDING_BLOCKS, result, amount).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(result) + "_from_stonecutting"));
    }

    protected static void transmogrification(Consumer<FinishedRecipe> consumer, Item input, Item result, int processingTime) {
        Ingredient ingredient = Ingredient.of(input);
        TransmogrificationBuilder.transmogrification(ingredient, result, processingTime).save(consumer, UnusualPrehistory2.modPrefix("transmogrification/" + getItemName(result)));
    }

    private static String getName(ItemLike object) {
        return ForgeRegistries.ITEMS.getKey(object.asItem()).getPath();
    }

    private static ResourceLocation getSaveLocation(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem());
    }

    private static ResourceLocation getSaveLocation(String name) {
        return UnusualPrehistory2.modPrefix(name);
    }
}
