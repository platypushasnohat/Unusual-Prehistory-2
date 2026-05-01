package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Tartuosteus;
import com.barlinc.unusual_prehistory.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Aegirocassis;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Desmatosuchus;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Grug;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Psilopterus;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Zhangsolva;
import com.barlinc.unusual_prehistory.entity.mob.update_6.*;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Ampyx;
import com.barlinc.unusual_prehistory.entity.mob.update_6.ambient.Setapedites;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // Update 1
        event.put(UP2Entities.CARNOTAURUS.get(), Carnotaurus.createAttributes().build());
        event.put(UP2Entities.DIPLOCAULUS.get(), Diplocaulus.createAttributes().build());
        event.put(UP2Entities.DROMAEOSAURUS.get(), Dromaeosaurus.createAttributes().build());
        event.put(UP2Entities.DUNKLEOSTEUS.get(), Dunkleosteus.createAttributes().build());
        event.put(UP2Entities.JAWLESS_FISH.get(), JawlessFish.createAttributes().build());
        event.put(UP2Entities.KENTROSAURUS.get(), Kentrosaurus.createAttributes().build());
        event.put(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), Kimmeridgebrachypteraeschnidium.createAttributes().build());
        event.put(UP2Entities.MAJUNGASAURUS.get(), Majungasaurus.createAttributes().build());
        event.put(UP2Entities.MEGALANIA.get(), Megalania.createAttributes().build());
        event.put(UP2Entities.STETHACANTHUS.get(), Stethacanthus.createAttributes().build());
        event.put(UP2Entities.TALPANAS.get(), Talpanas.createAttributes().build());
        event.put(UP2Entities.TELECREX.get(), Telecrex.createAttributes().build());
        event.put(UP2Entities.UNICORN.get(), Unicorn.createAttributes().build());

        // Update 2
        event.put(UP2Entities.ONCHOPRISTIS.get(), Onchopristis.createAttributes().build());

        // Update 3
        event.put(UP2Entities.LIVING_OOZE.get(), LivingOoze.createAttributes().build());
        event.put(UP2Entities.METRIORHYNCHUS.get(), Metriorhynchus.createAttributes().build());
        event.put(UP2Entities.TARTUOSTEUS.get(), Tartuosteus.createAttributes().build());

        // Update 4
        event.put(UP2Entities.BRACHIOSAURUS.get(), Brachiosaurus.createAttributes().build());
        event.put(UP2Entities.COELACANTHUS.get(), Coelacanthus.createAttributes().build());
        event.put(UP2Entities.HIBBERTOPTERUS.get(), Hibbertopterus.createAttributes().build());
        event.put(UP2Entities.KAPROSUCHUS.get(), Kaprosuchus.createAttributes().build());
        event.put(UP2Entities.LEPTICTIDIUM.get(), Leptictidium.createAttributes().build());
        event.put(UP2Entities.LOBE_FINNED_FISH.get(), LobeFinnedFish.createAttributes().build());
        event.put(UP2Entities.LYSTROSAURUS.get(), Lystrosaurus.createAttributes().build());
        event.put(UP2Entities.PACHYCEPHALOSAURUS.get(), Pachycephalosaurus.createAttributes().build());
        event.put(UP2Entities.PRAEPUSA.get(), Praepusa.createAttributes().build());
        event.put(UP2Entities.PSILOPTERUS.get(), Psilopterus.createAttributes().build());
        event.put(UP2Entities.PTERODACTYLUS.get(), Pterodactylus.createAttributes().build());
        event.put(UP2Entities.ULUGHBEGSAURUS.get(), Ulughbegsaurus.createAttributes().build());

        // Update 5
        event.put(UP2Entities.AEGIROCASSIS.get(), Aegirocassis.createAttributes().build());
        event.put(UP2Entities.DELITZSCHALA.get(), Delitzschala.createAttributes().build());
        event.put(UP2Entities.DESMATOSUCHUS.get(), Desmatosuchus.createAttributes().build());
        event.put(UP2Entities.ZHANGSOLVA.get(), Zhangsolva.createAttributes().build());

        event.put(UP2Entities.GRUG.get(), Grug.createAttributes().build());

        // Update 6
        event.put(UP2Entities.AMPYX.get(), Ampyx.createAttributes().build());
        event.put(UP2Entities.ANTARCTOPELTA.get(), Antarctopelta.createAttributes().build());
        event.put(UP2Entities.COTYLORHYNCHUS.get(), Cotylorhynchus.createAttributes().build());
        event.put(UP2Entities.CRYPTOCLIDUS.get(), Cryptoclidus.createAttributes().build());
        event.put(UP2Entities.HYNERPETON.get(), Hynerpeton.createAttributes().build());
        event.put(UP2Entities.ICHTHYOSAURUS.get(), Ichthyosaurus.createAttributes().build());
        event.put(UP2Entities.LORRAINOSAURUS.get(), Lorrainosaurus.createAttributes().build());
        event.put(UP2Entities.MAMMOTH.get(), Mammoth.createAttributes().build());
        event.put(UP2Entities.PROGNATHODON.get(), Prognathodon.createAttributes().build());
        event.put(UP2Entities.SETAPEDITES.get(), Setapedites.createAttributes().build());
        event.put(UP2Entities.SPIKE_TOOTHED_SALMON.get(), SpikeToothedSalmon.createAttributes().build());
    }
}