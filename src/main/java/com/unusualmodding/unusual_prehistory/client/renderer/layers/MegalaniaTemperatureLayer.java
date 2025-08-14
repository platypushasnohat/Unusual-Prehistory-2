package com.unusualmodding.unusual_prehistory.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.unusual_prehistory.client.models.entity.MegalaniaModel;
import com.unusualmodding.unusual_prehistory.entity.Megalania;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MegalaniaTemperatureLayer<T extends Megalania, M extends MegalaniaModel<T>> extends RenderLayer<T, M> {

    private final ResourceLocation texture;
    private final AlphaFunction<T> alphaFunction;

    public MegalaniaTemperatureLayer(RenderLayerParent<T, M> parent, ResourceLocation resourceLocation, AlphaFunction<T> alpha) {
        super(parent);
        this.texture = resourceLocation;
        this.alphaFunction = alpha;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int p_234904_, T entity, float p_234906_, float p_234907_, float p_234908_, float p_234909_, float p_234910_, float p_234911_) {
        if (!entity.isInvisible()) {
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(this.texture));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, p_234904_, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, this.alphaFunction.apply(entity, p_234908_, p_234909_));
            this.resetDrawForAllParts();
        }
    }

    private void resetDrawForAllParts() {
        this.getParentModel().root().getAllParts().forEach((modelPart) -> modelPart.skipDraw = false);
    }

    @OnlyIn(Dist.CLIENT)
    public interface AlphaFunction<T extends Megalania> {
        float apply(T entity, float p_234921_, float p_234922_);
    }

    @OnlyIn(Dist.CLIENT)
    public interface DrawSelector<T extends Megalania, M extends EntityModel<T>> {
        List<ModelPart> getPartsToDraw(M model);
    }
}


