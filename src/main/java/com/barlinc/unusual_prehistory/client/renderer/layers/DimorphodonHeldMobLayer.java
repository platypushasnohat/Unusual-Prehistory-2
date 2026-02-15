package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.DimorphodonModel;
import com.barlinc.unusual_prehistory.entity.Dimorphodon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class DimorphodonHeldMobLayer extends RiderLayer<Dimorphodon, DimorphodonModel> {

    public DimorphodonHeldMobLayer(RenderLayerParent<Dimorphodon, DimorphodonModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Dimorphodon entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Entity heldMob = entity.getHeldMob(entity);
        if (heldMob != null) {
            UnusualPrehistory2.PROXY.releaseRenderingEntity(heldMob.getUUID());
            poseStack.pushPose();
            this.getParentModel().translateToFeet(poseStack);
            poseStack.translate(0.0F, 2.0F * entity.getScale(), 0.0F);
            poseStack.mulPose(Axis.XN.rotationDegrees(180F));
            if (!UnusualPrehistory2.PROXY.isFirstPersonPlayer(heldMob)) {
                renderPassenger(heldMob, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
            }
            poseStack.popPose();
            UnusualPrehistory2.PROXY.blockRenderingEntity(heldMob.getUUID());
        }
    }
}