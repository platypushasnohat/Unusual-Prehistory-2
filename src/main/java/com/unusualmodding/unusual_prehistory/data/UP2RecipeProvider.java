package com.unusualmodding.unusual_prehistory.data;

import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import com.unusualmodding.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.unusualmodding.unusual_prehistory.UnusualPrehistory2.modPrefix;
import static net.minecraft.data.recipes.RecipeCategory.*;

public class UP2RecipeProvider extends RecipeProvider implements IConditionBuilder {

    public UP2RecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(MISC, UP2Blocks.EXTRACTOR.get()).define('#', Tags.Items.INGOTS_IRON).define('X', UP2ItemTags.FOSSILS).define('Y', Tags.Items.DUSTS_REDSTONE).pattern("###").pattern("#X#").pattern("#Y#").unlockedBy("has_fossil", has(UP2ItemTags.FOSSILS)).save(consumer);
        ShapedRecipeBuilder.shaped(MISC, UP2Blocks.CULTIVATOR.get()).define('#', Tags.Items.INGOTS_IRON).define('X', UP2ItemTags.CULTIVATOR_FUEL).define('Y', Tags.Items.DUSTS_REDSTONE).define('Z', Tags.Items.GLASS).pattern("###").pattern("ZXZ").pattern("#Y#").unlockedBy("has_organic_ooze", has(UP2ItemTags.CULTIVATOR_FUEL)).save(consumer);

        ShapelessRecipeBuilder.shapeless(MISC, UP2Items.ORGANIC_OOZE.get(), 2).requires(Tags.Items.SLIMEBALLS).requires(Items.ROTTEN_FLESH).requires(Items.SUGAR).requires(Tags.Items.MUSHROOMS).unlockedBy("has_slime", has(Items.SLIME_BALL)).save(consumer);

        stairs(UP2Blocks.GINKGO_STAIRS, UP2Blocks.GINKGO_PLANKS).save(consumer);
        slab(UP2Blocks.GINKGO_SLAB, UP2Blocks.GINKGO_PLANKS).save(consumer);
        fence(UP2Blocks.GINKGO_FENCE, UP2Blocks.GINKGO_PLANKS).save(consumer);
        fenceGate(UP2Blocks.GINKGO_FENCE_GATE, UP2Blocks.GINKGO_PLANKS).save(consumer);
        door(UP2Blocks.GINKGO_DOOR, UP2Blocks.GINKGO_PLANKS).save(consumer);
        trapdoor(UP2Blocks.GINKGO_TRAPDOOR, UP2Blocks.GINKGO_PLANKS).save(consumer);
        button(UP2Blocks.GINKGO_BUTTON, UP2Blocks.GINKGO_PLANKS).save(consumer);
        pressurePlate(UP2Blocks.GINKGO_PRESSURE_PLATE, UP2Blocks.GINKGO_PLANKS).save(consumer);
        planks(UP2Blocks.GINKGO_PLANKS, UP2ItemTags.GINKGO_LOGS).save(consumer);
        wood(UP2Blocks.GINKGO_WOOD, UP2Blocks.GINKGO_LOG).save(consumer);
        wood(UP2Blocks.STRIPPED_GINKGO_WOOD, UP2Blocks.STRIPPED_GINKGO_LOG).save(consumer);

