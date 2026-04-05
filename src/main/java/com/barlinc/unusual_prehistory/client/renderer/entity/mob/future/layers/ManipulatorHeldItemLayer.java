//package com.barlinc.unusual_prehistory.client.renderer.entity.mob.future.layers;
//
//import com.barlinc.unusual_prehistory.client.models.entity.mob.future.ManipulatorModel;
//import com.barlinc.unusual_prehistory.entity.mob.future.Manipulator;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Axis;
//import net.minecraft.client.renderer.ItemInHandRenderer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.world.entity.HumanoidArm;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import org.jetbrains.annotations.NotNull;
//
//@OnlyIn(Dist.CLIENT)
//public class ManipulatorHeldItemLayer extends RenderLayer<Manipulator, ManipulatorModel> {
//
//    private final ItemInHandRenderer itemInHandRenderer;
//
//    public ManipulatorHeldItemLayer(RenderLayerParent<Manipulator, ManipulatorModel> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
//        super(pRenderer);
//        this.itemInHandRenderer = pItemInHandRenderer;
//    }
//
//    @Override
//    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Manipulator entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
//        boolean flag = entity.getMainArm() == HumanoidArm.RIGHT;
//        ItemStack leftStack = flag ? entity.getOffhandItem() : entity.getMainHandItem();
//        ItemStack rightStack = flag ? entity.getMainHandItem() : entity.getOffhandItem();
//        if (!leftStack.isEmpty() || !rightStack.isEmpty()) {
//            poseStack.pushPose();
//            this.renderArmWithItem(entity, rightStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, bufferSource, packedLight);
//            this.renderArmWithItem(entity, leftStack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, bufferSource, packedLight);
//            poseStack.popPose();
//        }
//    }
//
//    protected void renderArmWithItem(Manipulator entity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        if (!itemStack.isEmpty()) {
//            poseStack.pushPose();
//            boolean flag = arm == HumanoidArm.LEFT;
//            this.getParentModel().translateToHand(arm, poseStack);
//            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
//            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
//            poseStack.mulPose(Axis.YP.rotationDegrees(flag ? -60.0F : 60.0F));
//            poseStack.translate(0.0F, 0.05F, -0.25F);
//            this.itemInHandRenderer.renderItem(entity, itemStack, displayContext, flag, poseStack, bufferSource, packedLight);
//            poseStack.popPose();
//        }
//    }
//}