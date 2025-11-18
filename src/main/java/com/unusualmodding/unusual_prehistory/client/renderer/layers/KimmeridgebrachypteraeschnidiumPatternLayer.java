package com.unusualmodding.unusual_prehistory.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.KimmeridgebrachypteraeschnidiumModel;
import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumPatternLayer extends RenderLayer<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel> {

    public KimmeridgebrachypteraeschnidiumPatternLayer(RenderLayerParent<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel> renderer) {
        super(renderer);
    }

    public ResourceLocation patternTextures(Kimmeridgebrachypteraeschnidium entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/kimmeridgebrachypteraeschnidium/patterns/" + Kimmeridgebrachypteraeschnidium.getPatternName(entity.getPattern()) + "/pattern_" + Kimmeridgebrachypteraeschnidium.getPatternName(entity.getPattern()) + "_" + entity.getPatternColor() + ".png");
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Kimmeridgebrachypteraeschnidium entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            ResourceLocation resourceLocation = patternTextures(entity);
            renderColoredCutoutModel(this.getParentModel(), resourceLocation, poseStack, bufferSource, packedLight, entity, 1.0F, 1.0F, 1.0F);
        }
    }
}
