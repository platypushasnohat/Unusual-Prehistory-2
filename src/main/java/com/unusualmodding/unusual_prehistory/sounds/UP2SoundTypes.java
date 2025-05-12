package com.unusualmodding.unusual_prehistory.sounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class UP2SoundTypes {
    public static final SoundType FROZEN_MEAT = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.GLASS_BREAK, () -> SoundEvents.CANDLE_STEP, () -> SoundEvents.CANDLE_PLACE, () -> SoundEvents.GLASS_BREAK, () -> SoundEvents.CANDLE_HIT);
}
