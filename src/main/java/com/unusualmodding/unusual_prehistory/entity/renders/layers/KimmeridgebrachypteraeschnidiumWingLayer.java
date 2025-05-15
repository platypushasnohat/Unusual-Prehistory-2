package com.unusualmodding.unusual_prehistory.entity.renders.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.custom.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class KimmeridgebrachypteraeschnidiumWingLayer extends GeoRenderLayer<KimmeridgebrachypteraeschnidiumEntity> {

    public KimmeridgebrachypteraeschnidiumWingLayer(GeoRenderer<KimmeridgebrachypteraeschnidiumEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, KimmeridgebrachypteraeschnidiumEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!entity.isInvisible()) {
            RenderType cameo = RenderType.entityCutoutNoCull(new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/wings/" + "/wings_" + entity.getWingColor()+".png"));
            ResourceLocation model = new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
            this.getRenderer().reRender(this.getGeoModel().getBakedModel(model), poseStack, bufferSource, entity, renderType, bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
