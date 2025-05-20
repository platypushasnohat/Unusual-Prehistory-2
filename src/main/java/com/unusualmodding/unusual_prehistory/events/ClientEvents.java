package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.ScaumenaciaModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.StethacanthusModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.UnicornModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.unusualmodding.unusual_prehistory.registry.UP2BlockProperties;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.*;
import com.unusualmodding.unusual_prehistory.registry.UP2Entities;
import com.unusualmodding.unusual_prehistory.client.renderer.*;
import com.unusualmodding.unusual_prehistory.registry.UP2ItemProperties;
import com.unusualmodding.unusual_prehistory.registry.UP2Particles;
import com.unusualmodding.unusual_prehistory.client.particles.FallingLeafParticle;
import com.unusualmodding.unusual_prehistory.client.screens.ExtractorScreen;
import com.unusualmodding.unusual_prehistory.registry.UP2MenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
        Sheets.addWoodType(UP2BlockProperties.GINKGO);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(UP2MenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UP2Entities.DUNKLEOSTEUS.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(UP2Entities.JAWLESS_FISH.get(), JawlessFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.SCAUMENACIA.get(), ScaumenaciaRenderer::new);
        event.registerEntityRenderer(UP2Entities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.UNICORN.get(), e -> new AncientRenderer<>(e, new UnicornModel()));
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UP2ModelLayers.CEPHALASPIS_LAYER, CephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DORYASPIS_LAYER, DoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_LARGE_LAYER, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM_LAYER, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_SMALL_LAYER, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.FURACACAUDA_LAYER, FurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SACABAMBASPIS_LAYER, SacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SCAMENACIA_LAYER, ScaumenaciaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.STETHACANTHUS_LAYER, StethacanthusModel::createBodyLayer);
    }
}