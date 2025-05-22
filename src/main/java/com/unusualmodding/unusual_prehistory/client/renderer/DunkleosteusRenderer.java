package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.base.UP2Model;
import com.unusualmodding.unusual_prehistory.entity.StethacanthusEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import com.unusualmodding.unusual_prehistory.client.models.entity.dunkleosteus.*;
import com.unusualmodding.unusual_prehistory.entity.DunkleosteusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class DunkleosteusRenderer extends MobRenderer<DunkleosteusEntity, UP2Model<DunkleosteusEntity>> {

    private final DunkleosteusLargeModel<DunkleosteusEntity> dunkleosteusLargeModel;
    private final DunkleosteusMediumModel<DunkleosteusEntity> dunkleosteusMediumModel;
    private final DunkleosteusSmallModel<DunkleosteusEntity> dunkleosteusSmallModel;

    public DunkleosteusRenderer(EntityRendererProvider.Context context) {
        super(context, new DunkleosteusLargeModel<>(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_LARGE_LAYER)), 0.5F);
        this.dunkleosteusLargeModel = new DunkleosteusLargeModel<>(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_LARGE_LAYER));
        this.dunkleosteusMediumModel = new DunkleosteusMediumModel<>(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM_LAYER));
        this.dunkleosteusSmallModel = new DunkleosteusSmallModel<>(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_SMALL_LAYER));
    }

    @Override
    public void render(DunkleosteusEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        switch (entity.getDunkSize()){
            case 1:
                this.model = dunkleosteusMediumModel;
                break;
            case 2:
                this.model = dunkleosteusLargeModel;
                break;
            default:
                this.model = dunkleosteusSmallModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(DunkleosteusEntity entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.6F, 0.6F, 0.6F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(DunkleosteusEntity entity) {
        return new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/dunkleosteus/dunkleosteus_" + entity.getVariantName() + ".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(DunkleosteusEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/dunkleosteus/dunkleosteus_" + entity.getVariantName() + ".png"));
    }
}
