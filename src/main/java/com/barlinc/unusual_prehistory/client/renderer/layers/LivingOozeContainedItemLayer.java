package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.client.models.entity.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.renderer.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.entity.LivingOoze;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class LivingOozeContainedItemLayer extends RenderLayer<LivingOoze, LivingOozeModel> {

    public LivingOozeContainedItemLayer(LivingOozeRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, @NotNull LivingOoze entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = entity.getMainHandItem();
        if (!itemstack.isEmpty()) {
            if(bufferSource instanceof MultiBufferSource.BufferSource source) {
                poseStack.pushPose();

                poseStack.translate(0.0F, 0.35F, 0.0F);
                poseStack.mulPose(new Quaternionf().rotateX(Mth.PI));
                Minecraft.getInstance()
                        .getItemRenderer()
                        .renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, source, entity.level(), (int) entity.blockPosition().asLong());
                source.endBatch();
            }
            poseStack.popPose();
        }
    }
}
