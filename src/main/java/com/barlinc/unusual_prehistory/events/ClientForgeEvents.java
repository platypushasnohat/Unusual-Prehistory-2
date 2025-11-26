package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2MapIcons;
import com.barlinc.unusual_prehistory.utils.ClientProxy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static float shakeAmount;
    private static float prevShakeAmount;

    public static final List<ScreenShakeEvent> SCREEN_SHAKE_EVENTS = new ArrayList<>();

    public static PoseStack lastMapPoseStack;
    public static MultiBufferSource lastMapRenderBuffer;
    public static int lastMapRenderPackedLight;
    private static final RenderType PALEOZOIC_SITE_ICON = RenderType.text(UnusualPrehistory2.modPrefix("textures/map/paleozoic_fossil_site_icon.png"));
    private static final RenderType MESOZOIC_SITE_ICON = RenderType.text(UnusualPrehistory2.modPrefix("textures/map/mesozoic_fossil_site_icon.png"));
    private static final RenderType PETRIFIED_TREE_ICON = RenderType.text(UnusualPrehistory2.modPrefix("textures/map/petrified_tree_icon.png"));

    @SubscribeEvent
    public void preRenderLiving(RenderLivingEvent.Pre event) {
        if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
            if (!UnusualPrehistory2.PROXY.isFirstPersonPlayer(event.getEntity())) {
                MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                event.setCanceled(true);
            }
            ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
        }
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END) {
            Entity cameraEntity = minecraft.getCameraEntity();
            prevShakeAmount = shakeAmount;
            float shake = 0.0F;
            Iterator<ScreenShakeEvent> groundShakeMomentIterator = SCREEN_SHAKE_EVENTS.iterator();
            while (groundShakeMomentIterator.hasNext()) {
                ScreenShakeEvent groundShakeMoment = groundShakeMomentIterator.next();
                groundShakeMoment.tick();
                if (groundShakeMoment.isDone()) groundShakeMomentIterator.remove();
                else shake = Math.max(shake, groundShakeMoment.getDegree(cameraEntity, 1.0F));
            }
            shakeAmount = shake * minecraft.options.screenEffectScale().get().floatValue();
        }
    }

    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        float partialTicks = (float) event.getPartialTick();

        float lerpedShakeAmount = Mth.clamp(prevShakeAmount + (shakeAmount - prevShakeAmount) * partialTicks, 0, 4.0F);
        if (lerpedShakeAmount > 0) {
            float time = minecraft.cameraEntity == null ? 0.0F : minecraft.cameraEntity.tickCount + minecraft.getPartialTick();
            event.setRoll((float) (lerpedShakeAmount * Math.sin(2.0F * time)));
        }
    }

    public static void renderMapDecoration(MapDecoration mapdecoration, int k) {
        if (mapdecoration.getType() == UP2MapIcons.PALEOZOIC_FOSSIL_SITE) {
            renderDecoration(mapdecoration, k, PALEOZOIC_SITE_ICON);
        }
        else if (mapdecoration.getType() == UP2MapIcons.MESOZOIC_FOSSIL_SITE) {
            renderDecoration(mapdecoration, k, MESOZOIC_SITE_ICON);
        }
        else if (mapdecoration.getType() == UP2MapIcons.PETRIFIED_TREE_SITE) {
            renderDecoration(mapdecoration, k, PETRIFIED_TREE_ICON);
        }
    }

    private static void renderDecoration(MapDecoration mapdecoration, int k, RenderType mapIcon) {
        MultiBufferSource multiBufferSource = lastMapRenderBuffer == null ? Minecraft.getInstance().renderBuffers().bufferSource() : lastMapRenderBuffer;
        PoseStack poseStack = lastMapPoseStack == null ? new PoseStack() : lastMapPoseStack;
        poseStack.pushPose();
        poseStack.translate(0.0F + (float) mapdecoration.getX() / 2.0F + 64.0F, 0.0F + (float) mapdecoration.getY() / 2.0F + 64.0F, -0.02F);
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) (mapdecoration.getRot() * 360) / 16.0F));
        poseStack.scale(4.0F, 4.0F, 3.0F);
        poseStack.translate(-0.125F, 0.125F, 0.0F);
        byte iconOrdinal = UP2MapIcons.getMapIconRenderOrdinal(mapdecoration.getType());
        float f1 = (float) (iconOrdinal % 16 + 0) / 16.0F;
        float f2 = (float) (iconOrdinal / 16 + 0) / 16.0F;
        float f3 = (float) (iconOrdinal % 16 + 1) / 16.0F;
        float f4 = (float) (iconOrdinal / 16 + 1) / 16.0F;
        Matrix4f matrix4f1 = poseStack.last().pose();
        VertexConsumer icons = multiBufferSource.getBuffer(mapIcon);
        icons.vertex(matrix4f1, -1.0F, 1.0F, (float) k * -0.001F).color(255, 255, 255, 255).uv(f1, f2).uv2(lastMapRenderPackedLight).endVertex();
        icons.vertex(matrix4f1, 1.0F, 1.0F, (float) k * -0.001F).color(255, 255, 255, 255).uv(f3, f2).uv2(lastMapRenderPackedLight).endVertex();
        icons.vertex(matrix4f1, 1.0F, -1.0F, (float) k * -0.001F).color(255, 255, 255, 255).uv(f3, f4).uv2(lastMapRenderPackedLight).endVertex();
        icons.vertex(matrix4f1, -1.0F, -1.0F, (float) k * -0.001F).color(255, 255, 255, 255).uv(f1, f4).uv2(lastMapRenderPackedLight).endVertex();
        poseStack.popPose();
        if (mapdecoration.getName() != null) {
            Font font = Minecraft.getInstance().font;
            Component component = mapdecoration.getName();
            float f6 = (float) font.width(component);
            float f7 = Mth.clamp(25.0F / f6, 0.0F, 6.0F / 9.0F);
            poseStack.pushPose();
            poseStack.translate(0.0F + (float) mapdecoration.getX() / 2.0F + 64.0F - f6 * f7 / 2.0F, 0.0F + (float) mapdecoration.getY() / 2.0F + 64.0F + 4.0F, -0.025F);
            poseStack.scale(f7, f7, 1.0F);
            poseStack.translate(0.0F, 0.0F, -0.1F);
            font.drawInBatch(component, 0.0F, 0.0F, -1, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, Integer.MIN_VALUE, lastMapRenderPackedLight);
            poseStack.popPose();
        }
    }
}
