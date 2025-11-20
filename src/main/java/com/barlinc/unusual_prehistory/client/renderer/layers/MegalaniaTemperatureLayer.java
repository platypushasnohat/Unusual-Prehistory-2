package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.MegalaniaModel;
import com.barlinc.unusual_prehistory.entity.Megalania;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class MegalaniaTemperatureLayer extends RenderLayer<Megalania, MegalaniaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_temperate.png");
    private static final ResourceLocation TEXTURE_COLD = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_WARM = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_warm.png");
    private static final ResourceLocation TEXTURE_NETHER = UnusualPrehistory2.modPrefix("textures/entity/megalania/megalania_nether.png");

    public MegalaniaTemperatureLayer(RenderLayerParent<Megalania, MegalaniaModel> parent) {
        super(parent);
    }

    @Override
    public void render (@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Megalania entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            float tempProgress = entity.prevTempProgress + (entity.tempProgress - entity.prevTempProgress) * partialTicks;
            float a = 1F;
            if (entity.getPrevTemperatureState() != null) {
                float alphaPrev = 1 - tempProgress * 0.2F;
                VertexConsumer prev = buffer.getBuffer(RenderType.entityTranslucent(getTexture(entity.getPrevTemperatureState())));
                if (entity.getPrevTemperatureState() == entity.getTemperatureState()) {
                    alphaPrev *= a;
                }
                this.getParentModel().renderToBuffer(poseStack, prev, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, alphaPrev);
            }
            float alphaCurrent = tempProgress * 0.2F;
            VertexConsumer current = buffer.getBuffer(RenderType.entityTranslucent(getTexture(entity.getTemperatureState())));
            this.getParentModel().renderToBuffer(poseStack, current, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, a * alphaCurrent);
        }
    }

    public ResourceLocation getTexture(Megalania.TemperatureStates state) {
        if (state == Megalania.TemperatureStates.COLD) {
            return TEXTURE_COLD;
        }
        else if (state == Megalania.TemperatureStates.WARM) {
            return TEXTURE_WARM;
        }
        else if (state == Megalania.TemperatureStates.NETHER) {
            return TEXTURE_NETHER;
        }
        else if (state == Megalania.TemperatureStates.TEMPERATE) {
            return TEXTURE;
        }
        else return TEXTURE;
    }
}