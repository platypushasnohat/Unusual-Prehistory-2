package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraBodyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraTailModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.Arthropleura;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraPart;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraPartRenderer extends EntityRenderer<ArthropleuraPart> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/arthropleura.png");

    private final ArthropleuraBodyModel bodyModel;
    private final ArthropleuraTailModel tailModel;

    public ArthropleuraPartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.bodyModel = new ArthropleuraBodyModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_BODY));
        this.tailModel = new ArthropleuraTailModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_TAIL));
    }

    @Override
    public boolean shouldRender(@NotNull ArthropleuraPart entity, @NotNull Frustum camera, double x, double y, double z) {
        if (super.shouldRender(entity, camera, x, y, z)) {
            return true;
        } else {
            Entity front = entity.getFrontEntity();
            if (front != null) {
                Vec3 pos = entity.position();
                Vec3 frontPos = front.position();
                return camera.isVisible(new AABB(frontPos.x, frontPos.y, frontPos.z, pos.x, pos.y, pos.z));
            }
            return false;
        }
    }

    @Override
    public void render(ArthropleuraPart entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        Entity head = entity.getHeadEntity();
        Entity front = entity.getFrontEntity();
        Entity back = entity.getBackEntity();

        float yRotLerp = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRotLerp = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

        Vec3 offset = this.getOffset(entity, front, partialTicks);

        poseStack.pushPose();
        poseStack.translate(0.0D, offset.y, 0.0D);
        poseStack.translate(0.0F, 1.0F, 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.translate(0.0F, -0.5F, 0.0F);

        if (head instanceof Arthropleura arthropleura) {
            if (LivingEntityRenderer.isEntityUpsideDown(arthropleura)) {
                poseStack.translate(0.0F, entity.getBbHeight() + 1.25F, 0.0F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        if (back == null) {
            tailModel.setBodyRotation(xRotLerp, yRotLerp);
            tailModel.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
            tailModel.renderToBuffer(poseStack, consumer, packedLight, getOverlayCoords(entity, 0.0F), -1);
        } else {
            bodyModel.setBodyRotation(xRotLerp, yRotLerp);
            bodyModel.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
            bodyModel.renderToBuffer(poseStack, consumer, packedLight, getOverlayCoords(entity, 0.0F), -1);
        }
        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    private Vec3 getOffset(ArthropleuraPart entity, Entity front, float partialTicks) {
        if (front != null) {
            Vec3 frontPos = front.getPosition(partialTicks);
            Vec3 pos = entity.getPosition(partialTicks);
            Vec3 diff = frontPos.subtract(pos);
            double yOffset = Mth.clamp(Math.max(-diff.y, 0.0D) * 0.5D, 0.0D, 0.75D);
            return new Vec3(0.0D, diff.y * 0.5D + yOffset, 0.0D);
        }
        return Vec3.ZERO;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArthropleuraPart entity) {
        return TEXTURE;
    }

    public static int getOverlayCoords(ArthropleuraPart segmentEntity, float f) {
        return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(segmentEntity.renderHurtFlag));
    }
}
