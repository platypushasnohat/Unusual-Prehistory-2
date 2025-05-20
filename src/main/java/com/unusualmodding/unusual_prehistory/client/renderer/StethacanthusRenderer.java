package com.unusualmodding.unusual_prehistory.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.registry.UP2ModelLayers;
import com.unusualmodding.unusual_prehistory.client.models.entity.StethacanthusModel;
import com.unusualmodding.unusual_prehistory.entity.StethacanthusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StethacanthusRenderer extends MobRenderer<StethacanthusEntity, StethacanthusModel<StethacanthusEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UnusualPrehistory2.MOD_ID,"textures/entity/stethacanthus.png");

    public StethacanthusRenderer(EntityRendererProvider.Context context) {
        super(context, new StethacanthusModel<>(context.bakeLayer(UP2ModelLayers.STETHACANTHUS_LAYER)), 0.5F);
    }

    @Override
    protected void scale(StethacanthusEntity entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.5F, 0.5F, 0.5F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(StethacanthusEntity entity) {
        return TEXTURE;
    }
}
