package com.unusualmodding.unusual_prehistory.utils;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.book.PaleopediaGui;
import com.unusualmodding.unusual_prehistory.client.renderer.UP2InternalShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public void init() {
    }

    public void clientInit() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerShaders);
    }

    @Override
    public void openPaleopediaGUI(ItemStack book) {
        Minecraft.getInstance().setScreen(new PaleopediaGui(book));
    }

    @Override
    public void openPaleopediaGUI(ItemStack book, String page) {
        Minecraft.getInstance().setScreen(new PaleopediaGui(book, page));
    }

    private void registerShaders(final RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(UnusualPrehistory2.MOD_ID, "rendertype_sepia"), DefaultVertexFormat.NEW_ENTITY), UP2InternalShaders::setRenderTypeSepiaShader);
            UnusualPrehistory2.LOGGER.info("registered internal shaders");
        } catch (IOException exception) {
            UnusualPrehistory2.LOGGER.error("could not register internal shaders");
            exception.printStackTrace();
        }
    }
}
