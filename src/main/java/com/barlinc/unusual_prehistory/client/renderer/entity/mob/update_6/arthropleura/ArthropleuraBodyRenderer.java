package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraBodyModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraBody;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraBodyRenderer extends ArthropleuraRenderer<ArthropleuraBody, ArthropleuraBodyModel<ArthropleuraBody>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/arthropleura.png");

    private final ArthropleuraTailRenderer tailRenderer;

    public ArthropleuraBodyRenderer(EntityRendererProvider.Context context) {
        super(context, new ArthropleuraBodyModel<>(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_BODY)));
        this.tailRenderer = new ArthropleuraTailRenderer(context);
    }

    @Override
    public void render(ArthropleuraBody entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        if (entity.isTail()) {
            this.tailRenderer.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        } else {
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArthropleuraBody entity) {
        return TEXTURE;
    }
}
