package com.unusualmodding.unusual_prehistory.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

    private final ResourceLocation texture;
    private final AlphaFunction<T> alphaFunction;

    public MegalaniaTemperatureLayer(RenderLayerParent<T, M> parent, ResourceLocation resourceLocation, AlphaFunction<T> alpha) {
        super(parent);
        this.texture = resourceLocation;
        this.alphaFunction = alpha;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(this.texture));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, this.alphaFunction.apply(entity, partialTicks, ageInTicks));
            this.resetDrawForAllParts();
        }
    }

    private void resetDrawForAllParts() {
        this.getParentModel().root().getAllParts().forEach((modelPart) -> modelPart.skipDraw = false);
    }

    @OnlyIn(Dist.CLIENT)
    public interface AlphaFunction<T extends Megalania> {
        float apply(T entity, float partialTicks, float ageInTicks);
    }
}