package com.barlinc.unusual_prehistory.mixins.client;

import com.barlinc.unusual_prehistory.events.ModelRotationEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer<LivingEntity> implements RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> {

    @Shadow
    protected EntityModel<LivingEntity> model;

    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V", at = @At("HEAD"), cancellable = true)
    protected void unusualPrehistory$onSetupRotation(LivingEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale, CallbackInfo ci) {
        ModelRotationEvent event = new ModelRotationEvent(entity, this.model, poseStack);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}