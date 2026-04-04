package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.advancements.CriteriaTriggers;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class UP2Criterion {

    public static final UP2CriteriaTriggers OPEN_BOOK_CREATIVE_MODE = CriteriaTriggers.register(new UP2CriteriaTriggers("open_book_creative_mode"));

}
