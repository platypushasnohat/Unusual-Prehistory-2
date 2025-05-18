package com.unusualmodding.unusual_prehistory.entity.client.renders.layers;

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

public class KimmeridgebrachypteraeschnidiumPatternLayer extends GeoRenderLayer<KimmeridgebrachypteraeschnidiumEntity> {

    public KimmeridgebrachypteraeschnidiumPatternLayer(GeoRenderer<KimmeridgebrachypteraeschnidiumEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, KimmeridgebrachypteraeschnidiumEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!entity.isInvisible()) {
            if (entity.getHasPattern()) {
                RenderType cameo = RenderType.entityCutoutNoCull(new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/patterns/" + entity.getPatternName(entity.getPattern()) + "/pattern_" + entity.getPatternName(entity.getPattern()) + "_" + entity.getPatternColor() + ".png"));
                ResourceLocation model = new ResourceLocation(UnusualPrehistory2.MOD_ID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
                this.getRenderer().reRender(this.getGeoModel().getBakedModel(model), poseStack, bufferSource, entity, renderType, bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
