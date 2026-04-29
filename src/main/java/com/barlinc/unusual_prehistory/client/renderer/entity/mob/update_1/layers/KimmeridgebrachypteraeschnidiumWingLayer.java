package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1.KimmeridgebrachypteraeschnidiumRenderer;
import com.barlinc.unusual_prehistory.entity.mob.update_1.Kimmeridgebrachypteraeschnidium;
import com.barlinc.unusual_prehistory.utils.ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumWingLayer extends RenderLayer<Kimmeridgebrachypteraeschnidium, UP2Model<Kimmeridgebrachypteraeschnidium>> {

    public KimmeridgebrachypteraeschnidiumWingLayer(RenderLayerParent<Kimmeridgebrachypteraeschnidium, UP2Model<Kimmeridgebrachypteraeschnidium>> renderer) {
        super(renderer);
    }

    public ResourceLocation wingTextures(Kimmeridgebrachypteraeschnidium entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/kimmeridgebrachypteraeschnidium/wings/wings_" + entity.getWingColor() + ".png");
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Kimmeridgebrachypteraeschnidium entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible() && !entity.isBaby()) {
            ResourceLocation resourceLocation = this.wingTextures(entity);
            renderTranslucentModel(this.getParentModel(), resourceLocation, poseStack, bufferSource, packedLight, partialTicks, entity);
        }
    }

    protected static void renderTranslucentModel(EntityModel<Kimmeridgebrachypteraeschnidium> model, @NotNull ResourceLocation resourceLocation, @NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTicks, Kimmeridgebrachypteraeschnidium entity) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation));
        int i = LivingEntityRenderer.getOverlayCoords(entity, KimmeridgebrachypteraeschnidiumRenderer.getExplosionOverlayProgress(entity, partialTicks));
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, ColorUtils.packColor(1, 1, 1, 1));
    }
}