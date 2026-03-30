package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.DesmatosuchusModel;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Desmatosuchus;
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

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class DesmatosuchusDirtyLayer extends RenderLayer<Desmatosuchus, DesmatosuchusModel> {

    public DesmatosuchusDirtyLayer(RenderLayerParent<Desmatosuchus, DesmatosuchusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Desmatosuchus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || !entity.isDirty()) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getDirtyTexture(entity)));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public ResourceLocation getDirtyTexture(Desmatosuchus entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/desmatosuchus/" + entity.getDirtType().name().toLowerCase(Locale.ROOT) + ".png");
    }
}