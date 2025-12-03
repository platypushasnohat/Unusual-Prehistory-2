package com.barlinc.unusual_prehistory.blocks;

import com.barlinc.unusual_prehistory.registry.UP2CauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class OozeCauldron extends LayeredCauldronBlock {

    public static final Predicate<Biome.Precipitation> NONE = (precipitation) -> false;

    public OozeCauldron(Properties properties) {
        super(properties, NONE, UP2CauldronInteractions.OOZE.map());
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(Items.CAULDRON);
    }
}