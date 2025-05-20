package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumEntity;
import com.unusualmodding.unusual_prehistory.client.models.entity.KimmeridgebrachypteraeschnidiumModel;
import com.unusualmodding.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.unusualmodding.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumWingLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KimmeridgebrachypteraeschnidiumRenderer extends GeoEntityRenderer<KimmeridgebrachypteraeschnidiumEntity> {

    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumModel());
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumWingLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/kimmeridgebrachypteraeschnidium/base/base_" + kimmer.getBaseColor() + ".png");
    }

    @Override
    public void render(KimmeridgebrachypteraeschnidiumEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    protected void applyRotations(KimmeridgebrachypteraeschnidiumEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isFlying() && !animatable.onGround()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 180 / 4));
        }
    }
}
