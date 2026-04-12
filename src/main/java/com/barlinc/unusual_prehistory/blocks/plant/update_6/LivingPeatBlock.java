package com.barlinc.unusual_prehistory.blocks.plant.update_6;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class LivingPeatBlock extends Block {

    public static final MapCodec<LivingPeatBlock> CODEC = simpleCodec(LivingPeatBlock::new);

    public LivingPeatBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull MapCodec<LivingPeatBlock> codec() {
        return CODEC;
    }
}
