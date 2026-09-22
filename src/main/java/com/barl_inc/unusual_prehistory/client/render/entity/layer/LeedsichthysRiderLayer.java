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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class LeedsichthysRiderLayer extends RiderLayer<Leedsichthys, LeedsichthysModel> {

    public LeedsichthysRiderLayer(RenderLayerParent<Leedsichthys, LeedsichthysModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Leedsichthys entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float bodyYaw = Mth.lerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        if (entity.isVehicle()) {
            Vec3 offset = new Vec3(0.0F, -0.75F, 0.0F);
            Vec3 ridePos = this.getParentModel().getRiderPosition(offset);
            for (Entity passenger : entity.getPassengers()) {
                if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    continue;
                }
                Sinew.PROXY.releaseRenderingEntity(passenger.getUUID());
                poseStack.pushPose();
                int index = Math.max(entity.getPassengers().indexOf(passenger), 0);
                float offsetZ = 0.0F;
                float offsetX = 0.0F;
                switch (index) {
                    case 0 -> {
                        offsetX = 1.0F;
                        offsetZ = -1.0F;
                    }
                    case 1 -> {
                        offsetX = -1.0F;
                        offsetZ = -1.0F;
                    }
                    case 2 -> {
                        offsetX = 1.0F;
                        offsetZ = 1.0F;
                    }
                    case 3 -> {
                        offsetX = -1.0F;
                        offsetZ = 1.0F;
                    }
                }
                float offsetY = passenger instanceof Player ? 0.6F : 0.0F;
                poseStack.translate(ridePos.x + offsetX, ridePos.y + offsetY, ridePos.z + offsetZ);
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(360.0F - bodyYaw));
                passenger.setYBodyRot(entity.getYRot());
                renderPassenger(passenger, 0.0F, partialTicks, poseStack, bufferSource, packedLight);
                poseStack.popPose();
                Sinew.PROXY.blockRenderingEntity(passenger.getUUID());
            }
        }
    }
}
