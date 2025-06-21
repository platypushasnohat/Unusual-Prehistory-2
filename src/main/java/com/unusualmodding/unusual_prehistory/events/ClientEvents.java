package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusBrevirostrisModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusMagnicornisModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusRecurvatisModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusSalamandroidesModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.unicorn.*;
import com.unusualmodding.unusual_prehistory.client.renderer.blockentity.*;
import com.unusualmodding.unusual_prehistory.client.screens.*;
import com.unusualmodding.unusual_prehistory.registry.*;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.*;
import com.unusualmodding.unusual_prehistory.client.renderer.*;
import com.unusualmodding.unusual_prehistory.client.particles.FallingLeafParticle;
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
public class ClientEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
        Sheets.addWoodType(UP2BlockProperties.GINKGO);

        MenuScreens.register(UP2MenuTypes.CULTIVATOR_MENU.get(), CultivatorScreen::new);
        MenuScreens.register(UP2MenuTypes.ANALYZER_MENU.get(), ExtractorScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UP2Entities.DIPLOCAULUS.get(), DiplocaulusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DUNKLEOSTEUS.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(UP2Entities.JAWLESS_FISH.get(), JawlessFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.KENTROSAURUS.get(), KentrosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH.get(), KimmeridgebrachypteraeschnidiumNymphRenderer::new);
        event.registerEntityRenderer(UP2Entities.MEGALANIA.get(), MegalaniaRenderer::new);
        event.registerEntityRenderer(UP2Entities.SCAUMENACIA.get(), ScaumenaciaRenderer::new);
        event.registerEntityRenderer(UP2Entities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS.get(), TalpanasRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX.get(), TelecrexRenderer::new);
        event.registerEntityRenderer(UP2Entities.UNICORN.get(), UnicornRenderer::new);

        event.registerEntityRenderer(UP2Entities.TELECREX_EGG.get(), ThrownItemRenderer::new);

        event.registerBlockEntityRenderer(UP2BlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), CultivatorBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UP2EntityModelLayers.CEPHALASPIS_LAYER, CephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_BREVIROSTRIS_LAYER, DiplocaulusBrevirostrisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_MAGNICORNIS_LAYER, DiplocaulusMagnicornisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_RECURVATIS_LAYER, DiplocaulusRecurvatisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DIPLOCAULUS_SALAMANDROIDES_LAYER, DiplocaulusSalamandroidesModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DORYASPIS_LAYER, DoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DUNKLEOSTEUS_LARGE_LAYER, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DUNKLEOSTEUS_MEDIUM_LAYER, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.DUNKLEOSTEUS_SMALL_LAYER, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.FURACACAUDA_LAYER, FurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.KENTROSAURUS_LAYER, KentrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_LAYER, KimmeridgebrachypteraeschnidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH_LAYER, KimmeridgebrachypteraeschnidiumNymphModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.MEGALANIA_LAYER, MegalaniaModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.SACABAMBASPIS_LAYER, SacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.SCAMENACIA_LAYER, ScaumenaciaModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.STETHACANTHUS_LAYER, StethacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.TALPANAS_LAYER, TalpanasModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.TELECREX_LAYER, TelecrexModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.UNICORN_LAYER, UnicornModel::createBodyLayer);
        event.registerLayerDefinition(UP2EntityModelLayers.UNICORN_SKELETON_LAYER, UnicornSkeletonModel::createBodyLayer);
    }
}