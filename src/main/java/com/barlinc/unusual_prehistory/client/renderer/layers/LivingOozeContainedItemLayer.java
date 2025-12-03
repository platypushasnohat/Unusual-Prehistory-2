package com.barlinc.unusual_prehistory.client.renderer.layers;

import com.barlinc.unusual_prehistory.client.models.entity.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.renderer.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.entity.LivingOoze;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
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
        float age = entity.tickCount + partialTicks;
        float yRot = age / 5;
        if (!itemstack.isEmpty() && !entity.isInvisible() && !entity.isCurrentlyGlowing()) {
            if (bufferSource instanceof MultiBufferSource.BufferSource source) {
                poseStack.pushPose();
                this.getParentModel().translateToCore(poseStack);
                float yPos = entity.getPose() == UP2Poses.SPITTING.get() ? 0.0F : (float) (0.0F + (Math.sin(age * 0.07F) * 0.03F));
                poseStack.translate(0.0F, yPos, 0.0F);
                poseStack.mulPose(new Quaternionf().rotateX(Mth.PI));
                poseStack.mulPose(Axis.YP.rotationDegrees(yRot * (30F / (float) Math.PI)));
                float xScale = (this.getParentModel().core_squish.xScale * this.getParentModel().core.xScale) * 0.38F;
                float yScale = (this.getParentModel().core_squish.yScale * this.getParentModel().core.yScale) * 0.38F;
                float zScale = (this.getParentModel().core_squish.zScale * this.getParentModel().core.zScale) * 0.38F;
                poseStack.scale(xScale, yScale, zScale);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, source, entity.level(), (int) entity.blockPosition().asLong());
                source.endBatch();
            }
            poseStack.popPose();
        }
    }
}
