package com.barl_inc.unusual_prehistory.event;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Ammonite;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Leedsichthys;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(UP2Entities.AMMONITE.get(), Ammonite.registerAttributes().build());
        event.put(UP2Entities.LEEDSICHTHYS.get(), Leedsichthys.registerAttributes().build());
    }
}
