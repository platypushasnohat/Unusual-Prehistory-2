package com.unusualmodding.unusual_prehistory.integration.jei;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.recipes.ChiselingRecipe;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChiselingCategory implements IRecipeCategory<ChiselingRecipe> {

    public final static ResourceLocation BACKGROUND = new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/gui/chiseling_jei.png");

    public static final int WIDTH = 116;
    public static final int HEIGHT = 54;
    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public ChiselingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BACKGROUND, 0, 0, WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, UP2Items.CHISEL.get().getDefaultInstance());
        this.localizedName = Component.translatable(UnusualPrehistory2.MOD_ID + "." + "jei.chiseling");
    }

    @Override
    public RecipeType<ChiselingRecipe> getRecipeType() {
        return JEIPlugin.CHISELING;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChiselingRecipe recipe, IFocusGroup foci) {
        builder.addSlot(RecipeIngredientRole.INPUT, 19, 19).addItemStack(new ItemStack(recipe.getInput().getBlock().asItem()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 19).addItemStack(new ItemStack(recipe.getOutput().getBlock().asItem()));
    }
}