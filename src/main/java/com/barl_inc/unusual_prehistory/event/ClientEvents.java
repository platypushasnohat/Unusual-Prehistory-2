package com.barl_inc.unusual_prehistory.event;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.model.AmmoniteModel;
import com.barl_inc.unusual_prehistory.client.model.LeedsichthysModel;
import com.barl_inc.unusual_prehistory.client.particle.OutOfWaterBubbleParticle;
import com.barl_inc.unusual_prehistory.client.particle.SnowflakeParticle;
import com.barl_inc.unusual_prehistory.client.render.entity.AmmoniteRenderer;
import com.barl_inc.unusual_prehistory.client.render.entity.LeedsichthysRenderer;
import com.barl_inc.unusual_prehistory.client.screen.TransmogrifierScreen;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Menus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.barl_inc.unusual_prehistory.registry.UP2ParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UP2Entities.AMMONITE.get(), AmmoniteRenderer::new);
        event.registerEntityRenderer(UP2Entities.LEEDSICHTHYS.get(), LeedsichthysRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_CRIOCERATITES, AmmoniteModel::createCrioceratitesBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_HOPLITES, AmmoniteModel::createHoplitesBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_NOSTOCERAS, AmmoniteModel::createNostocerasBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_PINACOCERAS, AmmoniteModel::createPinacocerasBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_TROPITES, AmmoniteModel::createTropitesBodyLayer);

        event.registerLayerDefinition(UP2ModelLayers.LEEDSICHTHYS, LeedsichthysModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEEDSICHTHYS_BABY, LeedsichthysModel::createBabyBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(UP2Menus.TRANSMOGRIFIER.get(), TransmogrifierScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2ParticleTypes.OOZE_BUBBLE.get(), OutOfWaterBubbleParticle.Factory::new);
        event.registerSpriteSet(UP2ParticleTypes.SNOWFLAKE.get(), SnowflakeParticle.Factory::new);
    }
}
