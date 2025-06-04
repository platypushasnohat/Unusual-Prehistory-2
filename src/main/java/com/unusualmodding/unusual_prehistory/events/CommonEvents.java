package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.*;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(UP2Entities.DIPLOCAULUS.get(), Diplocaulus.createAttributes().build());
        event.put(UP2Entities.DUNKLEOSTEUS.get(), Dunkleosteus.createAttributes().build());
        event.put(UP2Entities.JAWLESS_FISH.get(), JawlessFish.createAttributes().build());
        event.put(UP2Entities.KENTROSAURUS.get(), Kentrosaurus.createAttributes().build());
        event.put(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), Kimmeridgebrachypteraeschnidium.createAttributes().build());
        event.put(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), KimmeridgebrachypteraeschnidiumNymph.createAttributes().build());
        event.put(UP2Entities.MEGALANIA.get(), Megalania.createAttributes().build());
        event.put(UP2Entities.SCAUMENACIA.get(), Scaumenacia.createAttributes().build());
        event.put(UP2Entities.STETHACANTHUS.get(), Stethacanthus.createAttributes().build());
        event.put(UP2Entities.UNICORN.get(), Unicorn.createAttributes().build());
    }
}