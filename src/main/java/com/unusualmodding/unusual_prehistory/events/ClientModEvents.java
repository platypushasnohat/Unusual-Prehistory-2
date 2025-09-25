package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.unicorn.*;
import com.unusualmodding.unusual_prehistory.client.particles.EepyParticle;
import com.unusualmodding.unusual_prehistory.client.renderer.blockentity.*;
import com.unusualmodding.unusual_prehistory.registry.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.*;
import com.unusualmodding.unusual_prehistory.client.renderer.*;
import com.unusualmodding.unusual_prehistory.client.particles.*;
import com.unusualmodding.unusual_prehistory.screens.TransmogrifierScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
        Sheets.addWoodType(UP2BlockProperties.GINKGO);
        MenuScreens.register(UP2MenuTypes.TRANSMOGRIFIER.get(), TransmogrifierScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.EEPY.get(), EepyParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
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

        event.registerBlockEntityRenderer(UP2BlockEntities.SUSPICIOUS_STONE.get(), SuspiciousStoneRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UP2EntityModelLayers.CARNOTAURUS, CarnotaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.CEPHALASPIS, CephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_BREVIROSTRIS, DiplocaulusBrevirostrisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_MAGNICORNIS, DiplocaulusMagnicornisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_RECURVATIS, DiplocaulusRecurvatisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_SALAMANDROIDES, DiplocaulusSalamandroidesModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DORYASPIS, DoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DROMAEOSAURUS, DromaeosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DUNKLEOSTEUS_LARGE, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DUNKLEOSTEUS_MEDIUM, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DUNKLEOSTEUS_SMALL, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.FURACACAUDA, FurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.KENTROSAURUS, KentrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM, KimmeridgebrachypteraeschnidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH, KimmeridgebrachypteraeschnidiumNymphModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.MAJUNGASAURUS, MajungasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.MEGALANIA, MegalaniaModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.SACABAMBASPIS, SacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.STETHACANTHUS, StethacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.TALPANAS, TalpanasModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.TELECREX, TelecrexModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.UNICORN, UnicornModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.UNICORN_SKELETON, UnicornSkeletonModel::createBodyLayer);
    }
}