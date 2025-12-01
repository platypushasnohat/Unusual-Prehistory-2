package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.client.models.entity.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.renderer.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.entity.LivingOoze;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LivingOozeContainedItemLayer extends RenderLayer<LivingOoze, LivingOozeModel> {

    public LivingOozeContainedItemLayer(LivingOozeRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, @NotNull LivingOoze entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = entity.getMainHandItem();


        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.5F, 0.0F);
            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), poseStack, bufferSource, entity.level(), 0);
            poseStack.popPose();
        }
    }
}
