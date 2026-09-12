package com.barl_inc.unusual_prehistory.event;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.particle.OutOfWaterBubbleParticle;
import com.barl_inc.unusual_prehistory.client.particle.SnowflakeParticle;
import com.barl_inc.unusual_prehistory.client.screen.TransmogrifierScreen;
import com.barl_inc.unusual_prehistory.registry.UP2Menus;
import com.barl_inc.unusual_prehistory.registry.UP2ParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

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
