package com.unusualmodding.unusual_prehistory.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class UP2SoundTypes {
    public static final SoundType MOSSY_DIRT = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.GRAVEL_BREAK, () -> SoundEvents.MOSS_STEP, () -> SoundEvents.GRAVEL_PLACE, () -> SoundEvents.GRAVEL_HIT, () -> SoundEvents.MOSS_FALL);
}
