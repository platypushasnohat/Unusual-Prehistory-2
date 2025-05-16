package com.unusualmodding.unusual_prehistory.blocks.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.unusual_prehistory.blocks.custom.blockentity.CultivatorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CultivatorBlockEntityRenderer implements BlockEntityRenderer<CultivatorBlockEntity> {

    public CultivatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CultivatorBlockEntity blockEntity, float partialtick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        float age = blockEntity.tickCount + partialtick;

        float rotateAngleY = age / 5;

        ItemStack itemStack = blockEntity.getRenderStack();

        poseStack.pushPose();
        poseStack.translate(0.5, 1 + Math.sin(age * 0.05) * 0.2, 0.5);
        poseStack.scale(0.5F, 0.5F, 0.5F);

        poseStack.mulPose(Axis.YP.rotationDegrees(rotateAngleY * (45F / (float) Math.PI)));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.GUI, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(),1);

        poseStack.popPose();
    }
}