package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.blocks.UP2Properties;
import com.unusualmodding.unusual_prehistory.entity.UP2Entities;
import com.unusualmodding.unusual_prehistory.entity.models.*;
import com.unusualmodding.unusual_prehistory.entity.renders.*;
import com.unusualmodding.unusual_prehistory.items.UP2ItemProperties;
import com.unusualmodding.unusual_prehistory.particles.UP2Particles;
import com.unusualmodding.unusual_prehistory.particles.custom.FallingLeafParticle;
import com.unusualmodding.unusual_prehistory.screens.ExtractorScreen;
import com.unusualmodding.unusual_prehistory.screens.UP2MenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
        Sheets.addWoodType(UP2Properties.GINKGO);
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
        event.registerEntityRenderer(UP2Entities.DUNKLEOSTEUS.get(), e -> new AncientAquaticRenderer<>(e, new DunkleosteusModel()));
        event.registerEntityRenderer(UP2Entities.JAWLESS_FISH.get(), e -> new AncientAquaticRenderer<>(e, new JawlessFishModel()));
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.SCAUMENACIA.get(), e -> new AncientAquaticRenderer<>(e, new ScaumenaciaModel()));
        event.registerEntityRenderer(UP2Entities.STETHACANTHUS.get(), e -> new AncientAquaticRenderer<>(e, new StethacanthusModel()));
        event.registerEntityRenderer(UP2Entities.UNICORN.get(), e -> new AncientRenderer<>(e, new UnicornModel()));
    }
}