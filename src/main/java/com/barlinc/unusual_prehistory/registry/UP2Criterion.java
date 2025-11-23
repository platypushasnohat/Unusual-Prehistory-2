package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class UP2Criterion {

    public static final UP2CriteriaTriggers PACIFY_MOB = CriteriaTriggers.register(new UP2CriteriaTriggers("pacify_mob"));
    public static final UP2CriteriaTriggers BREED_HOLOCENE_MOBS = CriteriaTriggers.register(new UP2CriteriaTriggers("breed_holocene_mobs"));

}
