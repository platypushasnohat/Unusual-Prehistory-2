package com.barlinc.unusual_prehistory.mixins.client;

import com.barlinc.unusual_prehistory.events.ClientForgeEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapRenderer.MapInstance.class)
public class MapRendererMapInstanceMixin {

    @Inject(method = {"draw(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ZI)V"}, at = @At(value = "HEAD"))
    private void ac_render(PoseStack poseStack, MultiBufferSource multiBufferSource, boolean inFrame, int packedLighting, CallbackInfo ci) {
        ClientForgeEvents.lastMapPoseStack = poseStack;
        ClientForgeEvents.lastMapRenderBuffer = multiBufferSource;
        ClientForgeEvents.lastMapRenderPackedLight = packedLighting;
    }
}
