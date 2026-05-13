package com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraBodyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraTailModel;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.Arthropleura;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraPart;
import com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura.ArthropleuraPartIndex;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraPartRenderer extends EntityRenderer<ArthropleuraPart> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/arthropleura.png");

    private final ArthropleuraBodyModel BODY;
    private final ArthropleuraTailModel TAIL;

    public ArthropleuraPartRenderer(EntityRendererProvider.Context context) {
        super(context);
        BODY = new ArthropleuraBodyModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_BODY));
        TAIL = new ArthropleuraTailModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_TAIL));
    }

    @Override
    protected int getSkyLightLevel(ArthropleuraPart entity, @NotNull BlockPos pos) {
        Entity parent = entity.getParent();
        if (parent instanceof Arthropleura arthropleura) {
            return arthropleura.level().getBrightness(LightLayer.SKY, pos);
        }
        return super.getSkyLightLevel(entity, pos);
    }

    @Override
    protected int getBlockLightLevel(ArthropleuraPart entity, @NotNull BlockPos pos) {
        Entity parent = entity.getParent();
        if (parent instanceof Arthropleura arthropleura) {
            return arthropleura.isOnFire() ? 15 : arthropleura.level().getBrightness(LightLayer.BLOCK, pos);
        }
        return super.getBlockLightLevel(entity, pos);
    }

    @Override
    public boolean shouldRender(@NotNull ArthropleuraPart entity, @NotNull Frustum camera, double x, double y, double z) {
        if (super.shouldRender(entity, camera, x, y, z)) {
            return true;
        }
        Entity frontEntity = entity.getChild();
        if (frontEntity != null) {
            Vec3 position = entity.position();
            Vec3 frontPosition = frontEntity.position();
            return camera.isVisible(new AABB(frontPosition.x, frontPosition.y, frontPosition.z, position.x, position.y, position.z));
        }
        return false;
    }

    @Override
    public void render(ArthropleuraPart entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        float yRotLerp = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        poseStack.pushPose();
        poseStack.translate(0.0D, 1.0F, 0.0D);
        poseStack.mulPose(Axis.YN.rotationDegrees(yRotLerp));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.translate(0.0D, -0.5F, 0.0D);
        Entity parent = entity.getParent();
        if (parent instanceof Arthropleura arthropleura) {
            if (LivingEntityRenderer.isEntityUpsideDown(arthropleura)) {
                poseStack.translate(0.0F, entity.getBbHeight() + 1.25F, 0.0F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
        if (entity.getPartType() == ArthropleuraPartIndex.TAIL) {
            TAIL.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            TAIL.renderToBuffer(poseStack, consumer, packedLight, getOverlayCoords(entity, 0.0F), -1);
        } else {
            BODY.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            BODY.renderToBuffer(poseStack, consumer, packedLight, getOverlayCoords(entity, 0.0F), -1);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArthropleuraPart entity) {
        return TEXTURE;
    }

    public static int getOverlayCoords(ArthropleuraPart entity, float f) {
        return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(entity.renderHurtFlag));
    }
}
