package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraHeadModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.Arthropleura;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraRenderer extends MobRenderer<Arthropleura, ArthropleuraHeadModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/arthropleura.png");

    public ArthropleuraRenderer(EntityRendererProvider.Context context) {
        super(context, new ArthropleuraHeadModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_HEAD)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Arthropleura entity) {
        return TEXTURE;
    }

    @Override
    protected float getFlipDegrees(@NotNull Arthropleura entity) {
        return 0.0F;
    }

    @Override
    protected void setupRotations(@NotNull Arthropleura entity, @NotNull PoseStack poseStack, float bob, float yaw, float partialTicks, float scale) {
        if (this.isShaking(entity)) {
            yaw += (float)(Math.cos((double) entity.tickCount * 3.25D) * Math.PI * (double) 0.4F);
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
        poseStack.translate(0F, 1F, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-entity.getViewXRot(partialTicks)));
        poseStack.translate(0F, -1F, 0);
        if (isEntityUpsideDown(entity)) {
            poseStack.translate(0.0F, entity.getBbHeight() + 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }
}