        stairs(UP2Blocks.LEPIDODENDRON_STAIRS, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        slab(UP2Blocks.LEPIDODENDRON_SLAB, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        fence(UP2Blocks.LEPIDODENDRON_FENCE, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        fenceGate(UP2Blocks.LEPIDODENDRON_FENCE_GATE, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        door(UP2Blocks.LEPIDODENDRON_DOOR, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        trapdoor(UP2Blocks.LEPIDODENDRON_TRAPDOOR, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        button(UP2Blocks.LEPIDODENDRON_BUTTON, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        pressurePlate(UP2Blocks.LEPIDODENDRON_PRESSURE_PLATE, UP2Blocks.LEPIDODENDRON_PLANKS).save(consumer);
        planks(UP2Blocks.LEPIDODENDRON_PLANKS, UP2ItemTags.LEPIDODENDRON_LOGS).save(consumer);
        wood(UP2Blocks.LEPIDODENDRON_WOOD, UP2Blocks.LEPIDODENDRON_LOG).save(consumer);
        wood(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD, UP2Blocks.MOSSY_LEPIDODENDRON_LOG).save(consumer);
        wood(UP2Blocks.STRIPPED_LEPIDODENDRON_WOOD, UP2Blocks.STRIPPED_LEPIDODENDRON_LOG).save(consumer);

        cultivating(consumer, UP2Items.DIPLOCAULUS_DNA.get(), UP2Blocks.DIPLOCAULUS_EGGS.get().asItem());
        cultivating(consumer, UP2Items.DUNKLEOSTEUS_DNA.get(), UP2Items.DUNKLEOSTEUS_SAC.get());
        cultivating(consumer, UP2Items.JAWLESS_FISH_DNA.get(), UP2Blocks.JAWLESS_FISH_ROE.get().asItem());
        cultivating(consumer, UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DNA.get(), UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get().asItem());
        cultivating(consumer, UP2Items.STETHACANTHUS_DNA.get(), UP2Items.STETHACANTHUS_SAC.get());

        cultivating(consumer, UP2Items.CARNOTAURUS_DNA.get(), UP2Blocks.CARNOTAURUS_EGG.get().asItem());
        cultivating(consumer, UP2Items.DROMAEOSAURUS_DNA.get(), UP2Items.DROMAEOSAURUS_EGG.get());
        cultivating(consumer, UP2Items.KENTROSAURUS_DNA.get(), UP2Blocks.KENTROSAURUS_EGG.get().asItem());
        cultivating(consumer, UP2Items.MAJUNGASAURUS_DNA.get(), UP2Blocks.MAJUNGASAURUS_EGG.get().asItem());
        cultivating(consumer, UP2Items.MEGALANIA_DNA.get(), UP2Blocks.MEGALANIA_EGG.get().asItem());
        cultivating(consumer, UP2Items.TALPANAS_DNA.get(), UP2Items.TALPANAS_EGG.get());
        cultivating(consumer, UP2Items.TELECREX_DNA.get(), UP2Items.TELECREX_EGG.get());

        cultivating(consumer, UP2Items.ARCHAEOSIGILLARIA_DNA.get(), UP2Blocks.ARCHAEOSIGILLARIA.get().asItem());
        cultivating(consumer, UP2Items.BENNETTITALES_DNA.get(), UP2Blocks.BENNETTITALES.get().asItem());
        cultivating(consumer, UP2Items.CALAMOPHYTON_DNA.get(), UP2Blocks.CALAMOPHYTON.get().asItem());
        cultivating(consumer, UP2Items.CLADOPHLEBIS_DNA.get(), UP2Blocks.CLADOPHLEBIS.get().asItem());
        cultivating(consumer, UP2Items.COOKSONIA_DNA.get(), UP2Blocks.COOKSONIA.get().asItem());
        cultivating(consumer, UP2Items.GINKGO_DNA.get(), UP2Blocks.GINKGO_SAPLING.get().asItem());
        cultivating(consumer, UP2Items.HORSETAIL_DNA.get(), UP2Blocks.HORSETAIL.get().asItem());
        cultivating(consumer, UP2Items.ISOETES_DNA.get(), UP2Blocks.ISOETES.get().asItem());
        cultivating(consumer, UP2Items.LEEFRUCTUS_DNA.get(), UP2Blocks.LEEFRUCTUS.get().asItem());
        cultivating(consumer, UP2Items.LEPIDODENDRON_DNA.get(), UP2Blocks.LEPIDODENDRON_CONE.get().asItem());
        cultivating(consumer, UP2Items.RAIGUENRAYUN_DNA.get(), UP2Blocks.RAIGUENRAYUN.get().asItem());
        cultivating(consumer, UP2Items.RHYNIA_DNA.get(), UP2Blocks.RHYNIA.get().asItem());
        cultivating(consumer, UP2Items.SARRACENIA_DNA.get(), UP2Blocks.SARRACENIA.get().asItem());
        cultivating(consumer, UP2Items.ARCHAEFRUCTUS_DNA.get(), UP2Blocks.ARCHAEFRUCTUS.get().asItem());
        cultivating(consumer, UP2Items.NELUMBITES_DNA.get(), UP2Blocks.NELUMBITES.get().asItem());
        cultivating(consumer, UP2Items.QUEREUXIA_DNA.get(), UP2Blocks.QUEREUXIA.get().asItem());
        cultivating(consumer, UP2Items.ANOSTYLOSTROMA_DNA.get(), UP2Blocks.PETRIFIED_ANOSTYLOSTROMA.get().asItem());
        cultivating(consumer, UP2Items.CLATHRODICTYON_CORAL_DNA.get(), UP2Blocks.CLATHRODICTYON_CORAL.get().asItem());
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

    protected static void cultivating(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredients, Item result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        UP2RecipeBuilder.cultivating(ingredients1, result).save(finishedRecipeConsumer, modPrefix("cultivating/" + getItemName(result)));
    }
}
