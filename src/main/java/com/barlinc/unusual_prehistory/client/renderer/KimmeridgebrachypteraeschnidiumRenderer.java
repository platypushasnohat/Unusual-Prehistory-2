package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumModel;
import com.barlinc.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumBaseLayer;
import com.barlinc.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.barlinc.unusual_prehistory.client.renderer.layers.KimmeridgebrachypteraeschnidiumWingLayer;
import com.barlinc.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumRenderer extends MobRenderer<Kimmeridgebrachypteraeschnidium, KimmeridgebrachypteraeschnidiumModel> {

    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumModel(context.bakeLayer(UP2ModelLayers.KIMMERIDGEBRACHYTERAESCHNIDIUM)), 0.3F);
        this.addLayer(new KimmeridgebrachypteraeschnidiumBaseLayer(this));
        this.addLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
        this.addLayer(new KimmeridgebrachypteraeschnidiumWingLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Kimmeridgebrachypteraeschnidium entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png");
    }

    @Override
    public void render(Kimmeridgebrachypteraeschnidium entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        if (!entity.isInvisible()) {
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    @Override
    protected void scale(Kimmeridgebrachypteraeschnidium entity, PoseStack poseStack, float partialTicks) {
        float f = entity.getSwelling(partialTicks);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.05F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
    }

    public static float getExplosionOverlayProgress(Kimmeridgebrachypteraeschnidium entity, float partialTicks) {
        float f = entity.getSwelling(partialTicks);
        return (int) (f * 20.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Kimmeridgebrachypteraeschnidium entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(this.getTextureLocation(entity));
    }
}
