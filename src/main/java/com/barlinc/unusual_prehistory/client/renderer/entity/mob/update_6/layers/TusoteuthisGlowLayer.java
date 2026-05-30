package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.TusoteuthisModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Tusoteuthis;
import com.barlinc.unusual_prehistory.utils.ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class TusoteuthisGlowLayer extends RenderLayer<Tusoteuthis, TusoteuthisModel> {

    private static final ResourceLocation GLOW_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/tusoteuthis/tusoteuthis_glow.png");

    public TusoteuthisGlowLayer(RenderLayerParent<Tusoteuthis, TusoteuthisModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Tusoteuthis entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            float pulse = ((float)Math.sin(ageInTicks * 0.05F + entity.getId()) + 1.0F) * 0.5F;
            pulse *= pulse;
            float alpha = 0.1F + pulse * 0.9F;
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(GLOW_TEXTURE));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), ColorUtils.packColor(1.0F, 1.0F, 1.0F, alpha));
        }
    }
}