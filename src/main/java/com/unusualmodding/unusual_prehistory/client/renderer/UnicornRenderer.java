package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.client.models.entity.unicorn.UnicornModel;
import com.unusualmodding.unusual_prehistory.client.models.entity.unicorn.UnicornSkeletonModel;
import com.unusualmodding.unusual_prehistory.entity.Unicorn;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class UnicornRenderer extends MobRenderer<Unicorn, UP2Model<Unicorn>> {

    private final UnicornModel<Unicorn> unicornModel;
    private final UnicornSkeletonModel<Unicorn> unicornSkeletonModel;

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/unicorn/unicorn.png");
    private static final ResourceLocation TEXTURE_SKELETON = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/unicorn/unicorn_skeleton.png");

    public UnicornRenderer(EntityRendererProvider.Context context) {
        super(context, new UnicornModel<>(context.bakeLayer(UP2EntityModelLayers.UNICORN_LAYER)), 1f);
        this.unicornModel = new UnicornModel<>(context.bakeLayer(UP2EntityModelLayers.UNICORN_LAYER));
        this.unicornSkeletonModel = new UnicornSkeletonModel<>(context.bakeLayer(UP2EntityModelLayers.UNICORN_SKELETON_LAYER));
    }

    @Override
    public void render(Unicorn entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if (entity.isSkeletal()) {
            this.model = unicornSkeletonModel;
        } else {
            this.model = unicornModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(Unicorn entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.6f, 0.6f, 0.6f);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(Unicorn entity) {
        if (entity.isSkeletal()) {
            return TEXTURE_SKELETON;
        }
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Unicorn entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (entity.isSkeletal()) {
            return RenderType.entityCutoutNoCull(TEXTURE_SKELETON);
        }
        return RenderType.entityCutout(TEXTURE);
    }
}
