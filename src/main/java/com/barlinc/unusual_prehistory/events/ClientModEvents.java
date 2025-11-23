package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.*;
import com.barlinc.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusBrevirostrisModel;
import com.barlinc.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusMagnicornisModel;
import com.barlinc.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusRecurvatisModel;
import com.barlinc.unusual_prehistory.client.models.entity.diplocaulus.DiplocaulusSalamandroidesModel;
import com.barlinc.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusLargeModel;
import com.barlinc.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusMediumModel;
import com.barlinc.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusSmallModel;
import com.barlinc.unusual_prehistory.client.models.entity.jawless_fish.*;
import com.barlinc.unusual_prehistory.client.models.entity.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumModel;
import com.barlinc.unusual_prehistory.client.models.entity.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumNymphModel;
import com.barlinc.unusual_prehistory.client.models.entity.unicorn.UnicornModel;
import com.barlinc.unusual_prehistory.client.models.entity.unicorn.UnicornSkeletonModel;
import com.barlinc.unusual_prehistory.client.particles.*;
import com.barlinc.unusual_prehistory.client.renderer.*;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.screens.TransmogrifierScreen;
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
        Sheets.addWoodType(UP2BlockProperties.GINKGO_WOOD_TYPE);
        Sheets.addWoodType(UP2BlockProperties.LEPIDODENDRON_WOOD_TYPE);
        MenuScreens.register(UP2MenuTypes.TRANSMOGRIFIER.get(), TransmogrifierScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.EEPY.get(), EepyParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.OOZE_BUBBLE.get(), OozeBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.TAR_BUBBLE.get(), TarBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_HEART.get(), GrowingHeartParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
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

        event.registerEntityRenderer(UP2Entities.BOAT.get(), UP2BoatRenderer::new);
        event.registerEntityRenderer(UP2Entities.CHEST_BOAT.get(), UP2ChestBoatRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UP2ModelLayers.ARANDASPIS, ArandaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CARNOTAURUS, CarnotaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CEPHALASPIS, CephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_BREVIROSTRIS, DiplocaulusBrevirostrisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_MAGNICORNIS, DiplocaulusMagnicornisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_RECURVATIS, DiplocaulusRecurvatisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_SALAMANDROIDES, DiplocaulusSalamandroidesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DORYASPIS, DoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DROMAEOSAURUS, DromaeosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_LARGE, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_SMALL, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.FURACACAUDA, FurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KENTROSAURUS, KentrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM, KimmeridgebrachypteraeschnidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM_NYMPH, KimmeridgebrachypteraeschnidiumNymphModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MAJUNGASAURUS, MajungasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MEGALANIA, MegalaniaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SACABAMBASPIS, SacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.STETHACANTHUS, StethacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TALPANAS, TalpanasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TELECREX, TelecrexModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN, UnicornModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN_SKELETON, UnicornSkeletonModel::createBodyLayer);
    }
}