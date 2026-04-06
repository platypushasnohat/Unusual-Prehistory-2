package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Aegirocassis;
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
public class AegirocassisGlowLayer extends RenderLayer<Aegirocassis, UP2Model<Aegirocassis>> {

    private static final ResourceLocation GLOW_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/aegirocassis/aegirocassis_glow.png");

    public AegirocassisGlowLayer(RenderLayerParent<Aegirocassis, UP2Model<Aegirocassis>> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Aegirocassis entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || entity.isBaby()) return;
        float glowProgress = entity.getGlowProgress(partialTicks);
        if (glowProgress <= 0.0F) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(GLOW_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), (int) glowProgress);
    }
}