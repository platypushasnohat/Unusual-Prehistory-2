package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumModel;
import com.barlinc.unusual_prehistory.client.renderer.KimmeridgebrachypteraeschnidiumRenderer;
import com.barlinc.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumBaseLayer extends RenderLayer<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel> {

    public KimmeridgebrachypteraeschnidiumBaseLayer(RenderLayerParent<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel> renderer) {
        super(renderer);
    }

    public ResourceLocation baseTextures(Kimmeridgebrachypteraeschnidium entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png");
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Kimmeridgebrachypteraeschnidium entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            ResourceLocation resourceLocation = this.baseTextures(entity);
            renderTranslucentModel(this.getParentModel(), resourceLocation, poseStack, bufferSource, packedLight, partialTicks, entity);
        }
    }

    protected static void renderTranslucentModel(EntityModel<Kimmeridgebrachypteraeschnidium> model, @NotNull ResourceLocation resourceLocation, @NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTicks, Kimmeridgebrachypteraeschnidium entity) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation));
        int i = LivingEntityRenderer.getOverlayCoords(entity, KimmeridgebrachypteraeschnidiumRenderer.getExplosionOverlayProgress(entity, partialTicks));
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}