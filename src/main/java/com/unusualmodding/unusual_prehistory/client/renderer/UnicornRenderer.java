package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusLargeModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusMediumModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.DunkleosteusSmallModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.unicorn.UnicornModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.unicorn.UnicornSkeletonModel;
import com.unusualmodding.unusual_prehistory.entity.DunkleosteusEntity;
import com.unusualmodding.unusual_prehistory.entity.JawlessFishEntity;
import com.unusualmodding.unusual_prehistory.entity.ScaumenaciaEntity;
import com.unusualmodding.unusual_prehistory.entity.UnicornEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class UnicornRenderer extends MobRenderer<UnicornEntity, UP2Model<UnicornEntity>> {

    private final UnicornModel<UnicornEntity> unicornModel;
    private final UnicornSkeletonModel<UnicornEntity> unicornSkeletonModel;

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/unicorn/unicorn.png");
    private static final ResourceLocation TEXTURE_SKELETON = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/unicorn/unicorn_skeleton.png");

    public UnicornRenderer(EntityRendererProvider.Context context) {
        super(context, new UnicornModel<>(context.bakeLayer(UP2ModelLayers.UNICORN_LAYER)), 1f);
        this.unicornModel = new UnicornModel<>(context.bakeLayer(UP2ModelLayers.UNICORN_LAYER));
        this.unicornSkeletonModel = new UnicornSkeletonModel<>(context.bakeLayer(UP2ModelLayers.UNICORN_SKELETON_LAYER));
    }

    @Override
    public void render(UnicornEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if (entity.isSkeletal()) {
            this.model = unicornSkeletonModel;
        } else {
            this.model = unicornModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(UnicornEntity entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.6f, 0.6f, 0.6f);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(UnicornEntity entity) {
        if (entity.isSkeletal()) {
            return TEXTURE_SKELETON;
        }
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(UnicornEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (entity.isSkeletal()) {
            return RenderType.entityCutoutNoCull(TEXTURE_SKELETON);
        }
        return RenderType.entityCutout(TEXTURE);
    }
}
