package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class UP2Criterion {

    public static final UP2CriteriaTriggers HATCH_EGG = CriteriaTriggers.register(new UP2CriteriaTriggers("hatch_egg"));

}
