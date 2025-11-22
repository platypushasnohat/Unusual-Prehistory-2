package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.*;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(UP2Entities.CARNOTAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Carnotaurus::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DIPLOCAULUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Diplocaulus::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DROMAEOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dromaeosaurus::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DUNKLEOSTEUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dunkleosteus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.JAWLESS_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, JawlessFish::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KENTROSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kentrosaurus::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kimmeridgebrachypteraeschnidium::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KimmeridgebrachypteraeschnidiumNymph::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.MAJUNGASAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Majungasaurus::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.MEGALANIA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Megalania::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.STETHACANTHUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stethacanthus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.TALPANAS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Talpanas::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.TELECREX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Telecrex::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.UNICORN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Unicorn::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
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
    }
}