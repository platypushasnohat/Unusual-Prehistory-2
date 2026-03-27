package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.MosasaurusModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.layers.RiderLayer;
import com.barlinc.unusual_prehistory.entity.mob.update_5.Mosasaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MosasaurusHeldMobLayer extends RiderLayer<Mosasaurus, MosasaurusModel> {

    public MosasaurusHeldMobLayer(RenderLayerParent<Mosasaurus, MosasaurusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Mosasaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Entity heldMob = entity.getHeldMob(entity);
        if (heldMob != null) {
            UnusualPrehistory2.PROXY.releaseRenderingEntity(heldMob.getUUID());
            poseStack.pushPose();
            this.getParentModel().translateToMouth(poseStack);
            poseStack.translate(1.5F * entity.getScale(), -0.7F * entity.getScale(), -2.0F * entity.getScale());
            poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            if (!UnusualPrehistory2.PROXY.isFirstPersonPlayer(heldMob)) {
                renderPassenger(heldMob, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
            }
            poseStack.popPose();
            UnusualPrehistory2.PROXY.blockRenderingEntity(heldMob.getUUID());
        }
    }
}