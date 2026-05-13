package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class ArthropleuraRenderer<T extends ArthropleuraPart, M extends UP2Model<T>> extends MobRenderer<T, M> {

    public ArthropleuraRenderer(EntityRendererProvider.Context context, M model) {
        super(context, model, 0.5F);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        this.model.young = entity.isBaby();
        float bodyRotationX = entity.getBodyRotX(partialTicks);
        float bodyRotationY = entity.getBodyRotY(partialTicks);
        float headRotationX = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        float headRotationY = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        float neckRotationX = headRotationX - bodyRotationX;
        float neckRotationY = headRotationY - bodyRotationY;
        this.model.setBodyRotation(bodyRotationX, bodyRotationY);
        float rotationBob = this.getBob(entity, partialTicks);
        this.setupRotations(entity, poseStack, rotationBob, 0.0F, partialTicks, entity.getScale());
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity, poseStack, partialTicks);
        poseStack.translate(0.0D, -1.501F, 0.0D);
        float animationSpeed = 0.0F;
        float animationAmount = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            if (entity.isBaby()) {
                animationAmount *= 3.0F;
            }
        }
        this.model.setupAnim(entity, animationAmount, animationSpeed, rotationBob, neckRotationY, neckRotationX);
        this.model.prepareMobModel(entity, animationAmount, animationSpeed, partialTicks);
        Minecraft minecraft = Minecraft.getInstance();
        boolean isSpectator = !this.isBodyVisible(entity) && !entity.isInvisibleTo(Objects.requireNonNull(minecraft.player));
        boolean isGlowing = minecraft.shouldEntityAppearGlowing(entity);
        RenderType rendertype = this.getRenderType(entity, this.isBodyVisible(entity), isSpectator, isGlowing);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = bufferSource.getBuffer(rendertype);
            int i = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks));
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, -1);
        }
        if (!entity.isSpectator()) {
            for (RenderLayer<T, M> renderlayer : this.layers) {
                renderlayer.render(poseStack, bufferSource, packedLight, entity, animationAmount, animationSpeed, partialTicks, rotationBob, neckRotationY, neckRotationX);
            }
        }
        poseStack.popPose();
    }
}