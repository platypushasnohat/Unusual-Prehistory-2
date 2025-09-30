package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.data.custom.ChiselingBuilder;
import com.unusualmodding.unusual_prehistory.data.custom.TransmogrificationBuilder;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
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

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;
import static com.unusualmodding.unusual_prehistory.registry.UP2Blocks.*;
import static net.minecraft.data.recipes.RecipeCategory.*;

public class UP2RecipeProvider extends RecipeProvider implements IConditionBuilder {

    public UP2RecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(MISC, UP2Items.CHISEL.get()).define('#', Tags.Items.INGOTS_IRON).define('X', Tags.Items.LEATHER).define('Y', Tags.Items.RODS_WOODEN).pattern("#").pattern("X").pattern("Y").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer);

        ShapedRecipeBuilder.shaped(MISC, UP2Blocks.TRANSMOGRIFIER.get()).define('#', Tags.Items.INGOTS_IRON).define('X', UP2ItemTags.TRANSMOGRIFIER_FUEL).define('Y', Tags.Items.DUSTS_REDSTONE).define('Z', Tags.Items.GLASS).pattern("###").pattern("ZXZ").pattern("#Y#").unlockedBy("has_organic_ooze", has(UP2ItemTags.TRANSMOGRIFIER_FUEL)).save(consumer);

        ShapelessRecipeBuilder.shapeless(MISC, UP2Items.ORGANIC_OOZE.get(), 2).requires(Tags.Items.SLIMEBALLS).requires(Items.ROTTEN_FLESH).requires(Items.SUGAR).requires(Tags.Items.MUSHROOMS).unlockedBy("has_slime", has(Items.SLIME_BALL)).save(consumer);

        woodSet(UP2ItemTags.GINKGO_LOGS, GINKGO_PLANKS.get(), GINKGO_SLAB.get(), GINKGO_STAIRS.get(), GINKGO_LOG.get(), GINKGO_WOOD.get(), STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get(), UP2Items.GINKGO_BOAT.get(), UP2Items.GINKGO_CHEST_BOAT.get(), GINKGO_BUTTON.get(), GINKGO_DOOR.get(), GINKGO_TRAPDOOR.get(), GINKGO_FENCE.get(), GINKGO_FENCE_GATE.get(), GINKGO_PRESSURE_PLATE.get(), UP2Blocks.GINKGO_SIGN.get(), UP2Blocks.GINKGO_HANGING_SIGN.get(), consumer);
        woodSet(UP2ItemTags.LEPIDODENDRON_LOGS, LEPIDODENDRON_PLANKS.get(), LEPIDODENDRON_SLAB.get(), LEPIDODENDRON_STAIRS.get(), LEPIDODENDRON_LOG.get(), LEPIDODENDRON_WOOD.get(), STRIPPED_LEPIDODENDRON_LOG.get(), STRIPPED_LEPIDODENDRON_WOOD.get(), UP2Items.LEPIDODENDRON_BOAT.get(), UP2Items.LEPIDODENDRON_CHEST_BOAT.get(), LEPIDODENDRON_BUTTON.get(), LEPIDODENDRON_DOOR.get(), LEPIDODENDRON_TRAPDOOR.get(), LEPIDODENDRON_FENCE.get(), LEPIDODENDRON_FENCE_GATE.get(), LEPIDODENDRON_PRESSURE_PLATE.get(), UP2Blocks.LEPIDODENDRON_SIGN.get(), UP2Blocks.LEPIDODENDRON_HANGING_SIGN.get(), consumer);

        transmogrification(consumer, UP2Items.BOOMERANG_FOSSIL.get(), UP2Blocks.DIPLOCAULUS_EGGS.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.GUILLOTINE_FOSSIL.get(), UP2Blocks.DUNKLEOSTEUS_SAC.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.JAWLESS_FOSSIL.get(), UP2Blocks.JAWLESS_FISH_ROE.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.IMPERATIVE_FOSSIL.get(), UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get().asItem(), 1200);
        transmogrification(consumer, UP2Items.ANVIL_FOSSIL.get(), UP2Blocks.STETHACANTHUS_SAC.get().asItem(), 1200);

        transmogrification(consumer, UP2Items.FURY_FOSSIL.get(), UP2Blocks.CARNOTAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.RUNNER_FOSSIL.get(), UP2Items.DROMAEOSAURUS_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.PRICKLY_FOSSIL.get(), UP2Blocks.KENTROSAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.RUGOSE_FOSSIL.get(), UP2Blocks.MAJUNGASAURUS_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.THERMAL_FOSSIL.get(), UP2Blocks.MEGALANIA_EGG.get().asItem(), 2400);
        transmogrification(consumer, UP2Items.AGED_FEATHER.get(), UP2Items.TALPANAS_EGG.get(), 1200);
        transmogrification(consumer, UP2Items.PLUMAGE_FOSSIL.get(), UP2Items.TELECREX_EGG.get(), 1200);

        transmogrification(consumer, UP2Items.TRUNK_FOSSIL.get(), UP2Blocks.BENNETTITALES.get().asItem(), 600);
        transmogrification(consumer, UP2Items.BARK_FOSSIL.get(), UP2Blocks.CALAMOPHYTON.get().asItem(), 600);
        transmogrification(consumer, UP2Items.SPORE_FOSSIL.get(), UP2Blocks.CLADOPHLEBIS.get().asItem(), 600);
        transmogrification(consumer, UP2Items.SPINDLY_FOSSIL.get(), UP2Blocks.COOKSONIA.get().asItem(), 600);
        transmogrification(consumer, UP2Items.FAN_FOSSIL.get(), UP2Blocks.GINKGO_SAPLING.get().asItem(), 600);
        transmogrification(consumer, UP2Items.VASCULAR_FOSSIL.get(), UP2Blocks.HORSETAIL.get().asItem(), 600);
        transmogrification(consumer, UP2Items.LEAFY_FOSSIL.get(), UP2Blocks.ISOETES.get().asItem(), 600);
        transmogrification(consumer, UP2Items.FLOWERING_FOSSIL.get(), UP2Blocks.LEEFRUCTUS.get().asItem(), 600);
        transmogrification(consumer, UP2Items.SAPLING_FOSSIL.get(), UP2Blocks.LEPIDODENDRON_CONE.get().asItem(), 600);
        transmogrification(consumer, UP2Items.BLOOMED_FOSSIL.get(), UP2Blocks.RAIGUENRAYUN.get().asItem(), 600);
        transmogrification(consumer, UP2Items.FIBROUS_FOSSIL.get(), UP2Blocks.RHYNIA.get().asItem(), 600);

        chiseling(consumer, Blocks.STONE, Blocks.STONE_BRICKS);
        chiseling(consumer, Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
        chiseling(consumer, Blocks.STONE_STAIRS, Blocks.STONE_BRICK_STAIRS);
        chiseling(consumer, Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB);

        chiseling(consumer, Blocks.COBBLED_DEEPSLATE, Blocks.CHISELED_DEEPSLATE);

        chiseling(consumer, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE);
        chiseling(consumer, Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE);
        chiseling(consumer, Blocks.NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS);
        chiseling(consumer, Blocks.POLISHED_BLACKSTONE, Blocks.CHISELED_POLISHED_BLACKSTONE);
        chiseling(consumer, Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK);
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

    protected static void transmogrification(Consumer<FinishedRecipe> consumer, Item input, Item result, int processingTime) {
        Ingredient ingredient = Ingredient.of(input);
        TransmogrificationBuilder.transmogrification(ingredient, result, processingTime).save(consumer, modPrefix("transmogrification/" + getItemName(result)));
    }

    protected static void chiseling(Consumer<FinishedRecipe> consumer, Block from, Block to) {
        ChiselingBuilder.chiseling(from, to).save(consumer, modPrefix("chiseling/" + getItemName(to)));
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
