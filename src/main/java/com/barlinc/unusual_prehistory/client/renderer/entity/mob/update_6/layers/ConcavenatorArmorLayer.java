package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ConcavenatorModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.Concavenator;
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

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class ConcavenatorArmorLayer extends RenderLayer<Concavenator, ConcavenatorModel> {

    public ConcavenatorArmorLayer(RenderLayerParent<Concavenator, ConcavenatorModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Concavenator entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || !entity.hasArmor()) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getDirtyTexture(entity)));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), ColorUtils.packColor(1.0F, 1.0F, 1.0F, 1.0F));
    }

    public ResourceLocation getDirtyTexture(Concavenator entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/concavenator/" + entity.getArmorType().name().toLowerCase(Locale.ROOT) + ".png");
    }
}