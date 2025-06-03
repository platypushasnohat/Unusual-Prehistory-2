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

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumBaseLayer extends RenderLayer<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel<Kimmeridgebrachypteraeschnidium>> {

    public KimmeridgebrachypteraeschnidiumBaseLayer(RenderLayerParent<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel<Kimmeridgebrachypteraeschnidium>> renderer) {
        super(renderer);
    }

    public ResourceLocation baseTextures(Kimmeridgebrachypteraeschnidium entity) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png");
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Kimmeridgebrachypteraeschnidium entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!entity.isInvisible()) {
            ResourceLocation resourceLocation = baseTextures(entity);
            renderColoredCutoutModel(this.getParentModel(), resourceLocation, pPoseStack, pBuffer, pPackedLight, entity, 1.0F, 1.0F, 1.0F);
        }
    }
}