package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.UP2Entities;
import com.unusualmodding.unusual_prehistory.entity.custom.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(UP2Entities.DUNKLEOSTEUS.get(), DunkleosteusEntity.createAttributes().build());
        event.put(UP2Entities.JAWLESS_FISH.get(), JawlessFishEntity.createAttributes().build());
        event.put(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumEntity.createAttributes().build());
        event.put(UP2Entities.SCAUMENACIA.get(), ScaumenaciaEntity.createAttributes().build());
        event.put(UP2Entities.STETHACANTHUS.get(), StethacanthusEntity.createAttributes().build());
        event.put(UP2Entities.UNICORN.get(), UnicornEntity.createAttributes().build());
    }
}