package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UlughbegsaurusModel;
import com.barlinc.unusual_prehistory.entity.Ulughbegsaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class UlughbegsaurusRiderLayer extends RiderLayer<Ulughbegsaurus, UlughbegsaurusModel> {

    public UlughbegsaurusRiderLayer(RenderLayerParent<Ulughbegsaurus, UlughbegsaurusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Ulughbegsaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float bodyYaw = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks;
        if (entity.isVehicle()) {
            Vec3 offset = new Vec3(0, 0, 0.05F);
            Vec3 ridePos = this.getParentModel().getRiderPosition(offset);
            for (Entity passenger : entity.getPassengers()) {
                if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    continue;
                }
                UnusualPrehistory2.PROXY.releaseRenderingEntity(passenger.getUUID());
                poseStack.pushPose();
                poseStack.translate(ridePos.x, ridePos.y - 2.0F + passenger.getBbHeight(), ridePos.z);
                poseStack.mulPose(Axis.XN.rotationDegrees(180F));
                poseStack.mulPose(Axis.YN.rotationDegrees(360 - bodyYaw));
                renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
                poseStack.popPose();
                UnusualPrehistory2.PROXY.blockRenderingEntity(passenger.getUUID());
            }
        }
    }
}
