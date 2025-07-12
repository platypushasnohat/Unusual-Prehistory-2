package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.client.models.entity.MajungasaurusModel;
import com.unusualmodding.unusual_prehistory.entity.Majungasaurus;
import com.unusualmodding.unusual_prehistory.registry.UP2EntityModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class MajungasaurusRenderer extends MobRenderer<Majungasaurus, MajungasaurusModel<Majungasaurus>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/majungasaurus.png");

    public MajungasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new MajungasaurusModel<>(context.bakeLayer(UP2EntityModelLayers.MAJUNGASAURUS)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(Majungasaurus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Majungasaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (entity.getStealthProgress(1.0F) > 0.0F) {
            return RenderType.entityTranslucent(TEXTURE);
        } else {
            return RenderType.entityCutoutNoCull(TEXTURE);
        }
    }

    protected void scale(Majungasaurus entity, PoseStack poseStack, float partialTicks) {
        float alpha = 1.0F - 0.5F * entity.getStealthProgress(partialTicks);
        this.model.setAlpha(alpha);
    }
}