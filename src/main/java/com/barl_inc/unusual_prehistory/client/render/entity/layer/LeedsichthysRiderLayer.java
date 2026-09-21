package com.barl_inc.unusual_prehistory.client.render.entity.layer;

import com.barl_inc.unusual_prehistory.client.model.LeedsichthysModel;
import com.barl_inc.unusual_prehistory.entity.cliff_fossil.Leedsichthys;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.platypushasnohat.sinew.Sinew;
import com.platypushasnohat.sinew.client.render.RiderLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class LeedsichthysRiderLayer extends RiderLayer<Leedsichthys, LeedsichthysModel> {

    public LeedsichthysRiderLayer(RenderLayerParent<Leedsichthys, LeedsichthysModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Leedsichthys entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float bodyYaw = Mth.lerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        if (entity.isVehicle()) {
            Vec3 ridePos = new Vec3(0, 0, 0);
            for (Entity passenger : entity.getPassengers()) {
                if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    continue;
                }
                Sinew.PROXY.releaseRenderingEntity(passenger.getUUID());
                poseStack.pushPose();
                this.getParentModel().translateRiderToBody(poseStack);
                poseStack.translate(ridePos.x, ridePos.y - 3.48F + passenger.getBbHeight(), ridePos.z);
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(360.0F - bodyYaw));
                renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
                poseStack.popPose();
                Sinew.PROXY.blockRenderingEntity(passenger.getUUID());
            }
        }
    }
}
