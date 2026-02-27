package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.MajungasaurusModel;
import com.barlinc.unusual_prehistory.entity.Majungasaurus;
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
public class MajungasaurusEyesLayer extends RenderLayer<Majungasaurus, MajungasaurusModel> {

    public MajungasaurusEyesLayer(RenderLayerParent<Majungasaurus, MajungasaurusModel> parentModel) {
        super(parentModel);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Majungasaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || entity.isCamo()) return;
        float eyeGlowProgress = entity.getEyeGlowProgress(partialTicks);
        if (eyeGlowProgress <= 0.0F) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(this.getEyeTexture(entity)));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, eyeGlowProgress);
    }

    public ResourceLocation getEyeTexture(Majungasaurus entity) {
        Majungasaurus.MajungasaurusVariant variant = Majungasaurus.MajungasaurusVariant.byId(entity.getVariant());
        if (entity.isAggressive()) return UnusualPrehistory2.modPrefix("textures/entity/majungasaurus/" + variant.name().toLowerCase(Locale.ROOT) + "_angry_eyes.png");
        return UnusualPrehistory2.modPrefix("textures/entity/majungasaurus/majungasaurus_eyes.png");
    }
}