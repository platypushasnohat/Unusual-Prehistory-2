package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.KimmeridgebrachypteraeschnidiumModel;
import com.unusualmodding.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumBaseLayer;
import com.unusualmodding.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.unusualmodding.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumWingLayer;
import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumRenderer extends MobRenderer<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel<Kimmeridgebrachypteraeschnidium>> {

    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumModel<>(context.bakeLayer(UP2EntityModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM)), 0.3F);
        this.addLayer(new KimmeridgebrachypteraeschnidiumBaseLayer(this));
        this.addLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
        this.addLayer(new KimmeridgebrachypteraeschnidiumWingLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Kimmeridgebrachypteraeschnidium entity) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png");
    }

    @Override
    public void render(Kimmeridgebrachypteraeschnidium entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (!entity.isInvisible()) {
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    @Override
    protected @Nullable RenderType getRenderType(Kimmeridgebrachypteraeschnidium entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png"));
    }
}
