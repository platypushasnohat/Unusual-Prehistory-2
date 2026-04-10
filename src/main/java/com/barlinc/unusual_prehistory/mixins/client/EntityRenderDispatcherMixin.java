package com.barlinc.unusual_prehistory.mixins.client;

import com.barlinc.unusual_prehistory.entity.mob.update_1.Majungasaurus;
import com.barlinc.unusual_prehistory.entity.mob.update_2.Onchopristis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "renderHitbox", at = @At("TAIL"))
    private static void unusualPrehistory2$renderHitboxTail(PoseStack poseStack, VertexConsumer buffer, Entity entity, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if (entity instanceof Onchopristis onchopristis) {
            AABB aABB = onchopristis.getAggroHitbox().move(-entity.getX(), -entity.getY(), -entity.getZ());
            LevelRenderer.renderLineBox(poseStack, buffer, aABB, 1, 0, 0, 1.0F);
        }
    }

    @Inject(method = "renderHitbox", at = @At("HEAD"), cancellable = true)
    private static void unusualPrehistory2$renderHitboxHead(PoseStack poseStack, VertexConsumer buffer, Entity entity, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if (entity instanceof Majungasaurus majungasaurus && majungasaurus.isCamo()) {
            ci.cancel();
        }
    }
}