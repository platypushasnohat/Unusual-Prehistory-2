package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.future.*;
import com.barlinc.unusual_prehistory.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.barlinc.unusual_prehistory.entity.mob.update_3.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Metriorhynchus;
import com.barlinc.unusual_prehistory.entity.mob.update_3.Tartuosteus;
import com.barlinc.unusual_prehistory.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.entity.mob.update_4.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.update_4.ambient.Zhangsolva;
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
        // Update 1
        event.register(UP2Entities.CARNOTAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Carnotaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DIPLOCAULUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Diplocaulus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DROMAEOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dromaeosaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DUNKLEOSTEUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dunkleosteus::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.JAWLESS_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, JawlessFish::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KENTROSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kentrosaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kimmeridgebrachypteraeschnidium::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KimmeridgebrachypteraeschnidiumNymph::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.MAJUNGASAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Majungasaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.MEGALANIA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Megalania::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.STETHACANTHUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stethacanthus::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.TALPANAS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Talpanas::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.TELECREX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Telecrex::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.UNICORN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Unicorn::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

        // Update 2
        event.register(UP2Entities.ONCHOPRISTIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Onchopristis::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        // Update 3
        event.register(UP2Entities.METRIORHYNCHUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Metriorhynchus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.TARTUOSTEUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tartuosteus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

        // Update 4
        event.register(UP2Entities.AEGIROCASSIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Aegirocassis::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.BARINASUCHUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Barinasuchus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.BRACHIOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Brachiosaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.COELACANTHUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanthus::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DESMATOSUCHUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Desmatosuchus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.DIMORPHODON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dimorphodon::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.HIBBERTOPTERUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hibbertopterus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.KAPROSUCHUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Kaprosuchus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.LEPTICTIDIUM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Leptictidium::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.LOBE_FINNED_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LobeFinnedFish::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.LYSTROSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Lystrosaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.MANIPULATOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Manipulator::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.PACHYCEPHALOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pachycephalosaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.PRAEPUSA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Praepusa::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.PSILOPTERUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Psilopterus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.PTERODACTYLUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Pterodactylus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.THERIZINOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Therizinosaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.ULUGHBEGSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ulughbegsaurus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

        // Update 5
        event.register(UP2Entities.COTYLORHYNCHUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cotylorhynchus::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.ERYON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Eryon::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.MAMMOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mammoth::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UP2Entities.PALAEOPHIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Palaeophis::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

        // Update 6
        event.register(UP2Entities.WONAMBI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wonambi::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
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

        // Update 2
        event.put(UP2Entities.ONCHOPRISTIS.get(), Onchopristis.createAttributes().build());

        // Update 3
        event.put(UP2Entities.LIVING_OOZE.get(), LivingOoze.createAttributes().build());
        event.put(UP2Entities.METRIORHYNCHUS.get(), Metriorhynchus.createAttributes().build());
        event.put(UP2Entities.TARTUOSTEUS.get(), Tartuosteus.createAttributes().build());

        // Update 4
        event.put(UP2Entities.AEGIROCASSIS.get(), Aegirocassis.createAttributes().build());
        event.put(UP2Entities.BARINASUCHUS.get(), Barinasuchus.createAttributes().build());
        event.put(UP2Entities.BRACHIOSAURUS.get(), Brachiosaurus.createAttributes().build());
        event.put(UP2Entities.COELACANTHUS.get(), Coelacanthus.createAttributes().build());
        event.put(UP2Entities.DELITZSCHALA.get(), Delitzschala.createAttributes().build());
        event.put(UP2Entities.DESMATOSUCHUS.get(), Desmatosuchus.createAttributes().build());
        event.put(UP2Entities.DIMORPHODON.get(), Dimorphodon.createAttributes().build());
        event.put(UP2Entities.HIBBERTOPTERUS.get(), Hibbertopterus.createAttributes().build());
        event.put(UP2Entities.KAPROSUCHUS.get(), Kaprosuchus.createAttributes().build());
        event.put(UP2Entities.LEPTICTIDIUM.get(), Leptictidium.createAttributes().build());
        event.put(UP2Entities.LOBE_FINNED_FISH.get(), LobeFinnedFish.createAttributes().build());
        event.put(UP2Entities.LYSTROSAURUS.get(), Lystrosaurus.createAttributes().build());
        event.put(UP2Entities.MANIPULATOR.get(), Manipulator.createAttributes().build());
        event.put(UP2Entities.PACHYCEPHALOSAURUS.get(), Pachycephalosaurus.createAttributes().build());
        event.put(UP2Entities.PRAEPUSA.get(), Praepusa.createAttributes().build());
        event.put(UP2Entities.PSILOPTERUS.get(), Psilopterus.createAttributes().build());
        event.put(UP2Entities.PTERODACTYLUS.get(), Pterodactylus.createAttributes().build());
        event.put(UP2Entities.THERIZINOSAURUS.get(), Therizinosaurus.createAttributes().build());
        event.put(UP2Entities.ULUGHBEGSAURUS.get(), Ulughbegsaurus.createAttributes().build());
        event.put(UP2Entities.UNICORN.get(), Unicorn.createAttributes().build());
        event.put(UP2Entities.ZHANGSOLVA.get(), Zhangsolva.createAttributes().build());

        // Update 5
        event.put(UP2Entities.COTYLORHYNCHUS.get(), Cotylorhynchus.createAttributes().build());
        event.put(UP2Entities.ERYON.get(), Eryon.createAttributes().build());
        event.put(UP2Entities.MAMMOTH.get(), Mammoth.createAttributes().build());
        event.put(UP2Entities.PALAEOPHIS.get(), Palaeophis.createAttributes().build());

        // Update 6
        event.put(UP2Entities.WONAMBI.get(), Wonambi.createAttributes().build());
    }
}