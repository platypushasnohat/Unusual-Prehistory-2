package com.unusualmodding.unusual_prehistory.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.MegalaniaModel;
import com.unusualmodding.unusual_prehistory.entity.Megalania;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MegalaniaTemperatureLayer<T extends Megalania, M extends MegalaniaModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/megalania/megalania_temperate.png");
    private static final ResourceLocation TEXTURE_COLD = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_WARM = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/megalania/megalania_warm.png");
    private static final ResourceLocation TEXTURE_NETHER = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/megalania/megalania_nether.png");

    public MegalaniaTemperatureLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render (PoseStack matrixStackIn, MultiBufferSource buffer,int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            float transProgress = entity.prevTempProgress + (entity.tempProgress - entity.prevTempProgress) * partialTicks;
            float a = 1F;
            if (a == 1.0F) {
                a *= 0.9F + 0.1F * (float) Math.sin(entity.tickCount * 0.1F);
            }
            if (entity.getPrevTemperatureState() != null) {
                float alphaPrev = 1 - transProgress * 0.2F;
                VertexConsumer prev = buffer.getBuffer(RenderType.entityTranslucent(getFor(entity.getPrevTemperatureState())));
                if (entity.getPrevTemperatureState() == entity.getTemperatureState()) {
                    alphaPrev *= a;
                }
                this.getParentModel().renderToBuffer(matrixStackIn, prev, packedLightIn, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, alphaPrev);
            }
            float alphaCurrent = transProgress * 0.2F;
            VertexConsumer current = buffer.getBuffer(RenderType.entityTranslucent(getFor(entity.getTemperatureState())));
            this.getParentModel().renderToBuffer(matrixStackIn, current, packedLightIn, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, a * alphaCurrent);
        }
    }

    public ResourceLocation getFor (Megalania.TemperatureState state) {
        if (state == Megalania.TemperatureState.COLD) {
            return TEXTURE_COLD;
        }
        if (state == Megalania.TemperatureState.WARM) {
            return TEXTURE_WARM;
        }
        if (state == Megalania.TemperatureState.NETHER) {
            return TEXTURE_NETHER;
        }
        return TEXTURE;
    }
}