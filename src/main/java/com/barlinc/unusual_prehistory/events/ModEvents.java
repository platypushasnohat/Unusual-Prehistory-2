package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Tartuosteus;
import com.barlinc.unusual_prehistory.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.*;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.update_5.ambient.Zhangsolva;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        // Update 1
        event.register(UP2Entities.CARNOTAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Carnotaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.DIPLOCAULUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Diplocaulus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.DROMAEOSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dromaeosaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.DUNKLEOSTEUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dunkleosteus::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.JAWLESS_FISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, JawlessFish::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.KENTROSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kentrosaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kimmeridgebrachypteraeschnidium::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KimmeridgebrachypteraeschnidiumNymph::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.MAJUNGASAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Majungasaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.MEGALANIA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Megalania::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.STETHACANTHUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stethacanthus::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.TALPANAS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Talpanas::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.TELECREX.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Telecrex::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.UNICORN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Unicorn::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);

        // Update 2
        event.register(UP2Entities.ONCHOPRISTIS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Onchopristis::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        // Update 3
        event.register(UP2Entities.METRIORHYNCHUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Metriorhynchus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.TARTUOSTEUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tartuosteus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);

        // Update 4
        event.register(UP2Entities.BRACHIOSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Brachiosaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.COELACANTHUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanthus::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.HIBBERTOPTERUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hibbertopterus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.KAPROSUCHUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kaprosuchus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.LEPTICTIDIUM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Leptictidium::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.LOBE_FINNED_FISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LobeFinnedFish::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.LYSTROSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Lystrosaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.PACHYCEPHALOSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pachycephalosaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.PRAEPUSA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Praepusa::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.PTERODACTYLUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pterodactylus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.ULUGHBEGSAURUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ulughbegsaurus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);

        // Update 5
        event.register(UP2Entities.AEGIROCASSIS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Aegirocassis::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.DESMATOSUCHUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Desmatosuchus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.MOSASAURUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mosasaurus::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UP2Entities.PSILOPTERUS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Psilopterus::canSpawn, RegisterSpawnPlacementsEvent.Operation.OR);
    }

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
        event.put(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), KimmeridgebrachypteraeschnidiumNymph.createAttributes().build());
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
        event.put(UP2Entities.MOSASAURUS.get(), Mosasaurus.createAttributes().build());
        event.put(UP2Entities.ZHANGSOLVA.get(), Zhangsolva.createAttributes().build());

        event.put(UP2Entities.GRUG.get(), Grug.createAttributes().build());
    }
}