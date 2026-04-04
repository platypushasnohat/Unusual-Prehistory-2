package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusDwarfModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusMuddyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusSwampyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusTigerModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.dunkelosteus.DunkleosteusLargeModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.dunkelosteus.DunkleosteusMediumModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.dunkelosteus.DunkleosteusSmallModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.jawless_fish.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_2.OnchopristisModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.MetriorhynchusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.TartuosteusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.lobe_finned_fish.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.ambient.DelitzschalaModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.ambient.ZhangsolvaModel;
import com.barlinc.unusual_prehistory.client.particles.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_2.OnchopristisRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.MetriorhynchusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.TartuosteusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.ambient.DelitzschalaRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.ambient.ZhangsolvaRenderer;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.screens.TransmogrifierScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
        MenuScreens.register(UP2MenuTypes.TRANSMOGRIFIER.get(), TransmogrifierScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.EEPY.get(), EepyParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.OOZE_BUBBLE.get(), OozeBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.TAR_BUBBLE.get(), TarBubbleParticle.TarBubbleFactory::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_HEART.get(), GrowingHeartParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.IMPACT_STUN.get(), ImpactStunParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.POPPING_BUBBLE.get(), PoppingBubbleParticle.BubbleFactory::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Update 1
        event.registerEntityRenderer(UP2Entities.CARNOTAURUS.get(), CarnotaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DIPLOCAULUS.get(), DiplocaulusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DROMAEOSAURUS.get(), DromaeosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DUNKLEOSTEUS.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(UP2Entities.JAWLESS_FISH.get(), JawlessFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.KENTROSAURUS.get(), KentrosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), KimmeridgebrachypteraeschnidiumNymphRenderer::new);
        event.registerEntityRenderer(UP2Entities.MAJUNGASAURUS.get(), MajungasaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.MEGALANIA.get(), MegalaniaRenderer::new);
        event.registerEntityRenderer(UP2Entities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS.get(), TalpanasRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX.get(), TelecrexRenderer::new);
        event.registerEntityRenderer(UP2Entities.UNICORN.get(), UnicornRenderer::new);

        event.registerEntityRenderer(UP2Entities.DROMAEOSAURUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX_EGG.get(), ThrownItemRenderer::new);

        // Update 2
        event.registerEntityRenderer(UP2Entities.ONCHOPRISTIS.get(), OnchopristisRenderer::new);

        // Update 3
        event.registerEntityRenderer(UP2Entities.LIVING_OOZE.get(), LivingOozeRenderer::new);
        event.registerEntityRenderer(UP2Entities.METRIORHYNCHUS.get(), MetriorhynchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TARTUOSTEUS.get(), TartuosteusRenderer::new);

        // Update 4
        event.registerEntityRenderer(UP2Entities.BRACHIOSAURUS.get(), BrachiosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.COELACANTHUS.get(), CoelacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.HIBBERTOPTERUS.get(), HibbertopterusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KAPROSUCHUS.get(), KaprosuchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.LEPTICTIDIUM.get(), LeptictidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.LOBE_FINNED_FISH.get(), LobeFinnedFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.LYSTROSAURUS.get(), LystrosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PACHYCEPHALOSAURUS.get(), PachycephalosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PRAEPUSA.get(), PraepusaRenderer::new);
        event.registerEntityRenderer(UP2Entities.PSILOPTERUS.get(), PsilopterusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PTERODACTYLUS.get(), PterodactylusRenderer::new);
        event.registerEntityRenderer(UP2Entities.ULUGHBEGSAURUS.get(), UlughbegsaurusRenderer::new);

        event.registerEntityRenderer(UP2Entities.PSILOPTERUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.PTERODACTYLUS_EGG.get(), ThrownItemRenderer::new);

        // Update 5
        event.registerEntityRenderer(UP2Entities.AEGIROCASSIS.get(), AegirocassisRenderer::new);
        event.registerEntityRenderer(UP2Entities.DELITZSCHALA.get(), DelitzschalaRenderer::new);
        event.registerEntityRenderer(UP2Entities.DESMATOSUCHUS.get(), DesmatosuchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.MOSASAURUS.get(), MosasaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.ZHANGSOLVA.get(), ZhangsolvaRenderer::new);

        event.registerEntityRenderer(UP2Entities.GRUG.get(), GrugRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Update 1
        event.registerLayerDefinition(UP2ModelLayers.CARNOTAURUS, CarnotaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_TIGER, DiplocaulusTigerModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_SWAMPY, DiplocaulusSwampyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_MUDDY, DiplocaulusMuddyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_DWARF, DiplocaulusDwarfModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DROMAEOSAURUS, DromaeosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_LARGE, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_SMALL, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_ARANDASPIS, ArandaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS, CephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_DORYASPIS, DoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_FURACACAUDA, FurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_SACABAMBASPIS, SacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KENTROSAURUS, KentrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM, KimmeridgebrachypteraeschnidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, KimmeridgebrachypteraeschnidiumNymphModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MAJUNGASAURUS, MajungasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MEGALANIA, MegalaniaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.STETHACANTHUS, StethacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TALPANAS, TalpanasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TELECREX, TelecrexModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN, UnicornModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN_SKELETON, UnicornSkeletonModel::createBodyLayer);

        // Update 2
        event.registerLayerDefinition(UP2ModelLayers.ONCHOPRISTIS, OnchopristisModel::createBodyLayer);

        // Update 3
        event.registerLayerDefinition(UP2ModelLayers.LIVING_OOZE, LivingOozeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.METRIORHYNCHUS, MetriorhynchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TARTUOSTEUS, TartuosteusModel::createBodyLayer);

        // Update 4
        event.registerLayerDefinition(UP2ModelLayers.BRACHIOSAURUS, BrachiosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BRACHIOSAURUS_BABY, BrachiosaurusBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.COELACANTHUS, CoelacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HIBBERTOPTERUS, HibbertopterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KAPROSUCHUS, KaprosuchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEPTICTIDIUM, LeptictidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_ALLENYPTERUS, AllenypterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_EUSTHENOPTERON, EusthenopteronModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_GOOLOOGONGIA, GooloogongiaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_LACCOGNATHUS, LaccognathusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_SCAUMENACIA, ScaumenaciaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LYSTROSAURUS, LystrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PACHYCEPHALOSAURUS, PachycephalosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PRAEPUSA, PraepusaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PTERODACTYLUS, PterodactylusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ULUGHBEGSAURUS, UlughbegsaurusModel::createBodyLayer);

        // Update 5
        event.registerLayerDefinition(UP2ModelLayers.AEGIROCASSIS, AegirocassisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AEGIROCASSIS_BABY, AegirocassisBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DESMATOSUCHUS, DesmatosuchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DELITZSCHALA, DelitzschalaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MOSASAURUS, MosasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PSILOPTERUS, PsilopterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ZHANGSOLVA, ZhangsolvaModel::createBodyLayer);

        event.registerLayerDefinition(UP2ModelLayers.GRUG, GrugModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null) {
                        return FoliageColor.getDefaultColor();
                    }
                    return BiomeColors.getAverageFoliageColor(world, pos);
                },
                UP2Blocks.CLADOPHLEBIS.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                    BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return event.getBlockColors().getColor(blockstate, null, null, tintIndex);
                },
                UP2Blocks.CLADOPHLEBIS.get()
        );
    }
}