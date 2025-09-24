package com.unusualmodding.unusual_prehistory.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.unusual_prehistory.items.ChiselItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext context, boolean b, PoseStack poseStack, MultiBufferSource bufferSource, int i);

    @Shadow @Final private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "renderArmWithItem", cancellable = true)
    private void renderChiselItem(AbstractClientPlayer player, float v, float v1, InteractionHand hand, float v2, ItemStack stack, float v3, PoseStack poseStack, MultiBufferSource bufferSource, int i, CallbackInfo ci) {
        boolean flag = hand == InteractionHand.MAIN_HAND;
        HumanoidArm humanoidarm = flag ? player.getMainArm() : player.getMainArm().getOpposite();
        if ((stack.getItem() instanceof ChiselItem) && player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
            ci.cancel();
            poseStack.pushPose();
            boolean flag2 = humanoidarm == HumanoidArm.RIGHT;
            this.applyChiselTransform(poseStack, v, humanoidarm, v3);
            this.renderItem(player, stack, flag2 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !flag2, poseStack, bufferSource, i);
            poseStack.popPose();
        }
    }

    @Unique
    private void applyChiselTransform(PoseStack poseStack, float v, HumanoidArm arm, float v1) {
        float f = (float) (this.minecraft.player.getUseItemRemainingTicks() % 10);
        float f1 = f - v + 1.0F;
        float f2 = 1.0F - f1 / 10.0F;
        float f7 = -15.0F + 25.0F * Mth.cos(f2 * 2.0F * (float) Math.PI);
        int i = arm == HumanoidArm.RIGHT ? 1 : -1;
        transformSideFirstPerson(poseStack, arm, v1);
        poseStack.translate(i * -0.25F, 0.22F, 0.25F + (f7 * 0.005F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-97.5F));
        poseStack.mulPose(Axis.YP.rotationDegrees(i * 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(f7 / 2));
    }

    @Unique
    private static void transformSideFirstPerson(PoseStack poseStack, HumanoidArm arm, float v) {
        int i = arm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate(((float) i * 0.56F), (-0.52F + v * -0.6F), -0.72F);
    }
}